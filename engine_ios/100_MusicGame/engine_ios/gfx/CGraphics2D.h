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
#include "CMath.h"
#include "CImage.h"
#include "CColor.h"
#include "CBlend.h"
#include <iostream>
#include <vector>

namespace com_cell
{
    
    
	class Graphics2D
	{
    protected:
		CGRect              m_bounds;
        Color               m_color;
        float               m_alpha;
		Blend               m_blend;
        std::vector<Blend>  m_stack_blend;
        std::vector<float>  m_stack_alpha;
        std::vector<Color>  m_stack_color;
        
	protected:
		
        Graphics2D();
		
		~Graphics2D();

		
	public:
		
        //////////////////////////////////////////////////////////////////////////////////
		// color
        
		void        setColor(float a, float r, float g, float b);	
		void        setColor(Color color);	
		Color       getColor();		
		void        pushColor();
        void        popColor();
        
        //////////////////////////////////////////////////////////////////////////////////
        // gemo
        
		void fillScreen();
        
        void drawLine(float x1, float y1, float x2, float y2);
        
		void fillRect(float x, float y, float w, float h);
        
		void drawRect(float x, float y, float w, float h);
        
        void drawArc(float x, float y, float width, float height, float startAngle, float angle);
        
        void fillArc(float x, float y, float width, float height, float startAngle, float angle);
        
        void drawOval(float x, float y, float width, float height);        
        
        void fillOval(float x, float y, float width, float height);        
        
        // 绘制多边形
        // points[x0,y0,x1,y1...]
        void drawPolygon(float points[], float nPoints);
        
        // 填充多边形
        // points[x0,y0,x1,y1...]
        void fillPolygon(float points[], float nPoints);
        
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
        // degree 角度
        void rotate(float angle);
        
        // 缩放变换
        void scale(float sx, float sy);
        
        // 推入几何变换
        void pushTransform();
        
        // 弹出几何变换
        void popTransform();
        
        ///////////////////////////////////////////////////////////////////////////////
        // blend
        
        // 设置渲染方式
        void    setBlend(Blend blend);
        
        // 获取渲染模式
        Blend   getBlend();
        
        // 推入渲染模式
        void    pushBlend();
        
        // 弹出渲染模式
        void    popBlend();
        
        
        // 设置全局alpha混合
        void    setAlpha(float alpha);
        
        // 获得全局alpha混合
        float   getAlpha();
        
        // 推入全局alpha混合
        void    pushAlpha();
        
        // 弹出全局alpha混合
        void    popAlpha();
        
        
		
		
	};
    
	
	
}; // namespace com.cell



#endif //_COM_CELL_GRAPHICS_2D