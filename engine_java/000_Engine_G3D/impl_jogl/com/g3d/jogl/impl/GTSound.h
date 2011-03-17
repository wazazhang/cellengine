/*
 *  GTSound.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-17.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#ifndef _GAMETILER_SOUND
#define _GAMETILER_SOUND

#include <UIKit/UIKit.h>

#include <AudioToolbox/AudioToolbox.h>
#include <AudioToolbox/AudioQueue.h>
#include <AudioToolbox/AudioFile.h>

#include <CoreAudio/CoreAudioTypes.h>



namespace gt
{
	extern bool IS_SOUND_ENABLE;

	extern bool g_SoundEnable;
	//-------------------------------------------------------------------------------------------
	
	class Sound
	{
		SystemSoundID m_SoundID;
		
	public:
		
		Sound(const char *name, const char *ext);
		
		~Sound();
		
		void play();		
		
	}; // class Sound
	
	
	
	//-------------------------------------------------------------------------------------------
		
	
	class BGM
	{
		AudioQueueRef m_AudioQueue;
		
		
	public:
		
		
		BGM(const char *name, const char *ext);
		
		~BGM();
		
		void play();
		
	}; // class BGM
	
	
}; // namespace gt

#endif // #define _GAMETILER_SOUND
