package com.g2d.studio.ui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.property.ObjectPropertyListener;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.studio.ui.edit.gui.EditedCanvas;
import com.g2d.studio.ui.edit.gui.IComponent;

public class UITreeNode extends G2DTreeNode<UITreeNode> implements ObjectPropertyListener
{
	final public UIComponent display;
	final public IComponent comp;
	final public ObjectPropertyPanel opp;
	
	private BufferedImage icon;
	private UIEdit edit;
	
	public UITreeNode(IComponent comp, UIEdit edit) {
		this.display = comp.asComponent();
		this.comp = comp;
		this.edit = edit;
		this.opp = new ObjectPropertyPanel(comp, 100, 200, false); 
		this.opp.addObjectPropertyListener(this);
	}
	
	@Override
	public void onFieldChanged(Object object, Field field) {
		getIcon(true);
	}
	
	@Override
	protected ImageIcon createIcon() {
		UILayout uc = display.getCustomLayout();
		if (uc != null) {
			icon = Engine.getEngine().createImage(40, 30);
			Graphics2D g = icon.createGraphics();
			uc.render(g, 0, 0, 40, 30);
			g.dispose();
			return new ImageIcon(AwtEngine.unwrap(icon));
		}
		return null;
	}
	
	@Override
	public String getName() {
		return comp.getName() + "";
	}
	
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		edit.onSelectTreeNode(this);
	}
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new UIMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	
//	---------------------------------------------------------------------------------------------------------------

	class UIMenu extends NodeMenu<UITreeNode>
	{
		protected JMenuItem add = new JMenuItem("添加");
		
		public UIMenu(JTree tree, UITreeNode node) {
			super(node);
			super.remove(open);
			super.insert(add, 1);
			add.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == add) 
			{
				String name = JOptionPane.showInputDialog(
					AbstractDialog.getTopWindow(getInvoker()), 
					"输入名字！", "");
				if (name!=null && name.length()>0) {
					edit.createNode(node.comp, EditedCanvas.class, name);
				}
			}
			else if (e.getSource() == rename) 
			{
				
			}
			else if (e.getSource() == delete) 
			{
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				this.node.display.removeFromParent();
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}
	
}
