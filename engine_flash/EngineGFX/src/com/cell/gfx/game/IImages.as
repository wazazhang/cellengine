package com.cell.gfx.game
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.object.ImagesSet;
	
	import flash.events.IEventDispatcher;
	
	public interface IImages extends IEventDispatcher
	{
		function clone() : IImages;
		
		function getImage(index:int) : CImage;
		
		function getWidth(index:int) : int;
		
		function getHeight(index:int) : int;
		
		function render(g:IGraphics, index:int, x:int, y:int, w:int, h:int, transform:int) : void ;
	}
}