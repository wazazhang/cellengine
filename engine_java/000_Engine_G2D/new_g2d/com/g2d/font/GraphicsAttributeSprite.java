package com.g2d.font;


import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;


public class GraphicsAttributeSprite extends GraphicAttribute
{
	private CSprite		sprite;
	private Rectangle	frame_bounds;
	private CCD			cd_bounds;
	
	public GraphicsAttributeSprite(CSprite sprite, int animate) {
		this.sprite = sprite.copy();
		this.sprite.setCurrentAnimate(animate);
		this.cd_bounds = this.sprite.getFrameBounds(animate);
		this.frame_bounds = new Rectangle(
				0, 
				cd_bounds.Y1,
				cd_bounds.getWidth(), 
				cd_bounds.getHeight());
	}
	
	@Override
	public int getAlignment() {
		return GraphicsAttributeImage.BOTTOM_ALIGNMENT;
	}
	
    public float getAscent() {
        return cd_bounds.Y1;
    }

    public float getDescent() {
        return cd_bounds.Y2;
    }

    public float getAdvance() {
        return frame_bounds.width;
    }

    public Rectangle getBounds() {
		return frame_bounds;
    }

    public void draw(Graphics2D graphics, float x, float y) {
    	sprite.render(graphics, (int) (x-frame_bounds.x), (int) (y-frame_bounds.y));
    	sprite.nextCycFrame();
    }


}
