package com.g2d.display.event;

import com.g2d.display.InteractiveObject;

/**
 * 监听被拖拽的单位在此单位上释放的事件
 * @author WAZA
 */
public interface MouseDragDropAccepter extends EventListener 
{
	/**
	 * 当有某个控件被拖拽，并在此单位上释放时发生
	 * @param object 放下的单位
	 * @param drag_drop_object 被拖拽的控件
	 */
	void onDragDropedObject(InteractiveObject object, InteractiveObject drag_drop_object);
}
