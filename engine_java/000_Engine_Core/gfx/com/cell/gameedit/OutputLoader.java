package com.cell.gameedit;


import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
public interface OutputLoader
{
	/***
	 * 是否单独输出每张图
	 * @return
	 */
	public boolean isTile() ;
	
	/**
	 * 是否输出整图
	 * @return
	 */
	public boolean isGroup();
	
	/**
	 * 获得导出图片文件类型
	 * @return
	 */
	public String getImageExtentions();
	
	/**
	 * 读取导出资源，比如图片什么的
	 * @param name child name
	 * @return
	 */
	public byte[] loadRes(String name, AtomicReference<Float> percent);
	
	
	public Hashtable<String, ImagesSet>		getImgTable();
	public Hashtable<String, SpriteSet>		getSprTable();
	public Hashtable<String, MapSet>		getMapTable();
	public Hashtable<String, WorldSet>		getWorldTable();
	public Hashtable<String, TableSet>		getTableGroups();
	
	
//
//	public IImages		createImagesFromSet(ImagesSet img, IImage image, IImages stuff);
//	
//	public CMap			createMapFromSet(MapSet tmap, IImages tiles, boolean isAnimate, boolean isCyc);
//
//	public CSprite		createSpriteFromSet(SpriteSet tsprite, IImages tiles);
//	
//	public CWayPoint[]	createWayPointsFromSet(java.util.Map<Integer, WaypointObject> waypoints);
//
//	public CCD[] 		createRegionsFromSet(java.util.Map<Integer, RegionObject> regions);


	/**
	 * call by {@link SetResource}.dispose()
	 */
	public void 		dispose();

	
}
