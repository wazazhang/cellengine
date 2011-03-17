package com.g2d.text;

import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;

public interface TextLayout 
{
	public void 		draw(Graphics2D g, int x, int y);
	
	public Rectangle 	getBounds();
	
	public int 			getCharacterCount();
	
	public Shape 		getCaretShape();

	public int 			getInsertionIndex();
}
