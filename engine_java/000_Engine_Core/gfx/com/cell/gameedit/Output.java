package com.cell.gameedit;


import java.util.Hashtable;
import java.util.Vector;
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
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
public interface Output
{
	/**
	 * @param name child name
	 * @return
	 */
	public byte[]		loadRes(String name, AtomicReference<Float> percent);
	
	
	public Hashtable<String, ImagesSet>		getImgTable();
	public Hashtable<String, SpriteSet>		getSprTable();
	public Hashtable<String, MapSet>		getMapTable();
	public Hashtable<String, WorldSet>		getWorldTable();
	public Hashtable<String, TableSet>		getTableGroups();
	
	

	public IImages		createImagesFromSet(ImagesSet img, IImage image, IImages stuff);
	
	public CMap			createMapFromSet(MapSet tmap, IImages tiles, boolean isAnimate, boolean isCyc);

	public CSprite		createSpriteFromSet(SpriteSet tsprite, IImages tiles);
	
	public CWayPoint[]	createWayPointsFromSet(Vector<WaypointObject> waypoints);

	public CCD[] 		createRegionsFromSet(Vector<RegionObject> regions);


	/**
	 * call by {@link SetResource}.dispose()
	 */
	public void 		dispose();

	
}
