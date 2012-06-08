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
#include "MFUtil.h"

#include "CCConfiguration.h"
#include "CCDirector.h"
//#include "platform/platform.h"

//#include "support/ccUtils.h"

#include "CCGL.h"


namespace mf
{
	using namespace std;
	using namespace cocos2d;

	void mfSetVertices(
		GLfloat* vertices, 
		GLfloat cx, GLfloat cy, 
		GLfloat cw, GLfloat ch);
	void mfSetTexcoords(
		GLfloat* texcoords, 
		GLfloat cx, GLfloat cy,
		GLfloat cw, GLfloat ch,
		GLfloat tw, GLfloat th);

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	Graphics2D::Graphics2D()
	{
		setColor(1.0f,1.0f,1.0f,1.0f);
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
		GLfloat e1[4] = {  1, 0, 0.0, -x};
		GLfloat e2[4] = { 0, -1, 0.0, y2};
		GLfloat e3[4] = { 0,  1, 0.0, -y};

		glClipPlanef(GL_CLIP_PLANE0, e0);
		glClipPlanef(GL_CLIP_PLANE1, e1);
		glClipPlanef(GL_CLIP_PLANE2, e2);
		glClipPlanef(GL_CLIP_PLANE3, e3);

		glEnable(GL_CLIP_PLANE0);
 		glEnable(GL_CLIP_PLANE1);
 		glEnable(GL_CLIP_PLANE2);
  		glEnable(GL_CLIP_PLANE3);

		//glEnable(GL_SCISSOR_TEST);
		//glScissor(x, y, w, h);

		//gl.glScissor(b.x, b.y, b.width, b.height);
	}

	void Graphics2D::clipClean()
	{
		//glDisable(GL_SCISSOR_TEST);
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
		glColor4f(
			m_color.p0.R, 
			m_color.p0.G, 
			m_color.p0.B, 
			m_color.p0.A);
		glColorPointer(4, GL_FLOAT, 0, &m_color);
	}
	
    void Graphics2D::setColor(Color const &color)
	{
		setColor(color.R, color.G, color.B, color.A);
	}

	void Graphics2D::setColorAlpha(GLfloat alpha)
	{
		setColor(m_color.p0.R, m_color.p0.G, m_color.p0.B, alpha);
	}	

	void Graphics2D::setColorEnable(bool enable)
	{
		if (enable) {
			glEnable(GL_COLOR_ARRAY);
			glColor4f(
				m_color.p0.R, 
				m_color.p0.G, 
				m_color.p0.B, 
				m_color.p0.A);
		} else {
			glDisable(GL_COLOR_ARRAY);
			glColor4f(1, 1, 1, 1);
		}
	}

    Color Graphics2D::getColor()
    {
        return m_color.p0;
    }
    
    void Graphics2D::pushColor()
    {
        m_stack_color.push_back(m_color.p0);
    }

    void Graphics2D::popColor()
    {
		m_color.p0 = m_stack_color.back();
		setColor(m_color.p0);
        m_stack_color.pop_back();
    }


	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // geom

