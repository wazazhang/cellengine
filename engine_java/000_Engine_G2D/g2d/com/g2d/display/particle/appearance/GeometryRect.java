package com.g2d.display.particle.appearance;

import java.awt.Graphics2D;

import com.g2d.annotation.Property;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleAppearance;

@Property("几何造型-矩形")
public class GeometryRect extends Geometry
{
	private static final long serialVersionUID = 1L;
	
	/**宽*/@Property("宽")
	public int width = 2;
	
	/**高*/@Property("高")
	public int height = 2;
	
	@Override
	public ParticleAppearance cloneDisplay() {
		return this;
	}
	
	public void render(Graphics2D g, Layer layer) {
		g.setColor(color);
		if (is_fill) {
			g.fillRect(-(width<<1), -(height<<1), width, height);
		} else {
			g.drawRect(-(width<<1), -(height<<1), width, height);
		}
	}
}
