/*
 *  CRect.mm
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTBlock.h"

namespace gt
{
	Block::Block()
	{
		Type = -1;
	}
	
	float Block::getWidth()
	{
		return abs(X2 - X1);
	}
	
	float Block::getHeight()
	{
		return abs(Y2 - Y1);
	}
	
	bool Block::touch(Block const &b)
	{
		return intersectRect(X1, Y1, X2, Y2, b.X1, b.Y1, b.X2, b.Y2);
	}
	
	
	bool Block::touch(float x, float y)
	{
		return includeRectPoint(X1, Y1, X2, Y2, x, y);
	}
	
}; // namespace gt