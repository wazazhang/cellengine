package com.g2d.studio.ui.edit;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.cell.CIO;
import com.cell.reflect.Parser;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.annotation.Property;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.display.DisplayObject;
import com.g2d.display.DragResizeObject;
import com.g2d.display.InteractiveObject;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.display.ui.layout.UILayoutManager;
import com.g2d.editor.property.ObjectPropertyListener;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.studio.ui.edit.gui.SavedComponent;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UERoot;


public class UITreeNode extends G2DTreeNode<UITreeNode> 
implements ObjectPropertyListener
{
	private static UITreeNode copyed_node;
	
	final public ObjectPropertyPanel opp;

	private UITemplate template;
	private UIComponent display;
	private BufferedImage icon;
	private UIEdit edit;
	
	public UITreeNode(UIEdit edit, UITemplate template, String name) 
	{
		DisplayAdapter adapter = new DisplayAdapter();
		this.edit = edit;
		this.template = template;
		this.display = template.createDisplay(edit);
		this.getDisplay().name = name;
		this.getDisplay().debugDraw = adapter;
		this.getDisplay().enable = true;
		this.getDisplay().enable_drag = true;
		this.getDisplay().enable_drag_resize = true;
		this.getDisplay().enable_key_input = true;
		this.getDisplay().enable_focus = true;
		this.getDisplay().edit_mode = adapter;
		this.getDisplay().addEventListener(adapter);
		this.getDisplay().setSorter(adapter);
		this.getDisplay().setAttribute(UITreeNode.class.getSimpleName(), this);
		
		this.opp = new ObjectPropertyPanel(getDisplay(), 100, 200, false,
				UIPropertyPanel.getAdapters(edit)); 
		this.opp.addObjectPropertyListener(this);
		
		this.setAllowsChildren(getDisplay() instanceof com.g2d.display.ui.Container);
	}
	
	protected UIComponent createDisplay(Class<?> type) {
		try {
			UIComponent uie = (UIComponent) type.newInstance();
			return uie;
		} catch (Exception e) {
			e.printStackTrace();
			return new UECanvas();
		}
	}
	
	@Override
	public void onFieldChanged(Object object, Field field) {
		getIcon(true);
		edit.getTree().repaint();
	}
	
	@Override
	protected ImageIcon createIcon() {
		UILayout uc = getDisplay().getCustomLayout();
		if (uc == null) {
			uc = getDisplay().getLayout();
		}
		if (uc != null) {
			icon = Engine.getEngine().createImage(getDisplay().getWidth(), getDisplay().getHeight());
			Graphics2D g = icon.createGraphics();
			uc.render(g, 0, 0, getDisplay().getWidth(), getDisplay().getHeight());
			g.dispose();
			icon = Tools.combianImage(40, 30, icon);
			return new ImageIcon(AwtEngine.unwrap(icon));
		}
		return null;
	}
	
	@Override
	public String getName() {
		return this.getDisplay().name + "";
	}
	public void setName(String name) {
		this.getDisplay().name = name;
	}
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		edit.onSelectTreeNode(this);
	}
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new UIMenu(tree, this).show(tree, e.getX(), e.getY());
	}
	public boolean isSelected() {
		return edit.getTree().getSelectedNode() == this;
	}

	
	class DisplayAdapter implements 
	com.g2d.display.event.MouseListener, 
	com.g2d.display.event.MouseMoveListener,
	com.g2d.display.event.KeyListener,
	com.g2d.display.event.MouseDragResizeListener,
	com.g2d.display.ui.UIComponent.DebugModeDraw,
	com.g2d.display.InteractiveObject.EditModeSelect,
	Comparator<DisplayObject>
	{		
		boolean check = false;
		
		@Override
		public boolean isSelected(InteractiveObject obj) {
			if (!edit.isToolAutoSelect()) {
				return edit.getSelectedUINode() == UITreeNode.this;
			} else {
				return true;
			}
		}
		
		@Override
		public boolean isDragEnable(InteractiveObject obj) {
			return edit.isToolDragMove();
		}
		
		@Override
		public int compare(DisplayObject o1, DisplayObject o2) {
			if (o1 != null && o2!=null) {
				UITreeNode t1 = ((UIComponent)o1).getAttribute(UITreeNode.class.getSimpleName());
				UITreeNode t2 = ((UIComponent)o2).getAttribute(UITreeNode.class.getSimpleName());
				TreeNode p1 = t1.getParent();
				TreeNode p2 = t2.getParent();
				if (p1 == p2 && p1!=null) {
					return p1.getIndex(t1) - p2.getIndex(t2);
				}
			}
			return 0;
		}
		
		@Override
		public void mouseClick(com.g2d.display.event.MouseEvent e) {
			
		}
		@Override
		public void mouseDown(com.g2d.display.event.MouseEvent e) {
			edit.onSelectTreeNode(UITreeNode.this);
			check = true;
		}
		@Override
		public void mouseUp(com.g2d.display.event.MouseEvent e) {
			opp.refresh();
		}
		
		@Override
		public void mouseDragged(MouseMoveEvent e)
		{
			if (edit.isToolGridEnable()) 
			{
				edit.getLayoutManager().gridPos(getDisplay());
			}
		}
		
		@Override
		public void onDragResizeEnd(InteractiveObject object) {}

		@Override
		public void onDragResizeRunning(InteractiveObject object, DragResizeObject dr) 
		{
			if (edit.isToolGridEnable()) 
			{
				edit.getLayoutManager().gridSize(dr.start_drag_bounds);
			}
		}

		@Override
		public void onDragResizeStart(InteractiveObject object, int drag_type) {}
		
		
		@Override
		public void keyDown(com.g2d.display.event.KeyEvent e) {
		
		}
		@Override
		public void keyTyped(com.g2d.display.event.KeyEvent e) {

		}
		@Override
		public void keyUp(com.g2d.display.event.KeyEvent e) {}
		
		
		
		@Override
		public void render(com.g2d.Graphics2D g, UIComponent ui) 
		{
			if (edit.getTree().getSelectedNode() == UITreeNode.this) 
			{
				int s1 = 3;
				int s2 = 6;
				int s4 = 12;
				int w = ui.getWidth();
				int h = ui.getHeight();
				g.pushClip();
				g.setClip(-s2, -s2, w+s4, h+s4);
				
				if (edit.isToolDragMove())
				{
					float alpha = 0.5f + (float)Math.sin(ui.timer / 5.0f)/2;
					g.setColor(new Color(alpha, alpha, alpha, 1));
					g.drawRect(0, 0, w-1, h-1);
					
					g.fillRect( -s1,  -s1, s2, s2);
					g.fillRect(w-s1,  -s1, s2, s2);
					g.fillRect( -s1, h-s1, s2, s2);
					g.fillRect(w-s1, h-s1, s2, s2);
					
					g.fillRect(w/2-s1,  -s1, s2, s2);
					g.fillRect(w/2-s1, h-s1, s2, s2);
					g.fillRect( -s1, h/2-s1, s2, s2);
					g.fillRect(w-s1, h/2-s1, s2, s2);
				}
				else 
				{
					g.setColor(new Color(0.5f, 1f, 1f, 0.5f));
					g.drawRect(0, 0, w-1, h-1);
				}
				g.popClip();
			}
		}
	}

	
