package com.g2d.studio.quest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;

import com.cell.rpg.quest.formula.TriggerUnitMethod;
import com.g2d.awt.util.*;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormDynamic;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

public class QuestManager extends ManagerFormDynamic
{
	private static final long serialVersionUID = 1L;
	
	QuestTreeView 			tree_view;

	JButton					btn_quest_group = new JButton();
	
//	QuestItemManager		quest_items;
	
	public QuestManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "任务管理器", Res.icon_quest);

		initDynamicClasses();

		btn_quest_group.setText("任务编组");
		btn_quest_group.setIcon(Tools.createIcon(Res.icon_quest_group));
		btn_quest_group.addActionListener(this);
		tool_bar.add(btn_quest_group);
		
//		File items_dir	= new File(studio.project_save_path, "quests/questitems");
//		File items_list	= new File(items_dir, "questitems.list");
//		quest_items		= new QuestItemManager(studio, items_list);
		
		File quest_dir	= studio.project_save_path.getChildFile("quests");
		File quest_list	= quest_dir.getChildFile("quest.list");
		tree_view 		= new QuestTreeView(quest_list, progress);
		this.add(tree_view, BorderLayout.CENTER);
		
	}
	
	private void initDynamicClasses()
	{
		Class<?> player_class 	= null;
		Class<?> pet_class 		= null;
		Class<?> npc_class 		= null;
		try {
			player_class 	= Class.forName(Config.DYNAMIC_QUEST_PLAYER_CLASS);
		} catch (Exception err) {
			System.err.println("错误：未定义玩家类型");
		}
		try {
			pet_class 		= Class.forName(Config.DYNAMIC_QUEST_PET_CLASS);
		} catch (Exception err) {
			System.err.println("错误：未定义宠物类型");
		}
		try {
			npc_class 		= Class.forName(Config.DYNAMIC_QUEST_NPC_CLASS);
		} catch (Exception err) {
			System.err.println("错误：未定义NPC类型");
		}
		TriggerUnitMethod.setTriggerUnitClass(
				player_class, 
				pet_class, 
				npc_class);
	}
	
	public Vector<QuestNode> getDependedQuests() {
		Vector<QuestNode> ret = new Vector<QuestNode>();
		for (QuestNode n : tree_view.getAllObject()) {
			if (!n.getData().is_transient_quest) {
				ret.add(n);
			}
		}
		return ret;
	}
	
	public Vector<QuestNode> getQuests() {
		return tree_view.getAllObject();
	}
	
	public QuestNode getQuest(int id) {
		return tree_view.getNode(id);
	}
	
//	public QuestItemManager getQuestItems() {
//		return quest_items;
//	}
	
	@Override
	public void saveAll() throws Throwable {
		tree_view.saveAll();
	}
	@Override
	public void saveSingle() throws Throwable {
		tree_view.saveSingle();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == btn_quest_group) {
			Studio.getInstance().getQuestGroupManager().setVisible(true);
		}
	}
	
}
