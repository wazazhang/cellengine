package com.g2d.display;

import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;


public interface StageTransition
{
	public void startTransitionIn() ;
	
	public void startTransitionOut();
	
	public boolean isTransitionIn();
	
	public boolean isTransitionOut();

	public void render(Graphics2D g, Rectangle bounds);
}
