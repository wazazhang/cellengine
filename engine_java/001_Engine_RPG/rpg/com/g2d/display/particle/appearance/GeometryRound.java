package com.g2d.display.particle.appearance;


import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleAppearance;


@Property("几何造型-圆形")
public class GeometryRound extends Geometry
{
	private static final long serialVersionUID = 1L;
	
	/**半径*/@Property("半径")
	public int radius = 2;
	
	public ParticleAppearance cloneDisplay() {
		return this;
	}
	
	@Override
	public void render(Graphics2D g, Layer layer) {
		g.setColor(color);
		if (is_fill) {
			g.fillArc(-radius, -radius, radius << 1, radius << 1, 0, 360);
		} else {
			g.drawArc(-radius, -radius, radius << 1, radius << 1, 0, 360);
		}
	}
}
