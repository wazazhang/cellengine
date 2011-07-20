package com.cell.gameedit
{
	import flash.events.Event;
	
	public class ResourceEvent extends Event
	{
		public static const LOADED : String = "LOADED"; 
		
		public var res : ResourceLoader;
		
		public function ResourceEvent(evt:String) 
		{
			super(evt);
		}
		
		
	}
}