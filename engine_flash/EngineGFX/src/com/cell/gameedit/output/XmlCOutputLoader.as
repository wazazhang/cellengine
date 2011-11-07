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

	public class XmlCOutputLoader implements OutputLoader
	{
		internal var img_table	: Map = new Map();
		internal var spr_table	: Map = new Map();
		internal var map_table	: Map = new Map();
		internal var world_table	: Map = new Map();
		
//		-----------------------------------------------------------------------------------------------
		
		internal var res_img_table	: Map = new Map();
		internal var res_spr_table	: Map = new Map();
		internal var res_map_table	: Map = new Map();
		
		
//		-----------------------------------------------------------------------------------------------
		
		internal var image_type	: String;
		internal var image_tile	: Boolean;
		internal var image_group	: Boolean;
		
//		-----------------------------------------------------------------------------------------------
		
		
		public function load(complete:Function, error:Function) : void
		{
		}
		
		public function getPercent():Number
		{
			return 0;
		}
		
		
		
		protected function init(xml:XML) : void
		{
			xml.normalize();
			
			this.image_type 	= xml.IMAGE_TYPE;
			this.image_tile 	= xml.IMAGE_TILE;
			this.image_group	= xml.IMAGE_GROUP;
			
			initResource(xml.resource[0]);
			
			initLevel(xml.level[0]);
			
		}
		
		protected function initResource(xml:XML) : void
		{
			//		Integer.parseInt(resource.getAttribute("images_count"));
			//		Integer.parseInt(resource.getAttribute("map_count"));
			//		Integer.parseInt(resource.getAttribute("sprite_count"));

			for each (var e:XML in xml.images) {
				var img : ImagesSet = initImages(e);
				trace("get images : " + img.Name);
				img_table.put(img.Name, img);
				var cimg : IImages = createCImages(img);
				res_img_table.put(img.Name, cimg);
			}
			for each (var e:XML in xml.map) {
				var map : MapSet = initMap(e);
				trace("get map : " + map.Name);
				map_table.put(map.Name, map);
				var cmap : CMap = createCMap(map, res_img_table.get(map.ImagesName));
				res_map_table.put(map.Name, cmap);
			}
			for each (var e:XML in xml.sprite) {
				var spr : SpriteSet = initSprite(e);
				trace("get sprite : " + spr.Name);
				spr_table.put(spr.Name, spr);
				var cspr : CSprite = createCSprite(spr, res_img_table.get(spr.ImagesName));
				res_spr_table.put(spr.Name, cspr);
			}
			
		}
		
		protected function initImages(xml:XML) : ImagesSet
		{
			var ret : ImagesSet = new ImagesSet();
			
			ret.Index		= xml.attribute("index");
			ret.Name		= xml.attribute("name");
			ret.CustomOut	= xml.attribute("output_type");
			ret.CustomExt	= xml.attribute("output_file");
			
			ret.Count 		= xml.attribute("size");
			ret.ClipsX 		= new Array(ret.Count);
			ret.ClipsY 		= new Array(ret.Count);
			ret.ClipsW 		= new Array(ret.Count);
			ret.ClipsH 		= new Array(ret.Count);
			ret.ClipsKey 	= new Array(ret.Count);
			
			try {
				ret.AppendData = StringUtil.getArray1D(xml.AppendData[0].toString());
			} catch (err:Error) {}

			for each (var e:XML in xml.clip) 
			{
				var index : int 	= e.attribute("index");
				ret.ClipsX[index] 	= int(e.attribute("x"));
				ret.ClipsY[index] 	= int(e.attribute("y"));
				ret.ClipsW[index] 	= int(e.attribute("width"));
				ret.ClipsH[index] 	= int(e.attribute("height"));
				ret.ClipsKey[index] = String(e.attribute("data"));
			}
			
			return ret;
		}
		
		protected function initMap(map:XML) : MapSet
		{
			var ret : MapSet 	= new MapSet();
			
			ret.Index			= map.attribute("index");
			ret.Name			= map.attribute("name");
			
			ret.ImagesName 		= map.attribute("images_name");
			ret.XCount 			= map.attribute("xcount");
			ret.YCount 			= map.attribute("ycount");
			ret.CellW 			= map.attribute("cellw");
			ret.CellH 			= map.attribute("cellh");
			
			var scenePartCount	: int = map.attribute("scene_part_count");
			var animateCount 	: int = map.attribute("scene_frame_count");
			var cdCount 		: int = map.attribute("cd_part_count");
			
			ret.TileID 			= new Array(scenePartCount);
			ret.TileTrans 		= new Array(scenePartCount);
			ret.Animates 		= Arrays.newArray2D(animateCount);
			ret.BlocksType 		= new Array(cdCount);
			ret.BlocksMask 		= new Array(cdCount);
			ret.BlocksX1 		= new Array(cdCount);
			ret.BlocksY1 		= new Array(cdCount);
			ret.BlocksX2 		= new Array(cdCount);
			ret.BlocksY2 		= new Array(cdCount);
			ret.BlocksW 		= new Array(cdCount);
			ret.BlocksH 		= new Array(cdCount);
			
			ret.TerrainScene2D	= Arrays.newArray2D(ret.YCount, ret.XCount);
			ret.TerrainBlock2D	= Arrays.newArray2D(ret.YCount, ret.XCount);
			
			try {
				ret.AppendData = StringUtil.getArray1D(map.AppendData[0].toString());
			} catch (err:Error) {}
			
			
			for each (var e:XML in map.scene_part) {
				var index : int			= e.attribute("index");
				ret.TileID[index]		= int(e.attribute("tile"));
				ret.TileTrans[index]	= int(e.attribute("trans"));
			}
			for each (var e:XML in map.scene_frame) {
				var index : int 		= e.attribute("index");
				var frameCount : int 	= e.attribute("data_size");
				ret.Animates[index] 	= new Array(frameCount);
				if (frameCount > 0) {
					var data : Array = StringUtil.splitString(e.attribute("data"), ",");
					for (var f:int = 0; f < frameCount; f++) {
						ret.Animates[index][f] = int(data[f]);
					}
				}
			}
			for each (var e:XML in map.cd_part) {
				var index : int 		= int(e.attribute("index"));
				var cd_type : String	= e.attribute("type");
				ret.BlocksType[index]	= "rect" == cd_type ?
					CCD.CD_TYPE_RECT : CCD.CD_TYPE_LINE;
				ret.BlocksMask[index]	= int(e.attribute("mask"));
				ret.BlocksX1[index] 	= int(e.attribute("x1"));
				ret.BlocksY1[index] 	= int(e.attribute("y1"));
				ret.BlocksX2[index] 	= int(e.attribute("x2"));
				ret.BlocksY2[index] 	= int(e.attribute("y2"));
				ret.BlocksW[index] 		= int(e.attribute("width"));
				ret.BlocksH[index] 		= int(e.attribute("height"));
			}
			for each (var e:XML in map.matrix) {
				var tile_matrix : Array	= StringUtil.getArray1DGroup(e.attribute("tile_matrix"));
				var cd_matrix 	: Array	= StringUtil.getArray1DGroup(e.attribute("flag_matrix"));
				for (var y:int = 0; y < ret.YCount; y++) {
					var tline : Array = StringUtil.splitString(tile_matrix[y], ",");
					var cline : Array = StringUtil.splitString(cd_matrix[y],   ",");
					for (var x:int = 0; x < ret.XCount; x++) {
						ret.TerrainScene2D[y][x] = int(tline[x]);
						ret.TerrainBlock2D[y][x] = int(cline[x]);
					}
				}
			}
			
			return ret;
		}
		
		protected function initSprite(sprite:XML) : SpriteSet
		{
			var ret : SpriteSet = new SpriteSet();

			ret.Index			= sprite.attribute("index");
			ret.Name			= sprite.attribute("name");
			
			ret.ImagesName 		= sprite.attribute("images_name");
			
			var scenePartCount 	= int(sprite.attribute("scene_part_count"));
			var sceneFrameCount = int(sprite.attribute("scene_frame_count"));
			var cdCount 		= int(sprite.attribute("cd_part_count"));
			var collidesCount 	= int(sprite.attribute("cd_frame_count"));
			var animateCount 	= int(sprite.attribute("animate_count"));
			
			try {
				ret.AppendData = StringUtil.getArray1D(sprite.AppendData[0].toString());
			} catch (err:Error) {}

			ret.PartX 			= new Array(scenePartCount);
			ret.PartY 			= new Array(scenePartCount);
			ret.PartTileID 		= new Array(scenePartCount);
			ret.PartTileTrans 	= new Array(scenePartCount);
			ret.Parts 			= Arrays.newArray2D(sceneFrameCount);
			ret.BlocksMask 		= new Array(cdCount);
			ret.BlocksX1 		= new Array(cdCount);
			ret.BlocksY1 		= new Array(cdCount);
			ret.BlocksW 		= new Array(cdCount);
			ret.BlocksH 		= new Array(cdCount);
			ret.Blocks 			= Arrays.newArray2D(collidesCount);
			ret.AnimateCount 	= animateCount;
			ret.AnimateNames 	= new Array(animateCount);
			ret.FrameAnimate 	= Arrays.newArray2D(animateCount);
			ret.FrameCDMap 		= Arrays.newArray2D(animateCount);
			ret.FrameCDAtk 		= Arrays.newArray2D(animateCount);
			ret.FrameCDDef 		= Arrays.newArray2D(animateCount);
			ret.FrameCDExt 		= Arrays.newArray2D(animateCount);
			
			for each (var e:XML in sprite.scene_part) {
				var index : int			= int(e.attribute("index"));
				ret.PartTileID[index] 	= int(e.attribute("tile"));
				ret.PartX[index] 		= int(e.attribute("x"));
				ret.PartY[index] 		= int(e.attribute("y"));
				ret.PartTileTrans[index]= int(e.attribute("trans"));
			}
			for each (var e:XML in sprite.scene_frame) {
				var index : int			= int(e.attribute("index"));
				var frameCount : int 	= int(e.attribute("data_size"));
				ret.Parts[index] 		= new Array(frameCount);
				if (frameCount > 0) {
					var data : Array = StringUtil.splitString(e.attribute("data"), ",");
					for (var f : int = 0; f < frameCount; f++) {
						ret.Parts[index][f] = int(data[f]);
					}
				}
			}
			for each (var e:XML in sprite.cd_part) {
				var index : int			= int(e.attribute("index"));
				ret.BlocksMask[index]	= int(e.attribute("mask"));
				ret.BlocksX1[index] 	= int(e.attribute("x1"));
				ret.BlocksY1[index] 	= int(e.attribute("y1"));
				ret.BlocksW[index] 		= int(e.attribute("width"));
				ret.BlocksH[index] 		= int(e.attribute("height"));
			}
			for each (var e:XML in sprite.cd_frame) {
				var index : int			= int(e.attribute("index"));
				var frameCount : int 	= int(e.attribute("data_size"));
				ret.Blocks[index] 		= new Array(frameCount);
				if (frameCount > 0) {
					var data : Array = StringUtil.splitString(e.attribute("data"), ",");
					for (var f : int = 0; f < frameCount; f++) {
						ret.Blocks[index][f] = int(data[f]);
					}
				}
			}
			for each (var e:XML in sprite.frames) {
				var names_reader 	: TextReader= new TextReader(e.attribute("names"));
				var frame_counts	: Array 	= StringUtil.splitString(e.attribute("counts"), ",");
				var frame_animate	: Array 	= StringUtil.getArray1DGroup(e.attribute("animates"));
				var frame_cd_map	: Array 	= StringUtil.getArray1DGroup(e.attribute("cd_map"));
				var frame_cd_atk	: Array 	= StringUtil.getArray1DGroup(e.attribute("cd_atk"));
				var frame_cd_def	: Array 	= StringUtil.getArray1DGroup(e.attribute("cd_def"));
				var frame_cd_ext	: Array 	= StringUtil.getArray1DGroup(e.attribute("cd_ext"));
				for (var i : int = 0; i < animateCount; i++) {
					ret.AnimateNames[i] 		= TextDeserialize.getString(names_reader);
					var frameCount	: int		= int(frame_counts[i]);
					var animate 	: Array		= StringUtil.splitString(frame_animate[i], ",");
					var cd_map 		: Array		= StringUtil.splitString(frame_cd_map[i], ",");
					var cd_atk 		: Array		= StringUtil.splitString(frame_cd_atk[i], ",");
					var cd_def 		: Array		= StringUtil.splitString(frame_cd_def[i], ",");
					var cd_ext 		: Array		= StringUtil.splitString(frame_cd_ext[i], ",");
					ret.FrameAnimate[i] 		= new Array(frameCount);
					ret.FrameCDMap[i] 			= new Array(frameCount);
					ret.FrameCDAtk[i] 			= new Array(frameCount);
					ret.FrameCDDef[i] 			= new Array(frameCount);
					ret.FrameCDExt[i]			= new Array(frameCount);
					for (var f : int = 0; f < frameCount; f++) {
						ret.FrameAnimate[i][f] 	= int(animate[f]);
						ret.FrameCDMap[i][f] 	= int(cd_map[f]);
						ret.FrameCDAtk[i][f] 	= int(cd_atk[f]);
						ret.FrameCDDef[i][f] 	= int(cd_def[f]);
						ret.FrameCDExt[i][f] 	= int(cd_ext[f]);
					}
				}
			}
			
			return ret;
		}
		
		
		protected function initLevel(xml:XML) : void
		{
			//		Integer.parseInt(level.getAttribute("world_count"));
			for each (var e:XML in xml.world) {
				var world : WorldSet = initWorld(e);
				world_table.put(world.Name, world);
			}
		}
		
		protected function initWorld(world:XML) : WorldSet
		{
			var ret : WorldSet = new WorldSet();
			
			ret.Index			= world.attribute("index");
			ret.Name			= world.attribute("name");
			
			ret.GridXCount		= int(world.attribute("grid_x_count"));
			ret.GridYCount		= int(world.attribute("grid_y_count"));
			ret.GridW			= int(world.attribute("grid_w"));
			ret.GridH			= int(world.attribute("grid_h"));
			ret.Width			= int(world.attribute("width"));
			ret.Height			= int(world.attribute("height"));
			ret.Data			= StringUtil.getArray1DLines(world.attribute("data"));
			ret.Terrian			= Arrays.newArray2D(ret.GridXCount, ret.GridYCount);
			
			//		int maps_count	= Integer.parseInt(world.getAttribute("unit_count_map"));
			//		int sprs_count	= Integer.parseInt(world.getAttribute("unit_count_sprite"));
			//		int wpss_count	= Integer.parseInt(world.getAttribute("waypoint_count"));
			//		int wrss_count	= Integer.parseInt(world.getAttribute("region_count"));
			
			var terrains_count : int = ret.GridXCount * ret.GridYCount;
			var terrains : Array = StringUtil.splitString(world.attribute("terrain"), ",");
			for (var i : int = 0; i < terrains_count; i++) {
				var x : int = i / ret.GridYCount;
				var y : int	= i % ret.GridYCount;
				ret.Terrian[x][y] = int(terrains[i]);
			}
			
			for each (var e:XML in world.unit_map) {
				var map : MapObject = new MapObject();
				map.Index 		= int(e.attribute("index"));
				map.UnitName 	= e.attribute("map_name");
				map.MapID 		= e.attribute("id");
				map.X 			= int(e.attribute("x"));
				map.Y 			= int(e.attribute("y"));
				map.ImagesID 	= e.attribute("images");
				map.Data		= StringUtil.getArray1DLines(e.attribute("map_data"));
				map.Priority	= e.attribute("priority");
				ret.Maps.put(map.Index, map);
			}
			for each (var e:XML in world.unit_sprite) {
				var spr : SpriteObject = new SpriteObject();
				spr.Index 		= int(e.attribute("index"));
				spr.UnitName 	= e.attribute("spr_name");
				spr.SprID 		= e.attribute("id");
				spr.Anim		= int(e.attribute("animate_id"));
				spr.Frame		= int(e.attribute("frame_id"));
				spr.X 			= int(e.attribute("x"));
				spr.Y 			= int(e.attribute("y"));
				spr.ImagesID 	= e.attribute("images");
				spr.Data		= StringUtil.getArray1DLines(e.attribute("spr_data"));
				spr.Priority	= e.attribute("priority");
				ret.Sprs.put(spr.Index, spr);
			}
			for each (var e:XML in world.waypoint) {
				var wp : WaypointObject = new WaypointObject();
				wp.Index 		= int(e.attribute("index"));
				wp.X 			= int(e.attribute("x"));
				wp.Y 			= int(e.attribute("y"));
				wp.Data			= StringUtil.getArray1DLines(e.attribute("path_data"));
				ret.WayPoints.put(wp.Index, wp);
			}
			for each (var e:XML in world.region) {
				var wr : RegionObject = new RegionObject();
				wr.Index 		= int(e.attribute("index"));
				wr.X 			= int(e.attribute("x"));
				wr.Y 			= int(e.attribute("y"));
				wr.W 			= int(e.attribute("width"));
				wr.H 			= int(e.attribute("height"));
				wr.Data			= StringUtil.getArray1DLines(e.attribute("region_data"));
				ret.Regions.put(wr.Index, wr);
			}
			
			for each (var e:XML in world.waypoint_link)
			{
				var start 	: int = int(e.attribute("start"));
				var end 	: int = int(e.attribute("end"));
				ret.WayPoints.get(start).Nexts.push(ret.WayPoints.get(end));
			}
			
			return ret;
		}
		
		
//		-----------------------------------------------------------------------------------------------
		
		/***
		 * 是否单独输出每张图
		 * @return
		 */
		public function isTile() : Boolean 
		{
			return image_tile;
		}

		/**
		 * 是否输出整图
		 * @return
		 */
		public function isGroup() : Boolean
		{
			return image_group;
		}
		
		/**
		 * 获得导出图片文件类型
		 * @return
		 */
		public function getImageExtentions() : String 
		{
			return image_type;
		}
		
		
		public function	getImgTable() 	: Map {
			return img_table;
		}
		
		public function	getSprTable() 	: Map {
			return spr_table;
		}
		
		public function	getMapTable() 	: Map {
			return map_table;
		}
		
		public function	getWorldTable() : Map {
			return world_table;
		}
		
		/**
		 * call by {@link SetResource}.dispose()
		 */
		public function dispose() : void
		{
			
		}
		
//		-----------------------------------------------------------------------------------------------
// 		abstract
		
		public function getCImages(img:String) : IImages
		{
			return res_img_table[img];
		}

		public function getCSprite(spr:String) : CSprite
		{
			return res_spr_table[spr];
		}
		
		public function getCMap(map:String) : CMap
		{
			return res_map_table[map];
		}
		
//		-----------------------------------------------------------------------------------------------
		
		public function createCImages(img:ImagesSet) : IImages
		{
			throw new Error("not impl");
		}
		public function createTileImage(tileid:int, width:int, height:int, key:String) : CImage
		{
			return new CImage(new BitmapData(width, height, true));
		}
		public function createCSprite(spr:SpriteSet, images:IImages) : CSprite
		{
			if (spr != null) {
				return new XmlCSprite(this, images, spr);
			}
			return null;
		}
		public function createCMap(map:MapSet, images:IImages) : CMap
		{
			if (map != null) {
				return new XmlCMap(this, images, map);
			}
			return null;
		}
		
		
	}
}