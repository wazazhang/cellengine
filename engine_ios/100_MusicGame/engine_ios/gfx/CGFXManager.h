//
//  CGFXManager.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_GFX_MANAGER
#define _COM_CELL_GFX_MANAGER

#include <UIKit/UIKit.h>
#include "CImage.h"

namespace com_cell
{
    
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen object
	class GFXManager
	{
	public:
		
		inline static Image* createImage(char const *file)
		{        
			// Creates a Core Graphics image from an image file
			CGImageRef image = [UIImage imageNamed:[NSString stringWithUTF8String:file]].CGImage;
			if (image == NULL) {
				printf("image file not found : %s\n", file);
				return NULL;
			}
			return new Image(image);		
		}

//		inline static Image* createImage(u32 width, u32 height, Color const &c)
//		{
//			CGImageRef image = [CGImageCreate(width, 
//											  height,
//											  8,
//											  <#size_t bitsPerPixel#>,
//											  <#size_t bytesPerRow#>, 
//											  <#CGColorSpaceRef space#>,
//											  <#CGBitmapInfo bitmapInfo#>,
//											  <#CGDataProviderRef provider#>,
//											  <#const CGFloat *decode#>,
//											  <#bool shouldInterpolate#>,
//											  <#CGColorRenderingIntent intent#>)];
//			
//
//			
//		}
	};
    
};

#endif // #define _COM_CELL_GFX_MANAGER
