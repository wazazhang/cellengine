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
#include "CFont.h"

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
		
		/**
		 * 创建等宽图片字体
		 * @param 字体文件
		 * @param 每行有多少字
		 * @param 总共多少列
		 * 文字按照从左到右，从上到下顺序排列
		 */
		inline static Font* createBandwidthFont(char const *file, int xcount, int ycount)
		{
			Image* src = createImage(file);
			if (src != NULL) {
				std::vector<Image*> tiles;
				int cellw = src->getWidth()  / xcount;
				int cellh = src->getHeight() / ycount;
				for (int y=0; y<ycount; y++) {
					for (int x=0; x<xcount; x++) {
						Image* tile = src->subImage(x*cellw, y*cellh, cellw, cellh);
						tiles.push_back(tile);
					}
				}
				delete src;
				return new ImageFontBW(tiles);
			}
			return NULL;
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
