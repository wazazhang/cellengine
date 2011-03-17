package com.g2d.java2d;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.g2d.display.Stage;

public class SimpleFrame extends JFrame implements WindowListener
{	
	private SimpleCanvas canvas;
	
	public SimpleFrame(int width, int height) 
	{
		this.canvas = new SimpleCanvas(width, height);

		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(this);
		
		this.add(canvas);
		this.setVisible(true);

		int frameWidth  = canvas.getWidth()  + (getInsets().left+getInsets().right);
		int frameHeight = canvas.getHeight() + (getInsets().top+getInsets().bottom);
		
		
		this.setSize(frameWidth, frameHeight);
		this.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2
				);
		this.setVisible(true);
	}
	
	public SimpleCanvas getCanvas() {
		return canvas;
	}
	
	public void start(int fps, Class<? extends Stage> state_name) {
		 canvas.start(fps, state_name);
	}
	
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		new Thread() {
			public void run() {
				canvas.stop();
				System.exit(1);
			}
		}.start();
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	
}