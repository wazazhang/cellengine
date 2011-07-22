package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.gameedit.object.worldset.WaypointObject;
	import com.cell.gameedit.output.XmlOutputLoader;
	import com.cell.gfx.game.CImages;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	[Event(name=ResourceEvent.LOADED, type="com.cell.gameedit.ResourceEvent")]  
	public class ResourceLoader extends EventDispatcher
	{
		private var url 				: String;
		private var output 				: OutputLoader;
		private var resource_manager	: Map = new Map();
		
		public function ResourceLoader(url:String)
		{
			this.url = url;
			if (StringUtil.endsOf(url.toLowerCase(), ".xml")) {
				this.output	= new XmlOutputLoader(url);
			}
		}
		
		public function load() : void
		{
			trace("load resource : " + url);
			output.load(output_complete);
		}
		
		public function getUrl() : String
		{
			return url;
		}
		
		private function output_complete() : void 
		{
			trace("load resource complete : " + output);
			for each (var imgset : ImagesSet in output.getImgTable()) { 
				var images : CImages = output.createCImages(imgset);
				resource_manager.put("IMG_" + imgset.Name, images);
				trace("get images : " + imgset.Name);
			}
			for each (var sprset : SpriteSet in output.getSprTable()) { 
				var sprite : CSprite = output.createCSprite(sprset, getImages(sprset.ImagesName));
				resource_manager.put("SPR_" + sprset.Name, sprite);
				trace("get sprite : " + sprset.Name);
			}
			for each (var mapset : MapSet in output.getMapTable()) { 
				var map : CMap = output.createCMap(mapset, getImages(mapset.ImagesName));
				resource_manager.put("MAP_" + mapset.Name, map);
				trace("get map : " + mapset.Name);
			}

			var event : ResourceEvent = new ResourceEvent(ResourceEvent.LOADED);
			event.res = this;
			dispatchEvent(event);
		}
		
		
		public function getImages(name:String) : CImages
		{
			return resource_manager.get("IMG_" + name);
		}
		
		public function getSprite(name:String) : CSprite
		{
			var ret : CSprite = resource_manager.get("SPR_" + name);
			if (ret != null) {
				return ret.copy();
			}
			return ret;
		}
		
		public function getMap(name:String) : CMap
		{
			var ret : CMap = resource_manager.get("MAP_" + name);
			if (ret != null) {
				return ret.copy();
			}
			return ret;
		}
		
//		-------------------------------------------------------------------------------------
		
//		-------------------------------------------------------------------------------------
		
		public function getOutput() : OutputLoader {
			return output;
		}
		
		public function dispose() : void {
			output.dispose();
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		final public function  getSetWorld(name:String) : WorldSet {
			return output.getWorldTable().get(name);
		}
		
		final public function  getSetSprite(name:String) : SpriteSet {
			return  output.getSprTable().get(name);
		}
		
		final public function  getSetMap(name:String) : MapSet {
			return output.getMapTable().get(name);
		}
	
		final public function  getSetImages(name:String) : ImagesSet {
			return output.getImgTable().get(name);
		}
		
//		-------------------------------------------------------------------------------------------------------------------------------
		
		
		
	}
}