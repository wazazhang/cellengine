import java.util.Vector;

import game.unit.LevelManager;
import game.unit.Unit;
import game.unit.UnitEnemy;
import game.unit.UnitShoot;
import game.unit.UnitTower;


import javax.microedition.lcdui.Graphics;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

//#define __WEATHER 

//继承抽象类CScreen并实现其中的方法
public class ScreenTD_Main extends AScreen {

	// game world
	LevelManager 	world;
	CMap 			map;
	CCamera 		cam;
	
	UnitEnemy 	enemys[]	= new UnitEnemy[32];
	UnitTower	towers[]	= new UnitTower[32];
	UnitShoot	shoots[]	= new UnitShoot[128];
	CSprite		point;
	
//	Vector Enemys ;
//	Vector Towers ;
//	Vector Shoots ;
	
//#ifdef __WEATHER	
	int particleCount = 128;
	int spawnCount = 2;
	CParticleSystem weather;
//#endif
	
	public ScreenTD_Main(){
       	IsDebug = false;
       	FrameDelay = 25;
       	
       	// res
       	IImages mapTile = ResesScript.createClipImages_MapTile();
       	IImages sprTile = ResesScript.createClipImages_SprTile();
       	IImages guiTile = ResesScript.createClipImages_GUITile();
       	IImages towerTile = ResesScript.createClipImages_TowerTile();
       	IImages shootTile = ResesScript.createClipImages_EffectTile();
       	
       	// map type
       	map = ResesScript.createMap_Map00(mapTile, false, false);
       	
       	// spr type
       	CSprite enemy = ResesScript.createSprite_Enemy00(sprTile);
        CSprite tower = ResesScript.createSprite_Tower(towerTile);
        CSprite point = ResesScript.createSprite_Point(guiTile);
        CSprite shoot = ResesScript.createSprite_Weaopns(shootTile);
        
       	// camera 
       	cam = new CCamera(0,0,
       			SCREEN_WIDTH,
       			SCREEN_HEIGHT,
       			map,true,0);
       	
       	// world
       	world = new LevelManager();
    	world.isRPGView = true;
       	world.setMap(map);
       	world.setCamera(cam);
       	ResesScript.buildWorld(ResesScript.world_Level00, world);
       	
       	
       	for(int i=0;i<enemys.length;i++){
       		enemys[i] = new UnitEnemy(enemy);
       		enemys[i].world = world;
       	}
       	for(int i=0;i<towers.length;i++){
       		towers[i] = new UnitTower(tower);
       		towers[i].world = world;
       	}
       	for(int i=0;i<shoots.length;i++){
       		shoots[i] = new UnitShoot(shoot);
       		shoots[i].world = world;
       	}
       	this.point = point;
       	
       	world.Effects = ResesScript.createSprite_Effects(shootTile);
       	world.addSprites(shoots);
    	world.addSprites(enemys);
    	world.addSprites(towers);
    	world.addSprite(point);

//#ifdef __WEATHER	
    	//随机天气类型
    	IParticleLauncher launcher;
//		if(Math.abs(Random.nextInt())%2==0){
			launcher = new WeatherRain(world);
//		}else{
//			launcher = new WeatherSnow(world);
//		}
		//粒子
		CParticle[] particles = new CParticle[particleCount];
		for(int i=0;i<particles.length;i++){
       		particles[i] = new CParticle();
       	}
       	weather = new CParticleSystem(
       			particles,
       			launcher
       			);
//#endif
       	
       	resetTimer();
	}
	
	public void notifyDestroy(){
		
	}
	
	public void notifyLogic() {
    	if(isKeyDown(KEY_STAR)) {FrameDelay --;}
        if(isKeyDown(KEY_SHARP)){FrameDelay ++;}
    	if(isKeyDown(KEY_0)){ChangeSubScreen("ScreenLogo");}
    	
//    	if(isKeyDown(KEY_0)){AScreen.ExitGame = true;}
    	
//    	if(isKeyDown(KEY_0)){
//    		println("");
//    		for(int i=0;i<world.Sprs.size();i++){
//    			println(i + " : " + world.Sprs.elementAt(i).getClass().toString() 
//    					+ " : Y=" + world.getSprite(i).Y
//    					+ " : PRI=" + world.getSprite(i).Priority
//    					);
//    		}
//    	}
    	
    	processPoint();
    	processTowers();
    	processShoots();
    	processEnemys();
    	
    	
    	
    	int cdx = point.X - (cam.getX() + cam.getWidth() /2);
    	int cdy = point.Y - (cam.getY() + cam.getHeight()/2);
    	cam.mov(cdx/4,cdy/4);
		world.update();
		
//#ifdef __WEATHER  
//		weather.spawn(spawnCount, 0, cam.getX(), cam.getY());
		weather.update();
//#endif	
		
        tickTimer();
        
    }
	
