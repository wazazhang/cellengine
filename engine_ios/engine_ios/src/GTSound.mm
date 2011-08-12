/*
 *  GTSound.mm
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-17.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */

#define __SOUND_ENABLE = 1

#include "GTSound.h"


namespace gt 
{
	bool IS_SOUND_ENABLE = true;
	
	bool g_SoundEnable = true;
	
	Sound::Sound(const char *name, const char *ext)
	{
		if (IS_SOUND_ENABLE)
		{
			NSString *filePath = [[NSBundle mainBundle] pathForResource:[NSString stringWithUTF8String:name] ofType:[NSString stringWithUTF8String:ext]];
			
			NSURL *aFileURL = [NSURL fileURLWithPath:filePath isDirectory:NO];
			
			if (aFileURL != nil) 
			{
				SystemSoundID aSoundID;
				OSStatus error = AudioServicesCreateSystemSoundID((CFURLRef)aFileURL, &aSoundID);
				
				if (error == kAudioServicesNoError) { // success
					m_SoundID = aSoundID;
				} else {
					NSLog(@"Error %d loading sound at path: %@", error, filePath);
				}
			} else {
				NSLog(@"NSURL is nil for path: %@", filePath);
		}
		}
	}
	
	Sound::~Sound()
	{	
		if (IS_SOUND_ENABLE)
		{
			AudioServicesDisposeSystemSoundID(m_SoundID);		
		}
	}
	
	void Sound::play()
	{
		if(IS_SOUND_ENABLE)
		{
			if(g_SoundEnable)
			{
				AudioServicesPlaySystemSound(m_SoundID);
			}
		}
	}


	
	/*
	//--------------------------------------------------------------------------------------
	static const int kNumberBuffers = 3;
	// Create a data structure to manage information needed by the audio queue
	struct myAQStruct {
		AudioFileID                     mAudioFile;
		CAStreamBasicDescription        mDataFormat;
		AudioQueueRef                   mQueue;
		AudioQueueBufferRef             mBuffers[kNumberBuffers];
		SInt64                          mCurrentPacket;
		UInt32                          mNumPacketsToRead;
		AudioStreamPacketDescription    *mPacketDescs;
		bool                            mDone;
	};
	// Define a playback audio queue callback function
	static void AQTestBufferCallback(
									 void                   *inUserData,
									 AudioQueueRef          inAQ,
									 AudioQueueBufferRef    inCompleteAQBuffer
	) {
		myAQStruct *myInfo = (myAQStruct *)inUserData;
		if (myInfo->mDone) return;
		UInt32 numBytes;
		UInt32 nPackets = myInfo->mNumPacketsToRead;
		
		AudioFileReadPackets (
							  myInfo->mAudioFile,
							  false,
							  &numBytes,
							  myInfo->mPacketDescs,
							  myInfo->mCurrentPacket,
							  &nPackets,
							  inCompleteAQBuffer->mAudioData
							  );
		if (nPackets > 0) {
			inCompleteAQBuffer->mAudioDataByteSize = numBytes;
			AudioQueueEnqueueBuffer (
									 inAQ,
									 inCompleteAQBuffer,
									 (myInfo->mPacketDescs ? nPackets : 0),
									 myInfo->mPacketDescs
									 );
			myInfo->mCurrentPacket += nPackets;
		} else {
			AudioQueueStop (
							myInfo->mQueue,
							false
							);
			myInfo->mDone = true;
		}
	}
*/
	
	
	
	BGM::BGM(const char *name, const char *ext)
	{
		
	}
	
	BGM::~BGM()
	{
	}
	
	void BGM::play()
	{
	}
	
	
	
	
	
}; // namespace gt
