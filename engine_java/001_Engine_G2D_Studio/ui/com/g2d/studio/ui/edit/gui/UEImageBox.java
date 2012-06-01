package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.annotation.Property;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.ui.edit.UIEdit;

public class UEImageBox extends UIComponent implements SavedComponent
{
	@Property("图片地址")
	public String imagePath = "";
	
	public UEImageBox() {
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