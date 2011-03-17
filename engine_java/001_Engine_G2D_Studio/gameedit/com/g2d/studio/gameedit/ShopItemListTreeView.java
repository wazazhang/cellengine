package com.g2d.studio.gameedit;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.template.TShopItemList;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DShopItemList;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class ShopItemListTreeView extends ObjectTreeViewTemplateDynamic<DShopItemList, TShopItemList>
{
	private static final long serialVersionUID = 1L;

	public ShopItemListTreeView(String title, String objects_dir, ProgressForm progress) 
	{
		super(title, DShopItemList.class, TShopItemList.class, objects_dir, progress);		
	}

	@Override
	protected ShopItemListGroup createTreeRoot(String title) {
		return new ShopItemListGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class ShopItemListGroup extends ObjectGroup<DShopItemList, TShopItemList>
	{
		private static final long serialVersionUID = 1L;

		public ShopItemListGroup(String name) {
			super(name, 
					ShopItemListTreeView.this.list_file,
					ShopItemListTreeView.this.node_type, 
					ShopItemListTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
			return new ShopItemListGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, TShopItemList data) {
			try{
				addNode(this, new DShopItemList(ShopItemListTreeView.this, data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new ShopItemListRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------------------------------------------------
	class ShopItemListRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		ShopItemListGroup root;
		JMenuItem add_effect = new JMenuItem("添加ShopItemList");
		
		public ShopItemListRootMenu(ShopItemListGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_effect.addActionListener(this);
			add(add_effect);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_effect) {
				String name = JOptionPane.showInputDialog(getTree(), "输入ShopItemList名字");
				if (name.length()>0) {
					DShopItemList effect = new DShopItemList(ShopItemListTreeView.this, name);
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
