package game.unit;

import javax.microedition.lcdui.Graphics;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

public class UnitShoot extends Unit {
//	----------------------------------------------------------------------------------------------------
//	基本
	static final public int TYPE_NONE			= -1;
	
	/*TODO 子弹列表，对应编辑器Shoots*/
	static final public int TYPE00_ARROW		= 0;//
	static final public int TYPE01_ARROW		= 1;
	static final public int TYPE02_ARROW		= 2;
	static final public int TYPE03_ARROW		= 3;
	
	static final public int TYPE11_ICE			= 4;
	static final public int TYPE12_ICE			= 5;
	static final public int TYPE13_ICE			= 6;
	
	static final public int TYPE21_FIRE			= 7;
	static final public int TYPE22_FIRE			= 8;
	static final public int TYPE23_FIRE			= 9;
	
	static final public int TYPE30_ARROW		= 10;//
	static final public int TYPE31_ARROW		= 11;
	static final public int TYPE32_ARROW		= 12;
	static final public int TYPE33_ARROW		= 13;
	
	static final public int TYPE41_ICE			= 14;
	static final public int TYPE42_ICE			= 15;
	static final public int TYPE43_ICE			= 16;
	
	static final public int TYPE51_FIRE			= 17;
	static final public int TYPE52_FIRE			= 18;
	static final public int TYPE53_FIRE			= 19;
	
	/**类型*/
	private int Type = 0;
	private int state = -1;
	
	//public boolean Penetrable = false;
	/**是否溅射伤害*/
	public boolean 	Splash 			= true;
	/**溅射范围*/
	public int 		SplashRange 	= 100;
	
	public UnitShoot(CSprite stuff){
		super(stuff);
		setState(this);
		Priority = 1024;
		HP = 10+Random.nextInt()%10;
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
//		case STATE_SLUG:
//			if (!isEndSlug()) {
//				onSlug();
//			} else {
//				startTerminate(X, Y);
//			}
//			break;
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
			
	}
	
//	得到致命攻击比率
	public boolean getIsCriticalDamage(){
		switch(Type){
		case TYPE01_ARROW:return Math.abs(Random.nextInt())%100<10;
		case TYPE02_ARROW:return Math.abs(Random.nextInt())%100<20;
		case TYPE03_ARROW:return Math.abs(Random.nextInt())%100<30;
		
		case TYPE31_ARROW:return Math.abs(Random.nextInt())%100<10;
		case TYPE32_ARROW:return Math.abs(Random.nextInt())%100<20;
		case TYPE33_ARROW:return Math.abs(Random.nextInt())%100<30;
		}
		return false;
	}
	
	//得到减速比率
	public int getSlowRate(){
		switch(Type){
		case TYPE11_ICE:return 20;
		case TYPE12_ICE:return 40;
		case TYPE13_ICE:return 60;
		case TYPE41_ICE:return 20;
		case TYPE42_ICE:return 40;
		case TYPE43_ICE:return 60;
		}
		return 0;
	}
	//得到减速时间
	public int getSlowTime(){
		switch(Type){
		case TYPE11_ICE:return 2*AScreen.FrameDelay;
		case TYPE12_ICE:return 4*AScreen.FrameDelay;
		case TYPE13_ICE:return 8*AScreen.FrameDelay;
		case TYPE41_ICE:return 2*AScreen.FrameDelay;
		case TYPE42_ICE:return 4*AScreen.FrameDelay;
		case TYPE43_ICE:return 8*AScreen.FrameDelay;
		}
		return 0;
	}
	
	//得到持续减血比率
	public int getInjureRate(){
		switch(Type){
		
		case TYPE21_FIRE:return 5;
		case TYPE22_FIRE:return 10;
		case TYPE23_FIRE:return 15;

		case TYPE51_FIRE:return 5;
		case TYPE52_FIRE:return 10;
		case TYPE53_FIRE:return 15;
		}
		return 0;
	}
	
