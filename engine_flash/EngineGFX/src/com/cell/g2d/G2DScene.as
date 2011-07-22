package com.cell.g2d
{
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphics;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	
	import flash.display.Sprite;

	public class G2DScene extends Sprite
	{
		private var map_view	: CMapView;
		private var cg			: CGraphics;
		
		public function G2DScene(cmap:CMap, windowW:int, windowH:int)
		{
			this.map_view	= new CMapView(cmap, windowW, windowH);
			this.cg 		= new CGraphicsDisplay(graphics);
			repaint();
		}
		
		public function getMapView() : CMapView
		{
			return map_view;
		}
		
		public function getMap() : CMap {
			return map_view.getMap();
		}
		
		public function getCamera() : CCamera {
			return map_view.getCamera();
		}
		
		public function repaint() : void
		{
			this.map_view.render(cg);
		}
	}
}