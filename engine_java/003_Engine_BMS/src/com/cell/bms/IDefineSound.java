package com.cell.bms;

import java.util.ArrayList;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class IDefineSound implements IDefineNote
{
	SoundInfo	sound_info;
	ISound 		sound;
		
	public IDefineSound(BMSFile bms, String name) {
		this.sound_info	= SoundManager.getSoundManager().createSoundInfo(bms.bms_dir + "/" + name);
		this.sound 		= SoundManager.getSoundManager().createSound(sound_info);
	}
	
	synchronized
	public void play(){
		IPlayer player = SoundManager.getSoundManager().createPlayer();
		player.setSound(sound);
		player.play(false);
	}
	
	@Override
	public void dispose() {
		sound.dispose();
	}
	
}
