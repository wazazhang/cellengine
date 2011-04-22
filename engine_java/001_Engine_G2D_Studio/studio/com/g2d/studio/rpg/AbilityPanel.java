package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.CUtil;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;


/**
 * @author WAZA
 * 可编辑多个能力的面板
 */
@SuppressWarnings("serial")
public class AbilityPanel extends JPanel implements MouseListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	private static HashMap<Class<?>, Class<?>> last_add_ability = new HashMap<Class<?>, Class<?>>();
	
	// properties	
	final Abilities		abilities;
	final ArrayList<CellEditAdapter<?>> 
						adapters = new ArrayList<CellEditAdapter<?>>();
	
	// ui
	G2DList 			list_cur_ability 	= new G2DList();
	JButton 			btn_add_ability 	= new JButton("添加能力");
	JButton 			btn_del_ability 	= new JButton("删除能力");
	JButton 			btn_self		 	= new JButton("自有属性");
	JButton				tool_set_up			= new JButton("↑");
	JButton				tool_set_down		= new JButton("↓");	
	
	JSplitPane 			split 				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JPanel 				left 				= new JPanel(new BorderLayout());
	JPanel				right 				= new JPanel();
	
	public AbilityPanel(Abilities abilities, CellEditAdapter<?> ... adapters)
	{
		this.abilities 		= abilities;
		for (CellEditAdapter<?> a : adapters) {
			this.adapters.add(a);
		}
		this.adapters.add(new AbilitiesCellEditAdapter());
		this.adapters.add(new PropertyAdapters.UnitTypeAdapter());
		this.adapters.add(new PropertyAdapters.InstanceZoneScriptCodeAdapter());
		this.setLayout(new BorderLayout());
		
		// left
		{
			// left center
			{
				AbstractAbility[] ability_datas = abilities.getAbilities();
				this.list_cur_ability.setListData((ability_datas==null)? new AbstractAbility[0] : ability_datas);
				this.list_cur_ability.addMouseListener(this);
				this.list_cur_ability.setCellRenderer(new ListRender());
				JScrollPane scroll = new JScrollPane(list_cur_ability);
				scroll.setMaximumSize(new Dimension(250,200));
				left.add(scroll, BorderLayout.CENTER);
			}
			// top tool bar
			{
				JToolBar bpan = new JToolBar();
				this.btn_add_ability.addActionListener(this);
				this.btn_del_ability.addActionListener(this);
				this.btn_self.addActionListener(this);
				this.tool_set_up.addActionListener(this);
				this.tool_set_down.addActionListener(this);				
				bpan.add(btn_add_ability);
				bpan.add(btn_del_ability);
				bpan.addSeparator();
				bpan.add(btn_self);
				bpan.add(tool_set_up);
				bpan.add(tool_set_down);
				left.add(bpan, BorderLayout.NORTH);
			}
			left.setMinimumSize(new Dimension(250,200));
			left.setPreferredSize(new Dimension(250,200));
			split.setLeftComponent(left);
			
		}
		// right
		{
			split.setRightComponent(right);
		}
		
		this.add(split, BorderLayout.CENTER);
	}

