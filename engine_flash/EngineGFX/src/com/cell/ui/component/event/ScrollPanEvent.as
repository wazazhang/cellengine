package com.cell.ui.component.event
{
	import flash.events.Event;

	public class ScrollPanEvent extends Event
	{		
		public static const CLICK_ITEM 	: String = "CLICK_ITEM"; 
		
		public function ScrollPanEvent(type : String)
		{
			super(type);
		}
		
		
	}
}