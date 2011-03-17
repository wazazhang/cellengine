package com.g2d.display.ui.tree;

import com.g2d.display.event.EventListener;
import com.g2d.display.ui.TreeView;
import com.g2d.display.ui.UIComponent;

public interface TreeNodeListener extends EventListener
{

	public void onTreeNodeClick(TreeNode node, UIComponent component, TreeView tree);
	
}