//	-----------------------------------------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == btn_del_ability) 
		{
			deleteSeletedAbility();
		}
		else if (source == btn_add_ability) 
		{
			if (abilities.getSubAbilityTypes()!=null && abilities.getSubAbilityTypes().length>0)
			{
				new AddAbilityForm(this).setVisible(true);
			}
		}
		else if (source == btn_self)
		{
			AbstractDialog ad = new AbstractDialog(this);
			ad.add(new ObjectPropertyPanel(
					abilities, 
					adapters.toArray(new CellEditAdapter<?>[adapters.size()])));
			ad.setSize(400, 400);
			ad.setCenter();
			ad.setVisible(true);
		}
		else if (tool_set_up == source) 
		{
			int index = list_cur_ability.getSelectedIndex();
			if (index > 0) 
			{
				int new_index = index - 1;
				if (abilities.moveAbility(index, -1) > 0) 
				{
					AbstractAbility[] ability_datas = abilities.getAbilities();
					this.list_cur_ability.setListData((ability_datas==null)? new AbstractAbility[0] : ability_datas);
					list_cur_ability.setSelectedIndex(new_index);
				}
			}			
		}
		else if (tool_set_down == source) 
		{
			int index = list_cur_ability.getSelectedIndex();
			if ( (index >= 0) && (index < (list_cur_ability.getModel().getSize()-1 )) ) 
			{
				int new_index = index + 1;
				if (abilities.moveAbility(index, 1) > 0)
				{
					AbstractAbility[] ability_datas = abilities.getAbilities();
					this.list_cur_ability.setListData((ability_datas==null)? new AbstractAbility[0] : ability_datas);
					list_cur_ability.setSelectedIndex(new_index);
				}
			}			
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getSource() == list_cur_ability) 
		{
			Object selected = list_cur_ability.getSelectedValue();
			if (selected instanceof AbstractAbility) {
				resetAbility();
				AbstractAbility ability = (AbstractAbility)selected;
				ObjectPropertyPanel panel = new ObjectPropertyPanel(
						ability, 
						adapters.toArray(new CellEditAdapter<?>[adapters.size()]));
				split.setRightComponent(panel);
				list_cur_ability.setSelectedValue(selected, false);
			}
		} 
		else 
		{
			split.setRightComponent(right);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	
//	-----------------------------------------------------------------------------------------------------------------------------
	public void deleteSeletedAbility() {
		try{
			AbstractAbility data = (AbstractAbility)list_cur_ability.getSelectedValue();
			this.abilities.removeAbility(data);
			this.list_cur_ability.setListData(abilities.getAbilities());
		}catch (Exception err) {}
	}
	

	/**
	 * 得到正在编辑的Abilities
	 * @return
	 */
	public Abilities getAbilities() 
	{
		return abilities;
	}
	
	public void resetAbility() 
	{
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	public void addAbility(AbstractAbility ability) 
	{
		this.abilities.addAbility(ability);
		this.list_cur_ability.setListData(abilities.getAbilities());
	}
	
	@Override
	public String toString()
	{
		return "Ability";
	}

//	-----------------------------------------------------------------------------------------------------------------------------

	protected String getListAbilityText(AbstractAbility ability) 
	{
		return AbstractAbility.getEditName(ability.getClass());
	}
	
	class ListRender extends DefaultListCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public ListRender() 
		{
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) 
		{
			Component ret = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
			if (value instanceof AbstractAbility) {
				this.setText(getListAbilityText((AbstractAbility)value));
			}
			return ret;
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	
	
	/**
	 * 添加能力时弹出的框
	 * @author WAZA
	 */
	final class AddAbilityForm extends AbstractDialog implements 
	ListCellRenderer, 
	ActionListener, 
	Comparator<Class<?>>,
	ListSelectionListener
	{
		private static final long serialVersionUID = 1L;

		//		JComboBox		combo_abilities;
		G2DList<AddAbilityItem> list_abilities;
		JButton					btn_add				= new JButton("确定");
		JButton					btn_cancel			= new JButton("取消");
		JPanel					pan_property		= new JPanel();
		AbstractAbility			current_ability;
		
		public AddAbilityForm(Component owner)
		{
			super(owner);
			super.setTitle("添加能力到 : " + abilities);
			super.setLayout(new BorderLayout());
			super.setSize(900, 600);
			this.setIconImage(Res.icon_edit);
			
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			this.add(split, BorderLayout.CENTER);
			{
				pan_property.setLayout(new BorderLayout());
				pan_property.setMinimumSize(new Dimension(getWidth()/2, 400));
				split.setRightComponent(pan_property);
			}
			{
				Class<?>[] 	types_data 	= abilities.getSubAbilityTypes();
				Class<?> 	last_add 	= last_add_ability.get(abilities.getClass());				
				Arrays.sort(types_data, this);
				Vector<AddAbilityItem> 	types 		= new Vector<AddAbilityItem>(types_data.length);
				AddAbilityItem			last_type 	= null;
				for (Class<?> td : types_data) {
					AddAbilityItem aai = new AddAbilityItem(td);
					types.add(aai);
					if (td == last_add) {
						last_type = aai;
					}
				}
				list_abilities = new G2DList<AddAbilityItem>(types);
				list_abilities.addListSelectionListener(this);
				JScrollPane pan = new JScrollPane(list_abilities);
				pan.setMinimumSize(new Dimension(200,200));
				split.setLeftComponent(pan);
				if (last_type != null) {
					setAbilityClass(last_type.data);
					list_abilities.setSelectedValue(last_type, true);
				}
//				combo_abilities = new JComboBox(types_data);
//				combo_abilities.setRenderer(this);
//				combo_abilities.addActionListener(this);
//				combo_abilities.setMaximumRowCount(40);
//				this.add(combo_abilities, BorderLayout.NORTH);
			}
			{
				JPanel south = new JPanel(new FlowLayout());
				
				btn_add.setActionCommand("btn_add");
				btn_add.addActionListener(this);
				south.add(btn_add);

				btn_cancel.setActionCommand("btn_cancel");
				btn_cancel.addActionListener(this);
				south.add(btn_cancel);
				
				this.add(south, BorderLayout.SOUTH);
			}
			
			super.setCenter();
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
			AddAbilityItem selected = list_abilities.getSelectedItem();
			if (selected != null) {
				setAbilityClass(selected.data);
				last_add_ability.put(abilities.getClass(), selected.data);	
			}
		}
		
		public void actionPerformed(ActionEvent e) 
		{
//			if (e.getSource() == combo_abilities) {
//				Class<? extends AbstractAbility> ability_cls = 
//					(Class<? extends AbstractAbility>)combo_abilities.getSelectedItem();
//				setAbilityClass(ability_cls);
//			} 
			if (e.getSource() == btn_add) 
			{
				try{
					AbilityPanel.this.addAbility(current_ability);
					AddAbilityForm.this.setVisible(false);
					AddAbilityForm.this.dispose();
				}catch (Exception err) {
					err.printStackTrace();
				}
			}
			else if (e.getSource() == btn_cancel) 
			{
				try{
					AddAbilityForm.this.setVisible(false);
					AddAbilityForm.this.dispose();
				}catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
		

		@SuppressWarnings("unchecked")
		public Component getListCellRendererComponent(JList list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) 
		{
			Class<? extends AbstractAbility> ability_cls = (Class<? extends AbstractAbility>)value;
			return new JLabel(AbstractAbility.getEditName(ability_cls));
		}

		@Override
		public int compare(Class<?> o1, Class<?> o2) 
		{
			return CUtil.getStringCompare().compare(AbstractAbility.getEditName(o2), AbstractAbility.getEditName(o1));
		}
		
		public void setAbilityClass(Class<? extends AbstractAbility> cls) 
		{
			current_ability = AbstractAbility.createAbility(cls);
			ObjectPropertyPanel obj_pan = new ObjectPropertyPanel(current_ability, adapters.toArray(new CellEditAdapter<?>[adapters.size()]));
			pan_property.removeAll();
			pan_property.add(obj_pan, BorderLayout.CENTER);
			pan_property.updateUI();
			
			// 如果该 ability 不允许多个实例
			if (!current_ability.isMultiField() && abilities.getAbility(current_ability.getClass())!=null) {
				btn_add.setForeground(Color.RED);
				btn_add.setEnabled(false);
				btn_add.setText(AbstractAbility.getEditName(current_ability.getClass())+" (已存在)");
			} else {
				btn_add.setForeground(Color.BLACK);
				btn_add.setEnabled(true);
				btn_add.setText("确定");
			}
		}
		
		

		class AddAbilityItem implements G2DListItem
		{
			Class<? extends AbstractAbility> data;
			
			@SuppressWarnings("unchecked")
			public AddAbilityItem(Class<?> data) 
			{
				this.data = (Class<? extends AbstractAbility>) data;
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
				return AbstractAbility.getEditName(data);
			}
		}
		
	}

	
//	---------------------------------------------------------------------------------------------------------

	class AbilitiesCellEditAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() 
		{
			return Object.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) 
		{
			// 测试是否是集合
			try {
				field.getType().asSubclass(Abilities.class);
				System.out.println("field is Abilities");
				if (fieldValue == null) {
					fieldValue = field.getType().newInstance();
				}
				return new AbilityForm(
						owner,
						(Abilities) fieldValue,
						adapters.toArray(new CellEditAdapter<?>[adapters.size()]));
			} catch (Exception e) {}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) 
		{
			return null;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) 
		{
			return false;
		}
	
		@Override
		public Object getCellValue(Object editObject,
			PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue)
		{
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue)
		{
			return null;
		}
	}
	

//	---------------------------------------------------------------------------------------------------------
	
	public static abstract class AbilityCellEditAdapter<T extends AbstractAbility> implements CellEditAdapter<T>
	{
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) 
		{
			return false;
		}

		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) 
		{
			return null;
		}

		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src)
		{
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
			PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) 
		{
			return null;
		}

		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) 
		{
			return null;
		}
	}
	
}
