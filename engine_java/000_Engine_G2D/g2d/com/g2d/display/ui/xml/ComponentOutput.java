package com.g2d.display.ui.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;


/**
 * @author WAZA
 * 该类没有能够实际还原控件状态, 推荐使用 XStream 对控件反序列化
 */
@Deprecated
public class ComponentOutput 
{

	static public void putComponents(File path, UIComponent ... components)
	{
		try
		{
			if (!path.exists()) {
				path.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(path);
			putComponents(fos, components);
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	static public void putComponents(OutputStream os, UIComponent ... components)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Node root = document.createElement("components");
			document.appendChild(root);
			
			for (UIComponent component : components) 
			{
				putComponent(document, root, component);
			}
			
			document.normalizeDocument();
			document.normalize();
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(os);
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	static public void putComponent(Document document, Node root, UIComponent component)
	{
		try {
			Class.forName(component.getClass().getName()).newInstance();
		} catch (Exception e) {
			return;
		}

		Node node = document.createElement("component");
		root.appendChild(node);
		
		{
			Attr type = document.createAttribute("type");
			type.setNodeValue(component.getClass().getName());
			node.getAttributes().setNamedItem(type);
		
			Attr name = document.createAttribute("name");
			name.setNodeValue(component.name);
			node.getAttributes().setNamedItem(name);
		
			Attr bounds = document.createAttribute("bounds");
			bounds.setNodeValue(component.x + "," + component.y + "," + component.getWidth() + "," + component.getHeight());
			node.getAttributes().setNamedItem(bounds);
		}
		
		
		
		{
			Hashtable<String, Object> attributes = component.getAttributes();
			
			if (!attributes.isEmpty())
			{
				Node attrs = document.createElement("attributes");
				node.appendChild(attrs);
				
				for (String key : attributes.keySet()) 
				{
					Node attr = document.createElement("attribute");
					attrs.appendChild(attr);
					
					Attr attr_key = document.createAttribute("key");
					attr_key.setNodeValue(key);
					attr.getAttributes().setNamedItem(attr_key);
					
					Attr attr_value = document.createAttribute("value");
					attr_value.setNodeValue(attributes.get(key).toString());
					attr.getAttributes().setNamedItem(attr_value);
					
				
				}
			}
		}
		
		{
			Hashtable<String, Object> properties = component.getProperties();
			
			if (!properties.isEmpty())
			{
				Node attrs = document.createElement("properties");
				node.appendChild(attrs);
				
				for (String key : properties.keySet()) 
				{
					Node attr = document.createElement("property");
					attrs.appendChild(attr);
					
					Attr attr_key = document.createAttribute("key");
					attr_key.setNodeValue(key);
					attr.getAttributes().setNamedItem(attr_key);
					
					Attr attr_value = document.createAttribute("value");
					attr_value.setNodeValue(properties.get(key).toString());
					attr.getAttributes().setNamedItem(attr_value);
				}
			}
		}
		
		if (component instanceof Container)
		{
			Vector<UIComponent> components = ((Container)component).getComonents();
	
			Node child_root = document.createElement("components");
			node.appendChild(child_root);
			
			for (UIComponent child : components) 
			{
				putComponent(document, child_root, child);
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		{
//			Vector<UIComponent> components = ComponentInput.getComponents("/com/cell/g2d/display/ui/xml/Foundation.xml", null);
//			
//			Form xml_form = (Form)components.elementAt(0);
//			
//			ComponentOutput.putComponents(new File("./Foundation_m.xml"), xml_form);
//			
//			components = ComponentInput.getComponents("./Foundation_m.xml", null);
//			
//			
//			//TestForm test_form = new TestForm();
//			
//			ComponentOutput.putComponents(new File("./TestForm.xml"), test_form);
			
		}
	}
	
}


class writexml 
{
	private Document document;
	private String filename;

	public writexml(String name) throws ParserConfigurationException {
		filename = name;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
	}

	public void toWrite(String mytitle, String mycontent) {
		Element root = document.createElement("WorkShop");
		document.appendChild(root);
		Element title = document.createElement("Title");
		title.appendChild(document.createTextNode(mytitle));
		root.appendChild(title);
		Element content = document.createElement("Content");
		content.appendChild(document.createTextNode(mycontent));
		root.appendChild(content);

	}

	public void toSave() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
		} catch (TransformerException mye) {
			mye.printStackTrace();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	public static void main(String args[]) {
		try {
			writexml myxml = new writexml("d:\\9.xml");
			myxml.toWrite("中文题目", "中文内容");
			myxml.toSave();
			System.out.print("Your   writing   is   successful!");
		} catch (ParserConfigurationException exp) {
			exp.printStackTrace();
			System.out.print("Your   writing   is   failed!");
		}
	}
}
