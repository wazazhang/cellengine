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

import com.cell.rpg.template.ability.ItemRateCollection;
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
public class ItemRateCollectionEdit extends AbstractOptionDialog<ItemRateCollection> implements PropertyCellEdit<ItemRateCollection>
{
	final private ItemRateCollection edit_data;

	JLabel		cell_edit		= new JLabel();

	ListPanel	pnl_items_;
	
//	--------------------------------------------------------------------------------------------------------------
	
	public ItemRateCollectionEdit(Component owner, ItemRateCollection src_data) 
	{
		super(owner);
		super.setTitle(ItemRateCollection.getEditName(ItemRateCollection.class));
		
		if (src_data == null) {
			edit_data = new ItemRateCollection();
		} else {
			edit_data = src_data;
		}
		
		pnl_items_ = new ListPanel("道具列表", edit_data.getItemIDCountMap(), edit_data.getItemIDRateMap());
		this.add(pnl_items_);
	}

	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	protected ItemRateCollection getUserObject(ActionEvent e) {
		edit_data.item_ids_ = pnl_items_.getItemIDs();
		edit_data.item_counts_ = pnl_items_.getItemCounts();
		edit_data.item_rates_ = pnl_items_.getItemRates();
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
		ItemRateCollection data = getValue();
		cell_edit.setText(data.toString());
		return cell_edit;
	}
	
	@Override
	public ItemRateCollection getValue() {
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
		JButton					btn_set_rate_	= new JButton("设置概率");
		final Vector<ListItemData> list_data;
		
		public ListPanel(String title, HashMap<Integer, Integer> item_counts, HashMap<Integer, Integer> item_rates)
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
			this.tbar_tools_.add(btn_set_rate_);
			
			this.btn_add_.addActionListener(this);
			this.btn_del_.addActionListener(this);
			this.btn_set_count_.addActionListener(this);
			this.btn_set_rate_.addActionListener(this);
			
			this.list_data = new Vector<ListItemData>(item_counts.size());
			for (Integer key : item_counts.keySet()){
				Integer count = item_counts.get(key);
				Integer rate = item_rates.get(key);
				XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, key);
				if (item != null) {
					ListItemData data = new ListItemData(item, count, rate);
					list_data.add(data);
				}
			}
			this.lst_abilities_.setListData(list_data);
		}
		
		public Vector<Integer> getItemIDs() 
		{
			Vector<Integer> ret = new Vector<Integer>(list_data.size());
			for (ListItemData data : list_data) {
				ret.add(data.item_.getIntID());
			}
			return ret;
		}
		
		public Vector<Integer> getItemCounts()
		{
			Vector<Integer> ret = new Vector<Integer>(list_data.size());
			for (ListItemData data : list_data) {
				ret.add(data.count_);
			}
			return ret;		
		}
		
		public Vector<Integer> getItemRates()
		{
			Vector<Integer> ret = new Vector<Integer>(list_data.size());
			for (ListItemData data : list_data) {
				ret.add(data.rate_);
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
			} else if (e.getSource() == btn_set_rate_) {
				doSetRate();
			}
		}

		private void doDel() 
		{
			ListItemData item = lst_abilities_.getSelectedItem();
			if (item != null) {
				this.list_data.remove(item);
				this.lst_abilities_.setListData(list_data);
				this.lst_abilities_.repaint();
			}
		}
		
		private void doAdd() 
		{
			ObjectSelectDialog<XLSItem> dialog = new ObjectSelectDialog<XLSItem>(this, XLSItem.class, 1);
			dialog.setSize(
					200, 
					ItemRateCollectionEdit.this.getHeight());
			dialog.setLocation(
					ItemRateCollectionEdit.this.getX() + ItemRateCollectionEdit.this.getWidth(), 
					ItemRateCollectionEdit.this.getY());
			XLSItem item = dialog.showDialog();
			if (item != null) {
				ListItemData data = new ListItemData(item, 1, 0);
				this.list_data.add(data);
				this.lst_abilities_.setListData(list_data);
				this.lst_abilities_.repaint();
			}
		}
		
		private void doSetCount() 
		{
			ListItemData item = lst_abilities_.getSelectedItem();
			if (item != null) {
				String value = JOptionPane.showInputDialog(this, "设置数量", item.count_);
				try{
					if (value != null) {
						int count = Integer.parseInt(value);
						if (count < 1) {
							JOptionPane.showMessageDialog(this, "输入错误！必须大于 1");
						} else {
							item.count_ = count;
							this.lst_abilities_.repaint();
						}
					}
				} catch (Exception err){
					JOptionPane.showMessageDialog(this, "输入错误！");
				}
			}
		}
		
		private void doSetRate() 
		{
			ListItemData item = lst_abilities_.getSelectedItem();
			if (item != null) {
				String value = JOptionPane.showInputDialog(this, "设置概率（万分比整数）", item.rate_);
				try{
					if (value != null) {
						int rate = Integer.parseInt(value);
						if ( (rate < 0) || (rate > 10000) ) {
							JOptionPane.showMessageDialog(this, "输入错误！必须大于等于0且小于等于10000的整数");
						} else {
							item.rate_ = rate;
							this.lst_abilities_.repaint();
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
		final XLSItem 	item_;
		int 			count_;
		int				rate_;
		
		public ListItemData(XLSItem item, int count, int rate) 
		{
			this.item_	= item;
			this.count_	= count;
			this.rate_ = rate;
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
				sb.append("<font color=00ff00> - 概率(" + (float)rate_/100.0f + "%)" + "</font>");
				sb.append("</p>");
				sb.append("</body></html>");
				return sb.toString();
			}
			return "null " + count_;
		}	
	}; // class ListItemData
	
	
	public static class ItemRateCollectionAdapter implements CellEditAdapter<Object>
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
			if (ItemRateCollection.class.isAssignableFrom(field.getType())) 
			{
				ItemRateCollectionEdit dialog = new ItemRateCollectionEdit(owner.getComponent(), (ItemRateCollection)fieldValue);
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


