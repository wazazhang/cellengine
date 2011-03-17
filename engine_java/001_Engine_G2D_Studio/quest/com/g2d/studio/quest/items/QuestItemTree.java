package com.g2d.studio.quest.items;

import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.cell.rpg.quest.QuestCondition;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.quest.QuestNode.QuestItemManager;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.awt.util.*;

public class QuestItemTree extends G2DTree
{
	final private QuestNode quest_node ;

	final private ConditionGroup group_trigger_condition;
	final private ConditionGroup group_trigger_award;
	final private ConditionGroup group_complete_condition;
	final private ConditionGroup group_complete_award;
	final private ConditionGroup group_discard_award;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestItemTree(QuestNode quest)
	{
		super(new DefaultMutableTreeNode("任务数据"));
		super.setDragEnabled(true);
		super.setMinimumSize(new Dimension(200, 200));
		
		this.quest_node = quest;
		
		group_trigger_condition		= new ConditionGroup("接受条件");
		group_trigger_award			= new ConditionGroup("接受奖励");
		group_complete_condition	= new ConditionGroup("完成条件");
		group_complete_award		= new ConditionGroup("完成奖励");
		group_discard_award			= new ConditionGroup("放弃动作");
		
		getRoot().add(group_trigger_condition);
		getRoot().add(group_trigger_award);
		getRoot().add(group_complete_condition);
		getRoot().add(group_complete_award);
		getRoot().add(group_discard_award);
		
		group_trigger_condition	.removeAllChildren();		
		group_trigger_award		.removeAllChildren();
		group_complete_condition.removeAllChildren();
		group_complete_award	.removeAllChildren();
		group_discard_award		.removeAllChildren();
		
		group_trigger_condition	.loadList(quest_node.getData().accept_condition);
		group_trigger_award		.loadList(quest_node.getData().accept_award);
		group_complete_condition.loadList(quest_node.getData().complete_condition);
		group_complete_award	.loadList(quest_node.getData().complete_award);
		group_discard_award		.loadList(quest_node.getData().discard_action);
		reload();
	}
	
	public void save() {
		group_trigger_condition	.saveList(quest_node.getData().accept_condition);
		group_trigger_award		.saveList(quest_node.getData().accept_award);
		group_complete_condition.saveList(quest_node.getData().complete_condition);
		group_complete_award	.saveList(quest_node.getData().complete_award);	
		group_discard_award		.saveList(quest_node.getData().discard_action);	
	}
	
	@Override
	protected boolean checkDrag(DropTarget evtSource, Object src, Object dst, int position) {
		MutableTreeNode src_node = (MutableTreeNode)src;
		MutableTreeNode dst_node = (MutableTreeNode)dst;
		if (dst_node == getRoot()) {
			return false;
		}
		if (src_node.getParent() == getRoot()) {
			return false;
		}
		if (dst_node.getParent() == getRoot() && position != 0) {
			return false;
		}
		// 任务奖励不能作为条件
		if (G2DTree.containsNode(group_trigger_award, src_node) ||
			G2DTree.containsNode(group_complete_award, src_node) ||
			G2DTree.containsNode(group_discard_award, src_node)) {
			if (dst == group_trigger_condition || G2DTree.containsNode(group_trigger_condition, dst_node)) {
				return false;
			}
			if (dst == group_complete_condition || G2DTree.containsNode(group_complete_condition, dst_node)) {
				return false;
			}
		}
		// 任务条件不能作为奖励
		if (G2DTree.containsNode(group_trigger_condition, src_node) ||
			G2DTree.containsNode(group_complete_condition, src_node)) {
			if (dst == group_trigger_award || G2DTree.containsNode(group_trigger_award, dst_node)) {
				return false;
			}
			if (dst == group_complete_award || G2DTree.containsNode(group_complete_award, dst_node)) {
				return false;
			}
			if (dst == group_discard_award || G2DTree.containsNode(group_discard_award, dst_node)) {
				return false;
			}
		}
		return super.checkDrag(evtSource, src, dst, position);
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	class ConditionGroup extends G2DTreeNodeGroup<QuestItemNode>
	{
		public ConditionGroup(String title) {
			super(title);
		}
		
		@Override
		protected boolean pathAddLeafNode(String name, int index, int length) {
			QuestItemManager items = quest_node.getQuestItemManager();
			try{
				if (index == length - 1) {
					QuestItemNode node = items.get(Integer.parseInt(name));
					this.add(node);
					return true;
				}
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
			return new ConditionGroup(name);
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new ConditionGroupMenu(this).show(tree, e.getX(), e.getY());
			}
		}
	
		private void saveList(QuestCondition condition)
		{
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)getParent();
			removeFromParent();
			try{
				condition.clearQuestItem();
				for (QuestItemNode item : G2DTree.getNodesSubClass(this, QuestItemNode.class)) {
					condition.getNameList().add(toPathString(item, "/") + item.getID());
					condition.addQuestItem(item.getData());
				}
			} finally {
				parent.add(this);
			}
		}
		
		private void loadList(QuestCondition condition)
		{
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)getParent();
			removeFromParent();
			try{
				for (String line : condition.getNameList()) {
					try{
						loadPath(line.trim());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} finally {
				parent.add(this);
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	class ConditionGroupMenu extends GroupMenu
	{
		JMenuItem 	add_quest_item;
		boolean 	is_award;
		String 		name_type;
		
		public ConditionGroupMenu(ConditionGroup root) 
		{
			super(root, AbstractDialog.getTopWindow(QuestItemTree.this), QuestItemTree.this);
			if (root.getParent() == getRoot()) {
				super.remove(super.delete);
				super.remove(super.rename);
			}
			
			boolean is_trigger_award	=  group_trigger_award == root  || G2DTree.containsNode(group_trigger_award, root);
			boolean is_complete_award	=  group_complete_award == root || G2DTree.containsNode(group_complete_award, root);
			boolean is_discard_award	=  group_discard_award == root  || G2DTree.containsNode(group_discard_award, root);
			
			is_award = is_trigger_award || is_complete_award || is_discard_award;
			
			name_type = (is_award ? (is_discard_award ? "动作" : "奖励") : "条件");

			add_quest_item		= new JMenuItem("添加" + name_type);
			add_quest_item.addActionListener(this);

			add(add_quest_item);
			
			if (is_discard_award) {
				if (G2DTree.getNodesSubClass(root, QuestItemNode.class).size()>0) {
					remove(add_quest_item);
					remove(add_group);
				} 
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_quest_item) {
				String name = JOptionPane.showInputDialog(window, "输入" + name_type + "名字！", "未命名"+name_type);
				if (name!=null && name.length()>0) {
					QuestItemNode node = quest_node.getQuestItemManager().createQuestItem(name, is_award);
					root.add(node);
					g2d_tree.reload(root);
				}
			}
			else if (e.getSource() == delete) {
				if (JOptionPane.showConfirmDialog(window, "确实要删除过滤器 \"" + root + "\" ！") == JOptionPane.YES_OPTION) {
					G2DTreeNodeGroup<?> parent = (G2DTreeNodeGroup<?>)root.getParent();
					root.removeFromParent();
					Vector<QuestItemNode> nodes = G2DTree.getNodesSubClass(root, QuestItemNode.class);
					for (QuestItemNode node : nodes) {
						quest_node.getQuestItemManager().killID(node.getIntID());
					}
					g2d_tree.reload(parent);
				}
			}
			else {
				super.actionPerformed(e);
			}
			
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
}
