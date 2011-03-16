package Cell.Game;
import javax.microedition.lcdui.Image;

import Cell.CObject;
import Cell.CIO;


public class CResManager extends CObject{
//	----------------------------------------------------------------------------------------------
//// 	file arg
//	final static public int ARG_LOAD_BIN 			= 1;// load file bin
//	final static public int ARG_LOAD_IMAGE	 		= 2;// load file image
//	final static public int ARG_LOAD_PAK	 		= 3;// load file pak
//	----------------------------------------------------------------------------------------------
// 	images arg
	final static public int ARG_TILE_NEW 			= 1;// new : idimg, count
	final static public int ARG_TILE_SET_IMAGE 		= 2;// setTileImage : idimg
	final static public int ARG_TILE_ADD_ALL		= 3;// addtile : 
	final static public int ARG_TILE_ADD_RECT		= 4;// addtile : x, y, w, h
	final static public int ARG_TILE_ADD_REGION		= 5;// addtile : x, y, w, h, cw, ch
	final static public int ARG_TILE_ADD_IMAGE		= 6;// addtile : idimg
	final static public int ARG_TILE_ADD_PAK		= 7;// addtile : idbin
//	----------------------------------------------------------------------------------------------
//	 cd arg
	final static public int ARG_CD_NEW_RECT			= 1;// new rect : mask, x, y, w, h
	final static public int ARG_CD_NEW_LINE			= 2;// new line : mask, x, y, w, h, px, py, qx, qy
//	----------------------------------------------------------------------------------------------
// 	animate arg
	final static public int ARG_ANIMATE_NEW			= 1;// new : count, images
	final static public int ARG_ANIMATE_ADD_ALL		= 2;// add part all : center
	final static public int ARG_ANIMATE_ADD_POS		= 3;// add part : x, y, idimgsid, flip
	final static public int ARG_ANIMATE_SET_FRAME	= 4;// set combo frame : index, idimgsid[]
//	----------------------------------------------------------------------------------------------
// 	collide arg
	final static public int ARG_COLLIDE_NEW			= 1;// new : count
	final static public int ARG_COLLIDE_ADD_RECT	= 2;// add cd : mask, x, y, w, h
	final static public int ARG_COLLIDE_ADD_CD		= 3;// add cd : idcd
	final static public int ARG_COLLIDE_SET_FRAME	= 4;// set combo frame : index, idcdid[]
//	----------------------------------------------------------------------------------------------
// 	sprite arg
	final static public int ARG_SPRITE_NEW			= 1;// new : count, idanim, idcoll
	final static public int ARG_SPRITE_SET_FRAME_A	= 2;// set Animate frame : index, idanimid[]
	final static public int ARG_SPRITE_SET_FRAME_C	= 3;// set Animate frame : index, idcollid[]
	final static public int ARG_SPRITE_SET_POS		= 4;// set pos : x, y
	final static public int ARG_SPRITE_SET_CUR 		= 5;// set curFrame : anim, frame
	final static public int ARG_SPRITE_SET_MAP_CD	= 6;// set mapcd : mask, x, y, w, h
//	----------------------------------------------------------------------------------------------
// 	map arg
	final static public int ARG_MAP_NEW				= 1;// new : idanim, cellw, cellh, iscyc, isanim
//	final static public int ARG_MAP_SET_ANIMATE		= 2;// set animate tiles : index, idanim[]
//	----------------------------------------------------------------------------------------------
// 	camera arg
	final static public int ARG_CAMERA_NEW			= 1;// new : wx, wy, ww, wh, isbuff, color
	final static public int ARG_CAMERA_SET_POS		= 2;// set pos : x, y
//	----------------------------------------------------------------------------------------------
// 	script
	
//	----------------------------------------------------------------------------------------------
//	fields
//  data
	protected String[] ioFileListBins ;
	protected String[] ioFileListBinsPak ;
	protected String[] ioFileListImages ;
	protected String[] ioFileListImagesPak ;
	
	private byte[][] 	ioBins;
	private byte[][][] 	ioBinsPak;
	private Image[] 	ioImages ;
	private Image[][] 	ioImagesPak ;
	
	protected int[][][] resImages ;
	protected int[][][] resCDs ;
	protected int[][][] resAnimates ;
	protected int[][][] resCollides ;
	protected int[][][] resSprites ;
	
	protected short[][] resMapMatrixTile ;
	protected short[][] resMapMatrixCD ;

