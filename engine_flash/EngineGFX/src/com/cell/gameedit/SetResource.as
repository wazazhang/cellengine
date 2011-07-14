package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.gameedit.object.worldset.WaypointObject;
	import com.cell.gfx.CImages;
	import com.cell.gfx.game.CSprite;
	import com.cell.util.Map;
	
	import flash.display.BitmapData;
	
	public class SetResource
	{
	//	-------------------------------------------------------------------------------------
		
		private var ImgTable			: Map;
		private var SprTable			: Map;
		private var MapTable			: Map;
		private var WorldTable			: Map;
	
		protected var output_adapter	: Output;
		
		protected var resource_manager	: Map;
		
	//	-------------------------------------------------------------------------------------
		
		public function SetResource(adapter:Output)
		{
			this.output_adapter		= adapter;
			this.resource_manager	= new Map();
			
			this.ImgTable 			= output_adapter.getImgTable();
			this.SprTable 			= output_adapter.getSprTable();
			this.MapTable 			= output_adapter.getMapTable();
			this.WorldTable			= output_adapter.getWorldTable();
		}
		
		public function getOutput() : Output {
			return output_adapter;
		}
		
		public function dispose() : void {
			output_adapter.dispose();
		}
		
//	-------------------------------------------------------------------------------------------------------------------------------
	
		
		public function getImages(name:String) : CImages
		{
			return resource_manager.get("IMG_" + name);
		}
		
		public function getSprite(name:String) : CSprite
		{
			return resource_manager.get("SPR_" + name);
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		final public function  getSetWorld(name:String) : WorldSet {
			return WorldTable.get(name);
		}
	
		final public function  getSetSprite(name:String) : SpriteSet {
			return SprTable.get(name);
		}
	
		final public function  getSetMap(name:String) : MapSet {
			return MapTable.get(name);
		}
	
		final public function  getSetImages(name:String) : ImagesSet {
			return ImgTable.get(name);
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		protected function createCImages(set:ImagesSet) : CImages
		{
			return null;
		}
		
		protected function createCSprite(set:SpriteSet) : CSprite
		{
			
			return null;
		}
		
		
	}
}
