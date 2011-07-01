package com.cell.bms;

import com.g2d.Image;
import com.g2d.BufferedImage;

import com.g2d.Tools;

public class IDefineImage implements IDefineNote
{
	BufferedImage buffer;
	
	public IDefineImage(BMSFile bms, String image) {
		try{
			String path = bms.bms_dir+"/"+image;
			buffer = Tools.readImage(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return buffer;
	}

	@Override
	public void dispose() {}
}
