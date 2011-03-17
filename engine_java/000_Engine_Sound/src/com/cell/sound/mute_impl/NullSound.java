package com.cell.sound.mute_impl;

import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;

public class NullSound implements ISound {
	@Override
	public SoundInfo getSoundInfo() {
		return null;
	}

	@Override
	public int getSize() {
		return 0;
	}

	public void dispose() {
	}
}
