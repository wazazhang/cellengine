package com.cell.gfx.game
{
	import flash.display.Graphics;
	import flash.geom.Matrix;

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
		public function drawImage(img:CImage, x:int, y:int, w:int, h:int, transform:int) : void
		{
		
			g.beginFill(0x00ff00, 0.5);
			g.drawRect(x, y, w, h);
			g.endFill();
			
		
			g.beginBitmapFill(img.src, 
				getMatrix(x, y, w, h, transform), 
				false, false);
			g.drawRect(-200,-200,400,400);
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
		
		
		static private var ANGLE_90		: Number = Math.PI / 2;
		static private var ANGLE_180	: Number = Math.PI;
		static private var ANGLE_270	: Number = Math.PI * 3 / 2;
		
		
		static private function getMatrix(x:int, y:int, width:int, height:int, transform:int) : Matrix
		{
			var ret : Matrix = new Matrix();
			switch (transform) {
				case Transform.TRANS_NONE:
					ret.translate(x, y);
					break;
				case Transform.TRANS_90:
					ret.rotate(ANGLE_90);
					ret.translate(width, 0);
					ret.translate(x, y);
					break;
				case Transform.TRANS_180:
					ret.rotate(ANGLE_180);
					ret.translate(x, y);
					break;
				case Transform.TRANS_270:
					ret.rotate(ANGLE_270);
					ret.translate(x, y);
					break;
				
				case Transform.TRANS_H:
					ret.scale(-1, 1);
					ret.translate(width, 0);
					ret.translate(x, y);
					break;
				case Transform.TRANS_H90:
					ret.rotate(Math.PI/2);
					ret.scale(-1, 1);
					ret.translate(x, y);
					break;
				case Transform.TRANS_H180:
					break;
				case Transform.TRANS_H270:
					break;
			}
			return ret;
		}
		
		
	}
}