package com.cell.sound.mute_impl;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;


public class NullPlayer implements IPlayer
{
	ISound			sound;
	
	@Override
	public void setSound(ISound sound) {
		this.sound = sound;
	}
	public void play(boolean looping) {}
	public void pause() {}
	public void resume() {}
	public void stop() {}
	public void setVolume(float value) {}
	public void dispose() {}
	public boolean isPlaying() {
		return false;
	}
	public float getVolume() {
		return 0;
	}
	
}
