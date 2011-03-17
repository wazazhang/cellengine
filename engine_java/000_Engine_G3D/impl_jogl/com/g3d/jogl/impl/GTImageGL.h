/*
 *  GTImageGL.h
 *  TestGL
 *
 *  Created by WAZA on 08-5-22.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GT_IMAGEGL_H
#define _GT_IMAGEGL_H

#import <UIKit/UIKit.h>

#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

//#include "Texture2D.h";

namespace gt 
{
	
	
	
	class ImageGL
		{
		public:
			
		protected:
			
			/* OpenGL name for the sprite texture */
			GLuint spriteTexture;
			
			float m_ImageW, m_ImageH;
			float m_TextureW, m_TextureH;
			
			//GLubyte *spriteData;
			
			GLfloat m_Vertices[8];
			GLshort m_Texcoords[8];
			
			//Texture2D *m_Texture2D;
			
			
			
		public:
			
			ImageGL(char const *file);
			
			~ImageGL();
			
			void render(float x, float y, float scale_w, float scale_h, float angle);	
			
		};

	
}; // namespcace gt

#endif // _GTIMAGEGL_H