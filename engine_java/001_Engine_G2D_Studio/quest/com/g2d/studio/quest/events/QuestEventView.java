package com.g2d.studio.quest.events;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.cell.rpg.quest.Quest;

public class QuestEventView extends JSplitPane implements TreeSelectionListener
{
	private Quest 			quest;
	
	private QuestEventTree	tree;
	
	private JScrollPane		left;
	private JPanel			right;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestEventView(Quest quest)
	{
		super(HORIZONTAL_SPLIT);
		super.setMinimumSize(new Dimension(200, 180));
		this.quest	= quest;
		this.tree	= new QuestEventTree();
		
		this.left	= new JScrollPane(tree);
		this.right	= new JPanel();
		left.setMinimumSize(new Dimension(200, 180));
		right.setMinimumSize(new Dimension(200, 180));
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
		tree.addTreeSelectionListener(this);
		
		setQuest(quest);
		

	}
	
	void setQuest(Quest quest) 
	{
		tree.setQuest(quest);
		tree.reload();
		tree.expandAll();
	}
	
	public void save() {}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getLastPathComponent() instanceof QuestEventNode) {
			QuestEventNode node = (QuestEventNode)e.getPath().getLastPathComponent();
			this.setRightComponent(node.getEditComponent());
		} else {
			this.setRightComponent(right);
		}
	}
	
}
