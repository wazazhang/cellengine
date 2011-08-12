//
//  CColor.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-11.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_COLOR
#define _COM_CELL_COLOR

#include "stdio.h"

namespace com_cell
{
    typedef struct Color
    {
        float A;
        float R;
        float G;
        float B;
        
        Color(float a, float r, float g, float b) 
        {
            A = a;
            R = r;
            G = g;
            B = b;
        }
        
        Color()
        {
            A = 1;
            R = 0;
            G = 0;
            B = 0;
        }
        
    } Color ;
    
    
	const Color COLOR_WHITE         = Color(1,    1,    1,    1);
    const Color COLOR_LIGHT_GRAY	= Color(1, .75f, .75f, .75f);
    const Color COLOR_GRAY          = Color(1, .50f, .50f, .50f);
    const Color COLOR_DARK_GRAY     = Color(1, .25f, .25f, .25f);
    const Color COLOR_BLACK         = Color(1,    0,    0,    0);
    const Color COLOR_RED     		= Color(1,    1,    0,    0);
    const Color COLOR_PINK			= Color(1,    1, .68f, .68f);
    const Color COLOR_ORANGE        = Color(1,    1, .78f,    0);
    const Color COLOR_YELLOW        = Color(1,    1,    1,    0);
    const Color COLOR_GREEN         = Color(1, 0, 1, 0);
    const Color COLOR_MAGENTA       = Color(1, 1, 0, 1);
    const Color COLOR_CYAN 			= Color(1, 0, 1, 1);
    const Color COLOR_BLUE 			= Color(1, 0, 0, 1);
    

}; // namespace gametiler


#endif // #define _COM_CELL_COLOR

