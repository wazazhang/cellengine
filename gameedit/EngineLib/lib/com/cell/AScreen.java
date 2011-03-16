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

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;



/**
 * 子屏幕。</br>
 * 管理不同的游戏屏幕状态，比如：菜单界面、游戏界面、大厅界面、等等。每种界面只需要实现该类，而不需要去访问设备有关的Canvas。
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
abstract public class AScreen extends CObject {
//	-------------------------------------------------------------------------------------------------------
//	game refer
	
	/**游戏结束标志*/
	static public boolean ExitGame = false;
	
	/**当前的AScreen实例*/
	static public AScreen CurSubScreen = null;
	
	/**屏幕宽*/
	static public int SCREEN_WIDTH = 176;
	/**屏幕高*/
	static public int SCREEN_HEIGHT = 208;
	/**屏幕水平中心点*/
	static public int SCREEN_HCENTER = 176/2;
	/**屏幕垂直中心点*/
	static public int SCREEN_VCENTER = 208/2;

	/**游戏中的帧延迟，单位ms，比如预计游戏FPS=30，FrameDelay = 1000/30 = 33*/
	static public int FrameDelay = 25;//default fps = 50
	/**是否允许按键*/
	static public boolean KeyEnable = true;
	/**是否允许逻辑*/
	static public boolean LogicEnable = true;
	/**是否允许渲染*/
	static public boolean RenderEnable = true;
	/**是否允许切屏特效*/
	static public boolean TransitionEnable = true;

	//	------------------------------------------------------------------------------------------------------	

	/**
	 * canvas notifyHide call back </br>
	 * 游戏遇到系统暂停事件时回掉
	 */
	abstract public void notifyPause();
	/**
	 * canvas notifyShow call back</br>
	 * 游戏遇到系统恢复事件时回掉
	 */
	abstract public void notifyResume();
	/**
	 * main game logic call back</br>
	 * 游戏逻辑,执行逻辑的回掉方法，每帧在渲染前被执行一次
	 */
	abstract public void notifyLogic();
	/**
	 * main render call back : canvas paint call back</br>
	 * 游戏渲染,执行渲染的回掉方法，每帧被执行一次
	 * @param g 
	 */
	abstract public void notifyRender(Graphics g);
	/**
	 * main release call back : call back when screen is disposing </br>
	 * 游戏渲染,执行渲染的回掉方法，每帧被执行一次
	 * @param g 
	 */
	abstract public void notifyDestroy();
	//--------------------------------------------------------------------------------------------------------------------
	
	/**当前是否在进行屏幕切换*/
	static public boolean 	Transition 			= false;
	
	static private int 		TransitionMaxTime 	= 0 ;
	static private int 		TransitionTime 		= 0 ;
	static private boolean 	TransitionIn 		= false;
	static private boolean 	TransitionOut 		= false;
	
	static private String 	TransitionText[] 	= new String[]{"Loading..."};
	static private int 		TransitionCellW 	= 16;
	static private int		TransitionDelta 	= 2;

	/**
	 * 检测当前屏幕状态是否在进行屏幕切换。
	 * @return 
	 */
	static public boolean isTransition(){
		return Transition;
	}
	
	
	/**
	 * 设置当前屏幕切换状态为进入状态 
	 */
	static public void setTransitionIn(){
		Transition = true;
		TransitionMaxTime = SCREEN_WIDTH / TransitionCellW + TransitionCellW/TransitionDelta;
		TransitionTime = 0;
		TransitionIn = true;
		TransitionOut = false;
	}
	/**
	 * 设置当前屏幕切换状态为关闭状态
	 */
	static public void setTransitionOut(){
		Transition = true;
		TransitionMaxTime = SCREEN_WIDTH / TransitionCellW + TransitionCellW/TransitionDelta;
		TransitionTime = 0;
		TransitionIn = false;
		TransitionOut = true;
	}
	
	/**
	 * 当前屏幕切换状态是否在进行关闭
	 * @return 
	 */
	static public boolean isTransitionOut(){
		if(!TransitionOut || TransitionTime>TransitionMaxTime)return true;
		return false;
	}
	/**
	 * 当前屏幕切换状态是否在进行进入
	 * @return 
	 */
	static public boolean isTransitionIn(){
		if(!TransitionIn || TransitionTime>TransitionMaxTime)return true;
		return false;
	}
	
	/**
	 * 渲染切换屏幕的特效
	 * @param g
	 * @param w 最大宽度
	 * @param h 最大高度
	 */
	static public void Transition(Graphics g,int w,int h){
		TransitionTime++;
		
		if(TransitionIn && TransitionTime>TransitionMaxTime){
			Transition = false;
		}
		if(Transition==false){
			return;
		}
		
		//System.out.println("Transition Screen : " + TransitionTime);
		
		g.setClip(0, 0, w, h);
		g.setColor(0);

		int count = w/TransitionCellW+1;
		if(TransitionIn){// black -> clean
			for(int i=0;i<count;i++){
				g.fillRect(
						i*TransitionCellW , 0, 
						TransitionCellW-(TransitionTime-i)*TransitionDelta, h);
			}
		}
		if(TransitionOut){//clean -> black
			for(int i=0;i<count;i++){
				g.fillRect(
						i*TransitionCellW - (TransitionTime-i)*TransitionDelta, 0, 
						(TransitionTime-i)*TransitionDelta, h);
			}
		}
		

		// draw string
		if (TransitionText != null && (
				(TransitionIn && TransitionTime<=1) || 
				(TransitionOut && TransitionTime>=TransitionMaxTime-1)
			))
		{
//			g.setColor(0xffffffff);
//		    g.drawString(
//		    		TransitionText, 
//		    		w/2-AScreen.CurFont.stringWidth(TransitionText)/2, 
//		    		h/2-AScreen.CurFont.getHeight()/2, 
//		    		0
//		    		);
			
			int th = (getStringHeight() + 1) * TransitionText.length;
	    	int ty = SCREEN_HEIGHT/2 - th/2;
	    	
	    	for(int i=0;i<TransitionText.length;i++){
	            	drawString(g, TransitionText[i], 
	        				SCREEN_WIDTH/2 - getStringWidth(TransitionText[i])/2 , 
	        				ty + (getStringHeight() + 1) * i , 
	        				0xffffffff);
	            	
	    	}
		}

		
	}
	
