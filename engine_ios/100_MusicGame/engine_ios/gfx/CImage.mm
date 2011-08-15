//
//  CImage.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#include "CImage.h"

namespace com_cell 
{
    
    //	---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	// Sets up an array of values for the texture coordinates.
	const GLshort g_SpriteTexcoords[] =
	{
        0, 0,
        1, 0,
        0, 1,
        1, 1,
	};
	
	const GLubyte squareColors[] = {
        255, 255,   0, 255,
        0,   255, 255, 255,
        0,     0,   0,   0,
        255,   0, 255, 255,
	};
	
    Image::Image(char const *file)
    {	
        // Creates a Core Graphics image from an image file
        CGImageRef image = [UIImage imageNamed:[NSString stringWithUTF8String:file]].CGImage;
        if (image == NULL) {
            printf("image file not found : %s", file);
            return;
        }
        init(image);
    }
    
    Image::~Image()
    {
        if (m_texture_id != 0) 
        {
            glDeleteTextures(1, &m_texture_id);
        }
        if (spriteImage != NULL)
        {
            CGImageRelease(spriteImage);
        }
    }
        
    void Image::init(CGImageRef image)
    {
        spriteImage     = image;
        m_texture_id    = 0;
        m_texture_w     = 256;
        m_texture_h     = 256;
        m_image_w       = CGImageGetWidth(spriteImage);
        m_image_h       = CGImageGetHeight(spriteImage);
        
        // Allocated memory needed for the bitmap context
		size_t dataSize = m_texture_w * m_texture_h * 4;
        GLubyte *spriteData = (GLubyte *) malloc(dataSize);
		// 不将数据清0的话会有花屏
        memset(spriteData, 0, dataSize);
        // Uses the bitmatp creation function provided by the Core Graphics framework. 
        CGContextRef spriteContext = CGBitmapContextCreate(spriteData, 
														   m_texture_w, 
														   m_texture_h, 
														   8, 
														   m_texture_w * 4, 
														   CGImageGetColorSpace(spriteImage), 
														   kCGImageAlphaPremultipliedLast);
        // After you create the context, you can draw the sprite image to the context.
        CGContextDrawImage(spriteContext, CGRectMake(0.0, 0.0, m_texture_w, m_texture_h), spriteImage);
        // You don't need the context at this point, so you need to release it to avoid memory leaks.
        CGContextRelease(spriteContext);
        
		glEnable(GL_TEXTURE_2D);
		{
			// Use OpenGL ES to generate a name for the texture.
			glGenTextures(1, &m_texture_id);
			// Bind the texture name. 
			glBindTexture(GL_TEXTURE_2D, m_texture_id);
			// Speidfy a 2D texture image, provideing the a pointer to the image data in memory
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 
						 m_texture_w, m_texture_h, 
						 0, GL_RGBA, GL_UNSIGNED_BYTE, spriteData);
			// Set the texture parameters to use a minifying filter and a linear filer (weighted average)
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        }
		glDisable(GL_TEXTURE_2D);	
		
        // Release the image data
        free(spriteData);
        
        m_Vertices[0] = 0;			m_Vertices[1] = 0;
        m_Vertices[2] = m_image_w;	m_Vertices[3] = 0;
        m_Vertices[4] = 0;			m_Vertices[5] = -m_image_h;
        m_Vertices[6] = m_image_w;	m_Vertices[7] = -m_image_h;
    }

    
//    void Image::render(float x, float y, float scale_w, float scale_h, float angle)
//    {
//        if (m_texture_id==0) return;
//
//        glTranslatef(x, -y, 0);
//        
//        glVertexPointer(2, GL_FLOAT, 0, m_Vertices);
//        glEnableClientState(GL_VERTEX_ARRAY);
//        
//        //glColorPointer(4, GL_UNSIGNED_BYTE, 0, squareColors);
//        //glEnableClientState(GL_COLOR_ARRAY);
//        
//        glTexCoordPointer(2, GL_SHORT, 0, g_SpriteTexcoords);
//        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
//        glBindTexture(GL_TEXTURE_2D, m_texture_id);
//        glEnable(GL_TEXTURE_2D);
//        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
//        
//        glTranslatef(-x, y, 0);
//    }
  
    
    
    
}; // namespcace gt