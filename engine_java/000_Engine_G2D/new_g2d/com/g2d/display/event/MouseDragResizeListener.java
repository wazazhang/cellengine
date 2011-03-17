package com.g2d.display.event;

import com.g2d.display.InteractiveObject;

/**
 * 监听控件鼠标拖拽的尺寸变化
 * @author WAZA
 */
public interface MouseDragResizeListener extends EventListener 
{
	/**
	 * 当该控件被鼠标开始拖动以改变大小时发生
	 * @param object 被拖动的单位
	 * @param drag_type 拖动的类型 
	 * @see InteractiveObject.getDragDirect(Rectangle bounds, int bs, int dx, int dy)
	 */
	public void onDragResizeStart(InteractiveObject object, int drag_type);
	
	/**
	 * 当该控件被鼠标拖动完成改变大小时发生
	 * @param object 被拖动的单位
	 */
	public void onDragResizeEnd(InteractiveObject object);
	
}