	//得到持续减血时间
	public int getInjureTime(){
		switch(Type){
		case TYPE21_FIRE:return 5*AScreen.FrameDelay;
		case TYPE22_FIRE:return 10*AScreen.FrameDelay;
		case TYPE23_FIRE:return 15*AScreen.FrameDelay;

		case TYPE51_FIRE:return 5*AScreen.FrameDelay;
		case TYPE52_FIRE:return 10*AScreen.FrameDelay;
		case TYPE53_FIRE:return 15*AScreen.FrameDelay;
		}
		return 0;
	}
	
	public void startFire(int type,int sx,int sy,UnitEnemy[] targets,int targetID){
		Type = type;
		
		
		switch(Type){
		case TYPE00_ARROW:
		case TYPE01_ARROW:
		case TYPE02_ARROW:
		case TYPE03_ARROW:
		
		case TYPE11_ICE:
		case TYPE12_ICE:
		case TYPE13_ICE:
		
		case TYPE21_FIRE:
		case TYPE22_FIRE:
		case TYPE23_FIRE:
			startMissile(sx,sy,targets,targetID,Type);
			break;
			
		case TYPE30_ARROW:
		case TYPE31_ARROW:
		case TYPE32_ARROW:
		case TYPE33_ARROW:
		
		case TYPE41_ICE:
		case TYPE42_ICE:
		case TYPE43_ICE:
		
		case TYPE51_FIRE:
		case TYPE52_FIRE:
		case TYPE53_FIRE:
			Splash 			= true;
			SplashRange 	= 32;
			startMissile(sx,sy,targets,targetID,Type);
			break;
		}
		
	}
	


//	----------------------------------------------------------------------------------------------------
//	状态
	final public int STATE_NONE			= 	-1;
	void startNone(){
		this.Active = false;
		this.Visible = false;
		state = STATE_NONE;
		Splash = false;
	}
	boolean isEndNone(){
		return !Active;
	}
	void onNone(){
	}
//	----------------------------------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------
//	---------------------------------------------------------------------------------------------------------
//	----------------------------------------------------------------------------------------------------
////	多重攻击
//	final public int STATE_SLUG			= 1;
//	int SlugX;
//	int SlugY;
//	int SlugMaxSpeed = 4;
//	void startDst(int sx,int sy,int dx,int dy,int type){
//		state = STATE_SLUG;
//		Active = true;
//		Visible = true;
//		
//		SlugX = dx;
//		SlugY = dy;
//		
//		SpeedX256 = 0; 
//		SpeedY256 = 0;
//		
//		HPos256 = sx * 256 ; 
//		VPos256 = sy * 256 ; 
//		
//		X = sx;
//		Y = sy;
//		
//		setCurrentFrame(type, 0);
//	}
//	boolean isEndSlug(){
//		if( CCD.cdRectPoint(
//				SlugX-SlugMaxSpeed, 
//				SlugY-SlugMaxSpeed,
//				SlugX+SlugMaxSpeed, 
//				SlugY+SlugMaxSpeed,
//				X, Y))
//		{
//			return true;
//		}
//		return false;
//	}
//	void onSlug(){
//		int dx = SlugX - X;
//		int dy = SlugY - Y;
//		int bx = Math.abs(dx)*256/SlugMaxSpeed;
//		int by = Math.abs(dy)*256/SlugMaxSpeed;
//		int d = Math.max(bx,by);
//		
//		if(d!=0){
//			SpeedX256 =  dx * 256 * 256 / d ;
//			SpeedY256 =  dy * 256 * 256 / d ;
//		}
//		
//		if(Math.abs(dx)>SlugMaxSpeed){
//			HPos256 += SpeedX256 ; 
//			X = HPos256/256;
//		}else{
//			X = SlugX;
//		}
//		if(Math.abs(dy)>SlugMaxSpeed){
//			VPos256 += SpeedY256 ; 
//			Y = VPos256/256;
//		}else{
//			Y = SlugY;
//		}
//		
////		tryMove(dx-X, dy-Y);
//		EffectSpawn(EFFECT_TAIL_SWORD,X,Y,null);
//		nextCycFrame();
//		
//	}
//	---------------------------------------------------------------------------------------------------------
//	单体攻击
	final public int STATE_MISSILE		= 2;
	UnitEnemy[] MissileTargets				= null;
	int MissileTargetID					= -1;
	int MissileMaxSpeed					= 8;
	int DstX;
	int DstY;
	void startMissile(int sx, int sy, UnitEnemy[] targets, int targetID, int type){
		state = STATE_MISSILE;
		Active = true;
		Visible = true;
		
		MissileTargets = targets;
		MissileTargetID = targetID;
		DstX = MissileTargets[MissileTargetID].X;
		DstY = MissileTargets[MissileTargetID].Y;
		
		SpeedX256 = 0; 
		SpeedY256 = 0;
		
		HPos256 = (sx<<8) ; 
		VPos256 = (sy<<8) ; 
		
		X = sx;
		Y = sy;
//		tryMove(sx-X, sy-Y);
		setCurrentFrame(type, 0);
	}
	boolean isEndMissile(){
		if( !MissileTargets[MissileTargetID].Active ){
			if(DstX == X && DstY == Y){
				return true;
			}
		}
		if(CCD.cdRectPoint(
				X-MissileMaxSpeed, 
				Y-MissileMaxSpeed,
				X+MissileMaxSpeed, 
				Y+MissileMaxSpeed,
				DstX, 
				DstY))
		{
			/*TODO : 敌人被直接打中*/
			MissileTargets[MissileTargetID].directDamage(this);
			/*TODO : 敌人被溅射到*/
			if(Splash){
				for(int i=MissileTargets.length-1;i>=0;i--){
					if(MissileTargets[i].Active && i!=MissileTargetID)
					if(CCD.cdRectPoint(
							X-SplashRange, 
							Y-SplashRange,
							X+SplashRange, 
							Y+SplashRange,
							MissileTargets[i].X, 
							MissileTargets[i].Y)){
						MissileTargets[i].splashDamage(this);
					}
				}
			}
			
			EffectSpawn(EFFECT_DAMAGE_SWORD,MissileTargets[MissileTargetID].X,MissileTargets[MissileTargetID].Y,null, 0);
			return true;
		}
		return false;
	}
	void onMissile(){
		if( MissileTargets[MissileTargetID]!=null && MissileTargets[MissileTargetID].Active ){
			DstX = MissileTargets[MissileTargetID].X;
			DstY = MissileTargets[MissileTargetID].Y;
		}
		int dx = DstX - X;
		int dy = DstY - Y;
		int bx = (Math.abs(dx)<<8)/MissileMaxSpeed;
		int by = (Math.abs(dy)<<8)/MissileMaxSpeed;
		int d = Math.max(bx,by);
		
		if(d!=0){
			SpeedX256 = (dx<<16) / d ;
			SpeedY256 = (dy<<16) / d ;
		}
		
		if(Math.abs(dx)>MissileMaxSpeed){
			HPos256 += SpeedX256 ; 
			X = (HPos256>>8);
		}else{
			X = DstX;
		}
		
		if(Math.abs(dy)>MissileMaxSpeed){
			VPos256 += SpeedY256 ; 
			Y = (VPos256>>8);
		}else{
			Y = DstY;
		}
		
//		tryMove(dx-X, dy-Y);
//		EffectSpawn(EFFECT_TAIL_FIRE,X,Y,null);
		nextCycFrame();
	}
//	---------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------
//	完结状态
	final public int STATE_TERMINATE	= 3;
	void startTerminate(int sx,int sy){
		Type = -1;
		state = STATE_TERMINATE;
		Active = true;
		Visible = true;
		
		X = sx;
		Y = sy;
//		tryMove(sx-X, sy-Y);
		EffectSpawn(EFFECT_ATTACK_FIRE,X,Y,null, 0);
		setCurrentFrame(STATE_TERMINATE, 0);
	}
	void onTerminate(){
		
	}
	boolean isEndTerminate(){
		return nextFrame() || !OnScreen;
	}
	
	

	
	
}
