//
//  CGraphics2D.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CGraphics2D.h"


namespace com_cell
{
	
	
	Graphics2D::Graphics2D()
	{		
        
	}
	
	Graphics2D::~Graphics2D()
	{
	}
	
	void Graphics2D::beginRender(CGRect bounds)
	{		
        m_bounds = bounds;
        
        glPushMatrix();
        
		glViewport(0, 0, m_bounds.size.width, m_bounds.size.height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT); 
        glScalef(1, -1, 1);
        glOrthof(0, m_bounds.size.width, 0, m_bounds.size.height, -1, 1);

        
        glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        setBlend(BLEND_NORMAL);
        setColor(COLOR_BLACK);
        setAlpha(1);

	}
	
	void Graphics2D::endRender()
	{
        glPopMatrix();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// color
	void Graphics2D::setColor(float a, float r, float g, float b)
	{
        m_color = Color(a, r, g, b);
	}
	
    void Graphics2D::setColor(Color color)
	{
		m_color = color;
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // geom
    
	void Graphics2D::fillScreen()
	{
		fillRect(m_bounds.origin.x, m_bounds.origin.y, m_bounds.size.width, m_bounds.size.height);
	}
	
	void Graphics2D::fillRect(float x, float y, float w, float h)
	{
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
		GLfloat colors[] = {
            m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
		};
        
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
            glEnableClientState (GL_VERTEX_ARRAY);
            
            glColorPointer(4, GL_FLOAT, 0, colors);
            glEnableClientState (GL_COLOR_ARRAY);
            
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
            
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
		}
		glTranslatef(-x, -y, 0);		
	}
	
	void Graphics2D::drawRect(float x, float y, float w, float h)
	{
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
		GLfloat colors[] = {
            m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
		};
		
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
            glEnableClientState (GL_VERTEX_ARRAY);
            
			glColorPointer(4, GL_FLOAT, 0, colors);
            glEnableClientState (GL_COLOR_ARRAY);
            
			glDrawArrays(GL_LINE_STRIP, 0, 4);
            
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
		}
		glTranslatef(-x, -y, 0);		
        
	}
	
	void Graphics2D::drawLine(float x1, float y1, float x2, float y2)
	{
		
		GLfloat vertices[] = {0, 0, x2-x1, y1-y2};
		GLfloat colors[] = {
            m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
			m_color.R, m_color.G, m_color.B, m_color.A * m_alpha,
		};
		
		glTranslatef(x1, y1, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
            glEnableClientState (GL_VERTEX_ARRAY);
            
			glColorPointer(2, GL_FLOAT, 0, colors);
            glEnableClientState (GL_COLOR_ARRAY);
            
			glDrawArrays(GL_LINE_STRIP, 0, 2);
            
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
		}
		glTranslatef(-x1, -y1, 0);		
		
	}
	
    void Graphics2D::drawArc(float x, float y, float width, float height, float startDegree, float arcDegree)
    {
        
    }
    
    void Graphics2D::fillArc(float x, float y, float width, float height, float startDegree, float arcDegree)
    {
        
    }
    
    
    void Graphics2D::drawOval(float x, float y, float width, float height)
    {
        
    }
    
    void Graphics2D::fillOval(float x, float y, float width, float height)
    {
        
    }
    
    void Graphics2D::drawPolyline(float xPoints[], float yPoints[], float nPoints)
    {
        
    }
    
    void Graphics2D::drawPolygon(float xPoints[], float yPoints[], float nPoints)
    {
        
    }
    
