package com.g2d.display.ui.tree;

import com.g2d.display.ui.TreeView;
import com.g2d.display.ui.UIComponent;

public interface TreeAdapter 
{
	public UIComponent getComponent(TreeNode node, TreeView tree);
	
}
