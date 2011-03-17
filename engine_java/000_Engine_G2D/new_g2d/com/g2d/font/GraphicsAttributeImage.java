package com.g2d.font;


import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Tools;
import com.g2d.geom.Rectangle;


public class GraphicsAttributeImage extends GraphicAttribute
{
	public static Image	ERROR_IMAGE = Tools.createImage(16, 16);
	
	private Image 		fImage;
	private Rectangle	fImageBounds;
    
	public GraphicsAttributeImage(String image_path) 
	{		
		fImage 			= Tools.readImage(image_path);
		if (fImage == null) {
			fImage = ERROR_IMAGE;
		}
		fImageBounds	= new Rectangle(0, 0, fImage.getWidth(), fImage.getHeight());
	}
	
	public GraphicsAttributeImage(Image image)
	{	
		fImage 			= image;
		fImageBounds	= new Rectangle(0, 0, fImage.getWidth(), fImage.getHeight());
	}

	public int getAlignment() {
		return GraphicsAttributeImage.TOP_ALIGNMENT;
	}
	
    public float getAscent() {
        return 0;
    }

    public float getDescent() {
        return fImageBounds.height;
    }

    public float getAdvance() {
        return fImageBounds.width;
    }

    public Rectangle getBounds() {
		return fImageBounds;
    }

    public void draw(Graphics2D graphics, float x, float y) {
        graphics.drawImage(fImage, (int) (x), (int) (y));
    }


}
