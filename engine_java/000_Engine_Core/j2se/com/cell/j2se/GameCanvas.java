package com.cell.j2se;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.cell.CObject;
import com.cell.IAppBridge;
import com.cell.gfx.AScreen;
import com.cell.gfx.IImage;


public class GameCanvas implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Runnable
{
	static private GameCanvas s_m_GameCanvas;
	
	final static public GameCanvas createInstance(IGame game){
		AScreen.clearCurScreen();
		s_m_GameCanvas = new GameCanvas(game);
		return s_m_GameCanvas;
	}
	final static public GameCanvas getInstance(){
		return s_m_GameCanvas;
	}

	
	//------------------------------------------------------------------
	private volatile Component 	Canvas;
	private volatile Thread		GameThread;
	private volatile boolean 	ThreadChanged = false;
	
	public IGame 		Game;
	
	Image		Buffer;
	CGraphics 	CG;
	
	int 		offset_x;
	int 		offset_y;
	float 		rate_x;
	float 		rate_y;
	float 		scale_x;
	float 		scale_y;
	
	
	private GameCanvas(IGame game)
	{
		Game = game;
//		CObject.initSystem(new CStorage(), bridge);
//		bridge.setSavePath(game.getSavePath());
//		CStorage.setAppPath(bridge.getAppSavePath());
//		Game.init();
		

		
		AScreen.SCREEN_WIDTH 	= game.getWidth();
		AScreen.SCREEN_HEIGHT 	= game.getHeight();
		AScreen.SCREEN_HCENTER 	= game.getWidth()/2;
		AScreen.SCREEN_VCENTER 	= game.getHeight()/2;

		IImage img = Game.getLoadingImage();
		
		if(img!=null){
			AScreen.ChangeSubScreen(Game.getRootScreenName(), img);
		}else{
			AScreen.ChangeSubScreen(Game.getRootScreenName(), "Loading...");
		}
	}

