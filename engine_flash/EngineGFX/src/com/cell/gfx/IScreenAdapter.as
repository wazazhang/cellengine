package com.cell.gfx
{
	import flash.display.Sprite;

	/**
	 * 名字，屏幕实体。
	 */
	public interface IScreenAdapter
	{
		function createScreen(root:CellScreenManager, name:String) : CellScreen;
	}
}