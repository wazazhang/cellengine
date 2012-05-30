package com.g2d.studio.ui.edit;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.g2d.BufferedImage;
import com.g2d.Color;
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
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UERoot;


public class UITreeNode extends G2DTreeNode<UITreeNode> 
implements ObjectPropertyListener
{
	final public UIComponent display;
	final public ObjectPropertyPanel opp;
	
	private BufferedImage icon;
	private UIEdit edit;
	
	public UITreeNode(UIEdit edit, Class<?> type, String name) 
	{
		this.edit = edit;
		
		DisplayAdapter adapter = new DisplayAdapter();
		this.display = createDisplay(type);
		this.display.name = name;
		this.display.editDraw = adapter;
		this.display.enable = true;
		this.display.enable_drag = true;
		this.display.enable_drag_resize = true;
		this.display.enable_key_input = true;
		this.display.enable_focus = true;
		this.display.addEventListener(adapter);
		this.opp = new ObjectPropertyPanel(display, 100, 200, false); 
		this.opp.addObjectPropertyListener(this);
		edit.getLayoutManager().setLayout(display);
	}
	
	protected UIComponent createDisplay(Class<?> type) {
		try {
			UIComponent uie = (UIComponent) type.newInstance();
			return uie;
		} catch (Exception e) {
			return new UECanvas();
		}
	}
	
	@Override
	public void onFieldChanged(Object object, Field field) {
		getIcon(true);
		edit.getTree().repaint();
	}
	
	@Override
	protected ImageIcon createIcon() {
		UILayout uc = display.getCustomLayout();
		if (uc != null) {
			icon = Engine.getEngine().createImage(40, 30);
			Graphics2D g = icon.createGraphics();
			g.setClip(0, 0, 40, 30);
			uc.render(g, 0, 0, 40, 30);
			g.dispose();
			return new ImageIcon(AwtEngine.unwrap(icon));
		}
		return null;
	}
	
	@Override
	public String getName() {
		return this.display.name + "";
	}
	public void setName(String name) {
		this.display.name = name;
	}
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		edit.onSelectTreeNode(this);
	}
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new UIMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	
	class DisplayAdapter implements 
	com.g2d.display.event.MouseListener, 
	com.g2d.display.event.KeyListener,
	com.g2d.display.ui.UIComponent.EditModeDraw
	{		
		@Override
		public void mouseClick(com.g2d.display.event.MouseEvent e) {
			
		}
		@Override
		public void mouseDown(com.g2d.display.event.MouseEvent e) {
			edit.onSelectTreeNode(UITreeNode.this);
		}
		@Override
		public void mouseUp(com.g2d.display.event.MouseEvent e) {
			
		}
		
		@Override
		public void keyDown(com.g2d.display.event.KeyEvent e) {
			switch (e.keyCode) {
			case KeyEvent.VK_UP:
				display.y --;
				break;
			case KeyEvent.VK_DOWN:
				display.y ++;
				break;
			case KeyEvent.VK_LEFT:
				display.x ++;
				break;
			case KeyEvent.VK_RIGHT:
				display.x ++;
				break;
			}
		}
		@Override
		public void keyTyped(com.g2d.display.event.KeyEvent e) {

		}
		@Override
		public void keyUp(com.g2d.display.event.KeyEvent e) {}
		
		
		
		@Override
		public void render(com.g2d.Graphics2D g, UIComponent ui) 
		{
			if (edit.getTree().getSelectedNode() == UITreeNode.this) 
			{
				float alpha = 0.75f + (float)Math.sin(ui.timer / 5.0f)/4;
				int s1 = 3;
				int s2 = 6;
				int s4 = 12;
				int w = ui.getWidth();
				int h = ui.getHeight();
				
				g.pushClip();
				g.setClip(-s2, -s2, w+s4, h+s4);
				
				g.setColor(new Color(1, 1, 1, alpha));
				g.drawRect(0, 0, w-1, h-1);
				
				g.fillRect( -s1,  -s1, s2, s2);
				g.fillRect(w-s1,  -s1, s2, s2);
				g.fillRect( -s1, h-s1, s2, s2);
				g.fillRect(w-s1, h-s1, s2, s2);
				
				g.fillRect(w/2-s1,  -s1, s2, s2);
				g.fillRect(w/2-s1, h-s1, s2, s2);
				g.fillRect( -s1, h/2-s1, s2, s2);
				g.fillRect(w-s1, h/2-s1, s2, s2);
				
				
				g.popClip();
			}
		}
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
					edit.createNode(node, edit.getSelectedTemplate(), name);
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
