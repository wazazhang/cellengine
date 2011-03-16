package cv.unit;

import javax.microedition.lcdui.Graphics;

import com.cell.CObject;
import com.cell.game.CSprite;
import com.cell.game.IState;
import com.cell.particle.CParticle;
import com.cell.particle.CParticleSystem;
import com.cell.particle.IParticleLauncher;

import cv.LevelManager;

abstract public class Unit extends CSprite implements IState , IParticleLauncher {
	
	/**构造精灵的原料*/
	static public CSprite SprStuff;
	
	final static public int TEAM_ACTOR 	= 0;
	final static public int TEAM_ENEMY 	= 1;
	final static public int TEAM_ITEM	= 2;
	
	public String 	Type;//精灵在精灵编辑器中的类型
	public String 	Info;//精灵在场景编辑器里的信息
	public int 		Team ;//精灵敌我判断的标志
	
	
	
	public int HP 		= 100;
	public int Attack 	= 50;
	
	
	
	
	public CParticleSystem Effect;
	public boolean IsIncline = false;
	
	public LevelManager world;
	
	
	public Unit(){
		super(SprStuff);
		super.setState(this);
		haveSprBlock = false;
		haveMapBlock = true;
		
		CParticle[] particles = new CParticle[32];
	   	for(int i=0;i<particles.length;i++){
	   		particles[i] = new CParticle();
	   	}
	   	Effect = new CParticleSystem(particles,this);
	}
	
//	----------------------------------------------------------------------------------------------------------
//	抽象方法
//	----------------------------------------------------------------------------------------------------------
	
	abstract public void update();

	abstract public void attack(Unit unit);
	
	abstract public void damage(Unit unit);

//	----------------------------------------------------------------------------------------------------------
//	公共方法
//	----------------------------------------------------------------------------------------------------------

	public void init(String type,String info){
		Type = type;
		Info = info;
		
	}
	
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		Effect.update();
		Effect.render(g);
		if(IsDebug){
			super.collides.render(g, 0, x, y, 0xff00ff00);
		}
			
	}
	
	public boolean actMoveY(int y){
		if(y==0)return false;
		int dstY = Y + y;
		int dy = (y!=0?(y<0?-1:1):0);
		Y+=y;
		boolean ret = false;
		
		if(this.haveMapBlock){
			int prewAnimate = CurAnimate;
			int prewFrame   = CurFrame;
			setCurrentFrame(0, 0);
			
			boolean adjustMap = false;
			
			collides.getCD(0).Mask = 0xffff;
			adjustMap = touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map);
			collides.getCD(0).Mask = 0xffff - 0x02;
			
			if(adjustMap){
				Y-=y;
				while(Y!=dstY){
					Y+=dy;
					if(touch_Spr_Map(this,CD_TYPE_MAP,this.world.Map)){
						Y-=dy;
						ret = true;
						break;
					}
				}
			}
			
			setCurrentFrame(prewAnimate, prewFrame);
		}
		
		return ret;
	}
	
	public boolean actMoveX(int x){
		if(x==0)return false;
		int dstX = X + x;
		int dx = (x!=0?(x<0?-1:1):0);
		X+=x;
		boolean ret = false;
		
		if(this.haveMapBlock){
			int prewAnimate = CurAnimate;
			int prewFrame   = CurFrame;
			setCurrentFrame(0, 0);
			
			boolean adjustMap = false;
			adjustMap = touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map);

			if(IsIncline && !adjustMap ){
				int y = Math.abs(x) + 1;
				Y += y;
				if(touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map)){
					int dy = -1;
					for(int i=y;i>=0;i--){
						Y+=dy;
						if(!touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map)){
							break;
						}
					}
				}else{
					Y -= y;
				}
			}
			
			if(adjustMap){
				int dy = -1;
				X-=x;
				while(X!=dstX){
					X+=dx;
					//如果X被阻挡
					if(touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map)){
						if(IsIncline){
							Y+=dy;
							//如果Y被阻挡
							if(touch_SprSub_Map(this,CD_TYPE_MAP,0,this.world.Map)){
								X-=dx;
								Y-=dy;
								ret = true;
								break;
							}
						}else{
							X-=dx;
							ret = true;
							break;
						}
					}
				}
			}
			
			setCurrentFrame(prewAnimate, prewFrame);
		}
		
		return ret;
	}
	
	public boolean isLand(){
		int prewAnimate = CurAnimate;
		int prewFrame   = CurFrame;
		setCurrentFrame(0, 0);

		boolean flag = CSprite.touch_SprSub_Map(
				this, 
				CD_TYPE_EXT, 0, 
				world.getMap()
				);

		setCurrentFrame(prewAnimate, prewFrame);
		return flag;
	}
	
	
//	----------------------------------------------------------------------------------------------------------
//	粒子系统
//	----------------------------------------------------------------------------------------------------------
	
	//数字
	final private int EFFECT_NUMBER	= 0;
	private int EffectNumber;
	public void SpawnNumber(int number,int x,int y){
		EffectNumber = number;
		Effect.spawn(1, EFFECT_NUMBER, x, y);
	}
	
	//火焰
	final private int EFFECT_FIRE_U = 1;
	public void SpawnFireU(int x,int y){
		Effect.spawn(1, EFFECT_FIRE_U, x, y);
	}
	
	public void particleEmitted(CParticle particle, int id) {
		particle.Timer = 0;
		particle.X <<= 8 ;
		particle.Y <<= 8 ;
		particle.SpeedX = 0;
		particle.SpeedY = 0;
		particle.AccX = 0;
		particle.AccY = 0;
		
		switch(particle.Category){
		case EFFECT_NUMBER:
			particle.TerminateTime = 16;
			particle.Color 	= 0xffff0000;
			particle.Data  	= EffectNumber;
			particle.AccY	= -128;
			break;
		case EFFECT_FIRE_U:
			particle.TerminateTime = 16;
			particle.Color 	= 0xffff0000;
			particle.AccY	= -64;
			break;
		}
	}
	
	public void particleAffected(CParticle particle, int id) {
		particle.SpeedX += particle.AccX;
		particle.SpeedY += particle.AccY;
		particle.X += particle.SpeedX;
		particle.Y += particle.SpeedY;
	}
	
	public void particleRender(Graphics g, CParticle particle, int id) {
		int x = world.toScreenPosX(particle.X>>8);
		int y = world.toScreenPosY(particle.Y>>8);
		switch(particle.Category){
		case EFFECT_NUMBER:
			g.setColor(particle.Color);
			g.drawString(particle.Data+"", x, y, g.TOP|g.HCENTER);
			break;
		case EFFECT_FIRE_U:
			int size = particle.TerminateTime - particle.Timer;
			g.setColor(particle.Color);
			g.drawArc(x-size/2, y-size/2, size, size, 0, 360);
			break;
		}
	}

	public void particleTerminated(CParticle particle, int id) {

	}
	
	
	
	
}
