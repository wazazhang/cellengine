/*
 *  TGImage.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-11.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */




#ifndef _GAMETILER_IMAGE
#define _GAMETILER_IMAGE

#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>

#include <math.h>
#include <string>
#include "GTType.h"



namespace gt
{

	
	
	class Graphics2D;

	
	/**********************************************************************************************************************************************************/
	
	class Image
	{
	public:
		
		/* OpenGL name for the sprite texture */
		GLuint spriteTexture;
		GLubyte *spriteData;
		
		int m_ImageW, m_ImageH;
		int m_TextureW, m_TextureH;
		
		GLfloat m_Vertices[8];
		GLshort m_Texcoords[8];
		
	protected:

		Image(UIImage* src);
		
		void init(UIImage* src);
		
	public:
		
		Image(char const *file);
		
		Image(std::string const &text, float a, float r, float g, float b, float w, float h, int align);
		
		Image(float a, float r, float g, float b, float w, float h);
		
		~Image();
		
		int getWidth();
			
		int getHeight();
		
		/*
		void render(float x, float y, float scale_w, float scale_h, float angle);
		
		void renderMask(float x, float y, float scale_w, float scale_h, float angle, float maskR, float maskG, float maskB);
		*/
		
	public:
		
		
		//static Image* createImage(char const *path);	
		
		//static Image* createImage(Image* const src, u32 src_x, u32 src_y, u32 w, u32 h);
		
		static Image* createImage(UIImage* const src, u32 src_x, u32 src_y, u32 w, u32 h);
		
	}; // class Image
	
	
	
	/**********************************************************************************************************************************************************/
	
	
	
};

#endif // #define _GAMETILER_IMAGE
