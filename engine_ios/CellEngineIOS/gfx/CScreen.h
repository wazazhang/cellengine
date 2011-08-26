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
	//----------------------------------------------------------------------------------------------------------------------------//

	class IScreen
	{
		friend class ScreenManager;
		
	public:
		virtual ~IScreen(){}
		
		virtual void init(std::vector<void*> const &args) = 0;
		
		virtual void update()  = 0;
		
		virtual void render(Graphics2D &g)  = 0;
		
		virtual void destory() = 0;
        
        
		virtual void notifyPause() = 0;
		
		virtual void notifyResume() = 0;
		
		
	public:
		
		int getWidth();
		
		int getHeight();
		
	protected:
		
		virtual void onTouchBegan(float x, float y){}
		virtual void onTouchMoved(float x, float y){}
		virtual void onTouchEnded(float x, float y){}
		
//	public:
//		
//		static u32 getPointerCount();
//		
//		static bool isPointerHoldLag(u32 pid);
//		
//		static bool isPointerHold(u32 pid);
//		
//		static bool isPointerDown(u32 pid) ;
//		
//		static bool isPointerUp(u32 pid);
//		
//		static bool isPointerDrag(u32 pid) ;
//		
//		static float getPointerX(u32 pid);
//		
//		static float getPointerY(u32 pid);

	};
	
	
	//----------------------------------------------------------------------------------------------------------------------------//
	// game screen maker
	//----------------------------------------------------------------------------------------------------------------------------//

	class IScreenFactory
	{
	public:
		virtual ~IScreenFactory(){}
		virtual int         getRootScreen() = 0;
		virtual IScreen*    createScreen(int ScreenType) = 0;
		
	};
	
//	//----------------------------------------------------------------------------------------------------------------------------//
//	// 
//	//----------------------------------------------------------------------------------------------------------------------------//
//	
//	class ITouchListener
//	{
//	public:
//		virtual ~ITouchListener(){}
//		virtual void onTouchBegan() = 0;
//		virtual void onTouchMoved() = 0;
//		virtual void onTouchEnded() = 0;
//	};

	

};

#endif // #define _COM_CELL_GFX_SCREEN
