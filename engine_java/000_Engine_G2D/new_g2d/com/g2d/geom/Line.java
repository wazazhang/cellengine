package com.g2d.geom;

public class Line extends Line2D
{
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public Point2D getP1() {
		return new Point(x1, y1);
	}

	@Override
	public Point2D getP2() {
		return new Point(x2, y2);
	}

	@Override
	public double getX1() {
		return x1;
	}

	@Override
	public double getX2() {
		return x2;
	}

	@Override
	public double getY1() {
		return y1;
	}

	@Override
	public double getY2() {
		return y2;
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		this.x1 = (int)x1;
		this.y1 = (int)y1;
		this.x2 = (int)x2;
		this.y2 = (int)y2;
	}

	@Override
	public Rectangle2D getBounds2D() {
		int x, y, w, h;
		if (x1 < x2) {
			x = x1;
			w = x2 - x1;
		} else {
			x = x2;
			w = x1 - x2;
		}
		if (y1 < y2) {
			y = y1;
			h = y2 - y1;
		} else {
			y = y2;
			h = y1 - y2;
		}
		return new Rectangle(x, y, w, h);
	}
	
}
