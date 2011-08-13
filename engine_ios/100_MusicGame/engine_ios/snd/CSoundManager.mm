//
//  CSoundManager.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSoundManager.h"
#include <iostream.h>


namespace com_cell 
{
    
    SoundManager::SoundManager()
    {
        // Create a new OpenAL Device
        // Pass NULL to specify the system’s default output device
        device = alcOpenDevice(NULL);
        if (device != NULL)
        {
            // Create a new OpenAL Context
            // The new context will render to the OpenAL Device just created 
            context = alcCreateContext(device, 0);
            if (context != NULL)
            {
                // Make the new context the Current OpenAL Context
                alcMakeContextCurrent(context);
            }
        }
        // clear any errors
        checkError();
        
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

        cout << toString() << endl;
    }

    
    std::string SoundManager::toString() 
    {
        const ALCchar* device_spec = alcGetString(device, ALC_DEVICE_SPECIFIER);
        ALCint alc_state[5];
        alcGetIntegerv(device, ALC_FREQUENCY,      	1, alc_state); 
        alcGetIntegerv(device, ALC_MONO_SOURCES,   	1, alc_state); 
        alcGetIntegerv(device, ALC_REFRESH,        	1, alc_state); 
        alcGetIntegerv(device, ALC_STEREO_SOURCES,	1, alc_state); 
        alcGetIntegerv(device, ALC_SYNC, 			1, alc_state); 
        
        std::string sb = std::string("AL : Current OpenAL Device !\n");
        sb.append("\t  OpenAL Device : ").append(device_spec).append("\n");
        sb.append("\t      Frequency : ").append(intToString(alc_state[0])).append("\n");
        sb.append("\t   Mono sources : ").append(intToString(alc_state[1])).append("\n");
        sb.append("\t        Refresh : ").append(intToString(alc_state[2])).append("\n");
        sb.append("\t Stereo sources : ").append(intToString(alc_state[3])).append("\n");
        sb.append("\t           Sync : ").append(intToString(alc_state[4]));
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
    
    /**
     * suffix .wav or .ogg are supported
     */
    SoundInfo* SoundManager::createSoundInfo(std::string const &resource) 
    {
        if (stringEndWidth(resource, ".wav")) {
            return initWav(resource);
        } else if (stringEndWidth(resource, ".ogg")) {
            return initOgg(resource);
        } else {
            NSLog(@"only \'.wav\' or \'.ogg\' support");
        }
        return NULL;
    }

    Sound*  SoundManager::createSound(SoundInfo* info) 
    {
        
        return NULL;
    }
    
    SoundPlayer*  SoundManager::createPlayer()
    {
        return NULL;
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    

}; // namespace