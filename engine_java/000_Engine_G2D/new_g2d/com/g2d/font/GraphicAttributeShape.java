package com.g2d.font;


import com.g2d.Graphics2D;
import com.g2d.geom.Ellipse2D;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Rectangle2D;
import com.g2d.geom.Shape;



public class GraphicAttributeShape extends GraphicAttribute
{
    public static final boolean STROKE = true;
    public static final boolean FILL = false;

    private Shape fShape;
    private boolean fStroke;
    private Rectangle2D fShapeBounds;
    private int alignment;
    
	public GraphicAttributeShape(Shape shape, int alignment, boolean stroke) {
		this.alignment = alignment;
		this.fShape = shape;
		this.fStroke = stroke;
		this.fShapeBounds = fShape.getBounds2D();
	}
	
	@Override
	public int getAlignment() {
		return alignment;
	}
	
    public float getAscent() {
        return (float) Math.max(0, -fShapeBounds.getMinY());
    }

    public float getDescent() {
        return (float) Math.max(0, fShapeBounds.getMaxY());
    }

    public float getAdvance() {
        return (float) Math.max(0, fShapeBounds.getMaxX());
    }

	public void draw(Graphics2D graphics, float x, float y) {
		graphics.translate((int) x, (int) y);
		try {
			if (fStroke == STROKE) {
				graphics.draw(fShape);
			} else {
				graphics.fill(fShape);
			}
		} finally {
			graphics.translate(-(int) x, -(int) y);
		}
	}

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle();
        bounds.setRect(fShapeBounds);
        if (fStroke == STROKE) {
            ++bounds.width;
            ++bounds.height;
        }
        return bounds;
    }


}