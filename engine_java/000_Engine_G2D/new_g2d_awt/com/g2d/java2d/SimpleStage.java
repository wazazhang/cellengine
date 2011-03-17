package com.g2d.java2d;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;

public class SimpleStage extends Stage
{

	@Override
	public void added(DisplayObjectContainer parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removed(DisplayObjectContainer parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
		g.setColor(Color.WHITE);
		g.drawString("FPS="+getRoot().getFPS(), 10, 10);
	}

}
