package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.display.ui.Container;
import com.g2d.studio.ui.edit.UIEdit;

public class UECanvas extends Container implements SavedComponent
{
	public UECanvas() {
		setSize(200, 200);
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