package com.g2d.studio.ui.edit.gui;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.cell.CObject;
import com.cell.gfx.gui.Button;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.studio.ui.edit.UITreeNode;

public class UERoot extends Container implements SavedComponent
{
	public UERoot() 
	{	
		this.setSize(640, 480);
	}

	public void fromXMLStream(InputStream is) throws Exception
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element e = doc.getDocumentElement();
			internalRead(doc, e, this);
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());
			throw err;
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw e;
		}
	}
	
	public void toXMLStream(OutputStream os) throws Exception
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element e = doc.createElement(this.getClass().getCanonicalName());
			doc.appendChild(e);
			internalWrite(doc, e, this);
			doc.normalizeDocument();
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(
					new DOMSource(doc), 
					new StreamResult(os));
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw e;
		}
	}

	private void internalRead(Document doc, Element e, UIComponent ui)
	{
		
	}
	
	private void internalWrite(Document doc, Element e, UIComponent ui) throws Exception
	{
		writeFields(e, ui);
		if (ui instanceof SavedComponent) {
			((SavedComponent)ui).onWrite(e);
		}
		
		UILayout layout = ui.getCustomLayout();
		if (layout != null) {
			Element rect = doc.createElement("layout");
			rect.setAttribute("style", layout.getStyle().toString());
			rect.setAttribute("bgc", layout.getBackColor().toHexString());
			rect.setAttribute("bdc", layout.getBorderColor().toHexString());
			if (layout instanceof ImageUILayout) {
				ImageUILayout imgrect = (ImageUILayout)layout;
				rect.setAttribute("img", imgrect.srcImageName()+"");
				rect.setAttribute("clip", imgrect.clip_border+"");
			}
			e.appendChild(rect);
		}
		
		if (ui instanceof Container) 
		{
			Container cc = (Container)ui;
			Vector<UIComponent> comps = cc.getComonents();
			if (!comps.isEmpty()) {
				Element childs = doc.createElement("childs");
				for (UIComponent cn : comps) {
					Element child = doc.createElement(cn.getClass().getCanonicalName());
					internalWrite(doc, child, cn);
					childs.appendChild(child);
				}
				e.appendChild(childs);
			}
		}
		
	}
	
	private static void writeFields(Element e, UIComponent ui) 
	{
		e.setAttribute("name", ui.name);
		e.setAttribute("clipbounds", ui.clip_local_bounds+"");
		e.setAttribute("width", ui.getWidth()+"");
		e.setAttribute("height", ui.getHeight()+"");
		
		Field[] fields = ui.getClass().getFields();
		for (Field f : fields) {
			try {
				if (f.getName().equals("clip_local_bounds")) {
					continue;
				}
				if (f.getName().equals("custom_layout")) {
					continue;
				}
				if (f.getAnnotation(Property.class) != null) {
					Object attr = f.get(ui);
					e.setAttribute(f.getName(), attr + "");
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void onRead(Element e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWrite(Element e) throws Exception
	{
		
	}
	
}
