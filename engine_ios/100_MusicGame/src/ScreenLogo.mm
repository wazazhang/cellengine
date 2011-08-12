/*
 *  ScreenLogo.cpp
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */
#include "Screens.h"

#include "ScreenLogo.h"
#include "ScreenGame.h"

namespace gt_teris
{
    void ScreenLogo::init() 
    {
        //printf("init\n");
        
        pSprite = new Image("/Sprite.png");
        degree = 0;
    }
	
    void ScreenLogo::update() 
    {
        //printf("update\n");
        
    }
    
    void ScreenLogo::render(Graphics2D &g) 
    {
        g.setBlend(BLEND_NORMAL);
        g.setAlpha(1);
        
        g.setColor(1, 0, 0, 1);
        g.fillScreen();
        
        //printf("render %d\n", getTimer());
        g.setColor(1, 1, 0, 0);
        g.fillRect(10, 10, 100, 100);
        
        g.setColor(1, 1, 1, 1);
        g.fillRect(20, 20, 100, 100);
        
        g.setBlend(BLEND_SCREEN);
        g.translate(300, 300);
        //g.rotate(degree);
        g.setAlpha(.5f);
        g.drawImage(pSprite, 0, 0);
        g.drawImage(pSprite, 32, 32);
        g.drawImage(pSprite, -32, -32);
        g.translate(-300, -300);
        //printf("degree %f", degree);
    }
    
    void ScreenLogo::notifyPause()
    {
        //printf("notifyPause\n");
        
    }
    
    void ScreenLogo::notifyResume() 
    {
        //printf("notifyResume\n");
        
    }
    
    void ScreenLogo::destory() 
    {
        //printf("notifyDestory\n");
        
    }
	
}; // namespace gt_teris

