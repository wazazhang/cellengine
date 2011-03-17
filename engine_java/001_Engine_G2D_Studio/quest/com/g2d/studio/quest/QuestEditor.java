package com.g2d.studio.quest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.cell.io.CFile;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.io.File;
import com.g2d.studio.quest.events.QuestEventView;
import com.g2d.studio.quest.items.QuestItemView;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.awt.util.*;


public class QuestEditor extends ObjectViewer<QuestNode> implements RPGSerializationListener, ActionListener
{	
	JToolBar			toolbar = new JToolBar();
	JButton				btn_discussion = new JButton(Tools.createIcon(Res.icon_action));
	
	
//	PanelDiscussion		page_quest_discussion;
	JSplitPane			page_quest_data;

	QuestEventView		data_event;
	QuestItemView		data_quest;
	
//	-------------------------------------------------------------------------------------
	
	public QuestEditor(QuestNode node) {
		super(node);
		
		if (node.getData().getRPGSerializationListeners() == null ||
			node.getData().getRPGSerializationListeners().contains(this)==false) {
			node.getData().addRPGSerializationListener(this);
		}
		
		btn_discussion.setToolTipText("任务脚本");
		btn_discussion.addActionListener(this);
		toolbar.add(btn_discussion);
		
		this.add(toolbar, BorderLayout.NORTH);
		
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{
		data_event			= new QuestEventView(tobject.getData());
		data_quest			= new QuestItemView(tobject);
//		page_quest_discussion	= new PanelDiscussion();
		page_quest_data			= new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, 
				data_event, 
				data_quest);
		
		table.removeAll();
//		table.addTab("任务内容", page_quest_discussion);
		table.addTab("任务数据", page_quest_data);
		table.addTab("附加属性", page_object_panel);
	}

	@Override
	public void onReadComplete(RPGObject object) {
	}
	
	@Override
	public void onWriteBefore(RPGObject object) {
		data_event.save();
		data_quest.save();
//		tobject.getData().setDiscussion(page_quest_discussion.getText());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_discussion) {
			new DiscussionForm(
					Studio.getInstance().project_save_path.getChildFile(
					"quests/" + tobject.getIntID() + ".xml" + Config.TALK_SUFFIX)
					).setVisible(true);
		}
	}
	
//	-------------------------------------------------------------------------------------
	
	static class DiscussionForm extends AbstractFrame implements ActionListener, WindowListener
	{		
		final File 			file;

		G2DWindowToolBar	toolbar	= new G2DWindowToolBar(this);
		TextEditor 			text	= new TextEditor();
		
		String				src		;
		
		public DiscussionForm(File file) 
		{
			super.setSize(800, 600);
			super.setIconImage(Res.icon_edit);
			super.setCenter();
			super.setTitle(file.getName());
			super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			super.addWindowListener(this);
			
			this.file = file;
			
			add(toolbar, 	BorderLayout.NORTH);
			add(text, 		BorderLayout.CENTER);
			
			if (file.exists()) {
				this.src = file.readUTF();
			} else {
				this.src = Studio.getInstance().talk_example;
			}

			text.setText(src);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == toolbar.save) {
				file.writeUTF(text.getText());
			}
		}
		
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) {
			String dst = text.getText();
			if (!dst.equals(src)) {
				int result = JOptionPane.showConfirmDialog(this, "关闭该任务脚本前是否要保存？", "确认", JOptionPane.YES_NO_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					file.writeUTF(dst);
					this.setVisible(false);
				} else if (result == JOptionPane.NO_OPTION) {
					this.setVisible(false);
				}
			} else {
				this.setVisible(false);
			}
		}
		@Override
		public void windowDeactivated(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowOpened(WindowEvent e) {}
		
	}
	
//	class PanelDiscussion extends TextEditor
//	{		
//		public PanelDiscussion() {
//			setText(tobject.getData().getDiscussion());
//		}
//	}

//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
}
