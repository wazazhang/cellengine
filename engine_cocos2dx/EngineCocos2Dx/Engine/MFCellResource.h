//
//  MFCellResource.h
//
//  Created by wazazhang on 12-4-17.
//
#ifndef _MF_CELL_RESOURCE
#define _MF_CELL_RESOURCE

#include <string>
#include <vector>
#include <map>

#include "MFGraphics2D.h"
#include "MFGameObject.h"
#include "MFXMLParser.h"
#include "MFCellSprite.h"
#include "MFCellMap.h"


namespace mf
{
	using namespace std;
	
	//-------------------------------------------------------------------------------------
	// Set Object
	//-------------------------------------------------------------------------------------

	class SetObject
	{
	public:
		
		int		Index;
		string	Name;

		virtual ~SetObject();
	};
	
	//-------------------------------------------------------------------------------------	
	
	class ImagesSet : public SetObject
	{
	public:
		
		int				Count;
		vector<int>		ClipsX;
		vector<int>		ClipsY;
		vector<int>		ClipsW;
		vector<int>		ClipsH;
		vector<string>	ClipsKey;
		
	public:
		
		ImagesSet(int index, string const &name);
		
		ImagesSet();
	};
	
	//-------------------------------------------------------------------------------------
	
	
	class MapSet : public SetObject
	{
	public:
		
		string 		ImagesName;
		
		int 		XCount;
		int 		YCount;
		int			CellW;
		int			CellH;
		int			LayerCount;

		// block define
		vector<int>				BlocksType;
		vector<int>				BlocksMask;
		vector<int>				BlocksX1;
		vector<int>				BlocksY1;
		vector<int>				BlocksX2;
		vector<int>				BlocksY2;
		vector<int>				BlocksW;
		vector<int>				BlocksH;

		/** TerrainScene2D[layer][y][x] == Tile[index] */
		vector<vector<vector<int> > >	TerrainTiles2D;
		vector<vector<vector<int> > >	TerrainFlips2D;
		/** TerrainBlock2D[layer][y][x] == BlocksType[index] */
		vector<vector<vector<int> > >	TerrainBlocks2D;

		
		
	public:
		
		MapSet(int index, string const &name);

		MapSet();
		
	};

	//-------------------------------------------------------------------------------------
	
	class SpriteSet : public SetObject
	{
	public:
		
		string ImagesName;
		
		vector<int> PartX;
		vector<int> PartY;
		vector<int> PartTileID;
		vector<char> PartTileTrans;
		// scale
		// rotate
		// shew
		vector<vector<int> > Parts;
		
		vector<int> BlocksMask;
		vector<int> BlocksX1;
		vector<int> BlocksY1;
		vector<int> BlocksW;
		vector<int> BlocksH;
		vector<vector<int> > Blocks;
		
		int AnimateCount;
		vector<string> AnimateNames;
		vector<vector<int> > FrameAnimate;
		vector<vector<int> > FrameCDMap;
		vector<vector<int> > FrameCDAtk;
		vector<vector<int> > FrameCDDef;
		vector<vector<int> > FrameCDExt;
		
		
		
	public:
		
		SpriteSet(int index, string const &name) ;
		SpriteSet();
		
		int getPartImageIndex(int anim, int frame, int subpart);
		
		char getPartTrans(int anim, int frame, int subpart);
		
		int getPartX(int anim, int frame, int subpart);
		
		int getPartY(int anim, int frame, int subpart);
		
	};

	//-------------------------------------------------------------------------------------
	
	typedef struct WorldObjectMap
	{
		int Index;
		string UnitName;
		string MapID;
		string ImagesID;
		int X;
		int Y;
		string Data;
		
	} WorldObjectMap;
	
	typedef struct WorldObjectSprite
	{
		int Index;
		string UnitName;
		string SprID;
		string ImagesID;
		int Anim;
		int Frame;
		int X;
		int Y;
		string Data;

	} WorldObjectSprite;

	typedef struct WorldObjectWaypoint
	{
		int Index;
		int X;
		int Y;
		string Data;
		
		vector<int> Nexts;
		
	} WorldObjectWaypoint;
	
	typedef struct WorldObjectRegion
	{		
		int Index;
		int X;
		int Y;
		int W;
		int H;
		string Data;
		
	} WorldObjectRegion;
	
