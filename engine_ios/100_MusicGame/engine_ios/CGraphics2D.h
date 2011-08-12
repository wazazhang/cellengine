//
//  CGraphics2D.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_GRAPHICS_2D
#define _COM_CELL_GRAPHICS_2D

#include <UIKit/UIKit.h>
#include <math.h>
#include <string>
#include <vector>
#include <list>

#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

#include "CType.h"
#include "CImage.h"
#include "CColor.h"
#include "CBlend.h"


namespace com_cell
{
    
    
	class Graphics2D
	{
        
	public:
		static const int TEXT_ALIGN_LEFT	= UITextAlignmentLeft;
		static const int TEXT_ALIGN_CENTER	= UITextAlignmentCenter;
		static const int TEXT_ALIGN_RIGHT	= UITextAlignmentRight;
        
        
    private:
		CGRect			m_bounds;
        Color			m_color;
        float           m_alpha;
		Blend           m_blend;
        
	public:
        Graphics2D();
		
		~Graphics2D();
		
		void beginRender(CGRect bounds);
		
		void endRender();

        
        
		// color
		void        setColor(float a, float r, float g, float b);	
		void        setColor(Color color);	
		Color       getColor();		
		
		// rectangle
		void fillScreen();
        
        //////////////////////////////////////////////////////////////////////////////////
        // gemo
        void drawLine(float x1, float y1, float x2, float y2);
		void fillRect(float x, float y, float w, float h);
		void drawRect(float x, float y, float w, float h);
        
        void drawArc(float x, float y, float width, float height, float startDegree, float arcDegree);
        void fillArc(float x, float y, float width, float height, float startDegree, float arcDegree);
        
        void drawOval(float x, float y, float width, float height);        
        void fillOval(float x, float y, float width, float height);        
        void drawPolyline(float xPoints[], float yPoints[], float nPoints);
        void drawPolygon(float xPoints[], float yPoints[], float nPoints);
        void fillPolygon(float xPoints[], float yPoints[], float nPoints);
        
        //////////////////////////////////////////////////////////////////////////////////
		// image
		void drawImage(Image *src, float x, float y);
		
		void drawImageSize(Image *src, float x, float y, float w, float h);
		
		void drawImageScale(Image *src, float x, float y, float rate_w, float rate_h);
		
		void drawImageMask(Image *src, float x, float y, float maskR, float maskG, float maskB);
		
		void drawImageMaskSize(Image *src, float x, float y, float w, float h, float maskR, float maskG, float maskB);
		
		void drawImageMaskScale(Image *src, float x, float y, float scale_w, float scale_h, float maskR, float maskG, float maskB);
		
		
        /////////////////////////////////////////////////////////////////////////////////
		// string
        
		void drawString(NSString const *src, float x, float y, float charw, float charh, int align);
		
		void drawString(char const *src, float x, float y, float charw, float charh, int align);
		
		void drawString(std::string const &src, float x, float y, float charw, float charh, int align);
		
		float stringWidth(std::string const &src);
		
		float stringHeight();
		
        ///////////////////////////////////////////////////////////////////////////////
        // transform
        
        // 位移变换
        void translate(float x, float y);
        
        // 旋转变换
        // degree 弧度
        void rotate(float degree);
        
        // 旋转变换
        // degree 弧度
        void rotate(float degree, float dx, float dy);
        
        // 缩放变换
        void scale(float sx, float sy);
        
        // 推入几何变换
        void pushTransform();
        
        // 恢复几何变换
        void popTransform();
        
        ///////////////////////////////////////////////////////////////////////////////
        // blend
        
        void setBlend(Blend blend);
        
        void setAlpha(float alpha);
        
        void pushComposite();
        
        void popComposite();
        
	private:
		
		
	};
    
	
	
}; // namespace com.cell



#endif //_COM_CELL_GRAPHICS_2D