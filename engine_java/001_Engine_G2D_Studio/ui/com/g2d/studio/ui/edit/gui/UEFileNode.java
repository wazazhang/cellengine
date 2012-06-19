package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.g2d.Graphics2D;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.ui.edit.UIEdit;

public class UEFileNode extends UIComponent implements SavedComponent
{
	private String fileName;
	private UERoot croot = null;
	
	public UEFileNode(String fileName) {
		this.fileName = fileName;
		this.enable_childs = false;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void update() {
		super.update();
		if (croot != null) {
			croot.x = 0;
			croot.y = 0;
			this.setSize(
					croot.getWidth(),
					croot.getHeight());
		}
	}
	@Override
	public void render(Graphics2D g) {
		if (debugDraw != null) {
			debugDraw.render(g, this);
		}
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
	public void readComplete(UIEdit edit) {
		try {
			load(edit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void writeBefore(UIEdit edit) {}
	
	public void load(UIEdit edit) throws Exception {
		if (croot == null) {
			if (fileName != null) {
				croot = edit.getFileNode(fileName);
				this.addChild(croot);
			}
		}
	}
}
