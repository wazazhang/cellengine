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
        
		stringSplitRegx("what is the matrix", "\\s");
		
		double fvalue = 0;
		if (stringToFloat("0123233.14159", fvalue)) {
			printf(" %lf\n", fvalue);
		}
		
		long ivalue = 0;
		if (stringToInt("1234", ivalue)) {
			printf(" %ld\n", ivalue);
		}
		
        pSprite		= GFXManager::createImage("/Sprite.png");
        angle		= 0;
		
		
		pBmsFile	= new BMSFile("/res/btm_er/er-5.bms", NULL);
		delete pBmsFile;
/*	
		pSoundInfo1	= SoundManager::getInstance()->createSoundInfo("sound.caf");
		pSoundInfo2	= SoundManager::getInstance()->createSoundInfo("res/bgm.wav");
		pSoundInfo3	= SoundManager::getInstance()->createSoundInfo("res/s1.wav");
		pSoundInfo4	= SoundManager::getInstance()->createSoundInfo("res/s2.wav");
		pSoundInfo5	= SoundManager::getInstance()->createSoundInfo("res/s3.wav");
		pSoundInfo6	= SoundManager::getInstance()->createSoundInfo("res/s4.wav");
		pSoundInfo7	= SoundManager::getInstance()->createSoundInfo("res/s5.wav");
		pSoundInfo8	= SoundManager::getInstance()->createSoundInfo("res/s6.wav");
		
///*		
		pSound1		= SoundManager::getInstance()->createSound(pSoundInfo1);
		pSound2		= SoundManager::getInstance()->createSound(pSoundInfo2);
		pSound3		= SoundManager::getInstance()->createSound(pSoundInfo3);
		pSound4		= SoundManager::getInstance()->createSound(pSoundInfo4);
		pSound5		= SoundManager::getInstance()->createSound(pSoundInfo5);
		pSound6		= SoundManager::getInstance()->createSound(pSoundInfo6);
		pSound7		= SoundManager::getInstance()->createSound(pSoundInfo7);
		pSound8		= SoundManager::getInstance()->createSound(pSoundInfo8);

		pSoundPlayer1= SoundManager::getInstance()->createPlayer();
		pSoundPlayer2= SoundManager::getInstance()->createPlayer();
		pSoundPlayer3= SoundManager::getInstance()->createPlayer();
		pSoundPlayer4= SoundManager::getInstance()->createPlayer();
		pSoundPlayer5= SoundManager::getInstance()->createPlayer();
		pSoundPlayer6= SoundManager::getInstance()->createPlayer();
		pSoundPlayer7= SoundManager::getInstance()->createPlayer();
		pSoundPlayer8= SoundManager::getInstance()->createPlayer();
		
		pSoundPlayer1->setSound(pSound1);
		pSoundPlayer2->setSound(pSound2);
		pSoundPlayer3->setSound(pSound3);
		pSoundPlayer4->setSound(pSound4);
		pSoundPlayer5->setSound(pSound5);
		pSoundPlayer6->setSound(pSound6);
		pSoundPlayer7->setSound(pSound7);
		pSoundPlayer8->setSound(pSound8);
		
		pSoundPlayer1->play(false);		
		pSoundPlayer2->play(true);
//*/
	}
	
    void ScreenLogo::destory() 
    {
        printf("notifyDestory\n");
//		delete pSoundPlayer;
//		delete pSound;
//		delete pSoundInfo;
		delete pSprite;
    }
	
	
    void ScreenLogo::update() 
    {
        //printf("update\n");
        
        angle += 1.0f;
		
//		if (com_cell::IScreen::isPointerDown(0)) {
//			pSoundPlayer3->play(false);	
//		}
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

