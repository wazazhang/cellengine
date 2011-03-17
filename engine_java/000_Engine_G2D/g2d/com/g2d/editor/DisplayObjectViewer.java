package com.g2d.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import com.g2d.Version;
import com.g2d.display.Canvas;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.util.AbstractFrame;

public abstract class DisplayObjectViewer<D extends DisplayObject> extends AbstractFrame implements Runnable
{
	private static final long serialVersionUID = Version.VersionG2D;

	public class SimpleStage extends Stage
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public SimpleStage() {
			if (view_object!=null) {
				addChild(view_object);
			}
		}
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {
			g.setColor(Color.GREEN);
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
	
	protected com.g2d.SimpleCanvas	canvas			= new com.g2d.SimpleCanvas();
	
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
	
	public void run() 
	{
		while (!exited)
		{
			try
			{
				if (view_object!=null && isVisible())
				{
					canvas.repaint();
					Thread.sleep(canvas.getCanvasAdapter().getFrameDelay());
				}
				else
				{
					Thread.sleep(1000);
				}
				
				Thread.yield();
			}
			catch (Exception e) {
				e.printStackTrace();
			}		
//			System.out.println("paint cc");

		}

		try {
			canvas.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			new Thread(this).start();
		}
	}
	
	public void close()
	{
		exited = true;
	}
	
	
	
	
}
