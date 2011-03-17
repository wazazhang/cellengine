package com.cell.gfx.gui;


public interface FormManagerListener {
	public void formAdded(Form form, FormManager sender);
	public void formRemoved(Form form, FormManager sender);
	public void formShown(Form form, FormManager sender);
	public void formHidden(Form form, FormManager sender);
	
}
