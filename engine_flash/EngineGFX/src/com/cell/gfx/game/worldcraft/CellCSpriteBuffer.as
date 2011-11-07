package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.IGraphics;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public class CellCSpriteBuffer extends CellCSprite
	{
		private var _buff		: CSpriteBuffer;
		private var bitmap		: Bitmap;
		
		public function CellCSpriteBuffer(spr:CSpriteBuffer)
		{
			this._spr	= spr.getSrc();
			this._buff 	= spr;
			this.bitmap	= new Bitmap();
			this.repaint();
			this.mouseEnabled = false;
			this.mouseChildren = false;
			this.addChild(bitmap);
		}
		
		public function repaint() : void
		{
		}
		
		internal override function renderIn() : void 
		{
			var ca : int = _spr.getCurrentAnimate();
			var cf : int = _spr.getCurrentFrame();
			var cr : Rectangle	= _buff.getFrameBound(ca, cf);
			var cb : BitmapData	= _buff.getFrameBuffer(ca, cf);
			
			render(cb, ca, cf, cr.x, cr.y) ;
		}

		public function get spriteBuff() : CSpriteBuffer
		{
			return _buff;
		}
		
		protected function get imageBuff() : Bitmap
		{
			return bitmap;
		}
		
		protected function render(buff:BitmapData, anim:int, frame:int, x:Number, y:Number) : void 
		{
			bitmap.x = x;
			bitmap.y = y;
			bitmap.bitmapData = buff;
		}
	}
}