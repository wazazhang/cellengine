package com.g2d.display;

import java.awt.Graphics2D;
import java.awt.Image;

import com.g2d.Version;

public class DisplayImage extends DisplayObject 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient Image image ;
	
	public DisplayImage(Image image)
	{
		this.image = image;
		this.local_bounds.setBounds(0, 0, image.getWidth(this), image.getHeight(this));
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {}

	@Override
	public void removed(DisplayObjectContainer parent) {}

	@Override
	public void render(Graphics2D g) {
		if (image!=null)
		g.drawImage(image, local_bounds.x, local_bounds.y, local_bounds.width, local_bounds.height, this);
	}

	@Override
	public void update() {}
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}

}
