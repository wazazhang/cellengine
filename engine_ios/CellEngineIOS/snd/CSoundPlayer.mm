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

        // Bind buffer with a source.
        alGenSources(1, &m_source_id);
        
        if (SoundManager::checkError()) {
            NSLog(@"Error generating OpenAL source !");
			m_source_id = 0;
            return;
        }
		
        float zero_v[] = { 0.0f, 0.0f, 0.0f };
			
        alSourcefv(m_source_id,  AL_POSITION,			zero_v);
        alSourcefv(m_source_id,  AL_VELOCITY,			zero_v);
        alSourcefv(m_source_id,  AL_DIRECTION,			zero_v);
        
        alSourcef(m_source_id,   AL_PITCH,				1.0f);
        alSourcef(m_source_id,   AL_GAIN,				1.0f);
        alSourcef(m_source_id,   AL_ROLLOFF_FACTOR,		0.0f);
            
		alSourcei(m_source_id,   AL_SOURCE_RELATIVE,	AL_TRUE);
		alSourcei(m_source_id,   AL_LOOPING,			AL_FALSE);
		
		alSourcei(m_source_id,   AL_BUFFER,				AL_NONE);

        SoundManager::checkError();
		
        NSLog(@"AL: Create player : %d\n", m_source_id);
	}
	
	SoundPlayer::~SoundPlayer()
	{
		if (isEnable()) {
            // clearAllSound();				
			// make sure it is clean by resetting the source buffer to 0
			alSourcei(m_source_id, AL_BUFFER, AL_NONE);
            alDeleteSources(1, &m_source_id);
            SoundManager::checkError();
            NSLog(@"AL: Destory player : %d\n", m_source_id);		
			m_source_id = 0;	
		}
	}
	
	ALuint SoundPlayer::getSourceID()
	{
		return m_source_id;
	}

	
    bool SoundPlayer::isEnable()
    {
        return m_source_id != 0;
    }
    
	void SoundPlayer::setVolume(float value)
    {
		if (isEnable()) {
			value = MIN(1.0f, value);
			value = MAX(0.0f, value);
			alSourcef(m_source_id, AL_GAIN, value);
			SoundManager::checkError();
		}
	}
	
	float SoundPlayer::getVolume()
    {
		if (isEnable()) {
			ALfloat ret = 0;
			alGetSourcef(m_source_id, AL_GAIN, &ret);
			SoundManager::checkError();
			return ret;
		} else {
			return 0;
		}
	}
	
	void SoundPlayer::play(bool loop)
    {
		if (isEnable()) {
            ALint aloop = loop?AL_TRUE:AL_FALSE;
			alSourcei(m_source_id, AL_LOOPING, aloop);
			if (!SoundManager::checkError()) {
                alSourcePlay(m_source_id);
                SoundManager::checkError();
			}
		}
	}
	
	void SoundPlayer::pause() 
    {
		if (isEnable()) {
			alSourcePause(m_source_id);
			SoundManager::checkError();
		}
	}
	
	void SoundPlayer::resume() 
    {
		if (isEnable()) {
			alSourcePlay(m_source_id);
			SoundManager::checkError();
		}
	}
	
	void SoundPlayer::stop() 
    {
		if (isEnable()) {
			alSourceStop(m_source_id);
			SoundManager::checkError();
		}
	}
    
	bool SoundPlayer::isPlaying() 
    {
		if (isEnable()) {
			ALint state;
			alGetSourcei(m_source_id, AL_SOURCE_STATE, &state);
			if (SoundManager::checkError()) {
				return false;
			}
			return state == AL_PLAYING;
		}
		return true;
	}
	
    
	void SoundPlayer::setSound(Sound* sound)
	{
		if (isEnable()) {				
			// make sure it is clean by resetting the source buffer to 0
			alSourcei(m_source_id, AL_BUFFER, AL_NONE);
            if (sound->isEnable()) {
                alSourcei(m_source_id, AL_BUFFER, sound->getBufferID());
                SoundManager::checkError();
            }
		}
	}
    
	void SoundPlayer::queue(Sound* sound) 
	{
		if (isEnable()) {
            if (sound->isEnable()) {
				ALuint buffers[] = {sound->getBufferID()};
				alSourceQueueBuffers(m_source_id, 1, buffers);
				SoundManager::checkError();
            }	
		}
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
        if (!isEnable()) return;
        
        ALint processed;
        alGetSourcei(m_source_id, AL_BUFFERS_PROCESSED, &processed);
        SoundManager::checkError();
        
		ALuint buffers[processed];
        alSourceUnqueueBuffers(m_source_id, processed, buffers);
        
        ALenum error_code = alGetError();
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
		if (isEnable()) 
        {
			// stop all sound
			alSourceStop(m_source_id);
			SoundManager::checkError();
			
			// clean all sound
			alSourceRewind(m_source_id);
			SoundManager::checkError();
			
			alSourcei(m_source_id, AL_BUFFER, AL_NONE);
			SoundManager::checkError();

			// clean all processed sound
            clearQueued();
			
            
		}
	}
	
	
    
    
};