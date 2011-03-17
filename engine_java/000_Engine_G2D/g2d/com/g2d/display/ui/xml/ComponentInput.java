package com.g2d.display.ui.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cell.CIO;
import com.g2d.display.ui.UIComponent;

public class ComponentInput 
{
	static public Vector<UIComponent> getComponents(String path, ComponentInputListener listener)
	{
		try
		{
			byte[] data = CIO.loadData(path);
			return getComponents(new ByteArrayInputStream(data), listener);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public Vector<UIComponent> getComponents(InputStream is, ComponentInputListener listener)
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			
			NodeList roots = doc.getChildNodes();
			Node root = roots.item(0);
			for (int r = 0; r<roots.getLength(); ++r) {
				if (roots.item(r).getNodeName().equals("components")) {
					root = roots.item(r);
					break;
				}
			}
			
			return getComponents(root, listener);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public Vector<UIComponent> getComponents(Node node, ComponentInputListener listener)
	{
		try
		{
			if (node.getNodeName().equals("components"))
			{
				NodeList list = node.getChildNodes();

				Vector<UIComponent> components = new Vector<UIComponent>(list.getLength());
				
				for (int r = 0; r<list.getLength(); ++r) 
				{
					if (list.item(r).getNodeName().equals("component")) 
					{
						UIComponent child = getComponent(list.item(r), listener);
						components.add(child);
					}
				}
				
				return components;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Vector<UIComponent>(0);
	}
	
	
	static public UIComponent getComponent(Node node, ComponentInputListener listener)
	{
//		try
//		{
//			if (node.getNodeName().equals("component"))
//			{
//				NamedNodeMap attr	= node.getAttributes();
//				String type = attr.getNamedItem("type").getNodeValue().trim();
//				String name = attr.getNamedItem("name").getNodeValue().trim();
//				String[] bounds = attr.getNamedItem("bounds").getNodeValue().split(",");
//				
//				UIComponent component = (UIComponent)Class.forName(type).newInstance();
//				component.name = name;
//				component.x = Integer.parseInt(bounds[0].trim());
//				component.y = Integer.parseInt(bounds[1].trim());
//				component.local_bounds.x = 0;
//				component.local_bounds.y = 0;
//				component.local_bounds.width = Integer.parseInt(bounds[2].trim());
//				component.local_bounds.height = Integer.parseInt(bounds[3].trim());
//				
//				//
//				Node properties = null;
//				Node attributes = null;
//				Node components = null;
//				{
//					NodeList list = node.getChildNodes();
//					for (int r = 0; r<list.getLength(); ++r) {
//						if (list.item(r).getNodeName().equals("properties")) {
//							properties = list.item(r);
//						}
//						else if (list.item(r).getNodeName().equals("attributes")) {
//							attributes = list.item(r);
//						}
//						else if (list.item(r).getNodeName().equals("components")) {
//							components = list.item(r);
//						}
//					}
//				}
//				
//				// 得到 item 属性
//				if (properties!=null) 
//				{
//					NodeList list = properties.getChildNodes();
//					for (int r = 0; r<list.getLength(); ++r) 
//					{
//						if (list.item(r).getNodeName().equals("property")) 
//						{
//							NamedNodeMap ia = list.item(r).getAttributes();
//							String k = ia.getNamedItem("key").getNodeValue();
//							String v = ia.getNamedItem("value").getNodeValue();
//							component.setProperty(k, v);
//							if (listener!=null) listener.componentPropertySet(component, k, v);
//						}
//					}
//				}
//				
//				// 得到 item 附加数据
//				if (attributes!=null) 
//				{
//					NodeList list = attributes.getChildNodes();
//					for (int r = 0; r<list.getLength(); ++r) 
//					{
//						if (list.item(r).getNodeName().equals("attribute")) 
//						{
//							NamedNodeMap ia = list.item(r).getAttributes();
//							String k = ia.getNamedItem("key").getNodeValue();
//							String v = ia.getNamedItem("value").getNodeValue();
//							component.setAttribute(k, v);
//							if (listener!=null) listener.componentAttributeSet(component, k, v);
//						}
//					}
//				}
//				
//				// 得到 item 附加数据
//				if (components!=null) 
//				{
//					if (component instanceof Container)
//					{
//						Vector<UIComponent> childs = getComponents(components, listener);
//						
//						if (childs != null)
//						{
//							for (UIComponent child : childs) 
//							{
//								if (((Container)component).getXMLComponent(child.name)==null) 
//								{
//									((Container)component).addChild(child);
//									((Container)component).putXMLComponent(child.name, child);
//								}
//								else
//								{
//									Tools.printError(node + "\n子控件name冲突! name=\"" + child.name+"\"");
//								}
//							}
//						}
//						
//					}
//					else
//					{
//						Tools.printError(node + "\n该控件不是容器,所以不能拥有子控件! name=\"" + component.name+"\"");
//					}
//					
//				}
//				
//				return component;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return null;
	}



}