//	---------------------------------------------------------------------------------
	
	static private String NextScreenClassName = null;

	/**
	 * 切换到另一屏
	 * @param screenClassName 你继承的CScreen类名字
	 */
	static public void ChangeSubScreen(String screenClassName) 
	{
		TransitionText = new String[]{"Loading..."};
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		setTransitionOut();
	}
	
	/**
	 * 切换到另一屏
	 * @param screenClassName 你继承的CScreen类名字
	 * @param transitionText 想要在切屏时显示的文字
	 */
	static public void ChangeSubScreen(String screenClassName,String transitionText) 
	{
		TransitionText = new String[]{transitionText};
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		setTransitionOut();
	}
	
	/**
	 * 切换到另一屏
	 * @param screenClassName 你继承的CScreen类名字
	 * @param transitionText 想要在切屏时显示的文字
	 */
	static public void ChangeSubScreen(String screenClassName,String[] transitionText) 
	{
		TransitionText = transitionText;
		NextScreenClassName = screenClassName;
		KeyEnable = false;
		LogicEnable = false;
		setTransitionOut();
	}
	
	/**
	 * 检测是否有屏幕切换事件，如果有的话则切换屏幕，完成数据加载。
	 */
	static public void TryChangeSubSreen() {
	    if (NextScreenClassName!=null && isTransitionOut()){
	    	try {
	    		if(CurSubScreen!=null){
	    			CurSubScreen.notifyDestroy();
			    	CurSubScreen = null;
			    	System.gc();
					Thread.sleep(1);
	    		}
	    		
				CurSubScreen = (AScreen)((Class.forName(NextScreenClassName)).newInstance());
		    	KeyEnable = true;
				LogicEnable = true;
		    	setTransitionIn();
		    	NextScreenClassName = null;
		    	
	    	} catch (Exception e) {
	    		e.printStackTrace();
				ExitGame = true;
			}
		}
	}


	

	//	-------------------------------------------------------------------------------------------------------
