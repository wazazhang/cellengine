package com.cell.openapi
{
	import flash.events.Event;
	import flash.events.EventDispatcher;

	public class TopPlayersEvent extends Event
	{
		public static const LOADED 			: String = "LOADED"; 
		public static const SAVED 			: String = "SAVED"; 
		
		public var data 			: TopPlayers;
		
		public var error			: Error;
		public var error_source		: Event;
		public var error_message	: String;
		
		
		public function TopPlayersEvent(evt:String) 
		{
			super(evt);
		}
		
		public function isError() : Boolean
		{
			return (error!=null) || (error_source!=null) || (error_message!=null) ;
		}
		
		override public function clone() : Event
		{
			var ret : TopPlayersEvent = new TopPlayersEvent(type);
			ret.data 		  = this.data;
			ret.error		  = this.error;
			ret.error_source  = this.error_source;
			ret.error_message = this.error_message;
			return ret;
		}
	
	}
}