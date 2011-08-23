//
//  CGeometry.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-18.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_GEOMERTY
#define _COM_CELL_GEOMERTY

#include "stdio.h"

namespace com_cell
{
	class Shape
	{
	public:
		virtual ~Shape(){}
	};
	
	class Point2D : public Shape
    {
	public:
        float x;
        float y;
        
		Point2D()
		{
			x = 0;
			y = 0;
		}
		
    };
	
	class Line2D : public Shape
    {
	public:
        float x1;
        float y1;
        float x2;
        float y2;
        
		Line2D()
		{
			x1 = 0;
			y1 = 0;
			x2 = 0;
			y2 = 0;
		}
		
    };

	class Rectangle2D : public Shape
	{
	public:
		float x;
        float y;
        float width;
        float height;
		
		Rectangle2D()
		{
			x = 0;
			y = 0;
			width = 1;
			height = 1;
		}
		
	};

	
	
	
	
}; // namespace gametiler


#endif // #define _COM_CELL_GEOMERTY
