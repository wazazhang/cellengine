package com.cell.gameedit.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import com.cell.gameedit.SetObject;

public class WorldSet implements SetObject
{
	private static final long serialVersionUID = 1L;
	
	final public int Index;
	final public String Name;
	public int GridXCount;
	public int GridYCount;
	public int GridW;
	public int GridH;
	public int Width;
	public int Height;

	public TreeMap<Integer, SpriteObject> 	Sprs 		= new TreeMap<Integer, SpriteObject>();
	public TreeMap<Integer, MapObject> 		Maps 		= new TreeMap<Integer, MapObject>();
	public TreeMap<Integer, WaypointObject> WayPoints 	= new TreeMap<Integer, WaypointObject>();
	public TreeMap<Integer, RegionObject> 	Regions 	= new TreeMap<Integer, RegionObject>();

	public String Data;
	public int[][] Terrian;

	
	public WorldSet(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
	
	
	public int getIndex() {
		return Index;
	}

	public String getName() {
		return Name;
	}

	public int getTerrainCell(int grid_x, int grid_y) {
		return Terrian[grid_x][grid_y];
	}
	

	public static class MapObject implements Serializable 
	{
		private static final long serialVersionUID = 1L;

		public int Index;
		public String UnitName;
		public String MapID;
		public String ImagesID;
		public int X;
		public int Y;
		public String Data;

	}

	public static class SpriteObject implements Serializable 
	{
		private static final long serialVersionUID = 1L;

		public int Index;
		public String UnitName;
		public String SprID;
		public String ImagesID;
		public int Anim;
		public int Frame;
		public int X;
		public int Y;
		public String Data;

	}

	public static class WaypointObject implements Serializable 
	{
		private static final long serialVersionUID = 1L;

		public int Index;
		public int X;
		public int Y;
		public String Data;

		public ArrayList<WaypointObject> Nexts = new ArrayList<WaypointObject>();

	}

	public static class RegionObject implements Serializable
	{
		private static final long serialVersionUID = 1L;

		public int Index;
		public int X;
		public int Y;
		public int W;
		public int H;
		public String Data;

	}
	
}
