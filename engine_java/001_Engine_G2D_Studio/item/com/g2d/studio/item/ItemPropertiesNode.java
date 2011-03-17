package com.g2d.studio.item;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.cell.rpg.item.ItemProperties;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;

public class ItemPropertiesNode extends DynamicNode<ItemProperties>
{
	public ItemPropertiesNode(IDynamicIDFactory<ItemPropertiesNode> factory, String name) {
		super(factory, name);
	}

	@Override
	protected ItemProperties newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return new ItemProperties(getIntID(), name);
	}
	
	public ItemPropertiesNode(ItemProperties data) {
		super(data, data.getIntID(), data.name);
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new ItemPropertiesMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	
	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		}
		return false;
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		if (edit_component == null) {
			edit_component = new ItemPropertiesEditor(this);
		}
		return edit_component;
	}
	
//	---------------------------------------------------------------------------------------------------------------

	class ItemPropertiesMenu extends NodeMenu<ItemPropertiesNode>
	{
		public ItemPropertiesMenu(JTree tree, ItemPropertiesNode node) {
			super(node);
			super.remove(open);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rename) {
				String name = JOptionPane.showInputDialog(
						AbstractDialog.getTopWindow(getInvoker()), 
						"输入名字！",
						node.getData().name);
				if (name!=null && name.length()>0) {
					node.setName(name);
				}
				if (tree!=null) {
					tree.reload(node);
				}
			}
			else if (e.getSource() == delete) {
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------

	
}
