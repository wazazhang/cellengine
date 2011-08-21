//
//  CXMLOutputLoader.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CXMLOutputLoader.h"
#include "CXml.h"

namespace com_cell_game
{	  
	
	void XMLOutputLoader::init(XMLNode &doc)
	{
		for (map<string, XMLNode*>::iterator it=doc.childBegin(); 
			 it!=doc.childEnd(); ++it) 
		{
			XMLNode &child = *(it->second);
			
			if (stringEquals(child.getName(), "IMAGE_TYPE")) {
				image_type = child.value;
			}
			else if (stringEquals(child.getName(), "IMAGE_TILE")) {
				stringToBool(child.getName(), image_tile);
			}
			else if (stringEquals(child.getName(), "IMAGE_GROUP")) {
				stringToBool(child.getName(), image_group);
			}
			else if (stringEquals(child.getName(), "level")) {
				initLevel(child);
			}
			else if (stringEquals(child.getName(), "resource")) {
				initResource(child);
			}
		}
	}
	
	void XMLOutputLoader::initResource(XMLNode &resource)
	{
		//		Integer.parseInt(resource.getAttribute("images_count"));
		//		Integer.parseInt(resource.getAttribute("map_count"));
		//		Integer.parseInt(resource.getAttribute("sprite_count"));
		
		for (map<string, XMLNode*>::iterator it=resource.childBegin(); 
			 it!=resource.childEnd(); ++it) 
		{
			XMLNode &e = *(it->second);
			
			if (stringEquals(e.getName(), "images")) {
				initImages(e);
			}
			else if (stringEquals(e.getName(), "map")) {
				initMap(e);
			}
			else if (stringEquals(e.getName(), "sprite")) {
				initSprite(e);
			}
		}
	}
	
	void XMLOutputLoader::initImages(XMLNode &images)
	{
		ImagesSet set(images.getAttributeAsInt("index"),
					  images.getAttribute("name"));
	
		set.Count = images.getAttributeAsInt("size");
		set.ClipsX.resize(set.Count);
		set.ClipsY.resize(set.Count);
		set.ClipsW.resize(set.Count);
		set.ClipsH.resize(set.Count);
		set.ClipsKey.resize(set.Count);

		for (map<string, XMLNode*>::iterator it=images.childBegin(); 
			 it!=images.childEnd(); ++it) 
		{
			XMLNode &e = *(it->second);
			if (stringEquals(e.getName(), "clip")) {
				int i = e.getAttributeAsInt("index");
				set.ClipsX[i] 	= e.getAttributeAsInt("x");
				set.ClipsY[i] 	= e.getAttributeAsInt("y");
				set.ClipsW[i] 	= e.getAttributeAsInt("width");
				set.ClipsH[i] 	= e.getAttributeAsInt("height");
				set.ClipsKey[i] = e.getAttribute("data");
			}
		}
	}
	
