package com.g2d.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.g2d.SimpleCanvasNoInternal;
import com.g2d.display.Canvas;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;

public class DisplayObjectPanel extends JPanel implements Runnable, ComponentListener, AncestorListener
{
	private static final long serialVersionUID = 1L;
	
//	public Color back_color = Color.GREEN;
	
	final SimpleCanvasNoInternal canvas;
	ReentrantLock service_lock = new ReentrantLock();
	Thread service;
	
//	public DisplayObjectPanel()
//	{
//		this(new ObjectStage(Color.GREEN));
//	}
	
	public DisplayObjectPanel(Stage stage)
	{
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		this.addAncestorListener(this);
		
		this.canvas = new SimpleCanvasNoInternal(100, 100);
		this.canvas.getCanvasAdapter().setStage(stage);
		this.add(canvas);
	}
	
	public void componentResized(ComponentEvent e) {
		canvas.getCanvasAdapter().setStageSize(canvas.getWidth(), canvas.getHeight());
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void ancestorAdded(AncestorEvent event) {}
	public void ancestorMoved(AncestorEvent event) {}
	public void ancestorRemoved(AncestorEvent event) {
		stop();
	}
	
	public Canvas getCanvas() {
		return canvas.getCanvasAdapter();
	}
	
	public Stage getStage() {
		return this.canvas.getCanvasAdapter().getStage();
	}

	public void start() {
		service_lock.lock();
		try{
			if (service==null) {
				service = new Thread(this);
				service.start();
				System.out.println("DisplayObjectPanel : start");
			}
		} finally {
			service_lock.unlock();
		}
	}
	
	public void stop()
	{
		service_lock.lock();
		try{
			if (service!=null) {
				Thread t = service;
				try {
					service = null;
					t.join(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					canvas.getCanvasAdapter().exit();
				}
//				System.out.println("stop");
			}
		} finally {
			service_lock.unlock();
		}
	}

	public void run()
	{
		while (service != null) {
			try {
				if (isVisible()) {
					canvas.repaint();
					Thread.sleep(canvas.getCanvasAdapter().getFrameDelay());
				} else {
					Thread.sleep(1000);
					Thread.yield();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		canvas.getCanvasAdapter().exit();
		System.out.println("DisplayObjectPanel : stop paint !");
	}
	
	static public class ObjectStage extends Stage
	{
		public Color back_color;
		
		public ObjectStage(Color back_color) {
			this.back_color = back_color;
		}
		
		@Override
		public void added(DisplayObjectContainer parent) {
			getRoot().setFPS(40);
		}
		
		@Override
		public void removed(DisplayObjectContainer parent) {}
		
		@Override
		public void render(Graphics2D g) {
			g.setColor(back_color);
			g.fill(local_bounds);
		}
		@Override
		public void update() {}
	}
}
