package com.cell.g2d
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;

	public class G2DScene extends Sprite implements IImageObserver
	{
		private var map_view	: CMapView;
		private var cg			: IGraphics;
		
		public function G2DScene(cmap:CMap, windowW:int, windowH:int)
		{
			this.map_view	= new CMapView(cmap, windowW, windowH);
			this.cg 		= new CGraphicsDisplay(graphics);
			this.repaint();		
			this.map_view.getMap().getAnimates().getImages().addImagesLoadedListener(this);
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
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
//			trace(e.type + " : " + e.images_set.Name);
			map_view.getCamera().resetBuffer();
			repaint();
		}
	}
}