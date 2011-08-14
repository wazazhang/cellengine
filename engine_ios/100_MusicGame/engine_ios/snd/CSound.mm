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
        m_pData = sound_info;
        m_pBuffer = NULL;
        
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
            
            alGenBuffers(1, m_pBuffer);
			if (SoundManager::checkError()) {
				NSLog(@"Error generating OpenAL buffers");
                m_pBuffer = NULL;
                return;
			}

			// variables to load into
			alBufferData(*m_pBuffer, format, 
                         sound_info->getData(),
                         sound_info->getDataSize(), 
                         sound_info->getFrameRate());
			// Do another error check and return.
			if (SoundManager::checkError()) {
				alDeleteBuffers(1, m_pBuffer);
                m_pBuffer = NULL;
				if (SoundManager::checkError()) {}
			}
		}
	}
    
    Sound::~Sound()
    {
        destory();
    }
	
	void Sound::destory() 
	{
		if (m_pBuffer != NULL) {
			alDeleteBuffers(1, m_pBuffer);
            if (SoundManager::checkError()) {
                NSLog(@"Error delete OpenAL buffers : %d", *m_pBuffer);
            }
            m_pBuffer = NULL;
		}
	}

	ALuint Sound::getBufferID() {
        if (m_pBuffer != NULL) {
            return *m_pBuffer;
        }
        return 0;
	}
    
	bool Sound::isEnable() {
		return m_pBuffer != NULL;
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
