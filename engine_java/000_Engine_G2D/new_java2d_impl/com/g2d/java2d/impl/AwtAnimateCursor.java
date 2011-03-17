package com.g2d.java2d.impl;

import java.awt.Cursor;

import com.g2d.AnimateCursor;

public class AwtAnimateCursor implements AnimateCursor
{
	private Cursor[] 	list;
	private int 		index;
	
	public AwtAnimateCursor(Cursor[] list) {
		this.list = list;
		this.index = 0;
	}
	
	public AwtAnimateCursor(Cursor cursor) {
		this(new Cursor[]{cursor});
	}
	
	public AwtAnimateCursor(int type) {
		this(new Cursor[]{Cursor.getPredefinedCursor(type)});
	}
	
	
	public java.awt.Cursor update() {
		if (index < 0) {
			index = 0;
		} else {
			index++;
		}
		if (list.length > 0) {
			int i = index % list.length;
			return list[i];
		}
		return Cursor.getDefaultCursor();
	}
	
}
