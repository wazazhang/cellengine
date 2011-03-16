/* Encoding : 简体中文(GB2312) */
/* Cell Game Editor by WAZA Zhang */
/* Email : wazazhang@gmail.com */

//
// CastleVania Script v0.0.0
// 
// 指定文件输出
// <OUTPUT>     ..\..\res\ResesLevel00.java
// 
import com.cell.*;
import com.cell.game.*;

import cv.*;

public class ResesLevel00 implements ResesScript{

//--------------------------------------------------------------------------------------------------------------
// resource trunk
//--------------------------------------------------------------------------------------------------------------


	//--------------------------------------------------------------------------------------------------------------
	 
	// Images : MapTile00 
	final static public IImages createClipImages_MapTile00(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/MapTile00.png"),33);
		
		 stuff.addTile(0,0,16,16);//0 
		 stuff.addTile(16,0,16,16);//1 
		 stuff.addTile(32,0,16,16);//2 
		 stuff.addTile(48,0,16,16);//3 
		 stuff.addTile(0,16,16,16);//4 
		 stuff.addTile(16,16,16,16);//5 
		 stuff.addTile(32,16,16,16);//6 
		 stuff.addTile(48,16,16,16);//7 
		 stuff.addTile(0,32,16,16);//8 
		 stuff.addTile(16,32,16,16);//9 
		 stuff.addTile(32,32,16,16);//10 
		 stuff.addTile(48,32,16,16);//11 
		 stuff.addTile(0,48,16,16);//12 
		 stuff.addTile(16,48,16,16);//13 
		 stuff.addTile(32,48,16,16);//14 
		 stuff.addTile(64,0,16,16);//15 
		 stuff.addTile(64,64,16,16);//16 
		 stuff.addTile(64,48,16,16);//17 
		 stuff.addTile(0,64,16,16);//18 
		 stuff.addTile(16,64,16,16);//19 
		 stuff.addTile(32,64,16,16);//20 
		 stuff.addTile(64,16,16,16);//21 
		 stuff.addTile(48,64,16,16);//22 
		 stuff.addTile(64,32,16,16);//23 
		 stuff.addTile(0,80,16,16);//24 
		 stuff.addTile(0,96,16,16);//25 
		 stuff.addTile(16,80,16,16);//26 
		 stuff.addTile(16,96,16,16);//27 
		 stuff.addTile(32,80,16,16);//28 
		 stuff.addTile(32,96,16,16);//29 
		 stuff.addTile(48,80,16,16);//30 
		 stuff.addTile(48,96,16,16);//31 
		 stuff.addTile(48,48,16,16);//32 
		
		
		return stuff;
	}
	
 
	// Images : E00_Zombi 
	final static public IImages createClipImages_E00_Zombi(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/E00_Zombi.png"),11);
		
		 stuff.addTile(0,0,16,2);//0 
		 stuff.addTile(0,2,16,2);//1 
		 stuff.addTile(0,4,18,8);//2 
		 stuff.addTile(0,12,20,10);//3 
		 stuff.addTile(0,22,20,16);//4 
		 stuff.addTile(0,38,19,19);//5 
		 stuff.addTile(0,57,22,26);//6 
		 stuff.addTile(0,83,21,36);//7 
		 stuff.addTile(0,119,20,34);//8 
		 stuff.addTile(0,153,21,33);//9 
		 stuff.addTile(0,186,20,34);//10 
		
		
		return stuff;
	}
	


	//--------------------------------------------------------------------------------------------------------------
	
