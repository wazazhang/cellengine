package com.cell.ui.edit
{
	import com.cell.ui.layout.UIRect;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;

	public class UIEditRect extends UIRect
	{
		protected var src:Loader;
		
		protected var swidth:int;
		protected var sheight:int;
		protected var sstyle:int;
		
		public function UIEditRect(style:int, src:Loader, clipBorder:int, width:int, height:int)
		{
			super(IMAGE_STYLE_NULL, 0);
			this.sstyle			= style;
			this.src   			= src;
			this.clipBorder 	= clipBorder;
			this.swidth		 	= width;
			this.sheight		= height;
			
			if (style == IMAGE_STYLE_NULL || style == IMAGE_STYLE_COLOR) {
				
			}
			else {
				if (src.content is Bitmap)
				{
					this.style = sstyle;
					setImagesClip((src.content as Bitmap).bitmapData, this.style, clipBorder);
				}
				else
				{
					src.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
				}
			}
		
		}
		
		public function onComplete(e:Event) : void
		{
			this.style = sstyle;
			setImagesClip((src.content as Bitmap).bitmapData, getStyle(), clipBorder);
			src.contentLoaderInfo.removeEventListener(Event.COMPLETE, onComplete);
			resetBuffer(swidth, sheight);
		}
		
	}
}