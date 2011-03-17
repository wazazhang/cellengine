package com.g2d.studio.item.property;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.ItemPropertyTypes;
import com.g2d.studio.swing.G2DList;

@SuppressWarnings("serial")
public class ItemPropertyList extends G2DList<ItemPropertyNode>
{
	public ItemPropertyList() 
	{
		Vector<ItemPropertyNode> list_data = new Vector<ItemPropertyNode>();
		for (Class<?> type : ItemPropertyTypes.getItemPropertyTypesList()) {
			list_data.add(new ItemPropertyNode(type));
		}
		
		Collections.sort(list_data, new Comparator<ItemPropertyNode>() 
								{
									@Override
									public int compare(ItemPropertyNode o1, ItemPropertyNode o2) {
										return CUtil.getStringCompare().compare(AbstractAbility.getEditName(o2.type), AbstractAbility.getEditName(o1.type));
									}
								} 
					);
		
		super.setListData(list_data);
		super.setLayoutOrientation(G2DList.VERTICAL);
	}
}