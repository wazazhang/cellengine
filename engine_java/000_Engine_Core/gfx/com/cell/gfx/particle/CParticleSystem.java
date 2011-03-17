
package com.cell.gfx.particle;

import com.cell.gfx.IGraphics;

/**
 * particles manager
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
	public CParticleSystem(int count, IParticleLauncher launcher){
		Particles = new CParticle[count];
		for(int i=0;i<count;i++){
			Particles[i] = new CParticle();
		}
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
	
	
	public int spawn(int count, int category, int x, int y, int speedx, int speedy){
		int c = 0;
		for(int i=0;i<Particles.length;i++){
			if(c>=count)break;
			if(Particles[i].isTerminate()){
				Particles[i].Category = category;
				Particles[i].X = x;
				Particles[i].Y = y;
				Particles[i].SpeedX = speedx;
				Particles[i].SpeedY = speedy;
				Particles[i].Timer = 0;
				Launcher.particleEmitted(Particles[i],i);
				c++;		
			}
		}
		Active = true;
		return c;
	}
	
	public int spawn(int count, int category, int x, int y, int speedx, int speedy, int accx, int accy){
		int c = 0;
		for(int i=0;i<Particles.length;i++){
			if(c>=count)break;
			if(Particles[i].isTerminate()){
				Particles[i].Category = category;
				Particles[i].X = x;
				Particles[i].Y = y;
				Particles[i].SpeedX = speedx;
				Particles[i].SpeedY = speedy;
				Particles[i].AccX = accx;
				Particles[i].AccY = accy;
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
	 * @param x TODO
	 * @param y TODO
	 */
	public void render(IGraphics g, int x, int y){
		if(Active){
			for(int i=0;i<Particles.length;i++){
				if(Particles[i].isTerminate()==false){
					Launcher.particleRender(g, x, y, Particles[i], i);
				}
				Particles[i].Timer++;
	       	}
		}
	}
}