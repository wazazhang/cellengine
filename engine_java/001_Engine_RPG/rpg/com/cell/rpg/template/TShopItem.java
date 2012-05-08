package com.cell.rpg.template;

import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.g2d.annotation.Property;

@Property("商品")
public class TShopItem extends TemplateNode
{
	@Property("道具ID")
	@PropertyAdapter(PropertyType.ITEM_ID)
	public int item_template_id;
	
	@Property("数量")
	public int item_count = 1;
	
	public TShopItem(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
	
}
