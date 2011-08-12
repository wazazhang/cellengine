/*
 *  TGGfx.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTGfx.h"


namespace gt
{
	
	
	Graphics2D::Graphics2D()
	{
		m_rect		= CGRectMake(0, 0, 0, 0);
		
		m_Font		= new Font(20, 32);
		
	}
	
	Graphics2D::~Graphics2D()
	{
		if (m_Font!=NULL)
		{
			delete(m_Font);
		}
	}
	
	void Graphics2D::beginRender(CGRect const &bounds)
	{
		m_Bounds	= bounds;
		
		glViewport(0, 0, m_Bounds.size.width, m_Bounds.size.height);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
		glOrthof(-m_Bounds.size.width/2, m_Bounds.size.width/2, -m_Bounds.size.height/2, m_Bounds.size.height/2, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		
		glTranslatef(-m_Bounds.size.width/2, m_Bounds.size.height/2, 0);
		
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
	}
	
	void Graphics2D::endRender()
	{
		glTranslatef(m_Bounds.size.width/2, -m_Bounds.size.height/2, 0);
		
		
	}
	
	void Graphics2D::setFont(Font *font)
	{
		if (m_Font!=NULL && font!=NULL)
		{
			delete(m_Font);
			
			m_Font = font;
		}
	}
	
	//	-------------------------------------------------------------------------------------------------------------------------------------------------------//
	// color
	void Graphics2D::setColor(float a, float r, float g, float b)
	{
		m_A = a>1.0?1.0:(a<0?0:a); 
		m_R = r>1.0?1.0:(r<0?0:r); 
		m_G = g>1.0?1.0:(g<0?0:g); 
		m_B = b>1.0?1.0:(b<0?0:b); 
	}
	
	
	
	
	
	//	-------------------------------------------------------------------------------------------------------------------------------------------------------//
	// rectangle
	void Graphics2D::fillScreen()
	{
		fillRect(m_Bounds.origin.x, m_Bounds.origin.y, m_Bounds.size.width, m_Bounds.size.height);
	}
	
	void Graphics2D::fillRect(float x, float y, float w, float h)
	{
		GLfloat vertices[] = {0, 0, w, 0, 0, -h, w, -h,};
		GLubyte colors[] = {
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
		};
		
		glTranslatef(x, -y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glEnableClientState(GL_VERTEX_ARRAY);
			glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
			glEnableClientState(GL_COLOR_ARRAY);
			
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
			
			glDisableClientState(GL_COLOR_ARRAY);
		}
		glTranslatef(-x, y, 0);		
		
	}
	
	void Graphics2D::drawRect(float x, float y, float w, float h)
	{
		
		GLfloat vertices[] = {0, 0, w, 0, 0, -h, w, -h,};
		GLubyte colors[] = {
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
		};
		
		glTranslatef(x, -y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glEnableClientState(GL_VERTEX_ARRAY);
			glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
			glEnableClientState(GL_COLOR_ARRAY);
			
			glDrawArrays(GL_LINE_STRIP, 0, 4);
			
			glDisableClientState(GL_COLOR_ARRAY);
		}
		glTranslatef(-x, y, 0);		
		 
	}
	
	void Graphics2D::drawLine(float x1, float y1, float x2, float y2)
	{
		
		GLfloat vertices[] = {0, 0, x2-x1, y1-y2};
		GLubyte colors[] = {
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
			255*m_R*m_A, 255*m_G*m_A, 255*m_B*m_A, 255*m_A,
		};
		
		glTranslatef(x1, -y1, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glEnableClientState(GL_VERTEX_ARRAY);
			glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
			glEnableClientState(GL_COLOR_ARRAY);
			
			glDrawArrays(GL_LINE_STRIP, 0, 4);
			
			glDisableClientState(GL_COLOR_ARRAY);
		}
		glTranslatef(-x1, y1, 0);		
		
	}
	
	

	//	-------------------------------------------------------------------------------------------------------------------------------------------------------//
	// image
	
	void Graphics2D::drawImage(Image *src, float x, float y)
	{
		drawImageScale(src, x, y, 1, 1);
	}
	
	void Graphics2D::drawImageSize(Image *src, float x, float y, float w, float h)
	{
		drawImageScale(src, x, y, w/src->getWidth(), h/src->getHeight());
	}

	void Graphics2D::drawImageScale(Image *src, float x, float y, float scale_w, float scale_h)
	{
		if (src==NULL || src->spriteTexture==0) return;
		
		GLfloat vertices[] = {
			0,							0,
			src->m_ImageW * scale_w,	0,
			0,							-src->m_ImageH * scale_h,
			src->m_ImageW * scale_w,	-src->m_ImageH * scale_h,
		};
		GLshort texcoords[8] = {0, 0, 1, 0, 0, 1, 1, 1,};
		
		glTranslatef(x, -y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glEnableClientState(GL_VERTEX_ARRAY);
			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glBindTexture(GL_TEXTURE_2D, src->spriteTexture);
			glEnable(GL_TEXTURE_2D);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			glDisable(GL_TEXTURE_2D);
		}
		glTranslatef(-x, y, 0);		
		
	}
	
	
	void Graphics2D::drawImageMask(Image *src, float x, float y, float maskR, float maskG, float maskB)
	{
		drawImageMaskScale(src, x, y, 1, 1, maskR, maskG, maskB);
	}
	
	void Graphics2D::drawImageMaskSize(Image *src, float x, float y, float w, float h, float maskR, float maskG, float maskB)
	{
		drawImageMaskScale(src, x, y, w/src->getWidth(), h/src->getHeight(), maskR, maskG, maskB);
	}
	
	void Graphics2D::drawImageMaskScale(Image *src, float x, float y, float scale_w, float scale_h, float maskR, float maskG, float maskB)
	{
		if (src==NULL || src->spriteTexture==0) return;
		
		GLfloat vertices[] = {
			0,							0,
			src->m_ImageW * scale_w,	0,
			0,							-src->m_ImageH * scale_h,
			src->m_ImageW * scale_w,	-src->m_ImageH * scale_h,
		};
		GLshort texcoords[8] = {0, 0, 1, 0, 0, 1, 1, 1,};
		GLubyte colors[] = {
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
		};
		
		glTranslatef(x, -y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glEnableClientState(GL_VERTEX_ARRAY);
			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glBindTexture(GL_TEXTURE_2D, src->spriteTexture);
			glEnable(GL_TEXTURE_2D);
			glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
			glEnableClientState(GL_COLOR_ARRAY);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
			glDisableClientState(GL_COLOR_ARRAY);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			glDisable(GL_TEXTURE_2D);
		}
		glTranslatef(-x, y, 0);		
		
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------//
	// string
	
	
	float Graphics2D::stringWidth(std::string const &src)
	{
		return src.length() * (m_Font->getWidth());
	}
	
	float Graphics2D::stringHeight()
	{
		return  m_Font->getHeight();
	}
	
	
	void Graphics2D::drawString(std::string const &src, float x, float y, float charw, float charh, int align)
	{
		float rateh = charh / m_Font->getHeight();
		float ratew = charw / m_Font->getWidth();
		
		float xpos = x;
		
		switch (align)
		{
			case TEXT_ALIGN_LEFT: 
				break;
			case TEXT_ALIGN_RIGHT: 
				xpos = x - charw*src.length();
				break;
			case TEXT_ALIGN_CENTER:
				xpos = x - charw*src.length()/2;
				break;
		}
		
		for (std::string::const_iterator it=src.begin(); it!=src.end(); ++it)
		{
			drawImageMaskScale(m_Font->getFontImage(*it), xpos, y, ratew, rateh, m_R, m_G, m_B);
			
			xpos += charw;
		}
	}
	
	void Graphics2D::drawString(char const *src, float x, float y, float charw, float charh, int align)
	{
		drawString(std::string(src), x, y, charw, charh, align);
	}
	
	void Graphics2D::drawString(NSString const *src, float x, float y, float charw, float charh, int align)
	{
		drawString(std::string([src UTF8String]), x, y, charw, charh, align);
	}
	
	
	
	
	
	
	
	
};