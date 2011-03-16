/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.game;


import javax.microedition.lcdui.Graphics;


/**
 * 地图。 用于把地砖拼成完整的地图。</br>
 * 该系统支持动态地表，多层图片，任意的地形（线段，矩形），循环地图。</br>
 * 只有通过摄象机才能把地图绘制到屏幕。
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CMap extends CUnit {

	protected int CellW; //图块宽
	protected int CellH; //图块高
	
	protected CAnimates		Tiles;
	protected CCollides 	Collides;
	
	protected short[][] MatrixTile; //图块TILE号表
	protected short[][] MatrixFlag; //图块碰撞块索引表

	protected boolean IsCyc ;
	protected boolean IsAnimate ;
	
	protected int Width;
	protected int Height;
	//	----------------------------------------------------------------------------------------------

	/**
	 * 构造函数
	 * @param tiles 该地图用到的图片组
	 * @param collides 该地图用到的碰撞块组
	 * @param cellw 单元格宽
	 * @param cellh 单元格高
	 * @param tile_matrix 图片地表
	 * @param flag_matrix 地形表 
	 * @param isAnimate 是否显示动态地表
	 * @param isCyc 是否是循环地图
	 */
	public CMap(
			CAnimates tiles, 
			CCollides collides,
			int cellw, 
			int cellh,
			short[][] tile_matrix, 
			short[][] flag_matrix,
			boolean isAnimate,
			boolean isCyc
			) {
		IsCyc = isCyc;
		IsAnimate = isAnimate;
		
		Tiles = tiles;
		Collides = collides;
		MatrixTile = tile_matrix;
		MatrixFlag = flag_matrix;
		CellW = cellw;
		CellH = cellh;
		
		Width = MatrixTile[0].length * CellW;
		Height = MatrixTile.length * CellH;
	}
	
	/**
	 * 类似 拷贝构造函数
	 * @param map
	 * @param isCyc 
	 */
	public CMap(CMap map,boolean isCyc){
		IsCyc = isCyc;
		IsAnimate = map.IsAnimate;
		
		Tiles = map.Tiles;
		Collides = map.Collides;
		MatrixTile = map.MatrixTile;
		MatrixFlag = map.MatrixFlag;
		CellW = map.CellW;
		CellH = map.CellH;
		
		Width = MatrixTile[0].length * CellW;
		Height = MatrixTile.length * CellH;
	}

	

//	public CMap createMiniMap(int cellW,int cellH,int colorKeyMapPos){
//		CMap ret = new CMap(
//				this.Tiles,
//				this.Collides,
//				cellW,
//				cellH,
//				this.MatrixTile,
//				this.MatrixFlag,
//				this.IsAnimate,
//				this.IsCyc
//		);
//		
//		int[] mapColor = new int[Tiles.getCount()];
//    	for(int i=0;i<mapColor.length;i++){
//    		try {
//    			mapColor[i] = Tiles.images.getRGBFormPixcel(
//    					Tiles.STileID[Tiles.Frames[i][0]], 
//    					colorKeyMapPos
//    					);
//			} catch (RuntimeException e){
//				mapColor[i] = 0xff00ff00;
//			}
//       	}
//    	ret.TilesColor = mapColor;
//		
//		return ret;
//	}
	
	
//	----------------------------------------------------------------------------------------------------------------
	
//	final public boolean isMiniMap(){
//		return TilesColor!=null;
//	}
	
	/**
	 * 得到整个地图的宽
	 * @return 
	 */
	final public int getWidth() {
		return Width;
	}
	/**
	 * 得到整个地图的高
	 * @return 
	 */
	final public int getHeight() {
		return Height;
	}
	/**
	 * 得到横向格子数量
	 * @return 
	 */
	final public int getWCount() {
		return MatrixTile[0].length;
	}
	/**
	 * 得到纵向格子数量
	 * @return 
	 */
	final public int getHCount() {
		return MatrixTile.length;
	}
	/**
	 * 得到单元格宽
	 * @return 
	 */
	final public int getCellW() {
		return CellW;
	}
	/**
	 * 得到单元格高
	 * @return 
	 */
	final public int getCellH() {
		return CellH;
	}

	/**
	 * 根据地图块得到碰撞块
	 * @param bx
	 * @param by
	 * @return 
	 */
	final public CCD getCD(int bx,int by){
		return Collides.getCD(MatrixFlag[by][bx]); 
	}
	
	public int getFlag(int bx,int by){
		return MatrixFlag[by][bx];
	}
	public int getTile(int bx,int by){
		return Math.abs(MatrixTile[by][bx]);
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
	
	/**
	 * 
	 * @param time1
	 * @param time2
	 * @param bx
	 * @param by
	 * @return 
	 */
	protected boolean testSameAnimateTile(int time1,int time2,int bx,int by){
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

	/**
	 * 功能描述
	 * @param g
	 * @param x
	 * @param y
	 * @param cellX
	 * @param cellY 
	 */
	protected void renderCell(Graphics g, int x, int y, int cellX, int cellY) {
		if(MatrixTile[cellY][cellX]>=0){
			Tiles.renderSingle(g, MatrixTile[cellY][cellX], x, y);
		}else{
			Tiles.renderSingleSub(g, -MatrixTile[cellY][cellX], 0, x, y);
		}
//#ifdef _DEBUG
		if(IsDebug && MatrixFlag[cellY][cellX]>0){
			Collides.render(g, MatrixFlag[cellY][cellX], x, y, 0xff00ff00);
		}
//#endif
	}
	
	/**
	 * 功能描述
	 * @param g
	 * @param index
	 * @param x
	 * @param y
	 * @param cellX
	 * @param cellY 
	 */
	protected void renderAnimateCell(Graphics g, int index, int x, int y, int cellX, int cellY){
			if(MatrixTile[cellY][cellX]<0){
				Tiles.renderSingleSub(g, 
						-MatrixTile[cellY][cellX],
						index%Tiles.Frames[-MatrixTile[cellY][cellX]].length,
						x, y);
			}
//	#ifdef _DEBUG
			if(IsDebug && MatrixFlag[cellY][cellX]>0){
				Collides.getCD(MatrixFlag[cellY][cellX]).render(g, x, y, 0xff00ff00);
			}
//	#endif
		
	}

	
	/**
	 * 功能描述
	 * @param g
	 * @param index
	 * @param x
	 * @param y
	 * @param cellX
	 * @param cellY 
	 */
	protected void renderDirectCell(Graphics g, int index, int x, int y, int cellX, int cellY){
			if(MatrixTile[cellY][cellX]<0){
				Tiles.renderSingleSub(g, 
						-MatrixTile[cellY][cellX],
						index%Tiles.Frames[-MatrixTile[cellY][cellX]].length,
						x, y);
			}else{
				Tiles.renderSingle(g, MatrixTile[cellY][cellX], x, y);
			}
//	#ifdef _DEBUG
			if(IsDebug && MatrixFlag[cellY][cellX]>0){
				Collides.getCD(MatrixFlag[cellY][cellX]).render(g, x, y, 0xff00ff00);
			}
//	#endif
		
	}
}