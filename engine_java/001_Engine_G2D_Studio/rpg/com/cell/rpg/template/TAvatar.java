package com.cell.rpg.template;

import com.cell.rpg.display.UnitNode;


public class TAvatar extends TemplateNode
{
	public UnitNode body;
	
	public TAvatar(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
}
