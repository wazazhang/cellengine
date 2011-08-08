
package com.cell.gfx.game
{
	import com.cell.gameedit.ResourceEvent;
	
	import flash.display.BitmapData;
	
	/**
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CMapWorld extends CWorld
	{	
		protected var Map		: CMap;
		protected var Camera	: CCamera;
		
		public function CMapWorld(map:CMap, viewWidth:int, viewHeight:int)
		{	
			super(viewWidth, viewHeight);
			this.Map 	= map;
			this.Camera = new CCamera(viewWidth, viewHeight, map);
		}
		
		public function getMap() : CMap {
			return Map;
		}
		
//		------------------------------------------------------------------------------------------------------
		
//		------------------------------------------------------------------------------------------------------
		


	}
}
