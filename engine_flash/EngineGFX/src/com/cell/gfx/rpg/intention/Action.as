package com.cell.gfx.rpg.intention
{
	import com.cell.gfx.rpg.G2DActionUnit;
	import com.cell.gfx.rpg.G2DUnit;

	public interface Action
	{
		function onUpdate(unit:G2DActionUnit) : void ;
		
		function onStart(unit:G2DActionUnit) : void ;
		
		function onStop(unit:G2DActionUnit) : void ;
		
		function isEnd() : Boolean;
	}
}