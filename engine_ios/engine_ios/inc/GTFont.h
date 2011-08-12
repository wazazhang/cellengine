/*
 *  GTFont.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_FONT
#define _GAMETILER_FONT


#include "GTType.h"

namespace gt
{

class Graphics2D;
class EntityManager;	
class Image;	
	
	
class Font
{
	
	Image**			m_ImageChars;
	long			m_CharCount;
	float			m_TileW;
	float			m_TileH;
	EntityManager*	m_Resource;
	
public:
	
	Font(float tileW, float tileH);
	
	Font(char* const fontFile, char* const ext, char* const fontName);
	
	~Font();
		
	float getWidth();
	float getHeight();
	
	Image* getFontImage(char ch);
	
	
};
	
	
};

#endif // #define _GAMETILER_FONT