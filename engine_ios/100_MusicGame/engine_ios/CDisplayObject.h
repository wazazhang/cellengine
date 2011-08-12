//
//  CDisplayObject.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_DISPLAY_OBJECT
#define _COM_CELL_DISPLAY_OBJECT

#include <UIKit/UIKit.h>

#include "CType.h"
#include "CGraphics2D.h"

namespace com_cell
{
    
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen object
	class CDisplayObject
	{
        
    public:
        float x;
        float y;
        
	public:
		
		virtual void init() = 0;
		
		virtual void update()  = 0;
		
		virtual void render(Graphics2D &g)  = 0;
	};
	
	
};

#endif // #define _COM_CELL_DISPLAY_OBJECT
