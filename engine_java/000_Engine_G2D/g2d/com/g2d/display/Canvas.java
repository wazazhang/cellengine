package com.g2d.display;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;


public interface Canvas
{
	public Component getParent();
	
	public boolean isExit() ;
	
	public void exit();
	
	public boolean isFocusOwner();
	
	public int getUpdateIntervalMS();
	
	/**
	 * 是否在进行文本输入
	 * @return
	 */
	public boolean isOnTextInput();
	
	/**
	 * 是否由Applet进入游戏的
	 * @return
	 */
	public boolean isRootApplet() ;
	
	/**
	 * 获取当前的APPLET，如果以网页形式运行
	 * @return
	 */
	public Applet getApplet();
	
	/**
	 * 获得当前场景的后背缓冲图像
	 * @return
	 */
	public Image getVMBuffer();
	
	/**
	 * 设置默认鼠标指针图形
	 * @param cursor
	 */
	public void setDefaultCursor(AnimateCursor cursor);
	
//	/**
//	 * 设置鼠标指针
//	 * @param cursor
//	 */
//	public void setCursor(AnimateCursor cursor);
	
//	public CursorG2D getCursorG2D();
	
	/**
	 * 设置默认渲染子体
	 * @param font
	 */
	public void setDefaultFont(Font font) ;

	public Font getDefaultFont();
	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(AttributedString text) ;
//	
//	/**
//	 * 设置鼠标悬停
//	 * @param text
//	 */
//	public void setTip(String text);
	
	/**
	 * 切换场景
	 * @param stage
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(Stage stage, Object ... args) ;
	
	/**
	 * 切换场景
	 * @param stageName
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(String stageName, Object ... args) ;
	
	/**
	 * 切换场景
	 * @param stageClass
	 * @param args 传入参数，参数会在Stage.inited(Object[] args)通知stage
	 */
	public void changeStage(Class<?> stageClass, Object ... args);
	
	/***
	 * 立刻切换当前场景
	 * @param stage
	 */
	public void setStage(Stage stage);
	
	/**
	 * 设置场景象素大小
	 * @param width
	 * @param height
	 */
	public void setStageSize(int width, int height);
	
	public int getStageWidth() ;
	
	public int getStageHeight() ;
	
	public int getWidth();
	
	public int getHeight();
	
	/**
	 * 根据parentFrame的大小来设置stage，使得stage刚好包容在parentFrame里
	 * @param parentFrame
	 */
	public void fillStageSize(Container parentFrame);
	
	public Stage getStage() ;
	
//	--------------------------------------------------------------------------------
	
	/**
	 * 设置一个Window，贴在canvas的右边
	 * @param append
	 */
	public void setAppendWindow(Window append) ;
	
	/**
	 * 得到贴在canvas的右边的Window
	 */
	public Window getAppendWindow() ;
	

	/**
	 * 显示在低级stage中的高级ui
	 * @param frame
	 */
	public void setInternalFrame(Component frame) ;
	
	/**
	 * 当前在低级stage中显示的高级ui
	 * @return
	 */
	public Component getInternalFrame();
	
//	--------------------------------------------------------------------------------
//	update and transition

	/**
	 * 设置游戏更新速度
	 * @param fps
	 */
	public void setFPS(double fps);
	
	/**
	 * 得到帧延迟，单位ms
	 * @return
	 */
	public int getFrameDelay() ;
	
	/**
	 * 设置游戏更新速度(未活动窗口)
	 * @param fps
	 */
	public void setUnactiveFPS(double fps) ;
	
	/**
	 * 得到帧延迟，单位ms(未活动窗口)
	 * @return
	 */
	public int getUnactiveFrameDelay() ;

	/**
	 * 得到游戏更新速度
	 * @return
	 */
	public int getFPS();
	
	
//	public void repaint_game();
	
//	--------------------------------------------------------------------------------
//	control and input
	
	/**
	 * 检测当前帧键有没有被按住
	 * @param keycode
	 * @return
	 */
	public boolean isKeyHold(int ... keycode) ;
	
	/**
	 * 检测当前帧键有没有被按下
	 * @param keycode
	 * @return
	 */
	public boolean isKeyDown(int ... keycode);
	
	/**
	 * 检测当前帧键有没有被松开
	 * @param keycode
	 * @return
	 */
	public boolean isKeyUp(int ... keycode);
	
	/**
	 * 检测当前帧被按下的键的个数
	 * @return
	 */
	public int getDownKeyCount();
	
	/**
	 * 检测当前帧被松开的键的个数
	 * @return
	 */
	public int getUpKeyCount();
	
	/**
	 *  检测当前帧鼠标有没有被按住
	 * @param button
	 * @return
	 */
	public boolean isMouseHold(int ... button);
	
	/**
	 *  检测当前帧鼠标被连续的按下
	 * @param freeze_time
	 * @param button
	 * @return
	 */
	public boolean isMouseContinuous(long freeze_time, int ... button);
	
	/**
	 *  检测当前帧鼠标有没有被按下
	 * @param button
	 * @return
	 */
	public boolean isMouseDown(int ... button);
	
	/**
	 * 在上次鼠标被按下后多少时间，检查鼠标被按下
	 * @param time 如果(现在时间-上次按下时间 < time)则进行判断
	 * @param button
	 * @return
	 */
	public boolean isMouseDoubleDown(long time, int ... button);
	
	/**
	 *  检测当前帧鼠标有没有被松开
	 * @param button
	 * @return
	 */
	public boolean isMouseUp(int ... button) ;
	
	/**
	 *  检测当前帧鼠标滚轮有没有向上滚
	 * @param button
	 * @return
	 */
	public boolean isMouseWheelUP();
	
	/**
	 *  检测当前帧鼠标滚轮有没有向下滚
	 * @param button
	 * @return
	 */
	public boolean isMouseWheelDown();
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	public int getMouseX() ;
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	public int getMouseY() ;
	
	

}
