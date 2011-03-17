/*
 *  GTTiles.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-16.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTTiles.h"


namespace gt
{
	void ImageTile::createImageTile(Image *tile, float x, float y, u8 trans, ImageTile &imageTile)
	{
		imageTile.Tile = tile;
		imageTile.X = x;
		imageTile.Y = y;
		imageTile.Trans = trans;
	}
	
	ImageTile::ImageTile()
	{
		Tile = NULL;
	}
	
	ImageTile::~ImageTile()
	{
		//[View release];
	}
	
	
	
	
	
	
	SceneFrame::SceneFrame()
	{
		Parts.clear();
	}
	
	void SceneFrame::render(Graphics2D &g, TileGroup &tiles, float x, float y, float w_scale, float h_scale, bool mirror, float angle)
	{
		for (std::vector<u32>::reverse_iterator it=Parts.rbegin(); it!=Parts.rend(); it++)
		{
			ImageTile* tile = &(tiles[(*it)]);
			
			if (tile->Tile!=NULL)
			{
				g.drawImageScale(tile->Tile, tile->X*w_scale+x, tile->Y*h_scale+y, w_scale, h_scale);
				
			}
		}
	}
	
	
	
	
	
	
	CDFrame::CDFrame()
	{
		Parts.clear();
	}
	
	void CDFrame::render(Graphics2D &g, CDGroup &cds, float x, float y, float w_scale, float h_scale, bool mirror, float angle)
	{
	}
	
	
}; // namespace gt