
package com.cell.gfx.particle;


/**
 * Single Particle Node.
 * @author yifeizhang
 * @since 2006-12-5 
 * @version 1.0
 */
public class CParticle {

	// time
	public int Category = 0;
	
	/**Terminate time, not equal*/
	public int TerminateTime = 0;
	/**timer*/
	public int Timer 	= 0;
	
	// physical
	public int X 		= 0;
	public int Y 		= 0;
	public int SpeedX 	= 0;
	public int SpeedY 	= 0;
	public int AccX 	= 0;
	public int AccY 	= 0;
	public int Size 	= 0;
	
	// surface
	public int Color = 0;
	public int 		Data 		= 0;
	public Object 	Element 	= null;
	
	public boolean isTerminate(){
		return Timer >= TerminateTime;
	}
	
}
