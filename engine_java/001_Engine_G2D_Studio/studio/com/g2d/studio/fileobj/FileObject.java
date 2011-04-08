package com.g2d.studio.fileobj;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.g2d.awt.util.Tools;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DListItem;

abstract public class FileObject implements G2DListItem
{
	final private String 		name;

	final private File			file;
	String[]					path = new String[]{};
	
	FileObject(String name, File file) {
		this.name = name;
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String getListName() {
		return name;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}

