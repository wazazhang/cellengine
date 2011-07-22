package com.cell.gfx.game
{
	import flash.display.Graphics;
	import flash.geom.Matrix;

	public interface IGraphics
	{
		/**
		 * 绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。
		 * @param img 要绘制的指定图像。
		 * @param x x 坐标。
		 * @param y y 坐标。
		 * @param transform 翻转方式
		 */
		function drawImage(img:CImage, x:int, y:int, w:int, h:int, transform:int) : void;
		
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
		function drawImageRegion(src:CImage,
								   x_src:int,
								   y_src:int,
								   width:int, 
								   height:int, 
								   transform:int, 
								   x_dest:int, 
								   y_dest:int) : void;
		
//		/**
//		 * 平铺绘制图片到指定范围。
//		 * 如果目标范围比原图片大，则按顺序平铺。
//		 * @param img 要绘制的指定图像。
//		 * @param x 目标X坐标。
//		 * @param y 目标Y坐标。
//		 * @param width 目标矩形宽度。
//		 * @param height 目标矩形高度。
//		 * @param transform 翻转方式
//		 */
//		function drawImageRound(img:CImage, x:int, y:int, width:int, height:int, transform:int) : void;
		
	
		
		
		
	}
}