package com.cell.rpg.template;

import com.g2d.annotation.Property;

public class TItem extends TemplateNode
{	
	@Property("图标编号")
	public String	icon_index;

	@Property("道具属性组")
	public int item_properties_id;
	
	public TItem(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
	
}
