package com.cell.util
{
	import com.cell.gfx.game.Transform;
	import com.cell.ui.Anchor;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.geom.ColorTransform;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.utils.ByteArray;
	
	public class ImageUtil
	{
		
		public static const CHANNEL_ALPHA 	: int = 0;
		public static const CHANNEL_RED 	: int = 1;
		public static const CHANNEL_GREEN 	: int = 2;
		public static const CHANNEL_BLUE 	: int = 3;
		
		/**
		 * 整合所有Bitmap Class
		 * */
		public static function combineImageClass(anchor:int, ... classes) : Bitmap
		{
			var array : Vector.<BitmapData> = new Vector.<BitmapData>();
			for each (var c : Class in classes) {
				array.push((new c() as Bitmap).bitmapData);
			}
			
			var bd : BitmapData = combineImage(anchor, array);
			
			return new Bitmap(bd);
			
		}
		
		/**
		 * 整合所有BitmapData
		 */
		public static function combineImageBitmapData(anchor:int, ... images) : BitmapData
		{
			var array : Vector.<BitmapData> = new Vector.<BitmapData>();
			for each (var c : Class in images) {
				array.push(c);
			}
			return combineImage(anchor, array);
		}
		
		/**
		 * 整合所有BitmapData
		 */
		public static function combineImage(anchor:int, images:Vector.<BitmapData>) : BitmapData
		{
			var drect : Rectangle = new Rectangle(0,0,1,1);
			
			for each (var i : BitmapData in images) {
				drect.width = Math.max(drect.width, i.width);
				drect.height = Math.max(drect.height, i.height);
			}
			
			var ret : BitmapData = new BitmapData(drect.width, drect.height, true, 0);
			
			for each (var o : BitmapData in images) 
			{
				var p : Point = new Point();
				
				if ((anchor & Anchor.ANCHOR_HCENTER)!=0) {
					p.x = ret.width/2 - o.width/2;
				} else if ((anchor & Anchor.ANCHOR_RIGHT)!=0) {
					p.x = ret.width-o.width;
				} else {
					p.x = 0;
				}
				if ((anchor & Anchor.ANCHOR_VCENTER)!=0) {
					p.y = ret.height/2 - o.height/2;
				} else if ((anchor & Anchor.ANCHOR_BOTTOM)!=0) {
					p.y = ret.height-o.height;
				} else {
					p.y = 0;
				}
				
				ret.copyPixels(o, 
					new Rectangle(0,0,o.width,o.height), 
					p, 
					null,
					null, 
					true);
			}
			
			return ret;
		}
		
		public static function subImage(src:BitmapData, x:int, y:int, w:int, h:int) : BitmapData
		{
			var ret : BitmapData = new BitmapData(w, h, src.transparent);
			ret.copyPixels(src, new Rectangle(x, y, w, h), new Point(0, 0), null, null, false);
			return ret;
		}
		
		public static function colorTransform(src:BitmapData, trans:ColorTransform) : BitmapData
		{
			var ret : BitmapData = subImage(src, 0, 0, src.width, src.height);
			var rect:Rectangle = new Rectangle(0, 0, src.width, src.height);
			ret.colorTransform(rect, trans);
			return ret;
		}
		
		/**com.cell.gfx.game.Transform*/
		public static function transformImage(src:BitmapData, transform:int) : BitmapData
		{
			var w : int = src.width;
			var h : int = src.height;
			
			switch(transform) {
				case Transform.TRANS_NONE:
				case Transform.TRANS_180:
				case Transform.TRANS_H:
				case Transform.TRANS_H180:
					break;
				case Transform.TRANS_90:
				case Transform.TRANS_270:
				case Transform.TRANS_H90:
				case Transform.TRANS_H270:	
					w = src.height;
					h = src.width;
					break;
				default:
					throw new Error("error Transform value : " + transform);
			}
			
			var ret : BitmapData = new BitmapData(w, h, src.transparent, 0xff00ff);
			
			ret.draw(src, Transform.getMatrix(0,0,w,h,transform));
			
			return ret;
		}
		
		/**
		 * 将一幅图片的所有像素设置成灰度
		 * @param src
		 * @param channel (1=red, 2=green, 3=blue)
		 * @return 返回原图
		 */
		public static function toTurngrey(src:BitmapData, channel:uint) : BitmapData
		{
			var rgb : uint;
			var a : uint;
			var r : uint;
			var g : uint;
			var b : uint;
			var cc : uint;
			
			var w : uint = src.width;
			var h : uint = src.height;
			
			var ret : BitmapData = new BitmapData(src.width, src.height, src.transparent)
			
			for (var x : int = w - 1; x >= 0; x--)
			{
				for (var y : int = h - 1; y >= 0; y--)
				{
					rgb = src.getPixel32(x, y);
					
					a = (rgb & 0xff000000) >>> 24;
					
					if (a != 0) 
					{
						r = (rgb & 0x00ff0000) >>> 16;
						g = (rgb & 0x0000ff00) >>> 8;
						b = (rgb & 0x000000ff);
						cc = a;
						switch(channel) {
						case CHANNEL_RED:
							cc= r; break;
						case CHANNEL_GREEN:
							cc= g; break;
						case CHANNEL_BLUE: 
							cc= b; break;
						}
						r = cc;
						g = cc;
						b = cc;
						rgb = (a << 24) | (r << 16) | (g << 8) | b;
					}
					ret.setPixel32(x, y, rgb);
				}
			}
			
			return ret;
		}

	}
}