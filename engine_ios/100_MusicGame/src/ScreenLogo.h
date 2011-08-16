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
#include "BMSFile.h"


namespace gt_teris
{
	using namespace com_cell;	
	using namespace com_cell_bms;


	class ScreenLogo : public IScreen
	{
    private:
        Image			*pSprite;
        float			angle;
        
		SoundInfo*		pSoundInfo1;
		SoundInfo*		pSoundInfo2;
		SoundInfo*		pSoundInfo3;
		SoundInfo*		pSoundInfo4;
		SoundInfo*		pSoundInfo5;
		SoundInfo*		pSoundInfo6;
		SoundInfo*		pSoundInfo7;
		SoundInfo*		pSoundInfo8;
		
		Sound*			pSound1;
		Sound*			pSound2;
		Sound*			pSound3;
		Sound*			pSound4;
		Sound*			pSound5;
		Sound*			pSound6;
		Sound*			pSound7;
		Sound*			pSound8;
		
		SoundPlayer*	pSoundPlayer1;
		SoundPlayer*	pSoundPlayer2;
		SoundPlayer*	pSoundPlayer3;
		SoundPlayer*	pSoundPlayer4;
		SoundPlayer*	pSoundPlayer5;
		SoundPlayer*	pSoundPlayer6;
		SoundPlayer*	pSoundPlayer7;
		SoundPlayer*	pSoundPlayer8;
		
		BMSFile*		pBmsFile;
		
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