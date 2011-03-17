package com.g2d.java2d;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


import com.g2d.display.Stage;
import com.g2d.java2d.impl.AwtCanvas;


final public class SimpleCanvas extends Canvas implements Runnable
{
	final private AwtCanvas canvas;
	
	private Thread 		task;
	private boolean 	running = false;
	
	public SimpleCanvas(int width, int height)
	{
		super.setSize(width, height);
		
		this.canvas = new AwtCanvas(this, width, height);
	}
	
	public SimpleCanvas()
	{
		this.canvas = new AwtCanvas(this, getWidth(), getHeight());
	}
	
	
	public AwtCanvas getCanvasAdapter() {
		return canvas;
	}
	
	public void run()
	{
		try {
			while (running)
			{
				repaint();
				try {
					Thread.sleep(canvas.getFrameDelay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			canvas.setStage(null);
		}
	}
	
	public void update(Graphics dg) 
	{
		Graphics2D g = (Graphics2D)dg;
		
		canvas.update(g);

		Image vm_buffer = canvas.getVMBuffer();
		
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	public void paint(Graphics dg)
	{
		Graphics2D g = (Graphics2D)dg;
		Image vm_buffer = canvas.getVMBuffer();
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}

//	--------------------------------------------------------------------------------
    
   synchronized public void start(int fps, Class<? extends Stage> main_class) {
    	if (task == null) {
    		canvas.setFPS(fps);
    		canvas.changeStage(main_class);
    		task = new Thread(this, "paint");
    		running = true;
    		task.start();
    	}
    }
    
   synchronized  public void start() {
    	if (task == null) {
    		task = new Thread(this, "paint");
    		running = true;
    		task.start();
    	}
    }
    
   public void stop() {
    	if (task != null) {
    		running = false;
        	try {
				task.join(10000);
			} catch (InterruptedException e) {}
			task = null;
    	}
    }
    
    public float getFPS() {
    	return canvas.getFPS();
    }
    
//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------
    


   



}
