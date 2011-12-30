package com.cell.gfx.game
{
	import flash.display.BitmapData;
	import flash.display.Graphics;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	public class CGraphicsBitmap implements IGraphics
	{
		private var buff : BitmapData;
		
		private var dst_rect : Rectangle;		
		private var src_rect : Rectangle;
		private var dst_point : Point;
		
		public function CGraphicsBitmap(buff : BitmapData)
		{
			this.buff = buff;
			this.src_rect = new Rectangle(0, 0, buff.width, buff.height);
			this.dst_rect = new Rectangle(0, 0, buff.width, buff.height);
			this.dst_point = new Point(0, 0);
		}
		
		/**
		 * 绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。
		 * @param img 要绘制的指定图像。
		 * @param x x 坐标。
		 * @param y y 坐标。
		 * @param transform 翻转方式
		 */
		public function drawImage(img:CImage, x:int, y:int, w:int, h:int, transform:int) : void
		{
			if (transform == Transform.TRANS_NONE) 
			{				
				this.dst_point.x = x;
				this.dst_point.y = y;

				this.src_rect.width = w;
				this.src_rect.height = h;
				this.src_rect.x = 0;
				this.src_rect.y = 0;
				buff.copyPixels(img.src, src_rect, dst_point, null, null, img.src.transparent);
			}
			else 
			{
				this.dst_rect.x = x;
				this.dst_rect.y = y;			
				this.dst_rect.width = w;
				this.dst_rect.height = h;
				
				buff.draw(img.src, Transform.getMatrix(x, y, w, h, transform), null, null, dst_rect, false);
			}
		}
		
		
		/**
		 * 绘制指定图像中的一部分。 
		 * @param src 要绘制的指定图像。
		 * @param x_src 原图片部分的X位置
		 * @param y_src 原图片部分的Y位置
		 * @param width 原图片矩形的宽度。
		 * @param height 原图片矩形的高度。
		 * @param x_dest 目标X坐标。
		 * @param y_dest 目标Y坐标。
		 */
		public function drawImageRegion(src:CImage,
										x_src:int,
										y_src:int,
										width:int, 
										height:int, 
										x_dest:int, 
										y_dest:int) : void
		{
			this.src_rect.x = x_src;
			this.src_rect.y = y_src;
			this.src_rect.width = width;
			this.src_rect.height = height;
			
			this.dst_point.x = x_dest;
			this.dst_point.y = y_dest;
			
			buff.copyPixels(src.src, src_rect, dst_point, null, null, src.src.transparent);
		} 
		
//		-------------------------------------------------------------------------------------------------------------------
		
		public function drawBitmapData(img:BitmapData, x:int, y:int, megre:Boolean) : void
		{
			drawBitmapDataRegion(img, x, y, img.width, img.height, megre);
		}
		
		public function drawBitmapDataRegion(img:BitmapData, x:int, y:int, w:int, h:int, megre:Boolean) : void
		{
			this.dst_point.x = x;
			this.dst_point.y = y;
			this.src_rect.width = w;
			this.src_rect.height = h;
			this.src_rect.x = 0;
			this.src_rect.y = 0;
			buff.copyPixels(img, src_rect, dst_point, null, null, megre);
		}
		
		public function drawBitmapDataRound(img:BitmapData, x:int, y:int, rw:int, rh:int, megre:Boolean) : void
		{
			var sw : int = img.width;
			var sh : int = img.height;
			var sx : int = x;
			var sy : int = y;
			for (var ix:int = 0; ix<rw; ix+=sw) {
				for (var iy:int = 0; iy<rh; iy+=sh) {
					sx = ix + x;
					sy = iy + y;
					drawBitmapDataRegion(img, sx, sy, sw, sh, megre);
				}
			}
		}
		
		public function drawBitmapDataRoundH(img:BitmapData, x:int, y:int, rw:int, rh:int) : void
		{
			var sw : int = img.width;
			var sx : int = x;
			for (var ix:int = 0; ix<rw; ix+=sw) {
				sx = ix + x;
				if (img.height != rh) {
					drawBitmapDataScale(img, sx, y, sw, rh);
				} else {
					drawBitmapData(img, sx, y, false);
				}
			}
		}
		
		public function drawBitmapDataRoundV(img:BitmapData, x:int, y:int, rw:int, rh:int) : void
		{
			var sh : int = img.height;
			var sy : int = y;
			for (var iy:int = 0; iy<rh; iy+=sh) {
				sy = iy + y;
				if (img.width != rw) {
					drawBitmapDataScale(img, x, sy, rw, sh);
				} else {
					drawBitmapData(img, x, sy, false);
				}
			}
		}
		
		public function drawBitmapDataScale(img:BitmapData, x:int, y:int, rw:Number, rh:Number) : void
		{
			var matrix : Matrix = new Matrix();
			matrix.translate(x, y);
			matrix.scale(rw/img.width, rh/img.height);
			buff.draw(img, matrix, null, null, null, true);
			matrix = null;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}