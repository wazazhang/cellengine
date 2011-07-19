package com.cell.gfx.game
{
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CImage;
	import com.cell.gfx.game.CSprite;
	
	import flash.display.Sprite;
	
	/**
	 * @author yifeizhang
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CMap
	{
	//	----------------------------------------------------------------------------------------------
		
		protected var animateTimer	: int = 0;
		
		protected var cellW 		: int;
		protected var cellH 		: int;
	
		protected var tiles			: CAnimates;
		protected var collides		: CCollides;
		
		/**short[][]*/
		protected var matrixTile	: Array;
		/**short[][]*/
		protected var matrixFlag	: Array;
		
		protected var isCyc 		: Boolean = false;
		protected var isAnimate 	: Boolean = false;
		protected var isCombo 		: Boolean = false;
		
		protected var width 		: int;
		protected var height 		: int;
		
	//	----------------------------------------------------------------------------------------------
	
		public function CMap(
				tiles : CAnimates, 
				collides : CCollides,
				cellw : int, 
				cellh : int,
				tile_matrix : Array, 
				flag_matrix : Array,
				isCyc : Boolean) 
		{
			this.isCyc 		= isCyc;
			
			this.tiles 		= tiles;
			this.collides 	= collides;
			this.matrixTile	= tile_matrix;
			this.matrixFlag	= flag_matrix;
			
			this.cellW		= cellw;
			this.cellH		= cellh;
			
			this.width		= matrixTile[0].length * cellW;
			this.height		= matrixTile.length * cellH;
		}
			
	//	----------------------------------------------------------------------------------------------------------------
		
		/**
		 * before added world call
		 * @param cyc
		 */
		public function setCyc(cyc:Boolean) : void {
			this.isCyc = cyc;
		}
		
		public function isCycMap() : Boolean {
			return this.isCyc;
		}
		
		public function getAnimateTimer() : int {
			return animateTimer;
		}
		
	//	final public boolean isMiniMap(){
	//		return TilesColor!=null;
	//	}
		
		public function isEnableAnimate() : Boolean
		{
			return isAnimate;
		}
		
		public function getWidth() : int {
			return width;
		}
	
		public function getHeight() : int {
			return height;
		}
	
		public function getXCount() : int {
			return matrixTile[0].length;
		}
	
		public function getYCount() : int {
			return matrixTile.length;
		}
	
		public function getCellW() : int {
			return cellW;
		}
	
		public function getCellH() : int {
			return cellH;
		}
	
		public function getCD(bx:int, by:int) : CCD {
			return collides.getCD(matrixFlag[by][bx]); 
		}
		
		public function getImage(bx:int, by:int, layer:int) : CImage {
			var id : int = Math.abs(matrixTile[by][bx]);
			return tiles.getFrameImage(id, layer % tiles.Frames[id].length);
		}
		
		public function getTileLayerCount(bx:int, by:int) : int {
			var id : int = Math.abs(matrixTile[by][bx]);
			return tiles.Frames[id].length;
		}
		
		public function getFlag(bx:int, by:int) : int {
			return matrixFlag[by][bx];
		}
		public function getTile(bx:int, by:int) : int {
			return Math.abs(matrixTile[by][bx]);
		}
		public function getTileValue(bx:int, by:int) : int {
			return matrixTile[by][bx];
		}

		public function getAnimates() : CAnimates {
			return tiles;
		}
		
		public function getCollides() : CCollides {
			return collides;
		}
		
		public function testSameAnimateTile(time1:int, time2:int, bx:int, by:int) : Boolean {
			if( matrixTile[by][bx] >= 0 )return true;
			if( tiles.Frames[-matrixTile[by][bx]].length>1 &&
				tiles.Frames[-matrixTile[by][bx]][time1%tiles.Frames[-matrixTile[by][bx]].length]==
			    tiles.Frames[-matrixTile[by][bx]][time2%tiles.Frames[-matrixTile[by][bx]].length]){
				//println("same at : " + Tiles.Frames[-MatrixTile[by][bx]][time1%Tiles.Frames[-MatrixTile[by][bx]].length]);
				return true;
			}
			return false;
		}
		//	-------------------------------------------------------------------------------
	
		public function renderCell(g:CGraphics, x:int, y:int, cellX:int, cellY:int) : void
		{
			if(matrixTile[cellY][cellX]>=0)
			{
				if(isCombo)
				{
					tiles.renderSingle(g, matrixTile[cellY][cellX], x, y);
				}
				else
				{                                      
					var idx : int = tiles.Frames[matrixTile[cellY][cellX]][0];
					tiles.renderTile(g, idx, x, y);
				}
			}
			else
			{
				var index : int = animateTimer%tiles.Frames[-matrixTile[cellY][cellX]].length;
				var idx : int = tiles.Frames[-matrixTile[cellY][cellX]][index];
				tiles.renderTile(g, idx, x, y);
			}
		}
	
	}
}