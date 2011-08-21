//
//  CXMLOutputLoader.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_GAME_XML_OUTPUT_LOADER
#define _COM_CELL_GAME_XML_OUTPUT_LOADER

#include "CUtil.h"
#include "CFile.h"
#include "CXml.h"
#include "CGameObject.h"
#include "CCellResource.h"





namespace com_cell_game
{	    
	using namespace com_cell;
	using namespace std;

	class XMLOutputLoader : public OutputLoader
	{
	private:
		string	image_type;
		bool	image_tile;
		bool	image_group;
	public:
		
		XMLOutputLoader(char const *filename);
		~XMLOutputLoader();
		
		virtual bool isTile();
		
		virtual bool isGroup();
		
		virtual string getImageExtentions();
		
		virtual void  getSetObjects(map<string, ImagesSet>	&images,
									map<string, SpriteSet>	&sprites,
									map<string, MapSet>		&maps,
									map<string, WorldSet>	&worlds);
		
	protected:
		
		void init			(XMLNode &doc);
		
		void initResource	(XMLNode &resource);
		
		void initLevel		(XMLNode &level);
		
		void initImages		(XMLNode &images);
		
		void initMap		(XMLNode &map);
		
		void initSprite		(XMLNode &sprite);
		
		
	}; // class XMLOutputLoader
	
}; // namespace

#endif // #define _COM_CELL_GAME_XML_OUTPUT_LOADER
