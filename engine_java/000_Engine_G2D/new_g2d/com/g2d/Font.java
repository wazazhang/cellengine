package com.g2d;

import com.g2d.geom.Rectangle;

public interface Font
{
    /**
     * The plain style constant.
     */
    public static final int STYLE_PLAIN       = 0;

    /**
     * The bold style constant.  This can be combined with the other style
     * constants (except PLAIN) for mixed styles.
     */
    public static final int STYLE_BOLD        = 1;

    /**
     * The italicized style constant.  This can be combined with the other
     * style constants (except PLAIN) for mixed styles.
     */
    public static final int STYLE_ITALIC      = 2;
    
    
    
	public String getName() ;
	
	public int getSize();
	
	public int getStyle();
	
	public Rectangle getStringBounds(String src, Graphics2D g2d);
	
	public Font newSize(int size);
	
	public Font newStyle(int style);
	
	public Font newInstance(int style, int size);
}
