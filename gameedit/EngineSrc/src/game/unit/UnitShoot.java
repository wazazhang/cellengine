package game.unit;

import javax.microedition.lcdui.Graphics;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitShoot extends CSprite implements IState,IParticleLauncher{
//	----------------------------------------------------------------------------------------------------
//	基本
	public boolean Penetrable = false;

	public UnitShoot(CSprite stuff){
		super(stuff);
		setState(this);
	
		CParticle[] particles = new CParticle[128];
       	for(int i=0;i<particles.length;i++){
       		particles[i] = new CParticle();
       	}
       	Effect = new CParticleSystem(particles,this);
	}
	
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		if(OnScreen)Effect.render(g);
	}
	
	public void update(){
		// main state machine
		switch (state) {
		case STATE_MISSILE:
			if (!isEndMissile()) {
				onMissile();
			} else {
				startTerminate(X, Y);
			}
			break;
		case STATE_TERMINATE:
			if (!isEndTerminate()) {
				onTerminate();
			} else {
				startNone();
			}
			break;

		default:
			startNone();
		}	
			
		Effect.update();
	}

//	----------------------------------------------------------------------------------------------------
//	粒子系统
	CParticleSystem Effect;
	
	final static public int Div = 256 ;
	final static public int PARTICLE_SMOKE 		= 0;
	final static public int PARTICLE_EXP		= 1;
	final static public int PARTICLE_CONTRACT	= 3;

	
	public void particleEmitted(CParticle particle, int id) {
		
		particle.Timer = 10;
		particle.X *= 256 ;
		particle.Y *= 256 ;
		particle.SpeedX = 0;
		particle.SpeedY = 0;
		particle.AccX = 0;
		particle.AccY = 0;
		
		int angle = Random.nextInt();

		switch(particle.Category){
		case PARTICLE_EXP:
//			particle.TerminateTime = 10;
			particle.Color = 0xffff0000;
			particle.SpeedX = CMath.sinTimes256(id*10 + angle)*4;
			particle.SpeedY = CMath.cosTimes256(id*10 + angle)*4;
			particle.AccX = -CMath.sinTimes256(id*10 + angle)/8;
			particle.AccY = -CMath.cosTimes256(id*10 + angle)/8;
			break;
		case PARTICLE_SMOKE:
//			particle.TerminateTime = 10;
			particle.Color = 0xffffffff;
			break;
		case PARTICLE_CONTRACT:
//			particle.TerminateTime = 64;
			particle.Color = Random.nextInt();
			particle.X += CMath.sinTimes256(id*10 + angle)*64;
			particle.Y += CMath.cosTimes256(id*10 + angle)*64;
			particle.SpeedX = -CMath.sinTimes256(id*10 + angle);
			particle.SpeedY = -CMath.cosTimes256(id*10 + angle);
			break;
		}
	}
	public void particleAffected(CParticle particle, int id) {
		particle.SpeedX += particle.AccX;
		particle.SpeedY += particle.AccY;
		particle.Y += particle.SpeedY;
		particle.X += particle.SpeedX;
		
		switch(particle.Category){
		case PARTICLE_EXP:
			
			break;
		case PARTICLE_SMOKE:
		
			break;
		case PARTICLE_CONTRACT:
			
			break;
		}
	}
	public void particleRender(Graphics g, CParticle particle, int id) {
		int size = 8;
		switch(particle.Category){
		case PARTICLE_EXP:
			size = particle.Timer / 2;
			g.setColor(particle.Color);
			g.drawArc(
					world.toScreenPosX(particle.X/Div) - size/2, 
					world.toScreenPosY(particle.Y/Div) - size/2,
					size, 
					size, 
					0, 360);
			break;
		case PARTICLE_SMOKE:
			size = particle.Timer / 2;
			g.setColor(particle.Color);
			g.drawArc(
					world.toScreenPosX(particle.X/Div) - size/2, 
					world.toScreenPosY(particle.Y/Div) - size/2,
					size, 
					size, 
					0, 360);
			break;
		case PARTICLE_CONTRACT:
			if(particle.Timer<10/2){
				size = (particle.Timer) / 4;
			}else{
				size = (10-particle.Timer) / 4;
			}
			g.setColor(particle.Color);
			g.fillArc(
					world.toScreenPosX(particle.X/Div) - size/2, 
					world.toScreenPosY(particle.Y/Div) - size/2,
					size, 
					size, 
					0, 360);
			break;
		}
		

	}
	public void particleTerminated(CParticle particle, int id) {
	}

//	----------------------------------------------------------------------------------------------------
//	状态
	int state = -1;
	final public int STATE_NONE			= 	-1;
	public void startNone(){
		this.Active = false;
		this.Visible = false;
		state = STATE_NONE;
		Penetrable = false;
	}
	public boolean isEndNone(){
		return !Active;
	}
	public void onNone(){
	}
//	----------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------
//	跟踪导弹
	final public int STATE_MISSILE		= 0;
	public CSprite MissileHandle		= null;
	public int MissileMaxSpeed			= 8;
	public void startMissile(int sx,int sy,CSprite handle){
		state = STATE_MISSILE;
		Active = true;
		Visible = true;
		
		MissileHandle = handle;
		
		SpeedX256 = 0; 
		SpeedY256 = 0;
		
		HPos256 = sx * 256 ; 
		VPos256 = sy * 256 ; 
		
		X = sx;
		Y = sy;
//		tryMove(sx-X, sy-Y);
		
		setCurrentFrame(STATE_MISSILE, 0);
	}
	public boolean isEndMissile(){
		return !MissileHandle.Active ||
		CSprite.touch_SprSub_SprSub(
				this, 
				0, 0, 
				MissileHandle,
				0, 0);
		;
	}
	public void onMissile(){

		if(getTimer()%2==0){
			Effect.spawn(1,PARTICLE_SMOKE,X, Y);
		}
		
		int bx = Math.abs(MissileHandle.X - X)*256/MissileMaxSpeed;
		int by = Math.abs(MissileHandle.Y - Y)*256/MissileMaxSpeed;
		int d = Math.max(bx,by);
		
		if(d!=0){
			SpeedX256 = (MissileHandle.X - X) * 256 * 256 / d ;
			SpeedY256 = (MissileHandle.Y - Y) * 256 * 256 / d ;
		}
		
		HPos256 += SpeedX256 ; 
		VPos256 += SpeedY256 ; 
		
		int dx = HPos256/256;
		int dy = VPos256/256;
		
		X = dx;
		Y = dy;
		
//		tryMove(dx-X, dy-Y);
		
		nextCycFrame();
	}
//	---------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------
//	完结状态
	final public int STATE_TERMINATE	= 1;
	public void startTerminate(int sx,int sy){
		state = STATE_TERMINATE;
		Active = true;
		Visible = true;
		
		X = sx;
		Y = sy;
//		tryMove(sx-X, sy-Y);
		
		Effect.spawn(8,PARTICLE_EXP,X,Y);
		
		setCurrentFrame(STATE_TERMINATE, 0);
	}
	public void onTerminate(){
		
	}
	public boolean isEndTerminate(){
		return nextFrame() || !OnScreen;
	}
	
	

	
	
}
