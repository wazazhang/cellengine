package com.g2d.studio;

import com.g2d.awt.util.AbstractDialog;


@SuppressWarnings("serial")
public class VersionFrame extends AbstractDialog
{
	public VersionFrame() {
		super(Studio.getInstance());
		super.setSize(400, 300);
		super.setTitle(StudioConfig.TITLE + " version:" + Version.getFullVersion());
	}
}
