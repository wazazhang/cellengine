package com.cell.gameedit.output;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.gameedit.Output;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CAnimates;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CCollides;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.TextDeserialize;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
abstract public class BaseOutput implements Output
{
	final Hashtable<String, ImagesSet>		ImgTable 		= new Hashtable<String, ImagesSet>();
	final Hashtable<String, SpriteSet>		SprTable 		= new Hashtable<String, SpriteSet>();
	final Hashtable<String, MapSet>			MapTable 		= new Hashtable<String, MapSet>();
	final Hashtable<String, WorldSet>		WorldTable		= new Hashtable<String, WorldSet>();
	final Hashtable<String, TableSet>		TableGroups		= new Hashtable<String, TableSet>();
	
	@Override
	final public Hashtable<String, ImagesSet> getImgTable() {
		return ImgTable;
	}
	
	@Override
	final public Hashtable<String, MapSet> getMapTable() {
		return MapTable;
	}
	
	@Override
	final public Hashtable<String,SpriteSet> getSprTable() {
		return SprTable;
	}
	
	@Override
	final public Hashtable<String, TableSet> getTableGroups() {
		return TableGroups;
	}
	
	@Override
	final public Hashtable<String, WorldSet> getWorldTable() {
		return WorldTable;
	}
	
//	------------------------------------------------------------------------------------------------

	/**
	 * input "{1234},{5678}"
	 * return [1234][5678]
	 */
	final protected static String[] getArray2D(String text)
	{
		text = text.replace('{', ' ');
		String[] texts = CUtil.splitString(text, "},");
		for (int i=texts.length-1; i>=0; --i) {
			texts[i] = texts[i].trim();
		}
		return texts;
	}
	
	/**
	 * input 3,123,4,5678
	 * return [123] [5678]
	 * @param text
	 * @return
	 */
	final protected static String[] getArray1D(String text)
	{
		StringReader reader = new StringReader(text);
		ArrayList<String> list = new ArrayList<String>();
		try{
			while(true){
				String line = TextDeserialize.getString(reader);
				list.add(line);
			}
		}catch (Exception e) {}
		return list.toArray(new String[list.size()]);
	}
//	------------------------------------------------------------------------------------------------


//	--------------------------------------------------------------------------------------------------------------
	
