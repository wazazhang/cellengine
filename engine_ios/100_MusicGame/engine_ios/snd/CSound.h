//
//  CSound.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SOUND
#define _COM_CELL_SOUND

#include <string>
#import <OpenAL/al.h>
#import <OpenAL/alc.h>
#include "CSoundInfo.h"

namespace com_cell 
{
    class Sound
    {
		friend class SoundManager;
		
    private:
        ALuint		m_buffer_id;
        
		int			m_format;
		int			m_size;
		int			m_framerate;
		
        Sound(SoundInfo *info);
		
    public:
        
        ~Sound();
        
        ALuint      getBufferID() ;        
        
        bool        isEnable() ;        

		int         getSize() ;    
		int         getFormat() ;   
		int         getFrameRate() ;      
        
    };    

}; // namespcace 

#endif // _COM_CELL_SOUND

