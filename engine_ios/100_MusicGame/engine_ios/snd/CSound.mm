//
//  CSound.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSound.h"
#include "CSoundManager.h"
#include "CSoundDecode.h"

#import <OpenAL/oalStaticBufferExtension.h>

namespace com_cell 
{
	typedef ALvoid	AL_APIENTRY	(*alBufferDataStaticProcPtr) (const ALint bid, 
															  ALenum format,
															  ALvoid* data, 
															  ALsizei size,
															  ALsizei freq);
	ALvoid  alBufferDataStaticProc(const ALint bid, 
								   ALenum format, 
								   ALvoid* data, 
								   ALsizei size, 
								   ALsizei freq);

	
    Sound::Sound(SoundInfo* data)
	{
		m_buffer_id = 0;

		if (data != NULL) 
		{
            alGenBuffers(1, &m_buffer_id);
			if (SoundManager::checkError()) {
				NSLog(@"Error generating OpenAL buffers");
				m_buffer_id = 0;
                return;
			}
			
			m_format = AL_FORMAT_MONO16;
			if (data->getBitLength() == 16) {
				if (data->getChannels()==1) {
					m_format = AL_FORMAT_MONO16;
				} else {
					m_format = AL_FORMAT_STEREO16;
				}
			} else if (data->getBitLength() == 8) {
				if (data->getChannels()==1) {
					m_format = AL_FORMAT_MONO8;
				} else {
					m_format = AL_FORMAT_STEREO8;
				}
			}
            
			m_size		= data->getDataSize();
			m_framerate = data->getFrameRate();
			
			//*//将内存交给openAL管理
			// variables to load into
//			alBufferData(m_buffer_id, format, 
//                         sound_info->getData(),
//                         sound_info->getDataSize(), 
//                         sound_info->getFrameRate());
			//手动管理内存
			// use the static buffer data API
			alBufferDataStaticProc(m_buffer_id, 
								   m_format, 
								   data->getData(),
								   m_size, 
								   m_framerate);
			//*/
			
			// Do another error check and return.
			if (SoundManager::checkError()) {
				alDeleteBuffers(1, &m_buffer_id);
				if (SoundManager::checkError()) {}
				m_buffer_id = 0;
			}
			
			//printf("buffer : %s \n", data->toString().c_str() );
		}
	}
    
    Sound::~Sound()
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
	
    
	int Sound::getSize() {
		return m_size;
	}
	
	int Sound::getFormat()  {
		return m_format;
	}
	
	int Sound::getFrameRate() {
		return m_framerate;
	}
  

	
	ALvoid  alBufferDataStaticProc(const ALint bid, 
								   ALenum format, 
								   ALvoid* data, 
								   ALsizei size, 
								   ALsizei freq)
	{
		static	alBufferDataStaticProcPtr	proc = NULL;
		
		if (proc == NULL) {
			proc = (alBufferDataStaticProcPtr)
			alcGetProcAddress(NULL, (const ALCchar*) "alBufferDataStatic");
		}
		
		if (proc)
			proc(bid, format, data, size, freq);
		
		return;
	}

	
}; // namespcace 
