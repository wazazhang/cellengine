package com.g2d.studio.old.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.g2d.Tools;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.res.Res;

public class G2DTree extends JTree
{
	private static final long serialVersionUID = 1L;

	public G2DTree(DefaultMutableTreeNode tree_root) 
	{
		super(tree_root);
		this.setCellRenderer(new TreeRender());
		this.addMouseListener(new TreeMouseListener());
		this.addTreeSelectionListener(new TreeSelect());
	}
	
	public G2DTreeNode<?> getSelectedNode() {
		Object value = getLastSelectedPathComponent();
		if (value instanceof G2DTreeNode<?>){
			G2DTreeNode<?> node = ((G2DTreeNode<?>)value);
			return node;
		}
		return null;
	}

//	----------------------------------------------------------------------------------------------------------------------------
	
	static public G2DTreeNode<?> getNode(MutableTreeNode root, String ... path)
	{
		MutableTreeNode node = root;
		for (String name : path) {
			node = getNode(node, name);
			if (node == null) {
				return null;
			}
		}
		return (G2DTreeNode<?>)node;
	}
	
	@SuppressWarnings("unchecked")
	static public G2DTreeNode getNode(MutableTreeNode root, String name) 
	{
		try{
			Enumeration files = root.children();
			while(files.hasMoreElements()) {
				G2DTreeNode node = (G2DTreeNode)files.nextElement();
				if (node.getName().equals(name)) {
					return node;
				}
			}
		}catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	
	class TreeRender extends DefaultTreeCellRenderer
	{
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTreeCellRendererComponent(
				JTree tree, 
				Object value,
				boolean sel, 
				boolean expanded, 
				boolean leaf,
				int row,
				boolean hasFocus) 
		{
			Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus);
			if (value instanceof G2DTreeNode<?>){
				G2DTreeNode<?> node = ((G2DTreeNode<?>)value);
				ImageIcon aicon = node.getIcon(false);
				if (aicon != null) {
					setIcon(aicon);
					comp.setSize(aicon.getIconWidth(), aicon.getIconHeight());
				}
			}
			return comp;
		}		
	}
	
	
	class TreeSelect implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e) {
			G2DTree tree = G2DTree.this;
			Object value = getLastSelectedPathComponent();
			if (value instanceof G2DTreeNode<?>){
				G2DTreeNode<?> node = ((G2DTreeNode<?>)value);
				node.onSelected(tree);
			}			
		}
	}
	
	class TreeMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			G2DTree tree = G2DTree.this;
			int selRow = getRowForLocation(e.getX(), e.getY());
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			
			if (selRow != -1) 
			{
				Object obj = selPath.getLastPathComponent();

				if (obj instanceof G2DTreeNode<?>) 
				{
					G2DTreeNode<?> node = (G2DTreeNode<?>)obj;
					
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (e.getClickCount() == 1) {
							node.onClicked(tree, e);
						}
						else if (e.getClickCount() == 2) {
							node.onDoubleClicked(tree, e);
						}
					}
					else if (e.getButton() == MouseEvent.BUTTON3){
						node.onRightClicked(tree, e);
					}
				}
			}
		}
		
		
	}
	
	
}
