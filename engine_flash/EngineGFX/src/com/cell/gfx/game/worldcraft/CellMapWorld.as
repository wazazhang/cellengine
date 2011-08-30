package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IImageObserver;
	import com.cell.util.CMath;

	public class CellMapWorld extends CellWorld implements IImageObserver
	{
		internal var _cg : CGraphicsDisplay;
		internal var _map : CMap ;
		internal var cellw : int;
		internal var cellh : int;
		internal var xcount : int;
		internal var ycount : int;
		internal var xcount_camera : int;
		internal var ycount_camera : int;
	
		
		
		
		public function CellMapWorld(
			viewWidth:int, 
			viewHeight:int,
			map:CMap)
		{					
			super(viewWidth, viewHeight);
			this._cg = new CGraphicsDisplay(graphics);
			this._map = map;
			this.cellw = map.getCellW();
			this.cellh = map.getCellH();
			this.xcount = map.getXCount();
			this.ycount = map.getYCount();		
			this.xcount_camera = CMath.roundMod(viewWidth,  cellw) + 1;
			this.ycount_camera = CMath.roundMod(viewHeight, cellh) + 1;
			
			repaint();
		}
		
//		override public function render() : void
//		{
//			
//			
//			super.render();
//		}

		public function imagesLoaded(e:ResourceEvent) : void
		{
			repaint();
		}

		public function repaint() : void
		{
			graphics.clear();
			
			var sx : int = CMath.cycMod(_camera.x, cellw);
			var sy : int = CMath.cycMod(_camera.y, cellh);

			var dbx : int = CMath.cycMod(_camera.x, cellw);
			var dby : int = CMath.cycMod(_camera.y, cellh);
			
			var dx : int = dbx * cellw;		
			var dy : int = dby * cellh;

			
			for (var x:int=0; x<xcount_camera; x++)
			{
				for (var y:int=0; y<ycount_camera; y++) 
				{
					_map.renderCell(_cg,
						dx+x*cellw, 
						dy+y*cellh, 
						CMath.cycNum(sx, x, xcount), 
						CMath.cycNum(sy, y, ycount));
//					trace(x, y);
				}
			}
		}
		
		
	}
}