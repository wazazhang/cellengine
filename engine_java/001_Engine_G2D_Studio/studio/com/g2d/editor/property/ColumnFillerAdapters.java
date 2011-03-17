package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.g2d.Engine;
import com.g2d.editor.property.ObjectPropertyRowPanel.ColumnFiller;

@SuppressWarnings("serial")
public class ColumnFillerAdapters 
{
	public static class ColumnFillerClipboard extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充粘贴";
				
		private JTextArea info = new JTextArea();
		
		public ColumnFillerClipboard() {
			super(new BorderLayout());
			info.setEditable(false);
			this.add(info, BorderLayout.CENTER);
		}
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			String text = Engine.getEngine().getClipboardText();
			String[] lines = CUtil.splitString(text, "\n");
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line.trim()+"\n");
			}
			info.setText(sb.toString());
			return TITLE;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			try {
				String[] lines = CUtil.splitString(info.getText(), "\n");
				for (int i = 0; i < rowDatas.size(); i++) {
					if (i < lines.length) {
						ret.add(Parser.stringToObject(lines[i].trim(), columnType.getType()));
					} else {
						break;
					}
				}
			} catch (Exception err) {	
				err.printStackTrace();
			}
			return ret;
		}
	}

	
//	-----------------------------------------------------------------------------------------------------
	

	public static class ColumnFillerObject extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充当前对象";
		
		private Object field_data;
		
		private JTextArea info = new JTextArea();
		
		public ColumnFillerObject() {
			super(new BorderLayout());
			info.setEditable(false);
			this.add(info, BorderLayout.CENTER);
		}
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (Serializable.class.isAssignableFrom(columnField.getType())) {
				try {
					field_data = columnField.get(row_data);
					info.setText(field_data + "");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			if (field_data != null) {
				for (int i = 0; i < rowDatas.size(); i++) {
					ret.add(CIO.cloneObject(field_data));
				}
			} else {
				for (int i = 0; i < rowDatas.size(); i++) {
					ret.add(null);
				}
			}
			return ret;
		}
	}

	
//	-----------------------------------------------------------------------------------------------------
	
	public static class ColumnFillerBoolean extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充Boolean";
		
		JCheckBox checkbox = new JCheckBox("value");
		
		public ColumnFillerBoolean() {
			this.add(checkbox);
		}
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (Boolean.class.isAssignableFrom(columnField.getType()) ||
				boolean.class.isAssignableFrom(columnField.getType())) {
				try {
					Object obj = columnField.get(row_data);
					checkbox.setSelected((Boolean)obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			for (int i = 0; i < rowDatas.size(); i++) {
				ret.add(checkbox.isSelected());
			}
			return ret;
		}
	}

//	-----------------------------------------------------------------------------------------------------
	

	public static class ColumnFillerString extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充字符串";
		
		JTextField text = new JTextField();
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (String.class.isAssignableFrom(columnField.getType())) {
				try {
					Object obj = columnField.get(row_data);
					text.setText(obj + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			for (int i = 0; i < rowDatas.size(); i++) {
				ret.add(text.getText());
			}
			return ret;
		}
	}

//	-----------------------------------------------------------------------------------------------------
	
	public static class ColumnFillerNumber extends JPanel implements ColumnFiller, ChangeListener
	{
		public static String TITLE = "填充数字";
		
		JSplitPane split = new JSplitPane();
		
		JLabel				lb_base_value	= new JLabel("基础值");
		JLabel				lb_prev_multi	= new JLabel("乘因素");
		JLabel				lb_prev_add		= new JLabel("加因素");
		
		SpinnerNumberModel	num_base_value	= new SpinnerNumberModel(0, -Double.MAX_VALUE, Double.MAX_VALUE, 1);
		SpinnerNumberModel	num_prev_multi	= new SpinnerNumberModel(1, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1);
		SpinnerNumberModel	num_prev_add	= new SpinnerNumberModel(0, -Double.MAX_VALUE, Double.MAX_VALUE, 1);
		
		JSpinner			sp_base_value	= new JSpinner(num_base_value);
		JSpinner			sp_prev_multi	= new JSpinner(num_prev_multi);
		JSpinner			sp_prev_add		= new JSpinner(num_prev_add);
		
		JTable				example;
		Vector<Object>		example_datas	= new Vector<Object>();
		
		public ColumnFillerNumber() 
		{
			super(new BorderLayout());
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JSpinner) {
				refreshExample();
			}
		}
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (Parser.isNumber(columnField.getType())) {
				try {
					Double value = Parser.castNumber(columnField.get(row_data), Double.class);
					num_base_value.setValue(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel,
				Field columnType, ArrayList<?> rowDatas) 
		{
			example_datas = new Vector<Object>(rowDatas.size());
			for (Object row_data : rowDatas) {
				example_datas.add(0d);
			}
			
			JPanel left = new JPanel(null);
			{
				int sx = 2, sy = 2, sh = 24;
				
				lb_base_value.setBounds(sx,       sy, 100, sh);
				sp_base_value.setBounds(sx + 102, sy, 200, sh);
				sy += sh;
				lb_prev_multi.setBounds(sx,       sy, 100, sh);
				sp_prev_multi.setBounds(sx + 102, sy, 200, sh);
				sy += sh;
				lb_prev_add.setBounds(sx,       sy, 100, sh);
				sp_prev_add.setBounds(sx + 102, sy, 200, sh);
				sy += sh;
				
				left.add(lb_base_value);	left.add(sp_base_value);
				left.add(lb_prev_multi);	left.add(sp_prev_multi);
				left.add(lb_prev_add);		left.add(sp_prev_add);
				
				sp_base_value.addChangeListener(this);
				sp_prev_multi.addChangeListener(this);
				sp_prev_add.addChangeListener(this);
				
				Dimension msize = new Dimension(320, sh * 3 + 10);
				left.setPreferredSize(msize);
				left.setMinimumSize(msize);
			}
			JPanel right = new JPanel(new BorderLayout());
			{
				String[] header = new String[] { "level", "value" };
				Object[][] rows = new Object[example_datas.size()][2];
				example = new JTable(rows, header);
				right.add(new JScrollPane(example), BorderLayout.CENTER);
			}
			split.setLeftComponent(left);
			split.setRightComponent(right);
			this.add(split, BorderLayout.CENTER);
			
			refreshExample();
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel,
				Field columnType, ArrayList<?> rowDatas) {
			return new ArrayList<Object>(example_datas);
		}

		private void refreshExample()
		{
			double base 	= (Double)num_base_value.getValue();
			double multi 	= (Double)num_prev_multi.getValue();
			double add 		= (Double)num_prev_add.getValue();
			
			for (int r = 0; r < example_datas.size(); r++) {
				example_datas.set(r, base);
				example.setValueAt(r, r, 0);
				example.setValueAt(base, r, 1);
				base = base * multi + add;
			}
		}
	}
	
	
}
