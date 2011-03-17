package com.g2d;

import com.g2d.geom.Rectangle;

public class TexturePaint implements Paint
{
	private BufferedImage image;
	private Rectangle rect;
	
	public TexturePaint(BufferedImage image, Rectangle rect) {
		this.image = image;
		this.rect = rect;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
