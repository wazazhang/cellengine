package com.g2d;

import javax.swing.JApplet;

import com.g2d.display.Stage;

public class SimpleApplet extends JApplet implements Runnable
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	private CanvasAdapter canvas = null;

	public SimpleApplet() {
		this(new SimpleCanvas(640, 480).canvas_adapter, null);
	}
	
	public SimpleApplet(Stage stage) {
		this(new SimpleCanvas(stage.getWidth(), stage.getHeight()).canvas_adapter, null);
		this.canvas.changeStage(stage);
	}
	
	public SimpleApplet(CanvasAdapter canvas, String mainClass) 
	{
		this.setLayout(null);
		
		this.canvas = canvas;//new SimpleCanvas(width, height);
		this.canvas.setApplet(this);
		this.add(canvas.getComponent());
		if (mainClass!=null) {
			this.canvas.changeStage(mainClass);
		}
	}
	
	
	public CanvasAdapter getCanvas()
	{
		return this.canvas;
	}
	
	@Override
	public void init()
	{
		this.setSize(this.canvas.getStageWidth(), this.canvas.getStageHeight());
		this.setFocusable(true);
		new Thread(this, getClass().getName() + "-paint").start();
	}

	@Override
	public void destroy() {
		canvas.exit();
	}
	
	public void run()
	{
		while (!canvas.isExit())
		{
			canvas.getComponent().repaint();
			
			try {
				Thread.sleep(canvas.getFrameDelay());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		System.exit(0);
	}
	
	
	
	
}
