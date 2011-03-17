package com.g2d.display.ui.layout;

import java.awt.image.BufferedImage;

import com.g2d.Version;

public class ImageUILayout extends UILayout 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient BufferedImage image;
	String			image_name;
	ImageStyle 		clip_style;
	int				clip_border;
	
	public ImageUILayout(BufferedImage image, String image_name, ImageStyle style, int clip_border) 
	{
		this.image			= image;
		this.image_name		= image_name;
		this.clip_style		= style;
		this.clip_border	= clip_border;

		setImages(image, clip_style, clip_border);
	}
	
//	@Override
//	protected void onRead(MarkedHashtable data) {
//		super.onRead(data);
//		image = Tools.readImage(UILayoutManager.getInstance().save_path + "/" + image_name);
//		setImages(image, clip_style, clip_border);
//	}
//	
//	@Override
//	protected void onWrite(MarkedHashtable data) {
//		super.onWrite(data);
//		Tools.writeImage(UILayoutManager.getInstance().save_path + "/" + image_name, image);
//	}
	

	
	
	
}
