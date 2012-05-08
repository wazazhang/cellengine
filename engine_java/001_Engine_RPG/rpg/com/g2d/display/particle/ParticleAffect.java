package com.g2d.display.particle;

import java.io.Serializable;

public interface ParticleAffect extends Serializable
{
	public void update(float timeline_position, ParticleAffectNode particle);
}
