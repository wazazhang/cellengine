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
#import <OpenAL/al.h>
#import <OpenAL/alc.h>


namespace com_cell 
{
	extern SoundInfo* createStaticSound(char const *filepath);
	
}; // namespcace 

#endif // _COM_CELL_SOUND_DECODE