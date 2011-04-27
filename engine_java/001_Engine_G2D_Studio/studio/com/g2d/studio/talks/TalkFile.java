package com.g2d.studio.talks;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JList;

import com.g2d.studio.Config;
import com.g2d.studio.fileobj.FileObject;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DListItem;

public class TalkFile extends FileObject
{
	TalkFile(File file) {
		super(file.getName().substring(0, file.getName().length() - Config.TALK_SUFFIX.length()), file);
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		return new ImageIcon(Res.img_talk);
	}
	
	@Override
	public String getSaveListArgs() {
		return "";
	}
	
	
}
