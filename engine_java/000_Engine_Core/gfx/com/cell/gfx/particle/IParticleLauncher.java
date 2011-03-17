
package com.cell.gfx.particle;

import com.cell.gfx.IGraphics;


/**
 * Particle Launcher.</br>
 * do something what particle movement. 
 * @author yifeizhang
 * @since 2006-12-5 
 * @version 1.0
 */
public interface IParticleLauncher {
	/**
	 * Initial Emitted Particle.
	 * @param particle
	 * @param id 
	 */
	public void particleEmitted(CParticle particle, int id);

	/**
	 * when the Particle Terminated.
	 * @param particle
	 * @param id 
	 */
	public void particleTerminated(CParticle particle, int id);

	/**
	 * Affected the Particle. 
	 * @param particle
	 * @param id 
	 */
	public void particleAffected(CParticle particle, int id);

	/**
	 * Render the Particle.
	 * @param g
	 * @param x TODO
	 * @param y TODO
	 * @param particle
	 * @param id 
	 * @param x 
	 * @param y 
	 */
	public void particleRender(IGraphics g, int x, int y, CParticle particle, int id);

}
