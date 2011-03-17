package com.g2d.java2d.impl;

import java.text.AttributedString;

import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Shape;
import com.g2d.text.TextLayout;

public class AwtTextLayout implements TextLayout
{

	protected AwtTextLayout(AttributedString text) {
		
	}
	
	
	@Override
	public Shape getCaretShape() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void draw(Graphics2D g, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCharacterCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInsertionIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
