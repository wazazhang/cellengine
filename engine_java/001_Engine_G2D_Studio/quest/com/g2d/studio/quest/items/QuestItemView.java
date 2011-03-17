package com.g2d.studio.quest.items;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.g2d.studio.quest.QuestNode;

public class QuestItemView extends JSplitPane implements TreeSelectionListener
{
	private QuestNode		quest;
	
	private QuestItemTree	tree;
	
	private JScrollPane		left;
	private JPanel			right;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestItemView(QuestNode quest)
	{
		super(HORIZONTAL_SPLIT);
		super.setMinimumSize(new Dimension(200, 200));
		this.quest	= quest;
		this.tree	= new QuestItemTree(quest);
		
		this.left	= new JScrollPane(tree);
		this.right	= new JPanel(new BorderLayout());
		left.setMinimumSize(new Dimension(200, 200));
		right.setMinimumSize(new Dimension(200, 200));
		
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
		tree.addTreeSelectionListener(this);
		
	}
	
//	void setQuest(Quest quest) 
//	{
//		tree.reload();
//		tree.expandAll();
//	}
	
	public void save() {
		tree.save();
	}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getLastPathComponent() instanceof QuestItemNode) {
			QuestItemNode node = (QuestItemNode)e.getPath().getLastPathComponent();
			this.setRightComponent(node.getEditComponent());
		} else {
			this.setRightComponent(right);
		}
	}
	
}
