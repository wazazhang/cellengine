/*
 *  TGImage.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include "GTImage.h"
#include "GTGfx.h"
#include "GTScreen.h"

namespace gt
{
	
	
	
	
	
	
	

	
	
	/*
	Image* Image::createImage(char const *path)
	{
		return new Image(path);
	}*/
	/*
	Image* Image::createImage(Image* const src, u32 src_x, u32 src_y, u32 w, u32 h)
	{
		if (src != NULL)
		{
			if ((src_x + w) <= src->getWidth() && (src_y + h) <= src->getHeight()) 
			{
				UIImage *dst = [UIImage imageWithCGImage:CGImageCreateWithImageInRect ([src->m_SrcImage CGImage], CGRectMake(src_x, src_y, w, h))];
				
				return new Image(dst);
			}
		}
		
		return NULL;
	}
	*/
	
	
	
	Image* Image::createImage(UIImage* const src, u32 src_x, u32 src_y, u32 w, u32 h)
	{
		if (src != NULL)
		{
			if ((src_x + w) <= [src size].width && (src_y + h) <= [src size].height) 
			{
				UIImage *dst = [UIImage imageWithCGImage:CGImageCreateWithImageInRect ([src CGImage], CGRectMake(src_x, src_y, w, h))];
				return new Image(dst);
			}
		}
		
		return NULL;
	}

	

	
	
	Image::Image(char const *file)
	{
		init([UIImage imageNamed:[NSString stringWithUTF8String:file]]);
	}
	
	
	Image::Image(UIImage* src)
	{
		//m_SrcImage = src;
		init(src);
	}
	

	void Image::init(UIImage* src)
	{
		spriteTexture = 0;
		spriteData = NULL;
		
		UIImage* m_SrcImage = src;

		if(m_SrcImage!=NULL)
		{
			// Creates a Core Graphics image from an image file
			CGImageRef spriteImage = [m_SrcImage CGImage];
			m_ImageW = CGImageGetWidth(spriteImage);
			m_ImageH = CGImageGetHeight(spriteImage);
			
			if(spriteImage) 
			{
				int textureSize = 1;
				for(int i=2; i<=1024; i*=2)
				{
					if(textureSize==1)if(m_ImageW<=i && m_ImageH<=i){textureSize = i; break;}
				}
				m_TextureW = textureSize;
				m_TextureH = textureSize;
				
				// Allocated memory needed for the bitmap context
				spriteData = (GLubyte *) malloc(m_TextureW * m_TextureH * 4);
				// Uses the bitmatp creation function provided by the Core Graphics framework. 
				CGContextRef spriteContext = CGBitmapContextCreate(spriteData, m_TextureW, m_TextureH, 8, m_TextureW * 4, CGImageGetColorSpace(spriteImage), kCGImageAlphaPremultipliedLast);
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
		
	}
	
	Image::Image(std::string const &text, float a, float r, float g, float b, float w, float h, int align)
	{
		spriteTexture = 0;
		spriteData = NULL;
		
		{
			m_ImageW = w;
			m_ImageH = h;
			
			{
				/*
				m_TextureW = 0;
				m_TextureH = 0;
				for(int i=2; i<=1024; i*=2)
				{
					if(m_TextureW == 0 && m_ImageW<=i)m_TextureW = i;
					if(m_TextureH == 0 && m_ImageH<=i)m_TextureH = i;
				}
				*/
				int textureSize = 0;
				for(int i=2; i<=1024; i*=2)
				{
					if(textureSize==0)if(m_ImageW<=i && m_ImageH<=i){textureSize = i; break;}
				}
				m_TextureW = textureSize;
				m_TextureH = textureSize;
				
				// Allocated memory needed for the bitmap context
				spriteData = (GLubyte *) malloc(m_TextureW * m_TextureH * 4);
				// Uses the bitmatp creation function provided by the Core Graphics framework. 
				CGContextRef spriteContext = CGBitmapContextCreate(spriteData, m_TextureW, m_TextureH, 8, m_TextureW * 4, CGColorSpaceCreateDeviceRGB(), kCGImageAlphaPremultipliedLast);
				// After you create the context, you can draw the sprite image to the context.
				{
					CGRect rect = CGRectMake(0.0, 0.0, m_ImageW, m_ImageH);
					CGLayerRef layer = CGLayerCreateWithContext(spriteContext, CGSizeMake(m_ImageW, m_ImageH), NULL);
					CGContextRef layerContext = CGLayerGetContext(layer);
					
					UIGraphicsPushContext(layerContext);
					{
						//CGContextSetRGBFillColor(layerContext, 0, 0, 1, 0.5);
						//CGContextFillRect(layerContext, rect);
						CGContextSetRGBFillColor(layerContext, r, g, b, a);
						CGContextTranslateCTM(layerContext, 0, m_ImageH);
						CGContextScaleCTM(layerContext, 1, -1);
						UIFont *font = [UIFont systemFontOfSize:m_ImageH];
						[[NSString stringWithUTF8String:text.c_str()] drawInRect:rect
																		withFont:font
																   lineBreakMode:UILineBreakModeClip
																	   alignment:(UITextAlignment)align];
					}
					UIGraphicsPopContext();
					
					CGContextDrawLayerInRect(spriteContext, CGRectMake(0.0, 0.0, m_TextureW, m_TextureH), layer);
					
					//CGContextRelease(layerContext);
					CGLayerRelease(layer);
				}
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
		
	}
	
	Image::Image(float a, float r, float g, float b, float w, float h)
	{
		spriteTexture = 0;
		spriteData = NULL;

		{
			m_ImageW = w;
			m_ImageH = h;
			
			{
				m_TextureW = 1;
				m_TextureH = 1;
				
				// Allocated memory needed for the bitmap context
				spriteData = (GLubyte *) malloc(m_TextureW * m_TextureH * 4);
				for(int i=m_TextureW * m_TextureH-1; i>=0; i--)
				{
					spriteData[i*4+0] = (GLubyte)(r*a*255);
					spriteData[i*4+1] = (GLubyte)(g*a*255);
					spriteData[i*4+2] = (GLubyte)(b*a*255);
					spriteData[i*4+3] = (GLubyte)(a*255);
				}
				
				// Use OpenGL ES to generate a name for the texture.
				glGenTextures(1, &spriteTexture);
				
				// Bind the texture name. 
				glBindTexture(GL_TEXTURE_2D, spriteTexture);
				// Speidfy a 2D texture image, provideing the a pointer to the image data in memory
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_TextureW, m_TextureH, 0, GL_RGBA, GL_UNSIGNED_BYTE, spriteData);
				// Set the texture parameters to use a minifying filter and a linear filer (weighted average)
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				
				m_Vertices[0] = 0;			m_Vertices[1] = 0;
				m_Vertices[2] = m_ImageW;	m_Vertices[3] = 0;
				m_Vertices[4] = 0;			m_Vertices[5] = -m_ImageH;
				m_Vertices[6] = m_ImageW;	m_Vertices[7] = -m_ImageH;
				
				
				//m_Texcoords[8];
			}
		}
	}
	
	
	Image::~Image()
	{
		
		if (spriteTexture!=0)
		{
			glDeleteTextures(1, &spriteTexture);
			spriteTexture = 0;
		}
		
		if(spriteData!=NULL)
		{
			free(spriteData);
			spriteData = NULL;
		}
		
		//if(m_SrcImage!=NULL)
		{
			//CGImageRelease([m_SrcImage CGImage]);
			
			//[m_SrcImage release];
			//m_SrcImage = NULL;
		}
	}	

	/*
	void Image::render(float x, float y, float scale_w, float scale_h, float angle)
	{
		if (spriteTexture==0) return;
		
		m_Vertices[0] = 0;					m_Vertices[1] = 0;
		m_Vertices[2] = m_ImageW * scale_w;	m_Vertices[3] = 0;
		m_Vertices[4] = 0;					m_Vertices[5] = -m_ImageH * scale_h;
		m_Vertices[6] = m_ImageW * scale_w;	m_Vertices[7] = -m_ImageH * scale_h;
		
		glTranslatef(x, -y, 0);
		
		glVertexPointer(2, GL_FLOAT, 0, m_Vertices);
		glEnableClientState(GL_VERTEX_ARRAY);
		//glColorPointer(4, GL_UNSIGNED_BYTE, 0, g_SpriteColors);
		//glEnableClientState(GL_COLOR_ARRAY);
		glTexCoordPointer(2, GL_SHORT, 0, m_Texcoords);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindTexture(GL_TEXTURE_2D, spriteTexture);
		glEnable(GL_TEXTURE_2D);
		//glBlendFunc(GL_ONE_MINUS_SRC_ALPHA, GL_ONE);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		
		glTranslatef(-x, y, 0);
	}
	
	void Image::renderMask(float x, float y, float scale_w, float scale_h, float angle, float maskR, float maskG, float maskB)
	{
		if (spriteTexture==0) return;
		
		m_Vertices[0] = 0;					m_Vertices[1] = 0;
		m_Vertices[2] = m_ImageW * scale_w;	m_Vertices[3] = 0;
		m_Vertices[4] = 0;					m_Vertices[5] = -m_ImageH * scale_h;
		m_Vertices[6] = m_ImageW * scale_w;	m_Vertices[7] = -m_ImageH * scale_h;
		
		GLubyte colors[] = {
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
			255*maskR, 255*maskG, 255*maskB, 255,
		};
		
		glTranslatef(x, -y, 0);
		
		glVertexPointer(2, GL_FLOAT, 0, m_Vertices);
		glEnableClientState(GL_VERTEX_ARRAY);
		glTexCoordPointer(2, GL_SHORT, 0, m_Texcoords);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindTexture(GL_TEXTURE_2D, spriteTexture);
		glEnable(GL_TEXTURE_2D);
		glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
		glEnableClientState(GL_COLOR_ARRAY);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		
		glDisableClientState(GL_COLOR_ARRAY);
		
		glTranslatef(-x, y, 0);
	}
	*/
	
	int Image::getWidth()
	{
		return m_ImageW;
	}
	
	int Image::getHeight()
	{
		return m_ImageH;
	}
	
	
};