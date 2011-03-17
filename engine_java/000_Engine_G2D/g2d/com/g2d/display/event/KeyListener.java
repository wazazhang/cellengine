package com.g2d.display.event;

public interface KeyListener extends EventListener
{
	public void keyDown(KeyEvent e);
	
	public void keyUp(KeyEvent e);
	
	public void keyTyped(KeyEvent e);
}
