/*
 *  GTFont.mm
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTFont.h"

#include "GTImage.h"
#include "GTCore.h"
#include "GTGameManager.h"

namespace gt
{
	
	
	Font::Font(float tileW, float tileH)
	{
		m_Resource = NULL;
		m_CharCount = 256;
		m_TileW = tileW;
		m_TileH = tileH;
		m_ImageChars = (Image**)malloc(sizeof(Image*)*m_CharCount);
		
		for (int c=0; c<m_CharCount; c++)
		{
			char s[] = {c, 0};
			m_ImageChars[c] = new Image(s, 1, 1, 1, 1, m_TileW, m_TileH, 0);
		}
		
	}
	
	Font::Font(char* const fontFile, char* const ext, char* const fontName)
	{
		m_Resource = new EntityManager(fontFile, ext);
		Images* images = m_Resource->getImages(fontName);
		
		m_CharCount = images->size();
		m_ImageChars = (Image**)malloc(sizeof(Image*)*m_CharCount);
		
		for (int i=0; i<m_CharCount; i++)
		{
			m_ImageChars[i] = images->at(i);
			m_TileW = m_ImageChars[i]->getWidth();
			m_TileH = m_ImageChars[i]->getHeight();
		}
	}
	
	
	
	Font::~Font()
	{
		if (m_ImageChars!=NULL)
		{
			for (int i=0; i<m_CharCount; i++)
			{
				if (m_ImageChars[i] != NULL)
				{
					delete(m_ImageChars[i]);
					m_ImageChars[i] = NULL;
				}
			}
			free(m_ImageChars);
		}
		
		if (m_Resource!=NULL)
		{
			delete(m_Resource);
			m_Resource = NULL;
		}
		
	}
	
	
	float Font::getWidth()
	{
		return m_TileW;
	}
	
	float Font::getHeight()
	{
		return m_TileH;
	}
	
	
	Image* Font::getFontImage(char ch)
	{
		return m_ImageChars[ch];
	}
};