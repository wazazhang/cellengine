package com.cell.gfx.game;

import java.util.Hashtable;

import com.cell.CObject;
import com.cell.gfx.IImages;

public class ResourceManager extends CObject 
{
	static private ResourceManager s_ResourceManager;
	
	static public ResourceManager getInstance(){
		if(s_ResourceManager==null){
			s_ResourceManager = new ResourceManager();
		}
		return s_ResourceManager;
	}
	
//	----------------------------------------------------------------------------------
	
	Hashtable Table;

	protected ResourceManager(){
		Table = new Hashtable();
	}
	
	final public void clear(){
		Table.clear();
	}
	
	final public void addObj(String key, Object value){
		Table.put(key, value);
	}
	
	final public Object getObj(String key){
		return Table.get(key);
	}
	
	
	public void addTile(String key, IImages tiles){
		addObj("IMG_"+key, tiles);
	}
	public void addSprite(String key, CSprite spr){
		addObj("SPR_"+key, spr);
	}
	public void addMap(String key, CMap map){
		addObj("MAP_"+key, map);
	}

	public IImages getTile(String key){
		return (IImages)getObj("IMG_"+key);
	}
	public CSprite getSprite(String key){
		return (CSprite)getObj("SPR_"+key);
	}
	public CMap getCMap(String key){
		return (CMap)getObj("MAP_"+key);
	}
	
}
