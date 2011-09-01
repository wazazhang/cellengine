//
//  CFont.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-31.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CFont.h"

namespace com_cell
{

	
	using namespace std;
	
	
	ImageFontBW::ImageFontBW(vector<Image*> &tiles)
	{
		m_TileW			= 0;
		m_TileH			= 0;
		m_ImageChars	= tiles;
		
		for (vector<Image*>::const_iterator it=m_ImageChars.begin(); 
			 it != m_ImageChars.end(); ++it)
		{
			Image* tile = (*it);
			m_TileH = MAX(m_TileH, tile->getHeight());
			m_TileW = MAX(m_TileW, tile->getWidth());
		}
	}
	
	ImageFontBW::~ImageFontBW()
	{
		for (vector<Image*>::const_iterator it=m_ImageChars.begin(); 
			 it != m_ImageChars.end(); ++it)
		{
			Image* tile = (*it);
			if (tile != NULL)
			{
				delete(tile);
			}
		}
	}
	
	bool ImageFontBW::isBandwidth()
	{
		return true;
	}
	
	float ImageFontBW::getWidth(string const &text)
	{
		return m_TileW * text.size();
	}
	
	float ImageFontBW::getWidth(char const *text, int size)
	{
		return m_TileW * size;
	}
	
	float ImageFontBW::getWidth()
	{
		return m_TileW;
	}
	
	float ImageFontBW::getHeight()
	{
		return m_TileH;
	}
	
	Image* ImageFontBW::getFontImage(int ch)
	{
		return m_ImageChars[ch];
	}
	
	
	
}; // namespace com_cell
