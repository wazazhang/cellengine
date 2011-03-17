package com.g2d.studio.quest.items;

import javax.swing.JTabbedPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.quest.QuestCellEditAdapter.AbstractValueAdapter;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemAwardBattle;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemAwardItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemAwardTeleport;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemDropItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagQuest;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagQuestItem;
import com.g2d.studio.quest.QuestCellEditAdapter.QuestItemTagQuestStateKillMonsterComparison;
import com.g2d.studio.rpg.PropertyAdapters;

public class QuestItemEditor extends ObjectViewer<QuestItemNode> implements RPGSerializationListener
{
	QuestItemScriptPanel script_panel;
	
	public QuestItemEditor(QuestItemNode node) {
		super(node, 
				// tag
				new QuestItemTagQuest(),
				new QuestItemTagItem(),
				new QuestItemTagQuestItem(),
				new QuestItemTagQuestStateKillMonsterComparison(),
				
				// award
				new QuestItemAwardItem(),
				new QuestItemAwardTeleport(),
				new QuestItemAwardBattle(),
				new QuestItemDropItem(),
				
				// formual
				new AbstractValueAdapter(),
				new PropertyAdapters.TimeObjectAdapter()
		);
		if (node.getData().getRPGSerializationListeners() == null ||
			node.getData().getRPGSerializationListeners().contains(this)==false) {
			node.getData().addRPGSerializationListener(this);
		}
	}

	@Override
	protected void appendPages(JTabbedPane table) {
//		script_panel	= new QuestItemScriptPanel(tobject.getData());
//		table.insertTab("脚本", null, script_panel, null, 0);
		table.setSelectedComponent(page_abilities);
	}
	
	@Override
	public void onReadComplete(RPGObject object) {}
	
	@Override
	public void onWriteBefore(RPGObject object) {
//		tobject.getData().script = script_panel.getScript();
	}
}
