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
        
        SoundPlayerPool(unsigned int size);
        
        ~SoundPlayerPool();
        
		/**尽可能的播放，可能切断其他正在播放的音源
		 @return 是否切断其他音源*/
		bool			playSoundImmediately(Sound *sound, bool loop);

		/**尽可能的播放，如果没有可用音源，则放弃
		 @return 是否播放成功*/
		bool			playSound(Sound *sound, bool loop);
		
		/**得到空闲的音源*/
		SoundPlayer*	getFreePlayer();
		
		/**得到空闲或快播放完的音源*/
		SoundPlayer*	getImmediatePlayer(bool &out_is_cut_another);
		
		int				getFreePlayerCount();
    };    
	
}; // namespcace 

#endif // _COM_CELL_SOUND_PLAYER_POOL
