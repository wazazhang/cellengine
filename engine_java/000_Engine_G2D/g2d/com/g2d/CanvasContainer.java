package com.g2d;

import java.awt.Component;
import java.awt.Graphics;

public interface CanvasContainer 
{
	Component getContainer();
	
	Component getComponent();
	
	void superPaint(Graphics g);
	
	void superUpdate(Graphics g);
}
