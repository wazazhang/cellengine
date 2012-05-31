package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

import com.g2d.studio.ui.edit.UIEdit;

public interface SavedComponent {

	void onWrite(UIEdit edit, Element e) throws Exception;
	void onRead(UIEdit edit, Element e) throws Exception;
	
	
}
