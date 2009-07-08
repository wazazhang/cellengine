
#ifndef _CELL_SET_RESOURCE_H
#define _CELL_SET_RESOURCE_H

#include <string>
#include <hash_map>

using namespace std;

namespace cellsetresource
{



	extern vector<string>	getStringArray2D(string const &text);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	namespace resource
	{
		//-----------------------------------------------------------
		class ResImages
		{
		public:

			int						Index;
			string					Name;
			int						Count;
			
			vector<int>				ClipsX;
			vector<int>				ClipsY;
			vector<int>				ClipsW;
			vector<int>				ClipsH;
			vector<string>			ClipsKey;
		};
		
		//-----------------------------------------------------------
		class ResSprite
		{
		public:
			int 					Index;
			string 					Name;
			string 					ImagesName;
			
			vector<int>				PartX;
			vector<int>				PartY;
			vector<int>				PartTileID;
			vector<int>				PartTileTrans;
			vector<vector<int> >	Parts;

			vector<int>				BlocksMask;
			vector<int>				BlocksX1;
			vector<int>				BlocksY1;
			vector<int>				BlocksW;
			vector<int>				BlocksH;
			vector<vector<int> >	Blocks;
			
			int						AnimateCount;
			vector<string>			AnimateNames;
			vector<vector<int> >	FrameAnimate ;
			vector<vector<int> >	FrameCDMap ;
			vector<vector<int> >	FrameCDAtk ;
			vector<vector<int> >	FrameCDDef ;
			vector<vector<int> >	FrameCDExt ;
		};


		//-----------------------------------------------------------
		class ResMap
		{
		public:
			int 					Index;
			string 					Name;
			string 					ImagesName;
			
			int 					XCount;
			int 					YCount;
			int						CellW;
			int						CellH;
			
			vector<int> 			TileID;
			vector<int> 			TileTrans;
			vector<vector<int> >	Animates;
			vector<vector<int> >	TerrainScene2D;
			
			vector<int>				BlocksType;
			vector<int>				BlocksMask;
			vector<int>				BlocksX1;
			vector<int>				BlocksY1;
			vector<int>				BlocksX2;
			vector<int>				BlocksY2;
			vector<int>				BlocksW;
			vector<int>				BlocksH;
			
			vector<vector<int> >	TerrainBlock2D;
			
		};

	};
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	namespace world
	{
		//-----------------------------------------------------------
		class Map
		{
		public:
			int 					Index;
			string 					UnitName;
			string 					MapID;
			string 					ImagesID;
			int 					X;
			int 					Y;
			string					Data;

		public:
			Map(string const &segment);
		};

		//-----------------------------------------------------------
		class Sprite
		{
		public:
			int 					Index;
			string 					UnitName;
			string 					SprID;
			string 					ImagesID;
			int 					Anim;
			int 					Frame;
			int 					X;
			int 					Y;
			string					Data;

		public:
			Sprite(string const &segment);
		};

		//-----------------------------------------------------------
		class WayPoint
		{
		public:
			int 					Index;
			int						X;
			int						Y;
			string					Data;
			vector<WayPoint>		Nexts;

		public:
			WayPoint(string const &segment);
		};


		//-----------------------------------------------------------	
		class Region
		{
		public:

			int 					Index;
			int						X;
			int						Y;
			int						W;
			int						H;
			string					Data;

		public:
			Region(string const &segment);

		};
			
		//-----------------------------------------------------------
		class World
		{
		public:

			int						Index;
			string					Name;
			int						GridXCount;
			int						GridYCount;
			int						GridW;
			int						GridH;
			int						Width;
			int						Height;

			vector<Sprite> 			Sprs;
			vector<Map> 			Maps;
			vector<WayPoint> 		WayPoints;
			vector<Region> 			Regions;

			string					Data;
			vector<vector<int> >	Terrian;
		
		public:
			World();

			World(
				string const &world, 
				string const &_maps,
				string const &_sprs, 
				string const &_waypoints, 
				string const &_waypoint_link, 
				string const &_regions, 
				string const &_data, 
				string const &_terrain);

		};

	};

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	class CellSetResource
	{
	public:

		string						Path;
		string						Name;

		hash_map<string, resource::ResImages>	ImgTable;
		hash_map<string, resource::ResSprite>	SprTable;
		hash_map<string, resource::ResMap>		MapTable;
		hash_map<string, world::World>			WorldTable;

	public:
		CellSetResource(
			string const &path, 
			string const &name, 
			string const &text);

	};


};// namespace cellsetresource

#endif // #ifndef _CELL_SET_RESOURCE_H