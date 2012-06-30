package com.cell.game.ai.astar.manhattan
{
	public interface AstarManhattanMap
	{
		function getXCount():int;
		
		function getYCount():int;
		
		function getCellW():int;
		
		function getCellH():int;
		
		/**
		 * 测试是否可以通过
		 * @param bx
		 * @param by
		 * @return true＝不能通过 ；false＝可以通过
		 */
		function getFlag(bx:int,by:int):Boolean;
	
	}
}