	protected int[][] resMap ;
	protected int[][] resCameras ;
	
//	unit
	private CImages[]	cimagess;
	private CCD[] 		ccds;
	private CAnimate[] 	canimates;
	private CCollides[] ccollidess;
	private CSprite[] 	csprites;
	
	private CCamera 	ccamera;
	private CMap 		cmap;
//	----------------------------------------------------------------------------------------------
// 	method
	public void initFile(){
		println("ResFile : ");
		if(ioFileListBins!=null){
			ioBins = new byte[ioFileListBins.length][];
			for(int i=0;i<ioFileListBins.length;i++){
				if(ioFileListBins[i]!=null){
					println("File["+i+"] : " + ioFileListBins[i] );
					ioBins[i] = CIO.loadFile(ioFileListBins[i]);
				}else{
					println("File["+i+"] : null" );
				}
			}
		}
		if(ioFileListBinsPak!=null){
			ioBinsPak = new byte[ioFileListBinsPak.length][][];
			for(int i=0;i<ioFileListBinsPak.length;i++){
				if(ioFileListBinsPak[i]!=null){
					println("FilePak["+i+"] : " + ioFileListBinsPak[i] );
					ioBinsPak[i] = CIO.unpackFiles(ioFileListBinsPak[i]);
				}else{
					println("FilePak["+i+"] : null" );
				}
			}
		}
		if(ioFileListImages!=null){
			ioImages = new Image[ioFileListImages.length];
			for(int i=0;i<ioFileListImages.length;i++){
				if(ioFileListImages[i]!=null){
					println("Image["+i+"] : " + ioFileListImages[i] );
					ioImages[i] = CIO.loadImage(ioFileListImages[i]);
				}else{
					println("Image["+i+"] : null" );
				}
			}
		}
		if(ioFileListImagesPak!=null){
			ioImagesPak = new Image[ioFileListImagesPak.length][];
			for(int i=0;i<ioFileListImagesPak.length;i++){
				if(ioFileListImagesPak[i]!=null){
					println("ImagePak["+i+"] : " + ioFileListImagesPak[i] );
					ioImagesPak[i] = CIO.loadImages(ioFileListImagesPak[i]);
				}else{
					println("ImagePak["+i+"] : null" );
				}
			}
		}
	}
	
