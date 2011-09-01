//
//  CFont.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-31.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_FONT
#define _COM_CELL_FONT

#include "stdio.h"
#include <vector>
#include <string>



#include "CImage.h"
#include "CColor.h"
#include "CBlend.h"

namespace com_cell
{
	using namespace std;
	
	class Graphics2D;
	class Image;	
	
	///////////////////////////////////////////////////////////////////////////////////
	// Class Font
	///////////////////////////////////////////////////////////////////////////////////
	
	class Font
	{
		
	public:
		
		virtual ~Font(){}
		
		// 是否等宽
		virtual bool	isBandwidth() = 0;
		
		virtual float	getWidth(string const &text) = 0;
		
		virtual float	getWidth(char const *text, int size) = 0;
		
		// 等宽字体有效
		virtual float	getWidth() = 0;
		
		// 字体高
		virtual float	getHeight() = 0;
		
		// 字对应图片
		virtual Image*	getFontImage(int ch) = 0;
		
	};

	
	///////////////////////////////////////////////////////////////////////////////////
	// Class ImageFontBW
	///////////////////////////////////////////////////////////////////////////////////
	
	class ImageFontBW : public Font
	{
	private:
		vector<Image*>	m_ImageChars;
		float			m_TileW;
		float			m_TileH;
		
	public:
		
		ImageFontBW(vector<Image*> &tiles);

		
		~ImageFontBW();
		
		virtual bool	isBandwidth();

		virtual float	getWidth(string const &text);
		
		virtual float	getWidth(char const *text, int size);
		
		virtual float	getWidth();

		virtual float	getHeight();
		
		virtual Image*	getFontImage(int ch);
		
		
	};
	
}; // namespace com_cell


#endif // #define _COM_CELL_FONT
