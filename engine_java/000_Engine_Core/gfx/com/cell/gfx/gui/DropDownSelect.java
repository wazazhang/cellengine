package com.cell.gfx.gui;

import java.util.Vector;

import com.cell.gfx.IGraphics;

public class DropDownSelect extends LabelBar implements ItemListener
{
//	public static UIRect CommonUserRect = new UIRect();
//	public static UIRect CommonMenuRect;
//	public static UIRect CommonMenuItemRect;
//	static
//	{
//		CommonUserRect.BackColor   = 0xffc0c0c0;
//		CommonUserRect.BorderColor = 0xff000000;
//		
//		CommonMenuRect 		= CommonUserRect;
//		CommonMenuItemRect 	= CommonUserRect;
//	}
	
	Menu DroupDown;
	
	public UIRect MenuRect;
	public UIRect MenuItemRect;
	public int MenuTextColorFocused 	= 0xffffffff;
	public int MenuTextColorUnFocused 	= 0xffc0c0c0;
	public int MenuTextColorDisabled 	= 0xff808080;
	
	
	Command Selected;
	
	public DropDownSelect() {
		this(10, 10);
	}

	public DropDownSelect(int w, int h)
	{
		super("", w, h);
		this.CanFocus = true;
		this.Click = new Command("Drop");
		this.Click.SubCommands = new Vector();
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonMenuRect!=null)
//			MenuRect = CommonMenuRect;
//		if(CommonMenuItemRect!=null)
//			MenuItemRect = CommonMenuItemRect;
		
	}
	
	public DropDownSelect(Command[] list, int w, int h)
	{
		super(list.length>0?list[0].Lable:"", w, h);
		this.CanFocus = true;
		this.Click = new Command("Drop");
		this.Click.add(list);

//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
//		if(CommonMenuRect!=null)
//			MenuRect = CommonMenuRect;
//		if(CommonMenuItemRect!=null)
//			MenuItemRect = CommonMenuItemRect;
		
		Selected = list.length>0?list[0]:null;
	}
	
	public void setClick(Command command) {
		System.err.println("DropDownSelect::setClick()");
	}
	
	

	public void clicked(Form sender, int key, int x, int y) 
	{
		super.clicked(sender, key, x, y);
		
		try{
			
			if(DroupDown==null || !DroupDown.isVisible())
			{
				DroupDown = new Menu(sender.OwnerManager, this, W, H);
				if(MenuRect!=null){
					DroupDown.UserRect 		= MenuRect;
				}
				if(MenuItemRect!=null){
					DroupDown.CommandRect 	= MenuItemRect;
				}
				
				DroupDown.TextColorFocused		= MenuTextColorFocused;
				DroupDown.TextColorDisabled		= MenuTextColorDisabled;
				DroupDown.TextColorUnFocused	= MenuTextColorUnFocused;
				
				DroupDown.removeAllCommands();
				DroupDown.addCommands(Click.SubCommands);
				
				int sx = sender.toScreenPosX(X);
				int sy = sender.toScreenPosY(Y) + H;
				DroupDown.show(sx, sy, sx, sy);
				
				DroupDown.WindowX = sx;
				DroupDown.WindowW = W;
				DroupDown.PopedRect.X1 = (short)(sx);
				DroupDown.PopedRect.X2 = (short)(sx + W);
			} 
			else
			{
				DroupDown=null;
			}
			
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			//System.err.println("DropDownSelect::clicked");
		}
	}


	
	public int itemCommandAction(Command command, Item item)
	{
		if (command==null) {
			Selected = command;
			this.setText("");
			this.Icon = null;
		}
		else if(Click.SubCommands.contains(command)){
			Selected = command;
			this.setText(command.Lable);
			this.Icon = command.Icon;
		}
		if(Listener != null){
			Listener.itemCommandAction(command, this);
		}
		
		return 0;
	}

	public boolean update(Form form) {
		
		boolean ret = super.update(form);
		
		if(DroupDown!=null)
		{
			if (isPointerUp() && !DroupDown.isVisible()) {
				DroupDown = null;
			}
			else if (isKeyUp(DroupDown.KeyClose)) {
				DroupDown = null;
			}
		}
	
		
		return ret;
	}
	
	public void render(IGraphics g, int x, int y) 
	{
		super.render(g, x, y);
		
		g.setColor(TextColor);
		
		int dx = (W + g.getStringWidth(getText())>>1);
		if (Icon!=null)
		{
			dx+=Icon.getWidth();
		}
		g.fillTriangle(
			x+dx+1,
			y+H/2-2,
			x+dx+10,
			y+H/2-2,
			x+dx+5,
			y+H/2+3
		);
//		g.fillTriangle(
//				x+W-H/2+4,
//				y+H/2-2,
//				x+W-H/2-4,
//				y+H/2-2,
//				x+W-H/2,
//				y+H/2+2
//				);
		
	}


	public int getIndex(){
		if(Selected!=null){
			return getClick().SubCommands.indexOf(Selected);
		}
		return -1;
	}
	
	public void setSelected(Command command){
		itemCommandAction(command, null);
	}
	
	public void setSelected(int index){
		itemCommandAction(this.getClick().SubCommands.elementAt(index), null);
	}
	
	public Command getSelected(){
		return Selected;
	}
	
	public void addCommand(Command cmd){
		this.getClick().add(cmd);
	}
	public void addCommands(Command[] cmds){
		this.getClick().add(cmds);
	}
	public void addCommands(Vector cmds){
		this.getClick().add(cmds);	
	}
	
	public boolean removeCommand(Command cmd){
		for (int i = 0; i<this.getClick().SubCommands.size(); i++){
			if (this.getClick().SubCommands.elementAt(i) == cmd){
				this.getClick().SubCommands.remove(i);
				return true;
			}
		}
		return false;
	}
}