	public CImages[] loadCImages(){
		CImages[] imgs = null;
		println("Res Images : ");
		if(resImages!=null){
			imgs = new CImages[resImages.length];
			for(int i=0;resImages!=null&&i<resImages.length;i++){
				for(int j=0;resImages[i]!=null&&j<resImages[i].length;j++){
					if(resImages[i][j]!=null&&resImages[i][j].length>0){
						try {
							switch(resImages[i][j][0]){
							case ARG_TILE_NEW:
								imgs[i] = new CImages(ioImages[resImages[i][j][1]],resImages[i][j][2]);
								println("NewImages["+i+"] :"+" image="+resImages[i][j][1]+" count=" + resImages[i][j][2] );
								break;
							case ARG_TILE_SET_IMAGE:
								imgs[i].setTileImage(ioImages[resImages[i][j][1]]);
								break;
							case ARG_TILE_ADD_ALL:
								imgs[i].addTile();
								break;
							case ARG_TILE_ADD_RECT:
								imgs[i].addTile(resImages[i][j][1], resImages[i][j][2], resImages[i][j][3], resImages[i][j][4]); 
								break;
							case ARG_TILE_ADD_REGION:
								imgs[i].addTile(resImages[i][j][1], resImages[i][j][2], resImages[i][j][3], resImages[i][j][4], resImages[i][j][5], resImages[i][j][6]); 
								break;
							case ARG_TILE_ADD_IMAGE:
								imgs[i].setTileImage(ioImages[resImages[i][j][1]]);
								imgs[i].addTile(); 
								break;
							case ARG_TILE_ADD_PAK:
								imgs[i].addTile(ioImagesPak[resImages[i][j][1]]); 
								break;
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return imgs;
	}
	
	public CCD[] loadCCD(){
		CCD[] ccds = null;
		println("Res CDs : ");
		if(resCDs!=null){
			ccds = new CCD[resCDs.length];
			for(int i=0;resCDs!=null&&i<resCDs.length;i++){
				for(int j=0;resCDs[i]!=null&&j<resCDs[i].length;j++){
					if(resCDs[i][j]!=null&&resCDs[i][j].length>0){
						try {
							switch(resCDs[i][j][0]){
							case ARG_CD_NEW_RECT:
								ccds[i] = CCD.createCDRect(resCDs[i][j][1], resCDs[i][j][2], resCDs[i][j][3], resCDs[i][j][4], resCDs[i][j][5]);
								println("NewCDRect["+i+"] :"+" mask="+resCDs[i][j][1]+" x="+resCDs[i][j][2]+" y="+resCDs[i][j][3]+" w="+resCDs[i][j][4]+" h="+resCDs[i][j][5]);
								break;
							case ARG_CD_NEW_LINE:
								ccds[i] = CCD.createCDLine(resCDs[i][j][1], resCDs[i][j][2], resCDs[i][j][3], resCDs[i][j][4], resCDs[i][j][5], resCDs[i][j][6], resCDs[i][j][7], resCDs[i][j][8], resCDs[i][j][9]);
								println("NewCDLine["+i+"] :"+" mask="+resCDs[i][j][1]+" x="+resCDs[i][j][2]+" y="+resCDs[i][j][3]+" w="+resCDs[i][j][4]+" h="+resCDs[i][j][5]+" px="+resCDs[i][j][6]+" py="+resCDs[i][j][7]+" qx="+resCDs[i][j][8]+" qy="+resCDs[i][j][9]);
								break;
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return ccds;
	
	}
	
	public CAnimate[] loadCAnimate(){
		CAnimate[] anims = null;
		println("Res Animates : ");
		if(resAnimates!=null){
			anims = new CAnimate[resAnimates.length];
			for(int i=0;resAnimates!=null&&i<resAnimates.length;i++){
				for(int j=0;resAnimates[i]!=null&&j<resAnimates[i].length;j++){
					if(resAnimates[i][j]!=null&&resAnimates[i][j].length>0){
						try {
							switch(resAnimates[i][j][0]){
							case ARG_ANIMATE_NEW:
								anims[i] = new CAnimate(resAnimates[i][j][1],cimagess[resAnimates[i][j][2]]);
								println("NewAnimate["+i+"] :"+" count="+resAnimates[i][j][1]+" images="+resAnimates[i][j][2]);
								break;
							case ARG_ANIMATE_ADD_ALL:
								anims[i].addPart(resAnimates[i][j][1]==0?false:true);
								break;
							case ARG_ANIMATE_ADD_POS:
								anims[i].addPart(resAnimates[i][j][1], resAnimates[i][j][2], resAnimates[i][j][3], resAnimates[i][j][4]);
								break;
							case ARG_ANIMATE_SET_FRAME:
								anims[i].Frames[resAnimates[i][j][1]] = new int[resAnimates[i][j].length-2];
								System.arraycopy(resAnimates[i][j], 2, anims[i].Frames[resAnimates[i][j][1]], 0, resAnimates[i][j].length-2);
								break;
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return anims;
	}
	
	public CCollides[] loadCCollides(){

		CCollides[] cdss = null;
		println("Res Collides : ");
		if(resAnimates!=null){
			cdss = new CCollides[resCollides.length];
			for(int i=0;resCollides!=null&&i<resCollides.length;i++){
				for(int j=0;resCollides[i]!=null&&j<resCollides[i].length;j++){
					if(resCollides[i][j]!=null&&resCollides[i][j].length>0){
						try {
							switch(resCollides[i][j][0]){
							case ARG_COLLIDE_NEW:
								cdss[i] = new CCollides(resCollides[i][j][1]);
								println("NewCollides["+i+"] :"+" count="+resCollides[i][j][1]);
								break;
							case ARG_COLLIDE_ADD_RECT:
								cdss[i].addCDRect(resCollides[i][j][1], resCollides[i][j][2], resCollides[i][j][3], resCollides[i][j][4], resCollides[i][j][5]);
								break;
							case ARG_COLLIDE_ADD_CD:
								cdss[i].addCD(ccds[resCollides[i][j][1]]);
								break;
							case ARG_COLLIDE_SET_FRAME:
								cdss[i].Frames[resCollides[i][j][1]] = new int[resCollides[i][j].length-2];
								System.arraycopy(resCollides[i][j], 2, cdss[i].Frames[resCollides[i][j][1]], 0, resCollides[i][j].length-2);
								break;
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return cdss;
	
	}
	
	public CSprite[] loadCSprite(){
		CSprite[] sprs = null;
		println("Res Sprites : ");
		if(resSprites!=null){
			sprs = new CSprite[resSprites.length];
			for(int i=0;resSprites!=null&&i<resSprites.length;i++){
				for(int j=0;resSprites[i]!=null&&j<resSprites[i].length;j++){
					if(resSprites[i][j]!=null&&resSprites[i][j].length>0){
						try {
							switch(resSprites[i][j][0]){
							case ARG_SPRITE_NEW:
								sprs[i] = new CSprite(canimates[resSprites[i][j][2]],ccollidess[resSprites[i][j][3]]);
								sprs[i].FrameAnimate = new int[resSprites[i][j][1]][];
								sprs[i].FrameCollide = new int[resSprites[i][j][1]][];
								println("NewSprite["+i+"] :"+" count="+resSprites[i][j][1]+" animate="+resSprites[i][j][2]+" collides="+resSprites[i][j][3]);
								break;
							case ARG_SPRITE_SET_FRAME_A:
								sprs[i].FrameAnimate[resSprites[i][j][1]] = new int[resSprites[i][j].length-2];
								System.arraycopy(resSprites[i][j], 2, sprs[i].FrameAnimate[resSprites[i][j][1]], 0, resSprites[i][j].length-2);
								break;
							case ARG_SPRITE_SET_FRAME_C:
								sprs[i].FrameCollide[resSprites[i][j][1]] = new int[resSprites[i][j].length-2];
								System.arraycopy(resSprites[i][j], 2, sprs[i].FrameCollide[resSprites[i][j][1]], 0, resSprites[i][j].length-2);
								break;
							case ARG_SPRITE_SET_POS:
								sprs[i].setPos(resSprites[i][j][1],resSprites[i][j][2]);
								break;
							case ARG_SPRITE_SET_CUR:
								sprs[i].setCurrentFrame(resSprites[i][j][1],resSprites[i][j][2]);
								break;
							case ARG_SPRITE_SET_MAP_CD:
								sprs[i].mapcd = CCD.createCDRect(resSprites[i][j][1],resSprites[i][j][2],resSprites[i][j][3],resSprites[i][j][4],resSprites[i][j][5]);
								break;
							}
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return sprs;
	
	
	}
	
	public CMap loadCMap(){
		CMap map = null;
		println("Res Map : ");
		if(resMap!=null){
			for(int i=0;resMap!=null&&i<resMap.length;i++){
				if(resMap[i]!=null&&resMap[i].length>0){
					try {
						switch(resMap[i][0]){
						case ARG_MAP_NEW:
							map = new CMap(canimates[resMap[i][1]],resMap[i][2],resMap[i][3],resMapMatrixTile,resMapMatrixCD,resMap[i][4]==0?false:true,resMap[i][5]==0?false:true);
							println("NewMap :"+" tile(animate)="+resMap[i][1]+" cellw="+resMap[i][2]+" cellh="+resMap[i][3]+" iscyc="+resMap[i][4]+" isanim="+resMap[i][5]);
							break;
//						case ARG_MAP_SET_ANIMATE:
//							map.Animates[resMap[i][1]] = canimates[resMap[i][2]];
//							break;
						}
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return map;
	
	}
	
	public CCamera loadCCamera(){
		CCamera cam = null;
		println("Res Camera : ");
		if(resCameras!=null){
			for(int i=0;resCameras!=null&&i<resCameras.length;i++){
				if(resCameras[i]!=null&&resCameras[i].length>0){
					try {
						switch(resCameras[i][0]){
						case ARG_CAMERA_NEW:
							cam = new CCamera(resCameras[i][1],resCameras[i][2],resCameras[i][3],resCameras[i][4],cmap,resCameras[i][5]==0?false:true,resCameras[i][6]);
							println("NewCamera :"+" wx="+resCameras[i][1]+" wy="+resCameras[i][2]+" w="+resMap[i][3]+" h="+resCameras[i][4]+" isbackbuffer="+resCameras[i][5]+" backcolor="+resCameras[i][6]);
							break;
						case ARG_CAMERA_SET_POS:
							cam.setPos(resCameras[i][1],resCameras[i][2]);
							break;
						}
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return cam;

	}
	
	public CWorld createWorld(){
		println("");
		println("Begin Free Memory = "+(Runtime.getRuntime().freeMemory()/1024)+"(K byte)");
		println("");
		// load file
		initFile();
		// init all
		cimagess 	= loadCImages();
		ccds 		= loadCCD();
		canimates 	= loadCAnimate();
		ccollidess 	= loadCCollides();
		csprites 	= loadCSprite();
		
		cmap 		= loadCMap();
		ccamera 	= loadCCamera();
		
		CWorld ret 	= new CWorld(cmap,ccamera,csprites);
		
		println("");
		println("End Free Memory = "+(Runtime.getRuntime().freeMemory()/1024)+"(K byte)");
		println("");
		
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
}
