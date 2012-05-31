package com.g2d.studio.ui.edit;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.swing.G2DTree;

public class UITree extends G2DTree
{
	UIEdit edit;
	
	public UITree(UIEdit edit, UITreeNode node) {
		super(node);
		this.edit = edit;
		this.setDragEnabled(true);
	}
	
	@Override
	protected void onSelectChanged(TreeNode node) {
		if (node != null) {
			edit.onSelectTreeNode(((UITreeNode)node));
		} else {
			edit.onSelectTreeNode(null);
		}
	}
	
	@Override
	public String convertValueToText(Object value, boolean selected,
			boolean expanded, boolean leaf, int row, boolean hasFocus) {
		if (value instanceof UITreeNode) {
			UITreeNode node = (UITreeNode)value;
			StringBuffer sb = new StringBuffer();
			sb.append("<html><body>");
			sb.append("<p>");
			sb.append(node.getName());
			sb.append("<font color=0000ff> - " + node.display.getClass().getSimpleName() + "</font>");
			sb.append("</p>");
			sb.append("</body></html>");
			return sb.toString();
		}
		return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
	}
	
	protected boolean checkDrag(DropTarget evt_source, Transferable trans, 
			Object src, Object dst, int position) {
		if (super.checkDrag(evt_source, trans, src, dst, position)) {
//			UITreeNode tsrc	= (UITreeNode)src;
//			UITreeNode tdst	= (UITreeNode)dst;
//			if (tdst.display instanceof Container) {
				return true;
//			}
		}
		return false;
	}
	
	@Override
	protected void onDragDropChanged(
			MutableTreeNode src_node,
			MutableTreeNode dst_node, 
			MutableTreeNode src_parent,
			MutableTreeNode dst_parent)
	{
		UITreeNode src_tnode = (UITreeNode)src_node;
		UITreeNode dst_tnode = (UITreeNode)dst_node;
		UITreeNode src_tparent = (UITreeNode)src_parent;
		UITreeNode dst_tparent = (UITreeNode)dst_parent;
		
		if (src_tnode.getParent() != src_tparent) {
			src_tnode.display.removeFromParent();
			src_tnode.display.x = 0;
			src_tnode.display.y = 0;
			Container comp = (Container)((UITreeNode)src_tnode.getParent()).display;
			comp.addComponent(src_tnode.display);
		}
		
	}
}
