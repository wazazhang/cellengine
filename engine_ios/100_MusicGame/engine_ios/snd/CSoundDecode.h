//
//  CSoundDecode.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_SOUND_DECODE
#define _COM_CELL_SOUND_DECODE

#include <string>
#include "CSoundInfo.h"

namespace com_cell 
{
    extern SoundInfo* createWavSound(std::string const &resource);
    
    extern SoundInfo* createOggSound(std::string const &resource);
    
}; // namespcace 

#endif // _COM_CELL_SOUND_DECODE