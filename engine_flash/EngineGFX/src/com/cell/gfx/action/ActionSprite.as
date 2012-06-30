package com.cell.gfx.action
{
	public interface ActionSprite
	{
		function get x() : Number;
		function set x(v:Number) : void;
		
		function get y() : Number;
		function set y(v:Number) : void;
		
		function get visible() : Boolean;
		function set visible(v:Boolean) : void;
		
		function get alpha() : Number;
		function set alpha(v:Number) : void;
		
		function get rotation() : Number;
		function set rotation(v:Number) : void;
		
		function get scaleX() : Number;
		function set scaleX(v:Number) : void;
		
		function get scaleY() : Number;
		function set scaleY(v:Number) : void;

		
		
		function startAction(intention:Action, onStop:Function=null) : Action ;
		
		function stopAction(cls:Class) : Action;
	
		function addEventListener(e:Event, listener:Function);
		
	}
}