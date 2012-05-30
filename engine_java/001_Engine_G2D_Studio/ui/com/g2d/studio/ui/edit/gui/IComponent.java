package com.g2d.studio.ui.edit.gui;

import com.g2d.display.ui.UIComponent;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.studio.ui.edit.UITreeNode;

public interface IComponent 
{
	public void init(UIEdit edit);

	public UITreeNode getTreeNode() ;
	
	public String getName() ;
	
	public void setName(String name) ;
	
	public UIComponent asComponent();
}