	void XMLOutputLoader::initMap(XMLNode &doc)
	{
		MapSet set(doc.getAttributeAsInt("index"), 
				   doc.getAttribute("name"));
		
		set.ImagesName 		= doc.getAttribute("images_name");
		set.XCount 			= doc.getAttributeAsInt("xcount");
		set.YCount 			= doc.getAttributeAsInt("ycount");
		set.CellW 			= doc.getAttributeAsInt("cellw");
		set.CellH 			= doc.getAttributeAsInt("cellh");
		
		int scenePartCount 	= doc.getAttributeAsInt("scene_part_count");
		int animateCount 	= doc.getAttributeAsInt("scene_frame_count");
		int cdCount 		= doc.getAttributeAsInt("cd_part_count");
		
		set.TileID			.resize(scenePartCount);
		set.TileTrans		.resize(scenePartCount);

		set.Animates 		.resize(animateCount);
		set.BlocksType 		.resize(cdCount);
		set.BlocksMask 		.resize(cdCount);
		set.BlocksX1 		.resize(cdCount);
		set.BlocksY1 		.resize(cdCount);
		set.BlocksX2 		.resize(cdCount);
		set.BlocksY2 		.resize(cdCount);
		set.BlocksW 		.resize(cdCount);
		set.BlocksH 		.resize(cdCount);
		
		set.TerrainScene2D	.resize(set.YCount);
		set.TerrainBlock2D	.resize(set.YCount);
		for (int i=0; i<set.YCount; i++) {
			set.TerrainScene2D[i].resize(set.XCount);
			set.TerrainBlock2D[i].resize(set.XCount);
		}
		
		
		for (map<string, XMLNode*>::iterator it=doc.childBegin(); 
			 it!=doc.childEnd(); ++it) 
		{
			XMLNode &e = *(it->second);
			if (stringEquals(e.getName(), "scene_part")) 
			{
				int index 			= e.getAttributeAsInt("index");
				set.TileID[index]	= e.getAttributeAsInt("tile");
				set.TileTrans[index]= e.getAttributeAsInt("trans");
			}
			else if (stringEquals(e.getName(), "scene_frame")) 
			{
				int index		= e.getAttributeAsInt("index");
				int frameCount	= e.getAttributeAsInt("data_size");
				set.Animates[index].resize(frameCount);
				if (frameCount > 0) {
					vector<string> data = stringSplit(e.getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Animates[index][f] = stringToInt(data[f]);
					}
				}
			}
			else if (stringEquals(e.getName(), "cd_part")) 
			{
				int index				= e.getAttributeAsInt("index");
				set.BlocksType[index]	= stringEquals(e.getAttribute("type"), "rect") ?
											CCD::CD_TYPE_RECT : CCD::CD_TYPE_LINE;
				set.BlocksMask[index]	= e.getAttributeAsInt("mask");
				set.BlocksX1[index] 	= e.getAttributeAsInt("x1");
				set.BlocksY1[index] 	= e.getAttributeAsInt("y1");
				set.BlocksX2[index] 	= e.getAttributeAsInt("x2");
				set.BlocksY2[index] 	= e.getAttributeAsInt("y2");
				set.BlocksW[index] 		= e.getAttributeAsInt("width");
				set.BlocksH[index] 		= e.getAttributeAsInt("height");
			}
			else if (stringEquals(e.getName(), "matrix")) 
			{
				vector<string> tile_matrix	= getArray2D(e.getAttribute("tile_matrix"));
				vector<string> cd_matrix 	= getArray2D(e.getAttribute("flag_matrix"));
				for (int y = 0; y < set.YCount; y++) {
					vector<string> tline = stringSplit(tile_matrix[y], ",");
					vector<string> cline = stringSplit(cd_matrix[y],   ",");
					for (int x = 0; x < set.XCount; x++) {
						set.TerrainScene2D[y][x] = stringToInt(tline[x]);
						set.TerrainBlock2D[y][x] = stringToInt(cline[x]);
					}
				}
			}
		}
		
		
	}
	
