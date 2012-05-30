package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Element;

public interface SavedComponent {

	void onWrite(Element e) throws Exception;
	void onRead(Element e) throws Exception;
	
	
}
