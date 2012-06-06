package com.cell.ui.edit
{
	import flash.events.EventDispatcher;

	[Event(name=UIEditEvent.LOADED, type="com.cell.ui.edit.UIEditEvent")]  
	[Event(name=UIEditEvent.ERROR,  type="com.cell.ui.edit.UIEditEvent")]  
	public class UIEditLoader extends EventDispatcher
	{
		
		
		public function UIEditLoader(edit:UIEdit, fileName:String)
		{
		}
	}
}