//	---------------------------------------------------------------------------------------------------------------

	class UIMenu extends NodeMenu<UITreeNode>
	{
		protected JMenuItem add 	= new JMenuItem("添加");
		protected JMenuItem copy 	= new JMenuItem("复制");
		protected JMenuItem cut 	= new JMenuItem("剪贴");
		protected JMenuItem paste 	= new JMenuItem("粘贴");
		
		public UIMenu(JTree tree, UITreeNode node) {
			super(node);
			super.remove(open);
			super.remove(rename);

			if (getDisplay() instanceof com.g2d.display.ui.Container) {
				super.insert(paste, 1);
				paste.addActionListener(this);
				
				paste.setEnabled(copyed_node != null);
			}

			if (UITreeNode.this.getParent() != null) 
			{
				super.insert(cut, 1);
				cut.addActionListener(this);
				
				super.insert(copy, 1);
				copy.addActionListener(this);
			}
			
			if (getDisplay() instanceof com.g2d.display.ui.Container) {
				super.insert(add, 1);
				add.addActionListener(this);
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == add) 
			{
				if (edit.getSelectedTemplate() != null) {
					UITemplate type = edit.getSelectedTemplate();
					String name = JOptionPane.showInputDialog(
							AbstractDialog.getTopWindow(getInvoker()), "输入名字！",
							"");
					if (name != null && name.length() > 0) {
						UITreeNode tn = createChild(type, name);
						if (tn == null) {
							JOptionPane.showMessageDialog(this,
									getDisplay().getClass().getSimpleName()+"不能添加子节点！");
						}
					}
				}
			}
			else if (e.getSource() == copy)
			{
				copyed_node = UITreeNode.this.clone();
			}
			else if (e.getSource() == cut)
			{
				copyed_node = UITreeNode.this.clone();
				removeFromParent2();
			}
			else if (e.getSource() == paste)
			{
				if (copyed_node != null) {
					addChild(copyed_node.clone());
				}
			}
			else if (e.getSource() == delete) 
			{
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				this.node.getDisplay().removeFromParent();
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}
	
	public void removeFromParent2() {
		((UITreeNode)getParent()).removeChild(this);
	}
	
	public UITreeNode removeChild(UITreeNode tr) {
		try {
			super.remove(tr);
			this.getDisplay().removeChild(tr.getDisplay());
			return tr;
		} catch (Exception e) {}
		return null;
	}
	
	public void clearChilds() {
		while (getChildCount()>0) {
			removeChild((UITreeNode)getFirstChild());
		}
	}
	public UITreeNode addChild(UITreeNode node) {
		if (getDisplay() instanceof com.g2d.display.ui.Container) {
			com.g2d.display.ui.Container pc = (com.g2d.display.ui.Container) getDisplay();
			if (pc.addComponent(node.getDisplay())) {
				this.add(node);
				edit.getTree().reload(this);
				return node;
			}
		}
		return null;
	}
	
	public UITreeNode createChild(UITemplate template, String name) 
	{
		try {
			if (getDisplay() instanceof com.g2d.display.ui.Container) {
				UITreeNode node = new UITreeNode(edit, template, name);
				com.g2d.display.ui.Container pc = (com.g2d.display.ui.Container) getDisplay();
				if (pc.addComponent(node.getDisplay())) {
					this.add(node);
					edit.getTree().reload(this);
					return node;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public UITreeNode clone()
	{
		UITreeNode ret = new UITreeNode(edit, template, "");
		UIComponent ui = ret.getDisplay();
		Field[] fields = ui.getClass().getFields();
		for (Field f : fields) {
			try {
				if (f.getAnnotation(Property.class) != null) {
					Object attr = f.get(this.getDisplay());
					f.set(ui, CIO.cloneObject(attr));
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		((SavedComponent)ui).readComplete(edit);
		
		for (int i = 0; i < getChildCount(); i++) {
			UITreeNode cn = (UITreeNode) getChildAt(i);
			cn = cn.clone();
			ret.addChild(cn);
		}
		return ret;
	}
	
	public void fromXMLStream(UIEdit edit, InputStream is) throws Exception
	{
		try {
			clearChilds();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element e = doc.getDocumentElement();
			if (e != null) {
				readInternal(edit, doc, e, this);
			} else {
				throw new Exception("格式错误!");
			}
		} catch (SAXParseException err) {
			throw new Exception("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId(), err);
		} catch (SAXException e) {
			throw e;
		}
	}
	
	public void toXMLStream(UIEdit edit, OutputStream os) throws Exception
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element e = doc.createElement(getDisplay().getClass().getCanonicalName());
			doc.appendChild(e);
			writeInternal(edit, doc, e, this);
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

	private static void readInternal(UIEdit edit, Document doc, Element e, UITreeNode ui) throws Exception
	{
		readFields(edit, e, ui.getDisplay());
		if (ui.getDisplay() instanceof SavedComponent) {
			((SavedComponent)ui.getDisplay()).onRead(edit, e, doc);
			((SavedComponent)ui.getDisplay()).readComplete(edit);
		}
		
		NodeList list = e.getChildNodes();
		for (int i=0; i<list.getLength(); i++)
		{
			if (list.item(i) instanceof Element) 
			{
				Element ev = (Element)list.item(i);
				
				if (ev.getNodeName().equals("layout")) 
				{
					UILayout layout = readLayout(edit, ev);
					ui.getDisplay().custom_layout = layout;
				}
				else if (ev.getNodeName().equals("childs"))
				{
					NodeList childs = ev.getChildNodes();
					for (int c=0; c<childs.getLength(); c++) {
						if (childs.item(c) instanceof Element) {
							Element child = (Element)childs.item(c);
							Class<?> type = Class.forName(child.getNodeName());
							UITemplate template = edit.getTemplate(type);
							UITreeNode tn = ui.createChild(template, "");
							readInternal(edit, doc, child, tn);
						}
					}
				}
			}
		}
		
		ui.getIcon(true);
	}

	private static void writeInternal(UIEdit edit, Document doc, Element e, UITreeNode ui) throws Exception
	{
		writeFields(edit, e, ui.getDisplay());
		if (ui.getDisplay() instanceof SavedComponent) {
			((SavedComponent)ui.getDisplay()).writeBefore(edit);
			((SavedComponent)ui.getDisplay()).onWrite(edit, e, doc);
		}
		
		UILayout layout = ui.getDisplay().getCustomLayout();
		if (layout != null) {
			Element rect = doc.createElement("layout");
			writeLayout(edit, layout, rect);
			e.appendChild(rect);
		}
		if (ui.getChildCount() > 0)
		{
			Element childs = doc.createElement("childs");
			for (int i=0; i<ui.getChildCount(); i++) {
				UITreeNode cn = (UITreeNode)ui.getChildAt(i);
				Element child = doc.createElement(
						cn.getDisplay().getClass().getCanonicalName());
				writeInternal(edit, doc, child, cn);
				childs.appendChild(child);
			}
			e.appendChild(childs);
			
		}
		
	}

	private static void readFields(UIEdit edit, Element e, UIComponent ui) 
	{
		ui.name 				= e.getAttribute("name");
		ui.clip_local_bounds 	= Boolean.parseBoolean(e.getAttribute("clipbounds"));
		ui.local_bounds.width	= Integer.parseInt(e.getAttribute("width"));
		ui.local_bounds.height	= Integer.parseInt(e.getAttribute("height"));

		Field[] fields = ui.getClass().getFields();
		for (Field f : fields) {
			try {
				if (f.getName().equals("custom_layout")) {
					continue;
				}
				if (f.getName().equals("custom_layout_down")) {
					continue;
				}
				if (f.getAnnotation(Property.class) != null) {
					if (e.hasAttribute(f.getName())) {
						String attrvalue = e.getAttribute(f.getName());
						Object attr = Parser.stringToObject(
								attrvalue,
								f.getType());
						if (attr != null) {
							f.set(ui, attr);
						}
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	private static void writeFields(UIEdit edit, Element e, UIComponent ui) 
	{
		e.setAttribute("name", ui.name);
		e.setAttribute("clipbounds", ui.clip_local_bounds+"");
		e.setAttribute("width", ui.getWidth()+"");
		e.setAttribute("height", ui.getHeight()+"");
		
		Field[] fields = ui.getClass().getFields();
		for (Field f : fields) {
			try {
				if (f.getName().equals("custom_layout")) {
					continue;
				}
				if (f.getName().equals("custom_layout_down")) {
					continue;
				}
				if (f.getAnnotation(Property.class) != null) {
					Object attr = f.get(ui);
					String satr = Parser.objectToString(attr, f.getType());
					e.setAttribute(f.getName(), satr);
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	public static UILayout readLayout(UIEdit edit, Element rect)
	{
		UILayout layout;
		ImageStyle style = Enum.valueOf(
				ImageStyle.class,
				rect.getAttribute("style"));
		if (rect.hasAttribute("img")) {
			layout = edit.getLayoutManager().createLayout(
					rect.getAttribute("img"), 
					style,
					Integer.parseInt(rect.getAttribute("clip")));
		} else {
			layout = new UILayout(style);
		}
		layout.setBackColor(new Color(rect.getAttribute("bgc")));
		layout.setBorderColor(new Color(rect.getAttribute("bdc")));
		return layout;
	}

	public static void writeLayout(UIEdit edit, UILayout layout, Element rect) {
		rect.setAttribute("style", layout.getStyle().toString());
		rect.setAttribute("bgc", layout.getBackColor().toHexString());
		rect.setAttribute("bdc", layout.getBorderColor().toHexString());
		if (layout instanceof ImageUILayout) {
			ImageUILayout imgrect = (ImageUILayout) layout;
			rect.setAttribute("img", imgrect.srcImageName().getName() + "");
			rect.setAttribute("clip", imgrect.clip_border + "");
			edit.getLayoutManager().putLayout(imgrect);
		}
	}

	public UIComponent getDisplay() {
		return display;
	}
}
