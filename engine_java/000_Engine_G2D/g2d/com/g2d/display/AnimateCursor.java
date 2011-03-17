package com.g2d.display;

import java.awt.Cursor;

import com.cell.CMath;

public class AnimateCursor 
{
	final protected Cursor[]	animate;
	
	int index	= 0;
	
//	-------------------------------------------------------------------------------------------------------
	
	public AnimateCursor(Cursor cursor) {
		animate = new Cursor[]{cursor};
	}
	
	public AnimateCursor(Cursor[] cursors) {
		animate = cursors;
	}
	
	public void update() {
		index = CMath.cycNum(index, 1, animate.length);	
	}
	
//	-------------------------------------------------------------------------------------------------------

	public Cursor getCursor() {
		if (index < animate.length) {
			return animate[index];
		}
		return Cursor.getDefaultCursor();
	}
	
//	-------------------------------------------------------------------------------------------------------
	
	public static AnimateCursor RESIZE_CURSOR_NW = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
	public static AnimateCursor RESIZE_CURSOR_N	 = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
	public static AnimateCursor RESIZE_CURSOR_NE = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
	
	public static AnimateCursor RESIZE_CURSOR_W  = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
	public static AnimateCursor RESIZE_CURSOR_E  = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));

	public static AnimateCursor RESIZE_CURSOR_SW = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
	public static AnimateCursor RESIZE_CURSOR_S  = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
	public static AnimateCursor RESIZE_CURSOR_SE = new AnimateCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
	
	public static AnimateCursor HAND_CURSOR =
		new AnimateCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
	public static AnimateCursor TEXT_CURSOR =
		new AnimateCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
	
	
	
	
	
	
}

