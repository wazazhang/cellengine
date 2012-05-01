//
//  MFType.h
//  100_MusicGame
//
//  Created by wazazhang on 12-4-17.
//

#ifndef _MF_TYPE
#define _MF_TYPE

namespace mf
{
	typedef unsigned char               u8;
	typedef unsigned short              u16;
	typedef unsigned int				u32;
	typedef unsigned long				u64;
	
	typedef signed char                 s8;
	typedef signed short                s16;
	typedef signed int					s32;
	typedef signed long					s64;
	
	const s32 INT32_MAX	= 2147483647;
	const s32 INT32_MIN	= -2147483647;

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

}; // namespace mf


#endif // #define _MF_TYPE

