/* Encoding : ¼òÌåÖÐÎÄ(GB2312) */
/* Cell Game Editor by WAZA Zhang */
/* Email : wazazhang@gmail.com */
//
// CastleVania Script v0.0.0
// 
// Ö¸¶¨ÎÄ¼þÊä³ö
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


	//--------------------------------------------------------------------------------------------------------------
	 
	// Images : SprTile 
	final static public IImages createClipImages_SprTile(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/SprTile.png"),96);
		
		 stuff.addTile(0,0,24,32);//0 
		 stuff.addTile(24,0,24,32);//1 
		 stuff.addTile(48,0,24,32);//2 
		 stuff.addTile(72,0,24,32);//3 
		 stuff.addTile(96,0,24,32);//4 
		 stuff.addTile(120,0,24,32);//5 
		 stuff.addTile(144,0,24,32);//6 
		 stuff.addTile(168,0,24,32);//7 
		 stuff.addTile(192,0,24,32);//8 
		 stuff.addTile(216,0,24,32);//9 
		 stuff.addTile(240,0,24,32);//10 
		 stuff.addTile(264,0,24,32);//11 
		 stuff.addTile(0,32,24,32);//12 
		 stuff.addTile(24,32,24,32);//13 
		 stuff.addTile(48,32,24,32);//14 
		 stuff.addTile(72,32,24,32);//15 
		 stuff.addTile(96,32,24,32);//16 
		 stuff.addTile(120,32,24,32);//17 
		 stuff.addTile(144,32,24,32);//18 
		 stuff.addTile(168,32,24,32);//19 
		 stuff.addTile(192,32,24,32);//20 
		 stuff.addTile(216,32,24,32);//21 
		 stuff.addTile(240,32,24,32);//22 
		 stuff.addTile(264,32,24,32);//23 
		 stuff.addTile(0,64,24,32);//24 
		 stuff.addTile(24,64,24,32);//25 
		 stuff.addTile(48,64,24,32);//26 
		 stuff.addTile(72,64,24,32);//27 
		 stuff.addTile(96,64,24,32);//28 
		 stuff.addTile(120,64,24,32);//29 
		 stuff.addTile(144,64,24,32);//30 
		 stuff.addTile(168,64,24,32);//31 
		 stuff.addTile(192,64,24,32);//32 
		 stuff.addTile(216,64,24,32);//33 
		 stuff.addTile(240,64,24,32);//34 
		 stuff.addTile(264,64,24,32);//35 
		 stuff.addTile(0,96,24,32);//36 
		 stuff.addTile(24,96,24,32);//37 
		 stuff.addTile(48,96,24,32);//38 
		 stuff.addTile(72,96,24,32);//39 
		 stuff.addTile(96,96,24,32);//40 
		 stuff.addTile(120,96,24,32);//41 
		 stuff.addTile(144,96,24,32);//42 
		 stuff.addTile(168,96,24,32);//43 
		 stuff.addTile(192,96,24,32);//44 
		 stuff.addTile(216,96,24,32);//45 
		 stuff.addTile(240,96,24,32);//46 
		 stuff.addTile(264,96,24,32);//47 
		 stuff.addTile(0,128,24,32);//48 
		 stuff.addTile(24,128,24,32);//49 
		 stuff.addTile(48,128,24,32);//50 
		 stuff.addTile(72,128,24,32);//51 
		 stuff.addTile(96,128,24,32);//52 
		 stuff.addTile(120,128,24,32);//53 
		 stuff.addTile(144,128,24,32);//54 
		 stuff.addTile(168,128,24,32);//55 
		 stuff.addTile(192,128,24,32);//56 
		 stuff.addTile(216,128,24,32);//57 
		 stuff.addTile(240,128,24,32);//58 
		 stuff.addTile(264,128,24,32);//59 
		 stuff.addTile(0,160,24,32);//60 
		 stuff.addTile(24,160,24,32);//61 
		 stuff.addTile(48,160,24,32);//62 
		 stuff.addTile(72,160,24,32);//63 
		 stuff.addTile(96,160,24,32);//64 
		 stuff.addTile(120,160,24,32);//65 
		 stuff.addTile(144,160,24,32);//66 
		 stuff.addTile(168,160,24,32);//67 
		 stuff.addTile(192,160,24,32);//68 
		 stuff.addTile(216,160,24,32);//69 
		 stuff.addTile(240,160,24,32);//70 
		 stuff.addTile(264,160,24,32);//71 
		 stuff.addTile(0,192,24,32);//72 
		 stuff.addTile(24,192,24,32);//73 
		 stuff.addTile(48,192,24,32);//74 
		 stuff.addTile(72,192,24,32);//75 
		 stuff.addTile(96,192,24,32);//76 
		 stuff.addTile(120,192,24,32);//77 
		 stuff.addTile(144,192,24,32);//78 
		 stuff.addTile(168,192,24,32);//79 
		 stuff.addTile(192,192,24,32);//80 
		 stuff.addTile(216,192,24,32);//81 
		 stuff.addTile(240,192,24,32);//82 
		 stuff.addTile(264,192,24,32);//83 
		 stuff.addTile(0,224,24,32);//84 
		 stuff.addTile(24,224,24,32);//85 
		 stuff.addTile(48,224,24,32);//86 
		 stuff.addTile(72,224,24,32);//87 
		 stuff.addTile(96,224,24,32);//88 
		 stuff.addTile(120,224,24,32);//89 
		 stuff.addTile(144,224,24,32);//90 
		 stuff.addTile(168,224,24,32);//91 
		 stuff.addTile(192,224,24,32);//92 
		 stuff.addTile(216,224,24,32);//93 
		 stuff.addTile(240,224,24,32);//94 
		 stuff.addTile(264,224,24,32);//95 
		
		
		return stuff;
	}
	
 
	// Images : MapTile 
	final static public IImages createClipImages_MapTile(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/MapTile.png"),48);
		
		 stuff.addTile(0,0,16,16);//0 
		 stuff.addTile(16,0,16,16);//1 
		 stuff.addTile(32,0,16,16);//2 
		 stuff.addTile(48,0,16,16);//3 
		 stuff.addTile(64,0,16,16);//4 
		 stuff.addTile(80,0,16,16);//5 
		 stuff.addTile(0,16,16,16);//6 
		 stuff.addTile(16,16,16,16);//7 
		 stuff.addTile(32,16,16,16);//8 
		 stuff.addTile(48,16,16,16);//9 
		 stuff.addTile(64,16,16,16);//10 
		 stuff.addTile(80,16,16,16);//11 
		 stuff.addTile(0,32,16,16);//12 
		 stuff.addTile(16,32,16,16);//13 
		 stuff.addTile(32,32,16,16);//14 
		 stuff.addTile(48,32,16,16);//15 
		 stuff.addTile(64,32,16,16);//16 
		 stuff.addTile(80,32,16,16);//17 
		 stuff.addTile(0,48,16,16);//18 
		 stuff.addTile(16,48,16,16);//19 
		 stuff.addTile(32,48,16,16);//20 
		 stuff.addTile(48,48,16,16);//21 
		 stuff.addTile(64,48,16,16);//22 
		 stuff.addTile(80,48,16,16);//23 
		 stuff.addTile(0,64,16,16);//24 
		 stuff.addTile(16,64,16,16);//25 
		 stuff.addTile(32,64,16,16);//26 
		 stuff.addTile(48,64,16,16);//27 
		 stuff.addTile(64,64,16,16);//28 
		 stuff.addTile(80,64,16,16);//29 
		 stuff.addTile(0,80,16,16);//30 
		 stuff.addTile(16,80,16,16);//31 
		 stuff.addTile(32,80,16,16);//32 
		 stuff.addTile(48,80,16,16);//33 
		 stuff.addTile(64,80,16,16);//34 
		 stuff.addTile(80,80,16,16);//35 
		 stuff.addTile(0,96,16,16);//36 
		 stuff.addTile(16,96,16,16);//37 
		 stuff.addTile(32,96,16,16);//38 
		 stuff.addTile(48,96,16,16);//39 
		 stuff.addTile(64,96,16,16);//40 
		 stuff.addTile(80,96,16,16);//41 
		 stuff.addTile(0,112,16,16);//42 
		 stuff.addTile(16,112,16,16);//43 
		 stuff.addTile(32,112,16,16);//44 
		 stuff.addTile(48,112,16,16);//45 
		 stuff.addTile(64,112,16,16);//46 
		 stuff.addTile(80,112,16,16);//47 
		
		
		return stuff;
	}
	
 
	// Images : GUITile 
	final static public IImages createClipImages_GUITile(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/GUITile.png"),2);
		
		 stuff.addTile(0,0,18,18);//0 
		 stuff.addTile(0,18,18,18);//1 
		
		
		return stuff;
	}
	
 
	// Images : TowerTile 
	final static public IImages createClipImages_TowerTile(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/TowerTile.png"),1);
		
		 stuff.addTile(0,0,16,32);//0 
		
		
		return stuff;
	}
	
 
	// Images : EffectTile 
	final static public IImages createClipImages_EffectTile(){
		IImages stuff = new CImages20();
		stuff.buildImages(CIO.loadImage("/EffectTile.png"),97);
		
		 stuff.addTile(31,2,5,5);//0 
		 stuff.addTile(40,13,9,9);//1 
		 stuff.addTile(36,0,2,2);//2 
		 stuff.addTile(36,2,3,3);//3 
		 stuff.addTile(39,6,7,7);//4 
		 stuff.addTile(31,0,1,1);//5 
		 stuff.addTile(34,0,2,2);//6 
		 stuff.addTile(32,0,2,2);//7 
		 stuff.addTile(39,0,6,6);//8 
		 stuff.addTile(31,7,8,6);//9 
		 stuff.addTile(31,13,9,7);//10 
		 stuff.addTile(31,22,14,27);//11 
		 stuff.addTile(27,55,11,5);//12 
		 stuff.addTile(27,60,10,7);//13 
		 stuff.addTile(27,73,9,8);//14 
		 stuff.addTile(27,67,10,6);//15 
		 stuff.addTile(27,49,9,6);//16 
		 stuff.addTile(0,38,15,16);//17 
		 stuff.addTile(15,45,9,9);//18 
		 stuff.addTile(16,34,11,11);//19 
		 stuff.addTile(16,19,15,15);//20 
		 stuff.addTile(12,0,19,19);//21 
		 stuff.addTile(0,0,10,10);//22 
		 stuff.addTile(0,10,12,12);//23 
		 stuff.addTile(0,22,16,16);//24 
		 stuff.addTile(49,0,20,20);//25 
		 stuff.addTile(0,54,9,9);//26 
		 stuff.addTile(0,63,9,9);//27 
		 stuff.addTile(0,72,9,9);//28 
		 stuff.addTile(0,81,9,9);//29 
		 stuff.addTile(0,90,9,9);//30 
		 stuff.addTile(9,54,9,9);//31 
		 stuff.addTile(9,63,9,9);//32 
		 stuff.addTile(9,72,9,9);//33 
		 stuff.addTile(9,81,9,9);//34 
		 stuff.addTile(9,90,9,9);//35 
		 stuff.addTile(18,54,9,9);//36 
		 stuff.addTile(18,63,9,9);//37 
		 stuff.addTile(18,72,9,9);//38 
		 stuff.addTile(18,81,9,9);//39 
		 stuff.addTile(18,90,9,9);//40 
		 stuff.addTile(39,49,6,6);//41 
		 stuff.addTile(38,55,8,8);//42 
		 stuff.addTile(37,63,10,10);//43 
		 stuff.addTile(36,73,12,12);//44 
		 stuff.addTile(47,56,14,14);//45 
		 stuff.addTile(46,40,16,16);//46 
		 stuff.addTile(45,22,18,18);//47 
		 stuff.addTile(69,0,18,16);//48 
		 stuff.addTile(69,16,17,17);//49 
		 stuff.addTile(63,33,19,20);//50 
		 stuff.addTile(62,53,17,17);//51 
		 stuff.addTile(12,108,8,7);//52 
		 stuff.addTile(0,108,12,8);//53 
		 stuff.addTile(8,100,7,8);//54 
		 stuff.addTile(0,100,8,8);//55 
		 stuff.addTile(15,99,8,9);//56 
		 stuff.addTile(48,70,10,9);//57 
		 stuff.addTile(48,79,11,3);//58 
		 stuff.addTile(48,82,11,6);//59 
		 stuff.addTile(59,70,11,11);//60 
		 stuff.addTile(70,70,15,15);//61 
		 stuff.addTile(27,81,3,3);//62 
		 stuff.addTile(27,84,5,5);//63 
		 stuff.addTile(27,89,8,8);//64 
		 stuff.addTile(27,97,8,8);//65 
		 stuff.addTile(23,105,12,12);//66 
		 stuff.addTile(35,88,16,24);//67 
		 stuff.addTile(0,116,13,13);//68 
		 stuff.addTile(13,117,15,16);//69 
		 stuff.addTile(28,117,15,15);//70 
		 stuff.addTile(0,135,7,14);//71 
		 stuff.addTile(7,133,11,16);//72 
		 stuff.addTile(18,133,11,16);//73 
		 stuff.addTile(43,112,8,16);//74 
		 stuff.addTile(67,117,11,14);//75 
		 stuff.addTile(51,120,15,10);//76 
		 stuff.addTile(51,88,16,16);//77 
		 stuff.addTile(67,85,15,16);//78 
		 stuff.addTile(51,104,16,16);//79 
		 stuff.addTile(67,101,16,16);//80 
		 stuff.addTile(43,128,8,8);//81 
		 stuff.addTile(29,132,10,10);//82 
		 stuff.addTile(39,136,12,5);//83 
		 stuff.addTile(29,142,12,7);//84 
		 stuff.addTile(66,131,7,7);//85 
		 stuff.addTile(52,130,7,7);//86 
		 stuff.addTile(59,130,7,7);//87 
		 stuff.addTile(87,0,26,176);//88 
		 stuff.addTile(41,141,7,7);//89 
		 stuff.addTile(0,176,11,5);//90 
		 stuff.addTile(0,181,11,3);//91 
		 stuff.addTile(0,0,0,0);//92 
		 stuff.addTile(0,0,0,0);//93 
		 stuff.addTile(0,149,11,11);//94 
		 stuff.addTile(0,160,11,9);//95 
		 stuff.addTile(0,169,11,7);//96 
		
		
		return stuff;
	}
	


	//--------------------------------------------------------------------------------------------------------------
	
	// Map : Map00 //MapTile
	final static public CMap createMap_Map00(IImages tiles,boolean isAnimate,boolean isCyc){
		//32 x 32
		// tiles
	    CAnimates animates = new CAnimates(21,tiles);
	     animates.addPart(0,0,2,0);//0
		 animates.addPart(0,0,24,0);//1
		 animates.addPart(0,0,23,0);//2
		 animates.addPart(0,0,30,0);//3
		 animates.addPart(0,0,29,0);//4
		 animates.addPart(0,0,26,0);//5
		 animates.addPart(0,0,35,0);//6
		 animates.addPart(0,0,41,0);//7
		 animates.addPart(0,0,36,0);//8
		 animates.addPart(0,0,18,0);//9
		 animates.addPart(0,0,42,0);//10
		 animates.addPart(0,0,9,0);//11
		 animates.addPart(0,0,10,0);//12
		 animates.addPart(0,0,14,0);//13
		 animates.addPart(0,0,15,0);//14
		 animates.addPart(0,0,16,0);//15
		 animates.addPart(0,0,43,0);//16
		 animates.addPart(0,0,44,0);//17
		 animates.addPart(0,0,45,0);//18
		 animates.addPart(0,0,46,0);//19
		 animates.addPart(0,0,47,0);//20
		
		
	    animates.setFrames(new int[21][]);
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
		 animates.setComboFrame(new int[]{17,},17);//17
		 animates.setComboFrame(new int[]{18,},18);//18
		 animates.setComboFrame(new int[]{19,},19);//19
		 animates.setComboFrame(new int[]{20,},20);//20
		
		
		short[][] tileMatrix = new short[][]{
			{0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,3,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,1,4,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0,0,},
{0,3,6,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,},
{0,1,6,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,},
{0,3,7,0,0,5,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0,0,6,0,0,},
{0,8,2,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,2,0,0,},
{0,1,4,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,4,0,0,},
{0,3,4,0,0,5,0,0,5,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,0,0,5,0,0,6,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,5,0,0,5,0,0,7,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,5,0,0,5,0,0,2,0,0,},
{0,3,7,0,0,5,0,0,5,0,0,5,0,0,5,5,5,5,5,5,5,0,0,5,0,0,5,0,0,4,0,0,},
{0,8,2,0,0,5,0,0,5,0,0,5,0,0,5,0,0,0,0,0,5,0,0,5,0,0,5,0,0,6,0,0,},
{0,8,4,0,0,5,0,0,5,0,0,5,0,0,9,0,0,0,0,0,5,0,0,5,0,0,5,0,0,7,0,0,},
{0,8,4,0,0,5,0,0,5,0,0,5,0,10,9,10,10,10,0,0,5,0,0,5,0,0,5,0,0,2,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,5,0,10,9,9,10,10,0,0,5,0,0,5,0,0,5,0,0,4,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,5,0,0,9,9,10,10,0,0,5,0,0,5,0,0,5,0,0,6,0,0,},
{0,8,7,0,0,5,0,0,5,0,0,5,0,0,9,9,10,10,10,0,5,0,0,5,0,0,5,0,0,7,0,0,},
{0,8,2,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,5,0,0,5,0,0,5,0,0,2,0,0,},
{0,8,4,0,0,5,0,0,5,0,0,5,0,0,0,0,0,0,0,0,5,0,0,5,0,0,5,0,0,4,0,0,},
{0,8,4,0,0,5,0,0,5,0,0,5,5,5,5,5,5,5,5,5,5,0,0,5,0,0,5,0,0,6,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,11,0,0,0,0,0,0,0,0,0,0,0,5,0,0,5,0,0,7,0,0,},
{0,8,6,0,0,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,5,0,0,2,0,0,},
{0,8,7,0,0,5,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0,0,5,0,0,4,0,0,},
{0,8,2,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,6,0,0,},
{0,8,4,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,2,0,0,},
{0,8,2,0,0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0,0,4,4,0,},
{0,8,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,},
{0,8,12,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,14,15,13,0,7,0,0,},
{0,10,16,17,18,19,17,18,19,17,18,19,17,18,19,17,18,19,17,18,19,17,18,19,17,18,19,17,18,20,0,0,},
{0,0,0,17,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},

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
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,},
{0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,},
{0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,},
{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,},
{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,},
{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},

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
	
	// Sprite : Enemy00 //SprTile
	final static public CSprite createSprite_Enemy00(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(12,tiles);
	     animates.addPart(-12,-28,0,0);//0
		 animates.addPart(-12,-28,1,0);//1
		 animates.addPart(-12,-28,2,0);//2
		 animates.addPart(-12,-28,12,0);//3
		 animates.addPart(-12,-28,13,0);//4
		 animates.addPart(-12,-28,14,0);//5
		 animates.addPart(-12,-28,24,0);//6
		 animates.addPart(-12,-28,25,0);//7
		 animates.addPart(-12,-28,26,0);//8
		 animates.addPart(-12,-28,36,0);//9
		 animates.addPart(-12,-28,37,0);//10
		 animates.addPart(-12,-28,38,0);//11
		
		
	    animates.setFrames(new int[12][]);
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
		
		
		// cds
	    CCollides collides = new CCollides(1);
		 collides.addCDRect(65535, -5, -15, 10 , 15 );//rect//0
	    
	    
	    collides.setFrames(new int[2][]);
	     collides.setComboFrame(new int[]{0,},0);//0
	     collides.setComboFrame(new int[]{},1);//1
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			"0000",
"0001",
"0002",
"0003",

		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        {0,0,1,1,2,2,1,1,},
{3,3,4,4,5,5,4,4,},
{6,6,7,7,8,8,7,7,},
{9,9,10,10,11,11,10,10,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
{1,1,1,1,1,1,1,1,},
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
	

	// Sprite : Point //GUITile
	final static public CSprite createSprite_Point(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(3,tiles);
	     animates.addPart(-8,-8,1,0);//0
		 animates.addPart(-9,-9,1,0);//1
		 animates.addPart(-9,-9,0,0);//2
		
		
	    animates.setFrames(new int[3][]);
	     animates.setComboFrame(new int[]{0,},0);//0
		 animates.setComboFrame(new int[]{1,},1);//1
		 animates.setComboFrame(new int[]{2,},2);//2
		
		
		// cds
	    CCollides collides = new CCollides(0);
		
	    
	    collides.setFrames(new int[1][]);
	     collides.setComboFrame(new int[]{},0);//0
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			"0000",

		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        {0,1,1,1,2,2,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,0,0,0,0,0,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {0,0,0,0,0,0,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {0,0,0,0,0,0,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {0,0,0,0,0,0,},

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
	

	// Sprite : Tower //TowerTile
	final static public CSprite createSprite_Tower(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(1,tiles);
	     animates.addPart(-8,-24,0,0);//0
		
		
	    animates.setFrames(new int[1][]);
	     animates.setComboFrame(new int[]{0,},0);//0
		
		
		// cds
	    CCollides collides = new CCollides(1);
		 collides.addCDRect(65535, 0, -20, 1 , 1 );//rect//0
	    
	    
	    collides.setFrames(new int[2][]);
	     collides.setComboFrame(new int[]{0,},0);//0
	     collides.setComboFrame(new int[]{},1);//1
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			"0000",

		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        {0,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {1,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {1,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {1,},

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
	

	// Sprite : Effects //EffectTile
	final static public CSprite createSprite_Effects(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(52,tiles);
	     animates.addPart(-8,-8,48,0);//0
		 animates.addPart(-8,-8,49,0);//1
		 animates.addPart(-7,-8,50,0);//2
		 animates.addPart(-8,-8,51,0);//3
		 animates.addPart(-5,-5,22,0);//4
		 animates.addPart(-6,-6,23,0);//5
		 animates.addPart(-8,-8,24,0);//6
		 animates.addPart(-7,-8,17,0);//7
		 animates.addPart(-4,-4,55,0);//8
		 animates.addPart(-4,-4,54,0);//9
		 animates.addPart(-4,-4,56,0);//10
		 animates.addPart(-8,-4,53,0);//11
		 animates.addPart(-4,-3,52,0);//12
		 animates.addPart(-4,-4,31,0);//13
		 animates.addPart(-4,-4,32,0);//14
		 animates.addPart(-4,-4,33,0);//15
		 animates.addPart(-4,-4,34,0);//16
		 animates.addPart(-4,-4,35,0);//17
		 animates.addPart(-4,-4,26,0);//18
		 animates.addPart(-4,-4,27,0);//19
		 animates.addPart(-4,-4,28,0);//20
		 animates.addPart(-4,-4,29,0);//21
		 animates.addPart(-4,-4,30,0);//22
		 animates.addPart(-4,-4,36,0);//23
		 animates.addPart(-4,-4,37,0);//24
		 animates.addPart(-4,-4,38,0);//25
		 animates.addPart(-4,-4,39,0);//26
		 animates.addPart(-4,-4,40,0);//27
		 animates.addPart(0,0,5,0);//28
		 animates.addPart(-1,-1,7,0);//29
		 animates.addPart(-1,-1,6,0);//30
		 animates.addPart(-1,-1,2,0);//31
		 animates.addPart(-3,-3,4,0);//32
		 animates.addPart(-4,-4,1,0);//33
		 animates.addPart(-3,-3,8,0);//34
		 animates.addPart(-3,-3,41,0);//35
		 animates.addPart(-4,-4,42,0);//36
		 animates.addPart(-5,-5,43,0);//37
		 animates.addPart(-6,-6,44,0);//38
		 animates.addPart(-7,-7,45,0);//39
		 animates.addPart(-8,-8,46,0);//40
		 animates.addPart(-9,-9,47,0);//41
		 animates.addPart(-10,-10,25,0);//42
		 animates.addPart(-12,-171,88,0);//43
		 animates.addPart(-12,-173,88,3);//44
		 animates.addPart(-13,-171,88,2);//45
		 animates.addPart(-13,-173,88,1);//46
		 animates.addPart(-11,0,94,0);//47
		 animates.addPart(-11,1,95,0);//48
		 animates.addPart(-11,2,96,0);//49
		 animates.addPart(-11,3,90,0);//50
		 animates.addPart(-11,4,91,0);//51
		
		
	    animates.setFrames(new int[52][]);
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
		 animates.setComboFrame(new int[]{17,},17);//17
		 animates.setComboFrame(new int[]{18,},18);//18
		 animates.setComboFrame(new int[]{19,},19);//19
		 animates.setComboFrame(new int[]{20,},20);//20
		 animates.setComboFrame(new int[]{21,},21);//21
		 animates.setComboFrame(new int[]{22,},22);//22
		 animates.setComboFrame(new int[]{23,},23);//23
		 animates.setComboFrame(new int[]{24,},24);//24
		 animates.setComboFrame(new int[]{25,},25);//25
		 animates.setComboFrame(new int[]{26,},26);//26
		 animates.setComboFrame(new int[]{27,},27);//27
		 animates.setComboFrame(new int[]{28,},28);//28
		 animates.setComboFrame(new int[]{29,},29);//29
		 animates.setComboFrame(new int[]{30,},30);//30
		 animates.setComboFrame(new int[]{31,},31);//31
		 animates.setComboFrame(new int[]{32,},32);//32
		 animates.setComboFrame(new int[]{33,},33);//33
		 animates.setComboFrame(new int[]{34,},34);//34
		 animates.setComboFrame(new int[]{35,},35);//35
		 animates.setComboFrame(new int[]{36,},36);//36
		 animates.setComboFrame(new int[]{37,},37);//37
		 animates.setComboFrame(new int[]{38,},38);//38
		 animates.setComboFrame(new int[]{39,},39);//39
		 animates.setComboFrame(new int[]{40,},40);//40
		 animates.setComboFrame(new int[]{41,},41);//41
		 animates.setComboFrame(new int[]{42,},42);//42
		 animates.setComboFrame(new int[]{43,},43);//43
		 animates.setComboFrame(new int[]{44,},44);//44
		 animates.setComboFrame(new int[]{45,},45);//45
		 animates.setComboFrame(new int[]{46,},46);//46
		 animates.setComboFrame(new int[]{47,},47);//47
		 animates.setComboFrame(new int[]{48,},48);//48
		 animates.setComboFrame(new int[]{49,},49);//49
		 animates.setComboFrame(new int[]{50,},50);//50
		 animates.setComboFrame(new int[]{51,},51);//51
		
		
		// cds
	    CCollides collides = new CCollides(0);
		
	    
	    collides.setFrames(new int[1][]);
	     collides.setComboFrame(new int[]{},0);//0
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			"00 ±»¹¥»÷ ÎïÀí",
"01 ±»¹¥»÷ ±ù",
"02 ±»¹¥»÷ »ð",
"03 ÎïÀíÎ²°Í",
"04 ±ùÎ²°Í",
"05 »ðÎ²°Í",
"06 ¹¥»÷ ÎïÀí",
"07 ¹¥»÷ ±ù",
"08 ¹¥»÷ »ð",
"09",
"10",
"11 À×öªÍò¾û",
"12 µôÇ®",

		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        {0,1,2,3,},
{4,5,6,7,},
{8,9,10,11,12,},
{13,14,15,16,17,},
{18,19,20,21,22,},
{23,24,25,26,27,},
{17,17,16,16,15,15,14,14,13,13,},
{22,22,21,21,20,20,19,19,18,18,},
{27,27,26,26,25,25,24,24,23,23,},
{28,29,30,31,32,33,34,},
{35,36,37,38,39,40,41,42,},
{43,44,45,46,},
{47,48,49,50,51,50,49,48,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,0,0,0,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,0,0,0,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,0,0,0,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,0,0,0,0,0,},

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
	

	// Sprite : Weaopns //EffectTile
	final static public CSprite createSprite_Weaopns(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(97,tiles);
	     animates.addPart(-5,-1,58,2);//0
		 animates.addPart(-5,-2,59,0);//1
		 animates.addPart(-4,-4,57,2);//2
		 animates.addPart(-3,-5,59,7);//3
		 animates.addPart(-1,-5,58,5);//4
		 animates.addPart(-2,-5,59,6);//5
		 animates.addPart(-5,-4,57,0);//6
		 animates.addPart(-5,-2,59,2);//7
		 animates.addPart(-5,-1,58,0);//8
		 animates.addPart(-5,-3,59,3);//9
		 animates.addPart(-3,-5,57,6);//10
		 animates.addPart(-2,-6,59,4);//11
		 animates.addPart(-1,-6,58,7);//12
		 animates.addPart(-3,-6,59,5);//13
		 animates.addPart(-5,-5,57,7);//14
		 animates.addPart(-6,-4,59,1);//15
		 animates.addPart(-5,-2,83,0);//16
		 animates.addPart(-5,-3,84,0);//17
		 animates.addPart(-4,-5,82,2);//18
		 animates.addPart(-3,-6,84,7);//19
		 animates.addPart(-2,-6,83,7);//20
		 animates.addPart(-3,-6,84,6);//21
		 animates.addPart(-5,-5,82,0);//22
		 animates.addPart(-6,-3,84,2);//23
		 animates.addPart(-6,-2,83,2);//24
		 animates.addPart(-6,-3,84,3);//25
		 animates.addPart(-5,-4,82,6);//26
		 animates.addPart(-3,-5,84,4);//27
		 animates.addPart(-2,-5,83,5);//28
		 animates.addPart(-3,-5,84,5);//29
		 animates.addPart(-4,-4,82,7);//30
		 animates.addPart(-5,-3,84,1);//31
		 animates.addPart(-7,-7,70,0);//32
		 animates.addPart(-7,-8,69,0);//33
		 animates.addPart(-7,-6,68,0);//34
		 animates.addPart(-7,-3,74,5);//35
		 animates.addPart(-5,-5,76,0);//36
		 animates.addPart(-3,-8,75,0);//37
		 animates.addPart(-3,-9,74,0);//38
		 animates.addPart(-5,-10,76,6);//39
		 animates.addPart(-8,-8,75,6);//40
		 animates.addPart(-9,-5,74,6);//41
		 animates.addPart(-10,-5,76,3);//42
		 animates.addPart(-8,-6,75,3);//43
		 animates.addPart(-5,-6,74,3);//44
		 animates.addPart(-5,-5,76,5);//45
		 animates.addPart(-6,-3,75,5);//46
		 animates.addPart(-2,-2,0,0);//47
		 animates.addPart(-3,-2,9,0);//48
		 animates.addPart(-4,-2,10,0);//49
		 animates.addPart(-3,-3,86,0);//50
		 animates.addPart(-3,-3,87,0);//51
		 animates.addPart(-3,-3,85,0);//52
		 animates.addPart(-4,-4,64,0);//53
		 animates.addPart(-4,-4,65,0);//54
		 animates.addPart(-6,-6,66,0);//55
		 animates.addPart(-4,-3,56,0);//56
		 animates.addPart(-4,-3,53,2);//57
		 animates.addPart(-4,-3,55,0);//58
		 animates.addPart(-4,-3,52,0);//59
		 animates.addPart(-5,-5,60,0);//60
		 animates.addPart(-7,-7,61,0);//61
		 animates.addPart(-5,-9,72,0);//62
		 animates.addPart(-5,-9,72,2);//63
		 animates.addPart(-5,-9,73,0);//64
		 animates.addPart(-5,-9,73,2);//65
		 animates.addPart(-4,-4,81,0);//66
		 animates.addPart(-4,-3,16,0);//67
		 animates.addPart(-5,-2,12,0);//68
		 animates.addPart(-5,-3,13,0);//69
		 animates.addPart(-4,-3,10,0);//70
		 animates.addPart(-4,-3,9,0);//71
		 animates.addPart(-7,-22,11,3);//72
		 animates.addPart(-6,-6,44,0);//73
		 animates.addPart(-3,-3,41,0);//74
		 animates.addPart(-4,-4,42,0);//75
		 animates.addPart(-5,-5,43,0);//76
		 animates.addPart(-7,-9,78,0);//77
		 animates.addPart(-8,-8,77,0);//78
		 animates.addPart(-7,0,89,0);//79
		 animates.addPart(1,0,89,0);//80
		 animates.addPart(-3,-8,89,0);//81
		 animates.addPart(-8,-2,89,0);//82
		 animates.addPart(0,0,89,0);//83
		 animates.addPart(-2,-8,89,0);//84
		 animates.addPart(-8,-3,89,0);//85
		 animates.addPart(-1,0,89,0);//86
		 animates.addPart(-1,-7,89,0);//87
		 animates.addPart(-7,-4,89,0);//88
		 animates.addPart(-2,1,89,0);//89
		 animates.addPart(0,-5,89,0);//90
		 animates.addPart(-6,-6,89,0);//91
		 animates.addPart(-4,1,89,0);//92
		 animates.addPart(1,-4,89,0);//93
		 animates.addPart(-5,-7,89,0);//94
		 animates.addPart(-6,1,89,0);//95
		 animates.addPart(1,-2,89,0);//96
		
		
	    animates.setFrames(new int[85][]);
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
		 animates.setComboFrame(new int[]{17,},17);//17
		 animates.setComboFrame(new int[]{18,},18);//18
		 animates.setComboFrame(new int[]{19,},19);//19
		 animates.setComboFrame(new int[]{20,},20);//20
		 animates.setComboFrame(new int[]{21,},21);//21
		 animates.setComboFrame(new int[]{22,},22);//22
		 animates.setComboFrame(new int[]{23,},23);//23
		 animates.setComboFrame(new int[]{24,},24);//24
		 animates.setComboFrame(new int[]{25,},25);//25
		 animates.setComboFrame(new int[]{26,},26);//26
		 animates.setComboFrame(new int[]{27,},27);//27
		 animates.setComboFrame(new int[]{28,},28);//28
		 animates.setComboFrame(new int[]{29,},29);//29
		 animates.setComboFrame(new int[]{30,},30);//30
		 animates.setComboFrame(new int[]{31,},31);//31
		 animates.setComboFrame(new int[]{32,},32);//32
		 animates.setComboFrame(new int[]{33,},33);//33
		 animates.setComboFrame(new int[]{34,},34);//34
		 animates.setComboFrame(new int[]{35,},35);//35
		 animates.setComboFrame(new int[]{36,},36);//36
		 animates.setComboFrame(new int[]{37,},37);//37
		 animates.setComboFrame(new int[]{38,},38);//38
		 animates.setComboFrame(new int[]{39,},39);//39
		 animates.setComboFrame(new int[]{40,},40);//40
		 animates.setComboFrame(new int[]{41,},41);//41
		 animates.setComboFrame(new int[]{42,},42);//42
		 animates.setComboFrame(new int[]{43,},43);//43
		 animates.setComboFrame(new int[]{44,},44);//44
		 animates.setComboFrame(new int[]{45,},45);//45
		 animates.setComboFrame(new int[]{46,},46);//46
		 animates.setComboFrame(new int[]{47,},47);//47
		 animates.setComboFrame(new int[]{48,},48);//48
		 animates.setComboFrame(new int[]{49,},49);//49
		 animates.setComboFrame(new int[]{50,},50);//50
		 animates.setComboFrame(new int[]{51,},51);//51
		 animates.setComboFrame(new int[]{52,},52);//52
		 animates.setComboFrame(new int[]{53,},53);//53
		 animates.setComboFrame(new int[]{54,},54);//54
		 animates.setComboFrame(new int[]{55,},55);//55
		 animates.setComboFrame(new int[]{56,},56);//56
		 animates.setComboFrame(new int[]{57,},57);//57
		 animates.setComboFrame(new int[]{58,},58);//58
		 animates.setComboFrame(new int[]{59,},59);//59
		 animates.setComboFrame(new int[]{60,},60);//60
		 animates.setComboFrame(new int[]{61,},61);//61
		 animates.setComboFrame(new int[]{62,},62);//62
		 animates.setComboFrame(new int[]{63,},63);//63
		 animates.setComboFrame(new int[]{64,},64);//64
		 animates.setComboFrame(new int[]{65,},65);//65
		 animates.setComboFrame(new int[]{66,},66);//66
		 animates.setComboFrame(new int[]{67,},67);//67
		 animates.setComboFrame(new int[]{68,},68);//68
		 animates.setComboFrame(new int[]{69,},69);//69
		 animates.setComboFrame(new int[]{70,},70);//70
		 animates.setComboFrame(new int[]{71,},71);//71
		 animates.setComboFrame(new int[]{72,},72);//72
		 animates.setComboFrame(new int[]{73,},73);//73
		 animates.setComboFrame(new int[]{74,},74);//74
		 animates.setComboFrame(new int[]{75,},75);//75
		 animates.setComboFrame(new int[]{76,},76);//76
		 animates.setComboFrame(new int[]{77,},77);//77
		 animates.setComboFrame(new int[]{78,},78);//78
		 animates.setComboFrame(new int[]{79,80,81,},79);//79
		 animates.setComboFrame(new int[]{82,83,84,},80);//80
		 animates.setComboFrame(new int[]{85,86,87,},81);//81
		 animates.setComboFrame(new int[]{88,89,90,},82);//82
		 animates.setComboFrame(new int[]{91,92,93,},83);//83
		 animates.setComboFrame(new int[]{94,95,96,},84);//84
		
		
		// cds
	    CCollides collides = new CCollides(0);
		
	    
	    collides.setFrames(new int[1][]);
	     collides.setComboFrame(new int[]{},0);//0
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			"00 ¼ý",
"01 ¸ß¼¶¼ý",
"02 åó¼ý",
"03 µ¶ÈÐ",
"04 ±ùÊ¸",
"05 ±ùµ¯",
"06 ±ùÍø",
"07 »ðÊ¸",
"08 »ðµ¯",
"09 ÖËÑ×",
"10 ·¶Î§",
"11 ÅÚ",
"12 Õæ¿Õ",
"13 ÉÁµç",
"14 ÐÂÐÇ",
"15 ±ùÇò",
"16 ±©·çÑ©",
"17 »ðÇ½",
"18 »ðÇò",
"19 ÔÉÊ¯",

		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,},
{16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,},
{32,33,34,},
{35,36,37,38,39,40,41,42,43,44,45,46,},
{47,48,49,48,},
{50,51,52,},
{53,54,53,55,55,55,},
{56,56,57,57,58,58,59,59,},
{60,60,60,60,61,61,61,61,61,61,61,61,},
{62,62,63,63,64,64,65,65,},
{66,},
{66,},
{67,67,68,68,69,69,},
{70,70,71,71,72,72,},
{51,51,50,50,52,52,},
{73,},
{74,75,76,73,76,75,},
{77,77,77,77,78,78,},
{60,61,},
{79,80,81,82,83,84,},

	    };
	    int[][] frameCDMap = new int[][]{
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,},
{0,0,0,0,0,0,},

	    };
	    int[][] frameCDAtk = new int[][]{
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,},
{0,0,0,0,0,0,},

	    };
	    int[][] frameCDDef = new int[][]{
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,},
{0,0,0,0,0,0,},

	    };
	    int[][] frameCDExt = new int[][]{
	        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,},
{0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,0,0,0,0,},
{0,0,0,0,0,0,0,0,},
{0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,},
{0,0,0,0,0,0,},
{0,0,0,0,0,0,},
{0,0,},
{0,0,0,0,0,0,},

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
	

	// Sprite : Exp //EffectTile
	final static public CSprite createSprite_Exp(IImages tiles){
	    // tiles
	    CAnimates animates = new CAnimates(0,tiles);
	    
		
	    animates.setFrames(new int[0][]);
	    
		
		// cds
	    CCollides collides = new CCollides(0);
		
	    
	    collides.setFrames(new int[0][]);
	    
	    
		// sprite frame
		/*
		String[] frameName = new String[]{
			
		};
		for(int i=0;i<frameName.length;i++){
			System.out.println(frameName[i]);
		}
		*/
	    int[][] frameAnimate = new int[][]{
	        
	    };
	    int[][] frameCDMap = new int[][]{
	        
	    };
	    int[][] frameCDAtk = new int[][]{
	        
	    };
	    int[][] frameCDDef = new int[][]{
	        
	    };
	    int[][] frameCDExt = new int[][]{
	        
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
	
	 
	final public static String images_SprTile = "SprTile";
	
 
	final public static String images_MapTile = "MapTile";
	
 
	final public static String images_GUITile = "GUITile";
	
 
	final public static String images_TowerTile = "TowerTile";
	
 
	final public static String images_EffectTile = "EffectTile";
	


	
	final public static String map_Map00 = "Map00";
	

	
	
	final public static String spr_Enemy00 = "Enemy00";
	

	final public static String spr_Point = "Point";
	

	final public static String spr_Tower = "Tower";
	

	final public static String spr_Effects = "Effects";
	

	final public static String spr_Weaopns = "Weaopns";
	

	final public static String spr_Exp = "Exp";
	


	//--------------------------------------------------------------------------------------------------------------
	final public static IImages createImages(String key){
	 
		if(key=="SprTile"){
			return createClipImages_SprTile();
		}
	
 
		if(key=="MapTile"){
			return createClipImages_MapTile();
		}
	
 
		if(key=="GUITile"){
			return createClipImages_GUITile();
		}
	
 
		if(key=="TowerTile"){
			return createClipImages_TowerTile();
		}
	
 
		if(key=="EffectTile"){
			return createClipImages_EffectTile();
		}
	

		return null;
	}
	
	final public static CMap createMap(String key, IImages tiles, boolean isAnimate, boolean isCyc){
	
		if(key=="Map00"){
			return createMap_Map00(tiles,isAnimate,isCyc);
		}
	

		return null;
	}
	
	final public static CSprite createSprite(String key, IImages tiles){
	
		if(key=="Enemy00"){
			return createSprite_Enemy00(tiles);
		}
	

		if(key=="Point"){
			return createSprite_Point(tiles);
		}
	

		if(key=="Tower"){
			return createSprite_Tower(tiles);
		}
	

		if(key=="Effects"){
			return createSprite_Effects(tiles);
		}
	

		if(key=="Weaopns"){
			return createSprite_Weaopns(tiles);
		}
	

		if(key=="Exp"){
			return createSprite_Exp(tiles);
		}
	

		return null;
	}
	

//--------------------------------------------------------------------------------------------------------------
// level trunk
//--------------------------------------------------------------------------------------------------------------



	// name const
	
	final public static String world_Level00 = "Level00"; 


	// world names
	final static public String[] WorldNames = new String[]{
	 
	"Level00", 

	};

	// create world
	final static public void buildWorld(String name, CWorld level){
	
		// World : Level00
		if(name=="Level00"){
			// World Size
			level.Width  = 512 ;
			level.Height = 512;
			
			// Map
			 
			
			// Sprite
			// sprite count : 0
			
			
			// WayPoint
			// waypoint count : 22
			CWayPoint[] WayPoints = new CWayPoint[22];
			 
			WayPoints[0] = new CWayPoint(38,2);
			 
			WayPoints[1] = new CWayPoint(471,472);
			 
			WayPoints[2] = new CWayPoint(472,40);
			 
			WayPoints[3] = new CWayPoint(88,40);
			 
			WayPoints[4] = new CWayPoint(88,424);
			 
			WayPoints[5] = new CWayPoint(424,424);
			 
			WayPoints[6] = new CWayPoint(424,88);
			 
			WayPoints[7] = new CWayPoint(136,88);
			 
			WayPoints[8] = new CWayPoint(136,376);
			 
			WayPoints[9] = new CWayPoint(376,376);
			 
			WayPoints[10] = new CWayPoint(376,136);
			 
			WayPoints[11] = new CWayPoint(184,136);
			 
			WayPoints[12] = new CWayPoint(184,328);
			 
			WayPoints[13] = new CWayPoint(328,328);
			 
			WayPoints[14] = new CWayPoint(328,184);
			 
			WayPoints[15] = new CWayPoint(232,184);
			 
			WayPoints[16] = new CWayPoint(232,280);
			 
			WayPoints[17] = new CWayPoint(280,280);
			 
			WayPoints[18] = new CWayPoint(280,232);
			 
			WayPoints[19] = new CWayPoint(264,232);
			 
			WayPoints[20] = new CWayPoint(33,463);
			 
			WayPoints[21] = new CWayPoint(86,466);
			
			// waypoint link 
			 
			WayPoints[0].linkTo(WayPoints[20]);
			 
			WayPoints[1].linkTo(WayPoints[2]);
			 
			WayPoints[1].linkTo(WayPoints[21]);
			 
			WayPoints[2].linkTo(WayPoints[1]);
			 
			WayPoints[2].linkTo(WayPoints[3]);
			 
			WayPoints[3].linkTo(WayPoints[2]);
			 
			WayPoints[3].linkTo(WayPoints[4]);
			 
			WayPoints[4].linkTo(WayPoints[3]);
			 
			WayPoints[4].linkTo(WayPoints[5]);
			 
			WayPoints[5].linkTo(WayPoints[4]);
			 
			WayPoints[5].linkTo(WayPoints[6]);
			 
			WayPoints[6].linkTo(WayPoints[5]);
			 
			WayPoints[6].linkTo(WayPoints[7]);
			 
			WayPoints[7].linkTo(WayPoints[6]);
			 
			WayPoints[7].linkTo(WayPoints[8]);
			 
			WayPoints[8].linkTo(WayPoints[7]);
			 
			WayPoints[8].linkTo(WayPoints[9]);
			 
			WayPoints[9].linkTo(WayPoints[8]);
			 
			WayPoints[9].linkTo(WayPoints[10]);
			 
			WayPoints[10].linkTo(WayPoints[9]);
			 
			WayPoints[10].linkTo(WayPoints[11]);
			 
			WayPoints[11].linkTo(WayPoints[10]);
			 
			WayPoints[11].linkTo(WayPoints[12]);
			 
			WayPoints[12].linkTo(WayPoints[13]);
			 
			WayPoints[12].linkTo(WayPoints[11]);
			 
			WayPoints[13].linkTo(WayPoints[12]);
			 
			WayPoints[13].linkTo(WayPoints[14]);
			 
			WayPoints[14].linkTo(WayPoints[13]);
			 
			WayPoints[14].linkTo(WayPoints[15]);
			 
			WayPoints[15].linkTo(WayPoints[14]);
			 
			WayPoints[15].linkTo(WayPoints[16]);
			 
			WayPoints[16].linkTo(WayPoints[15]);
			 
			WayPoints[16].linkTo(WayPoints[17]);
			 
			WayPoints[17].linkTo(WayPoints[16]);
			 
			WayPoints[17].linkTo(WayPoints[18]);
			 
			WayPoints[18].linkTo(WayPoints[17]);
			 
			WayPoints[18].linkTo(WayPoints[19]);
			 
			WayPoints[19].linkTo(WayPoints[18]);
			 
			WayPoints[20].linkTo(WayPoints[0]);
			 
			WayPoints[20].linkTo(WayPoints[21]);
			 
			WayPoints[21].linkTo(WayPoints[20]);
			 
			WayPoints[21].linkTo(WayPoints[1]);
			
			level.WayPoints = WayPoints;
		}
	

	}
	
	

	
}



