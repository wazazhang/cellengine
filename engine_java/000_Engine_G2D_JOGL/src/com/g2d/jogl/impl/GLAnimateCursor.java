package com.g2d.jogl.impl;

import java.awt.Cursor;

import com.g2d.AnimateCursor;

public class GLAnimateCursor implements AnimateCursor
{
	private Cursor[] 	list;
	private int 		index;
	
	public GLAnimateCursor(Cursor[] list) {
		this.list = list;
		this.index = 0;
	}
	
	public GLAnimateCursor(Cursor cursor) {
		this(new Cursor[]{cursor});
	}
	
	public GLAnimateCursor(int type) {
		this(new Cursor[]{Cursor.getPredefinedCursor(type)});
	}
	
	
	public java.awt.Cursor update() {
		if (index < 0) {
			index = 0;
		}
		index ++;
		if (list.length >0 ) {
			return list[index%list.length];
		}
		return Cursor.getDefaultCursor();
	}
	
}
