package com.cell.gfx
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.geom.Rectangle;

	public interface IScreenTransition
	{
		function asSprite() : Sprite;
		
		/**开始进入当前屏幕*/
		function startTransitionIn() : void;
		
		/**开始离开当前屏幕*/
		function startTransitionOut() : void;
		
		
		/**是否正在进入当前屏幕*/
		function isTransitionIn() : Boolean;
		
		/**只否正在离开当前屏幕*/
		function isTransitionOut() : Boolean;
	
		/**切换屏幕是，在最上层画些什么*/
		function render(root:CellScreenManager) : void;
	}
}