//
//  CXMLOutputLoader.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-21.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _MF_CELL_RESOURCE_XML
#define _MF_CELL_RESOURCE_XML

#include "MFGameObject.h"
#include "MFCellResource.h"
#include "MFGraphics2D.h"





namespace mf
{	    
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
		
		XMLOutputLoader(XMLNode* node, string const &filepath);
		~XMLOutputLoader();

		virtual string getName();

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
		
		virtual ITiles*	createImagesFromSet(ImagesSet const &img);
				
	}; // class XMLOutputLoader
	
	
	//-------------------------------------------------------------------------------------------------------
	// XMLTiles
	//-------------------------------------------------------------------------------------------------------
	
	class XMLTiles : public TilesGroup
	{
	protected:

		std::string				img_path;

		cocos2d::CCTexture2D	*pTexture;

	public:
		
		XMLTiles(ImagesSet const &img, XMLOutputLoader *loader);

		virtual ~XMLTiles();


	}; // class XMLTiles
	
}; // namespace

#endif // #define _MF_CELL_RESOURCE_XML
