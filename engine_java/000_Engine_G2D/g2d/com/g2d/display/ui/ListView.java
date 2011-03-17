package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.util.Drawing;

public class ListView extends UIComponent 
{
	private static final long serialVersionUID = Version.VersionG2D;

	ViewMode 					view_mode			= ViewMode.BIG_ICON;
	
	ListViewPanel 				panel 				= new ListViewPanel();
	
	Item<?> 					selected_item;
	
	Vector<ListViewListener<?>> listview_listeners = new Vector<ListViewListener<?>>();

	ListViewAdapter				adapter;
	
//	----------------------------------------------------------------------------------------------------------

	
	public ListView() {
		enable_input = false;
		adapter = new DefaultListViewAdapter();
		super.addChild(panel);
	}

	public ListView(String items[]) {
		this();
		addItems(items);
	}

	public ListView(Item<?> items[]) {
		this();
		addItems(items);
	}

	public ListView(Collection<? extends Item<?>> items) {
		this();
		addItems(items);
	}
	
	

	public ListView(ListViewAdapter adapter) {
		this.enable_input = false;
		this.adapter = adapter;
		super.addChild(panel);
	}

	public ListView(ListViewAdapter adapter, String items[]) {
		this(adapter);
		addItems(items);
	}

	public ListView(ListViewAdapter adapter, Item<?> items[]) {
		this(adapter);
		addItems(items);
	}

	public ListView(ListViewAdapter adapter, Collection<? extends Item<?>> items) {
		this(adapter);
		addItems(items);
	}
	
//	----------------------------------------------------------------------------------------------------------

	public void setViewMode(ViewMode mode) {
		this.view_mode = mode;
	}

	public ViewMode getViewMode() {
		return this.view_mode;
	}

//	----------------------------------------------------------------------------------------------------------
//	items

	public Panel getPanel() {
		return panel;
	}
	
	public void selectItem(Item<?> item) {
		selected_item = item;
		onItemSelected(selected_item);
	}
	
	public Item<?> getSelectedItem() {
		return selected_item;
	}
	
	public int getItemCount() {
		return panel.getContainer().getChildCount();
	}
	
	synchronized public void addItem(Item<?> item) {
		panel.addItem(item);
		item.list_view = this;
		if (selected_item == null) {
			selectItem(item);
		}
	}
	
	synchronized public void removeItem(Item<?> item) {
		panel.removeItem(item);
		item.list_view = null;
		item.removeFromParent();
		if (selected_item==item) {
			selected_item = null;
		}
	}
	
	synchronized public void addItem(String item) {
		addItem(new TextItem<Object>(item));
	}
	synchronized public void addItems(String[] items) {
		for (String item : items) {
			addItem(item);
		}
	}	
	synchronized public void addItems(Item<?>[] items) {
		for (Item<?> item : items) {
			addItem(item);
		}
	}
	synchronized public void addItems(Collection<? extends Item<?>> items) {
		for (Item<?> item : items) {
			addItem(item);
		}
	}
	synchronized public void clearItems() {
		for (UIComponent item : panel.container.getComonents()) {
			removeItem((Item<?>)item);
		}
	}
	

//	----------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	protected void onItemSelected(Item item) {
		for (ListViewListener listener : listview_listeners) {
			listener.itemSelected(this, item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof ListViewListener) {
			listview_listeners.add((ListViewListener)listener);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof ListViewListener) {
			listview_listeners.remove((ListViewListener)listener);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------

	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	
	public void render(Graphics2D g) {
		super.render(g);
		
		panel.setLocation(0, 0);
		panel.setSize(getWidth(), getHeight());
		
		adapter.layoutItems(
				this, 
				new Dimension(panel.getViewPortWidth(), panel.getViewPortHeight()), 
				view_mode, 
				new java.util.ArrayList<Item<?>>(panel.components));
	}
	

//	----------------------------------------------------------------------------------------------------------

	class ListViewPanel extends Panel
	{
		private static final long serialVersionUID = Version.VersionG2D;

		Vector<Item<?>> components = new Vector<Item<?>>();
		
		boolean addItem(Item<?> child) {
			synchronized (components) {
				components.add(child);
			}
			return super.getContainer().addChild(child);
		}
		
		boolean removeItem(Item<?> child) {
			synchronized(components) {
				components.remove(child);
			}
			return super.getContainer().removeChild(child);
		}
	}
	

	public static enum ViewMode
	{
		LIST,
		BIG_ICON,
		SMALL_ICON,
		DETAIL,
		;
	}
	
	
	public static interface ListViewListener<DataType> extends EventListener
	{
		public void itemSelected(ListView sender, Item<DataType> item);
	}
	
	public static abstract class Item<DataType> extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public DataType data;
		
		ListView list_view;
		
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (list_view!=null) {
				list_view.selectItem(this);
			}
		}

		protected void onDrawMouseHover(Graphics2D g) {
			g.setColor(new Color(1,1,1,0.2f));
			g.fill(local_bounds);
		}
	}
	
	public static class TextItem<DataType> extends Item<DataType>
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		@Property("文字颜色")
		public Color textColor = Color.WHITE;
		
		@Property("文字")
		public String text;
		
		public String[] texts;
		
		
		public TextItem(String text) {
			this.text = text;
		}
		public TextItem(String text, DataType data) {
			this.text = text;
			this.data = data;
		}
		
		public TextItem(String[] texts) {
			this.texts = texts;
			this.text = texts[0];
		}
		public TextItem(String[] texts, DataType data) {
			this.texts = texts;
			this.text = texts[0];
			this.data = data;
		}
		
		public String toString() {
			return "Item["+text+"]["+data+"]";
		}
	
		public void render(Graphics2D g) 
		{
			super.render(g);
			
			g.setColor(textColor);
			Drawing.drawString(g, text, 
					layout.BorderSize, 
					layout.BorderSize, 
					getWidth() - layout.BorderSize*2,
					getHeight() - layout.BorderSize*2, 
					Drawing.TEXT_ANCHOR_LEFT | Drawing.TEXT_ANCHOR_VCENTER
			);
		}
		
	}
	

	public static interface ListViewAdapter
	{
		public void layoutItems(ListView view, Dimension bounds, ViewMode view_mode, List<Item<?>> components);
	}
	
	public static class DefaultListViewAdapter implements ListViewAdapter
	{
		public int LIST_ITEM_HEIGHT		= 20;
		
		public int LIST_ITEM_SMALL_SIZE	= 20;

		public int LIST_ITEM_BIG_SIZE 	= 40;
		
		@Override
		public void layoutItems(ListView view, Dimension bounds, ViewMode viewMode, List<Item<?>> components) 
		{
			switch (viewMode)
			{
			case LIST: 
			case DETAIL: {
				int h = 0;
				for (UIComponent item : components) {
					item.setLocation(0, h);
					item.setSize(bounds.width, LIST_ITEM_HEIGHT);
					h += LIST_ITEM_HEIGHT;
				}
				break;
			}
			
			case BIG_ICON: 
			case SMALL_ICON: 
				int sw = viewMode == ViewMode.BIG_ICON ? LIST_ITEM_BIG_SIZE : LIST_ITEM_SMALL_SIZE;
				int h = 0;
				int w = 0;
				for (UIComponent item : components) {
					item.setLocation(w, h);
					item.setSize(sw, sw);
					w += sw;
					if (w >= bounds.width) {
						h += sw;
						w = 0;
					}
				}
				break;
			
			}
			
			
		}
	}
	
	
}
