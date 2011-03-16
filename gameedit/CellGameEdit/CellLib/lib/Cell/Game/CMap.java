package Cell.Game;

import javax.microedition.lcdui.Graphics;

/**
 * 地图。 用于把地砖拼成完整的地图。
 * 
 *  
 */
public class CMap extends CUnit {

	protected int CellW; //图块宽
	protected int CellH; //图块高
	
	protected CAnimate		Tiles;
//	protected CCollides 	Collides;
//	protected CAnimate[] 	Animates;
	protected CCD mapcd;
	
	protected short[][] MatrixTile; //图块TILE号表
	protected short[][] MatrixCD; //图块碰撞块索引表
//	protected short[][] MatrixAnimate;
	
	protected boolean IsCyc ;
	protected boolean IsAnimate ;
	//	----------------------------------------------------------------------------------------------

	public CMap(
			CAnimate tiles, 
//			CCollides collides,
			int cellw, 
			int cellh,
			short[][] tile_matrix, 
			short[][] cd_matrix,
//			CAnimate[] animates,
//			short[][] animate_matrix,
			boolean isAnimate,
			boolean isCyc
			) {
		IsCyc = isCyc;
		IsAnimate = isAnimate;
		
		Tiles = tiles;
//		Collides = collides;
		mapcd = CCD.createCDRect(0xffffffff, 0, 0, cellw, cellh);
		MatrixTile = tile_matrix;
		MatrixCD = cd_matrix;
		CellW = cellw;
		CellH = cellh;
		
//		Animates = animates;
//		MatrixAnimate = animate_matrix;
	}

	
	final public int getWidth() {
		return MatrixTile[0].length * CellW;
	}
	final public int getHeight() {
		return MatrixTile.length * CellH;
	}
	final public int getWCount() {
		return MatrixTile[0].length;
	}
	final public int getHCount() {
		return MatrixTile.length;
	}
	final public int getCellWidth() {
		return CellW;
	}
	final public int getCellHeight() {
		return CellH;
	}

	protected boolean testSameAnimateTile(int time1,int time2,int bx,int by){
		if( Tiles.Frames[MatrixTile[by][bx]].length>1 &&
			Tiles.Frames[MatrixTile[by][bx]][time1%Tiles.Frames[MatrixTile[by][bx]].length]==
		    Tiles.Frames[MatrixTile[by][bx]][time2%Tiles.Frames[MatrixTile[by][bx]].length]){
//			println("same at : " + Tiles.Frames[MatrixTile[by][bx]][time1%Tiles.Frames[MatrixTile[by][bx]].length]);
			return true;
		}
		return false;
	}
	//	-------------------------------------------------------------------------------

	protected void renderDirectCell(
			Graphics g, 
			int x, int y, 
			int cellX, int cellY,boolean doAnim) {
		if(IsAnimate){
			if(doAnim==false && Tiles.Frames[MatrixTile[cellY][cellX]].length<2){
				Tiles.render(g, 
						MatrixTile[cellY][cellX],
						x, 
						y);
			}else{
				Tiles.render(g, 
						MatrixTile[cellY][cellX],
						getTimer()%Tiles.Frames[MatrixTile[cellY][cellX]].length,
						x, 
						y);
			}
		}else{
			Tiles.render(g, 
					MatrixTile[cellY][cellX],
					x, 
					y);
		}
	
		
		if(isDebug){
			if(MatrixCD[cellY][cellX]!=0){
				mapcd.render(g, x, y,DebugColor);
			}
		}
	}

	public void render(Graphics g,int x,int y){
		for(int cellY=0;cellY<MatrixTile.length;cellY++){
			for(int cellX=0;cellX<MatrixTile[cellY].length;cellX++){
				renderDirectCell(
						g,x + CellW*cellX ,y + CellH*cellY,cellX,cellY,true);
			}
		}
	}

	
}