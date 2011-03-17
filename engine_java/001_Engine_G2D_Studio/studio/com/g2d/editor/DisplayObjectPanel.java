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

import com.g2d.Canvas;
import com.g2d.awt.util.*;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.java2d.impl.SimpleCanvas;

public class DisplayObjectPanel extends JPanel implements ComponentListener, AncestorListener
{
	private static final long serialVersionUID = 1L;
	
//	public Color back_color = Color.GREEN;
	
	final SimpleCanvas canvas;
	ReentrantLock service_lock = new ReentrantLock();
	
//	public DisplayObjectPanel()
//	{
//		this(new ObjectStage(Color.GREEN));
//	}
	
	public DisplayObjectPanel(Stage stage)
	{
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		this.addAncestorListener(this);
		
		this.canvas = new SimpleCanvas(100, 100);
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
	public SimpleCanvas getSimpleCanvas() {
		return canvas;
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
			canvas.start();
		} finally {
			service_lock.unlock();
		}
	}
	
	public void stop()
	{
		service_lock.lock();
		try{
			canvas.stop();
		} finally {
			service_lock.unlock();
		}
	}
	
	static public class ObjectStage extends Stage
	{
		public com.g2d.Color back_color;
		
		public ObjectStage(com.g2d.Color back_color) {
			this.back_color = back_color;
		}
		
		@Override
		public void added(DisplayObjectContainer parent) {
			getRoot().setFPS(40);
		}
		
		@Override
		public void removed(DisplayObjectContainer parent) {
			clearChilds();
		}
		
		@Override
		public void render(com.g2d.Graphics2D g) {
			g.setColor(back_color);
			g.fill(local_bounds);
		}
		@Override
		public void update() {}
	}
}
