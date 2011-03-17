package com.g2d.studio.quest.events;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.quest.QuestGenerator;
import com.g2d.studio.swing.G2DTree;

public class QuestEventTree extends G2DTree implements MouseListener
{
	private Quest quest ;

//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestEventTree()
	{
		super(new DefaultMutableTreeNode("事件"));
		super.setDragEnabled(true);
		super.setMinimumSize(new Dimension(200, 200));
		super.addMouseListener(this);
	}
	
	void setQuest(Quest quest)
	{
		this.quest = quest;
		
		getRoot().removeAllChildren();
		
		if (quest.quest_generator == null) {
			quest.quest_generator = new QuestGenerator();
		}
		for (AbstractAbility ab : quest.quest_generator.getAbilities()) {
			QuestEventNode node = new QuestEventNode(ab);
			getRoot().add(node);
		}
		
		reload();
	}

//	----------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getButton() == MouseEvent.BUTTON3) {
			int selRow = getRowForLocation(e.getX(), e.getY());
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			setSelectionPath(selPath);
			if (selRow != -1) {
				Object obj = selPath.getLastPathComponent();
				if (obj == getRoot()) {
					new RootMenu().show(this, e.getX(), e.getY());
				} else if (obj instanceof QuestEventNode) {
					QuestEventNode node = (QuestEventNode) obj;
					new NodeMenu(node.ability).show(this, e.getX(), e.getY());
				}
			}
		}
		
	}
	


//	----------------------------------------------------------------------------------------------------------------------------------
	
	class RootMenu extends JPopupMenu implements ActionListener
	{
		HashMap<Object, Class<?>> type_map = new HashMap<Object, Class<?>>();
		
		public RootMenu() 
		{
			for (Class<?> type : quest.quest_generator.getSubAbilityTypes()) {
				JMenuItem item = new JMenuItem("添加 : " + AbstractAbility.getEditName(type));
				this.add(item);
				type_map.put(item, type);
				item.addActionListener(this);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (type_map.containsKey(e.getSource())) {
				Class<?> type = type_map.get(e.getSource());
				try{
					AbstractAbility ab = (AbstractAbility)type.newInstance();
					quest.quest_generator.addAbility(ab);
					setQuest(quest);
				}catch(Exception err){
					err.printStackTrace();
				}
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	class NodeMenu extends JPopupMenu implements ActionListener
	{
		JMenuItem del_item = new JMenuItem("删除");
		AbstractAbility ability;
		public NodeMenu(AbstractAbility ability) {
			this.add(del_item);
			this.del_item.addActionListener(this);
			this.ability = ability;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				quest.quest_generator.removeAbility(ability);
				setQuest(quest);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
}
