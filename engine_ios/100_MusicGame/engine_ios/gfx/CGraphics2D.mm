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
	
    Color Graphics2D::getColor()
    {
        return m_color;
    }
    
    void Graphics2D::pushColor()
    {
        m_stack_color.push_back(m_color);
    }

    void Graphics2D::popColor()
    {
        m_color = m_stack_color.back();
        m_stack_color.pop_back();
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
        
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
            glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		}
		glTranslatef(-x, -y, 0);		
	}
	
	void Graphics2D::drawRect(float x, float y, float w, float h)
	{
		GLfloat vertices[] = {0, 0, w, 0, w, h, 0, h,};
		
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);            
            glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_LINE_LOOP, 0, 4);
		}
		glTranslatef(-x, -y, 0);		
        
	}
	
	void Graphics2D::drawLine(float x1, float y1, float x2, float y2)
	{
		GLfloat vertices[] = {x1, y1, x2, y2};
        glVertexPointer(2, GL_FLOAT, 0, vertices);        
        glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_LINE_STRIP, 0, 2);
	}
	
    void Graphics2D::drawArc(float x, float y, float width, float height, float startAngle, float angle)
    {
        int point_count = MAX(width, height);
        GLfloat sw = width / 2;
        GLfloat sh = height / 2;
        GLfloat sx = x + sw;
        GLfloat sy = y + sh;
        
        GLfloat vertices[point_count*2];
        
        GLfloat degree_start = Math::toDegree(startAngle);
        GLfloat degree_delta = M_PI * 2 / point_count;
        for (int i=0; i<point_count; i++) {
            float idegree = degree_start+i*degree_delta;
            vertices[i*2+0] = cosf(idegree)*sw;
            vertices[i*2+1] = sinf(idegree)*sh;
        }
                
		glTranslatef(sx, sy, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);            
            glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_LINE_LOOP, 0, point_count);
		}
		glTranslatef(-sx, -sy, 0);		

    }
    
    void Graphics2D::fillArc(float x, float y, float width, float height, float startAngle, float angle)
    {
        int point_count = MAX(width, height);
        GLfloat sw = width / 2;
        GLfloat sh = height / 2;
        GLfloat sx = x + sw;
        GLfloat sy = y + sh;
        
        GLfloat vertices[point_count*2+4];
        vertices[0] = 0;
        vertices[1] = 0;
        GLfloat degree_start = Math::toDegree(startAngle);
        GLfloat degree_delta = M_PI * 2 / point_count;
        for (int i=0; i<point_count; i++) {
            float idegree = degree_start+i*degree_delta;
            vertices[2+i*2+0] = cosf(idegree)*sw;
            vertices[2+i*2+1] = sinf(idegree)*sh;
        }
        vertices[2+point_count*2] = vertices[2];
        vertices[3+point_count*2] = vertices[3];

		glTranslatef(sx, sy, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);            
            glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_TRIANGLE_FAN, 0, point_count+2);
		}
		glTranslatef(-sx, -sy, 0);		

    }
    
    
    void Graphics2D::drawOval(float x, float y, float width, float height)
    {
        drawArc(x, y, width, height, 0, 360);
    }
    
    void Graphics2D::fillOval(float x, float y, float width, float height)
    {
        fillArc(x, y, width, height, 0, 360);
    }
        
    void Graphics2D::drawPolygon(float points[], float nPoints)
    {
        glVertexPointer(2, GL_FLOAT, 0, points);            
        glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_LINE_LOOP, 0, nPoints);
    }
    
    void Graphics2D::fillPolygon(float points[], float nPoints)
    {
        glVertexPointer(2, GL_FLOAT, 0, points);            
        glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, nPoints);
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

		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);          
            
			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
            glEnableClientState (GL_TEXTURE_COORD_ARRAY);
            
            glEnable(GL_TEXTURE_2D);
            {
                glColor4f(1, 1, 1, m_alpha);
                glBindTexture(GL_TEXTURE_2D, src->getTextureID());
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
            }
            glDisable(GL_TEXTURE_2D);	
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
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

		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);       

			glTexCoordPointer(2, GL_SHORT, 0, texcoords);
            glEnableClientState (GL_TEXTURE_COORD_ARRAY);
            
            glEnable(GL_TEXTURE_2D);
            {
                glColor4f(maskR, maskG, maskB, m_alpha);
                glBindTexture(GL_TEXTURE_2D, src->getTextureID());
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
            }
            glDisable(GL_TEXTURE_2D);	
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
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
        // TODO
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
    
    void Graphics2D::rotate(float angle)
    {
        glRotatef(angle, 0, 0, 1);
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
    
    Blend Graphics2D::getBlend()
    {
        return m_blend;
    }
    
	void Graphics2D::pushBlend()
    {
        m_stack_blend.push_back(m_blend);
    }
    
    void Graphics2D::popBlend()
    {
        m_blend = m_stack_blend.back();
        glBlendFunc(m_blend.sfactor, m_blend.dfactor);
        m_stack_blend.pop_back();
    }
	
    
    void Graphics2D::setAlpha(float alpha)
    {
        m_alpha = alpha;
    }
    
    float   Graphics2D::getAlpha()
    {
        return m_alpha;
    }
    
    void    Graphics2D::pushAlpha()
    {
        m_stack_alpha.push_back(m_alpha);
    }
    
    void    Graphics2D::popAlpha()
    {
        m_alpha = m_stack_alpha.back();
        m_stack_alpha.pop_back();
    }
    
    
    
    
    
    
    
    
    
    
    
    
};