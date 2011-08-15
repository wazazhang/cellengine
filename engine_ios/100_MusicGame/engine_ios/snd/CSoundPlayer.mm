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
        alSourcef(m_source_id,   AL_ROLLOFF_FACTOR,	0.0f);
            
		alSourcei(m_source_id,   AL_SOURCE_RELATIVE,	AL_TRUE);
		alSourcei(m_source_id,   AL_LOOPING,			0);
			
        SoundManager::checkError();
		
        NSLog(@"AL: Create player : %d\n", m_source_id);
	}
	
	SoundPlayer::~SoundPlayer()
	{
		destory();
	}
	
	void SoundPlayer::destory()
	{
		if (isEnable()) {
            // clearAllSound();
            alDeleteSources(1, &m_source_id);
            SoundManager::checkError();
            NSLog(@"AL: Destory player : %d\n", m_source_id);		
			m_source_id = 0;	
		}
	}
    
    inline bool SoundPlayer::isEnable()
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
			ALfloat ret[1] = {0};
			alGetSourcef(m_source_id, AL_GAIN, ret);
			SoundManager::checkError();
			return ret[0];
		} else {
			return 0;
		}
	}
	
	void SoundPlayer::play(int loop_count)
    {
		if (isEnable()) {
            loop_count = loop_count>0?1:0;
			alSourcei(m_source_id, AL_LOOPING, loop_count);
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
			ALint state[1] = {0};
			alGetSourcei(m_source_id, AL_SOURCE_STATE, state);
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
        if (!isEnable()) return;
        
        int processed[1] = {0};
        alGetSourcei(m_source_id, AL_BUFFERS_PROCESSED, processed);
        SoundManager::checkError();
        
		ALuint buffers[processed[0]];
        alSourceUnqueueBuffers(m_source_id, processed[0], buffers);
        
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
		if (isEnable()) 
        {
			// stop all sound
			alSourceStop(m_source_id);
			SoundManager::checkError();
			
			// clean all sound
			alSourceRewind(m_source_id);
			SoundManager::checkError();
            
			// clean all processed sound
            clearQueued();
			
			// clean all queued sound
			// 
			
			alSourcei(m_source_id, AL_BUFFER, 0);
			SoundManager::checkError();
            
		}
	}
	
	
	void SoundPlayer::setSound(Sound* sound)
	{
		if (isEnable()) {
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
    
    
};