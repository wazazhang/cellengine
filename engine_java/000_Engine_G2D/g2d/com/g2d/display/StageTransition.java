package com.g2d.display;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface StageTransition
{
	public void startTransitionIn() ;
	
	public void startTransitionOut();
	
	public boolean isTransitionIn();
	
	public boolean isTransitionOut();

	public void render(Graphics2D g, Rectangle bounds);
}
