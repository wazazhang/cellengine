//
//  CSoundPlayerPool.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-19.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSoundPlayerPool.h"

namespace com_cell 
{
	SoundPlayerPool::SoundPlayerPool()
	{
		for (int i=0; i<10; i++) {
			SoundPlayer* player = SoundManager::getInstance()->createPlayer();
			if (player != NULL) {
				if (!player->isEnable()) {
					delete player;
					player = NULL;
				} else {
					m_list_playing.push_back(player);
				}
			}
		}
	}
	
	SoundPlayerPool::~SoundPlayerPool()
	{
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			p->clearSound();
			delete p;
		}
	}

	/**尽可能的播放，加入有空闲的声道的话*/
	void SoundPlayerPool::playSound(Sound *sound, bool loop)
	{
		SoundPlayer* free = getFreePlayer();
		if (free != NULL) {
			free->setSound(sound);
			free->play(loop);
		}
	}
	
	/**得到空闲的播放器*/
	SoundPlayer* SoundPlayerPool::getFreePlayer()
	{
		SoundPlayer* player = NULL;
		
		// find the free player
		if (player == NULL) 
		{
			for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
				 it!=m_list_playing.end(); ++it) {
				SoundPlayer* p = (*it);
				if (!p->isPlaying()) {
					player = p;
					break;
				}
			}
		}
		
		// create a new player
		if (player == NULL) 
		{
			player = SoundManager::getInstance()->createPlayer();
			if (player != NULL) {
				if (!player->isEnable()) {
					delete player;
					player = NULL;
				} else {
					m_list_playing.push_back(player);
				}
			}
		}
		
		// stop an playing sound
		if (player == NULL) {
			std::vector<SoundPlayer*>::iterator it=m_list_playing.begin();
			if (it != m_list_playing.end()) {
				SoundPlayer* p = (*it);
				player = p;
				printf("cut a playing player!\n");
			}
		}
		
		if (player != NULL) 
		{
			player->stop();
			player->clearSound();
		}
		
		return player;
	}

	
}; // namespcace 

