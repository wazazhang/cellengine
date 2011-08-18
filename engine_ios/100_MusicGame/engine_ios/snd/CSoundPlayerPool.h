//
//  CSoundPlayerManager.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-19.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SOUND_PLAYER_POOL
#define _COM_CELL_SOUND_PLAYER_POOL

#include <string>
#include "CSoundInfo.h"
#include "CSoundManager.h"

namespace com_cell 
{
    class SoundPlayerPool
    {
    private:
		
		std::vector<SoundPlayer*> m_list_playing;
		
    public:
        
        SoundPlayerPool();
        
        ~SoundPlayerPool();
        
		/**尽可能的播放，加入有空闲的声道的话*/
		void playSound(Sound *sound, bool loop);

		
		/**得到空闲的播放器*/
		SoundPlayer* getFreePlayer();
		
    };    
	
}; // namespcace 

#endif // _COM_CELL_SOUND_PLAYER_POOL
