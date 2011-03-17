package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.text.html.ObjectView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.cell.CUtil;
import com.cell.rpg.quest.QuestItem;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;

import com.g2d.studio.gameedit.dynamic.DQuestItem;
import com.g2d.studio.gameedit.dynamic.DQuestItem;
import com.g2d.studio.gameedit.entity.ObjectGroup;

import com.g2d.studio.quest.QuestTreeView;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;

public class QuestItemTreeView extends ObjectTreeViewDynamic<DQuestItem, QuestItem>
{
	private static final long serialVersionUID = 1L;

	public QuestItemTreeView(String title, File list_file) 
	{
		super(title, DQuestItem.class, QuestItem.class, list_file);		
	}

	@Override
	protected QuestItemGroup createTreeRoot(String title) {
		return new QuestItemGroup(title);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	public class QuestItemGroup extends ObjectGroup<DQuestItem, QuestItem>
	{
		private static final long serialVersionUID = 1L;

		public QuestItemGroup(String name) {
			super(name, 
					QuestItemTreeView.this.list_file,
					QuestItemTreeView.this.node_type, 
					QuestItemTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new QuestItemGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, QuestItem data) {
			try{
				addNode(this, new DQuestItem(data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new QuestItemRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
//	


//	-------------------------------------------------------------------------------------------------------------------------------
	class QuestItemRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		QuestItemGroup root;
		JMenuItem add = new JMenuItem("添加任务标志");
		
		public QuestItemRootMenu(QuestItemGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add.addActionListener(this);
			add(add);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add) {
				String name = JOptionPane.showInputDialog(AbstractDialog.getTopWindow(QuestItemTreeView.this), 
						"输入标志名字！");
				if (name!=null && !name.isEmpty()) {
					DQuestItem item = new DQuestItem(QuestItemTreeView.this, name);
					addNode(root, item);
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------
	

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	
}
