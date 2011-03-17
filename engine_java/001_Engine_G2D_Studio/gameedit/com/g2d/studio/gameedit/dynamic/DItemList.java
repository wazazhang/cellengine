package com.g2d.studio.gameedit.dynamic;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import com.cell.CIO;
import com.cell.rpg.template.TItemList;
import com.g2d.awt.util.Tools;
import com.g2d.studio.gameedit.ItemListTreeView;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.ItemListTreeView.ItemListGroup;
import com.g2d.studio.res.Res;

final public class DItemList extends DynamicNode<TItemList>
{
	private static final long serialVersionUID = 1L;

	private ItemListTreeView factory;
	
	public DItemList(ItemListTreeView f, String name) {
		super(f, name);
		this.factory = f;
	}
	
	public DItemList(ItemListTreeView f, TItemList effect) {
		super(effect, Integer.parseInt(effect.id), effect.name);
		this.factory = f;
	}
	
	@Override
	protected TItemList newData(IDynamicIDFactory<?> f, String name, Object... args) {
		return new TItemList(getIntID(), name);
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
		new ItemListMenu().show(tree, e.getX(), e.getY());
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		onOpenEdit();
		if (edit_component == null) {
			edit_component = new ItemListViewer();
		}
		return edit_component;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	public class ItemListMenu extends DynamicNodeMenu
	{
		protected JMenuItem 		clone	= new JMenuItem("复制");
		
		public ItemListMenu() {
			super(DItemList.this);
			super.add(clone);
			super.remove(delete);
			super.add(delete);
			clone.addActionListener(this);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clone) {
				if (node.getParent() instanceof ItemListGroup) {
					ItemListGroup	root	= (ItemListGroup)node.getParent();
					TItemList		src		= CIO.cloneObject(DItemList.this.getData());
					DItemList		effect	= new DItemList(factory, src.name+" Copy");
					factory.addNode(root, effect);
					factory.getTree().reload(root);
				}
			} 
			else {
				super.actionPerformed(e);
			}
		}
	}
	
	
	public class ItemListViewer extends ObjectViewer<DItemList>
	{
		private static final long serialVersionUID = 1L;
		
		public ItemListViewer() {
			super(DItemList.this, new ObjectAdapters.UnitDropItemNodeAdapter());
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
	}
}
