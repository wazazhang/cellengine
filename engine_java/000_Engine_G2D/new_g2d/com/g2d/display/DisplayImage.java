package com.g2d.display;

import com.g2d.Graphics2D;
import com.g2d.Image;



public class DisplayImage extends DisplayObject 
{
	transient Image image ;
	
	public DisplayImage(Image image)
	{
		this.image = image;
		this.local_bounds.setBounds(0, 0, image.getWidth(), image.getHeight());
	}
	
	@Override
	public void added(DisplayObjectContainer parent) {}

	@Override
	public void removed(DisplayObjectContainer parent) {}

	@Override
	public void render(Graphics2D g) {
		if (image!=null)
		g.drawImage(image, local_bounds.x, local_bounds.y, local_bounds.width, local_bounds.height);
	}

	@Override
	public void update() {}
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}

}
