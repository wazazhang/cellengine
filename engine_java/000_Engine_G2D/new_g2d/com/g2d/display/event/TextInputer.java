package com.g2d.display.event;

/**
 * 是否拥有获得所有键盘鼠标事件的焦点，
 * 一旦该控件是Stage中Picked的单位，
 * 则Root.isKeyDown等方法都返回false
 * @return
 */
public interface TextInputer {

	/**
	 * 是否拥有获得所有键盘鼠标事件的焦点，
	 * 一旦该控件是Stage中Picked的单位，
	 * 则Root.isKeyDown等方法都返回false
	 * @param key_code 检测字符是否被过滤
	 * @return true 忽略该字符低级事件
	 */
	public boolean isInput(int key_code) ;
	
}
