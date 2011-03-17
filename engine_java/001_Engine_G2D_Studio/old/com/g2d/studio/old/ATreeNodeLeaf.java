package com.g2d.studio.old;

import java.awt.event.MouseEvent;

import javax.swing.JTree;




public class ATreeNodeLeaf<V extends AFormDisplayObjectViewer<?>> extends TreeNode<V, ATreeNodeSet<V>, ATreeNodeLeaf<V>>
{
	public ATreeNodeLeaf(String name, ATreeNodeSet<V> parent) {
		super(name, null, parent);
	}
	
	public void onDoubleClicked(JTree tree, MouseEvent e) {}
	
	public void onRightClicked(JTree tree, MouseEvent e) {}
	
	public void onSelected(JTree tree, MouseEvent e) {}
	
	public boolean isLeaf() {
		return true;
	}
	public boolean getAllowsChildren() {
		return false;
	}
	
	
	public V getViewer(){
		return user_data;
	}
}