	public void notifyRender(Graphics g) {
        //clearScreenAndClip(g,0xff000000);

        world.render(g);
//#ifdef __WEATHER       
        weather.render(g);
//#endif
        showFPS(g, 1, 1, 0xffffffff);

       
    }

	
	public void notifyPause() {}


	public void notifyResume() {}

//	-------------------------------------------------------------------------------------------------------------------------------
	
	int holdTime = 10; 
	
	public void processPoint(){
		
		if(isPointerDown()){
			
		}
		
    	if(isKeyDown(KEY_5|KEY_C)){
    		for(int i=0;i<towers.length;i++){
    			if(!towers[i].Active){
    				int bx = point.X/map.getCellW();
    				int by = point.Y/map.getCellH();
    				if(map.getFlag(bx, by)==0){
    					map.putFlag(bx, by, 1);
    					int dx = (bx * map.getCellW()) + map.getCellW()/2;
    					int dy = (by * map.getCellH()) + map.getCellH()/2;
        				towers[i].startBuild(dx, dy-1);
    				}
    				break;
    			}
    		}
    	}
    	
    	
		
		
    	holdTime--;
    	
    	
    	
    	if(isKeyDown(KEY_2|KEY_UP)){
    		holdTime=10;
    	} 
    	if(isKeyDown(KEY_8|KEY_DOWN)){
    		holdTime=10;
    	}
    	if(isKeyDown(KEY_4|KEY_LEFT)){
    		holdTime=10;
    	}
		if(isKeyDown(KEY_6|KEY_RIGHT)){
			holdTime=10;
		}
		
    	if(holdTime<0 || holdTime==10){
	    	if(isKeyHold(KEY_2|KEY_UP)){
	    		if(point.Y>0)
	    			point.tryMove(0, -map.getCellH());
	    	} 
	    	if(isKeyHold(KEY_8|KEY_DOWN)){
	    		if(point.Y<map.getHeight()-map.getCellH())
	    			point.tryMove(0,  map.getCellH());
	    	}
	    	if(isKeyHold(KEY_4|KEY_LEFT)){
	    		if(point.X>0)
	    			point.tryMove(-map.getCellW(), 0);
	    	}
			if(isKeyHold(KEY_6|KEY_RIGHT)){
				if(point.X<map.getWidth()-map.getCellW())
					point.tryMove( map.getCellW(), 0);
			}
		}
    	
    	if(point.X%map.getCellW()!=map.getCellW()/2){
    		int dx = point.X/map.getCellW() + map.getCellW()/2;
    		point.tryMove(dx - point.X ,0);
    	}
		if(point.Y%map.getCellH()!=map.getCellH()/2){
    		int dy = point.Y/map.getCellH() + map.getCellH()/2;
    		point.tryMove(0, dy - point.Y);
    	}
		
    	point.nextCycFrame();
	}
	
	public void processEnemys(){
		boolean tag = false;
		for(int i=0;i<enemys.length;i++){
			if(enemys[i].Active){
				if(enemys[i].HP<=0){
					enemys[i].startDead();
				}
				tag = true;
			}
       	}
		if(!tag)resetEnemy();
	}
	
	public void processTowers(){
		for(int i=0;i<towers.length;i++){
			if(!towers[i].Active){
				
			}else{
				towers[i].startAttack(enemys,shoots);
			}
			
       	}
       	
	}

	public void processShoots(){
//		for(int i=0;i<shoots.length;i++){
//       	}
	}
	
	
	
	void resetEnemy(){
//		初始所有敌人
		for(int i=enemys.length-1;i>=0;i--){
       		enemys[i].Y 		= -32 - i*32;
       		enemys[i].X 		= 0;
       		enemys[i].HP_MAX 	= enemys[i].HP_MAX*2;
       		enemys[i].HP 		= enemys[i].HP_MAX;
       		enemys[i].startMove(world.WayPoints[0],512);
       	}
//		打乱敌人顺序
		for(int i=enemys.length-1;i>=0;i--){
       		int pos 		= Math.abs(Random.nextInt()%enemys.length);
       		UnitEnemy temp 	= enemys[i];
       		enemys[i] 		= enemys[pos];
       		enemys[pos] 	= temp;
       	}
		
	}
	
}


