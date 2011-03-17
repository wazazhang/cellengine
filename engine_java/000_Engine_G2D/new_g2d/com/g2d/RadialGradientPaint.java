package com.g2d;

import com.g2d.geom.Point2D;


public class RadialGradientPaint implements Paint 
{
	Point2D point;
	float 	radius;
	float[] fractions; 
	Color[] colors;
	
	public RadialGradientPaint(
			float cx, float cy, 
			float radius,
			float[] fractions,
			Color[] colors)
	{
		this.point		= new Point2D.Float(cx, cy);
		this.radius		= radius;
		this.fractions	= fractions; 
		this.colors		= colors;
	}

	public Point2D getPoint() {
		return point;
	}

	public float getRadius() {
		return radius;
	}

	public float[] getFractions() {
		return fractions;
	}

	public Color[] getColors() {
		return colors;
	}
	
}
