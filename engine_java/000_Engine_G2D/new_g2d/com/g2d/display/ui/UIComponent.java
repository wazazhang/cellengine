package com.g2d.display.ui;

import java.text.AttributedString;
import java.util.Vector;

import com.cell.sound.Sound;
import com.cell.util.MarkedHashtable;
import com.g2d.AnimateCursor;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;
import com.g2d.display.TextTip;
import com.g2d.display.Tip;
import com.g2d.display.UIObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.event.Action;
import com.g2d.display.ui.event.ActionEvent;
import com.g2d.display.ui.event.ActionListener;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayoutManager;
import com.g2d.geom.Rectangle;
import com.g2d.text.TextBuilder;



public abstract class UIComponent extends UIObject
{	
//	-----------------------------------------------------------------------------------------------------
//	visual
	
	public String 						name;
	
	/**提示文字*/
	public				String			tip;
	
	/**自定控件图片或颜色*/
	@Property("自定控件样式")
	public				UILayout		custom_layout;	
	
	/**被鼠标点击后播放的声音*/
	public 				Sound			on_click_sound;
	
	/**当前被渲染的layout,如果custom_layout不为空，或者在render前设置该值，将改变渲染样式*/
	transient protected	UILayout 		layout;
	
//	-----------------------------------------------------------------------------------------------------
//	switch
//	-----------------------------------------------------------------------------------------------------
//	event
	TextTip					default_tip = new TextTip();
	AnimateCursor			cursor;
	Window 					root_form;
	Vector<ActionListener>	action_listeners;
	Action					action;
	
//	-----------------------------------------------------------------------------------------------------
//	user data
	
	MarkedHashtable 		data_map;
	Object					user_data;
	public int				tag_data;
	
	public EditModeDraw		editDraw = null;
//	-----------------------------------------------------------------------------------------------------

	
	
	public UIComponent() 
	{
		clip_local_bounds = true;
		ignore_render_without_parent_bounds = true;
	
		action_listeners	= new Vector<ActionListener>();
		layout 				= UILayout.createBlankRect();
	}
	
	public void setBounds(int x, int y, int w, int h) {
		super.setLocation(x, y);
		super.setLocalBounds(0, 0, w, h);
	}
	
	public void setBounds(Rectangle bounds) {
		super.setLocation(bounds.x, bounds.y);
		super.setLocalBounds(0, 0, bounds.width, bounds.height);
	}
	
//	-----------------------------------------------------------------------------------------------------

