package com.cell.bms;

import java.util.ArrayList;

import com.cell.sound.IPlayer;
import com.cell.sound.SoundManager;

public class PlayerManager 
{
	static private ArrayList<IPlayer> players = new ArrayList<IPlayer>(255);
	
	static IPlayer getFreePlayer() {
		synchronized (players) {
			for (IPlayer player : players) {
				if (!player.isPlaying()) {
					return player;
				}
			}
			IPlayer player = SoundManager.getSoundManager().createPlayer();
			if (player != null) {
				players.add(player);
			}
			return player;
		}
	}
}
