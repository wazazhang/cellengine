package com.g2d.display.ui.tree;


import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.TreeView;
import com.g2d.display.ui.UIComponent;
import com.g2d.util.Drawing;

public class DefaultTreeAdapter implements TreeAdapter
{
	@Override
	public UIComponent getComponent(TreeNode node, TreeView tree) 
	{
		return new DefaultTreeNodeComponent(node);
	}
	
	public static class DefaultTreeNodeComponent extends UIComponent
	{
		public Color	text_color	= Color.WHITE;
		public int		text_anchor	= Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER ;

		public BufferedImage icon_child;
		public BufferedImage icon_group_open;
		public BufferedImage icon_group_close;
		/** 当鼠标放置在该控件上的效果 */
		public BufferedImage mouse_catched_mask;
		
		private TreeNode node;
		
		public DefaultTreeNodeComponent(TreeNode node) {
			super.setSize(100, 20);
			this.node = node;
		}
		
		public TreeNode getTreeNode() {
			return node;
		}
		
		public void render(Graphics2D g) 
		{
			super.render(g);
		
			TreeView tree = (TreeView)getParent();
			
			BufferedImage icon = null;
			
			if (node.getChildCount() == 0) {
				icon = icon_child;
			} else if ( (tree != null) && tree.isExpanded(node)) {
				icon = icon_group_close;
			} else {
				icon = icon_group_open;
			}

			g.setColor(text_color);
			if (icon != null) {
				g.drawImage(icon, 
						0, getHeight()/2-icon.getHeight()/2);
				Drawing.drawStringBorder(g, node.toString(), 
						icon.getWidth(), 0, getWidth()-icon.getWidth(), getHeight(), 
						text_anchor);
			} else {
				Drawing.drawStringBorder(g, node.toString(), 
						0, 0, getWidth(), getHeight(), 
						text_anchor);
			}
			
		}
		
		protected void onDrawMouseHover(Graphics2D g) {
			if(mouse_catched_mask==null){
				g.setColor(new Color(1,1,1,0.2f));
				g.fillRect(local_bounds);
			}else{
				g.drawImage(mouse_catched_mask, 0, 0, getWidth(), getHeight());
			}
		}
		
		@Override
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			TreeView tree = (TreeView)getParent();
			if (node.getChildCount() > 0) {
				tree.setExpand(node, !tree.isExpanded(node));
			}
			
		}
	}
}
