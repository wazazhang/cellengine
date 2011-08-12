/*
 *  untitled.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-12.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _TERIS_SCREEN_GAME
#define _TERIS_SCREEN_GAME


#include "CType.h"
#include "CScreen.h"
#include "CScreenManager.h"
#include "CGraphics2D.h"

namespace gt_teris
{
	
	using namespace com_cell;
	

	class ScreenGame : public IScreen
	{
		
		
	public:
		
		
						
	public:
		
		virtual void init() {
            
        }
		virtual void update() {
            
        }
		virtual void render(Graphics2D &g) {
        }
		virtual void notifyPause() {
            
        }
		virtual void notifyResume() {
            
        }
		virtual void destory() {
            
        }

	};
	
	
}; // namespace gt.teris




#endif // #define _TERIS_SCREEN_GAME