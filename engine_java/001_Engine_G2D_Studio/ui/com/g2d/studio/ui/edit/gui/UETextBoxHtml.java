package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.Engine;
import com.g2d.annotation.Property;
import com.g2d.display.ui.TextPan;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.text.MultiTextLayout;

public class UETextBoxHtml extends TextPan implements SavedComponent
{
	@Property("高级文本")
	public String HtmlText;
	
	public UETextBoxHtml() {}
	
	@Override
	public void onRead(UIEdit edit, Element e, Document doc) throws Exception {}

	@Override
	public void onWrite(UIEdit edit, Element e, Document doc) throws Exception{}	
	
	@Override
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
	
	@Override
	public void update() {
		
		super.update();
	}
}
