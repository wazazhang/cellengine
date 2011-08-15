//
//  CSoundDecode.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-14.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include <iostream.h>
#include "CSoundDecode.h"
#include "CStaticSoundInfo.h"
#include "CStreamSoundInfo.h"

#import <AudioToolbox/AudioToolbox.h>
#import <AudioToolbox/ExtendedAudioFile.h>


namespace com_cell 
{
	//////////////////////////////////////////////////////////////////////////////////////
	SoundInfo* createStaticSound(char* const filepath)
	{		
		ALsizei		channels;
		ALsizei		bitlength;
		ALsizei		size;
		ALsizei		freq;
		void*		data	= NULL;
		
		NSBundle*	bundle	= [NSBundle mainBundle];		
		NSString*	path	= [bundle pathForResource:[NSString stringWithUTF8String:filepath] ofType:nil];
		// get some audio data from a wave file
		//			CFURLRef fileURL = (CFURLRef)[[NSURL fileURLWithPath: [bundle 
		//			    pathForResource:[NSString stringWithUTF8String:name.c_str()] 
		//			             ofType:[NSString stringWithUTF8String:ext.c_str()]]] retain];
		CFURLRef fileURL = (CFURLRef)[NSURL fileURLWithPath:path];
		
		if (fileURL)
		{	
			if (stringEndWidth(filepath, "caf")) {
				data = loadCafSound(fileURL, &size, &channels, &bitlength, &freq);
			}
			if (stringEndWidth(filepath, "wav")) {
				data = loadWavSound(fileURL, &size, &channels, &bitlength, &freq);
			}
			CFRelease(fileURL);
			if (data != NULL) {
				StaticSoundInfo* ret = new StaticSoundInfo(filepath, channels, bitlength, freq, size, data);
				cout << ret->toString() << endl;
				return ret;
			} else {
				NSLog(@"only .caf .wav support");
			}
		}
		else {
			NSLog(@"Could not find file!\n");
		}
		return NULL;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	void* loadWavSound(CFURLRef const &inFileURL, 
					   ALsizei*	outDataSize, 
					   ALsizei*	outChannels, 
					   ALsizei*	outBitlength, 
					   ALsizei*	outSampleRate)
	{
		OSStatus						err = noErr;	
		SInt64							theFileLengthInFrames = 0;
		AudioStreamBasicDescription		theFileFormat;
		UInt32							thePropertySize = sizeof(theFileFormat);
		ExtAudioFileRef					extRef = NULL;
		void*							theData = NULL;
		AudioStreamBasicDescription		theOutputFormat;
		
		// Open a file with ExtAudioFileOpen()
		err = ExtAudioFileOpenURL(inFileURL, &extRef);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileOpenURL FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Get the audio data format
		err = ExtAudioFileGetProperty(extRef, 
									  kExtAudioFileProperty_FileDataFormat, 
									  &thePropertySize, 
									  &theFileFormat);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileDataFormat) FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		if (theFileFormat.mChannelsPerFrame > 2)  { 
			printf("MyGetOpenALAudioData - Unsupported Format, channel count is greater than stereo\n"); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Set the client format to 16 bit signed integer (native-endian) data
		// Maintain the channel count and sample rate of the original source format
		theOutputFormat.mSampleRate			= theFileFormat.mSampleRate;
		theOutputFormat.mChannelsPerFrame	= theFileFormat.mChannelsPerFrame;		
		theOutputFormat.mFormatID			= kAudioFormatLinearPCM;
		theOutputFormat.mBitsPerChannel		= 16;
		theOutputFormat.mBytesPerPacket		= 2 * theOutputFormat.mChannelsPerFrame;
		theOutputFormat.mFramesPerPacket	= 1;		
		theOutputFormat.mBytesPerFrame		= 2 * theOutputFormat.mChannelsPerFrame;	
		theOutputFormat.mFormatFlags		= (kAudioFormatFlagsNativeEndian | 
											   kAudioFormatFlagIsPacked |
											   kAudioFormatFlagIsSignedInteger);
		
		// Set the desired client (output) data format
		err = ExtAudioFileSetProperty(extRef, 
									  kExtAudioFileProperty_ClientDataFormat, 
									  sizeof(theOutputFormat), 
									  &theOutputFormat);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileSetProperty(kExtAudioFileProperty_ClientDataFormat) FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Get the total frame count
		thePropertySize = sizeof(theFileLengthInFrames);
		err = ExtAudioFileGetProperty(extRef,
									  kExtAudioFileProperty_FileLengthFrames, 
									  &thePropertySize, 
									  &theFileLengthInFrames);
		if(err) {
			printf("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileLengthFrames) FAILED, Error = %ld\n", err);
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Read all the data into memory
		UInt32		dataSize = theFileLengthInFrames * theOutputFormat.mBytesPerFrame;;
		theData = malloc(dataSize);
		if (theData)
		{
			AudioBufferList		theDataBuffer;
			theDataBuffer.mNumberBuffers = 1;
			theDataBuffer.mBuffers[0].mDataByteSize		= dataSize;
			theDataBuffer.mBuffers[0].mNumberChannels	= theOutputFormat.mChannelsPerFrame;
			theDataBuffer.mBuffers[0].mData				= theData;
			
			// Read the data into an AudioBufferList
			err = ExtAudioFileRead(extRef, (UInt32*)&theFileLengthInFrames, &theDataBuffer);
			if(err == noErr)
			{
				// success
				*outDataSize	= (ALsizei)dataSize;
				*outSampleRate	= (ALsizei)theOutputFormat.mSampleRate;
				*outChannels	= (ALsizei)theOutputFormat.mChannelsPerFrame;
				*outBitlength	= (ALsizei)theOutputFormat.mBitsPerChannel;
			}
			else 
			{ 
				// failure
				free (theData);
				theData = NULL; // make sure to return NULL
				printf("MyGetOpenALAudioData: ExtAudioFileRead FAILED, Error = %ld\n", err); 
			}	
		}
		// Dispose the ExtAudioFileRef, it is no longer needed
		if (extRef) ExtAudioFileDispose(extRef);
		return theData;
	}
	
	
	
	void* loadCafSound(CFURLRef const &inFileURL, 
					   ALsizei*	outDataSize, 
					   ALsizei*	outChannels, 
					   ALsizei*	outBitlength, 
					   ALsizei*	outSampleRate)
	{
		OSStatus						err = noErr;	
		SInt64							theFileLengthInFrames = 0;
		AudioStreamBasicDescription		theFileFormat;
		UInt32							thePropertySize = sizeof(theFileFormat);
		ExtAudioFileRef					extRef = NULL;
		void*							theData = NULL;
		AudioStreamBasicDescription		theOutputFormat;
		
		// Open a file with ExtAudioFileOpen()
		err = ExtAudioFileOpenURL(inFileURL, &extRef);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileOpenURL FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Get the audio data format
		err = ExtAudioFileGetProperty(extRef, 
									  kExtAudioFileProperty_FileDataFormat, 
									  &thePropertySize, 
									  &theFileFormat);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileDataFormat) FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		if (theFileFormat.mChannelsPerFrame > 2)  { 
			printf("MyGetOpenALAudioData - Unsupported Format, channel count is greater than stereo\n"); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Set the client format to 16 bit signed integer (native-endian) data
		// Maintain the channel count and sample rate of the original source format
		theOutputFormat.mSampleRate			= theFileFormat.mSampleRate;
		theOutputFormat.mChannelsPerFrame	= theFileFormat.mChannelsPerFrame;		
		theOutputFormat.mFormatID			= kAudioFormatLinearPCM;
		theOutputFormat.mBitsPerChannel		= 16;
		theOutputFormat.mBytesPerPacket		= 2 * theOutputFormat.mChannelsPerFrame;
		theOutputFormat.mFramesPerPacket	= 1;		
		theOutputFormat.mBytesPerFrame		= 2 * theOutputFormat.mChannelsPerFrame;	
		theOutputFormat.mFormatFlags		= (kAudioFormatFlagsNativeEndian | 
											   kAudioFormatFlagIsPacked |
											   kAudioFormatFlagIsSignedInteger);
		
		// Set the desired client (output) data format
		err = ExtAudioFileSetProperty(extRef, 
									  kExtAudioFileProperty_ClientDataFormat, 
									  sizeof(theOutputFormat), 
									  &theOutputFormat);
		if(err) { 
			printf("MyGetOpenALAudioData: ExtAudioFileSetProperty(kExtAudioFileProperty_ClientDataFormat) FAILED, Error = %ld\n", err); 
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Get the total frame count
		thePropertySize = sizeof(theFileLengthInFrames);
		err = ExtAudioFileGetProperty(extRef, kExtAudioFileProperty_FileLengthFrames, &thePropertySize, &theFileLengthInFrames);
		if(err) {
			printf("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileLengthFrames) FAILED, Error = %ld\n", err);
			// Dispose the ExtAudioFileRef, it is no longer needed
			if (extRef) ExtAudioFileDispose(extRef);
			return theData;
		}
		
		// Read all the data into memory
		UInt32		dataSize = theFileLengthInFrames * theOutputFormat.mBytesPerFrame;;
		theData = malloc(dataSize);
		if (theData)
		{
			AudioBufferList		theDataBuffer;
			theDataBuffer.mNumberBuffers = 1;
			theDataBuffer.mBuffers[0].mDataByteSize		= dataSize;
			theDataBuffer.mBuffers[0].mNumberChannels	= theOutputFormat.mChannelsPerFrame;
			theDataBuffer.mBuffers[0].mData				= theData;
			
			// Read the data into an AudioBufferList
			err = ExtAudioFileRead(extRef, (UInt32*)&theFileLengthInFrames, &theDataBuffer);
			if(err == noErr)
			{
				// success
				*outDataSize	= (ALsizei)dataSize;
				*outSampleRate	= (ALsizei)theOutputFormat.mSampleRate;
				*outChannels	= (ALsizei)theOutputFormat.mChannelsPerFrame;
				*outBitlength	= (ALsizei)theOutputFormat.mBitsPerChannel;
			}
			else 
			{ 
				// failure
				free (theData);
				theData = NULL; // make sure to return NULL
				printf("MyGetOpenALAudioData: ExtAudioFileRead FAILED, Error = %ld\n", err); 
			}	
		}
		// Dispose the ExtAudioFileRef, it is no longer needed
		if (extRef) ExtAudioFileDispose(extRef);
		return theData;
	}
	
//  beat detect
//	{
//		
//		
//		ALfloat energy = 0;
//		ALfloat aEnergy = 0;
//		ALint beats = 0;
//		bool init = false;
//		ALfloat Ei[42];
//		ALfloat V = 0;
//		ALfloat C = 0;
//		
//		
//		ALshort *hold;
//		hold = new ALshort[[myDat length]/2];
//		
//		[myDat getBytes:hold length:[myDat length]];
//		
//		ALuint uiNumSamples;
//		uiNumSamples = [myDat length]/4;
//		
//		if(alDatal == NULL)
//			alDatal = (ALshort *) malloc(uiNumSamples*2);
//			if(alDatar == NULL)
//				alDatar = (ALshort *) malloc(uiNumSamples*2);
//				for (int i = 0; i < uiNumSamples; i++)
//				{
//					alDatal[i] = hold[i*2];
//					alDatar[i] = hold[i*2+1];
//				}
//		energy = 0;
//		for(int start = 0; start<(22050*10); start+=512){
//			for(int i = start; i<(start+512); i++){
//				energy+= ((alDatal[i]*alDatal[i]) + (alDatal[i]*alDatar[i]));
//				
//			}
//			aEnergy = 0;
//			for(int i = 41; i>=0; i--){
//				
//				if(i ==0){
//					Ei[0] = energy;
//				}
//				else {
//					Ei[i] = Ei[i-1];
//				}
//				if(start >= 21504){
//					aEnergy+=Ei[i];
//				}
//			}
//			aEnergy = aEnergy/43.f;
//			if (start >= 21504) {
//				for(int i = 0; i<42; i++){
//					V += (Ei[i]-aEnergy);
//				}
//				V = V/43.f;
//				C = (-0.0025714*V)+1.5142857;
//				init = true;
//				if(energy >(C*aEnergy)) beats++;
//			}
//			
//		}
//		
//	}
	
	
	
}; // namespcace 
