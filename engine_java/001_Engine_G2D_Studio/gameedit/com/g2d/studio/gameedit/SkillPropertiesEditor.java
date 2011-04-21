package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.cell.rpg.item.ItemPropertyTemplate.ArgTemplate;
import com.g2d.editor.property.ObjectPropertyRowPanel;
import com.g2d.editor.property.ObjectPropertyRowPanel.ColumnFiller;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.item.ItemPropertiesEditor;
import com.g2d.studio.item.property.ItemPropertyNode;
import com.g2d.studio.item.property.ItemPropertySelectDialog;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;





@SuppressWarnings("serial")
public class SkillPropertiesEditor extends JPanel implements ActionListener
{
	final XLSSkill skill;
	
	JToolBar	tools			= new JToolBar();
	JButton		tool_add_column	= new JButton("添加属性");
	JButton		tool_del_column	= new JButton("删除属性");
	JButton		tool_set_level	= new JButton("设置等级");
	JButton		tool_set_up		= new JButton("↑");
	JButton		tool_set_down	= new JButton("↓");
	
	JSplitPane	split			= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JScrollPane split_left		= new JScrollPane();
	
//	------------------------------------------------------------------------------------------------------------------
	
	public SkillPropertiesEditor(XLSSkill skill) 
	{
		super(new BorderLayout());
		this.skill = skill;
		
		this.add(tools, BorderLayout.NORTH);
		{
			tool_add_column.addActionListener(this);
			tool_del_column.addActionListener(this);
			tool_set_level.addActionListener(this);
			tool_set_up.addActionListener(this);
			tool_set_down.addActionListener(this);
			tools.add(tool_add_column);
			tools.add(tool_del_column);
			tools.addSeparator();
			tools.add(tool_set_level);
			tools.addSeparator();
			tools.add(tool_set_up);
			tools.add(tool_set_down);
		}
		
		this.add(split, BorderLayout.CENTER);
		{
			split.setLeftComponent(split_left);
			split.setRightComponent(new JPanel());
		}
		
		refreshColumns();
	}

//	------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (tool_add_column == source) {
			ItemPropertyNode node = new ItemPropertySelectDialog(this, null).showDialog();
			if (node != null) {
				ItemPropertyTemplate tt = node.getItemPropertyTemplate();
				if (tt != null) {
					skill.getData().addColumn(tt.getClass());
					refreshColumns();
				}
			}
		}
		else if (tool_del_column == source) {
			ColumnItem item = ((ColumnList)split_left.getViewport().getView()).getSelectedItem();
			if (item != null) {
				skill.getData().removeColumn(item.column);
				refreshColumns();
			}
		}
		else if (tool_set_level == source) {
			Object value = JOptionPane.showInputDialog(this, "输入等级数！", skill.getData().getMaxLevel());
			if (value!=null) {
				try {
					int new_level = Integer.parseInt(value.toString());
					if (new_level > 0) {
						skill.getData().setMaxLevel(new_level);
						refreshColumns();
						Studio.getInstance().getObjectManager().refresh(XLSSkill.class);
					} else {
						JOptionPane.showMessageDialog(this, "等级不能小于 1 ！");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(this, err.getClass() + " : " + err.getMessage());
				}
			}
		}
		else if (tool_set_up == source) {
			ColumnItem item = ((ColumnList)split_left.getViewport().getView()).getSelectedItem();
			if (item != null) {
				int new_index = item.column - 1;
				if (skill.getData().moveColumn(item.column, -1) > 0) {
					refreshColumns();
					((ColumnList)split_left.getViewport().getView()).setSelectedIndex(new_index);
				}
			}			
		}
		else if (tool_set_down == source) {
			ColumnItem item = ((ColumnList)split_left.getViewport().getView()).getSelectedItem();
			if (item != null) {
				int new_index = item.column + 1;
				if (skill.getData().moveColumn(item.column, 1) > 0) {
					refreshColumns();
					((ColumnList)split_left.getViewport().getView()).setSelectedIndex(new_index);
				}
			}			
		}
	}

	private void refreshColumns() 
	{
		split_left.setViewportView(new ColumnList());
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	class ColumnList extends G2DList<ColumnItem> implements ListSelectionListener
	{
		public ColumnList() 
		{
			Vector<ColumnItem> list_data = new Vector<ColumnItem>(skill.getData().getMaxColumn());
			for (int column = 0; column < skill.getData().getMaxColumn(); column++) {
				list_data.add(new ColumnItem(column));
			}
			this.setListData(list_data);
			this.addListSelectionListener(this);
			split.setRightComponent(new JPanel());
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
			ColumnItem selected = getSelectedItem();
			if (selected != null) {			
				split.setRightComponent(selected.levels);
			} else {
				split.setRightComponent(new JPanel());
			}
		}
	}

//	------------------------------------------------------------------------------------------------------------------
	
	class ColumnItem implements G2DListItem
	{
		final private int 			column;
		final private Class<? extends ItemPropertyTemplate> 
									column_type;
		final private FieldTable 	levels;
		
		public ColumnItem(int column) 
		{
			this.column 		= column;
			this.column_type	= skill.getData().getColumnType(column);
			this.levels 		= new FieldTable();
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
		{
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) 
		{
			return null;
		}
		
		@Override
		public String getListName() 
		{
			return AbstractAbility.getEditName(column_type);
		}

//		------------------------------------------------------------------------------------------------------------------

		final String COLUMN_LEVEL = "等级";
		
		class FieldTable extends ObjectPropertyRowPanel<ItemPropertyTemplate>
		{
			public FieldTable() 
			{
				super(column_type, 
						skill.getData().getColumnProperties(column), 
						COLUMN_LEVEL,
						ItemPropertiesEditor.getAdapters()
				);
				super.addColumnFiller(new FillerRangeValue());
			}
		}
	}

//	------------------------------------------------------------------------------------------------------------------

	/**
	 * 用于填充类ArgTemplate
	 * @author WAZA
	 */
	public static class FillerRangeValue extends JPanel implements ColumnFiller
	{
		
		@Override
		public String getCommand(Object row_data, Field columnField) 
		{
			if (ArgTemplate.class.isAssignableFrom(columnField.getType())) 
			{
				return "填充" + ArgTemplate.class.getSimpleName();
			}

			return null;
		}

		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) 
		{
			return this;
		}

		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) 
		{
			return null;
		}
		
	}
}