	synchronized public void startGame(Component canvas, Thread thread)
	{
		if (GameThread!=null)
		{
			if (GameThread.isAlive()){
				ThreadChanged = true;
				try {
					GameThread.join(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			GameThread = null;
		}
		
		if (Canvas!=null)
		{
			Canvas.removeKeyListener(this);
			Canvas.removeMouseListener(this);
			Canvas.removeMouseMotionListener(this);
			Canvas.removeMouseWheelListener(this);
		}
		
		Canvas = canvas;
		
		Canvas.setFocusable(true);
		Canvas.setEnabled(true);
		Canvas.enableInputMethods(true);
		
		Canvas.addKeyListener(this);
		Canvas.addMouseListener(this);
		Canvas.addMouseMotionListener(this);
		Canvas.addMouseWheelListener(this);

		GameThread = thread;
		ThreadChanged = false;
		GameThread.start();
	}
	
	synchronized public void exitGame(long timeOut){
		AScreen.ExitGame = true;
		ThreadChanged = true;
		if (GameThread!=null){
			try {
				GameThread.join(timeOut);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public void paint(Graphics2D g, int x, int y, int w, int h) 
	{
		offset_x = x;
		offset_y = y;
		rate_x = AScreen.SCREEN_WIDTH  / (float)w;
		rate_y = AScreen.SCREEN_HEIGHT / (float)h;
		scale_x = (float)w / AScreen.SCREEN_WIDTH;
		scale_y = (float)h / AScreen.SCREEN_HEIGHT;
		
		if(Buffer==null)
		{
			GraphicsConfiguration gc 	= g.getDeviceConfiguration();
			Buffer = gc.createCompatibleImage(
					AScreen.SCREEN_WIDTH, 
					AScreen.SCREEN_HEIGHT,
					Transparency.OPAQUE);
			CG = new CGraphics((Graphics2D)Buffer.getGraphics());
			AScreen.CurGraphics = CG;
		}
		else{
			try {
				AScreen.gobal_paint(CG);
				g.drawImage(Buffer, offset_x, offset_y, w, h, Canvas);
			} catch (Exception err) {
				err.printStackTrace();
			}
			/*AffineTransform t = new AffineTransform();
			t.translate(offset_x, offset_y);
			t.scale(scale_x, scale_y);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(Buffer, t, Canvas);*/
			
		}
		
	}
	

	public void run()
	{
		AScreen.ExitGame					= false;
		
		AScreen.FrameDelay = 1000 / Game.getFPS();
		
		while (!AScreen.ExitGame && !ThreadChanged)
		{
			long time = System.currentTimeMillis();
			
			try {
				AScreen.gobal_update();
			} catch (Exception err) {
				Game.exceptionCaught(err);
				//AScreen.clearCurScreen();
				//s_m_GameCanvas = null; // destory singlton instance
				err.printStackTrace();
			}
			
			Canvas.repaint();

			try{
				long elapsedTime = System.currentTimeMillis() - time;
				if (elapsedTime < AScreen.FrameDelay) {
					Thread.sleep(AScreen.FrameDelay - elapsedTime);
				}
			}catch(InterruptedException ir){
				ThreadChanged = true;
				ir.printStackTrace();
				break;
			}
		}
		
		if (AScreen.ExitGame){
			AScreen.clearCurScreen();
			Game.exited();
			s_m_GameCanvas = null; // destory singlton instance
			
//			for (IAppBridge.IAppStateListener listener : CObject.AppBridge.getAppListeners()){
//				listener.appExited();
//			}
		}
		
		GameThread = null;
	}


	public int getWidth(){
		return AScreen.SCREEN_WIDTH;
	}
	
	public int getHeight(){
		return AScreen.SCREEN_HEIGHT;
	}
	
	private int OldFrameDelay = 40;
	
	public boolean isActive(){
		return AScreen.RenderEnable;
	}
	
	public void activated() {
		AScreen.RenderEnable = true;
		AScreen.FrameDelay = OldFrameDelay;
		if(AScreen.getCurScreen()!=null){
			AScreen.getCurScreen().notifyResume();
			AScreen.clearKey();
		}
	}
	
	public void deactivated() {
		OldFrameDelay = AScreen.FrameDelay;
		AScreen.RenderEnable = false;
		AScreen.FrameDelay = 100;
		if(AScreen.getCurScreen()!=null){
			AScreen.getCurScreen().notifyPause();
			AScreen.clearKey();
		}
	}
	
	final private int getKeyValue(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_0 :
				return AScreen.KEY_0;
			case KeyEvent.VK_1 :
				return AScreen.KEY_1;
			case KeyEvent.VK_2 :
				return AScreen.KEY_2;
			case KeyEvent.VK_3 :
				return AScreen.KEY_3;
			case KeyEvent.VK_4 :
				return AScreen.KEY_4;
			case KeyEvent.VK_5 :
				return AScreen.KEY_5;
			case KeyEvent.VK_6 :
				return AScreen.KEY_6;
			case KeyEvent.VK_7 :
				return AScreen.KEY_7;
			case KeyEvent.VK_8 :
				return AScreen.KEY_8;
			case KeyEvent.VK_9 :
				return AScreen.KEY_9;
				
			case KeyEvent.VK_ALT :
				return AScreen.KEY_SHARP;
			case KeyEvent.VK_CONTROL:
				return AScreen.KEY_STAR;
				
			case KeyEvent.VK_UP :
				return AScreen.KEY_UP;
			case KeyEvent.VK_DOWN :
				return AScreen.KEY_DOWN;
			case KeyEvent.VK_LEFT :
				return AScreen.KEY_LEFT;
			case KeyEvent.VK_RIGHT :
				return AScreen.KEY_RIGHT;
				
			case KeyEvent.VK_F1 :
				return AScreen.KEY_A;
			case KeyEvent.VK_F2 :
			case KeyEvent.VK_ESCAPE :
				return AScreen.KEY_B;
			case KeyEvent.VK_ENTER :
				return AScreen.KEY_C;
				
			case KeyEvent.VK_TAB:// tab
				return AScreen.KEY_TAB;
			case -11:
			default :
				return 0;
		}
	}
	public void keyPressed(KeyEvent e) {
		AScreen.keyPressed(getKeyValue(e.getKeyCode()));
	}
	public void keyReleased(KeyEvent e) {
		AScreen.keyReleased(getKeyValue(e.getKeyCode()));
	}
	public void keyTyped(KeyEvent e) {
		AScreen.keyTyped(e.getKeyChar());
	}


//	mouse
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			AScreen.pointerPressed(
					-offset_x + (int)(e.getX() * rate_x),
					-offset_y + (int)(e.getY() * rate_y)
					);
		}else{
			AScreen.keyPressed(AScreen.KEY_B);
		}
	}
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			AScreen.pointerReleased(
					-offset_x + (int)(e.getX() * rate_x),
					-offset_y + (int)(e.getY() * rate_y)
					);
		}else{
			AScreen.keyReleased(AScreen.KEY_B);
		}
	}
	public void mouseDragged(MouseEvent e) {
		AScreen.pointerMoved(
				-offset_x + (int)(e.getX() * rate_x),
				-offset_y + (int)(e.getY() * rate_y)
				);
	}
	public void mouseMoved(MouseEvent e) {
		AScreen.pointerMoved(
				-offset_x + (int)(e.getX() * rate_x),
				-offset_y + (int)(e.getY() * rate_y)
				);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			AScreen.keyPressed(AScreen.KEY_DOWN);
			AScreen.keyReleased(AScreen.KEY_DOWN);
		}else if(e.getWheelRotation()<0){
			AScreen.keyPressed(AScreen.KEY_UP);
			AScreen.keyReleased(AScreen.KEY_UP);
		}
	}
}


