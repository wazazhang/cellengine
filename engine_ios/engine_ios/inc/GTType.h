/*
 *  GTType.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */
 
 
#ifndef _GAMETILER_TYPE
#define _GAMETILER_TYPE


#include "stdio.h"


namespace gt
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
	
	
#define _INT2STR(i)		(::gt::Format<char,1024>("%d", (i)).Buffer)
#define _FLOAT2STR(f)	(::gt::Format<char,1024>("%f", (f)).Buffer)
	
	
	
	
	
	
	
	
}; // namespace gametiler


#endif // #define _GAMETILER_TYPE

