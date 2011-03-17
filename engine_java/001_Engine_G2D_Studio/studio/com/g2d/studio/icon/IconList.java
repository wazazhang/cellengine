package com.g2d.studio.icon;

import java.util.Vector;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;

public class IconList extends G2DList<IconFile>
{
	private static final long serialVersionUID = 1L;

	IconList(Vector<IconFile> icons) {
		super(icons);
	}
	
	IconList() {
		super(Studio.getInstance().getIconManager().getIcons());
	}
}