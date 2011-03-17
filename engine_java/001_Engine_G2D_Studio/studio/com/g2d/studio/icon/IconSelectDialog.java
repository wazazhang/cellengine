package com.g2d.studio.icon;

import java.awt.Window;

import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DListSelectDialog;

public class IconSelectDialog extends G2DListSelectDialog<IconFile>
{
	private static final long serialVersionUID = 1L;
	
	public IconSelectDialog(IconFile dv)
	{
		super(Studio.getInstance().getIconManager(), new IconList(), dv);
		super.setTitle("选择一个图标");
		
		this.setSize(1000, 800);
		this.setLocation(this.getParent().getLocation());
	}
	
	public IconSelectDialog(Window owner, IconFile dv) 
	{
		super(owner, new IconList(), dv);
		super.setTitle("选择一个图标");
		
		this.setSize(1000, 800);
		this.setLocation(this.getParent().getLocation());
	}
}
