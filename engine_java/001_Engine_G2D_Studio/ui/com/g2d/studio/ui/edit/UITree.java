package com.g2d.studio.ui.edit;

import javax.swing.tree.TreeNode;

import com.g2d.studio.swing.G2DTree;

public class UITree extends G2DTree
{
	UIEdit edit;
	
	public UITree(UIEdit edit, UITreeNode node) {
		super(node);
		this.edit = edit;
	}
	
	@Override
	protected void onSelectChanged(TreeNode node) {
		if (node != null) {
			edit.onSelectTreeNode(((UITreeNode)node));
		} else {
			edit.onSelectTreeNode(null);
		}
	}
	
	
}
