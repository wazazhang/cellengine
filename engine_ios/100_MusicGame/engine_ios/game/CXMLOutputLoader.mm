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
	using namespace com_cell;
	using namespace std;
	
	XMLOutputLoader::XMLOutputLoader(char const *filename)
	{
		m_filepath	= filename;
		m_filedir	= subString(m_filepath, 0, m_filepath.rfind("/"));
		
		NSLog(@"init cpj project file : %s", filename);
		
		XMLNode* node = parseXML(filename);
		init(node);
//		NSLog(@"\n%s", node->toString().c_str());
		delete node;
	}
	
	XMLOutputLoader::~XMLOutputLoader()
	{
		
	}
	
	/////////////////////////////////////////////////////////////////////////////
	
	bool XMLOutputLoader::isTile()
	{
		return m_image_tile;
	}
	
	bool XMLOutputLoader::isGroup()
	{
		return m_image_group;
	}
	
	string XMLOutputLoader::getImageExtentions()
	{
		return m_image_type;
	}
	
	void  XMLOutputLoader::getSetObjects(map<string, ImagesSet>	&images,
										 map<string, SpriteSet>	&sprites,
										 map<string, MapSet>	&maps,
										 map<string, WorldSet>	&worlds)
	{
		images	= m_images;
		sprites = m_sprites;
		maps	= m_maps;
		worlds	= m_worlds;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	
	void XMLOutputLoader::init(XMLNode *doc)
	{
		m_images.clear();
		m_sprites.clear();
		m_maps.clear();
		m_worlds.clear();
		
		for (vector<XMLNode*>::const_iterator it=doc->childBegin(); 
			 it!=doc->childEnd(); ++it) 
		{
			XMLNode *child = (*it);
			
			if (stringEquals(child->getName(), "IMAGE_TYPE")) {
				m_image_type = child->value;
			}
			else if (stringEquals(child->getName(), "IMAGE_TILE")) {
				m_image_tile = stringToBool(child->value);
			}
			else if (stringEquals(child->getName(), "IMAGE_GROUP")) {
				m_image_group = stringToBool(child->value);
			}
			else if (stringEquals(child->getName(), "level")) {
				initLevel(child);
			}
			else if (stringEquals(child->getName(), "resource")) {
				initResource(child);
			}
		}
	}

	
	void XMLOutputLoader::initResource(XMLNode *resource)
	{
		//		Integer.parseInt(resource.getAttribute("images_count"));
		//		Integer.parseInt(resource.getAttribute("map_count"));
		//		Integer.parseInt(resource.getAttribute("sprite_count"));
		
		for (vector<XMLNode*>::const_iterator it=resource->childBegin(); 
			 it!=resource->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
			
			if (stringEquals(e->getName(), "images")) {
				initImages(e);
			}
			else if (stringEquals(e->getName(), "map")) {
				initMap(e);
			}
			else if (stringEquals(e->getName(), "sprite")) {
				initSprite(e);
			}
		}
	}
	
	void XMLOutputLoader::initImages(XMLNode *images)
	{
		ImagesSet set(images->getAttributeAsInt("index"),
					  images->getAttribute("name"));
	
		set.Count = images->getAttributeAsInt("size");
		set.ClipsX.resize(set.Count);
		set.ClipsY.resize(set.Count);
		set.ClipsW.resize(set.Count);
		set.ClipsH.resize(set.Count);
		set.ClipsKey.resize(set.Count);

		for (vector<XMLNode*>::const_iterator it=images->childBegin(); 
			 it!=images->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
			
			if (stringEquals(e->getName(), "clip")) {
				int i = e->getAttributeAsInt("index");
				set.ClipsX[i] 	= e->getAttributeAsInt("x");
				set.ClipsY[i] 	= e->getAttributeAsInt("y");
				set.ClipsW[i] 	= e->getAttributeAsInt("width");
				set.ClipsH[i] 	= e->getAttributeAsInt("height");
				set.ClipsKey[i] = e->getAttribute("data");
			}
		}
		
		m_images[set.Name] = set;
	}
	
	void XMLOutputLoader::initMap(XMLNode *doc)
	{
		MapSet set(doc->getAttributeAsInt("index"), 
				   doc->getAttribute("name"));
		
		set.ImagesName 		= doc->getAttribute("images_name");
		set.XCount 			= doc->getAttributeAsInt("xcount");
		set.YCount 			= doc->getAttributeAsInt("ycount");
		set.CellW 			= doc->getAttributeAsInt("cellw");
		set.CellH 			= doc->getAttributeAsInt("cellh");
		
		int scenePartCount 	= doc->getAttributeAsInt("scene_part_count");
		int animateCount 	= doc->getAttributeAsInt("scene_frame_count");
		int cdCount 		= doc->getAttributeAsInt("cd_part_count");
		
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
		
		
		for (vector<XMLNode*>::const_iterator it=doc->childBegin(); 
			 it!=doc->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
			
			if (stringEquals(e->getName(), "scene_part")) 
			{
				int index 			= e->getAttributeAsInt("index");
				set.TileID[index]	= e->getAttributeAsInt("tile");
				set.TileTrans[index]= e->getAttributeAsInt("trans");
			}
			else if (stringEquals(e->getName(), "scene_frame")) 
			{
				int index		= e->getAttributeAsInt("index");
				int frameCount	= e->getAttributeAsInt("data_size");
				set.Animates[index].resize(frameCount);
				if (frameCount > 0) {
					vector<string> data = stringSplit(e->getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Animates[index][f] = stringToInt(data[f]);
					}
				}
			}
			else if (stringEquals(e->getName(), "cd_part")) 
			{
				int index				= e->getAttributeAsInt("index");
//				set.BlocksType[index]	= stringEquals(e.getAttribute("type"), "rect") ?
//											(CCD::CD_TYPE_RECT) : (CCD::CD_TYPE_LINE);
				set.BlocksType[index]	= CCD::CD_TYPE_RECT;
				set.BlocksMask[index]	= e->getAttributeAsInt("mask");
				set.BlocksX1[index] 	= e->getAttributeAsInt("x1");
				set.BlocksY1[index] 	= e->getAttributeAsInt("y1");
				set.BlocksX2[index] 	= e->getAttributeAsInt("x2");
				set.BlocksY2[index] 	= e->getAttributeAsInt("y2");
				set.BlocksW[index] 		= e->getAttributeAsInt("width");
				set.BlocksH[index] 		= e->getAttributeAsInt("height");
			}
			else if (stringEquals(e->getName(), "matrix")) 
			{
				vector<string> tile_matrix	= getArray2D(e->getAttribute("tile_matrix"));
				vector<string> cd_matrix 	= getArray2D(e->getAttribute("flag_matrix"));
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
		
		m_maps[set.Name] = set;
		
	}
	
	void XMLOutputLoader::initSprite(XMLNode *sprite)
	{
		SpriteSet set(sprite->getAttributeAsInt("index"), 
					  sprite->getAttribute("name"));
		
		set.ImagesName		= sprite->getAttribute("images_name");
		
		int scenePartCount 	= (sprite->getAttributeAsInt("scene_part_count"));
		int sceneFrameCount = (sprite->getAttributeAsInt("scene_frame_count"));
		int cdCount 		= (sprite->getAttributeAsInt("cd_part_count"));
		int collidesCount 	= (sprite->getAttributeAsInt("cd_frame_count"));
		int animateCount 	= (sprite->getAttributeAsInt("animate_count"));
		
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
				
		for (vector<XMLNode*>::const_iterator it=sprite->childBegin(); 
			 it!=sprite->childEnd(); ++it) 
		{
			XMLNode *e = (*it);

			if (stringEquals(e->getName(), "scene_part")) 
			{
				int index = e->getAttributeAsInt("index");
				set.PartTileID[index] 		= e->getAttributeAsInt("tile");
				set.PartX[index] 			= e->getAttributeAsInt("x");
				set.PartY[index] 			= e->getAttributeAsInt("y");
				set.PartTileTrans[index]	= e->getAttributeAsInt("trans");
			}
			else if (stringEquals(e->getName(), "scene_frame")) 
			{
				int index		= e->getAttributeAsInt("index");
				int frameCount	= e->getAttributeAsInt("data_size");
				set.Parts[index].resize(frameCount);
				if (frameCount > 0) {
					vector<string> data = stringSplit(e->getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Parts[index][f] = stringToInt(data[f]);
					}
				}
			}
			else if (stringEquals(e->getName(), "cd_part")) 
			{
				int index = e->getAttributeAsInt("index");
				set.BlocksMask[index]	= (e->getAttributeAsInt("mask"));
				set.BlocksX1[index] 	= (e->getAttributeAsInt("x1"));
				set.BlocksY1[index] 	= (e->getAttributeAsInt("y1"));
				set.BlocksW[index] 		= (e->getAttributeAsInt("width"));
				set.BlocksH[index] 		= (e->getAttributeAsInt("height"));
			}
			else if (stringEquals(e->getName(), "cd_frame")) 
			{
				int index = (e->getAttributeAsInt("index"));
				int frameCount = (e->getAttributeAsInt("data_size"));
				set.Blocks[index] .resize(frameCount);
				if (frameCount > 0) {
					vector<string> data = stringSplit(e->getAttribute("data"), ",");
					for (int f = 0; f < frameCount; f++) {
						set.Blocks[index][f] = stringToInt(data[f]);
					}
				}
			}
			else if (stringEquals(e->getName(), "frames"))
			{
				TextReader AnimateNamesReader(e->getAttribute("names"));
				vector<string> frame_counts 	= stringSplit(e->getAttribute("counts"), ",");
				vector<string> frame_animate 	= getArray2D(e->getAttribute("animates"));
				vector<string> frame_cd_map 	= getArray2D(e->getAttribute("cd_map"));
				vector<string> frame_cd_atk 	= getArray2D(e->getAttribute("cd_atk"));
				vector<string> frame_cd_def 	= getArray2D(e->getAttribute("cd_def"));
				vector<string> frame_cd_ext 	= getArray2D(e->getAttribute("cd_ext"));
				
				for (int i = 0; i < animateCount; i++) {
					set.AnimateNames[i] = TextDeserialize::getString(AnimateNamesReader);
					int frameCount = stringToInt(frame_counts[i]);
					vector<string> animate = stringSplit(frame_animate[i], ",");
					vector<string> cd_map = stringSplit(frame_cd_map[i], ",");
					vector<string> cd_atk = stringSplit(frame_cd_atk[i], ",");
					vector<string> cd_def = stringSplit(frame_cd_def[i], ",");
					vector<string> cd_ext = stringSplit(frame_cd_ext[i], ",");
					set.FrameAnimate[i] .resize(frameCount);
					set.FrameCDMap[i] .resize(frameCount);
					set.FrameCDAtk[i] .resize(frameCount);
					set.FrameCDDef[i] .resize(frameCount);
					set.FrameCDExt[i] .resize(frameCount);
					for (int f = 0; f < frameCount; f++) {
						set.FrameAnimate[i][f] = stringToInt(animate[f]);
						set.FrameCDMap[i][f] = stringToInt(cd_map[f]);
						set.FrameCDAtk[i][f] = stringToInt(cd_atk[f]);
						set.FrameCDDef[i][f] = stringToInt(cd_def[f]);
						set.FrameCDExt[i][f] = stringToInt(cd_ext[f]);
					}
				}
				
			}
		}
		m_sprites[set.Name] = set;
	}
	
	void XMLOutputLoader::initLevel(XMLNode *level)
	{
		//		Integer.parseInt(level.getAttribute("world_count"));
				
		for (vector<XMLNode*>::const_iterator it=level->childBegin(); 
			 it!=level->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
			if (stringEquals(e->getName(), "world")) {
				initWorld(e);
			}
		}
	}
	
	void XMLOutputLoader::initWorld(XMLNode *world)
	{
		
	}
	
	/*
	void XMLOutputLoader::initWorld(XMLNode &world)
	{
		WorldSet set(world.getAttributeAsInt("index"), 
					 world.getAttribute("name"));
		
		set.GridXCount	= (world.getAttributeAsInt("grid_x_count"));
		set.GridYCount	= (world.getAttributeAsInt("grid_y_count"));
		set.GridW		= (world.getAttributeAsInt("grid_w"));
		set.GridH		= (world.getAttributeAsInt("grid_h"));
		set.Width		= (world.getAttributeAsInt("width"));
		set.Height		= (world.getAttributeAsInt("height"));
		
		//		int maps_count	= Integer.parseInt(world.getAttribute("unit_count_map"));
		//		int sprs_count	= Integer.parseInt(world.getAttribute("unit_count_sprite"));
		//		int wpss_count	= Integer.parseInt(world.getAttribute("waypoint_count"));
		//		int wrss_count	= Integer.parseInt(world.getAttribute("region_count"));
		
		set.Data		= world.getAttribute("data");
		
		int terrains_count = set.GridXCount * set.GridYCount;
		set.Terrian .resize(set.GridXCount);//= new int[set.GridXCount][set.GridYCount];
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
	*/
	
	
	CTiles* XMLOutputLoader::createImagesFromSet(ImagesSet const &img)
	{
		return new XMLTiles(img, this);
	}
	
	//-------------------------------------------------------------------------------------------------------
	// XMLTiles
	//-------------------------------------------------------------------------------------------------------
	
	XMLTiles::XMLTiles(ImagesSet const &img, XMLOutputLoader *output)
	{
		int count = img.Count;
		tiles.resize(count);
		
		std::string img_path = std::string(output->m_filedir)
		.append("/")
		.append(img.Name)
		.append(".")
		.append(output->getImageExtentions());
		
		Image* src_img = GFXManager::createImage(img_path.c_str());
		
		for(int i=0; i<count; i++)
		{
			if (img.ClipsW[i] != 0 && img.ClipsH[i] != 0) 
			{
				tiles[i] = src_img->subImage(img.ClipsX[i], 
											 img.ClipsY[i],
											 img.ClipsW[i], 
											 img.ClipsH[i]);
			}
		}
		
		delete src_img;
	}
	
	XMLTiles::~XMLTiles()
	{
		for (vector<Image*>::iterator it=tiles.begin(); 
			 it!=tiles.end(); ++it) {
			Image* img = (*it);
			if (img != NULL) {
				delete img;
			}
		}
	}
	
	Image*	XMLTiles::getImage(int index)
	{
		return tiles[index];
	}
	
	int		XMLTiles::getWidth(int index)
	{
		Image* img = tiles[index];
		if (img != NULL) {
			return img->getWidth();
		}
		return 0;
	}
	
	int		XMLTiles::getHeight(int index)
	{
		Image* img = tiles[index];
		if (img != NULL) {
			return img->getHeight();
		}
		return 0;
	}
	
	int		XMLTiles::getCount()
	{
		return tiles.size();
	}
	
	void	XMLTiles::render(Graphics2D &g, int index, float PosX, float PosY, int Trans)
	{
		Image* img = tiles[index];
		if (img != NULL) {
			if (Trans != TRANS_NONE) {
				g.pushTransform();
				transform(g, img->getWidth(), img->getHeight(), Trans);
				g.drawImage(img, PosX, PosY);
				g.popTransform();
			} else {
				g.drawImage(img, PosX, PosY);
			}
		}
	}

	
	
	
	
}; // namespace
