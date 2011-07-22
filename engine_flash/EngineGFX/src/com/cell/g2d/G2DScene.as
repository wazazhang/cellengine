package com.cell.g2d
{
	import com.cell.gameedit.ImageObserver;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.IGraphics;
	
	import flash.display.Sprite;

	public class G2DScene extends Sprite implements ImageObserver
	{
		private var map_view	: CMapView;
		private var cg			: IGraphics;
		
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
		
		public function imagesLoaded(res:ResourceLoader, img:ImagesSet) : void
		{
			map_view.getCamera().resetBuffer();
			repaint();
		}
		
	}
}