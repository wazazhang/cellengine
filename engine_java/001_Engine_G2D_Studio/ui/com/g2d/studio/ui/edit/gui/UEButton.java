package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.display.ui.Button;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.ui.edit.UIEdit;

public class UEButton extends Button implements SavedComponent
{
	
	public UEButton() {
		setSize(100, 30);
	}
	
	@Override
	public void onRead(UIEdit edit, Element e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWrite(UIEdit edit, Element e) throws Exception
	{
		
	}
	
}
