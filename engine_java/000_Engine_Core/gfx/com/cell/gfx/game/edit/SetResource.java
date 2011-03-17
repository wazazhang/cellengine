package com.cell.gfx.game.edit;

import java.io.ByteArrayInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gfx.AScreen;
import com.cell.gfx.CImages;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.util.PropertyGroup;

/*
 * 

IMG=<NAME>@<IMAGES INDEX>
MAP=<NAME>@<MAP INDEX>@<IMAGES NAME>
SPR=<NAME>@<SPR INDEX>@<IMAGES NAME>@anim_name_1$anim_name_2$anim_name_3$

WORLD=<NAME>@<WORLD INDEX>@<WIDTH>@<HEIGHT>@<GRID W>@<GRID H>

WORLD_<NAME>_MAP=<INDEX>@<MAP NAME>@<IDENTIFY>@<SUPER>@<X>@<Y>
WORLD_<NAME>_SPR=<INDEX>@<SPR NAME>@<IDENTIFY>@<SUPER>@<ANIMATE ID>@<FRAME ID>@<X>@<Y>

 *
 */

public class SetResource 
{
	
	final public static String TS = "@";
	final public static String DS = "$";
	
	
	public static class IMG
	{
		final public String File;
		final public String ImagesID;
		final public int Index;
		
		public IMG(String[] args, SetResource res){
			File = args[0];
			ImagesID = replaceRegion(args[0], res.KeyRegionIMG);
			Index = Integer.parseInt(args[1]);
		}
	}
	
	public 	static class SPR
	{
		final public String File;
		final public String SprID;
		final public int Index;
		final public String ImagesID;
		
		final public int AnimateCount;
		final public String[] AnimateNames;
		
		public SPR(String[] args, SetResource res){
			File = args[0];
			SprID = replaceRegion(args[0], res.KeyRegionSPR);
			Index = Integer.parseInt(args[1]);
			ImagesID = args[2];
			
			if (args.length>4){
				AnimateNames = CUtil.splitString(args[4], DS);
				AnimateCount = AnimateNames.length;
			}else{
				AnimateCount = 0;
				AnimateNames = new String[0];
			}
		
		}
		
	}
	
	public 	static class MAP
	{
		final public String File;
		final public String MapID;
		final public int Index;
		final public String ImagesID;
		
		public MAP(String[] args, SetResource res){
			File = args[0];
			MapID = replaceRegion(args[0], res.KeyRegionMAP);
			Index = Integer.parseInt(args[1]);
			ImagesID = args[2];
		}
	}
	
	public 	static class WORLD
	{

		public 	static class MAP
		{
			
			final public 	int Index;
			final public 	String UnitName;
			final public 	String MapID;
			final public 	String ImagesID;
			final public 	int X;
			final public 	int Y;
			
			public MAP(String[] args, SetResource res)
			{
				Index 		= Integer.parseInt(args[0]);
				UnitName 	= args[1];
				MapID 		= replaceRegion(args[2], res.KeyRegionMAP);
				ImagesID 	= replaceRegion(args[3], res.KeyRegionIMG);
				X 			= Integer.parseInt(args[4]);
				Y 			= Integer.parseInt(args[5]);
			}
		}
		
		public 	static class SPR
		{
			final public 	int Index;
			final public 	String UnitName;
			final public 	String SprID;
			final public 	String ImagesID;
			final public 	int Anim;
			final public 	int Frame;
			final public 	int X;
			final public 	int Y;
			
			public SPR(String[] args, SetResource res)
			{
				Index 		= Integer.parseInt(args[0]);
				UnitName 	= args[1];
				SprID 		= replaceRegion(args[2], res.KeyRegionSPR);
				ImagesID 	= replaceRegion(args[3], res.KeyRegionIMG);
				Anim		= Integer.parseInt(args[4]);
				Frame		= Integer.parseInt(args[5]);
				X 			= Integer.parseInt(args[6]);
				Y 			= Integer.parseInt(args[7]);
			}
		}
		
		final public String File;
		final public String WorldID;
		final public int Index;
		final public int Width;
		final public int Height;
		final public int GridW;
		final public int GridH;
		
		
		final public Vector<WORLD.SPR> 	Sprs 		= new Vector<WORLD.SPR>();
		final public Vector<WORLD.MAP> 	Maps 		= new Vector<WORLD.MAP>();
	
		final public Vector<String[]> 	WayPointDatas	= new Vector<String[]>();
		final public Vector<String[]> 	RegionDatas		= new Vector<String[]>();
		
