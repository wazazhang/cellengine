package com.g2d.display.ui;

import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.layout.UILayout;


public abstract class ToggleButton extends BaseButton 
{	
	boolean is_checked = false;
	
	public ToggleButton() {}
	
	public boolean isChecked() {
		return this.is_checked;
	}
	
	public void setChecked(boolean checked) {
		this.is_checked = checked;
	}
	
	@Override
	protected void onMouseClick(MouseEvent event) {
		super.onMouseClick(event);
		this.setChecked(!is_checked);
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		
		renderLayout(g);
	}
	
	protected void renderLayout(Graphics2D g) 
	{
		UILayout rect = layout;
		if (custom_layout != null) {
			rect = custom_layout;
		}
		if (is_checked) {
			if (custom_layout_down != null) {
				rect = custom_layout_down;
			}
		} 
		rect.render(g, 0, 0, getWidth(), getHeight());
	}
	
	
	public static class ImageToggleButton extends ToggleButton
	{

		public UILayout	focuse_layout_down	= UILayout.createBlankRect();
		public UILayout	focuse_layout_up	= UILayout.createBlankRect();
		
		public ImageToggleButton(Image checked, Image unchecked)
		{
			custom_layout_down = new UILayout();
			custom_layout_down.setImages(checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
			
			custom_layout = new UILayout();
			custom_layout.setImages(unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
		}
		
		public ImageToggleButton(Image checked, Image unchecked, Image focuse_checked, Image focuse_unchecked)
		{
			this(checked, unchecked);
			
			focuse_layout_down = new UILayout();
			focuse_layout_down.setImages(focuse_checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
			
			focuse_layout_up = new UILayout();
			focuse_layout_up.setImages(focuse_unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
		}
		
		public void render(Graphics2D g) 
		{
			super.render(g);
		}
		
		protected void renderLayout(Graphics2D g) 
		{
			UILayout rect = layout;
			if (custom_layout != null) {
				rect = custom_layout;
			}
			if (is_checked) {
				if (custom_layout_down != null) {
					rect = custom_layout_down;
				}
			} 
			if (isCatchedMouse() && 
					focuse_layout_down != null && 
					focuse_layout_up != null) {
				if (is_checked) {
					rect = focuse_layout_down;
				} else {
					rect = focuse_layout_up;
				}
			}
			rect.render(g, 0, 0, getWidth(), getHeight());
		}
	}
}
