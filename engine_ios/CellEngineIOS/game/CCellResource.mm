//
//  CCellResource.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-20.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CCellResource.h"
#include "CXMLOutputLoader.h"

namespace com_cell_game
{
	
	
	//-------------------------------------------------------------------------------------
	// OutputLoader
	//-------------------------------------------------------------------------------------
	
//	CTiles* OutputLoader::createImagesFromSet(ImagesSet const &img)
//	{
//		int count = img.Count;
//		stuff.buildImages(image,count);
//		for(int i=0;i<count;i++){
//			int x = img.ClipsX[i];	// --> s16 * count
//			int y = img.ClipsY[i];	// --> s16 * count
//			int w = img.ClipsW[i];	// --> s16 * count
//			int h = img.ClipsH[i];	// --> s16 * count
//			stuff.addTile(x,y,w,h);
//		}
//		return NULL;
//	}
	
	CSpriteMeta* OutputLoader::createSpriteFromSet(SpriteSet const &tsprite, CTiles *tiles)
	{
			
		//scene parts
		int scenePartCount = tsprite.PartTileID.size();	// --> u16
		CAnimates animates(scenePartCount, tiles);
		for(int i=0;i<scenePartCount;i++){
			animates.addPart(tsprite.PartX[i], 
							 tsprite.PartY[i],
							 tsprite.PartTileID[i], 
							 tsprite.PartTileTrans[i]);
		}
		//scene frames
		animates.setFrames(tsprite.Parts);
		
		//cd parts
		int cdCount = tsprite.BlocksMask.size();	// --> u16
		CCollides collides(cdCount);
		for(int i=0;i<cdCount;i++){
			collides.addCDRect(tsprite.BlocksMask[i],
							   tsprite.BlocksX1[i], 
							   tsprite.BlocksY1[i],
							   tsprite.BlocksW[i], 
							   tsprite.BlocksH[i]);
		}
		//cd frames
		collides.setFrames(tsprite.Blocks);

		CSpriteMeta *ret = new CSpriteMeta();
		ret->animates = animates;
		ret->collides = collides;
		ret->AnimateNames = tsprite.AnimateNames;
		ret->FrameAnimate = tsprite.FrameAnimate; 
		ret->FrameCDMap = tsprite.FrameCDMap; 
		ret->FrameCDAtk = tsprite.FrameCDAtk; 
		ret->FrameCDDef = tsprite.FrameCDDef; 
		ret->FrameCDExt = tsprite.FrameCDExt;
		return ret;
	}
	
	
	//-------------------------------------------------------------------------------------
	// Cell Resource
	//-------------------------------------------------------------------------------------
	
	CellResource::CellResource(const char *filename)
	{
		if (stringEndWith(filename, "xml")) {
			init(new XMLOutputLoader(filename));
		}
	}
	
	CellResource::CellResource(OutputLoader *adapter)
	{
		init(adapter);
	}
	
	void CellResource::init(OutputLoader *adapter)
	{
		output_adapter = adapter;
		output_adapter->getSetObjects(ImgTable, SprTable, MapTable, WorldTable);
		
		for (map<string, ImagesSet>::iterator it=ImgTable.begin(); 
			 it != ImgTable.end(); ++it) {
			ImagesSet &img = (it->second);
			CTiles* tiles = output_adapter->createImagesFromSet(img);
			if (tiles != NULL) {
				res_map_tiles[img.Name] = tiles;
			}
		}
		
		for (map<string, SpriteSet>::iterator it=SprTable.begin(); 
			 it != SprTable.end(); ++it) {
			SpriteSet &spr = (it->second);
			CTiles* tiles = getImages(spr.ImagesName);
			CSpriteMeta* sprite = output_adapter->createSpriteFromSet(spr, tiles);
			if (sprite != NULL) {
				res_map_sprites[spr.Name] = sprite;
			}
		}
	}
	
	
	CellResource::~CellResource()
	{
		for (map<string, CTiles*>::iterator it = res_map_tiles.begin(); 
			 it!=res_map_tiles.end(); ++it) {
			ICellResource* obj = (it->second);
			delete obj;
		}
		for (map<string, CSpriteMeta*>::iterator it = res_map_sprites.begin(); 
			 it!=res_map_sprites.end(); ++it) {
			ICellResource* obj = (it->second);
			delete obj;
		}
		delete output_adapter;
	}
	
	//	-------------------------------------------------------------------------------------------------------------------------------
	//	Set Object in <setfile>.properties
	//	-------------------------------------------------------------------------------------------------------------------------------
	
	WorldSet	CellResource::getSetWorld	(string const &name)
	{
		return WorldTable[name];
	}
	SpriteSet	CellResource::getSetSprite	(string const &name)
	{
		return SprTable[name];
	}
	MapSet		CellResource::getSetMap		(string const &name)
	{
		return MapTable[name];
	}
	ImagesSet	CellResource::getSetImages	(string const &name)
	{
		return ImgTable[name];
	}

	//	--------------------------------------------------------------------------------------------------------------------------------------------------
	//	utils
	//	--------------------------------------------------------------------------------------------------------------------------------------------------
	
	CTiles*	CellResource::getImages(string const &name)
	{
		return (CTiles*)(res_map_tiles[name]);
	}

	
	CSpriteMeta* CellResource::getSpriteMeta(string const &name)
	{
		return (CSpriteMeta*)(res_map_sprites[name]);
	}

	
	CSprite* CellResource::createSprite	(string const &name)
	{
		CSpriteMeta* meta = getSpriteMeta(name);
		if (meta != NULL) {
			CSprite *ret = new CSprite(meta);
			return ret;
		}
		return NULL;
	}

	
	
	
}; // namespace gametiler