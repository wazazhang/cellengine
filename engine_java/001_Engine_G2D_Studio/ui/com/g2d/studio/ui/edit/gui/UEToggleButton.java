package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Button;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.studio.ui.edit.UIEdit;

public class UEToggleButton extends Button implements SavedComponent
{
	@Property("是否按下")
	public boolean isChecked = false;
	
	public UEToggleButton() {}
	
	@Override
	protected void onMouseClick(MouseEvent event) {
		super.onMouseClick(event);
		this.isChecked = (!isChecked);
	}
	
	@Override
	public void onRead(UIEdit edit, Element e) throws Exception {}
	@Override
	public void onWrite(UIEdit edit, Element e) throws Exception {}
	@Override
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
	
	protected void renderLayout(Graphics2D g) 
	{
		UILayout rect = layout;
		if (isChecked) {
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
}
