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
	
	template <typename T, int N>
	struct Format
	{
		Format(T const *format, ...)
		{
			va_list argptr;
			
			va_start(argptr, format);
			vsprintf(this->Buffer, format, argptr);
			va_end(argptr);
		}
		
		T Buffer[N];
		
	}; // struct Format
	
	
#define _INT2STR(i)		(::com_cell::Format<char,1024>("%d", (i)).Buffer)
#define _FLOAT2STR(f)	(::com_cell::Format<char,1024>("%f", (f)).Buffer)
	
}; // namespace gametiler


#endif // #define _COM_CELL_TYPE

