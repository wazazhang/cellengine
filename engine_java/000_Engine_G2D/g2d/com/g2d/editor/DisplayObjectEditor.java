package com.g2d.editor;

import java.awt.Component;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.util.AbstractFrame;

public class DisplayObjectEditor<V extends DisplayObject> extends AbstractFrame
{
	private static final long serialVersionUID = Version.VersionG2D;

	final public V 				object;

	
	final public JTabbedPane 	table					= new JTabbedPane();
	final public JMenuBar		menu					= new JMenuBar();
	
	protected JPanel 			info					= new JPanel();
	protected JMenu 			menu_window 			= new JMenu("Menu");
	protected JCheckBoxMenuItem	menu_item_auto_stick	= new JCheckBoxMenuItem("Stick Canvas");
	
	public DisplayObjectEditor(V o, Component ... append_page)
	{
		this.setSize(640,480);
		this.setTitle(o.getClass().getName() + " : " + o.toString());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.object = o;
	
		{
			menu_item_auto_stick.setState(true);
			menu_window.add(menu_item_auto_stick);
//			menu.add(menu_window);
		}
		
		this.setJMenuBar(menu);
		
		// property panel
//		{
//			table.addTab("Property", new ObjectPropertyPanel(o));
//		}
		
		// info panel
//		{
//			info.setLayout(null);
//			
//			JInternalFrame frame = new JInternalFrame(){
//				private static final long serialVersionUID = 1L;
//				public void paint(Graphics g) {
//					object.render((Graphics2D)g);
//				}
//			};
//			frame.setSize(object.getWidth(), object.getHeight());
//			frame.setVisible(true);
//			info.add(frame);
			
//			table.addTab("Info", info);
//		}

		// append panel
		for (Component append : append_page)
		{
			table.addTab(append.toString(), append);
		}
		
		
		this.add(table);
	}
	
	public boolean isAutoStick() {
		return menu_item_auto_stick.getState();
	}
	
}
