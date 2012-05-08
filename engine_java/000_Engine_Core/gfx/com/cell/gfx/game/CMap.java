
package com.cell.gfx.game;

import java.io.Serializable;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;




/**
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CMap extends CUnit implements Serializable
{
	private static final long serialVersionUID = 1L;
	
//	----------------------------------------------------------------------------------------------

	protected int CellW;
	protected int CellH;

	protected IImages		Tiles;
	protected CCollides 	Collides;
	
	protected int[][][] MatrixTile;
	protected int[][][] MatrixFlip;
	protected int[][][] MatrixFlag;
	
	protected boolean IsCyc 	= false;

	protected int Width;
	protected int Height;
//	----------------------------------------------------------------------------------------------

	public CMap(
			IImages tiles, 
			CCollides collides,
			int cellw, 
			int cellh,
			int[][][] tile_matrix, 
			int[][][] flip_matrix,
			int[][][] flag_matrix,
			boolean isCyc
			) {
		IsCyc 		= isCyc;
		
		Tiles 		= tiles;
		Collides 	= collides;
		MatrixTile 	= tile_matrix;
		MatrixFlip 	= flip_matrix;
		MatrixFlag 	= flag_matrix;
		
		CellW = cellw;
		CellH = cellh;
		
		Width 	= MatrixTile[0].length * CellW;
		Height	= MatrixTile.length * CellH;
	}
	
	public CMap(CMap map,boolean isCyc)
	{
		IsCyc 		= isCyc;
		
		Tiles 		= map.Tiles;
		Collides 	= map.Collides;
		MatrixTile 	= map.MatrixTile;
		MatrixFlip 	= map.MatrixFlip;
		MatrixFlag 	= map.MatrixFlag;
		
		CellW = map.CellW;
		CellH = map.CellH;
		
		Width 	= MatrixTile[0].length * CellW;
		Height 	= MatrixTile.length * CellH;
	}

//	----------------------------------------------------------------------------------------------------------------
	
	public IImages getTiles() {
		return Tiles;
	}
	
	/**
	 * before added world call
	 * @param cyc
	 */
	public void setCyc(boolean cyc) {
		IsCyc = cyc;
	}
	
	public int getLayerCount() {
		return MatrixTile.length;
	}	
	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}

	public int getXCount() {
		return MatrixTile[0].length;
	}

	public int getYCount() {
		return MatrixTile.length;
	}

	public int getCellW() {
		return CellW;
	}

	public int getCellH() {
		return CellH;
	}

	public CCD getCD(int layer, int bx, int by) {
		return Collides.getCD(MatrixFlag[layer][bx][by]);
	}

	public IImage getImage(int layer, int bx, int by) {
		return Tiles.getImage(MatrixTile[layer][bx][by]);
	}

	public int getFlag(int layer, int bx, int by) {
		return MatrixFlag[layer][bx][by];
	}

	public int getTile(int layer, int bx, int by) {
		return MatrixTile[layer][bx][by];
	}

	public void putFlag(int layer, int bx, int by, int data) {
		MatrixFlag[layer][by][bx] = data;
	}
	//	-------------------------------------------------------------------------------

	public void renderCell(IGraphics g, int layer, int x, int y, int cellX, int cellY)
	{
		Tiles.render(g, 
				MatrixTile[layer][cellX][cellY], 
				x, y,
				MatrixFlip[layer][cellX][cellY]);
	}
	
	public void renderCell(IGraphics g, int x, int y, int cellX, int cellY)
	{
		for (int layer=0; layer<MatrixTile.length; layer++) {
			Tiles.render(g, 
					MatrixTile[layer][cellX][cellY], 
					x, y,
					MatrixFlip[layer][cellX][cellY]);
		}
	}
}