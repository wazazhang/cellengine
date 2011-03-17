package com.g2d.studio.old;

import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;

public abstract class TreeNode<D, P extends javax.swing.tree.MutableTreeNode, C extends javax.swing.tree.TreeNode> implements javax.swing.tree.MutableTreeNode
{
	protected Vector<C> 	childs = new Vector<C>();
	public String			name;
	public String			path;
	public D				user_data;
	public P				parent;
	
	public TreeNode(String name, D data, P parent) 
	{
		this.name		= name;
		this.parent		= parent;
		this.user_data	= data;
		if (parent instanceof TreeNode) {
			this.path = ((TreeNode)parent).path + "/" + name;
		}else{
			this.path = name;
		}
		System.out.println("TreeNode : " + path);
	}
	
	abstract public void onDoubleClicked(JTree tree, MouseEvent e);
	
	abstract public void onRightClicked(JTree tree, MouseEvent e);

	abstract public void onSelected(JTree tree, MouseEvent e);
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public Vector<C> getChilds() {
		return childs;
	}
	
	@Override
	public String toString() {
		return getID();
	}
	
	public String getID(){
		return name;
	}

	public C getChild(String key)
	{
		for (C t : childs) {
			if (t.toString().equals(key)) {
				return t;
			}
		}
		return null;
	}

	public Enumeration<C> children() {
		return childs.elements();
	}
	
	public C getChildAt(int childIndex) {
		return childs.elementAt(childIndex);
	}
	
	public int getChildCount() {
		return childs.size();
	}
	
	public int getIndex(javax.swing.tree.TreeNode node) {
		return childs.indexOf(node);
	}
	
	public P getParent() {
		return parent;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public boolean isLeaf() {
		return false;
	}
	public boolean getAllowsChildren() {
		return true;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public void addChild(C child) {
		childs.addElement(child);
	}
	
	public void insert(MutableTreeNode child, int index) {
		childs.insertElementAt((C)child, index);
	}

	public void remove(int index) {
		childs.remove(index);
	}

	public void remove(MutableTreeNode node) {
		childs.remove(node);
	}

	public void removeFromParent() {
		if (parent!=null) {
			parent.remove(this);
		}
	}

	public void setParent(MutableTreeNode newParent) {
		parent = (P)newParent;
	}

	public void setUserObject(Object object) {
		user_data = (D)object;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	
	
}
