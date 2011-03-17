package com.cell.gfx.game.edit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cell.CIO;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CAnimates;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CCollides;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.LittleIODeserialize;

public class SetInput 
{
	public static class TImages 
	{
		final public String FileName; 
		
		final public int TileCount;	
		
		final public int[] ClipsX;
		final public int[] ClipsY;
		final public int[] ClipsW;
		final public int[] ClipsH;
		
		public TImages(InputStream is) throws IOException
		{
			FileName = null;
			
			TileCount = LittleIODeserialize.getInt(is);		// --> u16
			
			ClipsX = new int[TileCount];
			ClipsY = new int[TileCount];
			ClipsW = new int[TileCount];
			ClipsH = new int[TileCount];
			
			for(int i=0;i<TileCount;i++){
				ClipsX[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsY[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsW[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsH[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
			}
			
			is.close();
		}
		   
		public TImages(String fileName, InputStream is) throws IOException
		{
			FileName = fileName;
			
			TileCount = LittleIODeserialize.getInt(is);		// --> u16
			
			ClipsX = new int[TileCount];
			ClipsY = new int[TileCount];
			ClipsW = new int[TileCount];
			ClipsH = new int[TileCount];
			
			for(int i=0;i<TileCount;i++){
				ClipsX[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsY[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsW[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
				ClipsH[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
			}
			
			is.close();
		}
		
	}
	
	
	public static class TMap
	{

		final public int XCount;
		final public int YCount;
		final public int CellW;
		final public int CellH;
		
		/** TImages.Clips*[index] */
		final public int[] TileID;
		final public int[] TileTrans;
		/** TileID[index] & TileTrans[index] */
		final public int[][] Animates;
		/** TerrainScene2D[y][x] == Animates[index] */
		final public int[][] TerrainScene2D;
		
		
		final public int[] BlocksType;
		final public int[] BlocksMask;
		final public int[] BlocksX1;
		final public int[] BlocksY1;
		final public int[] BlocksX2;
		final public int[] BlocksY2;
		final public int[] BlocksW;
		final public int[] BlocksH;
		
		/** TerrainBlock2D[y][x] == BlocksType[index] */
		final public int[][] TerrainBlock2D;
		
		
		public int getLayerImagesIndex(int x, int y, int layer){
			return TileID[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		public int getLayerTrans(int x, int y, int layer){
			return TileTrans[Animates[TerrainScene2D[y][x]][layer]];
		}
		
		public TMap(InputStream is) throws IOException
		{
//			--------------------------------------------------------------------------------------------------------------
//			property
			XCount = LittleIODeserialize.getInt(is);	// --> u16
			YCount = LittleIODeserialize.getInt(is);	// --> u16
			CellW = LittleIODeserialize.getInt(is);		// --> u16
			CellH = LittleIODeserialize.getInt(is);		// --> u16
//			--------------------------------------------------------------------------------------------------------------
//			parts
			int scenePartCount = LittleIODeserialize.getInt(is);	// --> u16
			TileID = new int[scenePartCount];
			TileTrans = new int[scenePartCount];
		    for(int i=0;i<scenePartCount;i++){
		    	TileID[i] = LittleIODeserialize.getShort(is);	// --> s16 * count
		    	TileTrans[i] = LittleIODeserialize.getByte(is);	// --> s8  * count
		    }
//			--------------------------------------------------------------------------------------------------------------
//			frames
			int animateCount = LittleIODeserialize.getInt(is);		// --> u16
			Animates = new int[animateCount][];
			for(int i=0;i<animateCount;i++){
				int frameCount =  LittleIODeserialize.getInt(is);	// --> u16 * count
				Animates[i] = new int[frameCount];
				for(int f=0;f<frameCount;f++){
					Animates[i][f] = LittleIODeserialize.getShort(is);// --> s16 * length * count
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			tile matrix
			TerrainScene2D = new int[YCount][XCount];
			for(int y=0; y<YCount; y++){
				for(int x=0; x<XCount; x++){
					TerrainScene2D[y][x] = LittleIODeserialize.getShort(is);// --> s16 * xcount * ycount
				}
			}
//			--------------------------------------------------------------------------------------------------------------
//			cds
			int cdCount = LittleIODeserialize.getInt(is);	// --> u16
			BlocksType = new int[cdCount];
			BlocksMask = new int[cdCount];
			BlocksX1 = new int[cdCount];
			BlocksY1 = new int[cdCount];
			BlocksX2 = new int[cdCount];
			BlocksY2 = new int[cdCount];
			BlocksW = new int[cdCount];
			BlocksH = new int[cdCount];
			for(int i=0;i<cdCount;i++){
				BlocksType[i] = LittleIODeserialize.getByte(is);	// --> s8 * count
				BlocksMask[i] = LittleIODeserialize.getInt(is);		// --> s32 * count
				BlocksX1[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				BlocksY1[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				BlocksX2[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				BlocksY2[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				BlocksW[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				BlocksH[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
			}
			
//			--------------------------------------------------------------------------------------------------------------
//			cd matrix
			TerrainBlock2D = new int[YCount][XCount];
			for(int y=0; y<YCount; y++){
				for(int x=0; x<XCount; x++){
					TerrainBlock2D[y][x] = LittleIODeserialize.getShort(is);// --> s16 * xcount * ycount
				}
			}
//			--------------------------------------------------------------------------------------------------------------
	
		
			is.close();
	    
		}
		
	}
	
	public static class TSprite
	{
		
		final public short[] PartX;
		final public short[] PartY;
		final public short[] PartTileID;
		final public byte[] PartTileTrans;
		final public short[][] Parts;
		
		final public int[] BlocksMask;
		final public short[] BlocksX1;
		final public short[] BlocksY1;
		final public short[] BlocksW;
		final public short[] BlocksH;
		final public short[][] Blocks;
		
		final public short[][] FrameAnimate ;
		final public short[][] FrameCDMap ;
		final public short[][] FrameCDAtk ;
		final public short[][] FrameCDDef ;
		final public short[][] FrameCDExt ;
		
		//images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
		
		public int getPartImageIndex(int anim, int frame, int subpart){
			return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartTrans(int anim, int frame, int subpart){
			return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartX(int anim, int frame, int subpart){
			return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		public int getPartY(int anim, int frame, int subpart){
			return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
		}
		
		
		public TSprite(InputStream is) throws IOException
		{
//				--------------------------------------------------------------------------------------------------------------
//				scene parts
				int scenePartCount = LittleIODeserialize.getInt(is);	// --> u16
				PartX = new short[scenePartCount];
				PartY = new short[scenePartCount];
				PartTileID = new short[scenePartCount];
				PartTileTrans = new byte[scenePartCount];
			    for(int i=0;i<scenePartCount;i++){
			    	PartX[i] =  LittleIODeserialize.getShort(is);		// --> s16 * count
			    	PartY[i] =  LittleIODeserialize.getShort(is);		// --> s16 * count
			    	PartTileID[i] =  LittleIODeserialize.getShort(is);	// --> s16 * count
			    	PartTileTrans[i] =  LittleIODeserialize.getByte(is);	// --> s8  * count
			    }
//				--------------------------------------------------------------------------------------------------------------
//				scene frames
				int animatesCount = LittleIODeserialize.getInt(is);		// --> u16
				Parts = new short[animatesCount][];
				for(int i=0;i<animatesCount;i++){
					int frameCount =  LittleIODeserialize.getInt(is);	// --> u16 * count
					Parts[i] = new short[frameCount];
					for(int f=0;f<frameCount;f++){
						Parts[i][f] = LittleIODeserialize.getShort(is);// --> s16 * length * count
					}
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd parts
				int cdCount = LittleIODeserialize.getInt(is);	// --> u16
				BlocksMask = new int[cdCount];
				BlocksX1 = new short[cdCount];
				BlocksY1 = new short[cdCount];
				BlocksW = new short[cdCount];
				BlocksH = new short[cdCount];
				for(int i=0;i<cdCount;i++){
					BlocksMask[i] = LittleIODeserialize.getInt(is);		// --> s32 * count
					BlocksX1[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
					BlocksY1[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
					BlocksW[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
					BlocksH[i] = LittleIODeserialize.getShort(is);		// --> s16 * count
				}
//				--------------------------------------------------------------------------------------------------------------
//				cd frames
				int collidesCount = LittleIODeserialize.getInt(is);		// --> u16
				Blocks = new short[collidesCount][];
				for(int i=0;i<collidesCount;i++){
					int frameCount =  LittleIODeserialize.getInt(is);	// --> u16 * count
					Blocks[i] = new short[frameCount];
					for(int f=0;f<frameCount;f++){
						Blocks[i][f] = LittleIODeserialize.getShort(is);// --> s16 * length * count
					}
				}
//				--------------------------------------------------------------------------------------------------------------
//				animates
				int animateCount = LittleIODeserialize.getInt(is);			// --> u16
				FrameAnimate = new short[animateCount][];
				FrameCDMap = new short[animateCount][];
				FrameCDAtk = new short[animateCount][];
				FrameCDDef = new short[animateCount][];
				FrameCDExt = new short[animateCount][];
				for(int i=0;i<animateCount;i++){
					int frameCount = LittleIODeserialize.getInt(is);		// --> u16 * animateCount
					FrameAnimate[i] = new short[frameCount];
					FrameCDMap[i] = new short[frameCount];
					FrameCDAtk[i] = new short[frameCount];
					FrameCDDef[i] = new short[frameCount];
					FrameCDExt[i] = new short[frameCount];
					for(int f=0;f<frameCount;f++){
						FrameAnimate[i][f] =  LittleIODeserialize.getShort(is);		// --> s16 * animateCount * frameCount
						FrameCDMap[i][f] =  LittleIODeserialize.getShort(is);		// --> s16 * animateCount * frameCount
						FrameCDAtk[i][f] =  LittleIODeserialize.getShort(is);		// --> s16 * animateCount * frameCount
						FrameCDDef[i][f] =  LittleIODeserialize.getShort(is);		// --> s16 * animateCount * frameCount
						FrameCDExt[i][f] =  LittleIODeserialize.getShort(is);		// --> s16 * animateCount * frameCount
					}
				}
//				--------------------------------------------------------------------------------------------------------------
			
			is.close();
		}
	}
	
	
//	########################################################################################################################

	
	
	public static IImages createImagesFromSet(String setFile, IImage image, IImages stuff){
		byte[] data = CIO.loadData(setFile);
		return createImagesFromSet(data, image, stuff);
	}
	
	public static CMap createMapFromSet(String setFile, IImages tiles, boolean isAnimate, boolean isCyc){
		byte[] data = CIO.loadData(setFile);
		return createMapFromSet(data, tiles, isAnimate, isCyc);
	}

	public static CSprite createSpriteFromSet(String setFile, IImages tiles){
		byte[] data = CIO.loadData(setFile);
		return createSpriteFromSet(data, tiles);
	}

	public static CWayPoint[] createWayPointsFromSet(String setFile){
		byte[] data = CIO.loadData(setFile);
		return createWayPointsFromSet(data);
	}

	public static CCD[] createRegionsFromSet(String setFile){
		byte[] data = CIO.loadData(setFile);
		return createRegionsFromSet(data);
	}
	
	
	
//	########################################################################################################################


	public static IImages createImagesFromSet(byte[] data, IImage image, IImages stuff){

		try{
			if(data!=null)
			{
				TImages timgs = new TImages(new ByteArrayInputStream(data));
				
				int count = timgs.TileCount;
				stuff.buildImages(image,count);
				for(int i=0;i<count;i++){
					int x = timgs.ClipsX[i];	// --> s16 * count
					int y = timgs.ClipsY[i];	// --> s16 * count
					int w = timgs.ClipsW[i];	// --> s16 * count
					int h = timgs.ClipsH[i];	// --> s16 * count
					stuff.addTile(x,y,w,h);
				}
				
				data = null;
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

	public static CMap createMapFromSet(byte[] data, IImages tiles, boolean isAnimate, boolean isCyc)
	{

		CMap ret = null;
		
		try{
			
			if(data!=null)
			{
				TMap tmap = new TMap(new ByteArrayInputStream(data));
			
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

			data = null;
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
	
	public static CSprite createSpriteFromSet(byte[] data, IImages tiles){
		
		CSprite ret = null;
			 
		try{

			//ByteArrayInputStream is = new ByteArrayInputStream(data);
			
			if(data!=null)
			{
				TSprite tsprite = new TSprite(new ByteArrayInputStream(data));
				
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
			            frameAnimate, 
			            frameCDMap, 
			            frameCDAtk, 
			            frameCDDef, 
			            frameCDExt 
			            );
			}
			data = null;
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

	public static CWayPoint[] createWayPointsFromSet(InputStream is) throws IOException
	{
		
		CWayPoint wayPoints[] = null;

		// --------------------------------------------------------------------------------------------------------------
		// waypoints
		int waypointCount = LittleIODeserialize.getInt(is); // --> u16
		wayPoints = new CWayPoint[waypointCount];
		for (int i = 0; i < waypointCount; i++) {
			int x = LittleIODeserialize.getInt(is); // --> s32 * count
			int y = LittleIODeserialize.getInt(is); // --> s32 * count
			wayPoints[i] = new CWayPoint(x, y);
		}
		// --------------------------------------------------------------------------------------------------------------
		// links
		int start = LittleIODeserialize.getShort(is); // <-- s16 first test
		while (start >= 0) {
			int end = LittleIODeserialize.getShort(is); // <-- s16
			wayPoints[start].linkTo(wayPoints[end]);
			start = LittleIODeserialize.getShort(is); // <-- s16
		}
		// --------------------------------------------------------------------------------------------------------------

		is.close();
		
		return wayPoints;
	}
	
	
	public static CWayPoint[] createWayPointsFromSet(byte[] data){
		
		CWayPoint wayPoints[] = null;
		
		try{
			if(data!=null){
				wayPoints = createWayPointsFromSet(new ByteArrayInputStream(data));
			}
		}catch(Exception err){
			err.printStackTrace();
		}

		if(wayPoints!=null){
//			System.out.println("SetInput : Load WayPoints ^_^!");
		}else{
			System.err.println("SetInput : Load WayPoints -_-!");
		}
		
		return wayPoints;
	}
	
	public static CCD[] createRegionsFromSet(InputStream is) throws IOException
	{
		CCD regions[] = null;
		
		// regions
		int regionCount = LittleIODeserialize.getInt(is); // --> u16
		regions = new CCD[regionCount];
		for (int i = 0; i < regionCount; i++) {
			int x = LittleIODeserialize.getShort(is); // --> s16 * count
			int y = LittleIODeserialize.getShort(is); // --> s16 * count
			int w = LittleIODeserialize.getShort(is); // --> s16 * count
			int h = LittleIODeserialize.getShort(is); // --> s16 * count
			regions[i] = CCD.createCDRect(0, x, y, w, h);
		}
		
		return regions;
	}

	public static CCD[] createRegionsFromSet(byte[] data){
		
		CCD regions[] = null;
		
		try{
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			
			if(data!=null){
				regions = createRegionsFromSet(new ByteArrayInputStream(data));
			}
		}catch(Exception err){
			err.printStackTrace();
		}

		if(regions!=null){
//			System.out.println("SetInput : Load Regions ^_^!");
		}else{
			System.err.println("SetInput : Load Regions -_-!");
		}
		
		return regions;
	}
	
	
}
