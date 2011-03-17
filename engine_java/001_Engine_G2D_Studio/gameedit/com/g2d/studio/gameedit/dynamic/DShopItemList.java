package com.g2d.studio.gameedit.dynamic;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import com.cell.CIO;
import com.cell.rpg.template.TShopItemList;
import com.g2d.awt.util.Tools;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.ShopItemListTreeView;
import com.g2d.studio.gameedit.ShopItemListTreeView.ShopItemListGroup;
import com.g2d.studio.res.Res;

final public class DShopItemList extends DynamicNode<TShopItemList>
{
	private static final long serialVersionUID = 1L;

	private ShopItemListTreeView factory;
	
	public DShopItemList(ShopItemListTreeView f, String name) {
		super(f, name);
		this.factory = f;
	}
	
	public DShopItemList(ShopItemListTreeView f, TShopItemList data) {
		super(data, Integer.parseInt(data.id), data.name);
		this.factory = f;
	}
	
	@Override
	protected TShopItemList newData(IDynamicIDFactory<?> f, String name, Object... args) {
		return new TShopItemList(getIntID(), name);
	}
	
	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		return Tools.createIcon(Res.icon_res_9);
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new ShopItemListMenu().show(tree, e.getX(), e.getY());
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		onOpenEdit();
		if (edit_component == null) {
			edit_component = new ShopItemListViewer();
		}
		return edit_component;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	public class ShopItemListMenu extends DynamicNodeMenu
	{
		protected JMenuItem 		clone	= new JMenuItem("复制");
		
		public ShopItemListMenu() {
			super(DShopItemList.this);
			super.add(clone);
			super.remove(delete);
			super.add(delete);
			clone.addActionListener(this);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clone) {
				if (node.getParent() instanceof ShopItemListGroup) {
					ShopItemListGroup	root	= (ShopItemListGroup)node.getParent();
					TShopItemList		src		= CIO.cloneObject(DShopItemList.this.getData());
					DShopItemList		effect	= new DShopItemList(factory, src.name+" Copy");
					factory.addNode(root, effect);
					factory.getTree().reload(root);
				}
			} 
			else {
				super.actionPerformed(e);
			}
		}
	}
	
	
	public class ShopItemListViewer extends ObjectViewer<DShopItemList>
	{
		private static final long serialVersionUID = 1L;
		
		public ShopItemListViewer() {
			super(DShopItemList.this, new ObjectAdapters.ShopItemNodeAdapter());
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
	}
}
