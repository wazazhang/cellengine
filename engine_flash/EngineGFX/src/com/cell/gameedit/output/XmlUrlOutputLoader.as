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
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.getClassByAlias;
	import flash.utils.ByteArray;
	
	
	public class XmlUrlOutputLoader extends XmlCOutputLoader
	{
		internal var path 		: String;
		internal var path_root 	: String;
		internal var file_name 	: String;
		
		private var xml_loader		: URLLoader;
		private var img_loaders		: Vector.<XmlUrlTiles> = new Vector.<XmlUrlTiles>();
		private var img_loaders_cur	: Vector.<XmlUrlTiles> = new Vector.<XmlUrlTiles>();
		private var loaded_images	: int = 0;
		
		protected var all_complete	: Function;
		protected var all_error		: Function;
		
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
		
		override public function load(complete:Function, error:Function) : void
		{		
			this.all_error    = error;
			this.all_complete = complete;

			this.xml_loader = new URLLoader();
			this.xml_loader.addEventListener(Event.COMPLETE, xml_complete);
			this.xml_loader.addEventListener(IOErrorEvent.IO_ERROR, xml_error);
			this.xml_loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, xml_error);
			this.xml_loader.load(UrlManager.getUrl(path));
		}
		
		override public function getPercent():Number
		{
			var xml_pct : Number = 1;
			if (xml_loader != null) {
				if (xml_loader.bytesTotal > 0) {
					xml_pct = xml_loader.bytesLoaded / Number(xml_loader.bytesTotal);
				}
			}
			var img_pct : Number = 0;
			if (img_loaders.length > 0) {
				for each (var l : XmlUrlTiles in img_loaders) {
					var tpct : Number = l.getPercent();
					img_pct += l.getPercent();
				}
				img_pct = (img_pct / img_loaders.length);
			}
			return (xml_pct*0.2) + (img_pct*0.8);
		}
		
		private function xml_complete(e:Event) : void
		{
			init(new XML(this.xml_loader.data));
			
			if (startLoad()) {
				all_complete.call();
			}
		}
		
		private function xml_error(e:Event) : void
		{
			trace("load xml error : " + e);
			all_error.call(null, e);
		}
		
		protected function startLoad() : Boolean
		{
			if (img_loaders_cur.length > 0) {
				var tile : XmlUrlTiles = img_loaders_cur[0];
				img_loaders_cur.splice(0, 1);
				tile.load(img_complete, img_error);
				return false;
			}
			return true;
		}
		
		override public function createCImages(img:ImagesSet) : IImages
		{
			if (img != null) {
				var tiles : XmlUrlTiles = new XmlUrlTiles(this, img);
				img_loaders.push(tiles);
				img_loaders_cur.push(tiles);
				return tiles;
			}
			return null;
		}
		
		private function img_complete(e:Event) : void
		{
			loaded_images ++;
			if (startLoad()) {
				all_complete.call();
			}
		}
		
		private function img_error(e:IOErrorEvent) : void
		{
			trace("load img error : " + e);
			all_error.call(null, e);
		}
	}
}