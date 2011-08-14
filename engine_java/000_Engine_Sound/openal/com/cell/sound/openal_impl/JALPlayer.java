package com.cell.sound.openal_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import com.cell.CUtil;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.IStreamPlayer;

import net.java.games.joal.AL;

public class JALPlayer implements IPlayer, IStreamPlayer
{
	private final AL			al;
	private int[]				source;
	private int 				size = 0;	
	
	JALPlayer(AL al) throws Exception
	{
		this.al = al;
		JALSoundManager.checkError(al);
		{
			int[] source = new int[1];
			
			// Bind buffer with a source.
			al.alGenSources(1, source, 0);
			if (JALSoundManager.checkError(al)) {
				throw new Exception("Error generating OpenAL source !");
			} else {
				this.source = source;
			}

//			JALSoundManager.logger.log(Level.FINE,
//			"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" +
//			"S Create sound : " + source[0] + " : " + this + "\n"+
//			"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

			
			float[] zero_v = { 0.0f, 0.0f, 0.0f };
			
			al.alSourcefv(source[0], AL.AL_POSITION,	zero_v, 0);
			al.alSourcefv(source[0], AL.AL_VELOCITY,	zero_v, 0);
			al.alSourcefv(source[0], AL.AL_DIRECTION,	zero_v, 0);
			
			al.alSourcef(source[0], AL.AL_PITCH,			1.0f);
			al.alSourcef(source[0], AL.AL_GAIN,				1.0f);
			al.alSourcef(source[0], AL.AL_ROLLOFF_FACTOR, 	0.0f);

			al.alSourcei(source[0], AL.AL_SOURCE_RELATIVE, 	AL.AL_TRUE);
			al.alSourcei(source[0], AL.AL_LOOPING,			0);
			
			JALSoundManager.checkError(al);
		}
	}


	@Override
	synchronized public void setVolume(float value) {
		if (source != null) {
			value = Math.min(1f, value);
			value = Math.max(0f, value);
			al.alSourcef(source[0], AL.AL_GAIN, value);
			JALSoundManager.checkError(al);
		}
	}
	
	@Override
	public float getVolume() {
		if (source != null) {
			float[] ret = new float[1];
			al.alGetSourcef(source[0], AL.AL_GAIN, ret, 0);
			JALSoundManager.checkError(al);
			return ret[0];
		} else {
			return 0;
		}
	}
	
	@Override
	synchronized public void play(boolean loop) {
		if (source != null) {
			al.alSourcei(source[0], AL.AL_LOOPING,	loop?1:0);
			if (JALSoundManager.checkError(al)) {
			} else {
				al.alSourcePlay(source[0]);
				JALSoundManager.checkError(al);
			}
		}
	}
	
	synchronized public void pause() {
		if (source != null) {
			al.alSourcePause(source[0]);
			JALSoundManager.checkError(al);
		}
	}
	
	@Override
	synchronized public void resume() {
		if (source != null) {
			al.alSourcePlay(source[0]);
			JALSoundManager.checkError(al);
		}
	}
	
	synchronized public void stop() {
		if (source != null) {
			al.alSourceStop(source[0]);
			JALSoundManager.checkError(al);
		}
	}

	@Override
	synchronized public boolean isPlaying() {
		if (source != null) {
			int state[] = new int[1];
			al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state, 0);
			if (JALSoundManager.checkError(al)) {
				return true;
			}
			return state[0] == AL.AL_PLAYING;
		}
		return true;
	}
	

//	// clean all processed sound
//	int[] processed = new int[1];
//	al.alGetSourcei(source[0], AL.AL_BUFFERS_PROCESSED, processed, 0);
//	JALSoundManager.checkError(al);
//	
//	// clean all queued sound
//	int[] queued = new int[1];
//	al.alGetSourcei(source[0], AL.AL_BUFFERS_QUEUED, queued, 0);
//	JALSoundManager.checkError(al);

	protected void clearQueued(int count) 
	{
		int[] buffers = new int[count];
			al.alSourceUnqueueBuffers(source[0], count, buffers, 0);
			JALSoundManager.logger.info("openal clearQueued : " + CUtil.arrayToString(buffers));
			int error_code = al.alGetError();
			if (error_code != AL.AL_NO_ERROR) {
				switch (error_code) {
				case AL.AL_INVALID_VALUE:
					JALSoundManager.logger.warning(
							"At least one buffer can not be unqueued because it has not been processed yet.");
					break;
				case AL.AL_INVALID_NAME:
					JALSoundManager.logger.warning(
							"The specified source name is not valid.");
					break;
				case AL.AL_INVALID_OPERATION:
					JALSoundManager.logger.warning(
							"There is no current context.");
					break;
				default:
					JALSoundManager.logger.warning(
							"OpenAL error code : 0x" + Integer.toString(error_code, 16));
				}
			}
		
	}
	
	protected void clearAllSound() 
	{
		if (source != null) {
			// stop all sound
			al.alSourceStop(source[0]);
			JALSoundManager.checkError(al);
			
			// clean all sound
			al.alSourceRewind(source[0]);
			JALSoundManager.checkError(al);

			// clean all processed sound
			int[] processed = new int[1];
			al.alGetSourcei(source[0], AL.AL_BUFFERS_PROCESSED, processed, 0);
			JALSoundManager.checkError(al);
			clearQueued(processed[0]);
			
			// clean all queued sound
//			int[] queued = new int[1];
//			al.alGetSourcei(source[0], AL.AL_BUFFERS_QUEUED, queued, 0);
//			JALSoundManager.checkError(al);
//			clearQueued(queued[0]);
			
			al.alSourcei(source[0], AL.AL_BUFFER, 0);
			if (JALSoundManager.checkError(al)) {}

		}
	}
	
	@Override
	synchronized public void dispose()
	{
		if (source != null) {
			try {
//				clearAllSound();
				al.alDeleteSources(1, source, 0);
				JALSoundManager.checkError(al);
			} finally {
//				JALSoundManager.logger.log(Level.FINE,
//				"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" +
//				"S Dispose sound : " + source[0] + " : " + this + "\n"+
//				"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
				this.source = null;
			}
		}
	}
	
	
	@Override
	synchronized public void setSound(ISound sound)
	{
		if (source != null) {
			if (sound instanceof JALSound) {
				JALSound al_sound = (JALSound)sound;
				if (al_sound.isEnable()) {
					al.alSourcei(source[0], AL.AL_BUFFER, al_sound.getBufferID());
					JALSoundManager.checkError(al);
				}
			}
		}
	}

	@Override
	synchronized public void queue(ISound sound) 
	{
		if (source != null) {
			if (sound instanceof JALSound) {
				JALSound al_sound = (JALSound) sound;
				if (al_sound.isEnable()) {
					al.alSourceQueueBuffers(source[0], 1, new int[] { al_sound.getBufferID() }, 0);
					if (JALSoundManager.checkError(al)) {
					} else {
						size += al_sound.getSize();
					}
//					System.out.println("  queue" +
//							" : " + al_sound.getBufferID() + 
//							" : " + al_sound + 
//							" : total="+CUtil.getBytesSizeString(size));
					
				}
			}
		}
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
