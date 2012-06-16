package com.g2d.display.ui;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.UILayout;


public abstract class BaseButton extends UIComponent 
{
	protected UILayout	layout_down;
	
	/** 当鼠标放置在该控件上的效果 */
	public Image		mouse_catched_mask;
	
	/**自定义按下造型*/
	@Property("自定义按下造型")
	public UILayout		custom_layout_down;
	
	public BaseButton(int width, int height) {
		setSize(width, height);
	}
	
	public BaseButton() {}
	
	@Override
	protected void defaultLayout() {
		super.defaultLayout();
		super.setSize(20, 20);
	}
	
	@Override
	public void setLayout(UILayout layout) {
		super.setLayout(layout);
		layout_down = layout;
	}
	
	public void setLayout(UILayout up, UILayout down) {
		super.setLayout(up);
		layout_down = down;
	}
	
	@Override
	public void setCustomLayout(UILayout layout) {
		super.setCustomLayout(layout);
		custom_layout_down = layout;
	}
	public void setCustomLayout(UILayout up, UILayout down) {
		super.setCustomLayout(up);
		custom_layout_down = down;
	}
	
	public void render(Graphics2D g) 
	{
		
		
		super.render(g);
	}
	
	protected void renderLayout(Graphics2D g) 
	{
		UILayout rect = layout;
		if (isOnDragged()) {
			if (custom_layout_down != null) {
				rect = custom_layout_down;
			}
			else if (layout_down != null) {
				rect = layout_down;
			}
		} else {
			if (custom_layout != null) {
				rect = custom_layout;
			}
		}
		rect.render(g, 0, 0, getWidth(), getHeight());
	}
	
	
	protected void onDrawMouseHover(Graphics2D g) {
		if(mouse_catched_mask==null){
			g.setColor(new Color(1,1,1,0.2f));
			g.fillRect(local_bounds);
		}else{
			g.drawImage(mouse_catched_mask, 0, 0, getWidth(), getHeight());
		}
	}
	
}
