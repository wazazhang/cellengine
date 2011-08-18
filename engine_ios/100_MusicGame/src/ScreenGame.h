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

#include "BMSPlayer.h"
#include "BMSFile.h"

namespace gt_teris
{
	
	using namespace com_cell;
	using namespace com_cell_bms;
	

	class ScreenGame : public IScreen, public BMSPlayerListener
	{

	public:
		
		Image			*pSprite;

		BMSFile*		pBmsFile;
						
		BMSPlayer*		pBmsPlayer;
		
		int				bpm_pop;
		
	public:
		
		virtual void init() ;
		
		virtual void update() ;
		
		virtual void render(Graphics2D &g) ;
		
		virtual void notifyPause();
		
		virtual void notifyResume() ;
		
		virtual void destory() ;

		
		
		virtual void onBeat(int beat_count) ;
		
		virtual void onDropNote(Note *note) ;
		
		virtual void onHit(Note *note) ;
		
		virtual void onAutoHit(Note *note) ;

	};
	
	
}; // namespace gt.teris




#endif // #define _TERIS_SCREEN_GAME