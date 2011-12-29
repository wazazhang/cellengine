package com.cell.ui.layout
{
	import com.cell.gfx.CellScreenManager;
	
	import flash.display.DisplayObject;
	import flash.text.TextFormat;

	public class DefaultLayoutManager extends UILayoutManager
	{
		public function DefaultLayoutManager(
			s:CellScreenManager, 
			width:int,
			height:int)
		{
			super(s, width, height);
		}
		
		
		override public function createUI(key:String, owner:*) : UIRect
		{
			return null;
		}
		
		override public function createTextFormat(key:String, owner:*) : TextFormat
		{
			return new TextFormat("Verdana", 12, 0);
		}
		
		override public function createButton(key:String, owner:*) : DisplayObject
		{
			return null;
		}
		
	}
}