		public WORLD(String[] args, PropertyGroup config, SetResource res)
		{
			File = args[0];
			WorldID = replaceRegion(args[0], res.KeyRegionWorld);
			Index = Integer.parseInt(args[1]);

			if (args.length>5){
				Width	= Integer.parseInt(args[2]);
				Height	= Integer.parseInt(args[3]);
				GridW	= Integer.parseInt(args[4]);
				GridH	= Integer.parseInt(args[5]);
			} else {
				Width	= 0;
				Height	= 0;
				GridW	= 0;
				GridH	= 0;
			}
			
			Vector<String> maps = config.get("WORLD_"+args[0]+"_MAP");
			Vector<String> sprs = config.get("WORLD_"+args[0]+"_SPR");
			Vector<String> wpss = config.get("WORLD_"+args[0]+"_WPS");
			Vector<String> wrss = config.get("WORLD_"+args[0]+"_WRS");
			
			if (maps!=null){
				for (int i=maps.size()-1; i>=0; --i){
					String[] value = maps.get(i).split(TS);
					Maps.insertElementAt(new WORLD.MAP(value, res), 0);
				}
			}
			if (sprs!=null){
				for (int i=sprs.size()-1; i>=0; --i){
					String[] value = sprs.get(i).split(TS);
					Sprs.insertElementAt(new WORLD.SPR(value, res), 0);
				}
			}
			if (wpss!=null){
				for (int i=wpss.size()-1; i>=0; --i){
					String[] value = wpss.get(i).split(TS);
					String[] wpsd;
					if (value.length>1){
						wpsd = CUtil.splitString(value[1], DS);
					}else{
						wpsd = new String[0];
					}
					WayPointDatas.insertElementAt(wpsd, 0);
				}
			}
			if (wrss!=null){
				for (int i=wrss.size()-1; i>=0; --i){
					String[] value = wrss.get(i).split(TS);
					String[] wrsd;
					if (value.length>1){
						wrsd = CUtil.splitString(value[1], DS);
					}else{
						wrsd = new String[0];
					}
					RegionDatas.insertElementAt(wrsd, 0);
				}
			}
		}
		
	}
	
	// 只读入一个场景
	public static interface WorldMaker {
		
		public void begin(SetResource set, WORLD world);
		
		public void putSprite(SetResource set, WORLD.SPR wspr, SPR spr);
		
		public void putMap(SetResource set, WORLD.MAP wmap, MAP map);
	
		public void end(SetResource set, WORLD world);
	}
	
	// 对set里所有文件进行缓冲
	public static interface SetLoading
	{
		public void progress(SetResource set, IImages images, int progress, int maxcount);
		public void progress(SetResource set, CSprite spr, int progress, int maxcount);
		public void progress(SetResource set, CMap map, int progress, int maxcount);
		public void progress(SetResource set, CWayPoint[] points, int progress, int maxcount);
		public void progress(SetResource set, CCD[] regions, int progress, int maxcount);
	}
	
//	-------------------------------------------------------------------------------------
	private static class LoadingTask implements Runnable
	{
		SetResource Res;
		
		String WorldID;
		
		WorldMaker Maker;
		
		
		public LoadingTask(SetResource res, String worldID, WorldMaker maker)
		{
			Res = res;
			WorldID = worldID;
			Maker = maker;
		}
		
		public void run() {
			Res.makeWorld(WorldID, Maker);
		}
	}
	
	public static SetResource startLoadingWorld(final String path, final String worldID, final WorldMaker maker){
		SetResource res = new SetResource(path);
		Thread thread = CObject.getAppBridge().createServiceThread(new LoadingTask(res, worldID, maker));
		thread.start();
		return res;
	}
	
	private static String replaceRegion(String src, int[] region){
		String dst = src;
		if (region==null){
		}else if (region.length==1){
			dst = src.substring(region[0]);
		}else if (region.length==2){
			dst = src.substring(region[0], region[1]);
		}
		return dst;
	}
	
//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
	final private int[] KeyRegionIMG, KeyRegionMAP, KeyRegionSPR, KeyRegionWorld;

	final public String Path;

	PropertyGroup Config;

	public Hashtable<String, SPR>   SprTable 		= new Hashtable<String, SPR>();
	public Hashtable<String, IMG>   ImgTable 		= new Hashtable<String, IMG>();
	public Hashtable<String, MAP>   MapTable 		= new Hashtable<String, MAP>();
	public Hashtable<String, WORLD> WorldTable		= new Hashtable<String, WORLD>();
	
