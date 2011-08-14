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
    public:
        static SoundManager* getInstance() ;
		
        static void destoryInstance() ;
		
        static bool checkError() ;
		
    private:
        static SoundManager* g_instance;
		
		///////////////////////////////////////////////////////////////////////////////
    private:
        ALCcontext*				context;
        ALCdevice*				device;

        SoundManager();
        ~SoundManager();
        
    public:
        
        void setVolume(float volume);
        
        float getVolume();
        
        /**
         * suffix .wav or .ogg are supported
         */
        SoundInfo*      createSoundInfo(std::string const &filepath);  
		
		/**
         * suffix .wav or .ogg are supported
         */
        SoundInfo*      createStreamSoundInfo(std::string const &filepath); 
        
        Sound*          createSound(SoundInfo *info);
        
        SoundPlayer*    createPlayer() ;        
        
        
        std::string     toString();
        
    };

    
}; // namespcace 

#endif // _COM_CELL_SOUND_MANAGER

