package com.cell.midi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import com.cell.midi.stage.StageTitle;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.display.Canvas;
import com.g2d.display.Stage;

public class MainCanvas extends JPanel implements ComponentListener, Runnable
{
	private static final long serialVersionUID = 1L;
	
	SimpleCanvasNoInternal 	canvas 			= new SimpleCanvasNoInternal(100, 100);
	ReentrantLock 			service_lock 	= new ReentrantLock();
	Thread 					service;
	
	public MainCanvas()
	{
		canvas.getCanvasAdapter().setStage(new StageTitle());
		canvas.getCanvasAdapter().setFPS(Config.DEFAULT_FPS);
		
		
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		this.add(canvas);
		this.service = new Thread(this);
		this.service.start();
	}
	
	public void componentResized(ComponentEvent e) {
		canvas.getCanvasAdapter().setStageSize(canvas.getWidth(), canvas.getHeight());
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	
	
	
	public Canvas getCanvas() {
		return canvas.getCanvasAdapter();
	}
	
	public Stage getStage() {
		return this.canvas.getCanvasAdapter().getStage();
	}

	public void run()
	{
		while (service != null) {
			try {
				if (isVisible()) {
					canvas.getCanvasAdapter().repaint_game();
					Thread.sleep(canvas.getCanvasAdapter().getFrameDelay());
				} else {
					Thread.sleep(1000);
					Thread.yield();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
