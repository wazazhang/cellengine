package com.g2d.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.g2d.Version;
import com.g2d.display.ui.Form;

public class FormEditor extends UIComponentEditor<Form> 
{
	private static final long serialVersionUID = Version.VersionG2D;

	
	JPanel 		pan_form_property		= new JPanel();
	
	
	JCheckBox	chk_enable_close		= new JCheckBox();
	
	public FormEditor(Form form)
	{
		super(form);
		
		pan_form_property.setLayout(null);
		{

			int sx = 10, sy = -10;
			chk_enable_close.setText("是否有关闭按钮");
			chk_enable_close.setBounds(sx, sy += 20, 200, 20);
			chk_enable_close.setSelected(object.getCloseEnable());
			chk_enable_close.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.DESELECTED) {
						object.setCloseEnable(false);
					}else if (e.getStateChange() == ItemEvent.SELECTED) {
						object.setCloseEnable(true);
					}
				}
			});
			
			pan_form_property.add(chk_enable_close);
			
		}
		table.addTab("窗口属性", pan_form_property);
	}
	
	
	
}
