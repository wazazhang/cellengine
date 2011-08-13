//
//  CSound.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-13.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CSound.h"
#import <OpenAL/al.h>
#import <OpenAL/alc.h>


namespace com_cell 
{
    
    
	
    Sound::Sound(SoundInfo* sound_info)
	{
        m_pData = sound_info;
		
		if (m_pData != NULL) 
		{
			int format = AL_FORMAT_MONO16;
			if (m_pData->getBitLength() == 16) {
				if (m_pData->getChannels()==1) {
					format = AL_FORMAT_MONO16;
				} else {
					format = AL_FORMAT_STEREO16;
				}
			} else if (m_pData->getBitLength() == 8) {
				if (m_pData->getChannels()==1) {
					format = AL_FORMAT_MONO8;
				} else {
					format = AL_FORMAT_STEREO8;
				}
			}
            
			int[] buffer = new int[1];
			al.alGenBuffers(1, buffer, 0);
			if (JALSoundManager.checkError(al)) {
				throw new Exception("Error generating OpenAL buffers : " + toString());
			} else {
				this.buffer = buffer;
			}
			
			// variables to load into
			al.alBufferData(buffer[0], format, bytes, size, info.getFrameRate());
			// Do another error check and return.
			if (JALSoundManager.checkError(al)) {
				al.alDeleteBuffers(1, buffer, 0);
				if (JALSoundManager.checkError(al)) {}
				this.buffer = null;
				throw new Exception("Error generating OpenAL buffers : " + toString());
			}
		}
	}
	
	synchronized int getBufferID() {
		if (buffer != null) {
			return buffer[0];
		}
		return 0;
	}
	synchronized boolean isEnable() {
		return buffer != null;
	}
	
	
	synchronized public void dispose() 
	{
		if (buffer != null) {
			al.alDeleteBuffers(1, buffer, 0);
			if (JALSoundManager.checkError(al)) {
				System.err.println("buffer id : " + buffer[0]);
			}
			buffer = null;
            //			System.out.println("unbind sound !");
		}
	}
    
	@Override
	public SoundInfo getSoundInfo() {
		return info;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " : " + CUtil.getBytesSizeString(size);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}

    
    
}; // namespcace 
