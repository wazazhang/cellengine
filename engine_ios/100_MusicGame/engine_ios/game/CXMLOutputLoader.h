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
#include "CIO.h"
#include "CXml.h"
#include "CGameObject.h"
#include "CCellResource.h"





namespace com_cell_game
{	    
	using namespace com_cell;
	using namespace std;
	
	//-------------------------------------------------------------------------------------
	// XMLOutputLoader
	//-------------------------------------------------------------------------------------
	
	class XMLOutputLoader : public OutputLoader
	{
		friend class XMLTiles;
		
	private:
		string					m_image_type;
		bool					m_image_tile;
		bool					m_image_group;
		map<string, ImagesSet>	m_images;
		map<string, SpriteSet>	m_sprites;
		map<string, MapSet>		m_maps;
		map<string, WorldSet>	m_worlds;
		
		string					m_filepath;
		string					m_filedir;
		
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
		
		void init			(XMLNode *doc);
		
		void initResource	(XMLNode *resource);
		
		void initImages		(XMLNode *images);
		
		void initMap		(XMLNode *map);
		
		void initSprite		(XMLNode *sprite);
		
		
		void initLevel		(XMLNode *level);
		
		void initWorld		(XMLNode *world);
		
		CTiles* createImagesFromSet(ImagesSet const &img);
		
		
				
	}; // class XMLOutputLoader
	
	
	//-------------------------------------------------------------------------------------------------------
	// XMLTiles
	//-------------------------------------------------------------------------------------------------------
	
	class XMLTiles : public CTiles
	{
	private:
		vector<Image*> tiles;
		
	public:
		
		XMLTiles(ImagesSet const &img, XMLOutputLoader *output);
		
		~XMLTiles();
		
		virtual Image*	getImage(int index);
		
		virtual int		getWidth(int Index);
		
		virtual int		getHeight(int Index);
		
		virtual int		getCount();
		
		virtual void	render(Graphics2D &g, int Index, float PosX, float PosY, int Trans);
		
	}; // class XMLTiles
	
}; // namespace

#endif // #define _COM_CELL_GAME_XML_OUTPUT_LOADER
