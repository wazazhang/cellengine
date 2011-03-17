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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;

import javax.swing.JApplet;

import com.cell.CObject;
import com.cell.IAppBridge;


abstract public class GameApplet extends JApplet implements FocusListener, KeyListener, MouseListener, MouseMotionListener
{
	GameCanvas Canvas;
	
	public void init(IGame game)
	{
		GraphicsEnvironment ge 		= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd 			= ge.getDefaultScreenDevice();
		GraphicsConfiguration gc 	= gd.getDefaultConfiguration();

		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		
		DisplayMode	DesktopDisplayMode = gd.getDisplayMode();
		
		Canvas = GameCanvas.createInstance(game);
		
		try{
		Cursor cur = GameToolkit.createCustomCursor(game.getCursorImage().getSrc(), new Point(0, 0), "", this);
		this.setCursor(cur);
		}catch(Exception err){
			err.printStackTrace();
		}
		this.setFocusable(true);
		this.setEnabled(true);
		this.enableInputMethods(true);
		this.setSize(game.getWidth(), game.getHeight());
	}
	
	public void init()
	{
		try{
			Cursor cur = GameToolkit.createCustomCursor(Canvas.Game.getCursorImage().getSrc(), new Point(0, 0), "", this);
			this.setCursor(cur);
		}catch(Exception err){
			err.printStackTrace();
		}
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addFocusListener(this);
		this.setVisible(true);
		this.resize(Canvas.getWidth(), Canvas.getHeight());
		
		Canvas.Game.initApplet(this);
		
		Canvas.startGame(this, new Thread(GameCanvas.getInstance(), "Main Game Thread"));
		
		//System.out.println("[applet init]");
		Canvas.activated();
		
		
//		for (IAppBridge.IAppStateListener listener : CObject.AppBridge.getAppListeners()){
//			listener.appletRefresh();
//		}
		
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		Canvas.paint((Graphics2D) g, 0, 0, getWidth(), getHeight());
	}
	
	public void start(){
		Canvas.activated();
	}
	
	public void focusGained(FocusEvent e) {
		Canvas.activated();
	}

	public void focusLost(FocusEvent e) {
		Canvas.deactivated();
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (!Canvas.isActive()){
			Canvas.activated();
			processFocusEvent(new FocusEvent(this, FocusEvent.FOCUS_GAINED));
		}
		
	}
	public void mouseReleased(MouseEvent e) {}
	
	
	

}
