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
#include "CSoundInfo.h"

namespace com_cell 
{
    class Sound
    {
    private:
        
        SoundInfo*  m_pData;
        
    public:
        
        Sound(SoundInfo* sound_info);
        
        ~Sound();
        
        int         getBufferID() ;        
        
        bool        isEnable() ;        
        
        void        dispose() ;        

        SoundInfo*  getSoundInfo();        

        int         getSize() ;       
        
    };    
    
}; // namespcace 

#endif // _COM_CELL_SOUND

