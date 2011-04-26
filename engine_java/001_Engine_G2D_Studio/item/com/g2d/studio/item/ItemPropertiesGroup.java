package com.g2d.studio.item;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.item.ItemProperties;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class ItemPropertiesGroup extends ObjectGroup<ItemPropertiesNode, ItemProperties>
{
	private static final long serialVersionUID = 1L;
	
	ItemPropertiesTreeView view ;
	
	public ItemPropertiesGroup(String name, ItemPropertiesTreeView view) {
		super(name, 
				view.list_file,
				view.node_type, 
				view.data_type);
		this.view = view;
	}
	
	@Override
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new ItemPropertiesGroup(name, view);
	}
	
	@Override
	protected boolean createObjectNode(String key, ItemProperties data) {
		try{
			view.addNode(this, new ItemPropertiesNode(data));
			return true;
		}catch(Exception err){
			err.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			new ItemPropertiesRootMenu(this).show(
					view.getTree(),
					e.getX(),
					e.getY());
		}
	}
		
//	-------------------------------------------------------------------------------------
		
	class ItemPropertiesRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		ItemPropertiesGroup root;
		JMenuItem add_quest = new JMenuItem("添加道具属性模板");
		
		public ItemPropertiesRootMenu(ItemPropertiesGroup root) {
			super(root, view.getTree(), view.getTree());
			this.root = root;
			add_quest.addActionListener(this);
			add(add_quest);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add_quest) {
				String name = JOptionPane.showInputDialog(AbstractDialog.getTopWindow(view), "输入任务名字！");
				if (name!=null && name.length()>0) {
					ItemPropertiesNode node = new ItemPropertiesNode(view, name);
					view.addNode(root, node);
					view.getTree().reload(root);
				}
			}
		}
	}
}
