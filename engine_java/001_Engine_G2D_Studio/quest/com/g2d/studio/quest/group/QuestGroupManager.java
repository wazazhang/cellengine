package com.g2d.studio.quest.group;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormDynamic;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

public class QuestGroupManager extends ManagerFormDynamic
{
	private static final long serialVersionUID = 1L;
	
	File 				quest_group_dir;
	File 				quest_group_list;
	QuestGroupTreeView	quest_group_view;
	
	public QuestGroupManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "任务编组管理器", Res.icon_quest_group);

		quest_group_dir		= studio.project_save_path.getChildFile("questgroups");
		quest_group_list	= quest_group_dir.getChildFile("questgroups.list");
		quest_group_view	= new QuestGroupTreeView("任务编组管理器", quest_group_list, progress);
		this.add(quest_group_view, BorderLayout.CENTER);
		
		this.setSize(getWidth()-100, getHeight()-100);
		this.setLocation(getX()+100, getY()+100);
	}
	
	@Override
	public void saveAll(IProgress progress) throws Throwable {
		quest_group_view.saveAll(progress);
	}
	@Override
	public void saveSingle() throws Throwable {
		quest_group_view.saveSingle();
	}
	
	public Vector<DQuestGroup> getQuestGroups() {
		return quest_group_view.getAllObject();
	}
	
	public DQuestGroup getQuestGroup(int id) {
		return quest_group_view.getNode(id);
	}



}
