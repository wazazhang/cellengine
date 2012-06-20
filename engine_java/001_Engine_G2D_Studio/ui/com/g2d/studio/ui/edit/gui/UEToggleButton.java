package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.annotation.Property;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Button;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.studio.ui.edit.UITreeNode;

public class UEToggleButton extends UEButton implements SavedComponent
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
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
	
	protected void renderLayout(Graphics2D g) 
	{
		renderLayout(g, isChecked);
	}
}
