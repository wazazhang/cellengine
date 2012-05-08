package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.TriggersPackage;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.trigger.Event;

import com.g2d.awt.util.AbstractDialog;
import com.g2d.awt.util.TextEditor;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.Studio;
import com.g2d.studio.scene.script.TriggerPanel.TriggerEventRoot;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DWindowToolBar;

@SuppressWarnings("serial")
public class TriggerPanelScriptable extends TriggerPanel<SceneTriggerScriptable> 
implements ActionListener, AncestorListener, KeyListener, PropertyChangeListener
{
	TextEditor			script_text = new TextEditor();
	G2DWindowToolBar	tool_g2d	= new G2DWindowToolBar(this, true, true, true, false);
	JLabel				lbl			= new JLabel();
	
	String				last_script;
	
	public TriggerPanelScriptable(TriggersPackage pak, SceneTriggerScriptable trigger)
	{
		super(pak, trigger);
				
		tool_g2d.setFloatable(false);
		getMainPanel().add(tool_g2d, BorderLayout.NORTH);
		
		last_script = "";
		script_text.setPreferredSize(new Dimension(200, 400));
		Font font = new Font(StudioConfig.DEFAULT_FONT, 
				script_text.getFont().getStyle(), 
				script_text.getFont().getSize());
		script_text.getTextPane().setFont(font);
		getMainPanel().add(script_text, BorderLayout.CENTER);
		this.addAncestorListener(this);
		
		script_text.getTextPane().addKeyListener(this);
		script_text.getTextPane().addPropertyChangeListener(this);
		load();
	}

//	-------------------------------------------------------------------------------------------------------
	
	void createNew() {
		String text = "";
		for (Class<? extends Event> evt : trigger.getEventTypes()) {
			text += Studio.getInstance().getSceneScriptManager().createTemplateScript(
					pak, evt, trigger) + "\n";
		}
		script_text.setText(text);
	}
	
	void load() {
		last_script = Studio.getInstance().getSceneScriptManager().loadTriggers(
				pak, Studio.getInstance().project_path.getPath(), trigger);
		if (last_script == null) {
			last_script = "";
		}
		script_text.setText(last_script);
		
		refreshChanged();
	}
	
	void save() {
		last_script = script_text.getText();
		Studio.getInstance().getSceneScriptManager().saveTriggers(
				pak, Studio.getInstance().project_path.getPath(), trigger, last_script);
		
		refreshChanged();
	}
	
	public boolean hasChanged() {
		return !script_text.getText().equals(last_script);
	}
	
//	-------------------------------------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_g2d.new_) {
			if (!script_text.getText().isEmpty()) {
				if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(this, "确定要自动生成新的脚本？")) {
					return;
				}
			}
			createNew();
		}
		else if (e.getSource() == tool_g2d.load) {
			load();
		}
		else if (e.getSource() == tool_g2d.save) {
			save();
		}
	}

	protected void onAddEventNode(TriggerEventRoot.EventNode en) {
		if (script_text.getText().isEmpty()) {
			createNew();
		}
	}

	protected void onRemoveEventNode(TriggerEventRoot.EventNode en) {}

	protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node) {}
	
	
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		super.ancestorRemoved(event);
//		if (hasChanged()) {
//			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this, "关闭前保存脚本？")) {
//				save();
//			}
//		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {
		refreshChanged();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
	private void refreshChanged() {
		TriggersEditor top = AbstractDialog.getTopComponent(this, TriggersEditor.class);
		if (top != null) {
			top.refreshChanged();
		}
	}
}
