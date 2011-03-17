package com.g2d.display.particle;

import com.cell.math.Vector;

public interface ParticleAffectNode extends Vector
{
	public float	getSize();
	public float	getSpin();
	public float	getAlpha();

	public Vector	getSpeed();
	public Vector	getAcc();
	public float	getDamp();
}
