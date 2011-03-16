/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.particle;

import javax.microedition.lcdui.Graphics;

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
	 * @param particle
	 * @param id 
	 * @param x 
	 * @param y 
	 */
	public void particleRender(Graphics g, CParticle particle, int id);

}
