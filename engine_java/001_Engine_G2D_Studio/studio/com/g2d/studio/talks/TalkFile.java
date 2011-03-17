package com.g2d.studio.talks;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class TalkFile implements G2DListItem
{
	final public String 		talk_file_name;
	final public File			talk_file;
		
	
	TalkFile(String name, File talk_file) {
		this.talk_file_name = name;
		this.talk_file		= talk_file;
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return new ImageIcon(Res.img_talk);
	}
	
	@Override
	public String getListName() {
		return talk_file_name;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}
