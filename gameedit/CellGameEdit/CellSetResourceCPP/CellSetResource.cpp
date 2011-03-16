#include <iostream>
#include <sstream>

#include "CellSetResource.h"
#include "StringReader.h"

namespace cellsetresource
{

	/**
	 * input "{1234},{5678}"
	 * return [1234] [5678]
	 */
	vector<string> getStringArray2D(string const &text)
	{
		string stext = replaceString(text, "{", "");

		vector<string> lines = splitString(stext, "},");

		for (std::vector<std::string>::iterator it=lines.begin(); it!=lines.end(); it++)
		{
			(*it) = trimString(*it);
		}

		return lines;
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	namespace world
	{


		Map::Map(string const &segment)
		{
			vector<string> args = splitString(segment, ",", 7);
				
			Index 		= parseInt(args[0]);
			UnitName 	= args[1];
			MapID 		= args[2];
			X 			= parseInt(args[3]);
			Y 			= parseInt(args[4]);
			ImagesID 	= args[5];
			Data		= args[6];
		}

		Sprite::Sprite(string const &segment)
		{
			vector<string> args = splitString(segment, ",", 9);
			
			Index 		= parseInt(args[0]);
			UnitName 	= args[1];
			SprID 		= args[2];
			Anim		= parseInt(args[3]);
			Frame		= parseInt(args[4]);
			X 			= parseInt(args[5]);
			Y 			= parseInt(args[6]);
			ImagesID 	= args[7];
			Data		= args[8];
		}

		WayPoint::WayPoint(string const &segment)
		{
			vector<string> args = splitString(segment, ",", 4);
				
			Index 		= parseInt(args[0]);
			X 			= parseInt(args[1]);
			Y 			= parseInt(args[2]);
			Data		= args[3];
		}

		Region::Region(string const &segment)
		{
			vector<string> args = splitString(segment, ",", 6);
				
			Index 		= parseInt(args[0]);
			X 			= parseInt(args[1]);
			Y 			= parseInt(args[2]);
			W 			= parseInt(args[3]);
			H 			= parseInt(args[4]);
			Data		= args[5];
		}

		World::World(){}

		World::World(
			string const &world, 
			string const &_maps,
			string const &_sprs, 
			string const &_waypoints, 
			string const &_waypoint_link, 
			string const &_regions, 
			string const &_data, 
			string const &_terrain)
		{
			vector<string> args = splitString(world, ",");

			Index		= parseInt(args[0]);
			Name		= args[1];
			
			GridXCount	= parseInt(args[2]);
			GridYCount	= parseInt(args[3]);
			GridW		= parseInt(args[4]);
			GridH		= parseInt(args[5]);
			Width		= parseInt(args[6]);
			Height		= parseInt(args[7]);

			//------------------------------------------------------------------------
			// units
			int maps_count	= parseInt(args[8]);
			int sprs_count	= parseInt(args[9]);
			int wpss_count	= parseInt(args[10]);
			int wrss_count	= parseInt(args[11]);
			
			vector<string> maps	= getStringArray2D(_maps);
			vector<string> sprs	= getStringArray2D(_sprs);
			vector<string> wpss	= getStringArray2D(_waypoints);
			vector<string> wpsl	= getStringArray2D(_waypoint_link);
			vector<string> wrss	= getStringArray2D(_regions);
			
			for (int i=0; i<maps_count; i++)
			{
				Maps.push_back(Map(maps[i]));
			}
			for (int i=0; i<sprs_count; i++)
			{
				Sprs.push_back(Sprite(sprs[i]));
			}
			for (int i=0; i<wpss_count; i++)
			{
				WayPoints.push_back(WayPoint(wpss[i]));
			}
			for (int i=0; i<wrss_count; i++)
			{
				Regions.push_back(Region(wrss[i]));
			}
			
			for (vector<string>::iterator it=wpsl.begin(); it!=wpsl.end(); it++)
			{
				vector<string> link = splitString((*it), ",", 2);
				if (link.size() == 2) 
				{
					int start	= parseInt(link[0]);
					int end		= parseInt(link[1]);
					WayPoints[start].Nexts.push_back(WayPoints[end]);
				}
			}
			
			//------------------------------------------------------------------------
			// data
			Data		= _data;
			
			for (int x=0; x<GridXCount; x++)
			{
				vector<int> line;
				for (int y=0; y<GridYCount; y++)
				{
					line.push_back(0);
				}
				Terrian.push_back(line);
			}

			vector<string>::size_type terrains_count = GridXCount * GridYCount;
			vector<string> terrains = splitString(_terrain, ",");
			for (vector<string>::size_type i = 0; i < terrains_count; i++) 
			{
				vector<string>::size_type x = i / GridYCount;
				vector<string>::size_type y = i % GridYCount;
				Terrian[x][y] = parseInt(terrains[i]);
			}

		}

	}

	CellSetResource::CellSetResource(
			string const &path, 
			string const &name, 
			string const &text)
	{
		Path = path;
	
		Name = name;
		
		// 读入基础属性
		hash_map<string, string> config;
		{
			vector<string> lines = splitString(text, "\n");
			for (vector<string>::iterator it=lines.begin(); it!=lines.end(); it++)
			{
				string &line = (*it);
				vector<string> kv = splitString(line, "=", 2);
				if (kv.size()==2) {
					config[trimString(kv[0])] = trimString(kv[1]);
					//cout<<trimString(kv[0])<<" = "<<trimString(kv[1])<<endl;
				}
			}
		}
		

		// 解吸所有对象
		//int ImagesCount 	= parseInt(config["ImagesCount"]);
		//int SpriteCount 	= parseInt(config["SpriteCount"]);
		//int MapCount 		= parseInt(config["MapCount"]);
		int WorldCount 		= parseInt(config["WorldCount"]);


		for (int i = 0; i < WorldCount; i++)
		{
			stringstream s1, s2, s3, s4, s5, s6, s7, s8;
			s1 << "World_" << i;
			s2 << "World_" << i << "_maps";
			s3 << "World_" << i << "_sprs";
			s4 << "World_" << i << "_waypoints";
			s5 << "World_" << i << "_waypoint_link";
			s6 << "World_" << i << "_regions";
			s7 << "World_" << i << "_data";
			s8 << "World_" << i << "_terrain";

			world::World cur_world(
				config[s1.str()],
				config[s2.str()],
				config[s3.str()],
				config[s4.str()],
				config[s5.str()],
				config[s6.str()],
				config[s7.str()],
				config[s8.str()]
			);
			WorldTable[cur_world.Name] = cur_world;
		}
		
	
	}


}