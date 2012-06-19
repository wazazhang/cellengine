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
	private UERoot root;
	
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
		if (root != null) {
			root.x = 0;
			root.y = 0;
			this.setSize(
					root.getWidth(),
					root.getHeight());
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
		load(edit);
	}
	@Override
	public void writeBefore(UIEdit edit) {}
	
	public void load(UIEdit edit) {
		if (fileName != null) {
			root = edit.getFileNode(fileName);
			this.addChild(root);
		}
	}
}
