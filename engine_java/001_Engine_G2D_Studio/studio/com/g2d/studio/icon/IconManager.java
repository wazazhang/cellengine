package com.g2d.studio.icon;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormList;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;

public class IconManager extends ManagerFormList<IconFile>
{
	private static final long serialVersionUID = 1L;
		
	public IconManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "图标管理器", Res.icons_bar[6], 
				studio.root_icon_path,
				studio.project_save_path.getChildFile("icons/icon.list"));
	}
	
	public Vector<IconFile> getIcons() {
		return super.getNodes();
	}
	
	public IconFile getIcon(String icon_name) {
		return super.getNode(icon_name);
	}
	
	@Override
	protected G2DList<IconFile> createList(Vector<IconFile> files) {
		return new IconList(files);
	}
	@Override
	protected String asNodeName(File file) {
		return file.getName().substring(0, file.getName().length() - Config.ICON_SUFFIX.length());
	}
	@Override
	protected IconFile createNode(File file) {
		if (file.getName().endsWith(Config.ICON_SUFFIX)) {
			IconFile icon = new IconFile(asNodeName(file), file);
			icon.getImage();
			return icon;
		}
		return null;
	}
	
	@Override
	protected String getSaveListName(IconFile icon) {
		return icon.getListName()+","+icon.getImage().getWidth()+","+icon.getImage().getHeight();
	}
	
}
