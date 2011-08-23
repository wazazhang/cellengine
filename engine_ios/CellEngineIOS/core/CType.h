//
//  CType.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_TYPE
#define _COM_CELL_TYPE

#include "stdio.h"

namespace com_cell
{
	typedef unsigned char               u8;
	typedef unsigned short              u16;
	typedef unsigned int				u32;
	typedef unsigned long				u64;
	
	typedef signed char                 s8;
	typedef signed short                s16;
	typedef signed int					s32;
	typedef signed long					s64;

	
	typedef struct Vector2D
    {
        float x;
        float y;
        
    } Vector2D ;
	
	typedef struct Vector3D
    {
        float x;
        float y;
        float z;
        
    } Vector3D ;

}; // namespace gametiler


#endif // #define _COM_CELL_TYPE

