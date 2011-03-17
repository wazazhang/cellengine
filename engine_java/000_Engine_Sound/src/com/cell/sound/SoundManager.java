package com.cell.sound;

import java.io.InputStream;
import java.util.HashMap;

public abstract class SoundManager 
{
//	-----------------------------------------------------------------------------------------------------------
	
	private static SoundManager instance;

	public static void setSoundManager(SoundManager ins) {
		instance = ins;
	}
	
	public static SoundManager getSoundManager() {
		return instance;
	}

//	-----------------------------------------------------------------------------------------------------------
	
//	-----------------------------------------------------------------------------------------------------------
	
//	-----------------------------------------------------------------------------------------------------------
	
	abstract public void		setVolume(float volume);
	abstract public float		getVolume();
	
	abstract public SoundInfo	createSoundInfo(String resource, InputStream is);
	
	abstract public SoundInfo	createSoundInfo(String resource);
	
	abstract public ISound		createSound(SoundInfo info);
	
	abstract public IPlayer		createPlayer();
}
