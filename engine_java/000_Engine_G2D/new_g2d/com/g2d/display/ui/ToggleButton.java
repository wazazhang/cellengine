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
		if (is_checked) {
			if (custom_layout_down != null) {
				custom_layout = custom_layout_down;
			} else {
				layout = layout_down;
			}
		} else {
			if (custom_layout_up != null) {
				custom_layout = custom_layout_up;
			} else {
				layout = layout_up;
			}
		}
		
		renderLayout(g);
	}
	
	
	public static class ImageToggleButton extends ToggleButton
	{

		public UILayout	focuse_layout_down	= UILayout.createBlankRect();
		public UILayout	focuse_layout_up	= UILayout.createBlankRect();
		
		public ImageToggleButton(Image checked, Image unchecked)
		{
			custom_layout_down = new UILayout();
			custom_layout_down.setImages(checked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
			
			custom_layout_up = new UILayout();
			custom_layout_up.setImages(unchecked, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
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
			if (isCatchedMouse() && focuse_layout_down != null && focuse_layout_up != null) {
				if (is_checked) {
					custom_layout = focuse_layout_down;
				} else {
					custom_layout = focuse_layout_up;
				}
			} else {
				if (is_checked) {
					custom_layout = custom_layout_down;
				} else {
					custom_layout = custom_layout_up;
				}
			}
			
			renderLayout(g);
		}
	}
}
