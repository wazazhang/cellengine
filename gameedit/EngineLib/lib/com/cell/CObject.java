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
package com.cell;

import java.util.Random;

/**
 * base class object, all other class entends this </br>
 * 基础类，库中所有其他类从该类继承
 * @author WAZA
 * @since 2006-11-28
 * @version 1.0
 */
public class CObject {

	// ------------------------------------------------------
	/**DEBUG dip 开关*/
	static public boolean IsDebug = false;
	
	// ------------------------------------------------------

	/**公共随机数*/
	static public Random Random = new Random();
	
	//-------------------------------------------------------------------------------------------------------------------------
	static private int Timer = 1;
	
	/**
	 * tick frame timer
	 * 增加当前帧计数器
	 */
	static public void tickTimer() {
		Timer++;
	}
	/**
	 * reset frame timer
	 * 帧计数器清0
	 */
	static public void resetTimer() {
		Timer = 1;
	}
	/**
	 * get current frame timer
	 * 得到当前帧计数器值 
	 * @return 
	 */
	static public int getTimer() {
		return Timer;
	}
	
//	 ------------------------------------------------------

	/**
	 * debug console print, System.out.print();</br>
	 * 等同于System.print
	 * @param str 
	 */
	static public void print(String str) {
//#ifdef _DEBUG
			System.out.print(str);
//#endif
	}

	/**
	 * debug console println, System.out.println();</br>
	 * 等同于System.out.println
	 * @param str 
	 */
	static public void println(String str) {
//#ifdef _DEBUG
			System.out.println(str);
//#endif
	}

}
