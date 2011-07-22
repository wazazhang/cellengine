package com.cell.gfx.game
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.object.ImagesSet;
	
	public interface CImages
	{
		function clone() : CImages;
		
		function getImage(index:int) : CImage;
		
		function getWidth(index:int) : int;
		
		function getHeight(index:int) : int;
		
		function render(g:CGraphics, index:int, x:int, y:int, w:int, h:int, transform:int) : void ;
		
	}
}