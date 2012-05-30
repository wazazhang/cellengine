package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.display.ui.Label;

public class UELabel extends Label implements SavedComponent
{
	public UELabel() {
		setSize(100, 30);
	}
	
	@Override
	public void onRead(Element e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWrite(Element e) throws Exception
	{
		
	}
	
}