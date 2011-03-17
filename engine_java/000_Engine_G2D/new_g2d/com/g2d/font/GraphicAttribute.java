package com.g2d.font;

import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;



public abstract class GraphicAttribute
{
    /** 
     * Aligns top of graphic to top of line. 
     */
    public static final int TOP_ALIGNMENT = -1;

    /** 
     * Aligns bottom of graphic to bottom of line. 
     */
    public static final int BOTTOM_ALIGNMENT = -2;
    
    

	public abstract Rectangle getBounds();

    public abstract void draw(Graphics2D graphics, float x, float y);

    public abstract int getAlignment();

    /** bounds of min y */
    public abstract float getAscent();
    
    /** bounds of max y */
    public abstract float getDescent();

    /** bounds of max x */
    public abstract float getAdvance();
}