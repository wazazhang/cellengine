package com.g2d.display.ui.layout;

import java.util.Vector;

import com.g2d.display.ui.UIComponent;

/**
 * 只对一个控件进行填充到父控件
 * @author WAZA
 */
public class ListContainerLayout extends ContainerLayout
{
	final int border_size;
	final int height;
	
	public ListContainerLayout(int border_size, int height) {
		this.border_size	= Math.max(0, border_size);
		this.height			= Math.max(1, height);
	}
	
	@Override
	public void addLayoutComponent(UIComponent comp, Object layoutArg) {}
	
	public void layoutContainer(com.g2d.display.ui.Container parent) {
		Vector<UIComponent> childs = parent.getComonents();
		int sx = border_size;
		int sy = border_size;
		for (UIComponent child : childs) {
			child.setBounds(sx, sy, parent.getWidth()-(border_size<<1), height);
			sy += child.getHeight() + border_size;
		}
		parent.setSize(parent.getWidth(), sy);
	}
}
