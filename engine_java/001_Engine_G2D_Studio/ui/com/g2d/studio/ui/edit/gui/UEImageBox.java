package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.util.Drawing.ImageAnchor;

public class UEImageBox extends UIComponent implements SavedComponent
{
	public BufferedImage image;
	
	@Property("图片地址")
	public String imagePath = "";
	@Property("旋转0~360")
	public float x_rotate = 0;
	@Property("缩放X %")
	public float x_scaleX = 100f;
	@Property("缩放Y %")
	public float x_scaleY = 100f;
	
	
	public UEImageBox() {
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		if (image != null) {
			g.pushTransform();
			int iw = image.getWidth();
			int ih = image.getHeight();
			
			g.translate(getWidth()/2, getHeight()/2);
			g.rotate(Math.toRadians(x_rotate));
			g.scale(x_scaleX/100f, x_scaleY/100f);
			g.drawImage(image, -iw/2, -ih/2);
			g.popTransform();
		}
	}
	
	
	@Override
	public void onRead(UIEdit edit, Element e) throws Exception {
		image = edit.getLayoutManager().getImage(imagePath);
	}

	@Override
	public void onWrite(UIEdit edit, Element e) throws Exception
	{
		//imagePath = edit.getLayoutManager();
	}
	
}