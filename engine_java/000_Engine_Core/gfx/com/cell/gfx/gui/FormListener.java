package com.cell.gfx.gui;


public interface FormListener {

	
	
	final static public int ITEM_ADDED				= 1;
	final static public int ITEM_REMOVED			= 2;
	
	final static public int ITEM_ON_FOCUS 			= 101;
	final static public int ITEM_GET_FOCUS 			= 102;
	final static public int ITEM_LOSS_FOCUS 		= 103;
	
	final static public int GROUP_ADDED				= 201;
	final static public int GROUP_REMOVED			= 202;
	
	public int formAction(Form form, Control control, int action);

	
}
