//
//  CSoundPlayer.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SOUND_PLAYER
#define _COM_CELL_SOUND_PLAYER

#import <OpenAL/al.h>
#import <OpenAL/alc.h>
#include <string>
#include "CSound.h"
#include "CSoundInfo.h"


namespace com_cell 
{
    class SoundPlayer
    {
    private:
        ALuint  m_source_id;
        
    public:

        SoundPlayer();
		
        ~SoundPlayer();
        
        bool    isEnable();
        
        /**
         * 播放
         * @param loop 播放无限循环
         */
        void 	play(bool loop);
        
        void	pause();
        
        void	resume();
        
        void 	stop();
        
        bool 	isPlaying();
        
        void 	destory() ;
        
        void	setVolume(float value);
        
        float	getVolume();
        
        void	setSound(Sound* sound);
        
        /**
         * 将声音数据缓冲到到播放器。该方法和setSound有类似的功能。一般用于播放流
         * @param sound
         */
        void    queue(Sound* sound);
        
        void    clearSound();
        
        void    clearQueued();
        
    protected:
        
    };    
    
}; // namespcace 

#endif // _COM_CELL_SOUND_PLAYER

