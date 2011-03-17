package com.g2d.studio.old.actor;

import javax.swing.JFrame;

public class FormActorsPan extends JFrame
{
	static FormActorsPan instance;
	
	public static void showFormActorsPan(FormActorViewerGroup group){
		if (instance==null) {
			instance = new FormActorsPan(group);
		}
		instance.setVisible(true);
	}
	
	public FormActorsPan(FormActorViewerGroup group)
	{
		this.setLocation(group.studio.getX()+group.studio.getWidth(), group.studio.getY());
		this.setSize(32*5+8, 600);
		this.setAlwaysOnTop(true);
		this.add(new ResActorBox(group));
	}

	
}