	void Graphics2D::fillRect(float x, float y, float w, float h)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
		GLfloat vertices[] = {0, 0, w, 0, 0, h, w, h,};
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		}
		glTranslatef(-x, -y, 0);		
		glEnable(GL_TEXTURE_2D);	
		glEnable(GL_COLOR_ARRAY);	
	}
	
	void Graphics2D::drawRect(float x, float y, float w, float h)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
		GLfloat vertices[] = {0, 0, w, 0, w, h, 0, h,};
		glTranslatef(x, y, 0);
		{
			glVertexPointer(2, GL_FLOAT, 0, vertices);            
			glDrawArrays(GL_LINE_LOOP, 0, 4);
		}
		glTranslatef(-x, -y, 0);	
		glEnable(GL_TEXTURE_2D);	
		glEnable(GL_COLOR_ARRAY);
	}
	
	void Graphics2D::drawLine(float x1, float y1, float x2, float y2)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
		GLfloat vertices[] = {x1, y1, x2, y2};
        glVertexPointer(2, GL_FLOAT, 0, vertices);        
		glDrawArrays(GL_LINE_STRIP, 0, 2);	
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_ARRAY);
	}
	
    void Graphics2D::drawArc(float x, float y, float width, float height, float startAngle, float angle)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
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
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_ARRAY);
    }
    
    void Graphics2D::fillArc(float x, float y, float width, float height, float startAngle, float angle)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
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
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_ARRAY);
    }
    
    
    void Graphics2D::drawOval(float x, float y, float width, float height)
    {
        drawArc(x, y, width, height, 0, 360);
    }
    
    void Graphics2D::fillOval(float x, float y, float width, float height)
    {
        fillArc(x, y, width, height, 0, 360);
    }
        
    void Graphics2D::drawPolygon(float points[], int nPoints)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
        glVertexPointer(2, GL_FLOAT, 0, points);            
		//glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
		glDrawArrays(GL_LINE_LOOP, 0, nPoints);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_ARRAY);
    }
    
    void Graphics2D::fillPolygon(float points[], int nPoints)
	{
		glDisable(GL_COLOR_ARRAY);
		glDisable(GL_TEXTURE_2D);
        glVertexPointer(2, GL_FLOAT, 0, points);            
		//glColor4f(m_color.R, m_color.G, m_color.B, m_color.A * m_alpha);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, nPoints);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR_ARRAY);
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////
	// image

	void Graphics2D::beginImage(IImage* begin)
	{
		m_beginImage = begin;	
		m_beginImageMW = begin->getTexture2D()->getPixelsWide();
		m_beginImageMH = begin->getTexture2D()->getPixelsHigh();
		m_beginImageW  = begin->getWidth();
		m_beginImageH  = begin->getHeight();
		glBindTexture(GL_TEXTURE_2D, begin->getTextureName());
	}

	void Graphics2D::drawImage(float x, float y)
	{
		drawImageSize(x, y, 
			m_beginImageW, 
			m_beginImageH);
	}
	
	void Graphics2D::drawImageScale(float x, float y, float scale_w, float scale_h)
	{
        drawImageSize(x, y, 
			m_beginImageW * scale_w,
			m_beginImageH * scale_h);
	}
	
	void Graphics2D::drawImageSize(float x, float y, float w, float h)
	{
		GLfloat vertices[8];
		GLfloat texcords[8];
		mfSetVertices(vertices, x, y, w, h);
		mfSetTexcoords(texcords, 0, 0, 
			m_beginImageW, 
			m_beginImageH, 
			m_beginImageMW, 
			m_beginImageMH);
		glVertexPointer(2, GL_FLOAT, 0, vertices);          
		glTexCoordPointer(2, GL_FLOAT, 0, texcords);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
	}

	void Graphics2D::drawImageRegion(float sx, float sy, float sw, float sh, float dx, float dy)
	{
		drawImageRegionSize(
			 sx,  sy,  sw,  sh,
			 dx,  dy,  sw,  sh);
	}

	void Graphics2D::drawImageRegionSize(
		float sx, float sy, float sw, float sh,
		float dx, float dy, float dw, float dh)
	{
		GLfloat vertices[8];
		GLfloat texcords[8];
		mfSetVertices(vertices, dx, dy, dw, dh);
		mfSetTexcoords(texcords, sx, sy, sw, sh, 
			m_beginImageMW, 
			m_beginImageMH);
		glVertexPointer(2, GL_FLOAT, 0, vertices);          
		glTexCoordPointer(2, GL_FLOAT, 0, texcords);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
	}


	void Graphics2D::drawImage(IImage *src, float x, float y)
	{
		beginImage(src);
		drawImage(x, y);
// 		GLfloat vertices[8];
// 		GLfloat texcords[8];
// 		mfSetVertices(vertices, x, y, src->getWidth(), src->getHeight());
// 		mfSetTexcoords(texcords, 0, 0, 
// 			src->getWidth(), 
// 			src->getHeight(), 
// 			src->getTexture2D()->getPixelsWide(), 
// 			src->getTexture2D()->getPixelsHigh());
// 		glVertexPointer(2, GL_FLOAT, 0, vertices);          
// 		glTexCoordPointer(2, GL_FLOAT, 0, texcords);
// 		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
	}

	void Graphics2D::drawImageScale(IImage *src, float x, float y, float scale_w, float scale_h)
	{
		beginImage(src);
		drawImageScale(x, y, scale_w, scale_h);
	}

	void Graphics2D::drawImageSize(IImage *src, float x, float y, float w, float h)
	{
		beginImage(src);
		drawImageSize(x, y, w, h);
	}

	void Graphics2D::drawImageRegion(IImage *src, float sx, float sy, float sw, float sh, float dx, float dy)
	{
		beginImage(src);
		drawImageRegion(sx, sy, sw, sh, dx, dy);
	}

	void Graphics2D::drawImageRegionSize(IImage *src, 
		float sx, float sy, float sw, float sh,
		float dx, float dy, float dw, float dh)
	{
		beginImage(src);
		drawImageRegionSize(sx, sy, sw, sh, dx, dy, dw, dh);
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
	
	/////////////////////////////////////////////////////////////////////////
	BufferedGraphis2D::BufferedGraphis2D(IImage* buff)
		: m_uFBO(0)
		, m_nOldFBO(0)
	{
		this->m_Buff = buff;
		
		glGetIntegerv(CC_GL_FRAMEBUFFER_BINDING, &m_nOldFBO);

		// generate FBO
		ccglGenFramebuffers(1, &m_uFBO);
		ccglBindFramebuffer(CC_GL_FRAMEBUFFER, m_uFBO);

		// associate texture with FBO
		ccglFramebufferTexture2D(CC_GL_FRAMEBUFFER, CC_GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, 
			m_Buff->getTextureName(), 0);

		// check if it worked (probably worth doing :) )
		GLuint status = ccglCheckFramebufferStatus(CC_GL_FRAMEBUFFER);
		if (status != CC_GL_FRAMEBUFFER_COMPLETE)
		{
			CCAssert(0, "Render Texture : Could not attach texture to framebuffer");
		}

		ccglBindFramebuffer(CC_GL_FRAMEBUFFER, m_nOldFBO);

	}

	BufferedGraphis2D::~BufferedGraphis2D()
	{
		if (m_uFBO) {
			ccglDeleteFramebuffers(1, &m_uFBO);
		}
	}


	void BufferedGraphis2D::begin(void)
	{
		// Save the current matrix
		glPushMatrix();

		glGetIntegerv(CC_GL_FRAMEBUFFER_BINDING, &m_nOldFBO);
		ccglBindFramebuffer(CC_GL_FRAMEBUFFER, m_uFBO);//Will direct drawing to the frame buffer created above

		CCSize size = CCDirector::sharedDirector()->getDisplaySizeInPixels();
		const CCSize& texSize = m_Buff->getTexture2D()->getContentSizeInPixels();
		//float tw = m_Buff->getTexture2D()->getPixelsWide();
		//float th = m_Buff->getTexture2D()->getPixelsHigh();
		float tw = texSize.width;
		float th = texSize.height;

		float widthRatio = size.width / tw;
		float heightRatio = size.height / th;


		glViewport(0, 0, tw, th);
  		ccglOrtho(
  			(float)-1.0 / widthRatio,
  			(float)1.0 / widthRatio,
  			(float)-1.0 / heightRatio, 
  			(float)1.0 / heightRatio, 
  			-1, 1);

		//glMatrixMode(GL_MODELVIEW);  // 指定当前操作模型视图矩阵堆栈
		//glLoadIdentity();                  // 重置模型视图矩阵

		//glTranslatef(tw/2, th/2, 0);

		glTranslatef(0, th, 0);
		glScalef(1, -1, 1);

		CC_ENABLE_DEFAULT_GL_STATES();	

		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//glLoadIdentity();

	}

	void BufferedGraphis2D::end(void)
	{
		ccglBindFramebuffer(CC_GL_FRAMEBUFFER, m_nOldFBO);

		//glPopAttrib();

		// Restore the original matrix and viewport
		glPopMatrix();
	
		CCSize size = CCDirector::sharedDirector()->getDisplaySizeInPixels();
		//	glViewport(0, 0, (GLsizei)size.width, (GLsizei)size.height);
		CCDirector::sharedDirector()->getOpenGLView()->setViewPortInPoints(
			0, 0, size.width, size.height);
		
	}

	void BufferedGraphis2D::clear(Color const &color)
	{
		begin();
		{
			// save clear color
			GLfloat	clearColor[4];
			glGetFloatv(GL_COLOR_CLEAR_VALUE,clearColor); 

			glClearColor(color.R, color.G, color.B, color.A);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			// restore clear color
			glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);     
		}
		end();
	}

	GLuint BufferedGraphis2D::getTextureName(){
		return m_Buff->getTextureName();
	}

	/////////////////////////////////////////////////////////////////////////

	IImage* IImage::createFromFile(string const &path)
	{
		CCTexture2D *pTexture = cocos2d::CCTextureCache::sharedTextureCache()->addImage(path.c_str());
		if (pTexture) {
			pTexture->setAliasTexParameters();

			IImage* ret = new IImage(pTexture);
			ret->m_texture_f = pTexture;
			return ret;
		} 
		return NULL;
	}

	IImage* IImage::createWithSize(int w, int h, Color const &color)
	{
		// textures must be power of two squared
		unsigned int powW = mfNextPOT(w);
		unsigned int powH = mfNextPOT(h);
		unsigned int dsize = (int)(powW * powH * 4);
		u8 *data = (u8*)malloc(dsize);
		if (color.A == 0) {
			memset(data, 0, dsize);
		} else {
			for (int i=0; i<dsize; i+=4) {
				data[i + 0] = (u8)(color.R * 0xff);
				data[i + 1] = (u8)(color.G * 0xff);
				data[i + 2] = (u8)(color.B * 0xff);
				data[i + 3] = (u8)(color.A * 0xff);
			}
		}
		
		CCTexture2D *pTexture = new CCTexture2D();
		pTexture->initWithData(data, 
			kCCTexture2DPixelFormat_RGBA8888, 
			powW, powH,
			CCSizeMake((float)w, (float)h));
		free( data );
		pTexture->setAliasTexParameters();

		IImage* ret = new IImage(pTexture);
		ret->m_texture_s = pTexture;
		return ret;

	}
                 
	IImage* IImage::createWithText(const char *text, const char *fontName, float fontSize)
	{
		CCTexture2D *pTexture = new CCTexture2D();
		//string stext = text;
		//stext.append(text);
		pTexture->initWithString(text, CCSizeMake(0, 0), CCTextAlignmentLeft, fontName, fontSize);
		pTexture->setAliasTexParameters();
		
		IImage* ret = new IImage(pTexture);
		ret->m_texture_s = pTexture;
		return ret;

		//return createWithSize(16, 16, COLOR_RED);
	}

	IImage::IImage(CCTexture2D* texture)
	{
		this->m_texture = texture;
		this->m_texture_f = 0;
		this->m_texture_s = 0;
		CCSize csize = texture->getContentSizeInPixels();
		this->m_width  = csize.width;
		this->m_height = csize.height;
	}

	IImage::~IImage()
	{
		if (this->m_texture_f) {
			CCTextureCache::sharedTextureCache()->removeTexture(this->m_texture_f);
		}
		if (this->m_texture_s) {
			this->m_texture_s->release();
		}
		
	}

    BufferedGraphis2D* IImage::createGraphics()
	{
		BufferedGraphis2D* ret = new BufferedGraphis2D(this);
		return ret;
	}

	//////////////////////////////////////////////////////////////////////
	// 整图输出
	//////////////////////////////////////////////////////////////////////

	void mfSetVertices(GLfloat* vertices, GLfloat cx, GLfloat cy, GLfloat cw, GLfloat ch)
	{
		cw = cx + cw;
		ch = cy + ch;
		vertices[0] = cx;
		vertices[1] = cy;
		vertices[2] = cw;
		vertices[3] = cy;
		vertices[4] = cx;
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
		texcoords[3] = texcoords[1];
		texcoords[4] = texcoords[0];
		texcoords[5] = (cy + ch) / th;
		texcoords[6] = texcoords[2];
		texcoords[7] = texcoords[5];
	}

	void mfTransform(Graphics2D *g, float width, float height, int trans)
	{
		switch (trans) 
		{
		case ITiles::TRANS_ROT90: {
			g->translate(height, 0);
			g->rotate(MF_ANGLE_90);
			break;
						  }
		case  ITiles::TRANS_ROT180: {
			g->translate(width, height);
			g->rotate(MF_ANGLE_180);
			break;
						   }
		case  ITiles::TRANS_ROT270: {
			g->translate(0, width);
			g->rotate(MF_ANGLE_270);
			break;
						   }
		case  ITiles::TRANS_MIRROR: {
			g->translate(width, 0);
			g->scale(-1, 1);
			break;
						   }
		case  ITiles::TRANS_MIRROR_ROT90: {
			g->translate(height, 0);
			g->rotate(MF_ANGLE_90);
			g->translate(width, 0);
			g->scale(-1, 1);
			break;
								 }
		case  ITiles::TRANS_MIRROR_ROT180: {
			g->translate(width, 0);
			g->scale(-1, 1);
			g->translate(width, height);
			g->rotate(MF_ANGLE_180);
			break;
								  }
		case  ITiles::TRANS_MIRROR_ROT270: {
			g->rotate(MF_ANGLE_270);
			g->scale(-1, 1);
			break;
								  }
		}
	}


	TilesGroup::TilesGroup(int count, int imageWidth, int imageHeight)
	{
		this->count = count;
		this->imageWidth = imageWidth;
		this->imageHeight = imageHeight;

		for (int i=0; i<count; ++i) 
		{
			GLfloat* texcoords = (GLfloat*)malloc(sizeof(GLfloat) * 8);
			GLfloat* vertices  = (GLfloat*)malloc(sizeof(GLfloat) * 8);
			memset(texcoords, 0, sizeof(texcoords));
			memset(vertices, 0, sizeof(vertices));
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

	bool TilesGroup::isActive(int Index)
	{
		return getWidth(Index) != 0 && getHeight(Index) != 0;
	}

	void TilesGroup::setTile(int index, int cx, int cy, int cw, int ch)
	{
		GLfloat* texcoords = tile_texcoords[index];
		GLfloat* vertices  = tile_vertices[index];
		GLfloat tw = imageWidth;
		GLfloat th = imageHeight;
		mfSetVertices(vertices, 0, 0, cw, ch);
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
			if (trans != TRANS_NONE) {
				glPushMatrix();
				glTranslatef(PosX, PosY, 0);
				mfTransform(g, tile_vertices[Index][6], tile_vertices[Index][7], trans);
				glVertexPointer(2, GL_FLOAT, 0, tile_vertices[Index]);          
				glTexCoordPointer(2, GL_FLOAT, 0, tile_texcoords[Index]);
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
				glPopMatrix();
			} else {
				glTranslatef(PosX, PosY, 0);
				glVertexPointer(2, GL_FLOAT, 0, tile_vertices[Index]);          
				glTexCoordPointer(2, GL_FLOAT, 0, tile_texcoords[Index]);
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
				glTranslatef(-PosX, -PosY, 0);
			}
		}
	}

	void TilesGroup::copyPixcel(Graphics2D *g, int Index, float PosX, float PosY, int trans)
	{
		if (tuxture_name) 
		{
			if (trans != TRANS_NONE) {
				glPushMatrix();
				glTranslatef(PosX, PosY, 0);
				mfTransform(g, tile_vertices[Index][6], tile_vertices[Index][7], trans);
				glVertexPointer(2, GL_FLOAT, 0, tile_vertices[Index]);          
				glTexCoordPointer(2, GL_FLOAT, 0, tile_texcoords[Index]);
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
				glPopMatrix();
			} else {
				glTranslatef(PosX, PosY, 0);
				glVertexPointer(2, GL_FLOAT, 0, tile_vertices[Index]);          
				glTexCoordPointer(2, GL_FLOAT, 0, tile_texcoords[Index]);
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);	
				glTranslatef(-PosX, -PosY, 0);
			}
		}
	}

	void TilesGroup::renderEnd(Graphics2D *g)
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

    
    
};
