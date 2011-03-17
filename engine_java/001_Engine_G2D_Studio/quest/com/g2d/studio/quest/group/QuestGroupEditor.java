package com.g2d.studio.quest.group;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.cell.rpg.quest.QuestGroup;
import com.g2d.studio.Studio;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.quest.QuestSelectCellEdit;
import com.g2d.studio.swing.G2DList;


public class QuestGroupEditor extends JPanel implements ActionListener
{
	final QuestGroup data;
	
	JToolBar 	tool_bar		= new JToolBar();
	JButton		btn_add_quest	= new JButton("添加");
	JButton		btn_del_quest	= new JButton("删除");
	JButton		btn_up_quest	= new JButton("上移");
	JButton		btn_down_quest	= new JButton("下移");
	
	G2DList<QuestNode>	list		= new G2DList<QuestNode>();
	Vector<QuestNode>	list_data	= new Vector<QuestNode>();
	
	public QuestGroupEditor(QuestGroup e) 
	{
		super(new BorderLayout());
		this.data = e;
		
		tool_bar.add(btn_add_quest);
		tool_bar.add(btn_del_quest);
		tool_bar.add(btn_up_quest);
		tool_bar.add(btn_down_quest);
		btn_add_quest.addActionListener(this);
		btn_del_quest.addActionListener(this);
		btn_up_quest.addActionListener(this);
		btn_down_quest.addActionListener(this);
		
		this.add(tool_bar, 
				BorderLayout.NORTH);
		
		this.add(new JScrollPane(list), 
				BorderLayout.CENTER);
		
		setData(data.quest_ids);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_add_quest) {
			QuestSelectCellEdit dialog = new QuestSelectCellEdit(this, false, null);
			QuestNode node = dialog.showDialog();
			if (node != null) {
				list_data.add(node);
				list.setListData(list_data);
				getData();
			}
		} 
		else if (e.getSource() == btn_del_quest) {
			QuestNode node = list.getSelectedItem();
			if (node != null) {
				list_data.remove(node);
				list.setListData(list_data);
				getData();
			}
		} 
		else if (e.getSource() == btn_up_quest) {
			QuestNode node = list.getSelectedItem();
			if (node != null) {
				int index = list_data.indexOf(node);
				if (index > 0) {
					list_data.remove(index);
					list_data.insertElementAt(node, index-1);
					list.setListData(list_data);
					list.setSelectedValue(node, true);
					getData();
				}
			}
		} 
		else if (e.getSource() == btn_down_quest) {
			QuestNode node = list.getSelectedItem();
			if (node != null) {
				int index = list_data.indexOf(node);
				if (index < list_data.size()-1) {
					list_data.remove(index);
					list_data.insertElementAt(node, index+1);
					list.setListData(list_data);
					list.setSelectedValue(node, true);
					getData();
				}
			}
		}
	}

	
	private void setData(Vector<Integer> ids) {
		synchronized (list_data) {
			list_data.clear();
			if (ids != null) {
				for (Integer id : ids) {
					try {
						QuestNode node = Studio.getInstance().getQuestManager().getQuest(id);
						if (node != null) {
							list_data.add(node);
						}
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
			}
			list.setListData(list_data);
		}
	}
	
	Vector<Integer> getData() {
		Vector<Integer> ret = new Vector<Integer>(list_data.size());
		synchronized (list_data) {
			for (QuestNode node : list_data) {
				ret.add(node.getIntID());
			}
		}
		data.quest_ids = ret;
		return ret;
	}
	
//	--------------------------------------------------------------------------------------------------------------------

}

