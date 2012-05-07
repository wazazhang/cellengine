package com.cell.gameedit.object;

import com.cell.gameedit.SetObject;


public class MapSet implements SetObject
{
	private static final long serialVersionUID = 1L;
	
	final public int 	Index;
	final public String Name;
	
	public String 		ImagesName;
	
	public int 			XCount;
	public int 			YCount;
	public int			CellW;
	public int			CellH;
	public int			LayerCount;
	
	public int[] 		BlocksType;
	public int[] 		BlocksMask;
	public int[] 		BlocksX1;
	public int[] 		BlocksY1;
	public int[] 		BlocksX2;
	public int[] 		BlocksY2;
	public int[] 		BlocksW;
	public int[] 		BlocksH;

	/** [layer][y][x] */
	public int[][][] 	TerrainTile;
	public int[][][] 	TerrainFlip;
	public int[][][] 	TerrainFlag;
	
	

	public MapSet(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
	
	
	public int getIndex() {
		return Index;
	}
	
	public String getName() {
		return Name;
	}

}

