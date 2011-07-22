package com.cell.gfx.game
{
	import flash.display.BitmapData;
	import flash.display.Graphics;

	public class CGraphicsBitmap implements CGraphics
	{
		private var buff : BitmapData;
		
		public function CGraphicsBitmap(buff : BitmapData)
		{
			this.buff = buff;
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
			buff.draw(img.src, 
				null, 
				null, 
				null, 
				null, 
				false);
		}
		
		/**
		 * 绘制指定图像中的一部分。 
		 * @param src 要绘制的指定图像。
		 * @param x_src 原图片部分的X位置
		 * @param y_src 原图片部分的Y位置
		 * @param width 原图片矩形的宽度。
		 * @param height 原图片矩形的高度。
		 * @param transform 翻转方式
		 * @param x_dest 目标X坐标。
		 * @param y_dest 目标Y坐标。
		 */
		public function drawImageRegion(src:CImage,
										x_src:int,
										y_src:int,
										width:int, 
										height:int, 
										transform:int, 
										x_dest:int, 
										y_dest:int) : void
		{
			
		} 
		

	}
}