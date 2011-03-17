package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Vector;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.util.Drawing;

/**
 * ComboBox只能放在Form里面
 * @author waza
 *
 */
public class ComboBox extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static interface ComboxListener<DataType> extends EventListener
	{
		public void itemSelected(ComboBox sender, Item<DataType> item);
	}
	
	public static class Item<D> extends UIComponent
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		ComboBox					combo_box;
		
		public Color 				textColor = Color.WHITE;
		public String 				text;
		public D 					data;
		
		public Item(String text) {
			this.text = text;
		}
		public Item(String text, D binddata) {
			this.text = text;
			this.data = binddata;
		}
		public String toString() {
			return "Item["+text+"]["+data+"]";
		}
		
		@SuppressWarnings("unchecked")
		public<T> T getBindData() {
			return (T)data;
		}
		
		
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (combo_box!=null) {
				combo_box.selectItem(this);
				combo_box.closeDropDown();
			}
		}
		public void render(Graphics2D g) {
			super.render(g);
			g.setColor(textColor);
			Drawing.drawStringBorder(g, text, 
					layout.BorderSize, 
					layout.BorderSize, 
					getWidth() - layout.BorderSize*2,
					getHeight() - layout.BorderSize*2, 
					Drawing.TEXT_ANCHOR_LEFT | Drawing.TEXT_ANCHOR_VCENTER
			);
		}
		protected void onDrawMouseHover(Graphics2D g) {
			g.setColor(new Color(1,1,1,0.2f));
			g.fill(local_bounds);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------
	
	@Property("文字颜色")
	public Color 						textColor;
	
	public Vector<Item<?>> 				items;
	
	protected Item<?> 					selected_item;
	
	public DropButton 					btn_dropdown;
	
	transient Vector<ComboxListener<?>> combox_listeners;
	
	transient protected DropDownBox 	drop_down_box;
	
	
//	----------------------------------------------------------------------------------------------------------

	public ComboBox() {
		this(new String[] {}, 100, 20);
	}

	public ComboBox(String[] items) {
		this(items, 100, 20);
	}

	public ComboBox(int w, int h) {
		this(new String[] {}, w, h);
	}
	
	public ComboBox(String[] items, int w, int h)
	{
		this.btn_dropdown		= new DropButton();
		this.items 				= new Vector<Item<?>>();
		this.textColor			= Color.WHITE;
		this.combox_listeners	= new Vector<ComboxListener<?>>();
		
		super.setSize(w, h);
		super.addChild(btn_dropdown);

		this.addItems(items);
	}
	
	private void openDropDown() {
		if (root_form!=null) {
			if (drop_down_box==null) {
				drop_down_box = new DropDownBox(root_form.getParent());
			}
		}
	}
	private void closeDropDown() {
		if (root_form!=null) {
			if (drop_down_box!=null) {
				drop_down_box.removeFromParent();
				drop_down_box = null;
			}
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------
//	items
	
	public void selectItem(Item<?> item) {
		selected_item = item;
		onItemSelected(selected_item);
	}
	
	public void selectItem(int index) {
		selectItem(items.get(index));
	}
	
	public Item<?> getSelectedItem() {
		return selected_item;
	}
	
	synchronized public int getSelectedItemIndex() {
		return items.indexOf(selected_item);
	}
	
	synchronized public Item<?> getItem(int index) {
		return items.get(index);
	}
	
	synchronized public int getItemCount() {
		return items.size();
	}
	
	@SuppressWarnings("unchecked")
	synchronized public Vector<Item<?>> getItems() {
		return (Vector<Item<?>>)items.clone();
	}
	
	synchronized public void addItem(Item<?> item) {
		items.add(item);
		item.combo_box = this;
		if (selected_item == null) {
			selectItem(item);
		}
	}
	synchronized public void addItem(String item) {
		addItem(new Item<Object>(item));
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
	synchronized public void removeItem(Item<?> item) {
		items.remove(item);
		item.combo_box = null;
		if (selected_item==item) {
			selected_item = null;
		}
	}
	synchronized public void clearItem(Item<?> child) {
		while (!items.isEmpty()) {
			removeItem(items.elementAt(0));
		}
	}
	
	

	
//	----------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	protected void onItemSelected(Item item) {
		for (ComboxListener listener : combox_listeners) {
			listener.itemSelected(this, item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof ComboxListener) {
			combox_listeners.add((ComboxListener)listener);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof ComboxListener) {
			combox_listeners.remove((ComboxListener)listener);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------
//	parent implements 
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		if (selected_item!=null) {
			g.setColor(textColor);
			Drawing.drawString(g, selected_item.text, 0, 0, getWidth() - btn_dropdown.getWidth(), getHeight(), 
					Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER
			);
		}
		
		
		btn_dropdown.setSize(
				btn_dropdown.getWidth(), 
				getHeight()-(layout.BorderSize<<1));
		btn_dropdown.setLocation(
				getWidth() - btn_dropdown.getWidth() - layout.BorderSize, 
				layout.BorderSize
		);
	}
	
//	----------------------------------------------------------------------------------------------------------

	public class DropButton extends Button 
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public DropButton() {
			super("");
			super.setSize(13, 25);
		}
		protected void onMouseDown(MouseEvent event) {
			openDropDown();
		}
	}
	
	public class DropDownBox extends DropDownList
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		int offsetx;
		int offsety;
		
		public DropDownBox(DisplayObjectContainer parent) 
		{
			for (Item<?> item : items) {
				item.removeFromParent();
				super.getContainer().addChild(item);
			}
			
			offsetx = ComboBox.this.getScreenX()-ComboBox.this.root_form.getScreenX();
			offsety = ComboBox.this.getScreenY()-ComboBox.this.root_form.getScreenY() + ComboBox.this.getHeight();
			
			int h = Math.min(items.size()*20, ComboBox.this.getHeight()*5);
			
			this.setSize(
					ComboBox.this.getWidth(), 
					h);
			this.setLocation(
					ComboBox.this.root_form.x + offsetx, 
					ComboBox.this.root_form.y + offsety);
			
			if (this.y + this.getHeight() > parent.getHeight()) {
//				System.out.println("out y");
				this.setLocation(
						this.x, 
						this.y - this.getHeight() - ComboBox.this.getHeight());
			}

			parent.addChild(this);
		}
		
		public void update() {
			super.update();
//			this.setLocation(
//					ComboBox.this.root_form.x + offsetx, 
//					ComboBox.this.root_form.y + offsety);
			if (getRoot().isMouseDown(java.awt.event.MouseEvent.BUTTON1)) {
				if (!isHitMouse() && timer>1) {
					closeDropDown();
				}
			}
		}
		
		
		
	}
	
}
