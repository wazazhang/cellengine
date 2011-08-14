//
//  CStaticSoundInfo.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-14.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#ifndef _COM_CELL_STATIC_SOUND_INFO
#define _COM_CELL_STATIC_SOUND_INFO

#include <string>
#include "CUtil.h"

namespace com_cell 
{
    class StaticSoundInfo : public SoundInfo
    {
	private:
		int			channels;
		int			bitlength;
		ALsizei		size;
		ALsizei		freq;
		ALenum		format;
		void*		data;
		std::string file_path;
		
    public:
        
		inline StaticSoundInfo(std::string const &filepath)
		{
			file_path			= filepath;
			data				= NULL;
			ALenum		error	= AL_NO_ERROR;
			NSBundle*	bundle	= [NSBundle mainBundle];		
			NSString*	path	= [bundle pathForResource:@"bgm.wav" ofType:nil];
			// get some audio data from a wave file
//			CFURLRef fileURL = (CFURLRef)[[NSURL fileURLWithPath: [bundle 
//			    pathForResource:[NSString stringWithUTF8String:name.c_str()] 
//			             ofType:[NSString stringWithUTF8String:ext.c_str()]]] retain];
			CFURLRef fileURL = (CFURLRef)[NSURL fileURLWithPath:path];
			
			if (fileURL)
			{	
				data = MyGetOpenALAudioData(fileURL, &size, &format, &freq);
				CFRelease(fileURL);
				
				if((error = alGetError()) != AL_NO_ERROR) {
					NSLog(@"error loading sound: %x\n", error);
					data = NULL;
				} else {
					switch (format) {
						case AL_FORMAT_STEREO16:
							channels = 2;
							bitlength = 16;
							break;
						case AL_FORMAT_STEREO8:
							channels = 2;
							bitlength = 8;
							break;
						case AL_FORMAT_MONO16:
							channels = 1;
							bitlength = 16;
							break;
						case AL_FORMAT_MONO8:
							channels = 1;
							bitlength = 8;
							break;
						default:
							channels = 0;
							bitlength = 0;
							break;
					}
				}
				//// use the static buffer data API
				//alBufferDataStaticProc(buffer, format, data, size, freq);
				//
				//if((error = alGetError()) != AL_NO_ERROR) {
				//	NSLog(@"error attaching audio to buffer: %x\n", error);
				//}		
			}
			else {
				NSLog(@"Could not find file!\n");
			}
		}
		
		inline ~StaticSoundInfo()
		{
			if (data != NULL) {
				free (data);
			}
		}
		
		inline virtual std::string getFilePath() 
		{
			return file_path;
		}
        
		inline virtual int getChannels()
		{
			return channels;
		}
        
        inline virtual int getBitLength()
		{
			return bitlength;
		}
        
        inline virtual int getFrameRate() 
		{
			return freq;
		}
        
        inline virtual std::string getComment()
		{
			return "";
		}
        
        inline virtual void* getData()
		{
			return data;
		}
        
        inline virtual int getDataSize()
		{
			return size;
		}
        
		// not impl
        inline virtual bool hasData()
		{
			return false;
		}
        
		// not impl
        inline virtual void resetData()
		{
			
		}
        
        
    };
    
    
}; // namespcace 

#endif // _COM_CELL_STATIC_SOUND_INFO