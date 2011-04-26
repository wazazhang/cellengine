package com.g2d.studio.quest.group;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.quest.QuestGroup;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class QuestGroupTreeView extends ObjectTreeViewDynamic<DQuestGroup, QuestGroup>
{
	private static final long serialVersionUID = 1L;

	public QuestGroupTreeView(String title, File list_file, ProgressForm progress) 
	{
		super(title, DQuestGroup.class, QuestGroup.class, list_file, progress);		
	}

	@Override
	protected QuestGroupGroup createTreeRoot(String title) {
		return new QuestGroupGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class QuestGroupGroup extends ObjectGroup<DQuestGroup, QuestGroup>
	{
		private static final long serialVersionUID = 1L;

		public QuestGroupGroup(String name) {
			super(name, 
					QuestGroupTreeView.this.list_file,
					QuestGroupTreeView.this.node_type, 
					QuestGroupTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new QuestGroupGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, QuestGroup data) {
			try{
				addNode(this, new DQuestGroup(QuestGroupTreeView.this, data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new QuestGroupRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------------------------------------------------
	class QuestGroupRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		QuestGroupGroup root;
		JMenuItem add_group = new JMenuItem("添加QuestGroup");
		
		public QuestGroupRootMenu(QuestGroupGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_group.addActionListener(this);
			add(add_group);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_group) {
				String name = JOptionPane.showInputDialog(getTree(), "输入QuestGroup名字");
				if (name.length()>0) {
					DQuestGroup effect = new DQuestGroup(QuestGroupTreeView.this, name);
					addNode(root, effect);
					getTree().reload(root);
				} else {
					JOptionPane.showMessageDialog(getTree(), "名字不能为空");
				}
			} else {
				super.actionPerformed(e);
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------
	

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	

}