	typedef struct WorldObjectEvent
	{		
		int Index;
		long ID;
		string EventName;
		string EventFile;
		int X;
		int Y;
		string Data;

	} WorldObjectEvent;
	
	class WorldSet : SetObject
	{
	public:
		
		int GridXCount;
		int GridYCount;
		int GridW;
		int GridH;
		int Width;
		int Height;
		
		map<int, WorldObjectSprite>		Sprs;
		map<int, WorldObjectMap> 		Maps;
		map<int, WorldObjectWaypoint>	WayPoints;
		map<int, WorldObjectRegion>		Regions;
		map<int, WorldObjectEvent>		Events;

		string						Data;
		vector<vector<int> >		Terrian;
		
		
		WorldSet(int index, string const &name) ;
		
		WorldSet() ;
		
		int getTerrainCell(int grid_x, int grid_y);
		
	};
	

	
	
	//-------------------------------------------------------------------------------------
	// OutputLoader
	//-------------------------------------------------------------------------------------
	
	/**
	 * 如何将编辑器资源解析成单位
	 * @author WAZA
	 */
	class OutputLoader
	{
		friend class CellResource;
		
	public:

		virtual ~OutputLoader(){}

		virtual string getName() = 0;

		/***
		 * 是否单独输出每张图
		 * @return
		 */
		virtual bool isTile() = 0;
		
		/**
		 * 是否输出整图
		 * @return
		 */
		virtual bool isGroup() = 0;
		
		/**
		 * 获得导出图片文件类型
		 * @return
		 */
		virtual string getImageExtentions() = 0;
		
		/**解析所有单位纯数据*/
		virtual void  getSetObjects(map<string, ImagesSet>	&images,
									map<string, SpriteSet>	&sprites,
									map<string, MapSet>		&maps,
									map<string, WorldSet>	&worlds) = 0;
		
	protected:
		
		virtual ITiles*	createImagesFromSet(ImagesSet const &img) = 0;
		
		CSpriteMeta* createSpriteFromSet(SpriteSet const &tsprite, ITiles *tiles);
		
		CMapMeta* createMapFromSet(MapSet const &tmap, ITiles *tiles);
	};

	//-------------------------------------------------------------------------------------
	// Cell Resource
	//-------------------------------------------------------------------------------------

	/**
	* 编辑起的资源文件
	* @author WAZA
	*/
	class CellResource
	{
	private:
		OutputLoader *output_adapter;

		map<string, ImagesSet>			ImgTable;
		map<string, SpriteSet>			SprTable;
		map<string, MapSet>				MapTable;
		map<string, WorldSet>			WorldTable;
		//		map<string, TableSet>			TableGroups;

		map<string, ITiles*>			res_map_tiles;
		map<string, CSpriteMeta*>		res_map_sprites;
		map<string, CMapMeta*>			res_map_map;

	public:

		CellResource(OutputLoader *adapter);

		~CellResource();

		//	-------------------------------------------------------------------------------------------------------------------------------
		//	Set Object in <setfile>.properties
		//	-------------------------------------------------------------------------------------------------------------------------------

		WorldSet	getSetWorld		(string const &name);		
		SpriteSet	getSetSprite	(string const &name);		
		MapSet		getSetMap		(string const &name);
		ImagesSet	getSetImages	(string const &name);		

		//	--------------------------------------------------------------------------------------------------------------------------------------------------
		//	utils
		//	--------------------------------------------------------------------------------------------------------------------------------------------------

		ITiles*			getImages		(string const &name);

		CSpriteMeta*	getSpriteMeta	(string const &name);

		CMapMeta*		getMapMeta		(string const &name);

		//CCellSprite*		createSprite	(string const &name);


	public:

		static CellResource* createCellResourceXML(string const &name);


	};


	//-------------------------------------------------------------------------------------
	// Resource  Manager
	//-------------------------------------------------------------------------------------

	class ResourceManager 
	{
	protected:

		map<string, CellResource*> m_resources;

	public:
		
		CellResource* addResource(string const &key);

		CellResource* getResource(string const &key);

		bool destoryResource(string const &key);

		void destoryAll();

	protected:

		virtual CellResource* createResource(string const &name);

	};
	


}; // namespace mf


#endif // #define _MF_CELL_RESOURCE
