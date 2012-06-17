package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.display.ui.Container;
import com.g2d.studio.ui.edit.UIEdit;

public class UEFileNode extends Container implements SavedComponent
{
	private String fileName;
	
	public UEFileNode(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void onRead(UIEdit edit, Element e, Document doc) throws Exception {
		fileName = e.getAttribute("fileName");
	}

	@Override
	public void onWrite(UIEdit edit, Element e, Document doc) throws Exception{
		e.setAttribute("fileName", fileName);
	}
	
	@Override
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
	
}
