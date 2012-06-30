package com.cell.gfx.game
{	
	import com.cell.util.Arrays;
	import com.cell.util.CMath;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.geom.Rectangle;
	
	public class CSpriteBuffer
	{
		private var spr : CSprite;
		
		private var fmap	: Vector.<Vector.<int>>;
		
		private var buffers : Vector.<BitmapData>	= new Vector.<BitmapData>();
		private var bounds 	: Vector.<Rectangle>	= new Vector.<Rectangle>();
		
		public function CSpriteBuffer()
		{
			
		}
		
		public function init(spr : CSprite) : void
		{
			this.spr = spr.copy();
			
			this.fmap = new Vector.<Vector.<int>>(spr.getAnimateCount());
			for (var a:int=0; a<fmap.length; a++) {
				this.fmap[a] = new Vector.<int>(spr.getFrameCount(a));
				for (var f:int=0; f<fmap[a].length; f++) {
					this.fmap[a][f] = -1;
				}
			}
			
			for (a=0; a<fmap.length; a++) 
			{
				for (f=0; f<fmap[a].length; f++) 
				{
					var si : int = findSameFrameBuffer(a, f);
					
					if (si < 0) 
					{
						var bound : CCD = spr.getFrameImageBounds(a, f);
						var buff : BitmapData = new BitmapData(
							bound.getWidth(), 
							bound.getHeight(),
							true, 0x00000000);
						var bg : CGraphicsBitmap = new CGraphicsBitmap(buff);
						spr.render(bg, -bound.X1, -bound.Y1, a, f);
						buffers.push(buff);
						bounds.push(new Rectangle(
							bound.X1, bound.Y1, 
							bound.getWidth(), bound.getHeight()));
						bg = null;
						si = buffers.length - 1;
						//trace("gen sprite buffer at ["+a+"]["+f+"] -> " + si);
					}
					else
					{
						//trace("find save sprite buffer at ["+a+"]["+f+"] -> " + si);
					}
					
					fmap[a][f] = si;
				}
			}
		}
		
		private function findSameFrameBuffer(da:int, df:int) : int
		{
			var dfi : int = spr.getFrameAnimateIndex(da, df);
			var sfi : int = -1;
			
			for (var sa:int=0; sa<fmap.length; sa++) 
			{
				for (var sf:int=0; sf<fmap[sa].length; sf++) 
				{
					sfi = spr.getFrameAnimateIndex(sa, sf);
					
					if (dfi == sfi) {
						return fmap[sa][sf];
					}
				}
			}
			
			return -1;
		}
		
		public function getSrc() : CSprite
		{
			return spr;
		}
		
		public function getFrameBuffer(a:int, f:int) : BitmapData
		{
			return buffers[fmap[a][f]];
		}
		
		public function getFrameBound(a:int, f:int) : Rectangle
		{
			return bounds[fmap[a][f]];
		}
		
		public function copy() : CSpriteBuffer
		{
			var ret : CSpriteBuffer = new CSpriteBuffer();
			ret.spr		= this.spr.copy();
			ret.fmap	= this.fmap;
			ret.buffers	= this.buffers;
			ret.bounds	= this.bounds;
			return ret;
		}
	}
}



