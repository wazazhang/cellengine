package com.cell.math;

public class TVector implements Vector
{
	public double x;
	public double y;

	public TVector() {}
	
	public TVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void addVectorX(double dx) {
		this.x += dx;
	}
	public void addVectorY(double dy) {
		this.y += dy;
	}
	public void setVectorX(double x) {
		this.x = x;
	}
	public void setVectorY(double y) {
		this.y = y;
	}
	public double getVectorX() {
		return this.x;
	}
	public double getVectorY() {
		return this.y;
	}

}
