package com.g2d.java2d.impl;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


import com.g2d.display.Stage;
import com.g2d.java2d.CommonCanvasAdapter;


final public class SimpleCanvas extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	final private AwtCanvasAdapter	adapter;
	private Thread 					task;
	private boolean 				running = false;
	
	public SimpleCanvas(int width, int height)
	{
		super.setSize(width, height);
		
		this.adapter = new AwtCanvasAdapter(this, width, height);
	}
	
	public SimpleCanvas()
	{
		this.adapter = new AwtCanvasAdapter(this, getWidth(), getHeight());
	}
	
	
	public AwtCanvasAdapter getCanvasAdapter() {
		return adapter;
	}
	
	public void run()
	{
		try {
			while (running)
			{
				repaint();
				try {
					Thread.sleep(adapter.getFrameDelay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			adapter.setStage(null);
		}
	}
	
	public void update(Graphics dg) 
	{
		Graphics2D g = (Graphics2D)dg;
		
		adapter.update(g);

		Image vm_buffer = adapter.getVMBuffer();
		
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	public void paint(Graphics dg)
	{
		Graphics2D g = (Graphics2D)dg;
		Image vm_buffer = adapter.getVMBuffer();
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}

//	--------------------------------------------------------------------------------
    
   synchronized public void start(int fps, Class<? extends Stage> main_class) {
    	if (task == null) {
    		adapter.setFPS(fps);
    		adapter.changeStage(main_class);
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
    	return adapter.getFPS();
    }
    
//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------

//	---------------------------------------------------------------------------------------------------------------
//	
//	---------------------------------------------------------------------------------------------------------------
    


   



}
