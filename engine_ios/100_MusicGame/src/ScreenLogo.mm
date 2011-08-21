/*
 *  ScreenLogo.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */
#include "CMath.h"
#include "CFile.h"
#include "CXml.h"
#include "CGFXManager.h"

#include "Screens.h"
#include "ScreenLogo.h"
#include "ScreenGame.h"

namespace gt_teris
{
	using namespace com_cell;	
	using namespace com_cell_bms;

	
    void ScreenLogo::init() 
    {
        printf("init\n");
		
		parseXML("/edit/actor/output/Project.xml");

		angle		= 0;
		getTimer();
		stringSplitRegx("what is the matrix", "\\s");
		
		double fvalue = 0;
		if (stringToDouble("0123233.14159", fvalue)) {
			printf(" %lf\n", fvalue);
		}
		
		long ivalue = 0;
		if (stringToLong("1234", ivalue)) {
			printf(" %ld\n", ivalue);
		}
		
        pSprite		= GFXManager::createImage("/Sprite.png");

		pSoundInfo1	= SoundManager::getInstance()->createSoundInfo("sound.caf");
		pSoundInfo2	= SoundManager::getInstance()->createSoundInfo("res/bgm.wav");
		pSoundInfo3	= SoundManager::getInstance()->createSoundInfo("res/s1.wav");

		
		pSound1		= SoundManager::getInstance()->createSound(pSoundInfo1);
		pSound2		= SoundManager::getInstance()->createSound(pSoundInfo2);
		pSound3		= SoundManager::getInstance()->createSound(pSoundInfo3);

		pSoundPlayer1= SoundManager::getInstance()->createPlayer();
		pSoundPlayer2= SoundManager::getInstance()->createPlayer();
		pSoundPlayer3= SoundManager::getInstance()->createPlayer();
		
		pSoundPlayer1->setSound(pSound1);
		pSoundPlayer2->setSound(pSound2);
		pSoundPlayer3->setSound(pSound3);
		
		pSoundPlayer1->play(false);		
		pSoundPlayer2->play(true);

	}
	
    void ScreenLogo::destory() 
    {
        printf("destory\n");

		delete pSoundPlayer1;
		delete pSoundPlayer2;
		delete pSoundPlayer3;
		
		delete pSound1;
		delete pSound2;
		delete pSound3;
		
		delete pSoundInfo1;
		delete pSoundInfo2;
		delete pSoundInfo3;
		
		delete pSprite;
		
		

    }
	
	
    void ScreenLogo::update() 
    {
        //printf("update\n");
        
        angle += 1.0f;
		
		if (com_cell::IScreen::isPointerDown(0)) {
			pSoundPlayer3->play(false);	
			getScreenManager()->changeScreen(Screens::SCREEN_GAME);
		}
    }
    
    void ScreenLogo::render(Graphics2D &g) 
    {
        g.setBlend(BLEND_NORMAL);
        g.setAlpha(1);
        
        g.setColor(1, 0, 0, 0);
        g.fillScreen();
        
        
        g.setColor(1, 1, 0, 0);
        g.fillRect(10, 10, 100, 100);
        
        g.setColor(1, 1, 1, 1);
        g.fillRect(20, 20, 100, 100);    
        
        g.setColor(1, 1, 1, 0);
        g.drawRect(30, 330, 100, 100);   
        
        g.drawLine(0, 0, getScreenWidth(), getScreenHeight());   

        
        
        g.drawImage(pSprite, 32, 32);
        g.drawImage(pSprite, 64, 64);

        g.setAlpha(.5f);
        g.drawImage(pSprite, 96, 96);
        
        g.setBlend(BLEND_SCREEN);
        g.translate(getScreenWidth()/2, getScreenHeight()/2);
        g.setAlpha(.5f);
        g.drawImage(pSprite, 
                    10*sinf(angle/10), 
                    10*cosf(angle/10));
        g.drawImage(pSprite, 32, 32);
        g.drawImage(pSprite, -32, -32);

        
        g.setBlend(BLEND_NORMAL);
        g.pushTransform();
        {
            g.setAlpha(0.5+sinf(angle/10)/2);
            g.scale(2*sinf(angle/25), 2*sinf(angle/25));
            g.rotate(angle);
            g.drawImage(pSprite, -pSprite->getWidth()/2, -pSprite->getHeight()/2);
        }
        g.popTransform();
        
        
        
        g.setAlpha(1);
        g.setColor(1, 1, 1, 1);
        g.drawArc(0, 0, 200, 200, 0, 360);
        
        g.setColor(1, 1, 0, 0);
        g.fillArc(0, 200, 200, 100, 0, 360);
        
        // printf("degree %f", degree);
    }
    
    void ScreenLogo::notifyPause()
    {
        //printf("notifyPause\n");
        
    }
    
    void ScreenLogo::notifyResume() 
    {
        //printf("notifyResume\n");
        
    }
    
	
}; // namespace gt_teris

