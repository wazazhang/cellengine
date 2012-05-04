//
//  CXMLOutputLoader.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "MFCellResourceXML.h"
#include "MFXMLParser.h"
#include "MFUtil.h"

#include "cocos2d.h"

namespace mf
{
	using namespace std;


	/**
	* input "{1234},{5678}"
	* return [1234][5678]
	*/
	vector<string> getArray2D(string const &src)
	{
		string text = stringReplace(src, "{", "");
		vector < string > texts = stringSplit(text, "},");
		for (int i = texts.size() - 1; i >= 0; --i) {
			texts[i] = stringTrim(texts[i]);
		}
		return texts;
	}

	/////////////////////////////////////////////////////////////////////////////


	XMLOutputLoader::XMLOutputLoader(XMLNode* node, string const &filepath)
	{
		m_filepath	= filepath;
		m_filedir	= subString(m_filepath, 0, m_filepath.rfind("/"));
		init(node);
	}
	
	XMLOutputLoader::~XMLOutputLoader()
	{
	}
	
	/////////////////////////////////////////////////////////////////////////////
	string XMLOutputLoader::getName()
	{
		return m_filepath;
	}

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

		for (int i=0; i<set.Count;i++) {
			set.ClipsX[i] = 0;
			set.ClipsY[i] = 0;
			set.ClipsW[i] = 0;
			set.ClipsH[i] = 0;
		}
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
		set.LayerCount		= doc->getAttributeAsInt("layer_count");