	final public IImages createImagesFromSet(ImagesSet img, IImage image, IImages stuff)
	{
		try{
			if (img != null)
			{
				int count = img.Count;
				stuff.buildImages(image,count);
				for(int i=0;i<count;i++){
					int x = img.ClipsX[i];	// --> s16 * count
					int y = img.ClipsY[i];	// --> s16 * count
					int w = img.ClipsW[i];	// --> s16 * count
					int h = img.ClipsH[i];	// --> s16 * count
					stuff.addTile(x,y,w,h);
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		
		if(stuff!=null){
//			System.out.println("SetInput : Load TilesSet ^_^!");
		}else{
			System.err.println("SetInput : Load TilesSet -_-!");
		}
		
		return stuff;
	}
	
//	########################################################################################################################

	final public CMap createMapFromSet(MapSet tmap, IImages tiles, boolean isAnimate, boolean isCyc)
	{

		CMap ret = null;
		
		try{
			
			if(tmap!=null)
			{
//				--------------------------------------------------------------------------------------------------------------
//				property
				int xcount = tmap.XCount;	// --> u16
				int ycount = tmap.YCount;	// --> u16
				int cellw = tmap.CellW;		// --> u16
				int cellh = tmap.CellH;		// --> u16
//				--------------------------------------------------------------------------------------------------------------
//				parts
				int scenePartCount = tmap.TileID.length;	// --> u16
			    CAnimates animates = new CAnimates(scenePartCount,tiles);
			    for(int i=0;i<scenePartCount;i++){
			    	int tileID = tmap.TileID[i];	// --> s16 * count
			    	int trans = tmap.TileTrans[i];	// --> s8  * count
			    	animates.addPart(0,0,tileID,trans);
			    }
//				--------------------------------------------------------------------------------------------------------------
//				frames
				int animateCount = tmap.Animates.length;		// --> u16
				short[][] animates_frame = new short[animateCount][];
				for(int i=0;i<animateCount;i++){
					int frameCount =  tmap.Animates[i].length;	// --> u16 * count
					animates_frame[i] = new short[frameCount];
					for(int f=0;f<frameCount;f++){
						animates_frame[i][f] = (short)tmap.Animates[i][f];// --> s16 * length * count
					}
				}
				animates.setFrames(animates_frame);
//				--------------------------------------------------------------------------------------------------------------
//				tile matrix
				short[][] tileMatrix = new short[ycount][xcount];
				for(int y=0; y<ycount; y++){
					for(int x=0; x<xcount; x++){
						tileMatrix[y][x] = (short)tmap.TerrainScene2D[y][x];// --> s16 * xcount * ycount
					}
				}
//				--------------------------------------------------------------------------------------------------------------
//				cds
				int cdCount = tmap.BlocksType.length;	// --> u16
				CCollides collides = new CCollides(cdCount);
				for(int i=0;i<cdCount;i++){
					byte type = (byte)tmap.BlocksType[i];	// --> s8 * count
					int mask = tmap.BlocksMask[i];		// --> s32 * count
					int x1 = tmap.BlocksX1[i];		// --> s16 * count
					int y1 = tmap.BlocksY1[i];		// --> s16 * count
					int x2 = tmap.BlocksX2[i];		// --> s16 * count
					int y2 = tmap.BlocksY2[i];		// --> s16 * count
					int w = tmap.BlocksW[i];		// --> s16 * count
					int h = tmap.BlocksH[i];		// --> s16 * count
					if(type==0) collides.addCDRect(mask, x1, y1, w, h);
					if(type==1) collides.addCDLine(mask, x1, y1,x2,y2);
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd matrix
				short[][] flagMatrix = new short[ycount][xcount];
				for(int y=0; y<ycount; y++){
					for(int x=0; x<xcount; x++){
						flagMatrix[y][x] = (short)tmap.TerrainBlock2D[y][x];// --> s16 * xcount * ycount
					}
				}
//				--------------------------------------------------------------------------------------------------------------
				ret = new CMap(
						animates, 
						collides, 
						cellw, cellh, 
						tileMatrix, 
						flagMatrix, 
						isCyc 
						);
				ret.IsAnimate = isAnimate;
			}

		}catch(Exception err){
			err.printStackTrace();
		}
		
		if(ret!=null){
//			System.out.println("SetInput : Load Map ^_^!");
		}else{
			System.err.println("SetInput : Load Map -_-!");
		}
	    
	    return ret;
	}
	
//	########################################################################################################################
	
	final public CSprite createSpriteFromSet(SpriteSet tsprite, IImages tiles){
		
		CSprite ret = null;
			 
		try{

			if(tsprite!=null)
			{
//				--------------------------------------------------------------------------------------------------------------
//				scene parts
				int scenePartCount = tsprite.PartTileID.length;	// --> u16
			    CAnimates animates = new CAnimates(scenePartCount,tiles);
			    for(int i=0;i<scenePartCount;i++){
			    	animates.addPart(
			    			tsprite.PartX[i], 
			    			tsprite.PartY[i],
							tsprite.PartTileID[i], 
							tsprite.PartTileTrans[i]);
			    }
//				--------------------------------------------------------------------------------------------------------------
//				scene frames
				animates.setFrames(tsprite.Parts);
//				--------------------------------------------------------------------------------------------------------------
//				cd parts
				int cdCount = tsprite.BlocksMask.length;	// --> u16
				CCollides collides = new CCollides(cdCount);
				for(int i=0;i<cdCount;i++){
					collides.addCDRect(
							tsprite.BlocksMask[i],
							tsprite.BlocksX1[i], 
							tsprite.BlocksY1[i],
							tsprite.BlocksW[i], 
							tsprite.BlocksH[i]);
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd frames
				collides.setFrames(tsprite.Blocks);
//				--------------------------------------------------------------------------------------------------------------
//				animates
				short[][] frameAnimate = tsprite.FrameAnimate;
				short[][] frameCDMap = tsprite.FrameCDMap;
				short[][] frameCDAtk = tsprite.FrameCDAtk;
				short[][] frameCDDef = tsprite.FrameCDDef;
				short[][] frameCDExt = tsprite.FrameCDExt;
//				--------------------------------------------------------------------------------------------------------------
				ret = new CSprite(
			            animates, 
			            collides, 
			            tsprite.AnimateNames,
			            frameAnimate, 
			            frameCDMap, 
			            frameCDAtk, 
			            frameCDDef, 
			            frameCDExt 
			            );
			}

		}catch(Exception err){
			err.printStackTrace();
		}

		if(ret!=null){
//			System.out.println("SetInput : Load Spr ^_^!");
		}else{
			System.err.println("SetInput : Load Spr -_-!");
		}
		
	    return ret;
	}
	
//	########################################################################################################################

	final public CWayPoint[] createWayPointsFromSet(Vector<WaypointObject> waypoints)
	{
		CWayPoint wayPoints[] = new CWayPoint[waypoints.size()];
		for (int i = waypoints.size() - 1; i >= 0; --i) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = new CWayPoint(src.X, src.Y);
			wp.SetData = getArray1D(src.Data);
			wayPoints[i] = wp;
		}
		for (int i = waypoints.size() - 1; i >= 0; --i) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = wayPoints[i];
			for (int j = src.Nexts.size() - 1; j >= 0; --j) {
				WaypointObject next = src.Nexts.get(i);
				wp.link(wayPoints[next.Index]);
			}
		}
		
		return wayPoints;
	}
	
	
	final public CCD[] createRegionsFromSet(Vector<RegionObject> regions)
	{
		CCD cds[] = new CCD[regions.size()];
		for (int i = regions.size() - 1; i >= 0; --i) {
			RegionObject src = regions.get(i);
			CCD cd = CCD.createCDRect(0, src.X, src.Y, src.W, src.H);
			cd.SetData = getArray1D(src.Data);
			cds[i] = cd;
		}
		return cds;
	}



}



