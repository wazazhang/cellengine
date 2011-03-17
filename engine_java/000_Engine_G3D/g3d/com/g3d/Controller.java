package com.g3d;

public interface Controller 
{

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
	public int 		getDownKeyCount();
	
	/**
	 * 检测当前帧被松开的键的个数
	 * @return
	 */
	public int 		getUpKeyCount();
	
	/**
	 *  检测当前帧鼠标有没有被按住
	 * @param button
	 * @return
	 */
	public boolean 	isMouseHold(int ... button);
	
	/**
	 *  检测当前帧鼠标被连续的按下
	 * @param freeze_time
	 * @param button
	 * @return
	 */
	public boolean 	isMouseContinuous(long freeze_time, int ... button);
	
	/**
	 *  检测当前帧鼠标有没有被按下
	 * @param button
	 * @return
	 */
	public boolean 	isMouseDown(int ... button);
	
	/**
	 * 在上次鼠标被按下后多少时间，检查鼠标被按下
	 * @param time 如果(现在时间-上次按下时间 < time)则进行判断
	 * @param button
	 * @return
	 */
	public boolean 	isMouseDoubleDown(long time, int ... button);
	
	/**
	 *  检测当前帧鼠标有没有被松开
	 * @param button
	 * @return
	 */
	public boolean 	isMouseUp(int ... button) ;
	
	/**
	 *  检测当前帧鼠标滚轮有没有向上滚
	 * @param button
	 * @return
	 */
	public boolean 	isMouseWheelUP();
	
	/**
	 *  检测当前帧鼠标滚轮有没有向下滚
	 * @param button
	 * @return
	 */
	public boolean	isMouseWheelDown();
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	public int 		getMouseX() ;
	
	/**
	 * 得到鼠标指针在stage的位置
	 * @param button
	 * @return
	 */
	public int 		getMouseY() ;
	
	

}
