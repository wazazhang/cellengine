package com.cell.gfx
{
	import flash.display.Graphics;
	import flash.geom.Matrix;

	public class CGraphics
	{
		static public const ANGLE_90		: Number = Math.PI / 2;
		static public const ANGLE_270		: Number = Math.PI * 3 / 2;
		
		/** None Flip Rotate */
		static public const TRANS_NONE 		: int = 0;
		
		/** Flip Vertical */
		static public const TRANS_V 		: int = 1;
		
		/** Flip Horizental */
		static public const TRANS_H 		: int  = 2;
		
		/** Flip Horizental and Vertical */
		static public const TRANS_HV		: int = 3;
		
		/** anticlockwise rotate 90 angle */
		static public const TRANS_90 		: int = 6;
		
		/** anticlockwise rotate 270 angle */
		static public const TRANS_270 		: int = 5;
		
		/** first anticlockwise rotate 90 angle , second flip Horizental */
		static public const TRANS_H90 		: int = 4;
		
		/** first anticlockwise rotate 90 angle , second flip Vertical */
		static public const TRANS_V90 		: int = 7;
		
		/** 180 Rotate */
		static public const TRANS_180 		: int = 3; 
		

//		-------------------------------------------------------------------------------------------------
		
		private var g : Graphics;
		
		public function CGraphics(g:Graphics)
		{
			this.g = g;
		}
		
		function getMatrix(transform:int) : Matrix
		{
			switch (transform) {
				case TRANS_NONE:
					break;
				case TRANS_90:
					break;
				case TRANS_180:
					break;
				case TRANS_270:
					break;
				case TRANS_H:
					break;
				case TRANS_V:
					break;
				case TRANS_HV:
					break;
				case TRANS_H90:
					break;
				case TRANS_V90:
					break;
			}
			return null;
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
			g.beginBitmapFill(img.src, getMatrix(transform), false, false);
			g.drawRect(x, y, img.width, img.height);
			g.endFill();
		}
		
		/**
		 * 绘制指定图像中已缩放到适合指定矩形内部的图像。 
		 * 图像绘制在此图形上下文坐标空间的指定矩形内部，如果需要，则进行缩放。
		 * @param img 要绘制的指定图像。
		 * @param x x 坐标。
		 * @param y y 坐标。
		 * @param w 矩形的宽度。
		 * @param h 矩形的高度。
		 * @param transform 翻转方式
		 */
		public function drawImageScale(img:CImage, x:int, y:int, w:int, h:int, transform:int) : void
		{
			g.beginBitmapFill(img.src, getMatrix(transform), false, false);
			g.drawRect(x, y, w, h);
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
		
		/**
		 * 平铺绘制图片到指定范围。
		 * 如果目标范围比原图片大，则按顺序平铺。
		 * @param img 要绘制的指定图像。
		 * @param x 目标X坐标。
		 * @param y 目标Y坐标。
		 * @param width 目标矩形宽度。
		 * @param height 目标矩形高度。
		 * @param transform 翻转方式
		 */
		public function drawImageRound(img:CImage, x:int, y:int, width:int, height:int, transform:int) : void
		{
			
		}
		
		//	---------------------------------------------------------------------------------------------------------------------
		//	geometry
		
		
		
	}
}