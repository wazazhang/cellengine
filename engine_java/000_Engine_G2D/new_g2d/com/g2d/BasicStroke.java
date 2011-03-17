package com.g2d;


public class BasicStroke implements Stroke
{
	private int size;
	
	public BasicStroke(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
}
