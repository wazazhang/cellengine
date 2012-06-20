package com.g2d.studio.ui.edit;

import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObject;
import com.g2d.display.InteractiveObject;
import com.g2d.display.ui.Container;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.java2d.impl.SimpleCanvas;
import com.g2d.studio.swing.G2DDragDropListener;
import com.g2d.studio.swing.G2DTree;

public class UIStage extends DisplayObjectPanel.ObjectStage implements DropTargetListener
{
	private UIEdit edit;
	
	public UIStage(UIEdit edit) 
	{
		super(Color.black);
		this.edit = edit;
	}

	@Override
	public void update() 
	{
		UITreeNode un = (UITreeNode) edit.getTree().getSelectedNode();
		if (un != null) 
		{
			DisplayObject display = un.getDisplay();
			
			if (isKeyRepeat(KeyEvent.VK_UP)) {
				display.y--;
				un.opp.refresh();
			} else if (isKeyRepeat(KeyEvent.VK_DOWN)) {
				display.y++;
				un.opp.refresh();
			} else if (isKeyRepeat(KeyEvent.VK_LEFT)) {
				display.x--;
				un.opp.refresh();
			} else if (isKeyRepeat(KeyEvent.VK_RIGHT)) {
				display.x++;
				un.opp.refresh();
			}
		}
		
	}

	@Override
	public void render(Graphics2D g) 
	{
		super.render(g);
		
	}
	
	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);

		if (edit.isToolGridEnable() && edit.isToolShowBounds()) 
		{
//			float alpha = 0.5f + (float) Math.sin(this.timer / 5.0f) / 2;
			Color c1 = new Color(1, 1, 1, 1f);
			Color c2 = new Color(0, 0, 0, 1f);
			int gw = edit.getGridSize();
			for (int x = 0; x < getWidth(); x += gw) {
				for (int y = 0; y < getHeight(); y += gw) {
					if ((x/gw) % 2 == 0 || (y/gw) % 2 == 0) {
						g.setColor(c1);
					} else {
						g.setColor(c2);
					}
					g.fillRect(x, y, 1, 1);
				}
			}
		}
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		
	}
	
	@Override
	public void dragExit(DropTargetEvent dte) {}
	
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		
	}
	
	@Override
	public void drop(DropTargetDropEvent dtde)
	{
		if (G2DTree.dragged_tree_node instanceof UITemplate) 
		{
			UITemplate ut = (UITemplate)G2DTree.dragged_tree_node;
			UITreeNode ct = edit.getSelectedUINode();
			if (ct == null) {
				ct = (UITreeNode)edit.getTree().getRoot();
			}
			try {
				UITreeNode uc = ct.createChild(ut, "");
				if (uc == null) {
					JOptionPane.showMessageDialog(edit,
							ct.getDisplay().getClass().getSimpleName()+"不能添加子节点！");
				} else {
					int tx = ct.getDisplay().screenToLocalX(dtde.getLocation().x);
					int ty = ct.getDisplay().screenToLocalY(dtde.getLocation().y);
					uc.getDisplay().setLocation(tx, ty);
					if (edit.isToolGridEnable()) {
						edit.getLayoutManager().gridPos(ct.getDisplay());
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(edit, e.getMessage());
			}
		
		}
	}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}
	
	
}
