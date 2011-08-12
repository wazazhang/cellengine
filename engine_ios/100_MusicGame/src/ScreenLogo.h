/*
 *  ScreenLogo.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _TERIS_SCREEN_LOGO
#define _TERIS_SCREEN_LOGO


#include <math.h>
#include "Screens.h"


namespace gt_teris
{
	using namespace com_cell;

	class ScreenLogo : public IScreen
	{
    private:
        Image* pSprite;
        float angle;
        
	public:
			
		virtual void init() ;
	
		virtual void update() ;
		
		virtual void render(Graphics2D &g) ;
		
		virtual void notifyPause();
		
		virtual void notifyResume() ;
					
		virtual void destory() ;
		
	};


}; // namespace gt.teris




#endif // #define _TERIS_SCREEN_LOGO