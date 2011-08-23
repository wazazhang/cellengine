//
//  CScreenGraphics2D.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

#include "CScreenGraphics2D.h"


namespace com_cell
{
	ScreenGraphics2D::ScreenGraphics2D()
	{		
        
	}
	
	ScreenGraphics2D::~ScreenGraphics2D()
	{
	}
	
	void ScreenGraphics2D::beginRender(CGRect bounds)
	{		
        m_bounds = bounds;
        
		glViewport(0, 0, m_bounds.size.width, m_bounds.size.height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); 
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
		
        glPushMatrix();
		
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT); 
        glScalef(1, -1, 1);
        glOrthof(0, m_bounds.size.width, 0, m_bounds.size.height, -1, 1);
		
        glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        setBlend(BLEND_NORMAL);
        setColor(COLOR_BLACK);
        setAlpha(1);
        
        glEnableClientState (GL_VERTEX_ARRAY);
	}
	
	void ScreenGraphics2D::endRender()
	{
        glPopMatrix();
	}
	
	void ScreenGraphics2D::beginRenderTransition(CGRect bounds)
	{		
        m_bounds = bounds;
		
		glOrthof(0, m_bounds.size.width, 0, m_bounds.size.height, -1, 1);        
		
		glEnable(GL_BLEND);                
        setBlend(BLEND_NORMAL);
		setAlpha(1);

        glPushMatrix();
	}
	
	void ScreenGraphics2D::endRenderTransition()
	{
        glPopMatrix();
	}

	
};