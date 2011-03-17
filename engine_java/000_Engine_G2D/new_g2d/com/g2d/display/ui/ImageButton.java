package com.g2d.display.ui;


import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;




public class ImageButton extends BaseButton implements Runnable
{
	transient protected Image	loading;
	
	transient protected Image	background;
	
	String image_path;

	
	public ImageButton(String path)
	{
		image_path = path;

		new Thread(this).start();
	}

	public ImageButton(Image bg)
	{		
		background = bg;
	}
	
	public void setBackground(Image img)
	{
		background = img;
	}
	
	public void setLoadingImage(Image img)
	{
		loading = img;
	}
	
	public Image getBackground()
	{
		return background;
	}
	
	public Image getLoadingImage()
	{
		return loading;
	}	
	
	public void run() 
	{
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
		if (background != null)
		{
			g.drawImage(background, 0, 0, getWidth(), getHeight());
		} 
		else if (loading != null)
		{
			g.pushTransform();
			g.translate(getWidth()/2, getHeight()/2);
			g.rotate(timer / 5.f);
			g.drawImage(loading, -loading.getWidth()/2, -loading.getHeight()/2);
			g.popTransform();
		}
		
//		renderCatchedMouse(g);
	}
	
	public static class FocusImageButton extends BaseButton
	{
		public UILayout	unfocus	= UILayout.createBlankRect();
		public UILayout	focus	= UILayout.createBlankRect();

		public FocusImageButton(BufferedImage unfocus, BufferedImage focus)
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

