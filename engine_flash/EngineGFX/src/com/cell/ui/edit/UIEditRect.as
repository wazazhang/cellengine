package com.cell.ui.edit
{
	import com.cell.ui.layout.UIRect;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;

	public class UIEditRect extends UIRect
	{
		
		public function UIEditRect(src:BitmapData, style:int, clipBorder:int)
		{
			setImagesClip(src, style, clipBorder);
		}
		
		
		
	}
}