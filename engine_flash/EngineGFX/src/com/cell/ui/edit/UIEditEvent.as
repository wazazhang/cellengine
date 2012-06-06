package com.cell.ui.edit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IImages;
	
	import flash.events.Event;
	
	public class UIEditEvent extends Event
	{
		public static const LOADED 	: String = "LOADED"; 
		public static const ERROR		: String = "ERROR";
		
		
		public var edit : UIEdit;
		
		public var cause : Event;

		
		public function UIEditEvent(evt:String) 
		{
			super(evt);
		}
		
		override public function clone() : Event
		{
			var ret : UIEditEvent = new UIEditEvent(type);
			ret.edit = edit;
			ret.cause = cause;
			return ret;
		}
	}
}