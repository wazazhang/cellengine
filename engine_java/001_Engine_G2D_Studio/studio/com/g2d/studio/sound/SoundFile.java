package com.g2d.studio.sound;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.cell.sound.util.StaticSoundPlayer;
import com.g2d.awt.util.Tools;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.Studio;
import com.g2d.studio.fileobj.FileObject;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class SoundFile extends FileObject
{
	final static ImageIcon 		icon = Tools.createIcon(Res.icons_bar[3]);	
	
	SoundFile(File file) 
	{
		super(file.getName().substring(0, file.getName().length() - StudioConfig.SOUND_SUFFIX.length()), file);
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return icon;
	}
	
	@Override
	public String getSaveListArgs() {
		return "";
	}
	
	public StaticSoundPlayer createSoundPlayer()
	{
		String file = Studio.getInstance().root_sound_path.getPath() + "/" + getName() + StudioConfig.SOUND_SUFFIX;
		System.out.println("play sound : " + file);
		StaticSoundPlayer sound_player = new StaticSoundPlayer(file);
		return sound_player;
	}

}
