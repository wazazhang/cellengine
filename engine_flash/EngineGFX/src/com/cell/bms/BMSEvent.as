package com.cell.bms
{
	import flash.events.Event;
	
	public class BMSEvent extends Event
	{
		public static const LOADED : String = "LOADED"; 

		public var loader : BMSLoader;
		
		public function BMSEvent(evt:String) 
		{
			super(evt);
		}
		
		
	}
}