package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;

	public interface ImageObserver
	{
		function imagesLoaded(res:ResourceLoader, img:ImagesSet) : void;
	}
}