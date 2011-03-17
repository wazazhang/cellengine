package com.cell.sound.mute_impl;

import java.nio.ByteBuffer;

import com.cell.sound.SoundInfo;

class NullSoundInfo extends SoundInfo {

	@Override
	public int getBitLength() {
		return 0;
	}

	@Override
	public int getChannels() {
		return 0;
	}

	@Override
	public String getComment() {
		return null;
	}

	@Override
	public int getFrameRate() {
		return 0;
	}

	@Override
	public String getResource() {
		return null;
	}

	@Override
	public ByteBuffer getData() {
		return null;
	}
	
	@Override
	public boolean hasData() {
		return false;
	}
	
	@Override
	public void resetData() {}

}
