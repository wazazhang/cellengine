package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.IImages;
	
	import flash.events.Event;
	
	public class ResourceEvent extends Event
	{
		public static const LOADED : String = "LOADED"; 
		
		public static const IMAGES_LOADED : String = "IMAGES_LOADED"; 
		
		public var res 			: ResourceLoader;
		
		public var images 		: IImages;
		
		public var images_set 	: ImagesSet;
		
		public function ResourceEvent(evt:String) 
		{
			super(evt);
		}
		
		
	}
}