package com.cell.sound.util;

import com.cell.exception.NotImplementedException;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class SoundPlayer implements IPlayer, Runnable
{
	final Thread		stream_thread;
	final SoundManager	manager; 
	final String		resource;
	
	private SoundInfo	info;
	private ISound		sound;
	private IPlayer		player;
	private boolean		looping;
	
	public SoundPlayer(String resource) 
	{
		this.manager	= SoundManager.getSoundManager();
		this.resource	= resource;
		this.stream_thread = new Thread(this);
	}
	
	@Override
	public void run() {
		boolean play = false;
		synchronized(this) {
			if (this.info == null) {
				this.info	= manager.createSoundInfo(resource);
				this.sound	= manager.createSound(info);
				this.player	= manager.createPlayer();
				this.player.setSound(sound);
				play = true;
			}
		}
		if (play) {
			this.player.play(looping);
		}
	}
	
	@Override
	synchronized public void dispose() {
		if (this.sound!=null) {
			this.sound.dispose();
		}
		if (this.player!=null) {
			this.player.dispose();
		}
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
	synchronized public boolean isPlaying() {
		if (this.player!=null) {
			return player.isPlaying();
		}
		return false;
	}
	
	@Override
	synchronized public void pause() {
		if (this.player!=null) {
			player.pause();
		}
	}
	@Override
	public void resume() {
		if (this.player!=null) {
			player.resume();
		}
	}
	@Override
	synchronized public void play(boolean looping) {
		this.looping = looping;
		if (this.info == null) {
			stream_thread.start();
		}
		else if (this.player!=null) {
			player.play(looping);
		}
	}
	
	synchronized public void playNow(boolean looping) {
		this.looping = looping;
		if (this.info == null) {
			stream_thread.run();
		}
		else if (this.player!=null) {
			player.play(looping);
		}
	}

	
	@Override
	synchronized public void stop() {
		if (this.player!=null) {
			player.stop();
		}
	}
	
	@Override
	synchronized public void setSound(ISound sound) {
		throw new NotImplementedException();
	}
	
	
}
