//
//  CMath.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-12.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//


#ifndef _COM_CELL_MATH
#define _COM_CELL_MATH

#include <string>
#include <vector>
#include <fstream>
#include <math.h>

#include "CType.h"

namespace com_cell
{
    class Math
    {
    public:
        inline static float toDegree(float angle) 
        {
            return angle * 180.0 / M_PI;
        }
    };
    
    
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_MATH