package com.cell.gameedit;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

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
import com.cell.util.MarkedHashtable;
import com.cell.util.concurrent.ThreadPoolService;

/**
 * 对应output.properties的资源文件
 * @author WAZA
 */
abstract public class SetResource
{
//	-------------------------------------------------------------------------------------
	
	final public Hashtable<String, ImagesSet>		ImgTable;
	final public Hashtable<String, SpriteSet>		SprTable;
	final public Hashtable<String, MapSet>			MapTable;
	final public Hashtable<String, WorldSet>		WorldTable;
	final public Hashtable<String, TableSet>		TableGroups;

	final protected Output							output_adapter;
	
	final protected	MarkedHashtable 				resource_manager;
//	final protected	ThreadPoolService				loading_service;
	
//	-------------------------------------------------------------------------------------
	
	public SetResource(Output adapter) throws Exception
	{
		this.output_adapter		= adapter;
//		this.loading_service	= loading_service;
		this.resource_manager	= new MarkedHashtable();
		
		this.ImgTable 			= output_adapter.getImgTable();
		this.SprTable 			= output_adapter.getSprTable();
		this.MapTable 			= output_adapter.getMapTable();
		this.WorldTable			= output_adapter.getWorldTable();
		this.TableGroups		= output_adapter.getTableGroups();
	}
	
	public Output getOutput() {
		return output_adapter;
	}
	
