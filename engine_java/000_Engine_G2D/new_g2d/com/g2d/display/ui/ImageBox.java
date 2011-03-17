package com.g2d.display.ui;

import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Tools;

public class ImageBox extends UIComponent
{
	
	transient public Image	background;
	
	public ImageBox(){}
	
	public ImageBox(String path)
	{
		setImage(path);
	}
	
	public ImageBox(Image img) {
		setImage(img);
		setSize(img.getWidth(), img.getHeight());
	}


	public void setImage(Image img) {
		background = img;
	}
	
	public void setImage(String path) {
		if (path!=null) {
			try {
				background = Tools.readImage(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Image getImage()
	{
		return background;
	}
	
	@Override
	public void render(Graphics2D g)
	{
		super.render(g);
		if (background!=null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight());
		}
		
	}
	
}
