package com.cell.gameedit.output;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
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



}



