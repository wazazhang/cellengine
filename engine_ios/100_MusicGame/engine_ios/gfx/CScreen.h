//
//  CScreen.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-10.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SCREEN
#define _COM_CELL_SCREEN

#include <UIKit/UIKit.h>

#include "CType.h"
#include "CGraphics2D.h"

namespace com_cell
{
    
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen object
	class IScreen
	{
	public:
		
		virtual void init() = 0;
		
		virtual void update()  = 0;
		
		virtual void render(Graphics2D &g)  = 0;
		
		virtual void destory() = 0;
        
        
		virtual void notifyPause() = 0;
		
		virtual void notifyResume() = 0;
		
		
	public:
		static bool isPointerHoldLag(int id);
		
		static bool isPointerHold(int id);
		
		static bool isPointerDown(int id) ;
		
		static bool isPointerUp(int id);
		
		static bool isPointerDrag(int id) ;
		
		static float getPointerX(int id);
		
		static float getPointerY(int id);

	};
	
	
};

#endif // #define _COM_CELL_GFX_SCREEN
