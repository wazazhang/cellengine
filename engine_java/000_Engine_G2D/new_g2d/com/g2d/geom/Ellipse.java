package com.g2d.geom;

public class Ellipse extends Ellipse2D {

	private int x;
	private int y;
	private int w;
	private int h;
	
	public Ellipse(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return w;
	}

	public double getHeight() {
		return h;
	}

	@Override
	public boolean isEmpty() {
	    return (w <= 0 || h <= 0);
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
		this.x = (int)x;
		this.y = (int)y;
		this.w = (int)w;
		this.h = (int)h;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle(x, y, w, h);
	}

}
