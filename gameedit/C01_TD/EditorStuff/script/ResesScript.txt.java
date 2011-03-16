//
// CastleVania Script v0.0.0
// 
// 指定文件输出
// <OUTPUT>     	..\res\ResesScript.java
// <IMAGE OUTPUT>	..\res\
// <IMAGE TYPE>			png
// <IMAGE TILE>			false
// <IMAGE GROUP>		true

import com.cell.*;
import com.cell.game.*;


public class ResesScript {

//--------------------------------------------------------------------------------------------------------------
// resource trunk
//--------------------------------------------------------------------------------------------------------------
#<RESOURCE>

	//--------------------------------------------------------------------------------------------------------------
	#<IMAGES> 
	// Images : <NAME> 
	final static public IImages createClipImages_<NAME>(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/<NAME>.png"),<COUNT>);
		
		#<CLIP> stuff.addTile(<X>,<Y>,<W>,<H>);//<INDEX> 
		#<END CLIP>
		
		return stuff;
	}
	#<END IMAGES>

	//--------------------------------------------------------------------------------------------------------------
	#<MAP>
	// Map : <NAME> //<IMAGES NAME>
	final static public CMap createMap_<NAME>(IImages tiles,boolean isAnimate,boolean isCyc){
		//<X COUNT> x <Y COUNT>
		// tiles
	    CAnimates animates = new CAnimates(<SCENE PART COUNT>,tiles);
	    #<SCENE PART> animates.addPart(0,0,<TILE>,<TRANS>);//<INDEX>
		#<END SCENE PART>
		
	    animates.setFrames(new int[<SCENE FRAME COUNT>][]);
	    #<SCENE FRAME> animates.setComboFrame(new int[]{<DATA>},<INDEX>);//<INDEX>
		#<END SCENE FRAME>
		
		short[][] tileMatrix = new short[][]{
			<TILE MATRIX>
		};
		
		// cds
		CCollides collides = new CCollides(<CD PART COUNT>);
	    #<CD PART>
	    if("<TYPE>"=="rect") collides.addCDRect(<MASK>, <X1>, <Y1>, <W> , <H> );//<TYPE>//<INDEX>
		if("<TYPE>"=="line") collides.addCDLine(<MASK>, <X1>, <Y1>, <X2>, <Y2>);//<TYPE>//<INDEX>
		#<END CD PART>

		short[][] flagMatrix = new short[][]{
			<FLAG MATRIX>
		};
		
	    CMap ret = new CMap(
	            animates, 
	            collides, 
	            <CELL W>, <CELL H>, 
	            tileMatrix, 
	            flagMatrix, 
	            isAnimate,isCyc 
	            );
	
	    return ret;
	}
	#<END MAP>
	
	//--------------------------------------------------------------------------------------------------------------
	#<SPRITE>
	// Sprite : <NAME> //<IMAGES NAME>
	final static public CSprite createSprite_<NAME>(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(<SCENE PART COUNT>,tiles);
	    #<SCENE PART> animates.addPart(<X>,<Y>,<TILE>,<TRANS>);//<INDEX>
		#<END SCENE PART>
		
	    animates.setFrames(new int[<SCENE FRAME COUNT>][]);
	    #<SCENE FRAME> animates.setComboFrame(new int[]{<DATA>},<INDEX>);//<INDEX>
		#<END SCENE FRAME>
		
		// cds
	    CCollides collides = new CCollides(<CD PART COUNT>);
		#<CD PART> collides.addCDRect(<MASK>, <X1>, <Y1>, <W> , <H> );//<TYPE>//<INDEX>
	    #<END CD PART>
	    
	    collides.setFrames(new int[<CD FRAME COUNT>][]);
	    #<CD FRAME> collides.setComboFrame(new int[]{<DATA>},<INDEX>);//<INDEX>
	    #<END CD FRAME>
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			<FRAME NAME>
		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        <FRAME ANIMATE>
	    };
	    int[][] frameCDMap = new int[][]{
	        <FRAME CD MAP>
	    };
	    int[][] frameCDAtk = new int[][]{
	        <FRAME CD ATK>
	    };
	    int[][] frameCDDef = new int[][]{
	        <FRAME CD DEF>
	    };
	    int[][] frameCDExt = new int[][]{
	        <FRAME CD EXT>
	    };
	
	    CSprite ret = new CSprite(
	            animates, 
	            collides, 
	            frameAnimate, 
	            frameCDMap, 
	            frameCDAtk, 
	            frameCDDef, 
	            frameCDExt 
	            );
	
	    return ret;
	
	}
	#<END SPRITE>
	
	//--------------------------------------------------------------------------------------------------------------
	
	#<IMAGES> 
	final public static String images_<NAME> = "<NAME>";
	#<END IMAGES>

	#<MAP>
	final public static String map_<NAME> = "<NAME>";
	#<END MAP>
	
	#<SPRITE>
	final public static String spr_<NAME> = "<NAME>";
	#<END SPRITE>

	//--------------------------------------------------------------------------------------------------------------
	final public static IImages createImages(String key){
	#<IMAGES> 
		if(key=="<NAME>"){
			return createClipImages_<NAME>();
		}
	#<END IMAGES>
		return null;
	}
	
	final public static CMap createMap(String key, IImages tiles, boolean isAnimate, boolean isCyc){
	#<MAP>
		if(key=="<NAME>"){
			return createMap_<NAME>(tiles,isAnimate,isCyc);
		}
	#<END MAP>
		return null;
	}
	
	final public static CSprite createSprite(String key, IImages tiles){
	#<SPRITE>
		if(key=="<NAME>"){
			return createSprite_<NAME>(tiles);
		}
	#<END SPRITE>
		return null;
	}
	
#<END RESOURCE>
//--------------------------------------------------------------------------------------------------------------
// level trunk
//--------------------------------------------------------------------------------------------------------------

#<LEVEL>

	// name const
	#<WORLD>
	final public static String world_<NAME> = "<NAME>"; #<END WORLD>

	// world names
	final static public String[] WorldNames = new String[]{
	#<WORLD> 
	"<NAME>", #<END WORLD>
	};

	// create world
	final static public void buildWorld(String name, CWorld level){
	#<WORLD>
		// World : <NAME>
		if(name=="<NAME>"){
			// World Size
			level.Width  = <WIDTH> ;
			level.Height = <HEIGHT>;
			
			// Map
			#<UNIT MAP> 
			#<END UNIT MAP>
			// Sprite
			// sprite count : <UNIT SPRITE COUNT>
			#<UNIT SPRITE> 
			#<END UNIT SPRITE>
			
			// WayPoint
			// waypoint count : <WAYPOINT COUNT>
			CWayPoint[] WayPoints = new CWayPoint[<WAYPOINT COUNT>];
			#<WAYPOINT> 
			WayPoints[<INDEX>] = new CWayPoint(<X>,<Y>);
			#<END WAYPOINT>
			// waypoint link 
			#<WAYPOINT LINK> 
			WayPoints[<START>].linkTo(WayPoints[<END>]);
			#<END WAYPOINT LINK>
			level.WayPoints = WayPoints;
		}
	#<END WORLD>
	}
	
	
#<END LEVEL>
	
}



