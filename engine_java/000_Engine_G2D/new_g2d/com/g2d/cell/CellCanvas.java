package com.g2d.cell;

import com.cell.gfx.AScreen;
import com.cell.gfx.IImage;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;


/**
 * @author WAZA
 * 对于老的 Cell 引擎主窗口的容器
 */
public class CellCanvas extends InteractiveObject
{
	public CellCanvas(int width, int height, IImage loadingImage, String rootscreen)
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
			AScreen.ChangeSubScreen(rootscreen, loadingImage);
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
			AScreen.gobal_paint(g);
		} catch (Throwable err) {
			err.printStackTrace();
		}
	}
	
	final private int getKeyValue(int keyCode)
	{
		switch (keyCode) {
			case java.awt.event.KeyEvent.VK_0 :
				return AScreen.KEY_0;
			case java.awt.event.KeyEvent.VK_1 :
				return AScreen.KEY_1;
			case java.awt.event.KeyEvent.VK_2 :
				return AScreen.KEY_2;
			case java.awt.event.KeyEvent.VK_3 :
				return AScreen.KEY_3;
			case java.awt.event.KeyEvent.VK_4 :
				return AScreen.KEY_4;
			case java.awt.event.KeyEvent.VK_5 :
				return AScreen.KEY_5;
			case java.awt.event.KeyEvent.VK_6 :
				return AScreen.KEY_6;
			case java.awt.event.KeyEvent.VK_7 :
				return AScreen.KEY_7;
			case java.awt.event.KeyEvent.VK_8 :
				return AScreen.KEY_8;
			case java.awt.event.KeyEvent.VK_9 :
				return AScreen.KEY_9;
				
			case java.awt.event.KeyEvent.VK_ALT :
				return AScreen.KEY_SHARP;
			case java.awt.event.KeyEvent.VK_CONTROL:
				return AScreen.KEY_STAR;
				
			case java.awt.event.KeyEvent.VK_UP :
				return AScreen.KEY_UP;
			case java.awt.event.KeyEvent.VK_DOWN :
				return AScreen.KEY_DOWN;
			case java.awt.event.KeyEvent.VK_LEFT :
				return AScreen.KEY_LEFT;
			case java.awt.event.KeyEvent.VK_RIGHT :
				return AScreen.KEY_RIGHT;
				
			case java.awt.event.KeyEvent.VK_F1 :
				return AScreen.KEY_A;
			case java.awt.event.KeyEvent.VK_F2 :
			case java.awt.event.KeyEvent.VK_ESCAPE :
				return AScreen.KEY_B;
			case java.awt.event.KeyEvent.VK_ENTER :
				return AScreen.KEY_C;
				
			case java.awt.event.KeyEvent.VK_TAB:// tab
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
		if(event.mouseButton == java.awt.event.MouseEvent.BUTTON1){
			AScreen.pointerPressed(getMouseX(), getMouseY());
		}else{
			AScreen.keyPressed(AScreen.KEY_B);
		}
	}
	
	@Override
	protected void onMouseUp(com.g2d.display.event.MouseEvent event) {
		if(event.mouseButton==java.awt.event.MouseEvent.BUTTON1){
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
