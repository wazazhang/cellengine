package com.g2d.display.ui.layout;

import java.io.File;

import com.g2d.BufferedImage;


public class ImageUILayout extends UILayout 
{
	final BufferedImage image;
	
	final public File image_file;
	final public ImageStyle clip_style;
	final public int clip_border;
	
	public ImageUILayout(
			BufferedImage image, 
			File image_file, 
			ImageStyle style, 
			int clip_border) 
	{
		this.image			= image;
		this.image_file		= image_file;
		this.clip_style		= style;
		this.clip_border	= clip_border;

		setImages(image, clip_style, clip_border);
	}
	
	public ImageUILayout(ImageUILayout src) 
	{
		this.image			= src.image;
		this.image_file		= src.image_file;
		this.clip_style		= src.clip_style;
		this.clip_border	= src.clip_border;

		set(src);
	}
	
	@Override
	public ImageUILayout clone() {
		ImageUILayout ret = new ImageUILayout(this);
		return ret;
	}
	
	public BufferedImage srcImage() {
		return image;
	}
	
	public File srcImageName() {
		return image_file;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (image_file != null && image != null) {
			sb.append(image_file.getName() + " (" + image.getWidth() + "," + + image.getHeight() + ")\n");
		}
		sb.append(clip_style + "\n");
		sb.append(clip_border);
		return sb.toString();
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