	public Hashtable<Integer, SPR>   SprTableIndex 		= new Hashtable<Integer, SPR>();
	public Hashtable<Integer, IMG>   ImgTableIndex 		= new Hashtable<Integer, IMG>();
	public Hashtable<Integer, MAP>   MapTableIndex 		= new Hashtable<Integer, MAP>();
	public Hashtable<Integer, WORLD> WorldTableIndex	= new Hashtable<Integer, WORLD>();
	
	public int FileCount = 0;
	public int LoadedFileCount ;
	
	final public int SpriteTypeCount;
	final public int ImagesTypeCount;
	final public int MapTypeCount;
	final public int WorldTypeCount;
	

	//private Hashtable<String, String> CachedValTable = new Hashtable<String, String>(); 
	
	Hashtable<String, Object> ResourceManager ;
	
	SetLoading Progress;
	
//	final public boolean IsResourceManager;
	
	public SetResource(String path, int[] imgKeyRegion, int[] mapKeyRegion, int[] sprKeyRegion, int[] worldKeyRegion)
	{
		this(path, imgKeyRegion, mapKeyRegion, sprKeyRegion, worldKeyRegion, null);
	}
	
	public SetResource(String path, int[] imgKeyRegion, int[] mapKeyRegion, int[] sprKeyRegion, int[] worldKeyRegion, SetLoading progress)
	{
		Progress = progress;
		
		KeyRegionIMG = imgKeyRegion;
		KeyRegionMAP = mapKeyRegion;
		KeyRegionSPR = sprKeyRegion;
		KeyRegionWorld = worldKeyRegion;
		
		Path = path;
	
		String conf = new String(loadRes("/SetOutput.conf"));
		
		Config = new PropertyGroup(conf,"=");
		
		Vector<String> imgs = Config.get("IMG");
		Vector<String> maps = Config.get("MAP");
		Vector<String> sprs = Config.get("SPR");
		Vector<String> worlds = Config.get("WORLD");
		
		
		if (imgs!=null){
			for (int i=imgs.size()-1; i>=0; --i){
				String[] value = imgs.get(i).split(TS);
				IMG img = new IMG(value, this);
				ImgTable.put(img.ImagesID, img);
				ImgTableIndex.put(img.Index, img);
				FileCount ++ ;// .ts
			}
			ImagesTypeCount = imgs.size();
		}else{
			ImagesTypeCount = 0;
		}
		
		if (maps!=null){
			for (int i=maps.size()-1; i>=0; --i){
				String[] value = maps.get(i).split(TS);
				MAP map = new MAP(value, this);
				MapTable.put(map.MapID, map);
				MapTableIndex.put(map.Index, map);
				FileCount ++ ;// .ms
			}
			MapTypeCount = maps.size();
		}else{
			MapTypeCount = 0;
		}

		if(sprs!=null){
			for (int i=sprs.size()-1; i>=0; --i){
				String[] value = sprs.get(i).split(TS);
				SPR spr = new SPR(value, this);
				SprTable.put(spr.SprID, spr);
				SprTableIndex.put(spr.Index, spr);
				FileCount ++ ;// .ss
			}
			SpriteTypeCount = sprs.size();
		}else{
			SpriteTypeCount = 0;
		}
		
		
		if (worlds!=null){
			for (int i=worlds.size()-1; i>=0; --i){
				String[] value = worlds.get(i).split(TS);
				WORLD world = new WORLD(value, Config, this);
				WorldTable.put(world.WorldID, world);
				WorldTableIndex.put(world.Index, world);
				FileCount += 3;// .wps .wrs .ws
			}
			WorldTypeCount = worlds.size();
		}else{
			WorldTypeCount = 0;
		}
	
		if (Progress!=null)
		{
			ResourceManager = new Hashtable<String, Object>();
			
			initAllResource();
		}
		
	}
	
	public SetResource(String path)
	{
		this(path, null, null, null, null);
	}
	
	public SetResource(String path, SetLoading progress)
	{
		this(path, null, null, null, null, progress);
	}
	
	//------------------------------------------------------------------------------------
	
	public IImages getImages(IMG img) 
	{
		IImages stuff = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("IMG_"+img.Index);
			if (obj instanceof IImages) {
				stuff = (IImages)obj;
			}
		}
		
