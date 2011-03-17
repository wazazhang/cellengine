package com.g2d.studio.icon;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JList;


import com.g2d.awt.util.*;

import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DListItem;

public class IconFile implements G2DListItem
{
	final public String 		icon_file_name;
	final public File			image_file;
	
	
	transient private BufferedImage	image;
	transient private ImageIcon 	icon;	
	
	
	IconFile(String name, File image_file) {
		this.icon_file_name = name;
		this.image_file		= image_file;
	}
	
	public BufferedImage getImage() {
		if (image == null) {
			image = Tools.readImage(image_file.getPath());
		}
		return image;
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		if (icon == null) {
			icon = new ImageIcon(getImage());
		}
		return icon;
	}
	
	@Override
	public String getListName() {
		return icon_file_name;
	}
	
	@Override
	public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return null;
	}
}
