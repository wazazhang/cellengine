
/*�1�7
 * Created on 2005-7-29
 *
 */
package com.cell.gfx.game;

import com.cell.CObject;
import com.cell.gfx.AScreen;


/**
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CUnit extends CObject
{
	public CWorld world 			= null;

	public boolean Visible 			= true;
	
	public boolean Active 			= true; 
	
	public int BackColor 			= 0xff00ff00;
	
	public boolean IsDebug			= false;
	
	

	static public void tickTimer() {
		AScreen.tickTimer();
	}
	static public void resetTimer() {
		AScreen.resetTimer();
	}
	static public int getTimer() {
		return AScreen.getTimer();
	}
}

