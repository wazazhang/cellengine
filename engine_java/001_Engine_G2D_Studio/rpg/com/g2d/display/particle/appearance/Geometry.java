package com.g2d.display.particle.appearance;


import com.g2d.Color;
import com.g2d.annotation.Property;
import com.g2d.display.particle.ParticleAppearance;

public abstract class Geometry implements ParticleAppearance
{
	private static final long serialVersionUID = 1L;
	
	/**是否填充*/@Property("是否填充")
	public boolean	is_fill = true;
	
	/**颜色*/@Property("颜色")
	public Color	color = Color.WHITE;
	
}
