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
		IPlayer player = PlayerManager.getFreePlayer();
		if (player != null) {
			player.setSound(sound);
			player.play(false);
		} else {
			System.err.println("no player resource : " + sound_info.getResource());
		}
	}
	
	@Override
	public void dispose() {
		sound.dispose();
	}
	
}
