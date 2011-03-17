package com.g2d.studio.quest.group;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import com.cell.CIO;
import com.cell.rpg.quest.QuestGroup;
import com.g2d.awt.util.*;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.quest.group.QuestGroupTreeView.QuestGroupGroup;
import com.g2d.studio.res.Res;

final public class DQuestGroup extends DynamicNode<QuestGroup>
{
	private static final long serialVersionUID = 1L;
	
	QuestGroupTreeView factory;
	
	public DQuestGroup(QuestGroupTreeView f, String name) {
		super(f, name);
		this.factory = f;
	}
	
	public DQuestGroup(QuestGroupTreeView f, QuestGroup g) {
		super(g, Integer.parseInt(g.id), g.name);
		this.factory = f;
	}
	
	@Override
	protected QuestGroup newData(IDynamicIDFactory<?> f, String name, Object... args) {
		this.factory = (QuestGroupTreeView)f;
		return new QuestGroup(getIntID(), name);
	}
	
	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public ImageIcon getIcon(boolean update) {
		return Tools.createIcon(Res.icon_res_3);
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new QuestGroupMenu().show(tree, e.getX(), e.getY());
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		onOpenEdit();
		if (edit_component == null) {
			edit_component = new QuestGroupViewer();
		}
		return edit_component;
	}
	
	
	
//	----------------------------------------------------------------------------------------------------------------------
	public class QuestGroupMenu extends DynamicNodeMenu
	{
		protected JMenuItem 		clone	= new JMenuItem("复制");
		
		public QuestGroupMenu() {
			super(DQuestGroup.this);
			super.add(clone);
			super.remove(delete);
			super.add(delete);
			clone.addActionListener(this);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clone) {
				if (node.getParent() instanceof QuestGroupGroup) {
					QuestGroupGroup	root	= (QuestGroupGroup)node.getParent();
					QuestGroup		src		= CIO.cloneObject(DQuestGroup.this.getData());
					DQuestGroup		group	= new DQuestGroup(factory, src.name+" Copy");
					factory.addNode(root, group);
					factory.getTree().reload(root);
				}
			}
			else {
				super.actionPerformed(e);
			}
		}
	}
	
	
	public class QuestGroupViewer extends ObjectViewer<DQuestGroup>
	{
		private static final long serialVersionUID = 1L;
		
		public QuestGroupViewer() {
			super(DQuestGroup.this);
			table.insertTab("编组数据", null, new QuestGroupEditor(getData()), "", 0);
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
		
	}
}
