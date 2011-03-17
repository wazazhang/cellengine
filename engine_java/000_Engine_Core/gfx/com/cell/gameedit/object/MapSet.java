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
	
	/** TImages.Clips*[index] */
	public int[] 		TileID;
	public int[] 		TileTrans;
	/** TileID[index] & TileTrans[index] */
	public int[][] 		Animates;
	/** TerrainScene2D[y][x] == Animates[index] */
	public int[][] 		TerrainScene2D;
	
	
	public int[] 		BlocksType;
	public int[] 		BlocksMask;
	public int[] 		BlocksX1;
	public int[] 		BlocksY1;
	public int[] 		BlocksX2;
	public int[] 		BlocksY2;
	public int[] 		BlocksW;
	public int[] 		BlocksH;
	
	/** TerrainBlock2D[y][x] == BlocksType[index] */
	public int[][]		TerrainBlock2D;
	
	

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

	public int getLayerImagesIndex(int x, int y, int layer){
		return TileID[Animates[TerrainScene2D[y][x]][layer]];
	}
	
	public int getLayerTrans(int x, int y, int layer){
		return TileTrans[Animates[TerrainScene2D[y][x]][layer]];
	}
	
}