//	key refer
	//常量
	static final public int KEY_ANY 	= 0xffffffff;
	static final public int KEY_0 		= 1;
	static final public int KEY_1 		= 2;
	static final public int KEY_2 		= 4;
	static final public int KEY_3 		= 8;
	static final public int KEY_4 		= 16;
	static final public int KEY_5 		= 32;
	static final public int KEY_6 		= 64;
	static final public int KEY_7 		= 128;
	static final public int KEY_8 		= 256;
	static final public int KEY_9 		= 512;
	static final public int KEY_POUND 	= 1024;
	static final public int KEY_SHARP 	= 1024;
	static final public int KEY_STAR 	= 2048;
	static final public int KEY_UP 		= 4096;
	static final public int KEY_DOWN 	= 8192;
	static final public int KEY_LEFT 	= 16384;
	static final public int KEY_RIGHT 	= 32768;
	static final public int KEY_A 		= 65536;
	static final public int KEY_B 		= 131072;
	static final public int KEY_C 		= 262144;
	static final public int KEY_SEND 	= 524288;
	
	// key
	static public int KeyState = 0;//按键即时状态
	static public int KeyDownState = 0;//按键按下状态
	static public int KeyUpState = 0;//按键弹起状态

	static public boolean PointerState = false;//触摸屏状态
	static public boolean PointerDragState = false;
	static public boolean PointerDownState = false;
	static public boolean PointerUpState = false;

	static public int CurKeyState = 0;//按键即时状态快照
	static public int CurKeyDownState = 0;//按键按下状态快照
	static public int CurKeyUpState = 0;//按键弹起状态快照
	
	static public boolean CurPointerState = false;//触摸屏状态快照
	static public boolean CurPointerDragState = false;
	static public boolean CurPointerDownState = false;
	static public boolean CurPointerUpState = false;
	static public int PointerX;
	static public int PointerY;

	
	/**
	 * 清空键状态 
	 */
	static final public void clearKey(){
		KeyState = 0;
		KeyDownState = 0;
		KeyUpState = 0;
		
		PointerState = false;
		PointerDragState = false;
		PointerDownState = false;
		PointerUpState = false;

		CurKeyState = 0;
		CurKeyDownState = 0;
		CurKeyUpState = 0;
		
		CurPointerState = false;
		CurPointerDragState = false;
		CurPointerDownState = false;
		CurPointerUpState = false;
		
		PointerX = 0;
		PointerY = 0;
	}

	
	/**
	 * 查询按键并更新键状态 
	 */
	static final public void queryKey(){
		CurKeyState = KeyState;
		CurKeyDownState = KeyDownState;
		CurKeyUpState = KeyUpState;

		CurPointerState = PointerState;
		CurPointerDragState = PointerDragState;
		CurPointerDownState = PointerDownState;
		CurPointerUpState = PointerUpState;
		
		KeyDownState = 0;
		KeyUpState = 0;

		PointerDownState = false;
		PointerUpState = false;
		PointerDragState = false;
	}
	
	//方法
	/**
	 * 检测指定的键是否被按下
	 * @param TheKey 键常量
	 * @return 
	 */
	static public boolean isKeyDown(int TheKey) {
		if(!KeyEnable)return false;
		return (CurKeyDownState & TheKey) != 0;
	}

	/**
	 * 检测指定的键是否松开
	 * @param TheKey
	 * @return 
	 */
	static public boolean isKeyUp(int TheKey) {
		if(!KeyEnable)return false;
		return (CurKeyUpState & TheKey) != 0;
	}
	/**
	 * 检测指定的键是否被按住
	 * @param TheKey
	 * @return 
	 */
	static public boolean isKeyHold(int TheKey) {
		if(!KeyEnable)return false;
		return (CurKeyState & TheKey) != 0;
	}
	/**
	 * 检测触摸屏是否点住
	 * @return 
	 */
	static public boolean isPointerHold() {
		if(!KeyEnable)return false;
		return (CurPointerState);
	}
	/**
	 * 检测触摸屏是否被点下
	 * @return 
	 */
	static public boolean isPointerDown() {
		if(!KeyEnable)return false;
		return (CurPointerDownState);
	}
	/**
	 * 检测触摸屏是否松开
	 * @return 
	 */
	static public boolean isPointerUp() {
		if(!KeyEnable)return false;
		return (CurPointerUpState);
	}
	/**
	 * 检测触摸屏是否拖动
	 * @return 
	 */
	static public boolean isPointerDrag() {
		if(!KeyEnable)return false;
		return (CurPointerDragState);
	}
	/**
	 * 得到触摸点位置
	 * @return 
	 */
	static public int getPointerX(){
		return PointerX;
	}
	/**
	 * 得到触摸点位置
	 * @return 
	 */
	static public int getPointerY(){
		return PointerY;
	}

