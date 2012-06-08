//
//  MFType.h
//  100_MusicGame
//
//  Created by wazazhang on 12-4-17.
//

#ifndef _MF_TYPE
#define _MF_TYPE
#include <string>
#include "cocos2d.h"
namespace mf
{
	typedef CC_DLL unsigned char	u8;
	typedef CC_DLL unsigned short	u16;
	typedef CC_DLL unsigned int		u32;
	typedef CC_DLL unsigned long long	u64;
	
	typedef CC_DLL signed char		s8;
	typedef CC_DLL signed short		s16;
	typedef CC_DLL signed int		s32;
	typedef CC_DLL signed long long	s64;
	
	typedef CC_DLL wchar_t					mfchar_t;
	typedef CC_DLL basic_string<mfchar_t>	mfstring;

	typedef CC_DLL struct Vector2D
    {
        float x;
        float y;

    } Vector2D ;
	
	typedef CC_DLL struct Vector3D
    {
        float x;
        float y;
        float z;
        
    } Vector3D ;

	class CC_DLL  Shape
	{
	public:
		virtual ~Shape(){}
	};

	class  CC_DLL Point2D : public Shape
	{
	public:
		float x;
		float y;

		Point2D()
		{
			x = 0;
			y = 0;
		}
		Point2D(float x, float y)
		{
			this->x = x;
			this->y = y;
		}
	};

	class  CC_DLL Line2D : public Shape
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

	class CC_DLL  Rectangle2D : public Shape
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
		Rectangle2D(float x, float y, float w, float h)
		{
			this->x = x;
			this->y = y;
			this->width = w;
			this->height = h;
		}
	};

}; // namespace mf


#endif // #define _MF_TYPE

