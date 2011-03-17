
package com.cell.gfx.game;

import java.io.Serializable;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;




/**
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CMap extends CUnit implements Serializable
{
	private static final long serialVersionUID = 1L;

	public	int AnimateTimer = 0;
	
//	----------------------------------------------------------------------------------------------

	protected int CellW;
	protected int CellH;

	protected CAnimates		Tiles;
	protected CCollides 	Collides;
	
	protected short[][] MatrixTile;
	protected short[][] MatrixFlag;
	
	protected boolean IsCyc 	= false;
	public boolean IsAnimate 	= false;
	public boolean IsCombo 		= false;
	protected int Width;
	protected int Height;
//	----------------------------------------------------------------------------------------------

	public CMap(
			CAnimates tiles, 
			CCollides collides,
			int cellw, 
			int cellh,
			short[][] tile_matrix, 
			short[][] flag_matrix,
			boolean isCyc
			) {
		IsCyc 		= isCyc;
		
		Tiles 		= tiles;
		Collides 	= collides;
		MatrixTile 	= tile_matrix;
		MatrixFlag 	= flag_matrix;
		
		CellW = cellw;
		CellH = cellh;
		
		Width 	= MatrixTile[0].length * CellW;
		Height	= MatrixTile.length * CellH;
	}
	
	public CMap(CMap map,boolean isCyc){
		IsCyc 		= isCyc;
		IsAnimate 	= map.IsAnimate;
		IsCombo 	= map.IsCombo;
		
		Tiles 		= map.Tiles;
		Collides 	= map.Collides;
		MatrixTile 	= map.MatrixTile;
		MatrixFlag 	= map.MatrixFlag;
		
		CellW = map.CellW;
		CellH = map.CellH;
		
		Width 	= MatrixTile[0].length * CellW;
		Height 	= MatrixTile.length * CellH;
	}

//	----------------------------------------------------------------------------------------------------------------
	
	/**
	 * before added world call
	 * @param cyc
	 */
	public void setCyc(boolean cyc) {
		IsCyc = cyc;
	}
	
//	final public boolean isMiniMap(){
//		return TilesColor!=null;
//	}
	
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

	public CCD getCD(int bx,int by){
		return Collides.getCD(MatrixFlag[by][bx]); 
	}
	
	public IImage getImage(int bx,int by, int layer){
		int id = Math.abs(MatrixTile[by][bx]);
		return Tiles.getFrameImage(id, layer % Tiles.Frames[id].length);
	}
	
	public int getTileLayerCount(int bx,int by){
		int id = Math.abs(MatrixTile[by][bx]);
		return Tiles.Frames[id].length;
	}
	
	public int getFlag(int bx,int by){
		return MatrixFlag[by][bx];
	}
	public int getTile(int bx,int by){
		return Math.abs(MatrixTile[by][bx]);
	}
	public int getTileValue(int bx,int by){
		return MatrixTile[by][bx];
	}
	public void putFlag(int bx,int by,int data){
		MatrixFlag[by][bx] = (short)data;
	}
	public void putTile(int bx,int by,int data){
		MatrixTile[by][bx] = (short)data;
	}
	
	public CAnimates getAnimates(){
		return Tiles;
	}
	
	public CCollides getCollides(){
		return Collides;
	}
	
	public boolean testSameAnimateTile(int time1,int time2,int bx,int by){
		if( MatrixTile[by][bx] >= 0 )return true;
		if( Tiles.Frames[-MatrixTile[by][bx]].length>1 &&
			Tiles.Frames[-MatrixTile[by][bx]][time1%Tiles.Frames[-MatrixTile[by][bx]].length]==
		    Tiles.Frames[-MatrixTile[by][bx]][time2%Tiles.Frames[-MatrixTile[by][bx]].length]){
			//println("same at : " + Tiles.Frames[-MatrixTile[by][bx]][time1%Tiles.Frames[-MatrixTile[by][bx]].length]);
			return true;
		}
		return false;
	}
	//	-------------------------------------------------------------------------------

	public void renderCell(IGraphics g, int x, int y, int cellX, int cellY)
	{
		if(MatrixTile[cellY][cellX]>=0)
		{
			if(IsCombo)
			{
				Tiles.renderSingle(g, MatrixTile[cellY][cellX], x, y);
			}
			else
			{                                      
				int idx = Tiles.Frames[MatrixTile[cellY][cellX]][0];
				Tiles.images.render(g,Tiles.STileID[idx], x , y , Tiles.SFlip[idx]);
			}
		}
		else
		{
			int index = AnimateTimer%Tiles.Frames[-MatrixTile[cellY][cellX]].length;
			int idx = Tiles.Frames[-MatrixTile[cellY][cellX]][index];
			Tiles.images.render(g,Tiles.STileID[idx], x , y , Tiles.SFlip[idx]);
		}
		
//#ifdef _DEBUG
		if(IsDebug && MatrixFlag[cellY][cellX]>0)
		{
			Collides.render(g, MatrixFlag[cellY][cellX], x, y, 0xff00ff00);
		}
//#endif
	}

}