package com.cell.gfx.game
{
	import com.cell.math.IVector2D;
	
	import flash.geom.Rectangle;

	public interface ICamera extends IVector2D
	{
		 function get x() : Number;
		
		 function get y() : Number;
		
		 function get w() : Number;
		
		 function get h() : Number;
				
		 function setPos(x:Number, y:Number) : void ;
		
		 function move(x:Number, y:Number) : void;
		
		 function get bounds() : Rectangle;
	}
}