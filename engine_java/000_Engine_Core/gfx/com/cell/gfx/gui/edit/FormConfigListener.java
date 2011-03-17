package com.cell.gfx.gui.edit;

import com.cell.gfx.gui.Form;
import com.cell.gfx.gui.Item;

public interface FormConfigListener
{
	public void formPropertySet(Form form, String key, String value);
	
	public void formAttributeSet(Form form, String key, String value);
	
	public void itemPropertySet(Item item, String key, String value);
	
	public void itemAttributeSet(Item item, String key, String value);
	
	
}
