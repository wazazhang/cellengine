package com.cell.gfx.game
{
	import flash.display.BitmapData;
	import flash.display.Graphics;
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
//				
//				this.dst_rect.x = -x;
//				this.dst_rect.y = -y;
//				var src_ins_rect : Rectangle = intersection(src_rect, dst_rect);
//				
//				this.src_rect.x = 0;
//				this.src_rect.y = 0;
//				this.dst_rect.x = x;
//				this.dst_rect.y = y;
//				var dst_ins_rect : Rectangle = intersection(src_rect, dst_rect);
//				
//				this.dst_point.x = dst_ins_rect.x;
//				this.dst_point.y = dst_ins_rect.y;
//				trace("=======================================");
//				trace("src rect " + src_rect);
//				trace("dst rect " + dst_rect);
//				trace("ins rect " + ins_rect);
//				if (src_ins_rect.width != 0) {
//					buff.copyPixels(img.src, ins_rect, dst_point, null, null ,true);
//					buff.fillRect(new Rectangle(x,y,ins_rect.width, ins_rect.height), 0);
//					buff.copyPixels(img.src, src_ins_rect, dst_point, null, null ,true);
//				}
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
		
		public function intersection(a:Rectangle, r:Rectangle) : Rectangle
		{
			var tx1 : int = a.x;
			var ty1 : int = a.y;
			var rx1 : int = r.x;
			var ry1 : int = r.y;
			var tx2 : int  = tx1; tx2 += a.width;
			var ty2 : int  = ty1; ty2 += a.height;
			var rx2 : int  = rx1; rx2 += r.width;
			var ry2 : int  = ry1; ry2 += r.height;
			if (tx1 < rx1) tx1 = rx1;
			if (ty1 < ry1) ty1 = ry1;
			if (tx2 > rx2) tx2 = rx2;
			if (ty2 > ry2) ty2 = ry2;
			tx2 -= tx1;
			ty2 -= ty1;
			// tx2,ty2 will never overflow (they will never be
			// larger than the smallest of the two source w,h)
			// they might underflow, though...
			if (tx2 < int.MIN_VALUE) tx2 = int.MIN_VALUE;
			if (ty2 < int.MIN_VALUE) ty2 = int.MIN_VALUE;
			return new Rectangle(tx1, ty1, tx2, ty2);
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
		

	}
}