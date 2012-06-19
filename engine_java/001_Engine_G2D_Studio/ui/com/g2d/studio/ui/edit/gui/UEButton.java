package com.g2d.studio.ui.edit.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.g2d.BufferedImage;
import com.g2d.annotation.Property;
import com.g2d.awt.util.Tools;
import com.g2d.display.ui.Button;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.studio.res.Res;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.studio.ui.edit.UITreeNode;

public class UEButton extends Button implements SavedComponent
{
	public static BufferedImage mouse_catched_image = Tools.wrap_awt(Res.img_light64);

	@Property("按下图片文本")
	public String imageTextDown;
	@Property("图片文本")
	public String imageTextUp;
	
	public UEButton() {
		this.mouse_catched_mask = mouse_catched_image;
	}
	
	@Override
	public void onRead(UIEdit edit, Element e, Document doc) throws Exception 
	{
		if (imageTextDown != null && !imageTextDown.isEmpty()) {
			imageDown = edit.getLayoutManager().getImage(imageTextDown);
		}
		if (imageTextUp != null && !imageTextUp.isEmpty()) {
			imageUp = edit.getLayoutManager().getImage(imageTextUp);
		}
		NodeList list = e.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i) instanceof Element) {
				Element ev = (Element) list.item(i);
				if (ev.getNodeName().equals("layout_down")) {
					custom_layout_down = UITreeNode.readLayout(edit, ev);
				}
			}
		}
	}

	@Override
	public void onWrite(UIEdit edit, Element e, Document doc) throws Exception
	{
		if (custom_layout_down != null) {
			Element rect = doc.createElement("layout_down");
			UITreeNode.writeLayout(edit, custom_layout_down, rect);
			e.appendChild(rect);
		}
	}

	@Override
	public void readComplete(UIEdit edit) {}
	@Override
	public void writeBefore(UIEdit edit) {}
}