	public void dispose() {
		for (Object obj : resource_manager.values()) {
			if (obj instanceof StreamTiles) {
				((StreamTiles) obj).unloadAllImages();
			}
		}
		output_adapter.dispose();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	Resources
//	-------------------------------------------------------------------------------------------------------------------------------
//
//	/**
//	 * 同步获取图片方法<br>
//	 * 如果有特殊需要，可以重载此方法
//	 * @param img
//	 * @return
//	 * @throws IOException
//	 */
//	abstract protected StreamTiles getLocalImage(ImagesSet img) throws IOException ;
	
	/**
	 * 异步获取图片方法<br>
	 * 如果有特殊需要，可以重载此方法
	 * @param img
	 * @return
	 * @throws IOException
	 */
	abstract protected StreamTiles getStreamImage(ImagesSet img) throws IOException ;
	
//	-------------------------------------------------------------------------------------------------------------------------------

	synchronized
	final public StreamTiles getImages(ImagesSet img) 
	{
		StreamTiles stuff = resource_manager.get("IMG_" + img.Index, StreamTiles.class);
		if (stuff != null) {
			if (!stuff.isLoaded()) {
				if (!stuff.isLoading()) {
					Future<?> task = executeTask(stuff);
					if (task == null) {
						stuff.run();
					}
				}
			}
			return stuff;
		}

		try {
			stuff = getStreamImage(img);
			Future<?> task = executeTask(stuff);
			if (task == null) {
				stuff.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resource_manager.put("IMG_" + img.Index, stuff);
		
		return stuff;
	}
	
	
	synchronized
	final public StreamTiles getImages(String key){
		ImagesSet img = ImgTable.get(key);
		return getImages(img);
			
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	synchronized
	final public CSprite getSprite(SpriteSet spr){
		IImages tiles = getImages(spr.ImagesName);
		return getSprite(spr, tiles);
	}
	
	synchronized
	final public CSprite getSprite(SpriteSet spr, IImages tiles)
	{
		CSprite cspr = resource_manager.get("SPR_"+spr.Index, CSprite.class);
		if (cspr != null) {
			return new CSprite(cspr);
		}
		cspr = createSpriteFromSet(spr, tiles);
		resource_manager.put("SPR_"+spr.Index, cspr);
		return new CSprite(cspr);
	}
	
	synchronized
	final public CSprite getSprite(String key){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr);
	}
	
	synchronized
	final public CSprite getSprite(String key, IImages images){
		SpriteSet spr = SprTable.get(key);
		return getSprite(spr, images);
	}

	synchronized
	final public AtomicReference<CSprite> getSpriteAsync(String key, LoadSpriteListener ... listener)
	{
		SpriteSet spr = SprTable.get(key);
		if (spr != null) {
			AtomicReference<CSprite> ret = new AtomicReference<CSprite>();
			CSprite obj = resource_manager.get("SPR_"+key, CSprite.class);
			if (obj != null) {
				CSprite cspr = new CSprite(obj);
				ret.set(cspr);
				for (LoadSpriteListener l : listener) {
					l.loaded(this, cspr, spr);
				}
			} else {
				LoadSpriteTask task = createLoadSpriteTask(spr, ret, listener);
				Future<?> future_task = executeTask(task);
				if (future_task == null) {
					new Thread(task, "get-sprite-" + key).start();
				}
			}
			return ret;
		} else {
			throw new NullPointerException("sprite not found : " + key);
		}
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 异步执行一个任务
	 * @param r
	 * @return 返回为空表示该任务不能够异步执行，则将转为同步执行
	 */
	public Future<?> executeTask(Runnable r) {
		return null;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------------------

	//
	synchronized
	final public CWayPoint[] getWorldWayPoints(String key)
	{
		CWayPoint[] points = resource_manager.get("WPS_"+key, CWayPoint[].class);
		if (points != null) {
			return points;
		}
		WorldSet world = WorldTable.get(key);
		points = createWayPointsFromSet(world.WayPoints);
		resource_manager.put("WPS_"+key, points);
		return points;
	}
	
	synchronized
	final public CCD[] getWorldRegions(String key)
	{
		CCD[] regions = resource_manager.get("WRS_"+key, CCD[].class);
		if (regions != null) {
			return regions;
		}
		WorldSet world = WorldTable.get(key);
		regions = createRegionsFromSet(world.Regions);
		resource_manager.put("WRS_"+key, regions);
		return regions;
	}

//	-------------------------------------------------------------------------------------------------------------------------------
//	Set Object in <setfile>.properties
//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	final public WorldSet getSetWorld(String key) {
		return WorldTable.get(key);
	}

	final public SpriteSet getSetSprite(String key) {
		return SprTable.get(key);
	}

	final public MapSet getSetMap(String key) {
		return MapTable.get(key);
	}

	final public ImagesSet getSetImages(String key) {
		return ImgTable.get(key);
	}
	
	final public TableSet getTableGroup(String key) {
		return TableGroups.get(key);
	}
	
	final public<T extends SetObject> T getSetObject(Class<T> cls, String key)
	{
		SetObject ret = null;
		if (ImagesSet.class.isAssignableFrom(cls)) {
			ret = ImgTable.get(key);
		}
		else if (SpriteSet.class.isAssignableFrom(cls)) {
			ret = SprTable.get(key);
		}
		else if (MapSet.class.isAssignableFrom(cls)) {
			ret = MapTable.get(key);
		}
		else if (WorldSet.class.isAssignableFrom(cls)) {
			ret = WorldTable.get(key);
		}
		else if (TableSet.class.isAssignableFrom(cls)) {
			ret = TableGroups.get(key);
		}
		if (ret!=null) {
			return cls.cast(ret);
		}
		return null;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------------
//	utils
//	--------------------------------------------------------------------------------------------------------------------------------------------------
	
	final public void initAllResource(SetLoading progress)
	{
		// images 
		{
			int count = ImgTable.size();
			int index = 0;
			Enumeration<ImagesSet> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				ImagesSet ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (progress!=null) {
					progress.progress(this, images, index, count);
				}
				index ++;
			}
		}
		
		// sprites
		{
			int count = SprTable.size();
			int index = 0;
			Enumeration<SpriteSet> sprs = SprTable.elements();
			while (sprs.hasMoreElements()) {
				SpriteSet ss = sprs.nextElement();
				CSprite sprite = getSprite(ss);
				if (progress!=null) {
					progress.progress(this, sprite, index, count);
				}
				index ++;
			}
		}
		
		// worlds
		{
			int count = WorldTable.size();
			int index = 0;
			Enumeration<WorldSet> worlds = WorldTable.elements();
			while (worlds.hasMoreElements()) {
				WorldSet ws = worlds.nextElement();
				CWayPoint[] points = getWorldWayPoints(ws.Name);
				CCD[] regions = getWorldRegions(ws.Name);
				if (progress!=null) {
					progress.progress(this, points, index, count);
					progress.progress(this, regions, index, count);
				}
				index ++;
			}
		}
	}
	
	
	final public boolean isStreamingImages(String images_name) {
		ImagesSet img = ImgTable.get(images_name);
		if (img!=null) {
			StreamTiles tiles = resource_manager.get("IMG_" + img.Index, StreamTiles.class);
			if (tiles!=null) {
				return !tiles.isLoaded();
			}
		}
		return false;
	}
	
	
	

//	--------------------------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------

	protected IImages createImagesFromSet(ImagesSet img, IImage image, IImages stuff)
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

	protected CMap createMapFromSet(MapSet tmap, IImages tiles, boolean isAnimate, boolean isCyc)
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
	
	protected CSprite createSpriteFromSet(SpriteSet tsprite, IImages tiles)
	{
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

	protected CWayPoint[] createWayPointsFromSet(Map<Integer, WaypointObject> waypoints)
	{
		CWayPoint wayPoints[] = new CWayPoint[waypoints.size()];
		for (int i : waypoints.keySet()) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = new CWayPoint(src.X, src.Y);
			wp.SetData = src.Data;
			wayPoints[i] = wp;
		}
		for (int i : waypoints.keySet()) {
			WaypointObject src = waypoints.get(i);
			CWayPoint wp = wayPoints[i];
			for (int j = src.Nexts.size() - 1; j >= 0; --j) {
				WaypointObject next = src.Nexts.get(i);
				wp.link(wayPoints[next.Index]);
			}
		}
		
		return wayPoints;
	}
	
	
	protected CCD[] createRegionsFromSet(Map<Integer, RegionObject> regions)
	{
		CCD cds[] = new CCD[regions.size()];
		for (int i : regions.keySet()) {
			RegionObject src = regions.get(i);
			CCD cd = CCD.createCDRect(0, src.X, src.Y, src.W, src.H);
			cd.SetData = src.Data;
			cds[i] = cd;
		}
		return cds;
	}



//	-------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------
	

	protected LoadSpriteTask createLoadSpriteTask(
			SpriteSet spr, 
			AtomicReference<CSprite> ref,
			LoadSpriteListener[] listener) 
	{
		return new LoadSpriteTask(this, spr, ref, listener);
	}
	
	public static interface LoadSpriteListener
	{
		public void loaded(SetResource set, CSprite cspr, SpriteSet spr);
	}
	
	public static class LoadSpriteTask implements Runnable
	{
		final protected SetResource 	res;
		final protected SpriteSet 		spr;

		final AtomicReference<CSprite> 	ref;
		final LoadSpriteListener[] 		listener;
		
		public LoadSpriteTask(
				SetResource res,
				SpriteSet spr, 
				AtomicReference<CSprite> ref,
				LoadSpriteListener[] listener) {
			this.res		= res;
			this.spr		= spr;
			this.listener 	= listener;
			this.ref		= ref;
		}

		@Override
		public void run() {
			try {
				CSprite cspr = res.getSprite(spr);
				ref.set(cspr);
				for (LoadSpriteListener l : listener) {
					l.loaded(res, cspr, spr);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}
	
	
}
