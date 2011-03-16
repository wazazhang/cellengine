

import java.util.Hashtable;

import javax.microedition.lcdui.Graphics;
import javax.microedition.m3g.World;

import com.cell.AScreen;
import com.cell.CImages20;
import com.cell.CImagesJPhone;
import com.cell.CMath;
import com.cell.CTilesJPhone;
import com.cell.IImages;
import com.cell.game.CCD;
import com.cell.game.CCamera;
import com.cell.game.CMap;
import com.cell.game.CSprite;
import com.cell.game.CWorld;
import com.cell.game.CWorldMini;

import cv.LevelManager;
import cv.ResesScript;
import cv.unit.Unit;
import cv.unit.UnitActor;


public class ScreenLevel extends AScreen {

//	 game world
	static String		worldName;
	static LevelManager world;
	static Hashtable 	aiTable;
	
	public ScreenLevel(){

       	IsDebug = false;
       	FrameDelay = 40;

//       	// actor
//       	if(LevelManager.Actor==null){
//       		IImages tiles = ResesScript.createClipImages_Actor00();
//    		Unit.SprStuff = ResesScript.createSprite_Actor00(tiles);
//    		LevelManager.Actor = new UnitActor();
//    		Unit.SprStuff = null;
//    		LevelManager.Actor.Type = "";
//    		LevelManager.Actor.Info = "";
//    		LevelManager.Actor.X = 29;
//    		LevelManager.Actor.Y = 261;
//    		println("createActor X="+LevelManager.Actor.X+" Y="+LevelManager.Actor.Y);
//       	}
		
       	// world
       	
       	if(aiTable == null){
       		aiTable = new Hashtable();
           	//aiTable.put(ResesScript.spr_Actor00, "cv.unit.UnitActor");
           	//aiTable.put(ResesScript.spr_e00_zombi, "cv.unit.UnitZombi");
       	}
       	if(world   == null){
       		world = new LevelManager(new ResesLevel00());
       		//ResesScript.createWorld("WorldName",world);
       		world.UnitTable = aiTable;
       		world.initLevel();
       	}
       	
       	world.initRoom();
       	
//    	worldMini = new CWorldMini(
//       			world,
//       			world.Camera.getWidth()/2,
//       			world.Camera.getHeight()/4,
//       			2,2,
//       			8+8*16,
//       			20+20*40,
//       			false,
//       			true,
//       			true);
    	
    	
       	resetTimer();
	}
	
	public void notifyLogic() {
    	if(isKeyDown(KEY_STAR)) {FrameDelay -= 10;}
        if(isKeyDown(KEY_SHARP)){FrameDelay += 10;}
    	if(isKeyDown(KEY_A)){ChangeSubScreen("ScreenLogo");}
    	if(isKeyDown(KEY_B)){AScreen.ExitGame = true;}

    	if(isKeyDown(KEY_0)){IsDebug = !IsDebug;}
    	
    	if(world!=null){
    		world.update();
    		if(world.IsChangeRoom){
    			ChangeSubScreen("ScreenLevel");
    		}
    	}
		
		
        tickTimer();
        
    }
	
	public void notifyRender(Graphics g) {
        //clearScreenAndClip(g,0xff000000);

		if(world!=null){
			world.render(g);
		}
//        worldMini.render(g, 1, -1 + SCREEN_HEIGHT - worldMini.getHeight());
        
        showFPS(g, 1, 1, 0xffffffff);

       
    }

	
	public void notifyPause() {}


	public void notifyResume() {}

//	-------------------------------------------------------------------------------------------------------------------------------

}
