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


import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;





//import javax.microedition.lcdui.game.GameCanvas;
//import javax.microedition.lcdui.game.*;

/**
 * 主Canvas，整个MIDLET只存在这一个Canvas
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CCanvas20 extends Canvas{
	/**
	 * 构造函数 
	 */
	public CCanvas20() {

		System.out.println("profiles      : " + System.getProperty("microedition.profiles"));
		System.out.println("configuration : " + System.getProperty("microedition.configuration"));
		System.out.println("locale        : " + System.getProperty("microedition.locale"));
		System.out.println("platform      : " + System.getProperty("microedition.platform"));
		System.out.println("encoding      : " + System.getProperty("microedition.encoding"));
		if (this.hasRepeatEvents()) System.out.println("support Hold Key !");
		if (this.isDoubleBuffered()) System.out.println("support Double Buffered !");
		if (this.hasPointerEvents()) System.out.println("support Pointer Events !");
		if (this.hasPointerMotionEvents()) System.out.println("support Pointer Dragged !");

//		CScreen.SCREEN_WIDTH = getWidth();
//		CScreen.SCREEN_HEIGHT = getHeight();
//		CScreen.SCREEN_HCENTER = getWidth()/2;
//		CScreen.SCREEN_VCENTER = getHeight()/2;
//		CScreen.println("Screen W = " + CScreen.SCREEN_WIDTH);
//		CScreen.println("Screen H = " + CScreen.SCREEN_HEIGHT);
	}
	
	protected void sizeChanged(int w, int h) {
		AScreen.SCREEN_WIDTH = w;
		AScreen.SCREEN_HEIGHT = h;
		AScreen.SCREEN_HCENTER = w/2;
		AScreen.SCREEN_VCENTER = h/2;
		AScreen.println("Change Screen W = " + w);
		AScreen.println("Change Screen H = " + h);
	}
	
	//-------------------------------------------------------------------------------------
	/**键映射方法，不同的设备映射到相同的数值*/
	static final public int getKeyValue(int keyCode) {
		switch (keyCode) {
			case Canvas.KEY_NUM0 :
				return AScreen.KEY_0;
			case Canvas.KEY_NUM1 :
				return AScreen.KEY_1;
			case Canvas.KEY_NUM2 :
				return AScreen.KEY_2;
			case Canvas.KEY_NUM3 :
				return AScreen.KEY_3;
			case Canvas.KEY_NUM4 :
				return AScreen.KEY_4;
			case Canvas.KEY_NUM5 :
				return AScreen.KEY_5;
			case Canvas.KEY_NUM6 :
				return AScreen.KEY_6;
			case Canvas.KEY_NUM7 :
				return AScreen.KEY_7;
			case Canvas.KEY_NUM8 :
				return AScreen.KEY_8;
			case Canvas.KEY_NUM9 :
				return AScreen.KEY_9;
			case Canvas.KEY_POUND :
				return AScreen.KEY_POUND;
			case Canvas.KEY_STAR :
				return AScreen.KEY_STAR;
			case -1 :
			case 1 :
				return AScreen.KEY_UP;
			case -2 :
			case 6 :
				return AScreen.KEY_DOWN;
			case -3 :
			case 2 :
				return AScreen.KEY_LEFT;
			case -4 :
			case 5 :
				return AScreen.KEY_RIGHT;
			case -6 :
			case 21 :
				return AScreen.KEY_A;
			case -7 :
			case 22 :
				return AScreen.KEY_B;
			case -5 :
			case 20 :
				return AScreen.KEY_C;
			default :
				return 0;
		}
	}
	
	final protected void keyPressed(int keyCode) {
		int Code = getKeyValue(keyCode);
		AScreen.KeyDownState |= Code;
		AScreen.KeyState |= Code;
	}

	final protected void keyReleased(int keyCode) {
		int Code = getKeyValue(keyCode);
		AScreen.KeyUpState |= Code;
		AScreen.KeyState &= (~Code);
	}

	final protected void pointerPressed(int x, int y) {
		AScreen.PointerState = true;
		AScreen.PointerDownState = true;
		AScreen.PointerDragState = false;
		AScreen.PointerX = x;
		AScreen.PointerY = y;
	}

	final protected void pointerReleased(int x, int y) {
		AScreen.PointerState = false;
		AScreen.PointerUpState = true;
		AScreen.PointerDragState = false;
		AScreen.PointerX = x;
		AScreen.PointerY = y;
	}

	final protected void pointerDragged(int x, int y) {
		AScreen.PointerDragState = true;
		AScreen.PointerX = x;
		AScreen.PointerY = y;
	}



	//-------------------------------------------------------------------------------------------------

	final protected void paint(Graphics g) {
		AScreen.queryKey();
		
		AScreen.TryChangeSubSreen();
		
		if(AScreen.CurSubScreen!=null){
			if(AScreen.LogicEnable){
				AScreen.CurSubScreen.notifyLogic();
			}
			if(AScreen.RenderEnable){
				AScreen.CurSubScreen.notifyRender(g);
			}
			if(AScreen.TransitionEnable==false){
				AScreen.Transition = false;
			}
		}
		
		AScreen.Transition(g,getWidth(),getHeight());
		

	}

	//-----------------------------------------------------------------------------------------------


	protected void hideNotify() {
		if(AScreen.CurSubScreen!=null){
			if(AScreen.LogicEnable){
				AScreen.CurSubScreen.notifyPause();
			}
		}
	}
	
	protected void showNotify() {
		if(AScreen.CurSubScreen!=null){
			if(AScreen.LogicEnable){
				AScreen.CurSubScreen.notifyResume();
			}
		}
	
	}

//	--------------------------------------------------------------------------------------------------

	
}


