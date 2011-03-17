/*
 *  GTTiles.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-16.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_TILES
#define _GAMETILER_TILES

#include <vector>

#include "GTGfx.h"
#include "GTImage.h"
#include "GTBlock.h"


namespace gt
{

using namespace gt;


//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


class ImageTile
{
public:
	
	static void createImageTile(Image *tile, float x, float y, u8 trans, ImageTile &imageTile);

public:
	float	X;
	float	Y;
	u8		Trans;
	
	Image*	Tile;
	
public:
	
	ImageTile();
	~ImageTile();
	
	
}; // class ImageTile

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

typedef std::vector<Image*>			Images;

typedef std::vector<ImageTile>		TileGroup;
typedef std::vector<Block>			CDGroup;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


class SceneFrame
{
public:

	std::vector<u32> Parts;
	
public:
	
	SceneFrame();
	
	void render(Graphics2D &g, TileGroup &tiles, float x, float y, float w_scale, float h_scale, bool mirror, float angle);
	
}; // class SceneFrame



//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



class CDFrame
{
public:

	std::vector<u32> Parts;
	
public:
	CDFrame();

	void render(Graphics2D &g, CDGroup &cds, float x, float y, float w_scale, float h_scale, bool mirror, float angle);
	
}; // class SceneFrame





}; // namespace gt


#endif // #define _GAMETILER_TILES

