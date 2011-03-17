package com.cell.gfx.gui.edit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gfx.gui.Button;
import com.cell.gfx.gui.CheckBox;
import com.cell.gfx.gui.Command;
import com.cell.gfx.gui.Control;
import com.cell.gfx.gui.DropDownSelect;
import com.cell.gfx.gui.Form;
import com.cell.gfx.gui.FormListener;
import com.cell.gfx.gui.GroupPageSelect;
import com.cell.gfx.gui.Guage;
import com.cell.gfx.gui.Item;
import com.cell.gfx.gui.ItemListener;
import com.cell.gfx.gui.LabelBar;
import com.cell.gfx.gui.RichTextBox;
import com.cell.gfx.gui.TextBox;
import com.cell.gfx.gui.TextBoxSingle;

public class FormConfig 
{
	static public boolean initForm(Form form, String file, FormListener flistener, FormConfigListener fclistener)
	{
		InputStream is = CIO.getInputStream(file);
		try{
			initForm(form, is, flistener, fclistener);
			return true;
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {}
		}
		return false;
	}
	
	static public void initForm(Form form, InputStream is, FormListener flistener, FormConfigListener fclistener) throws Exception
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		
		NodeList roots = doc.getChildNodes();
		Node root = roots.item(0);
		for (int r = 0; r<roots.getLength(); ++r) {
			if (roots.item(r).getNodeName().equals("form")) {
				root = roots.item(r);
				break;
			}
		}
		
