package com.g2d.studio.sound;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.awt.util.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class SoundFile implements G2DListItem
{
	final static ImageIcon 		icon = Tools.createIcon(Res.icons_bar[3]);	
	
	final public String 		sound_file_name;
	
	SoundFile(String name) 
	{
		this.sound_file_name 	= name;
//		System.out.println("create a sound file : " + name);
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return icon;
	}
	
	@Override
	public String getListName() {
		return sound_file_name;
	}

	
	public StaticSoundPlayer createSoundPlayer()
	{
		String file = Studio.getInstance().root_sound_path.getPath() + "/" + sound_file_name + Config.SOUND_SUFFIX;
//		System.out.println(file);
		StaticSoundPlayer sound_player = new StaticSoundPlayer(file);
		return sound_player;
	}
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}
