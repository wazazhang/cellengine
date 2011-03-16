import game.unit.UnitEnemy;
import game.unit.UnitShoot;
import game.unit.UnitTower;


import javax.microedition.lcdui.Graphics;

import com.cell.*;
import com.cell.game.*;
import com.cell.game.ai.*;
import com.cell.particle.*;

//继承抽象类CScreen并实现其中的方法
public class ScreenTD_Main extends AScreen {

	// game world
	CWorld 		world;
	CMap 		map;
	CCamera 	cam;
	
	UnitEnemy 	enemys[]	= new UnitEnemy[8];
	UnitTower	towers[]	= new UnitTower[4];
	UnitShoot	shoots[]	= new UnitShoot[8];
	CSprite		point;
	
	
	public ScreenTD_Main(){

		
       	IsDebug = false;
    
       	FrameDelay = 25;
       	
       	// res
       	IImages mapTile = new CTiles20();
       	IImages sprTile = new CTiles20();
       	IImages guiTile = new CTiles20();
       	IImages towerTile = new CTiles20();
       	IImages shootTile = new CTiles20();
       	ResesScript.buildClipImages_MapTile(mapTile);
       	ResesScript.buildClipImages_SprTile(sprTile);
       	ResesScript.buildClipImages_GUITile(guiTile);
       	ResesScript.buildClipImages_TowerTile(towerTile);
       	ResesScript.buildClipImages_ShootTile(shootTile);
       	
       	// map type
       	map = ResesScript.createMap_Map00(mapTile, false, false);
       	
       	// spr type
       	CSprite enemy ;
       	switch(Math.abs(Random.nextInt()%4)){
       	case 0: enemy = ResesScript.createSprite_Enemy00(sprTile);break;
       	case 1: enemy = ResesScript.createSprite_Enemy01(sprTile);break;
       	case 2: enemy = ResesScript.createSprite_Enemy02(sprTile);break;
       	case 3: enemy = ResesScript.createSprite_Enemy03(sprTile);break;
       	case 4: enemy = ResesScript.createSprite_Enemy04(sprTile);break;
       	case 5: enemy = ResesScript.createSprite_Enemy05(sprTile);break;
       	case 6: enemy = ResesScript.createSprite_Enemy06(sprTile);break;
       	case 7: enemy = ResesScript.createSprite_Enemy07(sprTile);break;
       	default: enemy = ResesScript.createSprite_Enemy00(sprTile);break;
       	}
       	
        CSprite tower = ResesScript.createSprite_Tower(towerTile);
        CSprite point = ResesScript.createSprite_Point(guiTile);
        CSprite shoot = ResesScript.createSprite_Shoot(shootTile);
       	// camera 
       	cam = new CCamera(0,0,
       			SCREEN_WIDTH,
       			SCREEN_WIDTH,
       			map,true,0);
       	
       	// world
       	world = new world_Level00();
    	((world_Level00)world).Map00 = map;// setmap
    	((world_Level00)world).setCamera(cam);//set camera
       	((world_Level00)world).initPath();// init path
       	((world_Level00)world).initUnit();
       	world.isRPGView = true;
       	
       	
       	for(int i=0;i<enemys.length;i++){
       		enemys[i] = new UnitEnemy(enemy);
       		enemys[i].Active = false;
       		enemys[i].Visible = false;
       		enemys[i].Y = -32 - i*32;
       	}
       	for(int i=0;i<towers.length;i++){
       		towers[i] = new UnitTower(tower);
       		towers[i].Active = false;
       		towers[i].Visible = false;
       	}
       	for(int i=0;i<shoots.length;i++){
       		shoots[i] = new UnitShoot(shoot);
       		shoots[i].Active = false;
       		shoots[i].Visible = false;
       		shoots[i].Priority = 1024;
       	}
       	
       	this.point = point;
    	
       	world.addSprites(shoots);
    	world.addSprites(enemys);
    	world.addSprites(towers);
    	world.addSprite(point);


    	
       	resetTimer();
	}
	
	public void notifyLogic() {
//    	if(isKeyDown(KEY_STAR)) {FrameDelay --;}
//        if(isKeyDown(KEY_SHARP)){FrameDelay ++;}
//    	if(isKeyDown(KEY_0)){ChangeSubScreen("ScreenLogo");}
    	if(isKeyDown(KEY_0)){AScreen.ExitGame = true;}
    	
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
    	processEnemys();
    	processTowers();
    	processShoots();
    	
    	int cdx = point.X - (cam.getX() + cam.getWidth() /2);
    	int cdy = point.Y - (cam.getY() + cam.getHeight()/2);
    	cam.mov(cdx/4,cdy/4);
		world.update();
  
        tickTimer();
        
    }
	
	public void notifyRender(Graphics g) {
        //clearScreenAndClip(g,0xff000000);

        world.render(g);
        
        showFPS(g, 1, 1, 0xffffffff);

       
    }

	
	public void notifyPause() {}


	public void notifyResume() {}

//	-------------------------------------------------------------------------------------------------------------------------------
	
	int holdTime = 10; 
	
	public void processPoint(){
		
		if(isPointerDown()){
			
		}
		
    	if(isKeyDown(KEY_5)){
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
    	
    	
    	
    	if(isKeyDown(KEY_2)){
    		holdTime=10;
    	} 
    	if(isKeyDown(KEY_8)){
    		holdTime=10;
    	}
    	if(isKeyDown(KEY_4)){
    		holdTime=10;
    	}
		if(isKeyDown(KEY_6)){
			holdTime=10;
		}
		
    	if(holdTime<0 || holdTime==10){
	    	if(isKeyHold(KEY_2)){
	    		if(point.Y>0)
	    			point.tryMove(0, -map.getCellH());
	    	} 
	    	if(isKeyHold(KEY_8)){
	    		if(point.Y<map.getHeight()-map.getCellH())
	    			point.tryMove(0,  map.getCellH());
	    	}
	    	if(isKeyHold(KEY_4)){
	    		if(point.X>0)
	    			point.tryMove(-map.getCellW(), 0);
	    	}
			if(isKeyHold(KEY_6)){
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
		if(getTimer()==1)
		for(int i=0;i<enemys.length;i++){
			if(!enemys[i].Active){
				enemys[i].startMove(world.WayPoints[0]);
			}else{
				
			}
       	}
       
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
	
}



