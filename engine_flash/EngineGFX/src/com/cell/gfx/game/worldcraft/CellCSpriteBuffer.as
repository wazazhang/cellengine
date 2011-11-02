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
		
		private var old_anim 	: int = 0;
		private var old_frame 	: int = 0;
		private var _repaint	: Boolean;
		
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
			_repaint = true;
		}
		
		internal override function renderIn() : void 
		{
			var ca : int = _spr.getCurrentAnimate();
			var cf : int = _spr.getCurrentFrame();
			
			if (_repaint || old_anim  != ca || old_frame != cf) 
			{
				var cr : Rectangle	= _buff.getFrameBound(ca, cf);
				var cb : BitmapData	= _buff.getFrameBuffer(ca, cf);
				
				bitmap.x = cr.x;
				bitmap.y = cr.y;
				bitmap.bitmapData = cb;
			}				
			_repaint 	= false;
			old_anim  	= _spr.getCurrentAnimate();
			old_frame 	= _spr.getCurrentFrame();
		}

		protected function get imageBuff() : Bitmap
		{
			return bitmap;
		}

	}
}