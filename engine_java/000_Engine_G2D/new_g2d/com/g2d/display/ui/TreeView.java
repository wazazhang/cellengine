package com.g2d.display.ui;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.ui.event.ActionEvent;
import com.g2d.display.ui.event.ActionListener;
import com.g2d.display.ui.tree.DefaultTreeAdapter;
import com.g2d.display.ui.tree.TreeAdapter;
import com.g2d.display.ui.tree.TreeNode;
import com.g2d.display.ui.tree.TreeNodeListener;

public class TreeView extends Container
{
	final private TreeAdapter	adapter;
	
	final private TreeNodeActionListener
								action_listener = new TreeNodeActionListener();
	
	final private HashSet<TreeNodeListener> 
								tree_node_listeners = new HashSet<TreeNodeListener>();
	
//	------------------------------------------------------------------------------------------
	
	/**父节点x和子节点x相差的距离*/
	private int			border_w	= 20;
	/**没行间距*/
	private int			border_h	= 2;
	
	private TreeNode	root;
	
	private Hashtable<TreeNode, UIComponent> 
						nodes = new Hashtable<TreeNode, UIComponent>();
	
	private HashSet<TreeNode>
						expaneds = new HashSet<TreeNode>();
	
	private boolean 	display_root_node = true;
	
//	------------------------------------------------------------------------------------------
	
	public TreeView() 
	{
		this(null, new DefaultTreeAdapter());
	}

	public TreeView(TreeNode root) 
	{
		this(root, new DefaultTreeAdapter());
	}
	
	public TreeView(TreeAdapter adapter) 
	{
		this(null, adapter);
	}
	
	public TreeView(TreeNode root, TreeAdapter adapter) 
	{
		this.adapter = adapter;
		this.enable_drag = false;
		this.enable_drag_resize = false;
		this.setTreeRoot(root);
	}
	
	@Override
	public void setSize(int w, int h) 
	{
		super.setMinimumSize(w, h);
		this.refresh();
	}
	
	@Override
	public void setMinimumSize(int width, int height) 
	{
		int ori_width = this.getMinimumWidth();
		int ori_height = this.getMinimumHeight();
		
		super.setMinimumSize(width, height);
		
		int new_width = this.getMinimumWidth();
		int new_height = this.getMinimumHeight();
		
		if ( (ori_width!=new_width) || (ori_height!=new_height) )
			this.refresh();
	}
	
//	------------------------------------------------------------------------------------------
	
	synchronized public void setTreeRoot(TreeNode root) 
	{
		this.root = root;
		this.expaneds.clear();
		this.nodes.clear();
		this.expaneds.add(root);
		this.initTreeNode(root, 0);
		this.refresh();
	}
	
	synchronized public boolean addChildTreeNode(TreeNode node, TreeNode addto_node)
	{
		if (this.nodes.containsKey(addto_node))
		{
			int addto_node_depth = addto_node.getDepth();
			
			if (addto_node_depth >= 0)
			{
				addto_node.addChild(node);
				this.initTreeNode(node, addto_node_depth+1);
				
				return true;
			}
		}
		
		return false;
	}
	
	synchronized public boolean addSiblingTreeNode(TreeNode node, TreeNode addto_node)
	{
		if (addto_node == root)
			return false;
		
		if (this.nodes.containsKey(addto_node))
		{
			int addto_node_depth = addto_node.getDepth();
			
			if (addto_node_depth >= 0)
			{
				TreeNode parent = addto_node.getParent();
				
				if (this.nodes.containsKey(parent))
				{
					int parent_depth = parent.getDepth();
					
					if (parent_depth >= 0)
					{
						parent.insertChild(node, addto_node);
						this.initTreeNode(node, parent_depth+1);
						
						return true;						
					}
				}
			}
		}
		
		return false;		
	}
	
