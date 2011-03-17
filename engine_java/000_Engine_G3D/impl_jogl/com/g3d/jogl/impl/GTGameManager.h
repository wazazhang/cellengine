/*
 *  GTGameManager.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-16.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_GAME_MANAGER
#define _GAMETILER_GAME_MANAGER

#include <string>
#include <vector>
#include <map>

#include "GTCore.h"
#include "GTGfx.h"
#include "GTImage.h"
#include "GTBlock.h"
#include "GTTiles.h"
#include "GTSprite.h"


namespace gt
{

class EntityManager
{

public:
		
	EntityManager(char const *file, char const *ext);
		
	~EntityManager();
		
	Images* getImages(std::string const &name);

	int createSprite(std::string const &name, Sprite2D &spr);

	
protected:

	std::map<std::string, Images>		m_AllImages;
	std::map<std::string, Sprite2D>		m_AllSprite;
	

	u32 __findTiles(std::vector<std::string> const &lines, u32 begin, u32 end);
	
	u32 __findSprite(std::vector<std::string> const &lines, u32 begin, u32 end);
	
		
}; // class ImageTile





}; // namespace gt


#endif // #define _GAMETILER_GAME_MANAGER

