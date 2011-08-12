/*
 *  CRect.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_RECT
#define _GAMETILER_RECT

#include <UIKit/UIKit.h>
//#include <CoreGraphics/CGImage.h>

#include "GTType.h"

namespace gt
{
	
class Block
{
public:
	
	static const u8 TYPE_RECT = 1;
	
	static void createBlockRect(u32 mask, float x, float y, float w, float h, Block &block)
	{
		block.Type = TYPE_RECT;
		block.Mask = mask;
		block.X1 = x;
		block.Y1 = y;
		block.X2 = x + w;
		block.Y2 = y + h;
	}
	
public:
	u8 Type;
	u32 Mask;
	
	float X1;
	float Y1;
	float X2;
	float Y2;
	
public:
	
	Block();
	
	float getWidth();	
	
	float getHeight();	
	
	bool touch(Block const &b);	
	
	bool touch(float x, float y);	

			
			
};
	
	

	inline bool intersectRect(
			float sx1, float sy1, float sx2, float sy2, 
			float dx1, float dy1, float dx2, float dy2) {
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}
	
	inline bool intersectRect2(
			float sx1, float sy1, float sw, float sh, 
			float dx1, float dy1, float dw, float dh) {
		float sx2 = sx1 + sw  ;
		float sy2 = sy1 + sh  ;
		float dx2 = dx1 + dw  ;
		float dy2 = dy1 + dh  ;
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

	inline bool includeRectPoint(
			float sx1, float sy1, float sx2, float sy2, 
			float dx, float dy) {
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}
	
	inline bool includeRectPoint2(
			float sx1, float sy1, float sw, float sh, 
			float dx, float dy) {
		float sx2 = sx1 + sw  ;
		float sy2 = sy1 + sh  ;
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}


};

#endif // #define _GAMETILER_RECT
