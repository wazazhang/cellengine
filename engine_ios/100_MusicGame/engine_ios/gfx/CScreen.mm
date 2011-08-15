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

	bool IScreen::isPointerHoldLag(int id)
	{
		return ScreenManager::getInstance()->isPointerHoldLag(id);
	}

	bool IScreen::isPointerHold(int id)
	{
		return ScreenManager::getInstance()->isPointerHold(id);
	}


	bool IScreen::isPointerDown(int id) 
	{
		return ScreenManager::getInstance()->isPointerDown(id);
	}


	bool IScreen::isPointerUp(int id)
	{
		return ScreenManager::getInstance()->isPointerUp(id);
	}


	bool IScreen::isPointerDrag(int id) 
	{
		return ScreenManager::getInstance()->isPointerDrag(id);
	}


	float IScreen::getPointerX(int id)
	{
		return ScreenManager::getInstance()->getPointerX(id);
	}


	float IScreen::getPointerY(int id)
	{
		return ScreenManager::getInstance()->getPointerY(id);
	}

};