		if (root.getNodeName().equals("form"))
		{
			// 得到窗口主属性
			NamedNodeMap formAttribute	= root.getAttributes();
			{
				String title = formAttribute.getNamedItem("title").getNodeValue();
				int x = Integer.parseInt(formAttribute.getNamedItem("x").getNodeValue());
				int y = Integer.parseInt(formAttribute.getNamedItem("y").getNodeValue());
				int w = Integer.parseInt(formAttribute.getNamedItem("width").getNodeValue());
				int h = Integer.parseInt(formAttribute.getNamedItem("height").getNodeValue());
				form.setTitle(title);
				form.WindowX = x;
				form.WindowY = y;
				form.resize(w, h);
				//System.out.println(title + " " + x + " " + y + " " + w + " " + h);
			}
			
			//
			Node properties = null;
			Node attributes = null;
			{
				NodeList list = root.getChildNodes();
				for (int r = 0; r<list.getLength(); ++r) {
					if (list.item(r).getNodeName().equals("properties")) {
						properties = list.item(r);
					}
					else if (list.item(r).getNodeName().equals("attributes")) {
						attributes = list.item(r);
					}
				}
			}
			
			// 得到 item 附加属性
			if (properties!=null) 
			{
				try{
					NodeList list = properties.getChildNodes();
					for (int r = 0; r<list.getLength(); ++r) 
					{
						if (list.item(r).getNodeName().equals("property")) 
						{
							NamedNodeMap ia = list.item(r).getAttributes();
							String k = ia.getNamedItem("key").getNodeValue();
							String v = ia.getNamedItem("value").getNodeValue();
							form.setProperty(k, v);
							//System.out.println("set form property : " + k + " = " + v);
							setFormProperty(form, k, v, ia);
							if (fclistener!=null) {
								fclistener.formPropertySet(form, k, v);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
			}
			
			// 得到 item 附加数据
			if (attributes!=null) 
			{
				try{
					NodeList list = attributes.getChildNodes();
					for (int r = 0; r<list.getLength(); ++r) 
					{
						if (list.item(r).getNodeName().equals("attribute")) 
						{
							NamedNodeMap ia = list.item(r).getAttributes();
							String k = ia.getNamedItem("key").getNodeValue();
							String v = ia.getNamedItem("value").getNodeValue();
							form.setAttribute(k, v);
							//System.out.println("put form attribute : " + k + " = " + v);
							if (fclistener!=null) {
								fclistener.formAttributeSet(form, k, v);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			NodeList list = root.getChildNodes();
			for (int i=0; i<list.getLength(); i++)
			{
				if (list.item(i).getNodeName().equals("items"))
				{
					createItems(form, list.item(i).getChildNodes(), flistener, fclistener) ;
				}
				else if (list.item(i).getNodeName().equals("groups"))
				{
					createGroups(form, list.item(i).getChildNodes(), flistener, fclistener);
				}
			}
			
			for (int i=0; i<form.size(); i++)
			{
				validateProperty(form, form.getItem(i));
			}
			
			
		}
		
	}
	
	static private void createItems(Form form, NodeList items, FormListener flistener, FormConfigListener fclistener) 
	{
		for (int i=0; i<items.getLength(); i++)
		{
			Node node = items.item(i);
		
			if (node.getNodeName().equals("item"))
			{
				Item item = getItem(form, node, flistener, fclistener);
				if (item != null) {
					form.appendItem(item);
				}
			}
		}
	}
	
	
	static private Item getItem(Form form, Node node, FormListener flistener, FormConfigListener fclistener)
	{
		try 
		{
			// 得到 item 主属性
			NamedNodeMap attr	= node.getAttributes();
			String type = attr.getNamedItem("type").getNodeValue();
			String id = attr.getNamedItem("id").getNodeValue();
			String text = attr.getNamedItem("text").getNodeValue();
			int x = Integer.parseInt(attr.getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(attr.getNamedItem("y").getNodeValue());
			int w = Integer.parseInt(attr.getNamedItem("width").getNodeValue());
			int h = Integer.parseInt(attr.getNamedItem("height").getNodeValue());
			String desc = attr.getNamedItem("desc").getNodeValue();
			
			//System.out.println(type + " " + id + " " + text + " " + x + " " + y + " " + w + " " + h + " " + desc);
			
			Item item = (Item)Class.forName(type).newInstance();
			item.setText(text);
			item.X = x;
			item.Y = y;
			item.resize(w, h);
			item.setClick(new Command(text, desc));
		
			//
			if (form.getAttributeItem(id)==null) {
				form.putAttributeItem(id, item);
			}else{
				try{
					throw new Exception("控件id冲突!\n"+
							type + " " + id + " " + text + " " + x + " " + y + " " + w + " " + h + " " + desc
							);
				}catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			//
			Node properties = null;
			Node attributes = null;
			{
				NodeList list = node.getChildNodes();
				for (int r = 0; r<list.getLength(); ++r) {
					if (list.item(r).getNodeName().equals("properties")) {
						properties = list.item(r);
					}
					else if (list.item(r).getNodeName().equals("attributes")) {
						attributes = list.item(r);
					}
				}
			}
			
			// 得到 item 附加属性
			if (properties!=null) 
			{
				try{
					NodeList list = properties.getChildNodes();
					for (int r = 0; r<list.getLength(); ++r) 
					{
						if (list.item(r).getNodeName().equals("property")) 
						{
							NamedNodeMap ia = list.item(r).getAttributes();
							String k = ia.getNamedItem("key").getNodeValue();
							String v = ia.getNamedItem("value").getNodeValue();
							item.setProperty(k, v);
							//System.out.println("\tset property : " + k + " = " + v);
							setItemProperty(item, k, v, ia) ;
							if (fclistener!=null) {
								fclistener.itemPropertySet(item, k, v);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
			}
			
			// 得到 item 附加数据
			if (attributes!=null) 
			{
				try{
					NodeList list = attributes.getChildNodes();
					for (int r = 0; r<list.getLength(); ++r) 
					{
						if (list.item(r).getNodeName().equals("attribute")) 
						{
							NamedNodeMap ia = list.item(r).getAttributes();
							String k = ia.getNamedItem("key").getNodeValue();
							String v = ia.getNamedItem("value").getNodeValue();
							item.setAttribute(k, v);
							//System.out.println("\tput attribute : " + k + " = " + v);
							if (fclistener!=null) {
								fclistener.itemAttributeSet(item, k, v);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// set default command listener
			if (form instanceof ItemListener) {
				item.setCommandListener((ItemListener)form);
			}
			// call back form listener
			if (flistener!=null) {
				flistener.formAction(form, item, FormListener.ITEM_ADDED);
			}
			
			return item;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	

	static private void createGroups(Form form, NodeList groups, FormListener flistener, FormConfigListener fclistener) 
	{
		for (int i=0; i<groups.getLength(); i++)
		{
			Node node = groups.item(i);
		
			if (node.getNodeName().equals("group"))
			{
				try
				{
					// 得到 item 主属性
					NamedNodeMap attr	= node.getAttributes();
					String type = attr.getNamedItem("type").getNodeValue();
					String id = attr.getNamedItem("id").getNodeValue();
					
					//System.out.println(type + " " + id + " ");
					
					if (type.equals("com.cell.gui.GroupPageSelect")) 
					{
						int x = Integer.parseInt(attr.getNamedItem("x").getNodeValue());
						int y = Integer.parseInt(attr.getNamedItem("y").getNodeValue());
						int w = Integer.parseInt(attr.getNamedItem("width").getNodeValue());
						int h = Integer.parseInt(attr.getNamedItem("height").getNodeValue());
	
						com.cell.gfx.gui.GroupPageSelect group = new GroupPageSelect(form, x, y, w, h);
						
						NodeList list = node.getChildNodes();
						for (int s=0; s<list.getLength(); s++)
						{
							if (list.item(s).getNodeName().equals("pages"))
							{
								NodeList pages = list.item(s).getChildNodes();
								
								for (int p=0; p<pages.getLength(); p++)
								{
									Node page = pages.item(p);
								
									if (page.getNodeName().equals("page"))
									{
										NamedNodeMap pattr	= page.getAttributes();
										String head = pattr.getNamedItem("head").getNodeValue();
										
										int pageid = group.addPage(head);
										
										NodeList items = page.getChildNodes();
										
										for (int pi=0; pi<items.getLength(); pi++)
										{
											if (items.item(pi).getNodeName().equals("item"))
											{
												Item item = getItem(form, items.item(pi), flistener, fclistener);
												if (item != null) {
													group.addItem(pageid, item, item.X, item.Y);
												}
											}
										}
										
										
									}
								}
							}
						}
						
						form.putAttributeGroup(id, group);
					}
				}catch(Exception err) {
					err.printStackTrace();
				}
			}
		}
	}
	
	
	
	static private void setFormProperty(Form item, String key, String value, NamedNodeMap map) 
	{
		
	}
	
	
	
	static private void setItemProperty(Item item, String key, String value, NamedNodeMap map) 
	{
		if (key.equals("canfocus")) 
		{
			item.CanFocus = Boolean.parseBoolean(value);
		}
		else if (key.equals("textcolor"))
		{
			item.TextColor = (int)Long.parseLong(value, 16);
		}
		
		if (item instanceof LabelBar)
		{
			LabelBar lbl = (LabelBar)item;
			
			if (key.equals("textcenter"))
			{
				lbl.IsTextCenter = Boolean.parseBoolean(value);
			}
		}
		
		if (item instanceof Button)
		{
			Button btn = (Button)item;
			
			if (key.equals("pressonclick"))
			{
				btn.IsPressOnClick = Boolean.parseBoolean(value);
			}
			else if (key.equals("pressed"))
			{
				btn.setPressed(Boolean.parseBoolean(value));
			}
			// repels
		}
		
		if (item instanceof CheckBox)
		{
			CheckBox chk = (CheckBox)item;
			
			if (key.equals("checked"))
			{
				chk.setCheck(Boolean.parseBoolean(value));
			}
			// repels
		}
		
		if (item instanceof DropDownSelect)
		{
			DropDownSelect select = (DropDownSelect)item;
			
			if (key.equals("command"))
			{
				select.addCommand(new Command(value, map.getNamedItem("desc").getNodeValue()));
			}
		}
		
		if (item instanceof Guage)
		{
			Guage guage = (Guage)item;
			
			if (key.equals("guagecolor"))
			{
				guage.StripColor = (int)Long.parseLong(value, 16);
			}
			else if (key.equals("guagebackcolor"))
			{
				guage.StripBackColor = (int)Long.parseLong(value, 16);
			}
			
		}
		
		if (item instanceof RichTextBox)
		{
			RichTextBox text = (RichTextBox)item;
			
			if (key.equals("textscript"))
			{
				RichTextBox.Scripts script = RichTextBox.buildScript(value);
				text.setText(script);
			}
		}

		if (item instanceof TextBox)
		{
			TextBox text = (TextBox)item;
			
			if (key.equals("textscript"))
			{
				text.CanEdit = Boolean.parseBoolean(value);
			}
		}
		
		if (item instanceof TextBoxSingle)
		{
			//TextBoxSingle text = (TextBoxSingle)item;
			
		}
		
		
		
		
	}
	

	static private void validateProperty(Form form, Item item)
	{
		if (item instanceof Button)
		{
			Button btn = (Button)item;
			if (btn.IsPressOnClick)
			{
				Object repels = btn.getProperty("repels");
				
				if (repels!=null)
				{
					String[] rvs = CUtil.splitString(repels.toString(), ",");
					
					Vector<Button> rvd = new Vector<Button>(rvs.length);
					
					for (int i=0; i<rvs.length; i++) {
						Item rv = form.getAttributeItem(rvs[i]);
						if (rv instanceof Button) {
							rvd.add((Button)rv);
						}
					}
					
					btn.setRepels(rvd.toArray(new Button[rvd.size()]));
				}
				
			}
		}
		
		
		if (item instanceof CheckBox)
		{
			CheckBox chk = (CheckBox)item;
			
			Object repels = chk.getProperty("repels");
			
			if (repels!=null)
			{
				String[] rvs = CUtil.splitString(repels.toString(), ",");
				
				Vector<CheckBox> rvd = new Vector<CheckBox>(rvs.length);
				
				for (int i=0; i<rvs.length; i++) {
					Item rv = form.getAttributeItem(rvs[i]);
					if (rv instanceof CheckBox) {
						rvd.add((CheckBox)rv);
					}
				}
				
				chk.setRepels(rvd.toArray(new CheckBox[rvd.size()]));
			}
		}
	}
	
	static private void printNodelist(NodeList nodelist, int deep)
	{
		String hd = "";
		for (int d=0; d<deep; ++d) hd += "\t";
		
		for (int i=0; i<nodelist.getLength(); ++i)
		{
			Node node = nodelist.item(i);
			
			if (node!=null)
			{
				NodeList sublist = node.getChildNodes();
				
				if (sublist!=null && sublist.getLength()>0) {
					printNodelist((NodeList)node, deep + 1);
				} 
				else {
					System.out.println(hd + node.toString().trim());
				}
			}
		}
	}
		
	
	public static void main(String[] args) 
	{
		try {
			
			String file = "/com/cell/gui/edit/Foundation.xml";

			InputStream is = file.getClass().getResourceAsStream(file);
			
			Form form = new Form(10, 10);
			
			FormListener formListener = new FormListener(){
				public int formAction(Form form, Control control, int action) {
					System.out.println("formAction " + action);
					return 0;
				}
			};
			
			FormConfigListener configlistener = new FormConfigListener(){
				public void formAttributeSet(Form form, String key, String value) {}
				public void formPropertySet(Form form, String key, String value) {}
				public void itemAttributeSet(Item item, String key, String value) {}
				public void itemPropertySet(Item item, String key, String value) {}
			};
			
			initForm(form, is, formListener, configlistener);
			
			
			
			
			
			//printNodelist(doc.getChildNodes(), 0);
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
