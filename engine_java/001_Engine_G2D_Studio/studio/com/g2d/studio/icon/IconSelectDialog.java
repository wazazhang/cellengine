package com.g2d.studio.icon;

import java.awt.Window;

import com.g2d.studio.Studio;
import com.g2d.studio.fileobj.FileObjectSelectDialog;
import com.g2d.studio.swing.G2DListSelectDialog;

public class IconSelectDialog extends FileObjectSelectDialog<IconFile>
{
	private static final long serialVersionUID = 1L;
	
	public IconSelectDialog(IconFile dv)
	{
		super(Studio.getInstance().getIconManager(), Studio.getInstance().getIconManager().getList(), dv);
		super.setTitle("选择一个图标");
		
		this.setSize(1000, 800);
		this.setLocation(this.getParent().getLocation());
	}
	
	public IconSelectDialog(Window owner, IconFile dv) 
	{
		super(owner, Studio.getInstance().getIconManager().getList(), dv);
		super.setTitle("选择一个图标");
		
		this.setSize(1000, 800);
		this.setLocation(this.getParent().getLocation());
	}
}