	public void setAction(String action) {
		this.action = new Action(action);
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Action getAction() {
		return action;
	}
	
//	-----------------------------------------------------------------------------------------------------

	public void setLayout(UILayout layout) {
		this.layout = layout;
	}
	
	public UILayout getLayout() {
		if (custom_layout!=null){
			return custom_layout;
		}else{
			return layout;
		}
	}

	public void setCustomLayout(UILayout layout) {
		this.custom_layout 	= layout;
	}
	
	public UILayout getCustomLayout() {
		return this.custom_layout;
	}
	
	public void setCursor(AnimateCursor cursor) {
		this.cursor = cursor;
	}
	
	@Override
	public Tip getTip() {
		if (tip != null && !tip.isEmpty()) {
			AttributedString atext = TextBuilder.buildScript(tip);
			default_tip.setText(atext);
			return default_tip;
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------

	public boolean isFocusedComponent() {
		if (root_form!=null) {
			if (root_form.isFocused() && isFocused()) {
				return true;
			}
		}else{
			if (getParent()!=null && getParent() instanceof InteractiveObject) {
				InteractiveObject p = (InteractiveObject)getParent();
				if (p.isFocused() && isFocused()) {
					return true;
				}
			}
		}
		return false;
	}
	
//	-----------------------------------------------------------------------------------------------------

	void setRootForm(DisplayObjectContainer parent) {
		if (parent instanceof Window) {
			root_form = (Window)parent;
		}
		else if (parent instanceof UIComponent) {
			root_form = ((UIComponent)parent).root_form;
		}
	}
	
	public void added(DisplayObjectContainer parent) {
		setRootForm(parent);
		UILayoutManager.getInstance().setLayout(this);
	}
	public void removed(DisplayObjectContainer parent) {}
	public void update(){}
	public void render(Graphics2D g) {
		renderLayout(g);
		if (editDraw != null) {
			editDraw.render(g, this);
		}
	}
	
	protected void renderLayout(Graphics2D g) 
	{
		if (custom_layout!=null){
			custom_layout.render(g, 0, 0, getWidth(), getHeight());
		}else if (layout!=null){
			layout.render(g, 0, 0, getWidth(), getHeight());
		}
	}
	
	protected void renderCatchedMouse(Graphics2D g)
	{
		if (isPickedMouse()){
			onDrawMouseHover(g);
		}
	}
	
	@Override
	protected void renderAcceptDrapDrop(Graphics2D g) 
	{
		g.setColor(new Color(0x40ffffff));
		g.fillRect(local_bounds);
	}

	@Override
	protected void renderAfter(Graphics2D g) 
	{
		setRootForm(getParent());
		
		super.renderAfter(g);
		
		if (!enable) {
			onDrawDisable(g);
		}
	}
	
//	-----------------------------------------------------------------------------------------------------
	protected void onDrawDisable(Graphics2D g){
		g.clip(local_bounds);
		g.setColor(new Color(0x70000000));
		g.fillRect(local_bounds);
	}
	
	/**
	 * 渲染鼠标停留在该控件上的效果
	 * @param g
	 */
	protected void onDrawMouseHover(Graphics2D g) {}
	
	protected void onMouseClick(MouseEvent event)
	{
		if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
			for (ActionListener listener : action_listeners) {
				listener.itemAction(this, new ActionEvent(this, action));
			}
			if (on_click_sound!=null) {
				on_click_sound.play();
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------

	
//	-----------------------------------------------------------------------------------------------------
	
	public void addEventListener(EventListener listener)
	{
		super.addEventListener(listener);
		if (listener instanceof ActionListener) {
			action_listeners.add((ActionListener)listener);
		}
	}
	
	public void removeEventListener(EventListener listener) 
	{
		super.removeEventListener(listener);
		if (listener instanceof ActionListener) {
			action_listeners.remove((ActionListener)listener);
		}
	}
	
//	-----------------------------------------------------------------------------------------------------

	public<T> void setUserData(T data) 
	{
		user_data = data;
	}
	
	@SuppressWarnings("unchecked")
	public<T> T getUserData() 
	{
		return (T)user_data;
	}
	
	
//	-----------------------------------------------------------------------------------------------------

	
	/** return system define properties */
	public MarkedHashtable getProperties() 
	{
		MarkedHashtable ret = new MarkedHashtable();
		if (data_map!=null) {
			for (String key : data_map.keySet()) {
				if (key.startsWith("property_")) {
					ret.put(key.substring("property_".length()), data_map.get(key));
				}
			}
		}
		return ret;
	}

	/** return user define attributes */
	public MarkedHashtable getAttributes()
	{
		MarkedHashtable ret = new MarkedHashtable();
		if (data_map!=null) {
			for (String key : data_map.keySet()) {
				if (key.startsWith("attribute_")) {
					ret.put(key.substring("attribute_".length()), data_map.get(key));
				}
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public<T> T getProperty(String key) {
		if (data_map==null) return null;
		return (T)data_map.get("property_"+key);
	}
	
	@SuppressWarnings("unchecked")
	public<T> T getAttribute(String key) {
		if (data_map==null) return null;
		return (T)data_map.get("attribute_"+key);
	}
	
	public Object setProperty(String key, Object value) {
		if (data_map==null){
			data_map = new MarkedHashtable();
		}
		if (value==null) {
			return data_map.remove("property_"+key);
		}
		return data_map.put("property_"+key, value);
	}
	
	public Object setAttribute(String key, Object value) {
		if (data_map==null){
			data_map = new MarkedHashtable();
		}
		if (value==null) {
			return data_map.remove("attribute_"+key);
		}
		return data_map.put("attribute_"+key, value);
	}
	
//	-----------------------------------------------------------------------------------------------------


	static public interface EditModeDraw
	{
		public void render(Graphics2D g, UIComponent ui);
	}

	


	
	
}
