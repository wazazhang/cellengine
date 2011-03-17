package com.g2d.studio.old.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.cell.rpg.entity.Unit;
import com.g2d.editor.property.ObjectPropertyPanel;

public class RPGUnitPanel  extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final Unit current_unit;
	
	final ObjectPropertyPanel unit_property;
	
	public RPGUnitPanel(Unit unit)
	{
		this.setLayout(new BorderLayout());
		
		current_unit = unit;
	
		{
			this.unit_property = new ObjectPropertyPanel(unit);
			this.add(unit_property, BorderLayout.CENTER);
		}
	}
	
	@Override
	public String toString(){
		return "RPG Unit";
	}
	
	
	
}
