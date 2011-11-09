package com.cell.gfx.game
{
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
		protected var layerCount	: int;
		protected var xCount		: int;
		protected var yCount		: int;
		
		protected var cellW 		: int;
		protected var cellH 		: int;
	
		protected var tiles			: CAnimates;
		protected var collides		: CCollides;
		
		/**short[][][]*/
		protected var LayersTile	: Array;
		/**short[][][]*/
		protected var LayersFlag	: Array;
	
		protected var width 		: int;
		protected var height 		: int;
		
		protected var isCyc 		: Boolean = false;
		protected var isAnimate 	: Boolean = false;
		protected var isCombo 		: Boolean = false;
		
		
		
	//	----------------------------------------------------------------------------------------------
	
		protected function init(
				layerCount:int,
				tiles : CAnimates, 
				collides : CCollides,
				xcount : int,
				ycount : int,
				cellw : int, 
				cellh : int,
				tile_layers : Array, 
				flag_layers : Array) : void
		{
			this.isCyc 		= false;
			
			this.tiles 		= tiles;
			this.collides 	= collides;
			
			this.layerCount	= layerCount;
			this.LayersTile	= tile_layers;
			this.LayersFlag	= flag_layers;
			
			this.xCount		= xcount;
			this.yCount		= ycount;
			this.cellW		= cellw;
			this.cellH		= cellh;
			
			this.width		= xcount * cellW;
			this.height		= ycount * cellH;
		}
			
		public function copy() : CMap
		{
			var ret : CMap = new CMap();
			ret.init(layerCount, tiles, collides, xCount, yCount, cellW, cellH, LayersTile, LayersFlag);
			ret.isCyc 		= this.isCyc;
			ret.isAnimate 	= this.isAnimate;
			ret.isCombo 	= this.isCombo;
			return ret;
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
			return xCount;
		}
	
		public function getYCount() : int {
			return yCount;
		}
	
		public function getCellW() : int {
			return cellW;
		}
	
		public function getCellH() : int {
			return cellH;
		}
	
		public function getCD(layer:int, bx:int, by:int) : CCD {
			return collides.getCD(LayersFlag[layer][by][bx]); 
		}
		
		public function getImage(layer:int, bx:int, by:int, part:int) : CImage {
			var id : int = Math.abs(LayersTile[layer][by][bx]);
			return tiles.getFrameImage(id, part % tiles.Frames[id].length);
		}
		
		public function getTilePartCount(layer:int, bx:int, by:int) : int {
			var id : int = Math.abs(LayersTile[layer][by][bx]);
			return tiles.Frames[id].length;
		}
		
		public function getFlag(layer:int, bx:int, by:int) : int {
			return LayersFlag[layer][by][bx];
		}
		public function getTile(layer:int, bx:int, by:int) : int {
			return Math.abs(LayersTile[layer][by][bx]);
		}
		public function getTileValue(layer:int, bx:int, by:int) : int {
			return LayersTile[layer][by][bx];
		}

		public function getAnimates() : CAnimates {
			return tiles;
		}
		
		public function getCollides() : CCollides {
			return collides;
		}
	
		//	-------------------------------------------------------------------------------
	
		public function renderCell(g:IGraphics, x:int, y:int, cellX:int, cellY:int) : void
		{
			for (var L:int = 0; L<layerCount; L++) 
			{
				var tileID : int = LayersTile[L][cellY][cellX];
				
				if(tileID>=0)
				{
//					if(isCombo)
//					{
//						tiles.renderSingle(g, tileID, x, y);
//					}
//					else
//					{
						tiles.renderTile(g, tiles.Frames[tileID][0], x, y);
//					}
				}
				else
				{
					var index : int = animateTimer%tiles.Frames[-tileID].length;
					tiles.renderTile(g, tiles.Frames[-tileID][index], x, y);
				}
			}
			
		}
	
	}
}