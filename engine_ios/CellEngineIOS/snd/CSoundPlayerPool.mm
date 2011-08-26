//
//  CSoundPlayerPool.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-19.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSoundPlayerPool.h"
#import <OpenAL/al.h>
#import <OpenAL/alc.h>

namespace com_cell 
{
	SoundPlayerPool::SoundPlayerPool(unsigned int size)
	{
		for (int i=0; i<size; i++) {
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
	
	/////////////////////////////////////////////////////////////////////////
	
	bool SoundPlayerPool::playSoundImmediately(Sound *sound, bool loop)
	{
		bool ret = false;
		SoundPlayer* free = getImmediatePlayer(ret);
		if (free != NULL) {
			free->setSound(sound);
			free->play(loop);
		}
		return ret;
	}
	
	bool SoundPlayerPool::playSound(Sound *sound, bool loop)
	{
		SoundPlayer* free = getFreePlayer();
		if (free != NULL) {
			free->setSound(sound);
			free->play(loop);
		}
		return free != NULL;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	SoundPlayer* SoundPlayerPool::getFreePlayer()
	{		
		// find the free player
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			if (!p->isPlaying()) {
				return p;
			}
		}
		
		return NULL;
	}

	SoundPlayer* SoundPlayerPool::getImmediatePlayer(bool &out_is_cut_another)
	{
		int min_size_player = INT32_MAX;
		SoundPlayer* min_player = NULL;
		
		// find the free player
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			if (!p->isPlaying()) {
				out_is_cut_another = false;
				return p;
			} else {
				ALint buffer_id;
				alGetSourcei(p -> getSourceID(), AL_BUFFER, &buffer_id);
				if (!SoundManager::checkError()) {
					if (alIsBuffer(buffer_id)) {
						ALint size;
						alGetBufferi(buffer_id, AL_SIZE, &size);
						if (!SoundManager::checkError()) {
							if (size < min_size_player) {
								min_player = p;
								min_size_player = size;
							}
						}
					}
				}
			}
		}
		out_is_cut_another = true;
		return min_player;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	int SoundPlayerPool::getFreePlayerCount()
	{
		int ret = 0;
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			if (!p->isPlaying()) {
				ret++;
			}
		}
		return ret;
	}

	bool SoundPlayerPool::isAllFree()
	{
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			if (p->isPlaying()) {
				return false;
			}
		}
		return true;
	}
	
	
	void SoundPlayerPool::stopAllSound()
	{
		for (std::vector<SoundPlayer*>::iterator it=m_list_playing.begin(); 
			 it!=m_list_playing.end(); ++it) {
			SoundPlayer* p = (*it);
			p->stop();
		}
	}
	
}; // namespcace 

