package com.g2d.display.ui.layout;

import com.g2d.BufferedImage;


public class ImageUILayout extends UILayout 
{
	transient BufferedImage image;
	String			image_name;
	ImageStyle 		clip_style;
	int				clip_border;
	
	public ImageUILayout(
			BufferedImage image, 
			String image_name, 
			ImageStyle style, 
			int clip_border) 
	{
		this.image			= image;
		this.image_name		= image_name;
		this.clip_style		= style;
		this.clip_border	= clip_border;

		setImages(image, clip_style, clip_border);
	}
	
	public ImageUILayout(UILayout src) 
	{
		if (src instanceof ImageUILayout) {
			ImageUILayout ssrc = (ImageUILayout)src;
			this.image			= ssrc.image;
			this.image_name		= ssrc.image_name;
			this.clip_style		= ssrc.clip_style;
			this.clip_border	= ssrc.clip_border;
		}
		set(src);
	}
	
	public BufferedImage srcImage() {
		return image;
	}
	
	public String srcImageName() {
		return image_name;
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
