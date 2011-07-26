package com.cell.math
{

	/**
	 * 向量，在2D坐标系内，拥有方向和长度的单位
	 * @author WAZA
	 *
	 */
	public interface IVector2D 
	{
		function getVectorX() : Number;
		function getVectorY() : Number;
		
		function setVectorX(x : Number) : void;
		function setVectorY(y : Number) : void;
		
		function addVectorX(dx : Number) : void;
		function addVectorY(dy : Number) : void;
		
	}


}