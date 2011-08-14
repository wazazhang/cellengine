/*
 *  Screens.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-12.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _TERIS_SCREENS
#define _TERIS_SCREENS


#include "CType.h"
#include "CSoundManager.h"
#include "CScreenManager.h"

#include "CScreen.h"
#include "ScreenLogo.h"
#include "ScreenGame.h"


namespace gt_teris
{
	using namespace com_cell;

	
	class Screens : public IScreenFactory
	{
	public:
		
		static const int SCREEN_GAME	= 1;
		static const int SCREEN_LOGO	= 2;
		
	public:
	
		inline virtual int getRootScreen() {
            return SCREEN_LOGO;
        }

		inline virtual IScreen* createScreen(int type) {
            switch(type)
            {
                case SCREEN_GAME:
                    return new ScreenGame();
                case SCREEN_LOGO:
                    return new ScreenLogo();
            }
            return NULL;
        }
		
	};


}; // namespace gt.teris




#endif // #define _TERIS_SCREENS