package com.g2d.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import com.g2d.Canvas;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.java2d.impl.SimpleCanvas;

public abstract class DisplayObjectViewer<D extends DisplayObject> extends AbstractFrame
{
	private static final long serialVersionUID = 1L;

	public class SimpleStage extends Stage
	{		
		public SimpleStage() {
			if (view_object!=null) {
				addChild(view_object);
			}
		}
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void render(com.g2d.Graphics2D g) {
			g.setColor(com.g2d.Color.GREEN);
			g.fill(local_bounds);
		}
		public void update() {
			if (view_object!=null) {
				if (getRoot().isMouseDown(MouseEvent.BUTTON1)) {
					view_object.setLocation(getMouseX(), getMouseY());
				}
			}
		}
	}
	

	
	
	protected D 				view_object;
	
	protected SimpleCanvas		canvas			= new SimpleCanvas();
	
	private boolean 			exited			= true;
	
	
	/**
	 * 仅仅用于继承, 继承的类将自己处理如何显示对象
	 * @param object
	 */
	protected DisplayObjectViewer(D object)
	{
		view_object = object;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//		Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {close();}});
		
	}
	
	
	/**
	 * 创建一个有默认canvas的视图
	 * @param object
	 * @param width
	 * @param height
	 */
	public DisplayObjectViewer(D object, int width, int height)
	{
		this(object);
		
		this.setLayout(null);
		this.setSize(width, height);
		
		canvas.getCanvasAdapter().fillStageSize(this);
		canvas.getCanvasAdapter().setStage(new SimpleStage());
		
		
		this.add(canvas);
		this.addComponentListener(new ComponentListener(){
			public void componentResized(ComponentEvent e) {
				//System.out.println("resize : " + e.paramString());
				canvas.getCanvasAdapter().fillStageSize(DisplayObjectViewer.this);
			}
			public void componentHidden(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
		});
		
	}
	
	public Canvas getCanvas() {
		return this.canvas.getCanvasAdapter();
	}
	
	public D getViewObject() {
		return view_object;
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			reopen();
		} else {
			exited = true;
		}
	}
	
	public void reopen() 
	{
		if (exited) {
			exited = false;
			canvas.start();
		}
	}
	
	public void close()
	{
		exited = true;
		canvas.stop();
	}
	
	
	
	
}
