//
//  CSound.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSoundManager.h"
#include "CSound.h"

namespace com_cell 
{
    
    Sound::Sound(SoundInfo* sound_info)
	{
		m_buffer_id = 0;
        m_pData = sound_info;

		if (m_pData != NULL) 
		{
			int format = AL_FORMAT_MONO16;
			if (m_pData->getBitLength() == 16) {
				if (m_pData->getChannels()==1) {
					format = AL_FORMAT_MONO16;
				} else {
					format = AL_FORMAT_STEREO16;
				}
			} else if (m_pData->getBitLength() == 8) {
				if (m_pData->getChannels()==1) {
					format = AL_FORMAT_MONO8;
				} else {
					format = AL_FORMAT_STEREO8;
				}
			}
            
            alGenBuffers(1, &m_buffer_id);
			if (SoundManager::checkError()) {
				NSLog(@"Error generating OpenAL buffers");
                return;
			}

			// variables to load into
			alBufferData(m_buffer_id, format, 
                         sound_info->getData(),
                         sound_info->getDataSize(), 
                         sound_info->getFrameRate());
			// Do another error check and return.
			if (SoundManager::checkError()) {
				alDeleteBuffers(1, &m_buffer_id);
				if (SoundManager::checkError()) {}
				m_buffer_id = 0;
			}
		}
	}
    
    Sound::~Sound()
    {
        destory();
    }
	
	void Sound::destory() 
	{
		if (isEnable()) {
			alDeleteBuffers(1, &m_buffer_id);
            if (SoundManager::checkError()) {
                NSLog(@"Error delete OpenAL buffers : %d", m_buffer_id);
            }
            m_buffer_id = 0;
		}
	}

	ALuint Sound::getBufferID() {
		return m_buffer_id;
	}
    
	bool Sound::isEnable() {
		return m_buffer_id != 0;
	}
	
    
	SoundInfo* Sound::getSoundInfo()
    {
		return m_pData;
	}
	
	int Sound::getSize() {
        if (m_pData != NULL) {
            return m_pData->getDataSize();
        }
        return 0;
	}
    
}; // namespcace 
