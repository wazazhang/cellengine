//
//  CComposite.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-11.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_BLEND
#define _COM_CELL_BLEND

#import <UIKit/UIKit.h>

#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

namespace com_cell 
{
	typedef struct Blend
    {
        int sfactor;
        int dfactor;
        
        Blend()
        {
            sfactor = GL_SRC_ALPHA;
            dfactor = GL_ONE_MINUS_SRC_ALPHA;
        }
        
        Blend(int s, int d)
        {
            sfactor = s;
            dfactor = d;
        }
        
    } Blend ;
    
    
    const Blend  BLEND_NORMAL   = Blend(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    const Blend  BLEND_SCREEN   = Blend(GL_SRC_ALPHA, GL_ONE);
    
}; // namespcace 

#endif // _COM_CELL_BLEND