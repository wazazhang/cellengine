package game.unit;

import javax.microedition.lcdui.Graphics;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitEnemy extends Unit {

	public int HP_MAX 	= 100;
	
	public int SlowTime = 0;//减速持续时间
	public int SlowRate = 1;// %速度
	
	public int InjureTime = 0;//%速度
	public int InjureRate = 1;//%速度
	
	
	private int state = -1;
	
	
	public UnitEnemy(CSprite stuff){
		super(stuff);
		super.setState(this);
		
		Visible = true;
		Active  = false;
		
	}
	
	
	public void update() {
		switch(state){
		case STATE_MOVE:
			if(!isEndMove()){
				onMove();
			}else{
				state = -1;
			}
			break;
		case STATE_DEAD:
			if (!isEndDead()) {
				onDead();
			} else {
				state = -1;
			}
			break;
		}
		
	}
	
	
	
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		
		g.setColor(0xff00ff00);
		g.fillRect(x-10, y-32, 20 * HP / HP_MAX, 4);
		
		g.setColor(0xff000000);
		g.drawRect(x-10, y-32, 20, 4);
	}

	//被干到
	public void directDamage(UnitShoot shoot){
		if(shoot.getIsCriticalDamage()){
			HP -= shoot.HP;
			EffectSpawn(EFFECT_CRITICAL, X-AScreen.getStringWidth("critical")/2, Y, "critical", 0xff00ff00);
		}else{
			HP -= shoot.HP;
		}
		SlowRate = Math.max(SlowRate,shoot.getSlowRate());
		SlowTime = Math.max(SlowTime,shoot.getSlowTime());
		InjureRate = Math.max(InjureRate,shoot.getInjureRate());
		InjureTime = Math.max(InjureTime,shoot.getInjureRate());
	}
	
	public void splashDamage(UnitShoot shoot){
		if(shoot.getIsCriticalDamage()&&shoot.getIsCriticalDamage()){
			HP -= shoot.HP/2;
			EffectSpawn(EFFECT_CRITICAL, X-AScreen.getStringWidth("critical")/2, Y, "critical", 0xff00ff00);
		}else{
			HP -= shoot.HP/2;
		}
		SlowRate = Math.max(SlowRate,shoot.getSlowRate()/2);
		SlowTime = Math.max(SlowTime,shoot.getSlowTime()/2);
		InjureRate = Math.max(InjureRate,shoot.getInjureRate()/2);
		InjureTime = Math.max(InjureTime,shoot.getInjureRate()/2);
	}

//	------------------------------------------------------------------------------------
//	patrol
	final public int STATE_MOVE 	= 1;
	public CWayPoint NextWayPoint;
	private CWayPoint PrewWayPoint;
	private int MaxSpeed = 100 ;
	
	public void startMove(CWayPoint next,int Speed){
		SlowRate = 0;
		SlowTime = 0;
		InjureRate = 0;
		InjureTime = 0;
		
		state = STATE_MOVE;
		Active = true;
		Visible = true;
		
		MaxSpeed = Speed;
		NextWayPoint = next ;
		if(NextWayPoint.getNextCount()>0){
			int id = Math.abs(Random.nextInt()%NextWayPoint.getNextCount());
			if(PrewWayPoint != NextWayPoint.getNextPoint(id)){
				PrewWayPoint = NextWayPoint;
				NextWayPoint = NextWayPoint.getNextPoint(id);
			}else{
				PrewWayPoint = NextWayPoint;
				NextWayPoint = NextWayPoint.getNextPoint((id+1)%NextWayPoint.getNextCount());
			}
		}
		
		HPos256 = (X<<8);
		VPos256 = (Y<<8);
	}
	boolean isEndMove(){
		if( X==NextWayPoint.X && Y==NextWayPoint.Y ){
			if(NextWayPoint.getNextCount()>0){
				int id = Math.abs(Random.nextInt()%NextWayPoint.getNextCount());
				if(PrewWayPoint != NextWayPoint.getNextPoint(id)){
					PrewWayPoint = NextWayPoint;
					NextWayPoint = NextWayPoint.getNextPoint(id);
				}else{
					PrewWayPoint = NextWayPoint;
					NextWayPoint = NextWayPoint.getNextPoint((id+1)%NextWayPoint.getNextCount());
				}
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	void onMove(){
		int curSpeed = MaxSpeed;
		
		if(SlowTime-->0) {
			curSpeed -= MaxSpeed*SlowRate/100;
			EffectSpawn(EFFECT_DAMAGE_ICE, X, Y, null, 0);
		}else{
			SlowTime = 0;
			SlowRate = 0;
		}
		if(InjureTime-->0){
			HP -= InjureRate;
			EffectSpawn(EFFECT_DAMAGE_FIRE, X, Y, null, 0);
		}else{
			InjureTime = 0;
			InjureRate = 0;
		}
		
		
		DirectX = (NextWayPoint.X<<8) - HPos256;
		DirectY = (NextWayPoint.Y<<8) - VPos256;
	
		if(DirectX == 0 && DirectY == 0){
			setCurrentFrame(0, getCurrentFrame());
		}else if(DirectY < 0 ){
			setCurrentFrame(0, getCurrentFrame());
		}else if(DirectY > 0){
			setCurrentFrame(2, getCurrentFrame());
		}else if(DirectX < 0){
			setCurrentFrame(3, getCurrentFrame());
		}else if(DirectX > 0){
			setCurrentFrame(1, getCurrentFrame());
		}
		
		int dx = (DirectX==0 ? 0 : (DirectX>0 ? curSpeed : -curSpeed));
		int dy = (DirectY==0 ? 0 : (DirectY>0 ? curSpeed : -curSpeed));
		if(curSpeed>Math.abs(DirectX))dx = DirectX;
		if(curSpeed>Math.abs(DirectY))dy = DirectY;
		
		HPos256+=dx;
		VPos256+=dy;
		
		tryMove((HPos256>>8)-X, (VPos256>>8)-Y);
		
		nextCycFrame();
	}
	
	
//	------------------------------------------------------------------------------------
//	dead

//	---------------------------------------------------------------------------------------------------------
//	完结状态
	final public int STATE_DEAD	= 2;
	
	public void startDead(){
		state = STATE_DEAD;
//		EffectSpawn(EFFECT_ATTACK_ICE,X,Y,null);
		EffectSpawn(EFFECT_MONEY, X, Y, "+" + 100, 0xffffff00);
		println("Dead");
	}
	void onDead(){
	}
	boolean isEndDead(){
		Active = false;
		Visible = false;
		return true;
	}
	
	

}
