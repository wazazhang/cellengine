package com.g2d.studio.instancezone;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.cell.rpg.instance.zones.Data;
import com.cell.util.Pair;
import com.cell.util.PairEntry;
import com.g2d.editor.property.ListEnumEdit;
import com.g2d.editor.property.BaseObjectPropertyPanel.NullEditor;
import com.g2d.studio.Studio;
import com.g2d.awt.util.*;

@SuppressWarnings("serial")
public class InstanceZoneDataTable extends JPanel implements ActionListener
{
	private Data zone_data;
	
	JToolBar bar = new JToolBar();
	JButton btn_add_row = new JButton("添加变量");
	JButton btn_del_row = new JButton("删除变量");
	JButton btn_set_row = new JButton("设置变量");

	final String[]	columns = new String[] { "名字", "类型", "初始值" };
	
	DataTableModel	table_model;
	JTable			table;
	
	public InstanceZoneDataTable(Data zone_data) 
	{
		super(new BorderLayout());
		
		this.zone_data 		= zone_data;
		this.table_model	= new DataTableModel();
		{
			this.bar.setFloatable(false);
			this.bar.add(btn_add_row);
			this.bar.add(btn_set_row);
			this.bar.add(btn_del_row);
			this.btn_add_row.addActionListener(this);
			this.btn_set_row.addActionListener(this);
			this.btn_del_row.addActionListener(this);
		}
		super.add(bar, BorderLayout.NORTH);

		{
			this.table = new JTable(table_model);
//			this.table.setFillsViewportHeight(true);
			this.table.setFont(Studio.getInstance().getFont());
			this.table.setAutoCreateRowSorter(true);
			this.table.setRowHeight(25);
			
			this.table.getColumn(columns[0]).setCellEditor(new NullEditor());
			this.table.getColumn(columns[1]).setCellEditor(new NullEditor());
//			this.table.getColumn(columns[2]).setCellEditor(new NullEditor());
			
			this.table.getColumn(columns[0]).setWidth(100);
			this.table.getColumn(columns[1]).setWidth(50);
		}
		super.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_add_row) {
			Entry<String, Object> add = new  AddRowDialog().showDialog();
			table_model.addRow(add);
			table.repaint();
		}
		else if (e.getSource() == btn_set_row) {
			table_model.setRow(table.getSelectedRow());
			table.repaint();
		}
		else if (e.getSource() == btn_del_row) {
			table_model.deleteRow(table.getSelectedRows());
			table.repaint();
		}
	}

	class DataTableModel extends AbstractTableModel
	{
		public DataTableModel() {}
		
		private Object[][] as_rows() {
			Set<Entry<String, Object>> set = zone_data.entrySet();
			Object[][] rows = new Object[set.size()][columns.length];
			int r = 0;
			for (Entry<String, Object> e : set) {
				rows[r][0] = e.getKey();
				rows[r][1] = Number.class.isInstance(e.getValue()) ? Type.NUMBER : Type.STRING ;
				rows[r][2] = e.getValue();
				r++;
			}
			return rows;
		}
		
		void addRow(Entry<String, Object> add) {
			if (add != null) {
				zone_data.put(add.getKey(), add.getValue());
//				Object[][] rows = as_rows();
//				for (int i=0; i<rows.length; i++) {
//					if (rows[i][0].equals(add.getKey())) {
//						fireTableRowsInserted(i, i);
//					}
//				}
				fireTableStructureChanged();
			}
		}
		
		void setRow(int selected) {
			Object k = getValueAt(selected, 0);
			Object v = getValueAt(selected, 1);
			if (k != null) {
				Entry<String, Object> add = new  AddRowDialog(k.toString(), v).showDialog();
				if (add != null) {
					zone_data.put(add.getKey(), add.getValue());
				}
//				fireTableRowsUpdated(selected, selected);
				fireTableDataChanged();
			}
		}
		
		void deleteRow(int[] selected) {
			if (selected.length > 0) {
				ArrayList<String> removed = new ArrayList<String>();
				for (int r : selected) {
					Object row = table.getModel().getValueAt(r, 0);
					if (row != null) {
						removed.add(row.toString());
					}
				}	
				for (String r : removed) {
					zone_data.remove(r);
				}
//				fireTableRowsDeleted(
//						Math.min(selected[0], selected[selected.length-1]), 
//						Math.max(selected[0], selected[selected.length-1]));
				fireTableStructureChanged();
			}
		}
		
		@Override
		public String getColumnName(int column) {
			return columns[column];
		}
		@Override
		public int getColumnCount() {
			return columns.length;
		}
		@Override
		public int getRowCount() {
			return zone_data.size();
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				return as_rows()[rowIndex][columnIndex];
			} catch (Exception err) {
				return null;
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 2) {
				return true;
			}
			return false;
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if (col == 2) {
				Object[][] rows = as_rows();
				try {
					if (rows[row][1].equals(Type.NUMBER)) {
						Double v = Double.parseDouble(value + "");
						zone_data.put(rows[row][0].toString(), v);
					} else {
						zone_data.put(rows[row][0].toString(), value.toString());
					}
					fireTableCellUpdated(row, col);
				} catch (Exception err) {
				}
			}
		}
	}
	
	class AddRowDialog extends AbstractOptionDialog<Entry<String, Object>>
	{
		JTextField 			var_name 	= new JTextField();
		ListEnumEdit<Type> 	var_type 	= new ListEnumEdit<Type>(Type.class);
		JTextField 			var_value 	= new JTextField();
		
		public AddRowDialog(){
			this(null, null);
		}
		
		public AddRowDialog(String default_key, Object value)
		{
			super(InstanceZoneDataTable.this);
			super.setTitle("输入变量名");
			JPanel center = new JPanel(null);
			int sy = 10;
			var_name	.setBounds(10, sy, 200, 32); sy += 34;
			var_type	.setBounds(10, sy, 200, 32); sy += 34;
			var_value	.setBounds(10, sy, 200, 32); sy += 34;
			center.add(var_name);
			center.add(var_type);
			center.add(var_value);
			super.add(center, BorderLayout.CENTER);
			
			if (default_key != null) {
				var_name.setText(default_key);
				var_name.setEditable(false);
			}
			if (value != null) {
				var_value.setText(value.toString());
			}
		}
		
		@Override
		protected boolean checkOK() {
			try {
				if (!var_name.getText().isEmpty()) {
					if (var_type.getValue() == Type.NUMBER) {
						Double.parseDouble(var_value.getText());
						return true;
					} else {
						return true;
					}
				}
			} catch (Exception err){}
			return false;
		}
		@Override
		protected Entry<String, Object> getUserObject(ActionEvent e) {
			Entry<String, Object> ret = new PairEntry<String, Object>(var_name.getText(), var_value.getText());
			if (var_type.getValue() == Type.NUMBER) {
				Double value = Double.parseDouble(var_value.getText());
				ret.setValue(value);
			}
			return ret;
		}
		
	}
	
	static enum Type {
		NUMBER(),
		STRING(),;
		public String toString() {
			switch (this) {
			case NUMBER: return "数字";
			case STRING: return "字符";
			}
			return "NULL";
		}
	}
}
