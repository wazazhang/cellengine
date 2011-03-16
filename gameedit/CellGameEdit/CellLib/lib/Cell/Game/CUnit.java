/*
 * ÕÅÒí·É
 * Created on 2005-7-29
 *
 */
package Cell.Game;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import Cell.CObject;

/**
 * @author ÕÅÒí·É
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CUnit extends CObject
{

	protected CWorld world = null;
	
	static public int cycNum(int value,int d,int max){
		value += d;
		if(value>=0){
			return value%max;
		}else{
			return (max+value%max)%max;
		}
	}

	
	static public void drawRegion(Graphics g, Image src, 
			int src_x, int src_y, int w, int h, 
			int dst_x, int dst_y) {
		int cx = g.getClipX();
		int cy = g.getClipY();
		int cw = g.getClipWidth();
		int ch = g.getClipHeight();
    	g.setClip(dst_x,dst_y,w,h);
    	g.drawImage(src, dst_x-src_x, dst_y-src_y, (int)(Graphics.TOP | Graphics.LEFT));
    	g.setClip(cx,cy,cw,ch);
	}
	//---------------------------------------------------------------------------------------------
	 
	/** Unique ID */
	public int ID;// unique id
	public int State;
	public int NextState;
	public boolean ForceStateChange;
	
	public CMsg onMsg;

	public void sendMsg(int state, CUnit receiver) {
		CMsg msg = new CMsg();
		msg.state = state;
		msg.sender = this;
		msg.receiver = receiver;
		msg.deliveryTime = 0;

		MsgPool.addElement(msg);
	}

	public void sendDelayedMsg(int state, int delay, CUnit receiver) {
		CMsg msg = new CMsg();
		msg.state = state;
		msg.sender = this;
		msg.receiver = receiver;
		msg.deliveryTime = delay;

		MsgPool.addElement(msg);
	}

	//---------------------------------------------------------------------------------------------
	static private Vector MsgPool = new Vector();
	
	static public void routeMsg() {
		for (int i = MsgPool.size() - 1; i >= 0; i--) {
			if (((CMsg) (MsgPool.elementAt(i))).deliveryTime <= 0) {
				if (((CMsg) (MsgPool.elementAt(i))).receiver.onMsg == null) {
					((CMsg) (MsgPool.elementAt(i))).receiver.onMsg = ((CMsg) (MsgPool
							.elementAt(i)));
				} else {
					println("Wanning! failed to Send Mssage : Unit MsgPool full !");
				}
				MsgPool.removeElementAt(i);
			} else {
				((CMsg) (MsgPool.elementAt(i))).deliveryTime--;
			}
		}
	}


}

