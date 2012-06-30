package com.cell.gfx.action
{
	import flash.events.Event;

	public class ActionEvent extends Event
	{	
		public static const ON_STOP 	: String = "ON_STOP"; 
		public static const ON_START	: String = "ON_START";
		
		public var sprite : ActionSprite;
		public var action : Action;
		
		public function ActionEvent(evt:String, 
									sprite:ActionSprite, 
									action:Action) 
		{
			super(evt);
			this.sprite = sprite;
			this.action = action;
		}
		
		override public function clone() : Event
		{
			var ret : ActionEvent = new ActionEvent(type, sprite, action);
			return ret;
		}
	}
}