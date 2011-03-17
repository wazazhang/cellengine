package com.cell.sound.openal_impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import net.java.games.joal.AL;
import net.java.games.joal.util.ALut;

import com.cell.sound.SoundInfo;

public class JALWavSoundInfo extends SoundInfo
{
	private String		resource;
	private ByteBuffer	data;
	private int			size;
	private int			frame_rate;
	private int			channels;
	private int			bit_length;
	private String		comment;
	
	
	public JALWavSoundInfo(String resource, InputStream input) throws IOException
	{
		int[] 			out_format	= new int[1];
		int[] 			out_size  	= new int[1];
		ByteBuffer[] 	out_data	= new ByteBuffer[1];
		int[] 			out_freq	= new int[1];
		int[] 			out_loop	= new int[1];
		// Load wav data into a buffer.
		ALut.alutLoadWAVFile(input, out_format, out_data, out_size, out_freq, out_loop);
		if (out_data[0] == null) {
			throw new IOException("Error loading WAV file !");
		}
		this.resource	= resource;
		this.data 		= out_data[0];
		this.size 		= out_size[0];
		this.frame_rate = out_freq[0];
		this.resource	= resource;
		this.comment	= "ALut.alutLoadWAVFile(input, out_format, out_data, out_size, out_freq, out_loop);";
		
		switch(out_format[0])
		{
		case AL.AL_FORMAT_MONO16:
		case AL.AL_FORMAT_STEREO16: this.bit_length = 16;
			break;
		case AL.AL_FORMAT_MONO8:
		case AL.AL_FORMAT_STEREO8: this.bit_length = 8;
			break;
		}
		
		switch(out_format[0])
		{
		case AL.AL_FORMAT_MONO8:
		case AL.AL_FORMAT_MONO16: this.channels = 1;
			break;
		case AL.AL_FORMAT_STEREO16:
		case AL.AL_FORMAT_STEREO8: this.channels = 2;
			break;
		}
	}
	
	@Override
	public int getBitLength() {
		return bit_length;
	}

	@Override
	public int getChannels() {
		return channels;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	synchronized public ByteBuffer getData() {
		return data;
	}

	@Override
	public int getFrameRate() {
		return frame_rate;
	}

	@Override
	public String getResource() {
		return resource;
	}
	
	@Override
	synchronized public boolean hasData() {
		return data.hasRemaining();
	}
	
	@Override
	synchronized public void resetData() {
		data.rewind();
	}
}
