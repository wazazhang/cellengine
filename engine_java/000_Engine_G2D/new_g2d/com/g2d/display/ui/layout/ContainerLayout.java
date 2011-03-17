package com.g2d.display.ui.layout;

import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;

public abstract class ContainerLayout 
{
	/**
	 *  布置指定容器。 
	 * @param parent
	 */
	abstract public void layoutContainer(Container parent) ;
	
	/**
	 * 如果布局管理器使用每组件字符串，则将组件 comp 添加到布局，并将它与 name 指定的字符串关联。 
	 * @param name
	 * @param comp
	 */
	abstract public void addLayoutComponent(UIComponent comp, Object layout_arg) ;

}
