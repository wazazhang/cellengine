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
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.getClassByAlias;
	import flash.utils.ByteArray;

	public class XmlEmbedOutputLoader extends XmlCOutputLoader
	{
		protected var xml:XML;
		protected var class_name_map:Map;
		
		public function XmlEmbedOutputLoader(xml:XML, class_name_map:Map)
		{
			this.xml = xml;
			this.class_name_map = class_name_map;
		}
		
		public function toString() : String
		{
			return "[XmlEmbedOutputLoader:]";
		}
		
		override public function load(complete:Function, error:Function) : void
		{
			super.init(xml);
			complete.call();
		}
		
		override public function getPercent():Number
		{
			return 1;
		}
		
		override public function createCImages(img:ImagesSet) : IImages
		{
			if (img != null) {
				var tiles : XmlEmbedTiles = new XmlEmbedTiles(this, img);
				return tiles;
			}
			return null;
		}
		
		public function getImageClass(img:ImagesSet) : Bitmap
		{
			var cls : Class = class_name_map.get(img.getName());
			return new cls() as Bitmap;
		}
		
		public function getTileClass(img:ImagesSet, tile:int) : Bitmap
		{
			var cls : Class = class_name_map.get(img.getName()+"/"+tile);
			return new cls() as Bitmap;
		}
	}
}