		if (stuff==null){
			IImage image = AScreen.getGfxAdapter().createImage(new ByteArrayInputStream(loadRes("/set/"+img.File+".png")));
			stuff = new CImages();
			byte[] data = loadRes("/set/"+img.File+".ts");
			SetInput.createImagesFromSet(data, image, stuff);
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("IMG_"+img.Index, stuff);
		}
		
		return stuff;
	}
	
	public IImages getImages(String key){
		IMG img = ImgTable.get(key);
		return getImages(img);
			
	}	
	public IImages getImages(int index){
		IMG img = ImgTableIndex.get(index);
		return getImages(img);
	}
	

	
	//------------------------------------------------------------------------------------
	
	public CMap getMap(MAP map){
		IImages tiles = getImages(map.ImagesID);
		return getMap(map, tiles);
	}
	
	public CMap getMap(MAP map, IImages tiles)
	{
		CMap cmap = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("MAP_"+map.Index);
			if (obj instanceof CMap) {
				cmap = (CMap)obj;
			}
		}
		
		if (cmap==null){
			byte[] data = loadRes("/set/"+map.File+".ms");
			cmap = SetInput.createMapFromSet(data, tiles, false, false);
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("MAP_"+map.Index, cmap);
		}
		
		return cmap;
	}
	
	public CMap getMap(String key, boolean isAnimate, boolean isCyc){
		MAP map = MapTable.get(key);
		IImages tiles = getImages(map.ImagesID);
		CMap cmap = getMap(map, tiles);
		cmap.setCyc(isCyc);
		cmap.IsAnimate = isAnimate;
		return cmap;
	}
	
	public CMap getMap(String key, boolean isAnimate, boolean isCyc, IImages images){
		MAP map = MapTable.get(key);
		CMap cmap = getMap(map);
		cmap.setCyc(isCyc);
		cmap.IsAnimate = isAnimate;
		return cmap;
	}
	
	
	//------------------------------------------------------------------------------------
	
	public CSprite getSprite(SPR spr){
		IImages tiles = getImages(spr.ImagesID);
		return getSprite(spr, tiles);
	}
	
	public CSprite getSprite(SPR spr, IImages tiles)
	{
		CSprite cspr = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("SPR_"+spr.Index);
			if (obj instanceof CSprite) {
				cspr = (CSprite)obj;
			}
		}
		
		if (cspr==null){
			byte[] data = loadRes("/set/"+spr.File+".ss");
			cspr = SetInput.createSpriteFromSet(data, tiles);
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("SPR_"+spr.Index, cspr);
		}
		
		return new CSprite(cspr);
	}
	
	public CSprite getSprite(String key){
		SPR spr = SprTable.get(key);
		return getSprite(spr);
	}
	public CSprite getSprite(int index){
		SPR spr = SprTableIndex.get(index);
		return getSprite(spr);
	}
	public CSprite getSprite(String key, IImages images){
		SPR spr = SprTable.get(key);
		return getSprite(spr, images);
	}
	public CSprite getSprite(int index, IImages images){
		SPR spr = SprTableIndex.get(index);
		return getSprite(spr, images);
	}
	
	//------------------------------------------------------------------------------------
	
	//
	public CWayPoint[] getWorldWayPoints(String key)
	{
		WORLD world = WorldTable.get(key);
		
		CWayPoint[] points = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("WPS_"+key);
			if (obj instanceof CWayPoint[]) {
				points = (CWayPoint[])obj;
			}
		}
		
		if (points==null) {
			byte[] data = loadRes("/set/"+world.File+".wps");
			points = SetInput.createWayPointsFromSet(data);
			for (int i=0; i<world.WayPointDatas.size(); i++) {
				points[i].SetData = world.WayPointDatas.elementAt(i);
			}
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("WPS_"+key, points);
		}
		
		return points;
	}
	
	public CCD[] getWorldRegions(String key)
	{
		WORLD world = WorldTable.get(key);
		
		CCD[] regions = null;
		
		if (ResourceManager!=null) {
			Object obj = ResourceManager.get("WRS_"+key);
			if (obj instanceof CCD[]) {
				regions = (CCD[])obj;
			}
		}
		
		if (regions==null) {
			byte[] data = loadRes("/set/"+world.File+".wrs");
			regions = SetInput.createRegionsFromSet(data);
			for (int i=0; i<world.RegionDatas.size(); i++) {
				regions[i].SetData = world.RegionDatas.elementAt(i);
			}
		}
		
		if (ResourceManager!=null) {
			ResourceManager.put("WRS_"+key, regions);
		}
		
		return regions;
	}
	
	
	public WORLD getWorldSet(String key)
	{
		WORLD world = WorldTable.get(key);
		return world;
	}
	
	//------------------------------------------------------------------------------------
	
	public void makeWorld(String key, WorldMaker maker)
	{
		WORLD world = WorldTable.get(key);
		
		maker.begin(this, world);
		
		for (int i=world.Maps.size()-1; i>=0; --i){
			WORLD.MAP wmap = world.Maps.elementAt(i);
			MAP map = MapTable.get(wmap.MapID);
			maker.putMap(this, wmap, map);
		}
	
		for (int i=world.Sprs.size()-1; i>=0; --i){
			WORLD.SPR wspr = world.Sprs.elementAt(i);
			SPR spr = SprTable.get(wspr.SprID);
			maker.putSprite(this, wspr, spr);
		}
		
		maker.end(this, world);

	}
	
