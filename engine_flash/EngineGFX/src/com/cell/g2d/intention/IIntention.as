package com.cell.g2d.intention
{
	import com.cell.g2d.G2DUnit;

	public interface IIntention
	{
		function onUpdate(unit:G2DUnit) : void ;
		
		function onStart(unit:G2DUnit) : void ;
		
		function onStop(unit:G2DUnit) : void ;
	}
}