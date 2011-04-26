package com.g2d.studio.quest;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.quest.Quest;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.awt.util.*;

public class QuestNodeGroup extends ObjectGroup<QuestNode, Quest>
{
	private static final long serialVersionUID = 1L;
	
	QuestTreeView view ;
	
	public QuestNodeGroup(String name, QuestTreeView view) {
		super(name, 
				view.list_file,
				view.node_type, 
				view.data_type);
		this.view = view;
	}
	
	@Override
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new QuestNodeGroup(name, view);
	}
	
	@Override
	protected boolean createObjectNode(String key, Quest data) {
		try{
			view.addNode(this, new QuestNode(data));
			return true;
		}catch(Exception err){
			err.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			new QuestRootMenu(this).show(
					view.getTree(),
					e.getX(),
					e.getY());
		}
	}
		
//	-------------------------------------------------------------------------------------
		
	class QuestRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		QuestNodeGroup root;
		JMenuItem add_quest = new JMenuItem("添加任务");
		
		public QuestRootMenu(QuestNodeGroup root) {
			super(root, view.getTree(), view.getTree());
			this.root = root;
			add_quest.addActionListener(this);
			add(add_quest);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add_quest) {
				String name = JOptionPane.showInputDialog(AbstractDialog.getTopWindow(view), "输入任务名字！");
				if (name!=null && name.length()>0) {
					QuestNode node = new QuestNode(view, name);
					view.addNode(root, node);
					view.getTree().reload(root);
				}
			}
		}
	}
}
