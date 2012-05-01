//
//  CGraphics2D.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "cocos2d.h"
#include "MFGraphics2D.h"
#include "MFMath.h"

namespace mf
{


	void mfSetVertices(GLfloat* vertices, GLfloat cw, GLfloat ch)
	{
		vertices[0] = 0;
		vertices[1] = 0;
		vertices[2] = cw;
		vertices[3] = 0;
		vertices[4] = 0;
		vertices[5] = ch;
		vertices[6] = cw;
		vertices[7] = ch;
	}

	void mfSetTexcoords(
		GLfloat* texcoords, 
		GLfloat cx, GLfloat cy,
		GLfloat cw, GLfloat ch,
		GLfloat tw, GLfloat th)
	{
		texcoords[0] = (cx) / tw;
		texcoords[1] = (cy) / th;
		texcoords[2] = (cx + cw) / tw;
		texcoords[3] = (cy) / th;
		texcoords[4] = (cx) / tw;
		texcoords[5] = (cy + ch) / th;
		texcoords[6] = (cx + cw) / tw;
		texcoords[7] = (cy + ch) / th;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	Graphics2D::Graphics2D()
	{
		setColor(1,1,1,1);
	}

	Graphics2D::~Graphics2D()
	{
	}



	void Graphics2D::clipRect(float x, float y, float w, float h)
	{
		GLfloat x2 = x + w;
		GLfloat y2 = y + h;

		// 前3参数为向量，最后参数为距离，这样可以确定由原点到目标的一个切割平面。
		
		GLfloat e0[4] = { -1, 0, 0.0, x2};
		glClipPlanef(GL_CLIP_PLANE0, e0);
		glEnable(GL_CLIP_PLANE0);

		GLfloat e1[4] = {  1, 0, 0.0, -x};
		glClipPlanef(GL_CLIP_PLANE1, e1);
		glEnable(GL_CLIP_PLANE1);

		GLfloat e2[4] = { 0, -1, 0.0, y2};
		glClipPlanef(GL_CLIP_PLANE2, e2);
		glEnable(GL_CLIP_PLANE2);

		GLfloat e3[4] = { 0,  1, 0.0, -y};
		glClipPlanef(GL_CLIP_PLANE3, e3);
		glEnable(GL_CLIP_PLANE3);

		//gl.glScissor(b.x, b.y, b.width, b.height);
	}

	void Graphics2D::clipClean()
	{
		glDisable(GL_CLIP_PLANE0);
		glDisable(GL_CLIP_PLANE1);
		glDisable(GL_CLIP_PLANE2);
		glDisable(GL_CLIP_PLANE3);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// color
	void Graphics2D::setColor(float r, float g, float b, float a)
	{
		m_color.set(r, g, b, a);
		glColor4f(r, g, b, a);
		glColorPointer(4, GL_FLOAT, 0, (void*)(&m_color));
	}
	
    void Graphics2D::setColor(Color const &color)
	{
		setColor(color.R, color.G, color.B, color.A);
	}

	void Graphics2D::setColorAlpha(GLfloat alpha)
	{
		m_color.p0.A = alpha;
		m_color.p1.A = alpha;
		m_color.p2.A = alpha;
		m_color.p3.A = alpha;
		glColor4f( 
			m_color.p0.R, 
			m_color.p0.G,
			m_color.p0.B,
			m_color.p0.A);
		glColorPointer(4, GL_FLOAT, 0, (void*)(&m_color));
	}

    Color Graphics2D::getColor()
    {
        return m_color.p0;
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

	void Graphics2D::fillRect(float x, float y, float w, float h)
	{
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
        
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
            //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
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
            //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_LINE_LOOP, 0, 4);
		}
		glTranslatef(-x, -y, 0);		
        
	}
	
	void Graphics2D::drawLine(float x1, float y1, float x2, float y2)
	{
		GLfloat vertices[] = {x1, y1, x2, y2};
        glVertexPointer(2, GL_FLOAT, 0, vertices);        
        //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_LINE_STRIP, 0, 2);
	}
	
    void Graphics2D::drawArc(float x, float y, float width, float height, float startAngle, float angle)
    {
        const int point_count = 32;
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
            //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
			glDrawArrays(GL_LINE_LOOP, 0, point_count);
		}
		glTranslatef(-sx, -sy, 0);
    }
    
    void Graphics2D::fillArc(float x, float y, float width, float height, float startAngle, float angle)
    {
        const int point_count = 32;
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
            //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
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
        //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_LINE_LOOP, 0, nPoints);
    }
    
    void Graphics2D::fillPolygon(float points[], float nPoints)
    {
        glVertexPointer(2, GL_FLOAT, 0, points);            
        //glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, nPoints);
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////
	// image
	/*
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
    
	void Graphics2D::drawImageMask(Image *src, float x, float y,
								   Color const &maskColor)
	{
        if (src==NULL) return;
		drawImageMaskSize(src, x, y, 
                          src->getWidth(), 
                          src->getHeight(),
                          maskColor);
	}
	
	void Graphics2D::drawImageMaskScale(Image *src, float x, float y,
										float scale_w, float scale_h,
										Color const &maskColor)
	{
		if (src==NULL) return;
		drawImageMaskSize(src, x, y, 
                          src->getWidth()*scale_w, 
                          src->getHeight()*scale_h,
                          maskColor);
	}
	
	void Graphics2D::drawImageMaskSize(Image *src, float x, float y, 
									   float w, float h,
									   Color const &maskColor)
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
                glColor4f(maskColor.R, maskColor.G, maskColor.B, m_alpha);
                glBindTexture(GL_TEXTURE_2D, src->getTextureID());
                glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
            }
            glDisable(GL_TEXTURE_2D);	
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		}
		glTranslatef(-x, -y, 0);		
	}
	*/

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
	

    
    
	//////////////////////////////////////////////////////////////////////
	// 整图输出
	//////////////////////////////////////////////////////////////////////

	TilesGroup::TilesGroup(int count, int imageWidth, int imageHeight)
	{
		this->count = count;
		this->imageWidth = imageWidth;
		this->imageHeight = imageHeight;

		for (int i=0; i<count; ++i) 
		{
			GLfloat* texcoords = (GLfloat*)malloc(sizeof(GLfloat) * 8);
			GLfloat* vertices  = (GLfloat*)malloc(sizeof(GLfloat) * 8);
			tile_texcoords.push_back(texcoords);
			tile_vertices.push_back(vertices);
		}
	}

	TilesGroup::~TilesGroup()
	{
		for (int i=0; i<count; ++i) {
			free (tile_texcoords[i]);
			free (tile_vertices[i]);
		}
	}

	void TilesGroup::setTile(int index, int cx, int cy, int cw, int ch)
	{
		GLfloat* texcoords = tile_texcoords[index];
		GLfloat* vertices  = tile_vertices[index];
		GLfloat tw = imageWidth;
		GLfloat th = imageHeight;
		mfSetVertices(vertices, cw, ch);
		mfSetTexcoords(texcoords, cx, cy, cw, ch, tw, th);
	}

	int TilesGroup::getWidth(int Index)
	{
		return tile_vertices[Index][6];
	}

	int TilesGroup::getHeight(int Index)
	{
		return tile_vertices[Index][7];
	}

	int TilesGroup::getCount()
	{
		return count;
	}

	void TilesGroup::renderBegin(Graphics2D *g)
	{ 
		glBindTexture(GL_TEXTURE_2D, tuxture_name);
	}

	void TilesGroup::render(Graphics2D *g, int Index, float PosX, float PosY, int trans)
	{
		if (tuxture_name) 
		{
			glTranslatef(PosX, PosY, 0);
			glVertexPointer(2, GL_FLOAT, 0, tile_vertices[Index]);          
			glTexCoordPointer(2, GL_FLOAT, 0, tile_texcoords[Index]);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
			glTranslatef(-PosX, -PosY, 0);	
		}
	}
	
	void TilesGroup::renderEnd(Graphics2D *g)
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
    
    
    
    
};
