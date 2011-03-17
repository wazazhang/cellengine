package com.g2d;

import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.g2d.display.Stage;

public class SimpleFrame extends JFrame implements Runnable, WindowListener, ComponentListener
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	private CanvasAdapter canvas = null;
	
	public SimpleFrame(String mainClass, int width, int height) 
	{
		this(new SimpleCanvas(width, height).canvas_adapter, mainClass);
	}
	
	public SimpleFrame(CanvasAdapter canvas, String mainClass) 
	{
		this.setLayout(null);
		
		this.canvas = canvas;
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(this);
		this.addComponentListener(this);
		
		this.add(canvas.getComponent());
		this.setVisible(true);

		int frameWidth  = canvas.getComponent().getWidth()  + (getInsets().left+getInsets().right);
		int frameHeight = canvas.getComponent().getHeight() + (getInsets().top+getInsets().bottom);
		
		
		this.setSize(frameWidth, frameHeight);
		this.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2
				);
		this.setVisible(true);
		
		if (mainClass!=null) {
			this.canvas.changeStage(mainClass);
		}
		
		new Thread(this, getClass().getName() + "-paint").start();
	}
	
	public void fillCanvasSize() {
		if (canvas != null) {
			int cw = getWidth()  - (getInsets().left+getInsets().right);
			int ch = getHeight() - (getInsets().top+getInsets().bottom);
			canvas.getComponent().setSize(Math.max(cw, 10), Math.max(ch, 10));
		}
	}
	
	public CanvasAdapter getCanvas()
	{
		return this.canvas;
	}
	
	public void run()
	{
		while (!canvas.isExit())
		{
			canvas.getComponent().repaint();
			
			try {
				if (is_show_game){
					Thread.sleep(canvas.getFrameDelay());
				}else{
					Thread.sleep(canvas.getUnactiveFrameDelay());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.exit(0);
	}
	
	boolean is_show_game = true;

	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		if (canvas.getStage() != null) {
			if (!canvas.getStage().onWindowClose()) {
				canvas.exit();
			}
		}
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	
	
	public void windowDeiconified(WindowEvent e) {
//		System.out.println(e.paramString());
		is_show_game = true;
	}
	public void windowIconified(WindowEvent e) {
//		System.out.println(e.paramString());
		is_show_game = false;
	}
	
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {
		canvas.updateAppendWindow();
	}
	public void componentResized(ComponentEvent e) {
		canvas.updateAppendWindow();
	}
	public void componentShown(ComponentEvent e) {}
	
	
//	--------------------------------------------------------------------------------------------------------------------

	public static SimpleFrame showSimpleGameScene(String stagename, int width, int height){
		return new SimpleFrame(stagename, width, height);
	}
	
	public static SimpleFrame showSimpleStage(Stage stage){
		SimpleFrame frame = new SimpleFrame(null, stage.getWidth(), stage.getHeight());
		frame.getCanvas().changeStage(stage);
		return frame;
	}
	public static void main(String[] args){
		showSimpleGameScene(null, 640, 480);
	}
}