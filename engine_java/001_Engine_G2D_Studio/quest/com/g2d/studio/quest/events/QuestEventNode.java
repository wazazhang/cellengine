package com.g2d.studio.quest.events;

import javax.swing.ImageIcon;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.awt.util.*;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.studio.quest.QuestCellEditAdapter.AbstractValueAdapter;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestFestivalAdapter;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemAwardItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemAwardTeleport;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemDropItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagQuest;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagQuestItem;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.PropertyAdapters;
import com.g2d.studio.swing.G2DTreeNode;

public class QuestEventNode extends G2DTreeNode<G2DTreeNode<?>>
{	
	static ImageIcon icon_event	= Tools.createIcon(Res.icon_quest_event);

	AbstractAbility 		ability;
	
	ObjectPropertyPanel 	edit_panel;
	
	public QuestEventNode(AbstractAbility ability) {
		this.ability = ability;
	}

	@Override
	public String getName() {
		return ability.toString();
	}
	
	@Override
	protected ImageIcon createIcon() {
		return icon_event;
	}
	
	public ObjectPropertyPanel getEditComponent() {
		if (edit_panel == null) {
			edit_panel = new ObjectPropertyPanel(
					ability, 200, 100,
					new QuestItemTagQuest(),
					new QuestItemTagItem(),
					new QuestItemTagQuestItem(),
					
					new QuestItemAwardItem(),
					new QuestItemAwardTeleport(),
					new QuestItemDropItem(),
					
					//
					new AbstractValueAdapter(),
					new QuestFestivalAdapter(),
					new PropertyAdapters.TimeObjectAdapter()
					
					);
		}
		return edit_panel;
	}

}
