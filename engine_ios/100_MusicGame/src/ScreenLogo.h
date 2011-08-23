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
#include "CGFXManager.h"
#include "CCellResource.h"
#include "BMSFile.h"



namespace gt_teris
{
	using namespace com_cell;	
	using namespace com_cell_bms;
	using namespace com_cell_game;

	class ScreenLogo : public IScreen
	{
    private:
		CellResource	*resource;
		CSprite			*actor;
		
		
        Image			*pSprite;
        
		SoundInfo*		pSoundInfo1;
		SoundInfo*		pSoundInfo2;
		SoundInfo*		pSoundInfo3;
		
		Sound*			pSound1;
		Sound*			pSound2;
		Sound*			pSound3;
		
		SoundPlayer*	pSoundPlayer1;
		SoundPlayer*	pSoundPlayer2;
		SoundPlayer*	pSoundPlayer3;
		
		
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