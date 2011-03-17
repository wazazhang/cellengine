package com.g2d.display.event;

import com.g2d.display.DisplayObject;
import com.g2d.display.InteractiveObject;

/**
 * 监听被鼠标拖拽的事件
 * @author WAZA
 */
public interface MouseDragDropListener extends EventListener 
{
	/**
	 * 当该控件被鼠标开始拖拽时发生
	 * @param object 被拖拽的单位
	 */
	void onMouseStartDragDrop(InteractiveObject object);
	
	/**
	 * 当该控件被鼠标放下拖拽时发生
	 * @param object 被拖拽的单位
	 * @param drop_end_object 放下时，接触到的单位
	 */
	void onMouseStopDragDrop(InteractiveObject object, DisplayObject drop_end_object);
}
