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


/**
 * Single Particle Node.
 * @author yifeizhang
 * @since 2006-12-5 
 * @version 1.0
 */
public class CParticle {

	// time
	public int Category = 0;
	
	/**Terminate time*/
	public int TerminateTime = -1;
	/**timer*/
	public int Timer 	= 0;
	
	// physical
	public int X 		= 0;
	public int Y 		= 0;
	public int SpeedX 	= 0;
	public int SpeedY 	= 0;
	public int AccX 	= 0;
	public int AccY 	= 0;
	
	// surface
	public int Color = 0;
	public int 		Data 		= 0;
	public Object 	Element 	= null;
	
	public boolean isTerminate(){
		return Timer > TerminateTime;
	}
	
}
