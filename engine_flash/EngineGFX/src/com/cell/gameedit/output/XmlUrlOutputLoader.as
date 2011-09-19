package com.cell.gameedit.output
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.MapObject;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gameedit.object.worldset.WaypointObject;
	import com.cell.gfx.game.CCD;
	import com.cell.gfx.game.CImage;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IImages;
	import com.cell.io.TextDeserialize;
	import com.cell.io.TextReader;
	import com.cell.io.UrlManager;
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.cell.util.NumberReference;
	import com.cell.util.StringUtil;
	
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.getClassByAlias;
	import flash.utils.ByteArray;
	
	
	public class XmlUrlOutputLoader extends XmlCOutputLoader
	{
		internal var path 		: String;
		internal var path_root 	: String;
		internal var file_name 	: String;
		
		private var loader			: URLLoader;
		private var all_complete	: Function;
		private var loaded_images	: int = 0;
		
		public function XmlUrlOutputLoader(url:String)
		{
			this.path 		= url.replace('\\', '/');
			this.path_root	= path.substring(0, path.lastIndexOf("/")+1);
			this.file_name	= path.substring(path_root.length);
		}
		
		public function toString() : String
		{
			return "[XmlDirOutputLoader:" + path+"]";
		}
		
		override public function load(complete:Function) : void
		{
			this.loader = new URLLoader();
			this.loader.addEventListener(Event.COMPLETE, xml_complete);
			
			this.all_complete = complete;
			this.loader.load(UrlManager.getUrl(path));
		}
		
		private function xml_complete(e:Event) : void
		{
			init(new XML(this.loader.data));
		}
		
		override public function createCImages(img:ImagesSet) : IImages
		{
			if (img != null) {
				var ld : Loader = new Loader();
				ld.contentLoaderInfo.addEventListener(Event.COMPLETE, img_complete);
				return new XmlUrlTiles(this, img, ld);
			}
			return null;
		}
		
		private function img_complete(e:Event) : void
		{
			loaded_images ++;
			if (loaded_images >= super.getImgTable().size()) {
				all_complete.call();
			}
		}
		
	}
}