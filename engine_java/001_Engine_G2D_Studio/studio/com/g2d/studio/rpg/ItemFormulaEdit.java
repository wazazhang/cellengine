package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.template.ability.ItemFormula;
import com.g2d.awt.util.AbstractOptionDialog;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectDialog;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;




@SuppressWarnings("serial")
public class ItemFormulaEdit extends AbstractOptionDialog<ItemFormula> implements PropertyCellEdit<ItemFormula>
{
	final private ItemFormula edit_data;

	JLabel		cell_edit		= new JLabel();
	
	JSplitPane	center_split	= new JSplitPane();
	ListPanel	src;
	ListPanel	dst;
	
//	--------------------------------------------------------------------------------------------------------------
	
	public ItemFormulaEdit(Component owner, ItemFormula src_data) 
	{
		super(owner);
		super.setTitle(ItemFormula.getEditName(ItemFormula.class));
		
		if (src_data == null) {
			edit_data = new ItemFormula();
		} else {
			edit_data = src_data;
		}
		
		src = new ListPanel("原材料", edit_data.src_items_id);
		dst = new ListPanel("产生物", edit_data.dst_items_id);
		
		center_split.setLeftComponent(src);
		center_split.setRightComponent(dst);

		super.add(center_split, BorderLayout.CENTER);
	}

	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	protected ItemFormula getUserObject(ActionEvent e) {
		edit_data.src_items_id.clear();
		edit_data.src_items_id.putAll(src.getValue());
		edit_data.dst_items_id.clear();
		edit_data.dst_items_id.putAll(dst.getValue());
		return edit_data;
	}
	
//	@Override
//	protected Object[] getUserObjects()
//	{
//		Object[] objs = new Object[1];				
//		objs[0] = getUserObject();				
//		return objs;
//	}	
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		ItemFormula data = getSelectedObject();
		if (data != null)
			cell_edit.setText(data.toString());
		return cell_edit;
	}
	
	@Override
	public ItemFormula getValue()
	{
		return getSelectedObject();
	}
	
	
//	--------------------------------------------------------------------------------------------------------------
	class AddItemDialog extends ObjectSelectDialog<XLSItem>
	{
		JButton add = new JButton("添加");
		public AddItemDialog(Component owner, int wcount) {
			super(owner, XLSItem.class, wcount);
			super.south.add(add);
			add.addActionListener(this);
		}
	}
	
	class ListPanel extends JPanel implements ActionListener
	{
		G2DList<ListItemData>	list		= new G2DList<ListItemData>();
		JToolBar 				tools 		= new JToolBar();
		JButton					add			= new JButton("添加");
		JButton					del			= new JButton("删除");
		JButton					set_count	= new JButton("设置数量");
		final Vector<ListItemData> list_data;
		
		public ListPanel(String title, Map<Integer, Integer> datas)
		{
			super(new BorderLayout());

			super.add(new JScrollPane(list), BorderLayout.CENTER);
			super.add(tools, BorderLayout.NORTH);
			
			this.tools.setFloatable(false);
			this.tools.add(new JLabel(title));
			this.tools.addSeparator();
			this.tools.add(add);
			this.tools.add(del);
			this.tools.addSeparator();
			this.tools.add(set_count);
			
			this.add.addActionListener(this);
			this.del.addActionListener(this);
			this.set_count.addActionListener(this);
			
			this.list_data = new Vector<ListItemData>(datas.size());
			for (Integer key : datas.keySet()){
				Integer value = datas.get(key);
				XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, key);
				if (item != null) {
					ListItemData data = new ListItemData(item, value);
					list_data.add(data);
				}
			}
			this.list.setListData(list_data);
		}
		
		public Map<Integer, Integer> getValue() {
			Map<Integer, Integer> ret = new LinkedHashMap<Integer, Integer>(); // list_data.size());
			for (ListItemData data : list_data) {
				ret.put(data.item.getIntID(), data.count);
			}
			return ret;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == del) {
				doDel();
			} else if (e.getSource() == add) {
				doAdd();
			} else if (e.getSource() == set_count) {
				doSetCount();
			}
		}

		private void doDel() {
			ListItemData item = list.getSelectedItem();
			if (item != null) {
				this.list_data.remove(item);
				this.list.setListData(list_data);
				this.list.repaint();
			}
		}
		
		private void doAdd() {
			AddItemDialog dialog = new AddItemDialog(this, 1){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == add) {
						XLSItem item = super.getList().getSelectedItem();
						if (item != null) {
							ListItemData data = new ListItemData(item, 1);
							ListPanel.this.list_data.add(data);
							ListPanel.this.list.setListData(ListPanel.this.list_data);
							ListPanel.this.list.repaint();
						}
					} else {
						super.actionPerformed(e);
					}
				}
			};
			dialog.setSize(
					200, 
					ItemFormulaEdit.this.getHeight());
			dialog.setLocation(
					ItemFormulaEdit.this.getX() + ItemFormulaEdit.this.getWidth(), 
					ItemFormulaEdit.this.getY());
			XLSItem item = dialog.showDialog();
			if (item != null) {
				ListItemData data = new ListItemData(item, 1);
				this.list_data.add(data);
				this.list.setListData(list_data);
				this.list.repaint();
			}
		}
		
		private void doSetCount() {
			ListItemData item = list.getSelectedItem();
			if (item != null) {
				String value = JOptionPane.showInputDialog(this, "设置数量", item.count);
				try{
					if (value != null) {
						int count = Integer.parseInt(value);
						if (count < 1) {
							JOptionPane.showMessageDialog(this, "输入错误！必须大于 1");
						} else {
							item.count = count;
							this.list.repaint();
						}
					}
				} catch (Exception err){
					JOptionPane.showMessageDialog(this, "输入错误！");
				}
			}
		}
		
	}
	
	class ListItemData implements G2DListItem
	{
		final XLSItem 	item;
		int 			count;
		
		public ListItemData(XLSItem item, int count) {
			this.item	= item;
			this.count	= count;
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) {
			if (item!=null) {
				return item.getListIcon(update);
			}
			return null;
		}
		
		@Override
		public String getListName() {
			if (item!=null) {
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body>");
				sb.append("<p>");
				sb.append(item.getListName());
				sb.append("<font color=0000ff> - 数量(" + count + ")" + "</font>");
				sb.append("</p>");
				sb.append("</body></html>");
				return sb.toString();
			}
			return "null " + count;
		}
		
		
	}
	
	
	public static class ItemFormulaAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,Field field) {
			return false;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field) {
			if (ItemFormula.class.isAssignableFrom(field.getType())) {
				ItemFormulaEdit dialog = new ItemFormulaEdit(
						owner.getComponent(), 
						(ItemFormula)fieldValue);
				dialog.showDialog();
				return dialog;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) {
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field,
				Object fieldSrcValue) {
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		
	}
}
