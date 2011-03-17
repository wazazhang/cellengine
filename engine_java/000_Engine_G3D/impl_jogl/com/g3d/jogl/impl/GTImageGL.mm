/*
 *  GTImageGL.cpp
 *  TestGL
 *
 *  Created by WAZA on 08-5-22.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTImageGL.h"

namespace gt 
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
	
			ImageGL::ImageGL(char const *file)
			{	
				
				//m_Texture2D = [[Texture2D alloc] initWithImagePath:[NSString stringWithUTF8String:file]];
				
				spriteTexture = 0;
				
				CGImageRef spriteImage;
				CGContextRef spriteContext;
				GLubyte *spriteData;
				
				// Creates a Core Graphics image from an image file
				spriteImage = [UIImage imageNamed:[NSString stringWithUTF8String:file]].CGImage;
				m_ImageW = CGImageGetWidth(spriteImage);
				m_ImageH = CGImageGetHeight(spriteImage);
				
				if(spriteImage) 
				{
					m_TextureW = 256;
					m_TextureH = 256;
					
					// Allocated memory needed for the bitmap context
					spriteData = (GLubyte *) malloc(m_TextureW * m_TextureH * 4);
					// Uses the bitmatp creation function provided by the Core Graphics framework. 
					spriteContext = CGBitmapContextCreate(spriteData, m_TextureW, m_TextureH, 8, m_TextureW * 4, CGImageGetColorSpace(spriteImage), kCGImageAlphaPremultipliedLast);
					// After you create the context, you can draw the sprite image to the context.
					CGContextDrawImage(spriteContext, CGRectMake(0.0, 0.0, m_TextureW, m_TextureH), spriteImage);
					// You don't need the context at this point, so you need to release it to avoid memory leaks.
					CGContextRelease(spriteContext);
					
					// Use OpenGL ES to generate a name for the texture.
					glGenTextures(1, &spriteTexture);
					
					// Bind the texture name. 
					glBindTexture(GL_TEXTURE_2D, spriteTexture);
					// Speidfy a 2D texture image, provideing the a pointer to the image data in memory
					glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_TextureW, m_TextureH, 0, GL_RGBA, GL_UNSIGNED_BYTE, spriteData);
					// Set the texture parameters to use a minifying filter and a linear filer (weighted average)
					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
				
					m_Vertices[0] = 0;			m_Vertices[1] = 0;
					m_Vertices[2] = m_ImageW;	m_Vertices[3] = 0;
					m_Vertices[4] = 0;			m_Vertices[5] = -m_ImageH;
					m_Vertices[6] = m_ImageW;	m_Vertices[7] = -m_ImageH;
					
					
					//m_Texcoords[8];
				}
			}
			
			ImageGL::~ImageGL()
			{
				
			}
			
			void ImageGL::render(float x, float y, float scale_w, float scale_h, float angle)
			{
				/*if (m_Texture2D!=NULL)
				{
					[m_Texture2D drawInRect:CGRectMake(
													   x*scale_w,
													   y*scale_h,
													   [m_Texture2D pixelsWide] * scale_w,
													   [m_Texture2D pixelsHigh] * scale_h
					 )];
				}*/
				
				if (spriteTexture==0) return;
				/*
				0, 0,
				1, 0,
				0, 1,
				1, 1,
				 */
				/*
				x= (x-g_ScreenW/2)/m_TextureW;
				y=-(y-g_ScreenH/2)/m_TextureH;
				
				GLfloat _vertices[8];
				_vertices[0] = 0 + x;
				_vertices[1] = 0 + y;
				_vertices[2] = 2 + x;
				_vertices[3] = 0 + y;
				_vertices[4] = 0 + x;
				_vertices[5] = -2 + y;
				_vertices[6] = 2 + x;
				_vertices[7] = -2 + y;
			*/
				/*
				glColor4f(1, 1, 1, 1);
				// Sets up pointers and enables states needed for using vertex arrays and textures
				glVertexPointer(2, GL_FLOAT, 0, g_SpriteVertices);
				glEnableClientState(GL_VERTEX_ARRAY);
				glTexCoordPointer(2, GL_SHORT, 0, g_SpriteTexcoords);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				
				// Bind the texture name. 
				glBindTexture(GL_TEXTURE_2D, spriteTexture);
				
				// Enable use of the texture
				//glEnable(GL_TEXTURE_2D);
				// Set a blending function to use
				//glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
				// Enable blending
				//glEnable(GL_BLEND);
				
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
				*/
				
				
				glTranslatef(x, -y, 0);
				
				glVertexPointer(2, GL_FLOAT, 0, m_Vertices);
				glEnableClientState(GL_VERTEX_ARRAY);
				
				//glColorPointer(4, GL_UNSIGNED_BYTE, 0, squareColors);
				//glEnableClientState(GL_COLOR_ARRAY);

				glTexCoordPointer(2, GL_SHORT, 0, g_SpriteTexcoords);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glBindTexture(GL_TEXTURE_2D, spriteTexture);
				glEnable(GL_TEXTURE_2D);
				glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
				glEnable(GL_BLEND);
				
				glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
				
				glTranslatef(-x, y, 0);
			}
			
			
	
}; // namespcace gt