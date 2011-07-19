package com.cell.gfx.game
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CImage;
	
	public interface CImages
	{
		function clone() : CImages;
		
		function getImage(index:int) : CImage;
		
		function getWidth(index:int) : int;
		
		function getHeight(index:int) : int;
		
		function render(g:CGraphics, index:int, x:int, y:int, transform:int) : void ;
		
	}
}