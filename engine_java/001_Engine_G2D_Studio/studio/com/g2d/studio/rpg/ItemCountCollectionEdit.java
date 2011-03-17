package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;
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

import com.cell.rpg.template.ability.ItemCountCollection;
import com.cell.util.Pair;
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
public class ItemCountCollectionEdit extends AbstractOptionDialog<ItemCountCollection> implements PropertyCellEdit<ItemCountCollection>
{
	final private ItemCountCollection edit_data;

	JLabel		cell_edit		= new JLabel();

	ListPanel	pnl_items_;
	
//	--------------------------------------------------------------------------------------------------------------
	
	public ItemCountCollectionEdit(Component owner, ItemCountCollection src_data) 
	{
		super(owner);
		super.setTitle(ItemCountCollection.getEditName(ItemCountCollection.class));
		
		if (src_data == null) {
			edit_data = new ItemCountCollection();
		} else {
			edit_data = src_data;
		}
		
		pnl_items_ = new ListPanel("道具列表", edit_data.items_);
		this.add(pnl_items_);
	}

	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	protected ItemCountCollection getUserObject(ActionEvent e) {
		edit_data.items_ = pnl_items_.getItems();
		edit_data.item_names_ = pnl_items_.getItemNames();
		return edit_data;
	}
	
//	@Override
//	protected Object[] getUserObjects()
//	{
//		Object[] objs = new Object[1];
//		
//		objs[0] = getValue();
//		
//		return objs;
//	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		ItemCountCollection data = getSelectedObject();
		if (data != null)
			cell_edit.setText(data.toString());
		return cell_edit;
	}
	
	@Override
	public ItemCountCollection getValue() {
		return getSelectedObject();
	}
	
	
//	--------------------------------------------------------------------------------------------------------------
	
	
	class ListPanel extends JPanel implements ActionListener
	{
		G2DList<ListItemData>	lst_abilities_	= new G2DList<ListItemData>();
		JToolBar 				tbar_tools_ 	= new JToolBar();
		JButton					btn_add_		= new JButton("添加");
		JButton					btn_del_		= new JButton("删除");
		JButton					btn_set_count_	= new JButton("设置数量");
		final Vector<ListItemData> list_data;
		
		public ListPanel(String title, Vector<Pair<Integer, Integer>> items)
		{
			super(new BorderLayout());

			super.add(new JScrollPane(lst_abilities_), BorderLayout.CENTER);
			super.add(tbar_tools_, BorderLayout.NORTH);
			
			this.tbar_tools_.setFloatable(false);
			this.tbar_tools_.add(new JLabel(title));
			this.tbar_tools_.addSeparator();
			this.tbar_tools_.add(btn_add_);
			this.tbar_tools_.add(btn_del_);
			this.tbar_tools_.addSeparator();
			this.tbar_tools_.add(btn_set_count_);
			
			this.btn_add_.addActionListener(this);
			this.btn_del_.addActionListener(this);
			this.btn_set_count_.addActionListener(this);
			
			this.list_data = new Vector<ListItemData>(items.size());
			for (Pair<Integer, Integer> itm : items){
				XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, itm.getKey());
				if (item != null) {
					ListItemData data = new ListItemData(item, itm.getValue());
					list_data.add(data);
				}
			}
			this.lst_abilities_.setListData(list_data);
		}
		
		public Vector<Pair<Integer, Integer>> getItems() 
		{
			Vector<Pair<Integer, Integer>> ret = new Vector<Pair<Integer, Integer>>(list_data.size());
			for (ListItemData data : list_data) {
				ret.add(new Pair(data.item_.getIntID(), data.count_));
			}
			return ret;
		}
		
		public Vector<String> getItemNames() 
		{
			Vector<String> ret = new Vector<String>(list_data.size());
			for (ListItemData data : list_data) {
				ret.add(data.item_.getItemName());
			}
			return ret;
		}		
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == btn_del_) {
				doDel();
			} else if (e.getSource() == btn_add_) {
				doAdd();
			} else if (e.getSource() == btn_set_count_) {
				doSetCount();
			}
		}

		private void doDel() 
		{
			Object[] items = lst_abilities_.getSelectedItems();
			
			if (items != null)
			{
				for ( Object item : items )
				{
					this.list_data.remove(item);
					this.lst_abilities_.setListData(list_data);
					this.lst_abilities_.repaint();
				}
			}
		}
		
		private void doAdd() 
		{
			ObjectSelectDialog<XLSItem> dialog = new ObjectSelectDialog<XLSItem>(this, XLSItem.class, 1);
			dialog.setSize(200, ItemCountCollectionEdit.this.getHeight());
			dialog.setLocation(
					ItemCountCollectionEdit.this.getX() + ItemCountCollectionEdit.this.getWidth(), 
					ItemCountCollectionEdit.this.getY());
			Object[] items = dialog.showDialog2();
			if (items != null) {
				for ( Object item : items )
				{
					if (item instanceof XLSItem)
					{
						ListItemData data = new ListItemData((XLSItem)item, 1);
						this.list_data.add(data);
						this.lst_abilities_.setListData(list_data);
						this.lst_abilities_.repaint();
					}
				}
			}
		}
		
		private void doSetCount() 
		{
			Object[] items = lst_abilities_.getSelectedItems();
						
			if (items != null) 
			{
				String value = JOptionPane.showInputDialog(this, "设置数量", ((ListItemData)items[0]).count_);
				try
				{
					if (value != null)
					{
						int count = Integer.parseInt(value);
						
						if (count < 1) 
						{
							JOptionPane.showMessageDialog(this, "输入错误！必须大于 1");
						} 
						else
						{
							for ( Object item : items )
							{
								((ListItemData)item).count_ = count;
								this.lst_abilities_.repaint();
							}
						}
					}
				} 
				catch (Exception err)
				{
					JOptionPane.showMessageDialog(this, "输入错误！");
				}
			}
		}
	}
	
	class ListItemData implements G2DListItem
	{
		final XLSItem 	item_;
		int 			count_;
		
		public ListItemData(XLSItem item, int count) 
		{
			this.item_	= item;
			this.count_	= count;
		}
		
		@Override
		public Component getListComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
		{
			return null;
		}
		
		@Override
		public ImageIcon getListIcon(boolean update) 
		{
			if (item_!=null) {
				return item_.getListIcon(update);
			}
			return null;
		}
		
		@Override
		public String getListName() 
		{
			if (item_!=null) {
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body>");
				sb.append("<p>");
				sb.append(item_.getListName());
				sb.append("<font color=0000ff> - 数量(" + count_ + ")" + "</font>");
				sb.append("</p>");
				sb.append("</body></html>");
				return sb.toString();
			}
			return "null " + count_;
		}	
	}; // class ListItemData
	
	
	public static class ItemCountCollectionAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() 
		{
			return Object.class;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,Field field) 
		{
			return false;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) 
		{
			if (ItemCountCollection.class.isAssignableFrom(field.getType())) 
			{
				ItemCountCollectionEdit dialog = new ItemCountCollectionEdit(owner.getComponent(), (ItemCountCollection)fieldValue);
				dialog.showDialog();
				return dialog;
			}

			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) 
		{
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) 
		{
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) 
		{
			return null;
		}
		
	}
}


