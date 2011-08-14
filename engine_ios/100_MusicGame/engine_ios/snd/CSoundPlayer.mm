//
//  CSoundPlayer.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-14.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSoundPlayer.h"
#include "CSoundManager.h"


namespace com_cell 
{
    SoundPlayer::SoundPlayer()
	{
        m_pSource = NULL;

        // Bind buffer with a source.
        alGenSources(1, m_pSource);
        
        if (SoundManager::checkError()) {
            NSLog(@"Error generating OpenAL source !");
            m_pSource = NULL;
            return;
        }
            
        float zero_v[] = { 0.0f, 0.0f, 0.0f };
			
        alSourcefv(*m_pSource,  AL_POSITION,        zero_v);
        alSourcefv(*m_pSource,  AL_VELOCITY,        zero_v);
        alSourcefv(*m_pSource,  AL_DIRECTION,       zero_v);
        
        alSourcef(*m_pSource,   AL_PITCH,			1.0f);
        alSourcef(*m_pSource,   AL_GAIN,			1.0f);
        alSourcef(*m_pSource,   AL_ROLLOFF_FACTOR,  0.0f);
            
		alSourcei(*m_pSource,   AL_SOURCE_RELATIVE, AL_TRUE);
		alSourcei(*m_pSource,   AL_LOOPING,			0);
			
        SoundManager::checkError();
		
        NSLog(@"AL: Create player : %d\n", *m_pSource);
	}
	
	void SoundPlayer::destory()
	{
		if (m_pSource != NULL) {
            // clearAllSound();
            alDeleteSources(1, m_pSource);
            SoundManager::checkError();
            NSLog(@"AL: Destory player : %d\n", *m_pSource);			
		}
	}
    
    bool SoundPlayer::isEnable()
    {
        return m_pSource != NULL;
    }
    
	void SoundPlayer::setVolume(float value)
    {
		if (m_pSource != NULL) {
			value = MIN(1.0f, value);
			value = MAX(0.0f, value);
			alSourcef(*m_pSource, AL_GAIN, value);
			SoundManager::checkError();
		}
	}
	
	float SoundPlayer::getVolume()
    {
		if (m_pSource != NULL) {
			ALfloat ret[1] = {0};
			alGetSourcef(*m_pSource, AL_GAIN, ret);
			SoundManager::checkError();
			return ret[0];
		} else {
			return 0;
		}
	}
	
	void SoundPlayer::play(int loop_count)
    {
		if (m_pSource != NULL) {
            loop_count = MAX(loop_count, 0);
			alSourcei(*m_pSource, AL_LOOPING, loop_count);
			if (!SoundManager::checkError()) {
                alSourcePlay(*m_pSource);
                SoundManager::checkError();
			}
		}
	}
	
	void SoundPlayer::pause() 
    {
		if (m_pSource != NULL) {
			alSourcePause(*m_pSource);
			SoundManager::checkError();
		}
	}
	
	void SoundPlayer::resume() 
    {
		if (m_pSource != NULL) {
			alSourcePlay(*m_pSource);
			SoundManager::checkError();
		}
	}
	
	void SoundPlayer::stop() 
    {
		if (m_pSource != NULL) {
			alSourceStop(*m_pSource);
			SoundManager::checkError();
		}
	}
    
	bool SoundPlayer::isPlaying() 
    {
		if (m_pSource != NULL) {
			ALint state[1] = {0};
			alGetSourcei(*m_pSource, AL_SOURCE_STATE, state);
			if (SoundManager::checkError()) {
				return false;
			}
			return state[0] == AL_PLAYING;
		}
		return true;
	}
	
    
    //	// clean all processed sound
    //	int[] processed = new int[1];
    //	al.alGetSourcei(source[0], AL.AL_BUFFERS_PROCESSED, processed, 0);
    //	JALSoundManager.checkError(al);
    //	
    //	// clean all queued sound
    //	int[] queued = new int[1];
    //	al.alGetSourcei(source[0], AL.AL_BUFFERS_QUEUED, queued, 0);
    //	JALSoundManager.checkError(al);

	void SoundPlayer::clearQueued() 
	{
        if (m_pSource == NULL) return;
        
        int processed[1] = {0};
        alGetSourcei(*m_pSource, AL_BUFFERS_PROCESSED, processed);
        SoundManager::checkError();
        
		ALuint buffers[processed[0]];
        alSourceUnqueueBuffers(*m_pSource, processed[0], buffers);
        
        int error_code = alGetError();
        if (error_code != AL_NO_ERROR) {
            switch (error_code) {
            case AL_INVALID_VALUE:
                NSLog(@"At least one buffer can not be unqueued because it has not been processed yet.");
            break;
            case AL_INVALID_NAME:
                NSLog(@"The specified source name is not valid.");
            break;
            case AL_INVALID_OPERATION:
                NSLog(@"There is no current context.");
            break;
            default:
                NSLog(@"OpenAL error code : %x", error_code);
            }
        }
		
	}
	
	void SoundPlayer::clearSound() 
	{
		if (m_pSource != NULL) 
        {
			// stop all sound
			alSourceStop(*m_pSource);
			SoundManager::checkError();
			
			// clean all sound
			alSourceRewind(*m_pSource);
			SoundManager::checkError();
            
			// clean all processed sound
            clearQueued();
			
			// clean all queued sound
			// 
			
			alSourcei(*m_pSource, AL_BUFFER, 0);
			SoundManager::checkError();
            
		}
	}
	
	
	void SoundPlayer::setSound(Sound* sound)
	{
		if (m_pSource != NULL) {
            if (sound->isEnable()) {
                alSourcei(*m_pSource, AL_BUFFER, sound->getBufferID());
                SoundManager::checkError();
            }
		}
	}
    
	void SoundPlayer::queue(Sound* sound) 
	{
		if (m_pSource != NULL) {
            if (sound->isEnable()) {
				ALuint buffers[] = {sound->getBufferID()};
				alSourceQueueBuffers(*m_pSource, 1, buffers);
				SoundManager::checkError();
            }	
		}
	}
    
    
};