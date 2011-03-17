package com.g2d.display.ui.tree;

import java.util.Collection;
import java.util.Vector;

public class DefaultTreeNode implements TreeNode
{
	private static final long serialVersionUID = 1L;

	private TreeNode 			parent;
	
	private Vector<TreeNode>	sub_childs = new Vector<TreeNode>();
	
	private Object				data;
	
	private int					depth_ = -1;
	
	
	
	public DefaultTreeNode(Object data) {
		this.data = data;
	}
	
	public DefaultTreeNode() {
		this.data = "tree node";
	}
	
	@Override
	public Object getData() 
	{
		return this.data;
	}
	
	@Override
	public void setData(Object data)
	{
		this.data = data;
	}
	
	
	@Override
	public String toString() {
		return data + "";
	}
	
	
	@Override	
	public void addChild(TreeNode node) 
	{
		sub_childs.add(node);

		if (node instanceof DefaultTreeNode) {
			((DefaultTreeNode) node).parent = this;
		}
	}
	
	/**
	 * 插入在现有子节点之前
	 * @param node 要插入的子节点
	 * @param before 要插入到这个节点之前
	 */
	@Override	
	public void insertChild(TreeNode node, TreeNode before)
	{
		int index = sub_childs.indexOf(before);
		
		if (index >= 0)
		{		
			sub_childs.insertElementAt(node, index);
			
			if (node instanceof DefaultTreeNode) {
				((DefaultTreeNode) node).parent = this;
			}	
		}
	}
	
	@Override	
	public boolean removeChild(TreeNode node)
	{
		if (sub_childs.remove(node))
		{
			((DefaultTreeNode)node).parent = null;
			
			return true;
		}
		
		return false;
	}
	
	@Override	
	public void removeAllChilds()
	{
		for ( TreeNode node : sub_childs )
		{
			((DefaultTreeNode)node).parent = null;
		}
		
		sub_childs.removeAllElements();
	}	
	
	@Override
	public Collection<TreeNode> children() {
		return sub_childs;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return sub_childs.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return sub_childs.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return sub_childs.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getDepth() 
	{
		return this.depth_;
	}

	@Override
	public void setDepth(int depth) 
	{
		this.depth_ = depth;
	}
	
}
