//
//  CScreen.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-16.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CScreen.h"
#include "CScreenManager.h"

namespace com_cell
{
	
	int IScreen::getWidth(){
		return ScreenManager::getInstance()->getWidth();
	}
	
	int IScreen::getHeight() {
		return ScreenManager::getInstance()->getHeight();
	}

//	u32 IScreen::getPointerCount()
//	{
//		return ScreenManager::getInstance()->getPointerCount();
//	}
//
//	
//
//	bool IScreen::isPointerHoldLag(u32 pid)
//	{
//		return ScreenManager::getInstance()->isPointerHoldLag(pid);
//	}
//
//	bool IScreen::isPointerHold(u32 pid)
//	{
//		return ScreenManager::getInstance()->isPointerHold(pid);
//	}
//
//
//	bool IScreen::isPointerDown(u32 pid) 
//	{
//		return ScreenManager::getInstance()->isPointerDown(pid);
//	}
//
//
//	bool IScreen::isPointerUp(u32 pid)
//	{
//		return ScreenManager::getInstance()->isPointerUp(pid);
//	}
//
//
//	bool IScreen::isPointerDrag(u32 pid) 
//	{
//		return ScreenManager::getInstance()->isPointerDrag(pid);
//	}
//
//
//	float IScreen::getPointerX(u32 pid)
//	{
//		return ScreenManager::getInstance()->getPointerX(pid);
//	}
//
//
//	float IScreen::getPointerY(u32 pid)
//	{
//		return ScreenManager::getInstance()->getPointerY(pid);
//	}

};