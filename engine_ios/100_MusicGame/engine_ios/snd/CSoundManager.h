//
//  CSoundManager.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_SOUND_MANAGER
#define _COM_CELL_SOUND_MANAGER

#include <string>
#import <OpenAL/al.h>
#import <OpenAL/alc.h>


#include "CUtil.h"
#include "CSound.h"
#include "CSoundInfo.h"
#include "CSoundPlayer.h"

namespace com_cell 
{
    class SoundManager
    {  
    private:
        static SoundManager *p_instance;
    public:
        static SoundManager* getInstance() {
            if (p_instance == NULL) {
                p_instance = new SoundManager();
            }
            return p_instance;
        }
        static void destoryInstance() { 
            if (p_instance != NULL) {
                delete p_instance;
            }
        }
        static bool checkError() {
            int code = alGetError();
            if (code != AL_NO_ERROR) {
                NSLog(@"ALError: OpenAL error code : %x", code);
                return true;
            }
            return false;
        }
///////////////////////////////////////////////////////////////////////////////
    private:
        ALCcontext*				context;
        ALCdevice*				device;

        SoundManager();
        ~SoundManager();
        
        SoundInfo* initWav(std::string const &resource);        
        SoundInfo* initOgg(std::string const &resource);
    public:
        
        void setVolume(float volume);
        
        float getVolume();
        
        /**
         * suffix .wav or .ogg are supported
         */
        SoundInfo*      createSoundInfo(std::string const &resource);  
        
        Sound*          createSound(SoundInfo *info);
        
        SoundPlayer*    createPlayer() ;        
        
        
        std::string     toString();
        
    };

    
}; // namespcace 

#endif // _COM_CELL_SOUND_MANAGER

