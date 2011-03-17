package com.cell.rpg.template;

import com.cell.rpg.display.UnitNode;
import com.cell.rpg.struct.QuestStateDisplayOR;
import com.cell.rpg.template.ability.QuestItemDrop;
import com.cell.rpg.template.ability.UnitAvatar;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.cell.rpg.template.ability.UnitItemCollection;
import com.cell.rpg.template.ability.UnitItemDrop;
import com.cell.rpg.template.ability.UnitItemSell;
import com.g2d.annotation.Property;



public class TUnit extends TemplateNode
{
	UnitNode 	display_node;
	
	@Property("任务依赖显示条件")
	public QuestStateDisplayOR quest_display = null;
	
	public TUnit(int id, String name) {
		super(id, name);
	}

	public void setDisplayNode(String cpj_name, String set_name) {
		display_node = new UnitNode(cpj_name, set_name);
	}
	
	public UnitNode getDisplayNode() {
		return display_node;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				UnitBattleTeam.class,
				UnitItemDrop.class,
				QuestItemDrop.class,
				UnitItemSell.class,
				UnitItemCollection.class,
				UnitAvatar.class,
		};
	}
}
