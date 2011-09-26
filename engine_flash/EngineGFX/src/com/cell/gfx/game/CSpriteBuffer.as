package com.cell.gfx.game
{	
	import com.cell.util.CMath;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;
	
	public class CSpriteBuffer
	{
		private var spr : CSprite;
		
		private var buffers : Vector.<Vector.<BitmapData>>	= new Vector.<Vector.<BitmapData>>();
		private var bounds 	: Vector.<Vector.<Rectangle>>	= new Vector.<Vector.<Rectangle>>();
		
		public function CSpriteBuffer()
		{
			
		}
		
		public function init(spr : CSprite) : void
		{
			this.spr = spr.copy();
			
			buffers = new Vector.<Vector.<BitmapData>>(spr.getAnimateCount());
			bounds 	= new Vector.<Vector.<Rectangle>>(spr.getAnimateCount());
			
			for (var a=0; a<buffers.length; a++) 
			{
				buffers	[a] = new Vector.<BitmapData>(spr.getFrameCount(a));
				bounds	[a] = new Vector.<Rectangle>(spr.getFrameCount(a));
				
				for (var f=0; f<buffers[a].length; f++) 
				{
					var bound : CCD = spr.getFrameImageBounds(a, f);
					var buff : BitmapData = new BitmapData(bound.getWidth(), bound.getHeight(), true, 0x00000000);
					var bg : CGraphicsBitmap = new CGraphicsBitmap(buff);
					spr.render(bg, -bound.X1, -bound.Y1, a, f);
					
					buffers	[a][f] = (buff);
					bounds	[a][f] = (new Rectangle(bound.X1, bound.Y1, bound.getWidth(), bound.getHeight()));
				}
			}
		}
		
		public function getSrc() : CSprite
		{
			return spr;
		}
		
		public function getFrameBuffer(a:int, f:int) : BitmapData
		{
			return buffers[a][f];
		}
		
		public function getFrameBound(a:int, f:int) : Rectangle
		{
			return bounds[a][f];
		}
		
		public function copy() : CSpriteBuffer
		{
			var ret : CSpriteBuffer = new CSpriteBuffer();
			ret.spr = this.spr.copy();
			ret.buffers = this.buffers;
			ret.bounds = this.bounds;
			return ret;
		}
	}
}



