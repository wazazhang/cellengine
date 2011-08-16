//
//  CScreenGraphics2D.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
//
//  CGraphics2D.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SCREEN_GRAPHICS_2D
#define _COM_CELL_SCREEN_GRAPHICS_2D


#include "CGraphics2D.h"

namespace com_cell
{
    
    
	class ScreenGraphics2D : public Graphics2D
	{
		friend class ScreenManager;
		
	protected:
		
		
        ScreenGraphics2D();
		
		~ScreenGraphics2D();
		
		void beginRender(CGRect bounds);
		
		void endRender();
        
		
	};
    
	
	
}; // namespace com.cell



#endif //_COM_CELL_SCREEN_GRAPHICS_2D