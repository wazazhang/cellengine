package com.cell.gameedit
{
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.gameedit.object.worldset.WaypointObject;
	import com.cell.gameedit.output.XmlCOutputLoader;
	import com.cell.gameedit.output.XmlUrlOutputLoader;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.IImages;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	[Event(name=ResourceEvent.LOADED, type="com.cell.gameedit.ResourceEvent")]  
	[Event(name=ResourceEvent.ERROR,  type="com.cell.gameedit.ResourceEvent")]  
	public class ResourceLoader extends EventDispatcher
	{
		private var url 				: String;
		private var output 				: OutputLoader;
		private var spr_buff			: Map;
		
		public function ResourceLoader(url:String, output:OutputLoader = null)
		{
			this.url = url;
			if (output != null) {
				this.output = output;
			}
			else if (StringUtil.endsOf(url.toLowerCase(), ".xml")) {
				this.output	= new XmlUrlOutputLoader(url);
			}
			this.spr_buff = new Map();
		}
		
		public function load() : void
		{
			trace("load resource : " + url);
			output.load(output_complete, output_error);
		}
		
		public function getUrl() : String
		{
			return url;
		}
		
		private function output_complete() : void 
		{
			trace("load resource complete : " + url);
			var event : ResourceEvent = new ResourceEvent(ResourceEvent.LOADED);
			event.res = this;
			dispatchEvent(event);
		}
		
		private function output_error() : void
		{
			trace("load resource error : " + url);
			var event : ResourceEvent = new ResourceEvent(ResourceEvent.ERROR);
			event.res = this;
			dispatchEvent(event);
		}
		
		public function getImages(name:String) : IImages
		{
			return output.getCImages(name);
		}
		
		public function getSpriteTemplate(name:String) : CSprite
		{
			return output.getCSprite(name);
		}
		
		public function getSprite(name:String) : CSprite
		{
			var ret : CSprite = output.getCSprite(name);
			if (ret != null) {
				return ret.copy();
			}
			return ret;
		}
		
		public function getSpriteBuffer(name:String) : CSpriteBuffer
		{
			var ret : CSpriteBuffer = spr_buff.get(name);
			if (ret != null) {
				return ret.copy();
			} else {
				var spr : CSprite = output.getCSprite(name);
				if (spr != null) {
					ret = new CSpriteBuffer();
					ret.init(spr);
					spr_buff.put(name, ret);
					return ret.copy();
				}
			}
			return null;
		}
		
		public function getMap(name:String) : CMap
		{
			var ret : CMap = output.getCMap(name);
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