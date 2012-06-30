package com.cell.ui.edit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IImages;
	
	import flash.events.Event;
	
	public class UIEditEvent extends Event
	{
		public static const LOADED 			: String = "LOADED"; 
		public static const ERROR				: String = "ERROR";
		
		public var cause 			: Event;
		public var edit 			: UIEdit;
		public var loader 			: UIEditLoader;
		
		public function UIEditEvent(evt:String, loader:UIEditLoader, edit:UIEdit, cause:Event) 
		{
			super(evt);
			this.loader = loader;
			this.edit = edit;
			this.cause = cause;
		}
		
		override public function clone() : Event
		{
			var ret : UIEditEvent = new UIEditEvent(type, loader, edit, cause);
			return ret;
		}
	}
}