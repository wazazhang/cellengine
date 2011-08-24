//
//  BMSGameSample.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-23.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_BMS_GAME_SAMPLE
#define _COM_CELL_BMS_GAME_SAMPLE

#include <string>
#include <list>

#include "CSoundManager.h"
#include "CSoundPlayerPool.h"
#include "CGeometry.h"
#include "BMSFile.h"

namespace com_cell_bms
{
	using namespace com_cell;
	
	
	void render(Graphics2D &g, BMSPlayer *bms_player, BMSFile * bms_file)
	{
		
		if (bms_player->getPlayBGImage() != NULL) 
		{
			g.drawImageSize(bms_player->getPlayBGImage(), 0, 0, getWidth(), getHeight());
			//g.drawImage(pBmsPlayer->getPlayBGImage(), 0, 0);
		}
		
		// paint notes
		{
			vector<Note *> note_list;
			bms_player->getPlayKeyTracks(bms_file->getLineSplitDiv(), note_list);
			
			for (vector<Note *>::const_iterator it = note_list.begin(); 
				 it != note_list.end(); ++it)
			{
				Note *note = (*it);
				float x = 12*note->getTrack();
				float y = (pBmsPlayer->getPlayPosition() - note->getBeginPosition()) + getHeight()/2;
				g.setColor(COLOR_DARK_GRAY);
				g.fillRect(x-1, y-1, 12, 4);	
				g.setColor(COLOR_WHITE);
				g.fillRect(x, y, 10, 2);
			}
		}
	}
	
	
	
	
}; // namespcace 

#endif // _COM_CELL_BMS_GAME_SAMPLE
