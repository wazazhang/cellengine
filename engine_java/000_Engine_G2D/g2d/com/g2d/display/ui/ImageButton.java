package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.ui.layout.UILayout;

public class ImageButton extends BaseButton implements Runnable
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient public Image	loading;
	
	transient public Image	background;
	
	String 					image_path;
	
	public ImageButton(String path){
		image_path = path;
		new Thread(this).start();
	}
	
	public ImageButton(Image bg){
		background = bg;
	}
	
	public void run() {
		try {
			background = Tools.readImage(image_path);
			System.out.println("loadimage : "+image_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics2D g)
	{
		if (background != null) {
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		} else if (loading != null) {
			AffineTransform trans = g.getTransform();
			g.translate(getWidth()/2, getHeight()/2);
			g.rotate(timer / 5.f);
			g.drawImage(loading, -loading.getWidth(this)/2, -loading.getHeight(this)/2, this);
			g.setTransform(trans);
		}
		
//		renderCatchedMouse(g);
	}
	
	public static class FocusImageButton extends BaseButton
	{
		public UILayout	unfocus	= UILayout.createBlankRect();
		public UILayout	focus	= UILayout.createBlankRect();

		public FocusImageButton(Image unfocus, Image focus)
		{
			this.unfocus = new UILayout();
			this.unfocus.setImages(unfocus, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
			
			this.focus = new UILayout();
			this.focus.setImages(focus, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
		}
		
		@Override
		public void render(Graphics2D g)
		{
			if (isCatchedMouse()) {
				this.custom_layout = focus;				
			} else {
				this.custom_layout = unfocus;		
			}
			renderLayout(g);
		}
	}
}

