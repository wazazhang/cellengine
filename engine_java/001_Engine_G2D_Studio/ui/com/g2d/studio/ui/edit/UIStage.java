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
import com.g2d.display.DisplayObject;
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
			DisplayObject display = un.display;
			
			if (getRoot().isKeyDown(KeyEvent.VK_UP)) {
				display.y--;
				un.opp.refresh();
			} else if (getRoot().isKeyDown(KeyEvent.VK_DOWN)) {
				display.y++;
				un.opp.refresh();
			} else if (getRoot().isKeyDown(KeyEvent.VK_LEFT)) {
				display.x--;
				un.opp.refresh();
			} else if (getRoot().isKeyDown(KeyEvent.VK_RIGHT)) {
				display.x++;
				un.opp.refresh();
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
			UITreeNode uc = ct.createChild(ut.uiType, ut.name);
			if (uc == null) {
				JOptionPane.showMessageDialog(edit,
						ct.display.getClass().getSimpleName()+"不能添加子节点！");
			} else {
				int tx = ct.display.screenToLocalX(dtde.getLocation().x);
				int ty = ct.display.screenToLocalY(dtde.getLocation().y);
				uc.display.setLocation(tx, ty);
			}
		}
	}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}
	
	
}