	void XMLOutputLoader::initSprite(XMLNode &sprite)
	{
		SpriteSet set(sprite.getAttributeAsInt("index"), 
					  sprite.getAttribute("name"));
		
		set.ImagesName		= sprite.getAttribute("images_name");
		
		int scenePartCount 	= (sprite.getAttributeAsInt("scene_part_count"));
		int sceneFrameCount = (sprite.getAttributeAsInt("scene_frame_count"));
		int cdCount 		= (sprite.getAttributeAsInt("cd_part_count"));
		int collidesCount 	= (sprite.getAttributeAsInt("cd_frame_count"));
		int animateCount 	= (sprite.getAttributeAsInt("animate_count"));
		
		set.PartX 			.resize(scenePartCount);
		set.PartY 			.resize(scenePartCount);
		set.PartTileID 		.resize(scenePartCount);
		set.PartTileTrans 	.resize(scenePartCount);
		set.Parts 			.resize(sceneFrameCount);
		set.BlocksMask 		.resize(cdCount);
		set.BlocksX1 		.resize(cdCount);
		set.BlocksY1 		.resize(cdCount);
		set.BlocksW 		.resize(cdCount);
		set.BlocksH 		.resize(cdCount);
		set.Blocks 			.resize(collidesCount);
		set.AnimateCount 	= animateCount;
		set.AnimateNames 	.resize(animateCount);
		set.FrameAnimate 	.resize(animateCount);
		set.FrameCDMap 		.resize(animateCount);
		set.FrameCDAtk 		.resize(animateCount);
		set.FrameCDDef 		.resize(animateCount);
		set.FrameCDExt 		.resize(animateCount);
				
		for (map<string, XMLNode*>::iterator it=sprite.childBegin(); 
			 it!=sprite.childEnd(); ++it) 
		{
			XMLNode &e = *(it->second);

			if (stringEquals(e.getName(), "scene_part")) 
			{
				int index = e.getAttributeAsInt("index");
				set.PartTileID[index] 		= e.getAttributeAsInt("tile");
				set.PartX[index] 			= e.getAttributeAsInt("x");
				set.PartY[index] 			= e.getAttributeAsInt("y");
				set.PartTileTrans[index]	= e.getAttributeAsInt("trans");
			}
			else if (stringEquals(e.getName(), "scene_frame")) 
			{
				int index = Integer.parseInt(e.getAttribute("index"));
				int frameCount = Integer.parseInt(e.getAttribute("data_size"));
				set.Parts[index] = new short[frameCount];
				if (frameCount > 0) {
					String[] data = CUtil.splitString(e.getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Parts[index][f] = Short.parseShort(data[f]);
					}
				}
			}
			else if (stringEquals(e.getName(), "cd_part")) 
			{
				int index = Integer.parseInt(e.getAttribute("index"));
				set.BlocksMask[index]	= Integer.parseInt(e.getAttribute("mask"));
				set.BlocksX1[index] 	= Short.parseShort(e.getAttribute("x1"));
				set.BlocksY1[index] 	= Short.parseShort(e.getAttribute("y1"));
				set.BlocksW[index] 		= Short.parseShort(e.getAttribute("width"));
				set.BlocksH[index] 		= Short.parseShort(e.getAttribute("height"));
			}
			else if (stringEquals(e.getName(), "cd_frame")) 
			{
				int index = Integer.parseInt(e.getAttribute("index"));
				int frameCount = Integer.parseInt(e.getAttribute("data_size"));
				set.Blocks[index] = new short[frameCount];
				if (frameCount > 0) {
					String[] data = CUtil.splitString(e.getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Blocks[index][f] = Short.parseShort(data[f]);
					}
				}
			}
			else if (stringEquals(e.getName(), "frames"))
			{
				StringReader AnimateNamesReader = new StringReader(e.getAttribute("names"));
				String frame_counts[] 	= CUtil.splitString(e.getAttribute("counts"), ",");
				String frame_animate[] 	= getArray2D(e.getAttribute("animates"));
				String frame_cd_map[] 	= getArray2D(e.getAttribute("cd_map"));
				String frame_cd_atk[] 	= getArray2D(e.getAttribute("cd_atk"));
				String frame_cd_def[] 	= getArray2D(e.getAttribute("cd_def"));
				String frame_cd_ext[] 	= getArray2D(e.getAttribute("cd_ext"));
				
				for (int i = 0; i < animateCount; i++) {
					set.AnimateNames[i] = TextDeserialize.getString(AnimateNamesReader);
					int frameCount = Integer.parseInt(frame_counts[i]);
					String[] animate = CUtil.splitString(frame_animate[i], ",");
					String[] cd_map = CUtil.splitString(frame_cd_map[i], ",");
					String[] cd_atk = CUtil.splitString(frame_cd_atk[i], ",");
					String[] cd_def = CUtil.splitString(frame_cd_def[i], ",");
					String[] cd_ext = CUtil.splitString(frame_cd_ext[i], ",");
					set.FrameAnimate[i] = new short[frameCount];
					set.FrameCDMap[i] = new short[frameCount];
					set.FrameCDAtk[i] = new short[frameCount];
					set.FrameCDDef[i] = new short[frameCount];
					set.FrameCDExt[i] = new short[frameCount];
					for (int f = 0; f < frameCount; f++) {
						set.FrameAnimate[i][f] = Short.parseShort(animate[f]);
						set.FrameCDMap[i][f] = Short.parseShort(cd_map[f]);
						set.FrameCDAtk[i][f] = Short.parseShort(cd_atk[f]);
						set.FrameCDDef[i][f] = Short.parseShort(cd_def[f]);
						set.FrameCDExt[i][f] = Short.parseShort(cd_ext[f]);
					}
				}
				
			}
		}
	}
	
