package com.g2d.cell;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.cell.gfx.AScreen;
import com.cell.j2se.CGraphics;
import com.cell.j2se.CImage;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;


/**
 * @author WAZA
 * 对于老的 Cell 引擎主窗口的容器
 */
public class CellCanvas extends InteractiveObject
{
	public CellCanvas(int width, int height, Image loadingImage, String rootscreen)
	{
		enable_key_input		= true;
		enable_mouse_wheel		= true;
		setSize(width, height);
		
		AScreen.ExitGame 		= false;
		AScreen.SCREEN_WIDTH 	= width;
		AScreen.SCREEN_HEIGHT 	= height;
		AScreen.SCREEN_HCENTER 	= width/2;
		AScreen.SCREEN_VCENTER 	= height/2;
		AScreen.setTransitionTagImage(null);
		
		if(loadingImage!=null){
			AScreen.ChangeSubScreen(rootscreen, new CImage(loadingImage));
		}else{
			AScreen.ChangeSubScreen(rootscreen, "Loading...");
		}
	}

	@Override
	public void added(DisplayObjectContainer parent) {

	}
	
	@Override
	public void removed(DisplayObjectContainer parent) {
		AScreen.clearCurScreen();
	}
	
	
	@Override
	public void update() {
		try {
			AScreen.pointerMoved(getMouseX(), getMouseY());
			AScreen.gobal_update();
		} catch (Throwable err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		try {
			AScreen.gobal_paint(new CGraphics(g));
		} catch (Throwable err) {
			err.printStackTrace();
		}
	}
	
	final private int getKeyValue(int keyCode)
	{
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
	
	@Override
	protected void onKeyDown(com.g2d.display.event.KeyEvent event) {
		AScreen.keyPressed(getKeyValue(event.keyCode));
	}
	
	@Override
	protected void onKeyUp(com.g2d.display.event.KeyEvent event) {
		AScreen.keyReleased(getKeyValue(event.keyCode));
	}
	
	@Override
	protected void onKeyTyped(com.g2d.display.event.KeyEvent event) {
		AScreen.keyTyped(event.keyChar);
	}
	
//	mouse
	
	@Override
	protected void onMouseDown(com.g2d.display.event.MouseEvent event) {
		if(event.mouseButton == MouseEvent.BUTTON1){
			AScreen.pointerPressed(getMouseX(), getMouseY());
		}else{
			AScreen.keyPressed(AScreen.KEY_B);
		}
	}
	
	@Override
	protected void onMouseUp(com.g2d.display.event.MouseEvent event) {
		if(event.mouseButton==MouseEvent.BUTTON1){
			AScreen.pointerReleased(getMouseX(), getMouseY());
		}else{
			AScreen.keyReleased(AScreen.KEY_B);
		}
	}
	
	
	@Override
	protected void onMouseClick(com.g2d.display.event.MouseEvent event) {
		super.onMouseClick(event);
	}
	
	@Override
	protected void onMouseWheelMoved(com.g2d.display.event.MouseWheelEvent event) {
		if(event.scrollDirection>0){
			AScreen.keyPressed(AScreen.KEY_DOWN);
			AScreen.keyReleased(AScreen.KEY_DOWN);
		}else if(event.scrollDirection<0){
			AScreen.keyPressed(AScreen.KEY_UP);
			AScreen.keyReleased(AScreen.KEY_UP);
		}
	}
		
		
		
		
		
		
}
