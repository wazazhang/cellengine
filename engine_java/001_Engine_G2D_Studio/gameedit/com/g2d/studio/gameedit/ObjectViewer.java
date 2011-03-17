package com.g2d.studio.gameedit;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.g2d.editor.property.CellEditAdapter;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;

public abstract class ObjectViewer<T extends ObjectNode<?>> extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final protected T tobject;
	
	protected JTabbedPane 		table = new JTabbedPane();
	protected RPGObjectPanel	page_object_panel;
	protected AbilityPanel		page_abilities;
	
	public ObjectViewer(T object, CellEditAdapter<?> ... adapters) 
	{
		this.tobject = object;
		this.setLayout(new BorderLayout());
		{
//			page_properties = new JPanel();
//			table.addTab("属性", page_properties);
		} {
			page_object_panel = new RPGObjectPanel(object.getData(), adapters);
			table.addTab("RPG属性", page_object_panel);
		} {
			page_abilities = new AbilityPanel(object.getData(), adapters);
			table.addTab("能力", page_abilities);
		}
		
		appendPages(table);
		
		this.add(table);
		
	}
	
	final public JTabbedPane getPages() {
		return table;
	}
	
	final public T getRPGObject() {
		return tobject;
	}
	
	abstract protected void appendPages(JTabbedPane table) ;
	

//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	
}
