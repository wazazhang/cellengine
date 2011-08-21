/*
 *  untitled.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-12.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#include <string>
#include <math.h>

#include "CXml.h"
#include "CGFXManager.h"

#include "ScreenGame.h"
#include "Screens.h"

namespace gt_teris
{
	using namespace com_cell;
	using namespace com_cell_bms;

	void ScreenGame::init() 
	{
        pSprite		= GFXManager::createImage("/Sprite.png");
		pBmsFile	= new BMSFile("/res/btm_er/er-5.bms", NULL);
		pBmsPlayer	= new BMSPlayer(pBmsFile, 30);
		pBmsPlayer->setListener(this);
		pBmsPlayer->start();
		bpm_pop		= 0;
	}
	
	void ScreenGame::destory() 
	{			
		delete pSprite;
		delete pBmsPlayer;
		delete pBmsFile;
	}
	
	
	void ScreenGame::update()
	{
		pBmsPlayer->update();
		
		if (com_cell::IScreen::isPointerDown(0)) {
			getScreenManager()->changeScreen(Screens::SCREEN_LOGO);
		}

	}
	
	void ScreenGame::render(Graphics2D &g)
	{
		g.pushTransform();

		if (pBmsPlayer->getPlayBGImage() != NULL) 
		{
			g.drawImageSize(pBmsPlayer->getPlayBGImage(), 0, 0, getWidth(), getHeight());
			
//			g.drawImage(pBmsPlayer->getPlayBGImage(), 0, 0);
		}
		{
			g.translate(getScreenWidth()/2, getScreenHeight()/2);
		        
			float angle = getTimer();
		
			//g.setAlpha(0.5+sinf(angle/10.0f)/2);
			//g.scale(1+sinf(angle/25), 1+sinf(angle/25));
			g.rotate(angle);
			if (bpm_pop>0) {
				g.scale(1+bpm_pop/32.0f, 1+bpm_pop/32.0f);
			}
			g.drawImage(pSprite, -pSprite->getWidth()/2, -pSprite->getHeight()/2);
        
		}
        g.popTransform();
		
		bpm_pop--;

	}
	
	void ScreenGame::notifyPause() 
	{
		
	}
	
	void ScreenGame::notifyResume() 
	{
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	// BMS Event
	
	void ScreenGame::onBeat(int beat_count) 
	{
		bpm_pop = 16;
	}
	
	void ScreenGame::onDropNote(Note *note) 
	{
		
	}

	void ScreenGame::onHit(Note *note) 
	{
		
	}

	void ScreenGame::onAutoHit(Note *note) 
	{
		
	}



	
};// namespace gt_teris
