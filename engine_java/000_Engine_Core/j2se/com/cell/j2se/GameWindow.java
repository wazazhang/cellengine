package com.cell.j2se;

import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;

import javax.swing.JFrame;

public class GameWindow extends JFrame implements WindowListener
{
	DisplayMode	DesktopDisplayMode;
	//JFrame		FullScreen;
	
	GameCanvas	Canvas;
	
	public GameWindow(IGame game)
	{
		//System.out.println("Is double bufferd = "+super.isDoubleBuffered());
		
		try
		{
			GraphicsEnvironment ge 		= GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd 			= ge.getDefaultScreenDevice();
			GraphicsConfiguration gc 	= gd.getDefaultConfiguration();

			KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

			
			
			// layout manager
			DesktopDisplayMode			= gd.getDisplayMode();

			// game scene
			Canvas = GameCanvas.createInstance(game);
			
			// window
		    this.setTitle(game.getTitle());
			this.addWindowListener(this);
			this.setVisible(true);
			this.createBufferStrategy(2);
			
			this.setSize(
					game.getWidth()+getInsets().left+getInsets().right,
					game.getHeight()+getInsets().top+getInsets().bottom);
			this.setLocation(
					Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
					Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);
			
			if (game.getCursorImage()!=null) {
				Cursor cur = GameToolkit.createCustomCursor(game.getCursorImage().getSrc(), new Point(0, 0), "", this);
				this.setCursor(cur);
			}
			
			this.setVisible(true);
			this.setFocusable(true);
			this.setEnabled(true);
			this.enableInputMethods(true);
			
			this.setSize(
					game.getWidth()+getInsets().left+getInsets().right,
					game.getHeight()+getInsets().top+getInsets().bottom);
			
			Canvas.startGame(this,  new Thread(Canvas, "Main Game Thread"));
			
			this.setResizable(false);
			
			if (game.getIconImage()!=null) {
				this.setIconImage(game.getIconImage());
			}
			
		}catch(Exception err){
			err.printStackTrace();
		}
		
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		//super.paint(g);
		Canvas.paint((Graphics2D)g, 
				getInsets().left, 
				getInsets().top, 
				getWidth()-(getInsets().left+getInsets().right),
				getHeight()-(getInsets().top+getInsets().bottom));
	}



	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		try{
			Canvas.exitGame(1000);
		}catch(Exception err){err.printStackTrace();}
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e){
		Canvas.activated();
	}
	public void windowIconified(WindowEvent e){
		Canvas.deactivated();
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_HOME){
		}else if(e.getKeyCode()==KeyEvent.VK_F4){
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