	protected void initTreeNode(TreeNode node, int depth)
	{
		UIComponent ui = adapter.getComponent(node, this) ;
		ui.addEventListener(action_listener);
		this.nodes.put(node, ui);
		node.setDepth(depth);
		if (node.getChildCount() > 0) {
			for (int cr = 0; cr < node.getChildCount(); cr++) {
				TreeNode cn = node.getChildAt(cr);
				initTreeNode(cn, depth + 1);
			}
		}
	}

	synchronized public void refresh() 
	{
		DisplayObject focus = getFocus();
		removeChilds(nodes.values());
		if (root != null) {
			resetTreeNode(root, 0, new AtomicInteger(border_h), new AtomicInteger(border_h));
		}
		int mw = getMinimumWidth();
		int mh = getMinimumHeight();
		for (UIComponent ui : nodes.values()) {
			if (contains(ui)) {
				mw = Math.max(mw, ui.getX() + ui.getWidth()  + border_h);
				mh = Math.max(mh, ui.getY() + ui.getHeight() + border_h);
			}
		}
		if (focus != null) {
			focus(focus);
		}
		super.setSize(mw, mh);
	}
	

	void resetTreeNode(TreeNode node, int deep, AtomicInteger x, AtomicInteger y) 
	{
		UIComponent ui = nodes.get(node);
		boolean tab_width = true;
		if (deep != 0 || display_root_node) {
			this.addComponent(ui, x.get(), y.get());
			y.addAndGet(ui.getHeight() + border_h);
		} else {
			tab_width = false;
		}
		
		if (node.getChildCount() > 0) {
			if (isExpanded(node)) {
				try {
					if (tab_width) x.addAndGet(border_w);
					for (int cr = 0; cr < node.getChildCount(); cr++) {
						TreeNode cn = node.getChildAt(cr);
						resetTreeNode(cn, deep + 1, x, y);
					}
				} finally {
					if (tab_width) x.addAndGet(-border_w);
				}
			}
		}
	}
	
	
//	------------------------------------------------------------------------------------------

	public TreeNode getTreeRoot() 
	{
		return root;
	}
	
//	------------------------------------------------------------------------------------------
	
	public boolean isExpanded(TreeNode node) 
	{
		return expaneds.contains(node);
	}
	
	public void setExpand(TreeNode node, boolean expand) 
	{
		if (expand) {
			expaneds.add(node);
		} else {
			expaneds.remove(node);
		}
		refresh();
	}

	public void expandAll(boolean expand) 
	{
		if (expand) {
			expaneds.addAll(nodes.keySet());
		} else {
			expaneds.clear();
		}
		refresh();
	}
	
	public void setDisplayRootNode(boolean show) 
	{
		if (this.display_root_node != show) {
			this.display_root_node = show;
			refresh();
		}
	}
	
//	------------------------------------------------------------------------------------------
	
	@Override
	public void addEventListener(EventListener listener) 
	{
		super.addEventListener(listener);
		if (listener instanceof TreeNodeListener) {
			tree_node_listeners.add((TreeNodeListener)listener);
		}
	}
	
	@Override
	public void removeEventListener(EventListener listener) 
	{
		super.removeEventListener(listener);
		if (listener instanceof TreeNodeListener) {
			tree_node_listeners.remove((TreeNodeListener)listener);
		}
	}
	
	private TreeNode getTreeNode(UIComponent comp) 
	{
		for (TreeNode tn : nodes.keySet()) {
			UIComponent ui = nodes.get(tn);
			if (ui == comp) {
				return tn;
			}
		}
		return null;
	}
	
	class TreeNodeActionListener implements ActionListener 
	{
		@Override
		public void itemAction(UIComponent item, ActionEvent event) 
		{
			for (TreeNodeListener tnl : tree_node_listeners) {
				TreeNode tn = getTreeNode(item);
				tnl.onTreeNodeClick(tn, item, TreeView.this);
			}
		}
	}
	
}