		int cdCount 		= doc->getAttributeAsInt("cd_part_count");
		set.BlocksType 		.resize(cdCount);
		set.BlocksMask 		.resize(cdCount);
		set.BlocksX1 		.resize(cdCount);
		set.BlocksY1 		.resize(cdCount);
		set.BlocksX2 		.resize(cdCount);
		set.BlocksY2 		.resize(cdCount);
		set.BlocksW 		.resize(cdCount);
		set.BlocksH 		.resize(cdCount);
		
		
		set.TerrainTiles2D	.resize(set.LayerCount);
		set.TerrainFlips2D	.resize(set.LayerCount);
		set.TerrainBlocks2D	.resize(set.LayerCount);
		for (int ly=0; ly<set.LayerCount; ly++) {
			set.TerrainTiles2D[ly]	.resize(set.YCount);
			set.TerrainFlips2D[ly]	.resize(set.YCount);
			set.TerrainBlocks2D[ly]	.resize(set.YCount);
			for (int cy=0; cy<set.YCount; cy++) {
				set.TerrainTiles2D[ly][cy].resize(set.XCount);
				set.TerrainFlips2D[ly][cy].resize(set.XCount);
				set.TerrainBlocks2D[ly][cy].resize(set.XCount);
			}
		}
		
		
		for (vector<XMLNode*>::const_iterator it=doc->childBegin(); 
			 it!=doc->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
			
			if (stringEquals(e->getName(), "cd_part")) 
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
			else if (stringEquals(e->getName(), "layer")) 
			{
				int lyIndex = e->getAttributeAsInt("index");

				vector<string> tile_matrix	= getArray2D(e->getAttribute("tile_matrix"));
				vector<string> flip_matrix	= getArray2D(e->getAttribute("flip_matrix"));
				vector<string> cd_matrix 	= getArray2D(e->getAttribute("flag_matrix"));

				for (int y = 0; y < set.YCount; y++) 
				{
					vector<string> tline = stringSplit(tile_matrix[y], ",");
					vector<string> fline = stringSplit(flip_matrix[y], ",");
					vector<string> cline = stringSplit(cd_matrix[y],   ",");

					for (int x = 0; x < set.XCount; x++) 
					{
						set.TerrainTiles2D[lyIndex][y][x] = stringToInt(tline[x]);
						set.TerrainFlips2D[lyIndex][y][x] = stringToInt(fline[x]);
						set.TerrainBlocks2D[lyIndex][y][x] = stringToInt(cline[x]);
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
		WorldSet set(
			world->getAttributeAsInt("index"), 
			world->getAttribute("name"));

		set.GridXCount	= (world->getAttributeAsInt("grid_x_count"));
		set.GridYCount	= (world->getAttributeAsInt("grid_y_count"));
		set.GridW		= (world->getAttributeAsInt("grid_w"));
		set.GridH		= (world->getAttributeAsInt("grid_h"));
		set.Width		= (world->getAttributeAsInt("width"));
		set.Height		= (world->getAttributeAsInt("height"));

		set.Data		= world->getAttribute("data");

		int terrains_count = set.GridXCount * set.GridYCount;
		vector<string> terrains = stringSplit(world->getAttribute("terrain"), ",", INT_MAX);
		set.Terrian.resize(set.GridXCount);//= new int[set.GridXCount][set.GridYCount];
		for (int x=0, i=0; x<set.GridXCount; ++x) {
			set.Terrian[x].resize(set.GridYCount);
			for (int y=0; y<set.GridYCount; ++y) {
				set.Terrian[x][y] = stringToInt(terrains[i]);
				i++;
			}
		}
		
		for (vector<XMLNode*>::const_iterator it=world->childBegin(); 
			it!=world->childEnd(); ++it) 
		{
			XMLNode *e = (*it);
		
			if (stringEquals(e->getName(), "unit_map")) 
			{
				WorldObjectMap map;
				map.Index 		= e->getAttributeAsInt("index");
				map.UnitName 	= e->getAttribute("map_name");
				map.MapID 		= e->getAttribute("id");
				map.X 			= e->getAttributeAsInt("x");
				map.Y 			= e->getAttributeAsInt("y");
				map.ImagesID 	= e->getAttribute("images");
				map.Data		= e->getAttribute("data");
				set.Maps[map.Index] = map;
			}
			else if (stringEquals(e->getName(), "unit_sprite")) 
			{
				WorldObjectSprite spr;
				spr.Index 		= e->getAttributeAsInt("index");
				spr.UnitName 	= e->getAttribute("spr_name");
				spr.SprID 		= e->getAttribute("id");
				spr.Anim		= e->getAttributeAsInt("animate_id");
				spr.Frame		= e->getAttributeAsInt("frame_id");
				spr.X 			= e->getAttributeAsInt("x");
				spr.Y 			= e->getAttributeAsInt("y");
				spr.ImagesID 	= e->getAttribute("images");
				spr.Data		= e->getAttribute("spr_data");
				set.Sprs[spr.Index] = spr;
			}
			else if (stringEquals(e->getName(), "unit_image")) 
			{
				WorldObjectImage img;
				img.Index 		= e->getAttributeAsInt("index");
				img.UnitName 	= e->getAttribute("img_name");
				img.ImagesID 	= e->getAttribute("id");
				img.TileID		= e->getAttributeAsInt("tile_id");
				img.Anchor		= WorldObjectImage::stringToAnchor(e->getAttribute("anchor"));
				img.Trans		= WorldObjectImage::stringToTrans(e->getAttribute("trans"));
				img.X 			= e->getAttributeAsInt("x");
				img.Y 			= e->getAttributeAsInt("y");
				img.Data		= e->getAttribute("img_data");
				
				set.Images[img.Index] = img;
			}
			else if (stringEquals(e->getName(), "waypoint")) 
			{
				WorldObjectWaypoint wp;
				wp.Index 		= e->getAttributeAsInt("index");
				wp.X 			= e->getAttributeAsInt("x");
				wp.Y 			= e->getAttributeAsInt("y");
				wp.Data			= e->getAttribute("path_data");
				set.WayPoints[wp.Index] = wp;
			}
			else if (stringEquals(e->getName(), "region")) 
			{
				WorldObjectRegion wr;
				wr.Index 		= e->getAttributeAsInt("index");
				wr.X 			= e->getAttributeAsInt("x");
				wr.Y 			= e->getAttributeAsInt("y");
				wr.W 			= e->getAttributeAsInt("width");
				wr.H 			= e->getAttributeAsInt("height");
				wr.Data			= e->getAttribute("region_data");
				set.Regions[wr.Index] = wr;
			}
			else if (stringEquals(e->getName(), "event")) 
			{
				WorldObjectEvent ev;
				ev.Index 		= e->getAttributeAsInt("index");
				ev.ID			= e->getAttributeAsInt("id");
				ev.X 			= e->getAttributeAsInt("x");
				ev.Y 			= e->getAttributeAsInt("y");
				ev.EventName 	= e->getAttribute("event_name");
				ev.EventFile 	= e->getAttribute("event_file");
				ev.Data		 	= e->getAttribute("event_data");
				set.Events[ev.Index] = ev;
			}
		}

		for (vector<XMLNode*>::const_iterator it=world->childBegin(); 
			it!=world->childEnd(); ++it) 
		{
			XMLNode *e = (*it);

			if (stringEquals(e->getName(), "waypoint_link")) 
			{
				int start	= e->getAttributeAsInt("start");
				int end 	= e->getAttributeAsInt("end");

				WorldObjectWaypoint &r_start = set.WayPoints[start];
				WorldObjectWaypoint &r_end = set.WayPoints[end];
				r_start.Nexts.push_back(&r_end);
			}
		}

		m_worlds[set.Name] = set;
	}
	
	
	ITiles*	XMLOutputLoader::createImagesFromSet(ImagesSet const &img)
	{
		XMLTiles *tiles = new XMLTiles(img, this);
		return tiles;
	}

	//-------------------------------------------------------------------------------------------------------
	// XMLTiles
	//-------------------------------------------------------------------------------------------------------
	
	XMLTiles::XMLTiles(ImagesSet const &img, XMLOutputLoader *loader): 
	TilesGroup(img.Count, 1, 1) 
	{
		this->img_path = std::string(loader->m_filedir)
			.append("/")
			.append(img.Name)
			.append(".")
			.append(loader->getImageExtentions());
		
		this->pTexture = cocos2d::CCTextureCache::sharedTextureCache()->addImage(img_path.c_str());

		if (this->pTexture != NULL) 
		{
			this->tuxture_name = pTexture->getName();
			this->imageWidth = pTexture->getPixelsWide();
			this->imageHeight = pTexture->getPixelsHigh();
			for (int i=0; i<count; ++i) 
			{
				setTile(i,
					img.ClipsX[i], 
					img.ClipsY[i], 
					img.ClipsW[i], 
					img.ClipsH[i]);
			}
		}
		else
		{
			this->tuxture_name = 0;
		}
	}
	
	XMLTiles::~XMLTiles()
	{
		if (this->pTexture != NULL) {
			cocos2d::CCTextureCache::sharedTextureCache()->removeTexture(this->pTexture);
		}
	}
	
}; // namespace
