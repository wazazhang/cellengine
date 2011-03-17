package com.g2d.display.ui.text.ga;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphicsImage;


public class GraphicsAttributeSprite extends java.awt.font.GraphicAttribute
{
	private CSprite		sprite;
	private Rectangle	frame_bounds;
    
	public GraphicsAttributeSprite(CSprite sprite, int animate) {
		super(GraphicsAttributeImage.BOTTOM_ALIGNMENT);
		this.sprite = sprite.copy();
		this.sprite.setCurrentAnimate(animate);
		CCD bounds = this.sprite.getFrameBounds(animate);
		this.frame_bounds = new Rectangle(
				bounds.X1, 
				bounds.Y1,
				bounds.getWidth(), 
				bounds.getHeight());
	}
	
    public float getAscent() {
        return 0;
    }

    public float getDescent() {
        return frame_bounds.height;
    }

    public float getAdvance() {
        return frame_bounds.width;
    }

    public Rectangle2D getBounds() {
		return frame_bounds;
    }

    public void draw(Graphics2D graphics, float x, float y) {
    	CGraphicsImage cg = new CGraphicsImage(graphics);
    	sprite.render(cg, (int) (x-frame_bounds.x), (int) (y-frame_bounds.y));
    	sprite.nextCycFrame();
    }


}
