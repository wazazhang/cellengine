package com.g2d.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.cell.reflect.Parser;
import com.cell.util.DateUtil.TimeObject;
import com.g2d.awt.util.AbstractOptionDialog;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;


public class DialogTimeObjectEdit extends AbstractOptionDialog<TimeObject> implements PropertyCellEdit<TimeObject>
{
	private static final long serialVersionUID = 1L;

	JLabel edit_label = new JLabel();
	
	final TimeObject src;
	
	ListEnumEdit<TimeUnit>	combo_time_unit		= new ListEnumEdit<TimeUnit>(TimeUnit.class);
	JSpinner 				combo_time_value	= new JSpinner(new SpinnerNumberModel(
			new Long(1), 
			new Long(Long.MIN_VALUE), 
			new Long(Long.MAX_VALUE), 
			new Long(1)));
	
	public DialogTimeObjectEdit(Component owner, TimeObject src)
	{
		super(owner);
		
		if (src != null) {
			this.src = src;
		} else {
			this.src = new TimeObject();
		}
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(combo_time_value, BorderLayout.CENTER);
		panel.add(combo_time_unit, BorderLayout.EAST);
		this.add(panel, BorderLayout.CENTER);
		
		try{
			combo_time_unit.setSelectedItem(this.src.time_unit);
			combo_time_value.setValue(this.src.time_value);
		}catch(Exception err){
			err.printStackTrace();
		}
		
		super.setSize(400, 100);
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		edit_label.setText(src+"");
		return edit_label;
	}
	
	@Override
	public TimeObject getValue() {
		return getSelectedObject();
	}
	
	@Override
	protected TimeObject getUserObject(ActionEvent e) {
		src.time_unit = combo_time_unit.getValue();
		src.time_value = Parser.castNumber(combo_time_value.getValue(), Long.class);
		return src;
	}
	
//	@Override
//	protected Object[] getUserObjects()
//	{
//		Object[] objs = new Object[1];				
//		objs[0] = getUserObject();				
//		return objs;
//	}		
}
