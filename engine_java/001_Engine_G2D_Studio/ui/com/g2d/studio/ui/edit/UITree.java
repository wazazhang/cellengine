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
}