class WeatherRain extends CObject implements IParticleLauncher {
	final static public int Div = 256 ;
	//
	final public int TYPE_LIPPER	= 0;
	final public int TYPE_RAINDROP	= 1;
	
	int WindSpeed = Div * 2;
	int Gravity = Div * 16 ;
	
	int Width = 176 ;
	int Height = 208 ;

	CWorld world;
	
	public WeatherRain(CWorld world){
		this.world 	= world;
		this.Width 	= world.getCamera().getWidth() ;
		this.Height = world.getCamera().getHeight() ;
	}
	
	public void particleTerminated(CParticle particle, int id) {
		
	}
	
	public void particleEmitted(CParticle particle, int id) {
		particle.Color = 0xffffffff;
		particle.Timer = 0;
		particle.Category = Math.abs(Random.nextInt()%2);
		
		particle.X*=Div;
		particle.Y*=Div;
		
		switch(particle.Category){
		case TYPE_RAINDROP:
			particle.TerminateTime = 32;
			particle.X += Math.abs(Random.nextInt()) % Width * Div;
			particle.Y += Random.nextInt() % Height * Div;
			
			particle.SpeedX = WindSpeed;
			particle.SpeedY = Gravity;
			particle.AccX = WindSpeed;
			particle.AccY = Gravity;
			break;
		case TYPE_LIPPER:
			particle.TerminateTime = 8;
			particle.X += Math.abs(Random.nextInt()) % Width * Div;
			particle.Y += Math.abs(Random.nextInt()) % Height * Div;
			break;
		}

	}

	public void particleAffected(CParticle particle, int id) {
		switch(particle.Category){
		case TYPE_RAINDROP:
			particle.Y += particle.SpeedY;
			particle.X += particle.SpeedX;
			break;
		case TYPE_LIPPER:
			break;
		}

	}
	
	public void particleRender(Graphics g, CParticle particle, int id) {
		
		int X = world.toScreenPosX(particle.X/Div) ;
		int Y = world.toScreenPosY(particle.Y/Div) ;

		// color
		g.setColor(particle.Color);
		
		switch(particle.Category){
		case TYPE_RAINDROP:
			int X2 = world.toScreenPosX((particle.X+particle.AccX)/Div);
			int Y2 = world.toScreenPosY((particle.Y+particle.AccY)/Div);
			g.drawLine(X, Y, X2, Y2);
			break;
		case TYPE_LIPPER:
			int size = particle.Timer ;
			g.drawArc(X - size/2, Y - size/2/2, size, size/2, 0, 360);
			break;
		}

		
	}
}

class WeatherSnow extends CObject implements IParticleLauncher  {

	final static public int Div = 256 ;
	//
	int SizeScope = Div * 4 ;
	int WaveScope = Div * 1 ;
	
	int WindSpeed = Div * 1;
	int Gravity = Div * 2 ;
	
	int Width = 176 ;
	int Height = 208 ;

	CWorld world;
	
	public WeatherSnow(CWorld world){
		this.world 	= world;
		this.Width 	= world.getCamera().getWidth() ;
		this.Height = world.getCamera().getHeight() ;
	}
	
	public void particleTerminated(CParticle particle, int id) {
		
	}
	
	public void particleEmitted(CParticle particle, int id) {
		particle.TerminateTime = 32;
		particle.Color = 0xffffffff;
		particle.Timer = 0;
		
		particle.X*=Div;
		particle.Y*=Div;
		
		particle.X += Math.abs(Random.nextInt() % Width ) * Div ;
		particle.Y += Math.abs(Random.nextInt() % Height) * Div ;
		
		particle.SpeedX = WindSpeed + Math.abs(Random.nextInt())%WaveScope;
		particle.SpeedY = Gravity + Math.abs(Random.nextInt())%Gravity;

	}

	public void particleAffected(CParticle particle, int id) {
		particle.Y += particle.SpeedY;
		particle.X += particle.SpeedX;

	}
	
	public void particleRender(Graphics g, CParticle particle, int id) {
		int size = 4 ;
		int X = world.toScreenPosX(particle.X / Div) ;
		int Y = world.toScreenPosY(particle.Y / Div) ;

		// color
		g.setColor(particle.Color);
		g.fillArc(X - size/2, Y - size/2, size, size, 0, 360);

		
		
	}

	
	
	
	
}

