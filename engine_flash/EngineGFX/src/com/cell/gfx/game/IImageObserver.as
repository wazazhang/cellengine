package com.cell.gfx.game
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.ImagesSet;

	public interface IImageObserver
	{
		function imagesLoaded(e:ResourceEvent) : void;
	}
}