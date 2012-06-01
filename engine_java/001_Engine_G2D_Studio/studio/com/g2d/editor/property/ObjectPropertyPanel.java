package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.g2d.Color;
import com.g2d.annotation.Property;
import com.g2d.java2d.impl.AwtEngine;


/**
 * 该编辑器可将一个对象内所有的 {@link Property}标注的字段，显示在该控件内，并可以编辑。<br>
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 *
 */
public class ObjectPropertyPanel extends BaseObjectPropertyPanel
{
	private static final long serialVersionUID = 1L;
	
	public static int DEFAULT_ROW_HEIGHT = 24;

	/** 剪贴板共享数据 */
	private static transient Object copy_field_data;

	final public Object 	object;

	final Vector<Object[]> 	rows			= new Vector<Object[]>();
	final FieldTable		rows_table;
	final JTextPane			anno_text;
	final ValueEditor 		value_editor	= new ValueEditor();
	
	public ObjectPropertyPanel(Object obj,
			CellEditAdapter<?> ... adapters){
		this(obj, 100, 200, adapters);
	}
	
	public ObjectPropertyPanel(Object obj, 
			int min_w, int min_h, 
			CellEditAdapter<?> ... adapters)
	{
		this(obj, 100, 200, true, adapters);
	}
	
	public ObjectPropertyPanel(Object obj, 
			int min_w, int min_h, 
			boolean showType,
			CellEditAdapter<?> ... adapters)
	{
		super(adapters);
		
		this.setLayout(new BorderLayout());
		
		this.object = obj;
		
		// north
		{
			Property type_property = object.getClass().getAnnotation(Property.class);
			if (type_property!=null) {
				JLabel lbl_name = new JLabel(CUtil.arrayToString(type_property.value(), " - "));
				this.add(lbl_name, BorderLayout.NORTH);
			}
		}

		
		// center
		{
			JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			
			{
				// create fields key value
				Class<?> cls = object.getClass();
				Field[] fields = cls.getFields();
				String[] colum_header = new String[] { 
						"filed", "value", "type", };
				
				for (int r = 0; r < fields.length; r++) {
					try {
						Field field = fields[r];
						if (field.getAnnotation(Property.class) != null) {
							Object[] row = new Object[] { 
									field.getName(),
									field.get(object),
									field.getType().getSimpleName(),
									field };
							rows.add(row);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				Object[][] rowsdata = rows.toArray(new Object[rows.size()][]);
				
				rows_table = new FieldTable(rowsdata, colum_header, showType);
				
				JScrollPane scroll = new JScrollPane(rows_table);
				scroll.setMinimumSize(new Dimension(min_w, min_h));
				split.setTopComponent(scroll);
			}
			
			{
				anno_text = new JTextPane();
				anno_text.setPreferredSize(new Dimension(200, 100));
				split.setBottomComponent(anno_text);
			}
			split.setResizeWeight(1);
			
			this.add(split, BorderLayout.CENTER);
		}
		
	}

	public Object getObject(){
		return object;
	}
	/**
	 * 通知单元格编辑器，已经完成了编辑
	 */
	final public void fireEditingStopped(){
		value_editor.stopCellEditing();
		value_editor.getCellEditorValue();
		rows_table.refresh();
		rows_table.repaint();
	}
	
	final public void refresh(){
		rows_table.refresh();
		rows_table.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		rows_table.refresh();
	}
//	-------------------------------------------------------------------------------------------------------------------------------------
	

	/**
	 * 显示字段的表格，第一列为字段名，第二列为字段值，第三列为字段类型
	 * @author WAZA
	 *
	 */
	class FieldTable extends JTable implements ListSelectionListener, KeyListener
	{
		private static final long serialVersionUID = 1L;

		public FieldTable(
				final Object[][] rowData, 
				final Object[] columnNames,
				boolean showType) 
		{
			super(rowData, columnNames);
			super.setMinimumSize(new Dimension(100, 100));
			super.setRowHeight(DEFAULT_ROW_HEIGHT);
			
			super.getColumn(columnNames[1]).setCellRenderer(new TableRender());
//			super.getColumn(columnNames[2]).setCellRenderer(new ClassRender());
			
			super.getColumn("filed").setCellEditor(new NullEditor());
			super.getColumn("value").setCellEditor(value_editor);
			super.getColumn("type").setCellEditor(new NullEditor());
			
			if (!showType) {
				super.getColumn("type").setPreferredWidth(1);
			}
			
			super.addKeyListener(this);
			super.getSelectionModel().addListSelectionListener(this);
		}
		
		public void valueChanged(ListSelectionEvent event) {
			try {
				int r = rows_table.getSelectedRow();
				if (r>=0 && r<rows.size()) {
					int c = 3;
					Field	field 		= (Field) rows.elementAt(r)[c];
					Object	field_value	= field.get(object);
					String	field_text	= getPropertyCellText(object, field, field_value);

					Property doc = field.getAnnotation(Property.class);
					if (doc != null) {
						anno_text.setText(
								CUtil.arrayToString(doc.value(), "\n") + 
								"--------------------------\n" + 
								field_text);
					} else {
						anno_text.setText(
								field.getName() + "\n" +
								"--------------------------\n" + 
								field_text);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			rows_table.refresh();
			repaint();
		}
		
		public void refresh()
		{
			for (int r=0; r<rows.size(); r++) 
			{
				Object[] row = rows.elementAt(r);
				Field field = (Field)row[3];
				try {
					row[1] = field.get(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.setValueAt(row[1], r, 1);
			}
		}

//		----------------------------------------------------------
//		Key Events
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				if (e.getKeyCode() == KeyEvent.VK_C) {
					this.copy();
				}else if (e.getKeyCode() == KeyEvent.VK_V) {
					this.parser();
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}

//		----------------------------------------------------------

		private void copy() {
			try {
				Object[] row = rows.elementAt(getSelectedRow());
				copy_field_data = CIO.cloneObject(row[1]);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		
		private void parser() {
			try {
				Object[] row = rows.elementAt(getSelectedRow());
				Field column = (Field)row[3];
				if (copy_field_data != null) {
					if (Parser.isNumber(column.getType())) {
						copy_field_data = Parser.castNumber(copy_field_data, column.getType());
					}
					try {
						Object fdata = CIO.cloneObject(copy_field_data);
						column.set(object, fdata);
						setValueAt(fdata, getSelectedRow(), 1);
					} catch (Exception err) {}
					this.repaint();
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
//		----------------------------------------------------------
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------
//	class ClassRender extends DefaultTableCellRenderer
//	{
//		@Override
//		public Component getTableCellRendererComponent(JTable table,
//				Object value, boolean isSelected, boolean hasFocus, int row,
//				int column)
//		{
//			
//			Component src = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			this.setText(((Class<?>)value).getSimpleName());
//			return src;
//		}
//	}

	class TableRender extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		Icon old_icon;
		public TableRender() {
			old_icon = super.getIcon();
		}
		
		@Override
		public Component getTableCellRendererComponent(
				JTable table,
				Object value,
				boolean isSelected,
				boolean hasFocus, 
				int row,
				int column) 
		{
			super.setIcon(old_icon);
			
			Component src = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			try{
				Field field = (Field) rows.elementAt(row)[3];
				Object field_value = field.get(object);
				if (src instanceof DefaultTableCellRenderer) {
					DefaultTableCellRenderer dc = (DefaultTableCellRenderer)src;
					dc.setText(getPropertyCellText(object, field, field_value));
//					if (field_value instanceof Color) {
//						dc.setBackground(AwtEngine.unwrap((Color)field_value));
//					}
				}
				Component comp = getPropertyCellRender(this, object, field, field_value);
				return comp;
			}catch(Exception err){
				err.printStackTrace();
			}
			return src;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 单元格值编辑器
	 * @author WAZA
	 *
	 */
	class  ValueEditor extends DefaultCellEditor implements TableCellEditor
	{
		private static final long serialVersionUID = 1L;

		int 		editrow;
		Object 		src_value;
		
		PropertyCellEdit<?> current_cell_edit;
		
		// -----------------------------------
		// editors
		
		public ValueEditor()
		{
			super(new JTextField());
		}
		
		
		public Object getCellEditorValue() 
		{
			try {
				Field field = (Field) rows.elementAt(editrow)[3];
				Object obj = getPropertyCellEditValue(
						object, 
						field, 
						current_cell_edit, 
						src_value);
//				System.out.println("getCellEditorValue " + obj);
				if (obj != null) {
					field.set(object, obj);
					rows.elementAt(editrow)[1] = obj;
					onFieldChanged(object, field);
					rows_table.refresh();
					rows_table.repaint();
					return obj;
				} else {
					return src_value;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return src_value;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
		{
			src_value 	= value;
			editrow 	= row;
			Field field = (Field) rows.elementAt(editrow)[3];
			try {
				current_cell_edit = getPropertyCellEdit(object, field, value);
				Component ret = current_cell_edit.getComponent(ObjectPropertyPanel.this);
//				System.out.println("getTableCellEditorComponent");
				return ret;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		
		
	}
//	------------------------------------------------------------------------------------------------------------------------------------------
	
}