//	----------------------------------------------------------------------------------------------------

	public void initAllResource()
	{
		// images 
		{
			int count = ImgTable.size();
			int index = 0;
			Enumeration<IMG> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				IMG ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (Progress!=null) {
					Progress.progress(this, images, index, count);
				}
				index ++;
			}
		}
		
		// sprites
		{
			int count = SprTable.size();
			int index = 0;
			Enumeration<SPR> sprs = SprTable.elements();
			while (sprs.hasMoreElements()) {
				SPR ss = sprs.nextElement();
				CSprite sprite = getSprite(ss);
				if (Progress!=null) {
					Progress.progress(this, sprite, index, count);
				}
				index ++;
			}
		}
		
		// maps
		{
			int count = MapTable.size();
			int index = 0;
			Enumeration<MAP> maps = MapTable.elements();
			while (maps.hasMoreElements()) {
				MAP ms = maps.nextElement();
				CMap map = getMap(ms);
				if (Progress!=null) {
					Progress.progress(this, map, index, count);
				}
				index ++;
			}
		}
		
		// worlds
		{
			int count = WorldTable.size();
			int index = 0;
			Enumeration<WORLD> worlds = WorldTable.elements();
			while (worlds.hasMoreElements()) {
				WORLD ws = worlds.nextElement();
				CWayPoint[] points = getWorldWayPoints(ws.WorldID);
				CCD[] regions = getWorldRegions(ws.WorldID);
				if (Progress!=null) {
					Progress.progress(this, points, index, count);
					Progress.progress(this, regions, index, count);
				}
				index ++;
			}
		}
	}
	
	public void distoryAllResource(){
		if (ResourceManager!=null) {
			ResourceManager.clear();
		}
	}
	
//	----------------------------------------------------------------------------------------------------
	private boolean m_IsDataBuffer = false;
	
	
	public void loadBuffer(boolean background)
	{
		if (m_IsDataBuffer){
			return;
		}
		
		m_IsDataBuffer = true;
		
		Thread t = CObject.getAppBridge().createServiceThread(
		new Runnable()
		{
			public void run()
			{
				try{
					{
						Enumeration<IMG> imgs = ImgTable.elements();
						while (imgs.hasMoreElements()) {
							IMG ts = imgs.nextElement();
							loadRes("/set/"+ts.File+".ts");
							loadRes("/set/"+ts.File+".png");
							Thread.sleep(1);
						}
					}
					{
						Enumeration<SPR> sprs = SprTable.elements();
						while (sprs.hasMoreElements()) {
							SPR ss = sprs.nextElement();
							loadRes("/set/"+ss.File+".ss");
							Thread.sleep(1);
						}
					}
					{
						Enumeration<MAP> maps = MapTable.elements();
						while (maps.hasMoreElements()) {
							MAP ms = maps.nextElement();
							loadRes("/set/"+ms.File+".ms");
							Thread.sleep(1);
						}
					}
					{
						Enumeration<WORLD> worlds = WorldTable.elements();
						while (worlds.hasMoreElements()) {
							WORLD ws = worlds.nextElement();
							loadRes("/set/"+ws.File+".wps");
							loadRes("/set/"+ws.File+".wrs");
							Thread.sleep(1);
						}
					}
				}catch(InterruptedException err){err.printStackTrace();}
			}
			
		}		
		);
		
		if (background){
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		}else{
			t.run();
		}
		
	}
	
	
	private byte[] loadRes(String path)
	{
		byte[] data = CIO.loadData(Path+path);
		if (data == null) {
			System.err.println("SetResource : read error : " + path);
		} 
		return data;
	}
	
	
	
	
}
