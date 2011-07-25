package com.cell.gfx
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CGameScene;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;
	import flash.geom.Vector3D;

	public class CellSprite extends Sprite implements IImageObserver
	{
		public static var DEBUG : Boolean = true;
		
		private var spr		: CSprite;
		private var cg		: IGraphics;
		private var pos		: Vector3D;
		
		public function CellSprite(spr:CSprite)
		{
			this.spr = spr;
			this.cg = new CGraphicsDisplay(graphics);			
			this.repaint();
			this.spr.getAnimates().getImages().addImageObserver(this);
		}
		
		public function get src() : CSprite
		{
			return spr;
		}
		
		public function getCSprite() : CSprite
		{
			return spr;
		}
		
		public function repaint() : void
		{
			graphics.clear();
			spr.render(cg, 0, 0, spr.getCurrentAnimate(), spr.getCurrentFrame());
			graphics.moveTo( 0, 0);
			if (DEBUG) {
				graphics.lineStyle(1, 0xffff0000, 1);
				graphics.lineTo(-8, 0); graphics.moveTo( 0, 0);
				graphics.lineTo( 8, 0); graphics.moveTo( 0, 0);
				graphics.lineTo( 0,-8); graphics.moveTo( 0, 0);
				graphics.lineTo( 0, 8); graphics.moveTo( 0, 0);
				graphics.endFill();
			}
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
//			trace(e.type + " : " + e.images_set.Name);
			repaint();
		}
		
	}
}