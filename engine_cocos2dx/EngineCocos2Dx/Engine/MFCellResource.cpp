
#include "MFCellResource.h"
#include "MFCellResourceXML.h"
#include "MFUtil.h"
#include "MFXMLParser.h"

namespace mf
{
	using namespace std;

	//-------------------------------------------------------------------------------------
	// Set Object
	//-------------------------------------------------------------------------------------

	SetObject::~SetObject(){}
	
	ImagesSet::ImagesSet(int index, string const &name) {
		Index = index;
		Name = name;
	}

	ImagesSet::ImagesSet() {
	}

	MapSet::MapSet(int index, string const &name) {
		Index = index;
		Name = name;
	}

	MapSet::MapSet() {
	}

	SpriteSet::SpriteSet(int index, string const &name) {
		Index = index;
		Name = name;
	}
	SpriteSet::SpriteSet() {
	}

	// images[PartTileID[Parts[FrameAnimate[anim][frame]][subpart]]];
	int SpriteSet::getPartImageIndex(int anim, int frame, int subpart) {
		return PartTileID[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	char SpriteSet::getPartTrans(int anim, int frame, int subpart) {
		return PartTileTrans[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	int SpriteSet::getPartX(int anim, int frame, int subpart) {
		return PartX[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	int SpriteSet::getPartY(int anim, int frame, int subpart) {
		return PartY[Parts[FrameAnimate[anim][frame]][subpart]];
	}

	WorldSet::WorldSet(int index, string const &name) {
		Index = index;
		Name = name;
	}

	WorldSet::WorldSet() {
	}

	int WorldSet::getTerrainCell(int grid_x, int grid_y) {
		return Terrian[grid_x][grid_y];
	}
	

	//-------------------------------------------------------------------------------------
	// OutputLoader
	//-------------------------------------------------------------------------------------

	CSpriteMeta* OutputLoader::createSpriteFromSet(SpriteSet const &tsprite, ITiles *tiles)
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


	CMapMeta* OutputLoader::createMapFromSet(MapSet const &tmap, ITiles *tiles)
	{
		
		CMapMeta* ret = new CMapMeta(
			tiles, 
			tmap.CellW, tmap.CellH, 
			tmap.LayerCount, 
			tmap.XCount, tmap.YCount
			);
		
		for (int la=0; la<tmap.LayerCount; la++) {
			for (int by=0; by<tmap.YCount; by++) {
				for (int bx=0; bx<tmap.XCount; bx++) {
					ret->fillMapCell(la, bx, by, 
						tmap.TerrainTiles2D[la][by][bx],
						tmap.TerrainFlips2D[la][by][bx],
						tmap.TerrainBlocks2D[la][by][bx]
						);
				}
			}
		}
		for (int i=tmap.BlocksType.size()-1; i>=0; --i) {
			ret->defineBlockType(i,
				tmap.BlocksType[i], tmap.BlocksMask[i],
				tmap.BlocksX1[i], tmap.BlocksY1[i], 
				tmap.BlocksX2[i], tmap.BlocksY2[i]
				);
		}
		return ret;
	}

	//-------------------------------------------------------------------------------------
	// Cell Resource
	//-------------------------------------------------------------------------------------



	CellResource::CellResource(OutputLoader *adapter)
	{
		output_adapter = adapter;
		output_adapter->getSetObjects(ImgTable, SprTable, MapTable, WorldTable);

		for (map<string, ImagesSet>::iterator it=ImgTable.begin(); 
			it != ImgTable.end(); ++it) {
				ImagesSet &img = (it->second);
				ITiles* tiles = output_adapter->createImagesFromSet(img);
				if (tiles != NULL) {
					res_map_tiles[img.Name] = tiles;
				} else {
					printf("error : can not create images from \"%s\"", output_adapter->getName());
				}
		}

		for (map<string, SpriteSet>::iterator it=SprTable.begin(); 
			it != SprTable.end(); ++it) {
				SpriteSet &spr = (it->second);
				ITiles* tiles = getImages(spr.ImagesName);
				CSpriteMeta* sprite = output_adapter->createSpriteFromSet(spr, tiles);
				if (sprite != NULL) {
					res_map_sprites[spr.Name] = sprite;
				}
		}

		for (map<string, MapSet>::iterator it=MapTable.begin(); 
			it != MapTable.end(); ++it) {
				MapSet &map = (it->second);
				ITiles* tiles = getImages(map.ImagesName);
				CMapMeta* cmap = output_adapter->createMapFromSet(map, tiles);
				if (cmap != NULL) {
					res_map_map[map.Name] = cmap;
				}
		}
	}


	CellResource::~CellResource()
	{
		for (map<string, ITiles*>::iterator it = res_map_tiles.begin(); 
			it!=res_map_tiles.end(); ++it) {
				void* obj = (it->second);
				delete obj;
		}
		for (map<string, CSpriteMeta*>::iterator it = res_map_sprites.begin(); 
			it!=res_map_sprites.end(); ++it) {
				void* obj = (it->second);
				delete obj;
		}
		for (map<string, CMapMeta*>::iterator it = res_map_map.begin(); 
			it!=res_map_map.end(); ++it) {
				void* obj = (it->second);
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

	ITiles*	CellResource::getImages(string const &name)
	{
		return (ITiles*)(res_map_tiles[name]);
	}


	CSpriteMeta* CellResource::getSpriteMeta(string const &name)
	{
		return (CSpriteMeta*)(res_map_sprites[name]);
	}

	CMapMeta* CellResource::getMapMeta(string const &name)
	{	
		return res_map_map[name];
	}


// 	CCellSprite* CellResource::createSprite	(string const &name)
// 	{
// 		CSpriteMeta* meta = getSpriteMeta(name);
// 		if (meta != NULL) {
// 			CCellSprite *ret = new CCellSprite(meta);
// 			return ret;
// 		}
// 		return NULL;
// 	}


	//	--------------------------------------------------------------------------------------------------------------------------------------------------
	//	Util
	//	--------------------------------------------------------------------------------------------------------------------------------------------------

	CellResource* CellResource::createCellResourceXML(string const &filename)
	{
		if (stringEndWith(filename, "xml")) 
		{
			XMLNode* node = XMLNode::parseFromFile(filename);
			if (node != NULL) {
				printf("Create CellResource : %s\n", filename.c_str());
				OutputLoader *output = new XMLOutputLoader(node, filename);
				delete node;
				CellResource *resource = new CellResource(output);
				return resource;
			}
		}
		return NULL;
	}


	//	--------------------------------------------------------------------------------------------------------------------------------------------------
	//	ResourceManager
	//	--------------------------------------------------------------------------------------------------------------------------------------------------

	

	CellResource* ResourceManager::addResource(string const &key)
	{
		CellResource* ret;
		map<string, CellResource*>::iterator it = m_resources.find(key);
		if (it == m_resources.end()) 
		{
			// create new resource
			ret = createResource(key.c_str());
			if (ret != NULL) {
				m_resources[key] = ret;
			}
		}
		else
		{
			ret = (it->second);
		}
		return ret;
	}

	CellResource* ResourceManager::getResource(string const &key)
	{
		map<string, CellResource*>::iterator it = m_resources.find(key);
		if (it != m_resources.end()) 
		{
			return (it->second);
		}
		return NULL;
	}

	bool ResourceManager::destoryResource(string const &key)
	{
		map<string, CellResource*>::iterator it = m_resources.find(key);
		if (it != m_resources.end()) 
		{
			CellResource* ret = (it->second);
			delete ret;
			return true;
		}
		else
		{
			return false;
		}
	}

	void ResourceManager::destoryAll()
	{
		for (map<string, CellResource*>::iterator it = m_resources.begin(); 
			it!=m_resources.end(); ++it)
		{
			CellResource* ret = (it->second);
			delete ret;
		}
	}

	// virtual implements
	CellResource* ResourceManager::createResource(string const &filename)
	{
		CellResource* ret = CellResource::createCellResourceXML(filename);

		return ret;
	}

}; // namespace mf
