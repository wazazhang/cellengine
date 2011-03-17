package com.g2d.studio.particles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import com.g2d.awt.util.CompositeRule;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.Config;
import com.g2d.studio.gameedit.EffectEditor;

public class ParticleViewer extends JFrame implements ActionListener
{
	final private EffectEditor cur_edit;

	DisplayObjectPanel	display_object_panel;
	JToolBar 			tools 					= new JToolBar();
	JComboBox			composite_list			= new JComboBox(CompositeRule.getEnumNames());
	
	JCheckBox			toggle_show_debug		= new JCheckBox("显示坐标系");
	JCheckBox			toggle_show_origin		= new JCheckBox("显示产生区域");
	JCheckBox			toggle_show_bounds		= new JCheckBox("显示产生范围");
	JButton				btn_change_color		= new JButton("改变背景色");
	
	ParticleStage		stage;
	
	public ParticleViewer(EffectEditor data) 
	{
		super.setTitle("粒子查看器 : " + data.getData().getName());
		super.setSize(800, 600);
		super.setAlwaysOnTop(true);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.cur_edit = data;
		{
//			tools.add(new JLabel("混合模式"));
//			tools.add(composite_list);
//			composite_list.addActionListener(this);
//			composite_list.setSelectedItem(CompositeRule.SRC_OVER.name());
			
			tools.add(toggle_show_debug);
			toggle_show_debug.addActionListener(this);
			
			tools.add(toggle_show_origin);
			toggle_show_origin.addActionListener(this);
		
			tools.add(toggle_show_bounds);
			toggle_show_bounds.addActionListener(this);
			
			tools.add(btn_change_color);
			btn_change_color.setBackground(Color.BLACK);
			btn_change_color.addActionListener(this);
		}
		
		display_object_panel	= new DisplayObjectPanel(new DisplayObjectPanel.ObjectStage(com.g2d.Color.GREEN));
		display_object_panel.getCanvas().setFPS(Config.DEFAULT_FPS);
		
		add(display_object_panel, BorderLayout.CENTER);
		add(tools, BorderLayout.SOUTH);

		stage = new ParticleStage(cur_edit);
		display_object_panel.getCanvas().changeStage(stage);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == composite_list) {
			try{
				String name = composite_list.getSelectedItem().toString();
				CompositeRule rule = CompositeRule.forName(name);
//				ParticleDisplay.composite_rule = rule.rule;
				System.out.println("Change composite rule : " + rule);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		else if (e.getSource() == btn_change_color) {
			Color color = JColorChooser.showDialog(this, "选择背景色", btn_change_color.getBackground());
			if (color != null) {
				btn_change_color.setBackground(color);
				stage.back_color = color;
			}
		}
		else if (e.getSource() == toggle_show_debug) {
			stage.is_show_cross = toggle_show_debug.isSelected();
		}
		else if (e.getSource() == toggle_show_origin) {
			stage.is_show_spawn_region = toggle_show_origin.isSelected();
		}
		else if (e.getSource() == toggle_show_bounds) {
			stage.is_show_spawn_bounds = toggle_show_bounds.isSelected();
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			display_object_panel.start();
		} else {
			display_object_panel.stop();
		}
		super.setVisible(b);
	}
	
}
