package com.g2d.studio.gameedit;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.template.TItemList;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class ItemListTreeView extends ObjectTreeViewTemplateDynamic<DItemList, TItemList>
{
	private static final long serialVersionUID = 1L;

	public ItemListTreeView(String title, com.g2d.studio.io.File objects_dir, ProgressForm progress) 
	{
		super(title, DItemList.class, TItemList.class, objects_dir, progress);		
	}

	@Override
	protected ItemListGroup createTreeRoot(String title) {
		return new ItemListGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class ItemListGroup extends ObjectGroup<DItemList, TItemList>
	{
		private static final long serialVersionUID = 1L;

		public ItemListGroup(String name) {
			super(name, 
					ItemListTreeView.this.list_file,
					ItemListTreeView.this.node_type, 
					ItemListTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new ItemListGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, TItemList data) {
			try{
				addNode(this, new DItemList(ItemListTreeView.this, data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new ItemListRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------------------------------------------------
	class ItemListRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		ItemListGroup root;
		JMenuItem add_effect = new JMenuItem("添加ItemList");
		
		public ItemListRootMenu(ItemListGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_effect.addActionListener(this);
			add(add_effect);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_effect) {
				String name = JOptionPane.showInputDialog(getTree(), "输入ItemList名字");
				if (name.length()>0) {
					DItemList effect = new DItemList(ItemListTreeView.this, name);
					addNode(root, effect);
					getTree().reload(root);
				} else {
					JOptionPane.showMessageDialog(getTree(), "名字不能为空");
				}
			} else {
				super.actionPerformed(e);
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------
	

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	

}
