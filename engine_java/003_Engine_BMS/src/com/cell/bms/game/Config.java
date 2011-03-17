package com.cell.bms.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.g2d.Tools;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Menu;

public class Config extends com.cell.util.Config
{
	public static int STAGE_WIDTH	= 800;
	public static int STAGE_HEIGHT	= 600;
	
	public static int KEY_11		= KeyEvent.VK_A;
	public static int KEY_12		= KeyEvent.VK_S;
	public static int KEY_13		= KeyEvent.VK_D;
	public static int KEY_14		= KeyEvent.VK_F;
	public static int KEY_15		= KeyEvent.VK_J;
	public static int KEY_16		= KeyEvent.VK_K;
	public static int KEY_17		= KeyEvent.VK_L;
	public static int KEY_18		= KeyEvent.VK_SEMICOLON;
	
	public static int KEY_21		= KeyEvent.VK_A;        
	public static int KEY_22		= KeyEvent.VK_S;        
	public static int KEY_23		= KeyEvent.VK_D;        
	public static int KEY_24		= KeyEvent.VK_F;        
	public static int KEY_25		= KeyEvent.VK_J;        
	public static int KEY_26		= KeyEvent.VK_K;        
	public static int KEY_27		= KeyEvent.VK_L;        
	public static int KEY_28		= KeyEvent.VK_SEMICOLON;
	
	
	public static int getKey(int track) {
		switch(track) {
		case 11: return KEY_11;
		case 12: return KEY_12;
		case 13: return KEY_13;
		case 14: return KEY_14;
		case 15: return KEY_15;
		case 16: return KEY_16;
		case 17: return KEY_17;
		case 18: return KEY_18;
		case 21: return KEY_21;
		case 22: return KEY_22;
		case 23: return KEY_23;
		case 24: return KEY_24;
		case 25: return KEY_25;
		case 26: return KEY_26;
		case 27: return KEY_27;
		case 28: return KEY_28;
		}
		return -1;
	}
}
