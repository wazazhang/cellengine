package com.g2d.studio.item.property;

import java.awt.Component;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.cell.rpg.item.ItemPropertyTypes;
import com.cell.rpg.item.anno.ItemType;
import com.g2d.annotation.Property;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DListItemData;
import com.g2d.studio.swing.G2DListSelectDialog;

@SuppressWarnings("serial")
public class ItemPropertySavedTypeSelectDialog extends G2DListSelectDialog<G2DListItemData<ItemType>> 
implements PropertyCellEdit<Integer>
{
	JLabel cell_edit_component = new JLabel();
	
	public ItemPropertySavedTypeSelectDialog(Component owner, Integer dv) {
		super(owner, createG2DList(dv), null);
		getList().setLayoutOrientation(G2DList.VERTICAL);
		try {
		getList().setSelectedValue(getList().getSelectedValue(), true);
		}catch(Exception err){}
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		G2DListItemData<ItemType> node = getSelectedObject();
		if (node != null) {
			cell_edit_component.setText(node.getListName());
		} else {
			cell_edit_component.setText("");
		}
		return cell_edit_component;
	}
	
	@Override
	public Integer getValue() {
		G2DListItemData<ItemType> node = getSelectedObject();
		if (node != null) {
			return node.getData().value();
		}
		return null;
	}

	static G2DList<G2DListItemData<ItemType>> createG2DList(Integer dv) {
		Vector<G2DListItemData<ItemType>> items = createG2DListData();
		G2DList<G2DListItemData<ItemType>> list = new G2DList<G2DListItemData<ItemType>>(items);
		if (dv != null) {
			G2DListItemData<ItemType> default_value = null;
			for (G2DListItemData<ItemType> item : items) {
				if (dv == item.getData().value()) {
					default_value = item;
				}
			}
			list.setSelectedValue(default_value, true);
		}
		return list;
	}
	
	static Vector<G2DListItemData<ItemType>> createG2DListData() {
		Vector<G2DListItemData<ItemType>> items = new Vector<G2DListItemData<ItemType>>();
		for (Class<?> t : ItemPropertyTypes.getItemPropertyTypesList()) {
			ItemType it = t.getAnnotation(ItemType.class);
			G2DListItemData<ItemType> item = new G2DListItemData<ItemType>(
					it, AbstractAbility.getEditName(t));
			items.add(item);
		}
		Collections.sort(items, new Comparator<G2DListItemData<ItemType>>(){
			@Override
			public int compare(
					G2DListItemData<ItemType> o1,
					G2DListItemData<ItemType> o2) {
				return o1.getData().value() - o2.getData().value();
			}
		});
		return items;
	}
}
