package com.g2d.display.ui.layout;

import java.util.Vector;

import com.g2d.display.ui.UIComponent;

/**
 * 只对一个控件进行填充到父控件
 * @author WAZA
 */
public class FillContainerLayout extends ContainerLayout
{
	@Override
	public void addLayoutComponent(UIComponent comp, Object layoutArg) {}
	
	public void layoutContainer(com.g2d.display.ui.Container parent) {
		Vector<UIComponent> childs = parent.getComonents();
		if (!childs.isEmpty()) {
			UIComponent child = childs.get(0);
			child.setLocation(0, 0);
			child.setSize(parent.getWidth(), parent.getHeight());
		}
	}
}