	private void initLevel(Element level)  throws IOException 
	{
		//		Integer.parseInt(level.getAttribute("world_count"));
		
		NodeList list = level.getChildNodes();
		
		for (int i = list.getLength() - 1; i >= 0; --i) {
			Node node = list.item(i);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("world")) {
					initWorld(e);
				}
			}
		}
	}
	
	
	private void initWorld(Element world) throws IOException 
	{
		WorldSet set = new WorldSet(
									Integer.parseInt(world.getAttribute("index")), 
									world.getAttribute("name"));
		
		set.GridXCount	= Integer.parseInt(world.getAttribute("grid_x_count"));
		set.GridYCount	= Integer.parseInt(world.getAttribute("grid_y_count"));
		set.GridW		= Integer.parseInt(world.getAttribute("grid_w"));
		set.GridH		= Integer.parseInt(world.getAttribute("grid_h"));
		set.Width		= Integer.parseInt(world.getAttribute("width"));
		set.Height		= Integer.parseInt(world.getAttribute("height"));
		
		//		int maps_count	= Integer.parseInt(world.getAttribute("unit_count_map"));
		//		int sprs_count	= Integer.parseInt(world.getAttribute("unit_count_sprite"));
		//		int wpss_count	= Integer.parseInt(world.getAttribute("waypoint_count"));
		//		int wrss_count	= Integer.parseInt(world.getAttribute("region_count"));
		
		set.Data		= world.getAttribute("data");
		
		int terrains_count = set.GridXCount * set.GridYCount;
		set.Terrian = new int[set.GridXCount][set.GridYCount];
		String terrains[] = CUtil.splitString(world.getAttribute("terrain"), ",");
		for (int i = 0; i < terrains_count; i++) {
			int x = i / set.GridYCount;
			int y = i % set.GridYCount;
			set.Terrian[x][y] = Integer.parseInt(terrains[i]);
		}
		
		NodeList list = world.getChildNodes();
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("unit_map")) 
				{
					WorldSet.MapObject map = new WorldSet.MapObject();
					map.Index 		= Integer.parseInt(e.getAttribute("index"));
					map.UnitName 	= e.getAttribute("map_name");
					map.MapID 		= e.getAttribute("id");
					map.X 			= Integer.parseInt(e.getAttribute("x"));
					map.Y 			= Integer.parseInt(e.getAttribute("y"));
					map.ImagesID 	= e.getAttribute("images");
					map.Data		= e.getAttribute("data");
					set.Maps.put(map.Index, map);
				}
				else if (e.getNodeName().equals("unit_sprite"))
				{
					WorldSet.SpriteObject spr = new WorldSet.SpriteObject();
					spr.Index 		= Integer.parseInt(e.getAttribute("index"));
					spr.UnitName 	= e.getAttribute("spr_name");
					spr.SprID 		= e.getAttribute("id");
					spr.Anim		= Integer.parseInt(e.getAttribute("animate_id"));
					spr.Frame		= Integer.parseInt(e.getAttribute("frame_id"));
					spr.X 			= Integer.parseInt(e.getAttribute("x"));
					spr.Y 			= Integer.parseInt(e.getAttribute("y"));
					spr.ImagesID 	= e.getAttribute("images");
					spr.Data		= e.getAttribute("data");
					set.Sprs.put(spr.Index, spr);
				}
				else if (e.getNodeName().equals("waypoint"))
				{
					WorldSet.WaypointObject wp = new WorldSet.WaypointObject();
					wp.Index 		= Integer.parseInt(e.getAttribute("index"));
					wp.X 			= Integer.parseInt(e.getAttribute("x"));
					wp.Y 			= Integer.parseInt(e.getAttribute("y"));
					wp.Data			= getArray1D(e.getAttribute("data"));
					set.WayPoints.put(wp.Index, wp);
				}
				else if (e.getNodeName().equals("region"))
				{
					WorldSet.RegionObject wr = new WorldSet.RegionObject();
					wr.Index 		= Integer.parseInt(e.getAttribute("index"));
					wr.X 			= Integer.parseInt(e.getAttribute("x"));
					wr.Y 			= Integer.parseInt(e.getAttribute("y"));
					wr.W 			= Integer.parseInt(e.getAttribute("width"));
					wr.H 			= Integer.parseInt(e.getAttribute("height"));
					wr.Data			= getArray1D(e.getAttribute("data"));
					set.Regions.put(wr.Index, wr);
				}
			}
		}
		
		for (int s = list.getLength() - 1; s >= 0; --s) 
		{
			Node node = list.item(s);
			if (node instanceof Element) {
				Element e = (Element)node;
				if (e.getNodeName().equals("waypoint_link")) {
					int start	= Integer.parseInt(e.getAttribute("start"));
					int end 	= Integer.parseInt(e.getAttribute("end"));
					set.WayPoints.get(start).Nexts.add(set.WayPoints.get(end));
				}
			}
		}
	}
	
	//	------------------------------------------------------------------------------------------------
	
	@Override
	public String getImageExtentions() {
		return image_type;
	}
	
	@Override
	public boolean isGroup() {
		return image_group;
	}
	
	@Override
	public boolean isTile() {
		return image_tile;
	}
	
	@Override
	public void dispose() 
	{}
	
	
	
	

}; // namespace