	// Map : Level_00 //MapTile00
	final static public CMap createMap_Level_00(IImages tiles,boolean isAnimate,boolean isCyc){
		
		//40 x 20
		
		// tiles
	    CAnimates animates = new CAnimates(24,tiles);
	     animates.addPart(0,0,14,0);//0
		 animates.addPart(0,0,20,0);//1
		 animates.addPart(0,0,32,5);//2
		 animates.addPart(0,0,1,0);//3
		 animates.addPart(0,0,0,0);//4
		 animates.addPart(0,0,12,0);//5
		 animates.addPart(0,0,19,0);//6
		 animates.addPart(0,0,32,3);//7
		 animates.addPart(0,0,13,0);//8
		 animates.addPart(0,0,5,0);//9
		 animates.addPart(0,0,18,0);//10
		 animates.addPart(0,0,2,0);//11
		 animates.addPart(0,0,6,0);//12
		 animates.addPart(0,0,7,0);//13
		 animates.addPart(0,0,0,2);//14
		 animates.addPart(0,0,32,2);//15
		 animates.addPart(0,0,26,0);//16
		 animates.addPart(0,0,28,0);//17
		 animates.addPart(0,0,30,0);//18
		 animates.addPart(0,0,24,0);//19
		 animates.addPart(0,0,27,0);//20
		 animates.addPart(0,0,29,0);//21
		 animates.addPart(0,0,31,0);//22
		 animates.addPart(0,0,25,0);//23
		
		
	    animates.setFrames(new int[24][]);
	     animates.setComboFrame(new int[]{0,},0);//0
		 animates.setComboFrame(new int[]{1,},1);//1
		 animates.setComboFrame(new int[]{2,},2);//2
		 animates.setComboFrame(new int[]{3,},3);//3
		 animates.setComboFrame(new int[]{4,},4);//4
		 animates.setComboFrame(new int[]{5,},5);//5
		 animates.setComboFrame(new int[]{6,},6);//6
		 animates.setComboFrame(new int[]{7,},7);//7
		 animates.setComboFrame(new int[]{8,},8);//8
		 animates.setComboFrame(new int[]{9,},9);//9
		 animates.setComboFrame(new int[]{10,},10);//10
		 animates.setComboFrame(new int[]{11,},11);//11
		 animates.setComboFrame(new int[]{12,},12);//12
		 animates.setComboFrame(new int[]{13,},13);//13
		 animates.setComboFrame(new int[]{14,},14);//14
		 animates.setComboFrame(new int[]{15,},15);//15
		 animates.setComboFrame(new int[]{16,17,18,19,},16);//16
		 animates.setComboFrame(new int[]{17,18,19,16,},17);//17
		 animates.setComboFrame(new int[]{18,19,16,17,},18);//18
		 animates.setComboFrame(new int[]{19,16,17,18,},19);//19
		 animates.setComboFrame(new int[]{20,21,22,23,},20);//20
		 animates.setComboFrame(new int[]{21,22,23,20,},21);//21
		 animates.setComboFrame(new int[]{22,23,20,21,},22);//22
		 animates.setComboFrame(new int[]{23,20,21,22,},23);//23
		
		
		short[][] tileMatrix = new short[][]{
			{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
{0,2,3,3,3,3,3,3,3,4,3,4,3,3,4,4,3,3,4,5,0,6,3,4,3,4,4,3,3,3,3,3,3,3,3,3,3,3,7,0,},
{8,3,9,3,3,3,3,3,3,3,3,3,3,3,3,3,4,3,3,5,0,6,3,3,4,3,3,3,3,9,3,3,9,9,3,3,4,4,3,5,},
{8,3,9,3,3,3,9,3,3,3,3,9,3,3,9,9,9,3,3,5,0,6,4,3,3,4,4,9,3,3,3,9,3,3,4,3,3,4,3,5,},
{8,3,9,4,3,-16,3,3,-17,4,3,-18,3,4,-19,3,3,3,4,5,0,6,3,-17,4,9,-18,9,3,3,5,0,0,8,3,3,3,10,0,0,},
{1,1,6,3,3,-20,3,3,-21,4,4,-22,3,3,-23,3,4,3,3,5,0,6,3,-21,9,3,-22,3,9,3,3,10,6,3,3,3,3,3,3,5,},
{8,3,3,3,3,4,3,3,3,4,3,4,3,4,4,3,3,3,4,5,0,6,3,4,3,3,9,3,4,3,3,10,6,4,3,9,4,3,4,5,},
{8,4,9,9,3,3,3,3,3,3,3,3,3,4,3,4,3,3,3,5,0,6,3,3,4,3,4,3,4,3,3,10,6,4,3,3,3,4,3,5,},
{8,3,3,3,3,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,3,3,9,10,0,0,0,0,8,4,3,5,},
{8,3,3,3,3,3,4,3,3,3,3,3,3,4,3,3,3,3,3,5,0,6,3,4,3,3,3,4,3,3,3,10,6,3,3,3,3,3,3,5,},
{8,4,3,3,3,3,3,3,9,3,3,3,3,4,4,3,3,4,3,5,0,6,3,4,4,3,3,3,3,4,3,10,6,3,3,3,9,3,3,5,},
{8,3,3,12,13,3,3,3,3,12,13,4,3,3,3,12,13,3,3,5,0,6,3,3,4,9,4,3,12,13,3,10,6,3,9,4,3,3,3,5,},
{8,3,4,3,3,3,3,3,4,4,3,4,9,9,4,3,4,3,3,5,0,6,4,3,4,4,3,3,4,9,3,10,6,3,4,10,0,0,0,0,},
{8,3,3,4,4,3,9,9,3,3,4,3,3,3,3,3,3,3,3,5,0,6,3,3,3,3,3,4,3,3,4,10,6,3,4,3,3,3,4,5,},
{8,4,4,3,4,3,9,3,3,3,3,3,9,4,3,3,3,9,3,5,0,6,3,3,3,3,9,3,3,3,3,10,6,3,4,4,9,9,3,5,},
{3,3,4,12,13,3,9,3,3,12,13,3,3,9,3,12,13,4,3,5,0,6,3,4,4,10,0,0,0,0,0,0,6,12,13,4,3,4,4,5,},
{4,3,3,3,3,3,3,4,3,3,3,3,4,9,3,4,4,3,3,4,4,4,3,3,9,3,3,3,3,4,3,10,6,4,3,3,3,4,3,4,},
{4,3,3,3,3,3,3,3,4,3,3,9,9,3,3,4,3,4,3,3,3,3,3,3,3,3,4,4,3,4,3,10,6,3,3,3,9,4,3,3,},
{0,0,0,0,0,0,0,8,3,4,4,3,3,3,3,3,3,4,3,3,3,4,4,4,4,4,4,4,4,14,15,0,6,3,4,4,3,3,4,3,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},

		};
		
		// cds
		CCollides collides = new CCollides(8);
	    
	    if("rect"=="rect") collides.addCDRect(0, 0, 0, 16 , 16 );//rect//0
		if("rect"=="line") collides.addCDLine(0, 0, 0, 16, 16);//rect//0
		
	    if("rect"=="rect") collides.addCDRect(1, 0, 0, 16 , 16 );//rect//1
		if("rect"=="line") collides.addCDLine(1, 0, 0, 16, 16);//rect//1
		
	    if("rect"=="rect") collides.addCDRect(2, 0, 0, 16 , 1 );//rect//2
		if("rect"=="line") collides.addCDLine(2, 0, 0, 16, 16);//rect//2
		
	    if("rect"=="rect") collides.addCDRect(4, 0, 15, 16 , 1 );//rect//3
		if("rect"=="line") collides.addCDLine(4, 0, 15, 16, 16);//rect//3
		
	    if("rect"=="rect") collides.addCDRect(8, 0, 0, 1 , 16 );//rect//4
		if("rect"=="line") collides.addCDLine(8, 0, 0, 16, 16);//rect//4
		
	    if("rect"=="rect") collides.addCDRect(16, 15, 0, 1 , 16 );//rect//5
		if("rect"=="line") collides.addCDLine(16, 15, 0, 16, 16);//rect//5
		
	    if("line"=="rect") collides.addCDRect(32, 0, 0, 16 , 16 );//line//6
		if("line"=="line") collides.addCDLine(32, 0, 0, 15, 15);//line//6
		
	    if("line"=="rect") collides.addCDRect(64, 15, 0, 16 , 16 );//line//7
		if("line"=="line") collides.addCDLine(64, 15, 0, 0, 15);//line//7
		

		short[][] flagMatrix = new short[][]{
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},
{1,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,1,1,},
{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,2,2,0,0,0,0,2,2,0,0,0,0,2,2,0,0,1,1,1,0,0,0,0,0,0,2,2,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1,1,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
{0,0,0,2,2,0,0,0,0,2,2,0,0,0,0,2,2,0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1,2,2,0,0,0,0,1,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,},
{1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,0,0,0,0,0,0,0,},
{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},

		};
		
	    CMap ret = new CMap(
	            animates, 
	            collides, 
	            16, 16, 
	            tileMatrix, 
	            flagMatrix, 
	            isAnimate,isCyc 
	            );
	
	    return ret;
	}
	

	// Map : Level_01 //MapTile00
	final static public CMap createMap_Level_01(IImages tiles,boolean isAnimate,boolean isCyc){
		
		//40 x 20
		
		// tiles
	    CAnimates animates = new CAnimates(17,tiles);
	     animates.addPart(0,0,14,2);//0
		 animates.addPart(0,0,13,0);//1
		 animates.addPart(0,0,0,2);//2
		 animates.addPart(0,0,12,0);//3
		 animates.addPart(0,0,14,0);//4
		 animates.addPart(0,0,0,0);//5
		 animates.addPart(0,0,4,0);//6
		 animates.addPart(0,0,1,0);//7
		 animates.addPart(0,0,5,0);//8
		 animates.addPart(0,0,6,0);//9
		 animates.addPart(0,0,7,0);//10
		 animates.addPart(0,0,32,0);//11
		 animates.addPart(0,0,32,2);//12
		 animates.addPart(0,0,32,4);//13
		 animates.addPart(0,0,5,2);//14
		 animates.addPart(0,0,4,2);//15
		 animates.addPart(0,0,18,0);//16
		
		
	    animates.setFrames(new int[17][]);
	     animates.setComboFrame(new int[]{0,},0);//0
		 animates.setComboFrame(new int[]{1,},1);//1
		 animates.setComboFrame(new int[]{2,},2);//2
		 animates.setComboFrame(new int[]{3,},3);//3
		 animates.setComboFrame(new int[]{4,},4);//4
		 animates.setComboFrame(new int[]{5,},5);//5
		 animates.setComboFrame(new int[]{6,},6);//6
		 animates.setComboFrame(new int[]{7,},7);//7
		 animates.setComboFrame(new int[]{8,},8);//8
		 animates.setComboFrame(new int[]{9,},9);//9
		 animates.setComboFrame(new int[]{10,},10);//10
		 animates.setComboFrame(new int[]{11,},11);//11
		 animates.setComboFrame(new int[]{12,},12);//12
		 animates.setComboFrame(new int[]{13,},13);//13
		 animates.setComboFrame(new int[]{14,},14);//14
		 animates.setComboFrame(new int[]{15,},15);//15
		 animates.setComboFrame(new int[]{16,},16);//16
		
		
		short[][] tileMatrix = new short[][]{
			{0,0,1,2,2,2,3,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,},
{1,5,5,6,5,7,7,5,5,6,5,8,5,5,5,7,7,6,8,3,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,},
{1,5,5,9,10,6,5,5,5,8,8,7,5,8,6,6,5,5,5,3,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,},
{1,5,6,5,5,8,5,6,7,5,5,7,6,5,8,5,5,5,5,3,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,8,4,},
{1,6,5,5,7,7,8,8,5,5,6,5,6,6,6,5,6,6,6,3,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,8,8,4,},
{4,11,7,5,5,7,5,6,5,6,8,5,5,5,6,5,5,6,5,3,4,4,4,4,11,5,5,5,5,5,5,5,5,5,5,5,12,0,8,4,},
{4,4,4,4,4,4,4,1,5,5,6,6,3,4,4,4,4,4,4,4,4,4,4,4,4,11,5,5,5,5,5,5,5,5,5,12,0,8,8,4,},
{1,5,6,8,7,8,7,6,8,7,8,8,8,8,8,6,5,8,13,4,4,4,4,4,4,4,11,5,5,5,5,5,5,5,7,0,0,8,8,4,},
{1,6,8,7,6,8,6,5,6,8,7,8,8,8,5,5,8,5,8,3,4,4,4,4,4,4,4,11,5,5,5,5,5,5,5,5,4,5,4,4,},
{1,5,6,5,7,5,7,6,8,7,5,6,5,8,5,6,6,8,7,3,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,5,7,4,},
{1,8,7,7,8,6,6,8,3,4,4,4,0,0,0,0,0,0,0,4,1,5,5,5,5,5,8,8,5,5,5,5,12,11,5,5,4,5,5,4,},
{1,8,5,7,8,8,5,8,5,5,5,6,8,8,5,5,5,5,5,3,1,5,5,5,5,5,5,5,5,5,5,12,4,4,11,5,4,4,5,4,},
{1,8,6,5,8,8,5,6,5,5,5,8,6,7,5,5,5,6,5,3,1,5,5,5,5,5,5,5,5,5,12,4,4,4,4,11,4,7,5,4,},
{1,8,12,11,6,5,5,8,5,8,5,7,8,8,8,5,8,5,12,4,1,5,5,5,5,5,5,5,5,12,4,4,4,4,4,4,4,7,5,4,},
{4,4,4,4,4,4,4,1,5,8,8,8,3,4,4,4,4,4,4,4,1,5,5,5,5,14,12,0,0,0,4,0,0,4,4,0,0,8,4,4,},
{1,6,8,6,7,7,7,5,5,6,5,5,7,5,6,5,5,5,5,3,1,5,5,5,5,12,0,0,0,0,0,0,0,0,0,0,0,8,7,7,},
{15,5,7,8,6,5,5,5,8,6,5,5,5,5,5,5,8,7,5,15,5,5,5,5,12,0,0,0,0,0,0,0,0,0,0,0,0,8,8,7,},
{8,5,5,5,6,5,5,5,12,4,4,11,5,5,6,5,6,5,8,8,5,5,5,12,0,0,0,0,0,0,0,0,0,0,0,0,0,8,8,7,},
{8,6,5,5,5,8,12,4,4,4,4,4,4,1,8,8,5,5,5,6,5,5,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,},
{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,},

		};
		
		// cds
		CCollides collides = new CCollides(8);
	    
	    if("rect"=="rect") collides.addCDRect(0, 0, 0, 16 , 16 );//rect//0
		if("rect"=="line") collides.addCDLine(0, 0, 0, 16, 16);//rect//0
		
	    if("rect"=="rect") collides.addCDRect(1, 0, 0, 16 , 16 );//rect//1
		if("rect"=="line") collides.addCDLine(1, 0, 0, 16, 16);//rect//1
		
	    if("rect"=="rect") collides.addCDRect(2, 0, 0, 16 , 1 );//rect//2
		if("rect"=="line") collides.addCDLine(2, 0, 0, 16, 16);//rect//2
		
	    if("rect"=="rect") collides.addCDRect(4, 0, 15, 16 , 1 );//rect//3
		if("rect"=="line") collides.addCDLine(4, 0, 15, 16, 16);//rect//3
		
	    if("rect"=="rect") collides.addCDRect(8, 0, 0, 1 , 16 );//rect//4
		if("rect"=="line") collides.addCDLine(8, 0, 0, 16, 16);//rect//4
		
	    if("rect"=="rect") collides.addCDRect(16, 15, 0, 1 , 16 );//rect//5
		if("rect"=="line") collides.addCDLine(16, 15, 0, 16, 16);//rect//5
		
	    if("line"=="rect") collides.addCDRect(32, 0, 0, 16 , 16 );//line//6
		if("line"=="line") collides.addCDLine(32, 0, 0, 15, 15);//line//6
		
	    if("line"=="rect") collides.addCDRect(64, 15, 0, 16 , 16 );//line//7
		if("line"=="line") collides.addCDLine(64, 15, 0, 0, 15);//line//7
		

		short[][] flagMatrix = new short[][]{
			{1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,},
{1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,7,1,0,1,},
{1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,7,1,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,1,1,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,1,0,1,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,},
{1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,7,6,0,0,1,0,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,7,1,1,6,0,1,1,0,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,7,1,1,1,1,6,1,0,0,1,},
{1,0,7,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,0,0,0,0,0,0,0,0,7,1,1,1,1,1,1,1,0,0,1,},
{1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,0,1,1,},
{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
{0,0,0,0,0,0,0,0,7,1,1,6,0,0,0,0,0,0,0,0,0,0,0,7,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
{0,0,0,0,0,0,7,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},

		};
		
	    CMap ret = new CMap(
	            animates, 
	            collides, 
	            16, 16, 
	            tileMatrix, 
	            flagMatrix, 
	            isAnimate,isCyc 
	            );
	
	    return ret;
	}
	

	
	//--------------------------------------------------------------------------------------------------------------
	
	// Sprite : e00_zombi //E00_Zombi
	
	final static public CSprite createSprite_e00_zombi(IImages tiles){

	    // tiles
	    CAnimates animates = new CAnimates(8,tiles);
	     animates.addPart(-8,0,0,0);//0
		 animates.addPart(-9,-7,2,0);//1
		 animates.addPart(-10,-9,3,0);//2
		 animates.addPart(-10,-15,4,0);//3
		 animates.addPart(-10,-18,5,0);//4
		 animates.addPart(-11,-25,6,0);//5
		 animates.addPart(-10,-35,7,0);//6
		 animates.addPart(-10,-33,8,0);//7
		
		
	    animates.setFrames(new int[8][]);
	     animates.setComboFrame(new int[]{0,},0);//0
		 animates.setComboFrame(new int[]{1,},1);//1
		 animates.setComboFrame(new int[]{2,},2);//2
		 animates.setComboFrame(new int[]{3,},3);//3
		 animates.setComboFrame(new int[]{4,},4);//4
		 animates.setComboFrame(new int[]{5,},5);//5
		 animates.setComboFrame(new int[]{6,},6);//6
		 animates.setComboFrame(new int[]{7,},7);//7
		
		
		// cds
	    CCollides collides = new CCollides(4);
		 collides.addCDRect(65535, -5, -8, 10 , 8 );//rect//0
	     collides.addCDRect(2, -5, -1, 10 , 1 );//rect//1
	     collides.addCDRect(65535, -5, 0, 10 , 1 );//rect//2
	     collides.addCDRect(65535, -5, -34, 13 , 34 );//rect//3
	    
	    
	    collides.setFrames(new int[4][]);
	     collides.setComboFrame(new int[]{0,1,},0);//0
	     collides.setComboFrame(new int[]{},1);//1
	     collides.setComboFrame(new int[]{2,},2);//2
	     collides.setComboFrame(new int[]{3,},3);//3
	    
	    
	    
		// sprite frame
		
		/*
		String[] frameName = new String[]{
			"0000",
"0001",
"0003",
"0004",

		};
		
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
		
	    int[][] frameAnimate = new int[][]{
	        {0,1,2,3,4,5,6,},
{6,6,6,6,7,7,7,7,},
{6,5,4,3,2,1,0,},
{6,6,6,6,6,6,6,6,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {1,1,1,1,1,1,1,},
{3,3,3,3,3,3,3,3,},
{1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {1,1,1,1,1,1,1,},
{3,3,3,3,3,3,3,3,},
{1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {2,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

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
	

	

	//--------------------------------------------------------------------------------------------------------------
	
	 
	final public static String images_MapTile00 = "MapTile00";
	
 
	final public static String images_E00_Zombi = "E00_Zombi";
	


	
	final public static String map_Level_00 = "Level_00";
	

	final public static String map_Level_01 = "Level_01";
	

	
	
	final public static String spr_e00_zombi = "e00_zombi";
	



	//--------------------------------------------------------------------------------------------------------------
	public IImages createImages(String key){
	 
		if(key=="MapTile00"){
			return createClipImages_MapTile00();
		}
	
 
		if(key=="E00_Zombi"){
			return createClipImages_E00_Zombi();
		}
	

		return null;
	}
	
	public CMap createMap(String key, IImages tiles, boolean isAnimate, boolean isCyc){
	
		if(key=="Level_00"){
			return createMap_Level_00(tiles,isAnimate,isCyc);
		}
	

		if(key=="Level_01"){
			return createMap_Level_01(tiles,isAnimate,isCyc);
		}
	

		return null;
	}
	
	public CSprite createSprite(String key, IImages tiles){
	
		if(key=="e00_zombi"){
			return createSprite_e00_zombi(tiles);
		}
	

		return null;
	}
	

//--------------------------------------------------------------------------------------------------------------
// level trunk
//--------------------------------------------------------------------------------------------------------------



	// name const
	
	final public static String world_Level_00 = "Level_00"; 

	final public static String world_Level_01 = "Level_01"; 

	
	// room scopes
	final static public CCD[] WorldRooms = new CCD[]{
	 
		CCD.createCDRect(0xffffffff, 
			CUtil.stringKeyValue( "x320y0" , "x"), 
			CUtil.stringKeyValue( "x320y0" , "y"),
			640, 
			320),
	
 
		CCD.createCDRect(0xffffffff, 
			CUtil.stringKeyValue( "x960y0" , "x"), 
			CUtil.stringKeyValue( "x960y0" , "y"),
			640, 
			320),
	

	};

	// world names
	final static public String[] WorldNames = new String[]{
	 
	"Level_00", 
 
	"Level_01", 

	};


	
	// create world
	public void initWorld(String name, LevelManager level){
		
		level.WorldName = name;
	
		// World : Level_00
		if(level.WorldName=="Level_00"){
		
			// World Size
			level.Width  = 640 ;
			level.Height = 320;
			
			// Map
			 
			level.MapTile = "MapTile00";    // map tile name
			level.MapType = "Level_00"; // map type 
			level.MapInfo = "x320y0";     // map info form editor
			level.X       = CUtil.stringKeyValue("x320y0","x"); // level x pos
			level.Y       = CUtil.stringKeyValue("x320y0","y"); // level y pos
			CObject.println("x320y0");
			CObject.println("Level X = " + level.X );
			CObject.println("Level Y = " + level.Y );
			CObject.println("Level W = " + level.Width ); 
			CObject.println("Level H = " + level.Height);
			
			
			
			// WayPoint
			// waypoint count : 0
			CWayPoint[] WayPoints = new CWayPoint[0];
			
			// waypoint link 
			
			level.WayPoints = WayPoints;
			
			// Sprite
			// sprite count : 7
			level.SprsTile = new String[7];
			level.SprsType = new String[7];
			level.SprsInfo = new String[7];
			level.SprsX    = new int[7];
			level.SprsY    = new int[7];
			 
			level.SprsTile[0] = "E00_Zombi";    // sprite tile name
			level.SprsType[0] = "e00_zombi"; // sprite type
			level.SprsInfo[0] = "e00_zombi";     // sprite info form editor
			level.SprsX[0]    = 368;          // sprite xpos form editor
			level.SprsY[0]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[1] = "E00_Zombi";    // sprite tile name
			level.SprsType[1] = "e00_zombi"; // sprite type
			level.SprsInfo[1] = "e00_zombi";     // sprite info form editor
			level.SprsX[1]    = 320;          // sprite xpos form editor
			level.SprsY[1]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[2] = "E00_Zombi";    // sprite tile name
			level.SprsType[2] = "e00_zombi"; // sprite type
			level.SprsInfo[2] = "e00_zombi";     // sprite info form editor
			level.SprsX[2]    = 272;          // sprite xpos form editor
			level.SprsY[2]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[3] = "E00_Zombi";    // sprite tile name
			level.SprsType[3] = "e00_zombi"; // sprite type
			level.SprsInfo[3] = "e00_zombi";     // sprite info form editor
			level.SprsX[3]    = 176;          // sprite xpos form editor
			level.SprsY[3]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[4] = "E00_Zombi";    // sprite tile name
			level.SprsType[4] = "e00_zombi"; // sprite type
			level.SprsInfo[4] = "e00_zombi";     // sprite info form editor
			level.SprsX[4]    = 464;          // sprite xpos form editor
			level.SprsY[4]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[5] = "E00_Zombi";    // sprite tile name
			level.SprsType[5] = "e00_zombi"; // sprite type
			level.SprsInfo[5] = "e00_zombi";     // sprite info form editor
			level.SprsX[5]    = 416;          // sprite xpos form editor
			level.SprsY[5]    = 304;          // sprite ypos form editor
			 
			level.SprsTile[6] = "E00_Zombi";    // sprite tile name
			level.SprsType[6] = "e00_zombi"; // sprite type
			level.SprsInfo[6] = "e00_zombi";     // sprite info form editor
			level.SprsX[6]    = 224;          // sprite xpos form editor
			level.SprsY[6]    = 304;          // sprite ypos form editor
			
			
		}
	

		// World : Level_01
		if(level.WorldName=="Level_01"){
		
			// World Size
			level.Width  = 640 ;
			level.Height = 320;
			
			// Map
			 
			level.MapTile = "MapTile00";    // map tile name
			level.MapType = "Level_01"; // map type 
			level.MapInfo = "x960y0";     // map info form editor
			level.X       = CUtil.stringKeyValue("x960y0","x"); // level x pos
			level.Y       = CUtil.stringKeyValue("x960y0","y"); // level y pos
			CObject.println("x960y0");
			CObject.println("Level X = " + level.X );
			CObject.println("Level Y = " + level.Y );
			CObject.println("Level W = " + level.Width ); 
			CObject.println("Level H = " + level.Height);
			
			
			
			// WayPoint
			// waypoint count : 0
			CWayPoint[] WayPoints = new CWayPoint[0];
			
			// waypoint link 
			
			level.WayPoints = WayPoints;
			
			// Sprite
			// sprite count : 5
			level.SprsTile = new String[5];
			level.SprsType = new String[5];
			level.SprsInfo = new String[5];
			level.SprsX    = new int[5];
			level.SprsY    = new int[5];
			 
			level.SprsTile[0] = "E00_Zombi";    // sprite tile name
			level.SprsType[0] = "e00_zombi"; // sprite type
			level.SprsInfo[0] = "S001_e00_zombi";     // sprite info form editor
			level.SprsX[0]    = 292;          // sprite xpos form editor
			level.SprsY[0]    = 303;          // sprite ypos form editor
			 
			level.SprsTile[1] = "E00_Zombi";    // sprite tile name
			level.SprsType[1] = "e00_zombi"; // sprite type
			level.SprsInfo[1] = "S003_e00_zombi";     // sprite info form editor
			level.SprsX[1]    = 267;          // sprite xpos form editor
			level.SprsY[1]    = 303;          // sprite ypos form editor
			 
			level.SprsTile[2] = "E00_Zombi";    // sprite tile name
			level.SprsType[2] = "e00_zombi"; // sprite type
			level.SprsInfo[2] = "S004_e00_zombi";     // sprite info form editor
			level.SprsX[2]    = 242;          // sprite xpos form editor
			level.SprsY[2]    = 303;          // sprite ypos form editor
			 
			level.SprsTile[3] = "E00_Zombi";    // sprite tile name
			level.SprsType[3] = "e00_zombi"; // sprite type
			level.SprsInfo[3] = "S005_e00_zombi";     // sprite info form editor
			level.SprsX[3]    = 316;          // sprite xpos form editor
			level.SprsY[3]    = 303;          // sprite ypos form editor
			 
			level.SprsTile[4] = "E00_Zombi";    // sprite tile name
			level.SprsType[4] = "e00_zombi"; // sprite type
			level.SprsInfo[4] = "S006_e00_zombi";     // sprite info form editor
			level.SprsX[4]    = 344;          // sprite xpos form editor
			level.SprsY[4]    = 303;          // sprite ypos form editor
			
			
		}
	

	}
	
	

	
}



