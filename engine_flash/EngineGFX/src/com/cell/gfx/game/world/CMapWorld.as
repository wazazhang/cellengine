
package com.cell.gfx.game.world
{
	import com.cell.gameedit.ResourceEvent;
	
	import flash.display.BitmapData;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.IImageObserver;
	
	/**
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CMapWorld extends CWorld implements IImageObserver
	{	
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		
		public function CMapWorld(map:CMap, viewWidth:int, viewHeight:int)
		{	
			super(viewWidth, viewHeight);
			this.Map 	= map;
			this.Map.getAnimates().getImages().addImageObserver(this);
			this.Camera = new CCamera(viewWidth, viewHeight, map);
		}
		
		public function getMap() : CMap {
			return Map;
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			
		}
		
//		------------------------------------------------------------------------------------------------------
		
//		------------------------------------------------------------------------------------------------------
		


	}
}
