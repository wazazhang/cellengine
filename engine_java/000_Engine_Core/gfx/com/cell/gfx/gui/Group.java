package com.cell.gfx.gui;

import com.cell.gfx.IGraphics;

/**
 * @author�1�7
 *
 */
abstract public class Group extends Control 
{
	public void notifyDestroy() {}

	public void notifyLogic() {}

	public void notifyRender(IGraphics g) {}



	protected Form OwnerForm;
//	protected ItemCommandListener Listener;
	
	
	
	protected void appended(Form form){}
	protected void removed(Form form){}
	
	
	abstract protected void update(Form form);
	
	/**
	 * @param command
	 * @param item
	 * @param group
	 * @return is processed this event
	 * 注：当返回true会打断一个窗口中所有itemAction
	 */
	abstract protected boolean itemAction(Command command , Item item, Group group);
	
	
	
//	public void setItemCommandListener(ItemCommandListener listener){
//		Listener = listener;
//	}
	
}
