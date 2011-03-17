package com.cell.sound.util;

import com.cell.exception.NotImplementedException;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class StaticSoundPlayer implements IPlayer
{
	final SoundManager			manager; 
	final String				resource;
	
	final private SoundInfo		info;
	final private ISound		sound;
	final private IPlayer		player;
	
	public StaticSoundPlayer(String resource) 
	{
		this.manager	= SoundManager.getSoundManager();
		this.resource	= resource;
		
		this.info		= manager.createSoundInfo(resource);
		this.sound		= manager.createSound(info);
		this.player		= manager.createPlayer();
		this.player.setSound(sound);
	}
	
	@Override
	public void dispose() {
		this.player.dispose();
		this.sound.dispose();
	}

	@Override
	public void setVolume(float value) {
		if (this.player!=null) {
			this.player.setVolume(value);
		}
	}

	@Override
	public float getVolume() {
		if (this.player!=null) {
			return this.player.getVolume();
		}
		return 0;
	}
	
	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}
	
	@Override
	public void pause() {
		player.pause();
	}
	@Override
	public void resume() {
		player.resume();
	}
	@Override
	public void play(boolean looping) {
		player.play(looping);
	}
	
	@Override
	public void stop() {
		player.stop();
	}
	
	@Override
	public void setSound(ISound sound) {
		throw new NotImplementedException();
	}
	
	
}
