package com.g2d.display.particle.appearance;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.g2d.annotation.Property;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleAppearance;

@Property("几何造型-任意形状")
public class GeometryShape extends Geometry
{
	private static final long serialVersionUID = 1L;
	
	/**几何造型*/
	public Shape shape = new Rectangle();
	
	@Override
	public ParticleAppearance cloneDisplay() {
		return this;
	}
	
	public void render(Graphics2D g, Layer layer) {
		g.setColor(color);
		if (is_fill) {
			g.fill(shape);
		} else {
			g.draw(shape);
		}
	}
}
