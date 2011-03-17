/*
 *  TGGfx.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GAMETILER_GFX_2D
#define _GAMETILER_GFX_2D


#include <UIKit/UIKit.h>
#include <math.h>
#include <string>
#include <vector>
#include <list>

#include "GTImage.h"
#include "GTFont.h"

namespace gt
{
	
	
	class Graphics2D
	{
	private:
		
		CGRect			m_rect;
		
		GLfloat			m_R;
		GLfloat			m_G;
		GLfloat			m_B;
		GLfloat			m_A;
		
	public:
		static const int TEXT_ALIGN_LEFT	= UITextAlignmentLeft;
		static const int TEXT_ALIGN_CENTER	= UITextAlignmentCenter;
		static const int TEXT_ALIGN_RIGHT	= UITextAlignmentRight;

		CGRect			m_Bounds;
		
	private:
		
		Font*				m_Font;
		
	public:
		
		Graphics2D();
		
		~Graphics2D();
		
		void beginRender(CGRect const &bounds);
		
		void endRender();
				
		void setFont(Font *font);
		
		// color
		void setColor(float a, float r, float g, float b);		
		
		// rectangle
		void fillScreen();
		
		void fillRect(float x, float y, float w, float h);
		
		void drawRect(float x, float y, float w, float h);
	
		void drawLine(float x1, float y1, float x2, float y2);
		
		// image
		void drawImage(Image *src, float x, float y);
		
		void drawImageSize(Image *src, float x, float y, float w, float h);
		
		void drawImageScale(Image *src, float x, float y, float rate_w, float rate_h);
		
		
		void drawImageMask(Image *src, float x, float y, float maskR, float maskG, float maskB);
		
		void drawImageMaskSize(Image *src, float x, float y, float w, float h, float maskR, float maskG, float maskB);
		
		void drawImageMaskScale(Image *src, float x, float y, float scale_w, float scale_h, float maskR, float maskG, float maskB);
		
		
		// string

		void drawString(NSString const *src, float x, float y, float charw, float charh, int align);
		
		void drawString(char const *src, float x, float y, float charw, float charh, int align);
		
		void drawString(std::string const &src, float x, float y, float charw, float charh, int align);
		
		
		float stringWidth(std::string const &src);
		
		float stringHeight();
		
	private:
		
		
	};

};

#endif
