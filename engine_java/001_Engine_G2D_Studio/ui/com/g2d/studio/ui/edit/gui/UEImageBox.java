package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.annotation.Property;
import com.g2d.display.ui.UIComponent;

public class UEImageBox extends UIComponent implements SavedComponent
{
	@Property("图片地址")
	public String imagePath = "";
	
	public UEImageBox() {
		setSize(100, 100);
	}
	
	@Override
	public void onRead(Element e) throws Exception {
		
	}

	@Override
	public void onWrite(Element e) throws Exception
	{
		
	}
	
}