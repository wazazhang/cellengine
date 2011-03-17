package com.g2d.studio.gameedit;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList.UnitDropItems;
import com.cell.rpg.template.TItemList.UnitDropItems.DropItemNode;
import com.cell.rpg.template.TItemList.UnitDropItems.DropItems;
import com.cell.rpg.template.TShopItemList.ShopItem;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.cell.rpg.template.ability.UnitItemDrop;
import com.cell.rpg.template.ability.UnitItemSell;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.dynamic.DShopItemList;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSShopItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.item.ItemPropertiesNode;
import com.g2d.studio.item.ItemPropertiesSelectCellEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.rpg.RPGObjectPanel.RPGObjectAdapter;

public class ObjectAdapters 
{
	
	/**
	 * 编辑战斗队伍的工具
	 * @author WAZA
	 */
	public static class UnitBattleTeamNodeAdapter extends AbilityCellEditAdapter<UnitBattleTeam.TeamNode>
	{
		@Override
		public Class<UnitBattleTeam.TeamNode> getType() {
			return UnitBattleTeam.TeamNode.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyEdit owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("template_unit_id")){
				ObjectSelectCellEdit<XLSUnit> dialog = new ObjectSelectCellEdit<XLSUnit>(
						owner.getComponent(), XLSUnit.class, fieldValue);
				dialog.showDialog();
				return dialog;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(
				ObjectPropertyEdit owner,
				Object editObject,
				Object fieldValue,
				Field field, 
				DefaultTableCellRenderer src) {
			if (field.getName().equals("template_unit_id")){
				XLSUnit unit = null;
				if (fieldValue != null) {
					String tid = (String)fieldValue;
					unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, tid);
				}
				if (fieldValue != null && unit != null) {
					src.setText(unit.getName());
				} else {
//					src.setForeground(Color.RED);
					src.setText("null");
				}
			}
			return src;
		}
	}
	
	/**
	 * 编辑掉落道具的工具
	 * @author WAZA
	 */
	public static class UnitDropItemNodeAdapter extends AbilityCellEditAdapter<UnitDropItems>
	{
		@Override
		public Class<UnitDropItems> getType() {
			return UnitDropItems.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyEdit owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("item_types")){
				DropItemEditor editor = new DropItemEditor(owner.getComponent(), (DropItems)fieldValue);
				editor.setVisible(true);
				return editor;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("item_types")){
				if (fieldValue!=null) {
					DropItemEditor editor = new DropItemEditor(owner.getComponent(), (DropItems)fieldValue);
					return editor.getComponent(null);
				}
			}
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("item_types")){
				DropItems types = (DropItems)fieldSrcValue;
				if (types != null) {
					StringBuffer sb = new StringBuffer();
					for (DropItemNode n : types) {
						XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, n.titem_id);
						if (item != null) {
							sb.append("[" + item.getData().getName() + "(" + n.drop_rate_percent + "%)" + "]");
						} else {
							sb.append("[" + n.titem_id               + "(" + n.drop_rate_percent + "%)" + "]");
						}
						sb.append("  ·  ");
					}
					return sb.toString();
				}
			}
			return null;
		}
	}
	
	public static class ItemListIDSelectAdapter extends AbilityCellEditAdapter<UnitItemDrop>
	{
		@Override
		public Class<UnitItemDrop> getType() {
			return UnitItemDrop.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("item_list_id")) {
				ObjectSelectCellEditInteger<DItemList> item_list = 
					new ObjectSelectCellEditInteger<DItemList>(owner.getComponent(), DItemList.class, fieldValue);
				item_list.showDialog();
				return item_list;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("item_list_id")) {
				DItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DItemList.class,
						(Integer)fieldValue);
				if (src != null && item_list != null) {
					src.setText(item_list.getData().name);
				}
				return src;
			}
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("item_list_id")) {
				DItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DItemList.class,
						(Integer)fieldSrcValue);
				if (item_list!=null) {
					return item_list.getData().name;
				}
			}
			return null;
		}
	}
	
	public static class ShopItemListIDSelectAdapter extends AbilityCellEditAdapter<UnitItemSell>
	{
		@Override
		public Class<UnitItemSell> getType() {
			return UnitItemSell.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("shopitem_list_id")) {
				ObjectSelectCellEditInteger<DShopItemList> item_list = new ObjectSelectCellEditInteger<DShopItemList>(
						owner.getComponent(), DShopItemList.class, fieldValue);
				item_list.showDialog();
				return item_list;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("shopitem_list_id")) {
				DShopItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DShopItemList.class,
						(Integer)fieldValue);
				if (src != null && item_list != null) {
					src.setText(item_list.getData().name);
				}
				return src;
			}
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("shopitem_list_id")) {
				DShopItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DShopItemList.class,
						(Integer)fieldSrcValue);
				if (item_list!=null) {
					return item_list.getData().name;
				}
			}
			return null;
		}
	}
	
	

	public static class ItemPropertiesSelectAdapter extends RPGObjectAdapter<TItem>
	{
		@Override
		public Class<TItem> getType() {
			return TItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			if (editObject instanceof TItem) {
				if (field.getName().equals("item_properties_id")) {
					ItemPropertiesSelectCellEdit edit = new ItemPropertiesSelectCellEdit(
							owner.getComponent(), 
							Studio.getInstance().getItemManager().getNode((Integer)fieldValue)
							);
//					edit.setValue((Integer)fieldValue);
					edit.showDialog();
					return edit;
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (editObject instanceof TItem) {
				if (field.getName().equals("item_properties_id") && fieldValue!=null) {
					ItemPropertiesNode node = Studio.getInstance().getItemManager().getNode((Integer)fieldValue);
					if (node != null) {
						src.setText(node.toString());
						src.setIcon(node.getIcon(false));
					}
					return src;
				}
			}
			return null;
		}
		
	}
	


	


	/**
	 * 编辑出售的商品
	 * @author WAZA
	 */
	public static class ShopItemNodeAdapter extends AbilityCellEditAdapter<ShopItem>
	{
		@Override
		public Class<ShopItem> getType() {
			return ShopItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyEdit owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("shop_item_id")){
				ObjectSelectCellEditInteger<XLSShopItem> item = new ObjectSelectCellEditInteger<XLSShopItem>(
						owner.getComponent(), XLSShopItem.class, fieldValue);
				if (item!=null) {
					item.showDialog();
					return item;
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("shop_item_id")){
				XLSShopItem item = Studio.getInstance().getObjectManager().getObject(
						XLSShopItem.class, 
						(Integer)fieldValue);
				if (item != null) {
					src.setText(item.getName());
				}
			}
			return null;
		}
	}
}
