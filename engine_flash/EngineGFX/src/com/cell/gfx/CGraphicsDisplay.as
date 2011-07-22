package com.cell.gfx
{
	import flash.display.Graphics;

	public class CGraphicsDisplay implements CGraphics
	{
		private var g : Graphics;
		
		public function CGraphicsDisplay(g:Graphics)
		{
			this.g = g;
		}
		
		/**
		 * 绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。
		 * @param img 要绘制的指定图像。
		 * @param x x 坐标。
		 * @param y y 坐标。
		 * @param transform 翻转方式
		 */
		public function drawImage(img:CImage, x:int, y:int, transform:int) : void
		{
			g.beginBitmapFill(img.src, 
				Transform.getMatrix(x, y, img.width, img.height, transform), 
				false, false);
			g.drawRect(x, y, img.width, img.height);
			g.endFill();
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
		public function drawImageRegion(img:CImage,
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