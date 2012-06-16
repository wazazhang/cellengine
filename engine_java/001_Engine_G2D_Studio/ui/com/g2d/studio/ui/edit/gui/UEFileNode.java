package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.display.ui.Container;
import com.g2d.studio.ui.edit.UIEdit;

public class UEFileNode extends Container implements SavedComponent
{
	
	
	public UEFileNode() {}
	
	@Override
	public void onRead(UIEdit edit, Element e, Document doc) throws Exception {}

	@Override
	public void onWrite(UIEdit edit, Element e, Document doc) throws Exception{}
	
	@Override
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
	
}
