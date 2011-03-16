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
 * particles manager.
 * @author yifeizhang
 * @since 2006-12-5 
 * @version 1.0
 */
public class CParticleSystem {

	protected IParticleLauncher Launcher;
	protected CParticle[] Particles ;
	protected boolean Active = false;


	/**
	 * constract
	 * @param particle
	 * @param launcher 
	 */
	public CParticleSystem(CParticle[] particles,IParticleLauncher launcher){
		Particles = particles;
		Launcher  = launcher;
	}
	
	/**
	 * is active
	 * @return
	 */
	public boolean isActive(){
		return Active;
	}
	
	/**
	 * change particles node
	 * @param particle 
	 */
	public void changeParticles(CParticle[] particles){
		Particles = particles;
	}
	/**
	 * change particles movement's state
	 * @param launcher 
	 */
	public void changeLauncher(IParticleLauncher launcher){
		Launcher  = launcher;
	}
	
	/**
	 * get how many particles 
	 * @return 
	 */
	public int getCount(){
		return Particles.length;
	}
	
	/**
	 * spawn particles
	 * @param count
	 * @param category TODO
	 * @param x TODO
	 * @param y TODO
	 * @return 
	 */
	public int spawn(int count, int category, int x, int y){
		int c = 0;
		for(int i=0;i<Particles.length;i++){
			if(c>=count)break;
			if(Particles[i].isTerminate()){
				Particles[i].Category = category;
				Particles[i].X = x;
				Particles[i].Y = y;
				Particles[i].Timer = 0;
				Launcher.particleEmitted(Particles[i],i);
				c++;		
			}
		}
		Active = true;
		return c;
	}
	
	/**
	 * update particles state
	 */
	public void update(){
		if(Active){
			Active = false;
			for(int i=0;i<Particles.length;i++){
				Particles[i].Timer++;
				if(Particles[i].isTerminate()==false){
					Launcher.particleAffected(Particles[i],i);
					Active = true;
				}else{
					Launcher.particleTerminated(Particles[i],i);
				}
	       	}
		}
	}
	
	/**
	 * render particles 
	 * @param g 
	 */
	public void render(Graphics g){
		if(Active){
			for(int i=0;i<Particles.length;i++){
				if(Particles[i].isTerminate()==false){
					Launcher.particleRender(g, Particles[i], i);
				}
	       	}
		}
	}
}