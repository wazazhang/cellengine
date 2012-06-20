package com.g2d.display.ui;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.util.Drawing;
import com.g2d.util.Drawing.ImageAnchor;


public abstract class BaseButton extends UIComponent 
{
	protected UILayout	layout_down;
	
	/** 当鼠标放置在该控件上的效果 */
	public Image		mouse_catched_mask;
	
	/**自定义按下造型*/
	@Property("自定义按下造型")
	public UILayout		custom_layout_down;
	

	@Property("上层图片对其方式")
	public ImageAnchor imageAnchor = ImageAnchor.C_C;
	@Property("上层图片偏移X")
	public int imageOffsetX = 0;
	@Property("上层图片偏移Y")
	public int imageOffsetY = 0;
	
	public Image imageDown;
	public Image imageUp;
	
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
		renderLayout(g, isOnDragged());
	}
	
	protected void renderLayout(Graphics2D g, boolean isDown) {
		Image cimg = imageUp;
		UILayout rect = layout;
		if (isDown) {
			if (custom_layout_down != null) {
				rect = custom_layout_down;
			}
			else if (layout_down != null) {
				rect = layout_down;
			}
			cimg = imageDown;
		} else {
			if (custom_layout != null) {
				rect = custom_layout;
			}
		}
		rect.render(g, 0, 0, getWidth(), getHeight());
		if (cimg != null) {
			Drawing.drawImageAnchor(g, cimg, 
					imageOffsetX, 
					imageOffsetY, 
					getWidth(),
					getHeight(),
					imageAnchor);
		}
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
