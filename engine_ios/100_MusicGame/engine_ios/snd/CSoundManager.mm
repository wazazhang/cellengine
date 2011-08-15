//
//  CSoundManager.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include <iostream.h>
#include "CSoundManager.h"
#include "CSoundDecode.h"



namespace com_cell 
{
	SoundManager* SoundManager::g_instance;
	
    SoundManager* SoundManager::getInstance() 
	{
		if (g_instance == NULL) {
			g_instance = new SoundManager();
		}
		return g_instance;
	}
	
	void SoundManager::destoryInstance() 
	{ 
		if (g_instance != NULL) {
			delete(g_instance);
		}
	}
	
	bool SoundManager::checkError()
	{
		int code = alGetError();
		if (code != AL_NO_ERROR) {
			NSLog(@"ALError: OpenAL error code : %x", code);
			return true;
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	
    SoundManager::SoundManager()
    {
        // Create a new OpenAL Device
        // Pass NULL to specify the system’s default output device
        device = alcOpenDevice(NULL);
        if (device != NULL)
        {
            // Create a new OpenAL Context
            // The new context will render to the OpenAL Device just created 
            context = alcCreateContext(device, NULL);
            if (context != NULL)
            {
                // Make the new context the Current OpenAL Context
                alcMakeContextCurrent(context);
            }
        }
        // clear any errors
        checkError();
		
        cout << toString() << endl;

        // set listeners
        // Position of the listener.
        float listenerPos[3] = { 0.0f, 0.0f, 0.0f };
        // Velocity of the listener.
        float listenerVel[3] = { 0.0f, 0.0f, 0.0f };
        // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
        float listenerOri[6] = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };
        
        alListenerfv(AL_POSITION,       listenerPos);
        alListenerfv(AL_VELOCITY,       listenerVel);
        alListenerfv(AL_ORIENTATION, 	listenerOri);
        
        checkError();
    }

	SoundManager::~SoundManager()
	{
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
    
    std::string SoundManager::toString() 
    {
        const ALCchar* device_spec = alcGetString(device, ALC_DEVICE_SPECIFIER);
		
        ALCint frequency, monosources, refresh, stereosources, sync;
        alcGetIntegerv(device, ALC_FREQUENCY,      	1, &frequency); 
        alcGetIntegerv(device, ALC_MONO_SOURCES,   	1, &monosources); 
        alcGetIntegerv(device, ALC_REFRESH,        	1, &refresh); 
        alcGetIntegerv(device, ALC_STEREO_SOURCES,	1, &stereosources); 
        alcGetIntegerv(device, ALC_SYNC, 			1, &sync); 
        
        std::string sb = std::string("AL : Current OpenAL Device !\n");
        sb.append("\t  OpenAL Device : ").append(device_spec).append("\n");
        sb.append("\t      Frequency : ").append(intToString(frequency)).append("\n");
        sb.append("\t   Mono sources : ").append(intToString(monosources)).append("\n");
        sb.append("\t        Refresh : ").append(intToString(refresh)).append("\n");
        sb.append("\t Stereo sources : ").append(intToString(stereosources)).append("\n");
        sb.append("\t           Sync : ").append(intToString(sync));
        return sb;
    }

    void SoundManager::setVolume(float volume)
    {
        alListenerf(AL_GAIN, volume);
        checkError();
    }
    
    float SoundManager::getVolume() 
    {
        float ret[1];
        alGetListenerf(AL_GAIN, ret);
        checkError();
        return ret[0];
    }
    
    SoundInfo* SoundManager::createSoundInfo(char* const filepath) 
    {
		return createStaticSound(filepath);
    }

    SoundInfo* SoundManager::createStreamSoundInfo(char* const filepath) 
    {
        return NULL;
    }

	
    Sound*  SoundManager::createSound(SoundInfo* info) 
    {
        return new Sound(info);
    }
    
    SoundPlayer*  SoundManager::createPlayer()
    {
        return new SoundPlayer();
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    

}; // namespace