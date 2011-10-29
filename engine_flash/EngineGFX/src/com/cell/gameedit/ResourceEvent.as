package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IImages;
	
	import flash.events.Event;
	
	public class ResourceEvent extends Event
	{
		public static const LOADED 			: String = "LOADED"; 
//		public static const PROGRESS 		: String = "PROGRESS";
		public static const ERROR			: String = "ERROR";
//		public static const IMAGES_LOADED 	: String = "IMAGES_LOADED"; 
//		public static const IMAGES_PROGRESS	: String = "IMAGES_PROGRESS"; 
		
		
		public var res 			: ResourceLoader;
		
		public var error_source		: Event;
//		public var images 		: IImages;
		
//		public var images_set 	: ImagesSet;
		
		public function ResourceEvent(evt:String) 
		{
			super(evt);
		}
		
		override public function clone() : Event
		{
			var ret : ResourceEvent = new ResourceEvent(type);
			ret.res = res;
			ret.error_source = error_source;
			return ret;
		}
	}
}