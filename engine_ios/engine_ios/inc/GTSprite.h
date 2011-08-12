/*
 *  GTSprite.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GAMETILER_SPRITE
#define _GAMETILER_SPRITE

#include <vector>
#include <string>
#include <map>
#include <math.h>

#include "GTGfx.h"
#include "GTImage.h"
#include "GTBlock.h"
#include "GTTiles.h"

namespace gt
{

using namespace gt;


	
class Sprite2D
{
	protected:
		
		
	
	public:
	
		// physical
		float	X;
		float	Y;
		float	ScaleWidth;
		float	ScaleHeight;
		float	Angle;
		bool	Mirror;
		
		// animation 
		TileGroup		SceneParts;
		CDGroup			CDParts;
		
		std::vector<SceneFrame>		SceneFrames;
		std::vector<CDFrame>		CDFrames;
				
		std::vector<std::vector<u32> > Animates;


		u32 CurAnimate;
		u32 CurFrame;
		
	public:
		
	Sprite2D();
		
	~Sprite2D();
		
	void clone(Sprite2D& dst);
		
	void move(float dx, float dy);
		
	int touch(float x, float y);		
		
	int getFrameImage(u32 animate, u32 frame, u32 part, ImageTile *&pImage);
		
	int getFrameBlock(u32 animate, u32 frame, u32 part, Block &block);		
		
		
	u32 getFrameCount(u32 animate);		
	
	u32 getAnimateCount();		
	
	void setCurrentFrame(u32 animate, u32 frame);	
	
	void setCurrentAnimate(u32 animate);		
	
	bool nextFrame(bool isCyc);		
	
	bool nextAnimate(bool isCyc);		
	
	void render(Graphics2D &g);		
	
	void render(Graphics2D &g, float x, float y, float w_scale, float h_scale, bool mirror, bool angle, u32 animate, u32 frame);	
	
}; // class Sprite2D



}; // namespace gt


#endif // #define _GAMETILER_SPRITE

