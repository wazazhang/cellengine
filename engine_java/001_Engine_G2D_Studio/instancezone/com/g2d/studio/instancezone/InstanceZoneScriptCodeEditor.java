package com.g2d.studio.instancezone;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

//import com.cell.rpg.scene.script.SceneScriptManager;
import com.cell.rpg.struct.InstanceZoneScriptCode;
import com.g2d.awt.util.AbstractOptionDialog;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;

@SuppressWarnings("serial")
public class InstanceZoneScriptCodeEditor extends AbstractOptionDialog<InstanceZoneScriptCode> implements 
PropertyCellEdit<InstanceZoneScriptCode>
{
//	-------------------------------------------------------------------------------------------
	InstanceZoneNode 	zone;
//	-------------------------------------------------------------------------------------------
	JToolBar			tool_bar		= new JToolBar();
	JButton				btn_change_zone	= new JButton("改变副本数据");
	JButton				btn_build		= new JButton("编译");

	JPanel				center			= new JPanel(new BorderLayout());
	
	JSplitPane			split_lr 		= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JToolBar			left_bar		= new JToolBar();
	JList				var_list		= new JList();
	JTextPane 			text_pane 		= new JTextPane();
	
	JToolBar			state_bar		= new JToolBar();
	JLabel				build_state		= new JLabel();
	
//	-------------------------------------------------------------------------------------------
	JLabel				label			= new JLabel();
//	-------------------------------------------------------------------------------------------
	
	
	public InstanceZoneScriptCodeEditor(Component owner, InstanceZoneScriptCode old) 
	{
		super(owner);
		this.resetTitle(old);
		
		{	
			tool_bar		.setFloatable(false);
			tool_bar		.add(btn_change_zone);
			tool_bar		.addSeparator();
			tool_bar		.add(btn_build);
			btn_change_zone	.addActionListener(this);
			btn_build		.addActionListener(this);
		}
		super.add(tool_bar, BorderLayout.NORTH);
		
		
		{
			JPanel left = new JPanel(new BorderLayout());
			left.setMinimumSize(new Dimension(160, 100));
			left.setPreferredSize(new Dimension(160, 100));
			
			text_pane.setFont(Studio.getInstance().getFont());
			
			left_bar.setFloatable(false);
			left_bar.add(new JLabel("变量列表"));
			var_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			var_list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						Object obj = var_list.getSelectedValue();
						if (obj != null) {
							try {
								int pos = text_pane.getCaretPosition();
								String text = text_pane.getText();
								String ntext = text.substring(0, pos) + obj + text.substring(pos);
								text_pane.setText(ntext);
								text_pane.setCaretPosition(pos+obj.toString().length());
							} catch (Exception err) {}
						}
					}
				}
			});
			
			left.add(left_bar, BorderLayout.NORTH);
			left.add(new JScrollPane(var_list), BorderLayout.CENTER);
			split_lr.setLeftComponent(left);
	
			JPanel right = new JPanel(new BorderLayout());
			right.add(new JScrollPane(text_pane), BorderLayout.CENTER);
			split_lr.setRightComponent(right);
			
			center.add(split_lr, BorderLayout.CENTER);
		}
		{
			state_bar.add(build_state);
			center.add(state_bar, BorderLayout.SOUTH);
		}
		super.add(center, BorderLayout.CENTER);
		
	
		
		if (old != null) {
			text_pane.setText(old.script+"");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == btn_change_zone) {
			zone = new InstanceZoneSelectDialog(this, null).showDialog();
			if (zone != null) {
				InstanceZoneScriptCode data = getUserObject(null);
				resetTitle(data);
			}
		}
		else if (e.getSource() == btn_build) {
			try {
				if (zone != null) {
					HashMap<String, Object> data_map = zone.getData().getData().asMap();
//					if (Studio.getInstance().getSceneScriptManager().checkScriptCode(
//							getUserObject(null), 
//							data_map
//							)) {
//						build_state.setText("<html><body><font color=\"#00ff00\">succeed</font></body></html>");
//					} else {
//						build_state.setText("<html><body><font color=\"#ff0000\">未知错误</font></body></html>");
//					}
				}
			} catch (Exception err) {
				err.printStackTrace();
				build_state.setText("<html><body><font color=\"#ff0000\">"+err.getMessage()+"</font></body></html>");
			}
		}
	}
	
	private void resetTitle(InstanceZoneScriptCode old) {
		if (old != null) {
			zone = Studio.getInstance().getInstanceZoneManager().getNode(old.instance_zone_id);
			if (zone != null) {
				super.setTitle(zone.getListName() + " :: " + old);
				this.var_list.setListData(new Vector<String>(zone.getData().getData().keySet()));
			} else {
				super.setTitle(old.instance_zone_id + " :: " + old);
				this.var_list.setListData(new Object[]{});
			}
		} else {
			super.setTitle("null");
			this.var_list.setListData(new Object[]{});
		}
	}
	
	public static String getTitle(InstanceZoneScriptCode old) {
		if (old != null) {
			InstanceZoneNode zone = Studio.getInstance().getInstanceZoneManager().getNode(old.instance_zone_id);
			if (zone != null) {
				return (zone.getListName() + " :: " + old);
			} else {
				return (old.instance_zone_id + " :: " + old);
			}
		} else {
			return "null";
		}
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		label.setText(getTitle());
		return label;
	}

	@Override
	protected InstanceZoneScriptCode getUserObject(ActionEvent e) {
		InstanceZoneScriptCode data = new InstanceZoneScriptCode();
		data.script = text_pane.getText();
		if (zone != null) {
			data.instance_zone_id = zone.getIntID();
		}
		return data;
	}

	@Override
	public InstanceZoneScriptCode getValue() {
		return getSelectedObject();
	}
	
}
