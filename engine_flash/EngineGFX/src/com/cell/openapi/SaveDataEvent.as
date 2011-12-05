package com.cell.openapi
{
	import flash.events.Event;
	
	public class SaveDataEvent extends Event
	{
		public static const LOADED 			: String = "LOADED"; 
		public static const SAVED 			: String = "SAVED"; 

		public var data 			: SaveData;
		
		public var error			: Error;
		public var error_source		: Event;
		public var error_message	: String;
		
		
		public function SaveDataEvent(evt:String) 
		{
			super(evt);
		}
		
		public function isError() : Boolean
		{
			return (error!=null) || (error_source!=null) || (error_message!=null) ;
		}
		
		override public function clone() : Event
		{
			var ret : SaveDataEvent = new SaveDataEvent(type);
			ret.data 		  = this.data;
			ret.error		  = this.error;
			ret.error_source  = this.error_source;
			ret.error_message = this.error_message;
			return ret;
		}
	}
}