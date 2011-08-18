//
//  CImage.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_IMAGE
#define _COM_CELL_IMAGE

#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

//#include "Texture2D.h";

namespace com_cell 
{
	class Image
    {
    private:
        /* OpenGL name for the sprite texture */
        GLuint          m_texture_id;
        
        float           m_image_w;
        float           m_image_h;
        float           m_texture_w;
        float           m_texture_h;
        
        //GLubyte       *spriteData;
        
        GLfloat         m_Vertices[8];
        GLshort         m_Texcoords[8];
        
        //Texture2D     *m_Texture2D;
        
        CGImageRef		spriteImage ;
		
    private:
		
		friend class GFXManager;
		
        Image(CGImageRef image_ref);
		
    public:
        
        ~Image();
        
        //void    render(float x, float y, float scale_w, float scale_h, float angle);	
        
        Image   subImage(int x, int y, int w, int h);
        
        
        inline int getWidth()
        {
            return m_image_w;
        }
        
        inline int getHeight()
        {
            return m_image_h;
        }
        
        inline GLuint getTextureID()
        {
            return m_texture_id;
        }
        
        
        
    private:
        void  init(CGImageRef image);
        
        
    };
    
	
}; // namespcace gt

#endif // _COM_CELL_IMAGE