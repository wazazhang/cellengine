package com.cell.gfx.gui;

public interface ControlEventListener 
{
	final public int EVENT_CLICK 					= 0x001;
	
	final public int EVENT_KEY_PRESSED 				= 0x101;
	final public int EVENT_KEY_RELEASED 			= 0x102;
	final public int EVENT_KEY_HOLDING				= 0x103;
	
	final public int EVENT_MOUSE_GETED 				= 0x201;
	final public int EVENT_MOUSE_LOSSED				= 0x202;
	final public int EVENT_MOUSE_KEEPING			= 0x203;
	final public int EVENT_MOUSE_PRESSED	 		= 0x204;
	final public int EVENT_MOUSE_RELEASED	 		= 0x205;
	final public int EVENT_MOUSE_HOLDING	 		= 0x206;
	
	
	final public int EVENT_DRAG_START				= 0x10000001;
	final public int EVENT_DRAG_DROP				= 0x10000002;
	
	public int controlAction(Control sender, Object data, int event);
	
}
