/*
 *  ScreenLogo.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */
#include "CMath.h"
#include "Screens.h"
#include "ScreenLogo.h"
#include "ScreenGame.h"

namespace gt_teris
{
    void ScreenLogo::init() 
    {
        printf("init\n");
        
        pSprite		= new Image("/Sprite.png");
        angle		= 0;
		
    
//		pSoundInfo	= SoundManager::getInstance()->createSoundInfo("sound.caf");
		pSoundInfo	= SoundManager::getInstance()->createSoundInfo("bgm.wav");
		pSound		= SoundManager::getInstance()->createSound(pSoundInfo);
		pSoundPlayer= SoundManager::getInstance()->createPlayer();
		
		pSoundPlayer->setSound(pSound);
		
		pSoundPlayer->play(100);
	}
	
    void ScreenLogo::destory() 
    {
        printf("notifyDestory\n");
//        delete pSoundPlayer;
//        delete pSound;
//        delete pSoundInfo;
		delete pSprite;
    }
	
	
    void ScreenLogo::update() 
    {
        //printf("update\n");
        
        angle += 1.0f;
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

