package com.cell.sound;

import java.nio.ByteBuffer;

public abstract class SoundInfo
{
	abstract public String		getResource();
	
	/** stereo mono */
	abstract public int			getChannels();
	
	/** 16bit 8bit */
	abstract public int			getBitLength();
	
	/** 44100hz 22050hz */
	abstract public int 		getFrameRate();
	
	/** file comment */
	abstract public String		getComment();
	
	/** 
	 * PCM raw data stream <br>
	 * 读取此缓冲区当前位置的字节流，然后该位置递增。 
	 * */
	abstract public ByteBuffer	getData();

	/**
	 * if the raw stream has remain data <br>
	 * 该缓冲区是否有新数据。
	 */
	abstract public boolean		hasData();
	
	/**
	 * reset the raw data stream to head<br>
	 * 重设读取的数据流到最开始。
	 */
	abstract public void		resetData();
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SoundInfo : " + getResource() + "\n");
		sb.append("\tchannels : " + getChannels() + "\n");
		sb.append("\tbit_length : " + getBitLength() + "\n");
		sb.append("\tframe_rate : " + getFrameRate() + "\n");
		sb.append(getComment());
		return sb.toString();
	}

}
