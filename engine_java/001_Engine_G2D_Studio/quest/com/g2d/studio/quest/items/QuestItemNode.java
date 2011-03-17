package com.g2d.studio.quest.items;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.cell.rpg.quest.QuestItem;
import com.g2d.awt.util.*;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.awt.util.*;

public class QuestItemNode extends DynamicNode<QuestItem>
{
	static ImageIcon icon_condition	= Tools.createIcon(Res.icon_quest_condition);
	static ImageIcon icon_result	= Tools.createIcon(Res.icon_quest_result);
	
	final QuestNode quest_node;
	
	public QuestItemNode(QuestNode quest, String name, Boolean is_award) {
		super(quest.getQuestItemManager(), name, is_award);
		this.quest_node = quest;
	}
	
	public QuestItemNode(QuestNode quest, QuestItem item) {
		super(item, item.getType(), item.name);
		this.quest_node = quest;
	}
	
	@Override
	protected QuestItem newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return new QuestItem(this.getIntID(), name, (Boolean)args[0]);
	}

	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		}
		return false;
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new QuestItemMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		if (getData().isResult()) {
			return icon_result;
		} else {
			return icon_condition;
		}
	}

	@Override
	public ObjectViewer<?> getEditComponent() {
		onOpenEdit();
		if (edit_component==null) {
			edit_component = new QuestItemEditor(this);
		}
		return edit_component;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	

	class QuestItemMenu extends NodeMenu<QuestItemNode>
	{
		public QuestItemMenu(JTree tree, QuestItemNode node) {
			super(node);
			super.remove(open);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rename) {
				String name = JOptionPane.showInputDialog(
						AbstractDialog.getTopWindow(getInvoker()), 
						"输入名字！",
						node.getData().name);
				if (name!=null && name.length()>0) {
					node.setName(name);
				}
				if (tree!=null) {
					tree.reload(node);
				}
			}
			else if (e.getSource() == delete) {
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				quest_node.getQuestItemManager().killID(node.getIntID());
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}

//	---------------------------------------------------------------------------------------------------------------

}
