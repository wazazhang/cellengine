package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.display.ui.TextBoxSingle;
import com.g2d.studio.ui.edit.UIEdit;

public class UETextInput extends TextBoxSingle implements SavedComponent
{
	public UETextInput() {
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