//	------------------------------------------------------------------------------------------------------
//	paint refer
	/**current font*/
	static public Font CurFont = Font.getDefaultFont();
	
	/**
	 * 清除屏幕的 clip
	 * @param g 
	 */
	static public void clearClip(Graphics g) {
		g.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	/**
	 * 填充屏幕
	 * @param g
	 * @param Color 
	 */
	static public void clearScreen(Graphics g,int Color) {
		g.setColor(Color);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	/**
	 * 清除并填充屏幕
	 * @param g
	 * @param Color 
	 */
	static public void clearScreenAndClip(Graphics g,int Color) {
		clearClip(g);
		clearScreen(g,Color);
	}
	
	/**
	 * get current width with string
	 * 得到当前字体的字符串宽度
	 * @param str
	 * @return 
	 */
	static public int getStringWidth(String str) {
		return CurFont.stringWidth(str);
	}
	/**
	 * get current font height
	 * 得到当前字符高度
	 * @return 
	 */
	static public int getStringHeight() {
		return CurFont.getHeight();
	}
	/**
	 * draw string at Graphics object with current font
	 * 画字符串
	 * @param g
	 * @param str
	 * @param x
	 * @param y
	 * @param Color 
	 */
	static public void drawString(Graphics g, String str, int x, int y,
			int Color) {
		g.setFont(CurFont);
		g.setColor(Color);
		g.drawString(str, x, y, 0);
	}
	/**
	 * take midp 1.0 support midp 2.0 drawRegion (None Flip Rotated) method 
	 * 无翻转的 drawRegion 
	 * @param g
	 * @param src
	 * @param src_x
	 * @param src_y
	 * @param w
	 * @param h
	 * @param dst_x
	 * @param dst_y 
	 */
	static public void drawRegion(Graphics g, Image src, int src_x, int src_y,
			int w, int h, int dst_x, int dst_y) {
		int cx = g.getClipX();
		int cy = g.getClipY();
		int cw = g.getClipWidth();
		int ch = g.getClipHeight();
		g.setClip(dst_x, dst_y, w, h);
		g.drawImage(src, dst_x - src_x, dst_y - src_y, Graphics.TOP
				| Graphics.LEFT);
		g.setClip(cx, cy, cw, ch);
	}
	
	
	/**
	 * 显示FPS
	 * @param g
	 * @param x
	 * @param y 
	 * @param color TODO
	 */
	static public void showFPS(Graphics g,int x,int y, int color){
		drawString(
		        g, "" 
				+ " FPS="
				+ (1000 / ((System.currentTimeMillis() - CurRealTime) == 0 ? 1 : (System.currentTimeMillis() - CurRealTime)))
				+ "/" + FrameDelay
				,
				x,y, 
				color);
		CurRealTime = System.currentTimeMillis();
	}
	static private long CurRealTime = 0 ;
}