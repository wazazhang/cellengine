package com.g2d.studio.old;

import java.awt.event.MouseEvent;

import javax.swing.JTree;


public class ATreeNodeSet<V extends AFormDisplayObjectViewer<?>> extends TreeNode<Object, ATreeNodeGroup<V>, ATreeNodeLeaf<V>>
{
	public ATreeNodeSet(String name, ATreeNodeGroup<V> parent) 
	{
		super(name, null, parent);
	}
	

	@Override
	public void onDoubleClicked(JTree tree, MouseEvent e) {
		parent.onParentDoubleClicked(tree, this, e);
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		parent.onParentRightClicked(tree, this, e);
	}
	
	@Override
	public void onSelected(JTree tree, MouseEvent e) {
		parent.onParentSelected(tree, this, e);
	}
	

}
