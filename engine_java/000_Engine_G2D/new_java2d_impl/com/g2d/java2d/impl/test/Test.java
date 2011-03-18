package com.g2d.java2d.impl.test;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cell.j2se.CAppBridge;
import com.g2d.Canvas;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.event.MouseEvent;
import com.g2d.java2d.SimpleStage;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.java2d.impl.SimpleFrame;


public class Test extends Stage
{
	@Override
	public void inited(Canvas root, Object[] args)
	{
		for (int i = 0; i < 10; i++) {
			addChild(new TestShape());
		}
	}
	
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
		if (getRoot().isKeyDown(KeyEvent.VK_1)) {
			TestShape sp = new TestShape();
			sp.setLocation(getMouseX(), getMouseY());
			addChild(sp);
		}	
		if (getRoot().isKeyDown(KeyEvent.VK_2)) {
			if (getChildCount()>4) {
				removeChild(getChilds().firstElement());
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		
//		g.pushClip();
		g.setClip(32, 32, 200, 200);
		g.setColor(Color.RED);
		g.fillRect(32, 32, getWidth() / 2, getHeight() / 2);
//		g.popClip();
		
		g.setColor(Color.BLUE);
		g.drawRect(32, 32, 200, 200);
		
		g.setColor(Color.YELLOW);
		g.fillRect(16, 16, 16, 16);

		g.setColor(Color.GREEN);
		g.drawRect(32, 32, 64, 64);
		
		
		g.setColor(Color.WHITE);
		g.drawString("FPS="+getRoot().getFPS(), 0, 0);
		
	}
	
	
	public static void main(String[] args) 
	{

    	EventQueue.invokeLater(new Runnable() {
            public void run() {
            	CAppBridge.init();
            	new AwtEngine();
                SimpleFrame frame = new SimpleFrame(800, 600);
                frame.setVisible(true);
                frame.start(60, Test.class);
            }
        });
    
	}
}
