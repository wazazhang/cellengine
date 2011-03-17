package com.cell.game.ai.pathfind;

public interface AstarManhattanMap
{
	public int getXCount() ;

	public int getYCount() ;

	public int getCellW() ;

	public int getCellH() ;
	
	/**
	 * 测试是否可以通过
	 * @param bx
	 * @param by
	 * @return true＝不能通过 ；false＝可以通过
	 */
	public boolean getFlag(int bx, int by);
}