    void Graphics2D::fillPolygon(float xPoints[], float yPoints[], float nPoints)
    {
        
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////
	// image
	
	void Graphics2D::drawImage(Image *src, float x, float y)
	{
		if (src==NULL) return;
		drawImageSize(src, x, y, src->getWidth(), src->getHeight());
	}
	
	void Graphics2D::drawImageScale(Image *src, float x, float y, float scale_w, float scale_h)
	{
        if (src==NULL) return;
        drawImageSize(src, x, y, src->getWidth() * scale_w, src->getHeight() * scale_h);
	}
	
	void Graphics2D::drawImageSize(Image *src, float x, float y, float w, float h)
	{
        if (src==NULL || src->getTextureID()==0) return;
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
		GLshort texcoords[8] = {0, 0, 1, 0, 0, 1, 1, 1,};
        GLfloat colors[] = {
            1, 1, 1, m_alpha,
			1, 1, 1, m_alpha,
			1, 1, 1, m_alpha,
			1, 1, 1, m_alpha,
		};
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);          
            glEnableClientState (GL_VERTEX_ARRAY);

            glColorPointer(4, GL_FLOAT, 0, colors);
            glEnableClientState (GL_COLOR_ARRAY);
            
			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
            glEnableClientState (GL_TEXTURE_COORD_ARRAY);
            
            glEnable(GL_TEXTURE_2D);
            {
                glBindTexture(GL_TEXTURE_2D, src->getTextureID());
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
            }
            glDisable(GL_TEXTURE_2D);	
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
		}
		glTranslatef(-x, -y, 0);	
	}
    
	void Graphics2D::drawImageMask(Image *src, float x, float y, float maskR, float maskG, float maskB)
	{
        if (src==NULL) return;
		drawImageMaskSize(src, x, y, 
                          src->getWidth(), 
                          src->getHeight(),
                          maskR, maskG, maskB);
	}
	
	void Graphics2D::drawImageMaskScale(Image *src, float x, float y, float scale_w, float scale_h, float maskR, float maskG, float maskB)
	{
		if (src==NULL) return;
		drawImageMaskSize(src, x, y, 
                          src->getWidth()*scale_w, 
                          src->getHeight()*scale_h,
                          maskR, maskG, maskB);
	}
	
	void Graphics2D::drawImageMaskSize(Image *src, float x, float y, float w, float h, float maskR, float maskG, float maskB)
	{
		if (src==NULL || src->getTextureID()==0) return;
		
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
		GLshort texcoords[8] = {0, 0, 1, 0, 0, 1, 1, 1,};
		GLfloat colors[] = {
			maskR, maskG, maskB, 1,
			maskR, maskG, maskB, 1,
			maskR, maskG, maskB, 1,
			maskR, maskG, maskB, 1,
		};
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);       
            glEnableClientState (GL_VERTEX_ARRAY);
            
            glColorPointer(4, GL_FLOAT, 0, colors);
            glEnableClientState (GL_COLOR_ARRAY);

			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
            glEnableClientState (GL_TEXTURE_COORD_ARRAY);
            
            glEnable(GL_TEXTURE_2D);
            {
                glBindTexture(GL_TEXTURE_2D, src->getTextureID());
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
            }
            glDisable(GL_TEXTURE_2D);	
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
		}
		glTranslatef(-x, -y, 0);		
	}
	
    ///////////////////////////////////////////////////////////////////////////////
	// string
	
	
	float Graphics2D::stringWidth(std::string const &src)
	{
        return 1;
	}
	
	float Graphics2D::stringHeight()
	{
        return 1;
	}
	
	
	void Graphics2D::drawString(std::string const &src, float x, float y, float charw, float charh, int align)
	{
        
	}
	
	void Graphics2D::drawString(char const *src, float x, float y, float charw, float charh, int align)
	{
		drawString(std::string(src), x, y, charw, charh, align);
	}
	
	void Graphics2D::drawString(NSString const *src, float x, float y, float charw, float charh, int align)
	{
		drawString(std::string([src UTF8String]), x, y, charw, charh, align);
	}
	
	
    ///////////////////////////////////////////////////////////////////////////////
    // transform
    
    void Graphics2D::translate(float x, float y)
    {
        glTranslatef(x, y, 0);
    }
    
    void Graphics2D::rotate(float degree)
    {
        glRotatef(degree, 0, 0, 0);
    }
    
    void Graphics2D::rotate(float degree, float dx, float dy)
    {
        glRotatef(degree, dx, dy, 0);
    }
    
    void Graphics2D::scale(float sx, float sy)
    {
        glScalef(sx, sy, 1);
    }
    
    void Graphics2D::pushTransform() 
    {
        glPushMatrix();
    }
    
    void Graphics2D::popTransform() 
    {
        glPopMatrix();
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    // blend
    
    void Graphics2D::setBlend(Blend blend)
    {
        m_blend = blend;
        glBlendFunc(m_blend.sfactor, m_blend.dfactor);
    }
    
    void Graphics2D::setAlpha(float alpha)
    {
        m_alpha = alpha;
        
    }

	
	
};