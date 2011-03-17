package com.cell.gfx.gui;

import java.util.Hashtable;
import java.util.Vector;

import com.cell.gfx.IGraphics;

public class Menu extends Form 
{

	public static int MenuPopFreezeTime = 8;
	
//	--------------------------------------------------------------------------------------------------------------------------------------
	
	public class SelectItem extends Button
	{
		private int HoldTimer = 0;
		
		private Menu NextMenu = null;
		
		private SelectItem(int w, int h, Command cmd)
		{
			super(w, h, cmd);
		}
		
		public boolean update(Form form) 
		{
			boolean ret = super.update(form);
			
			if(IsEnabled){
				if(--HoldTimer < 0 && IsFocused && (NextMenu==null || !NextMenu.IsVisible) && Click.size()>0)
				{
					if(form.includeItemPoint(this, Form.getPointerX(), Form.getPointerY())){
						popSub(this);
					}
				}
			}
			
			return ret;
		}

		
		
		public void render(IGraphics g, int x, int y) {
			
			if(!IsEnabled){
				ColorFocusedText = TextColorDisabled;
				ColorUnFocusedText = TextColorDisabled;
			}
			
			super.render(g, x, y);
			
			
			ColorFocusedText = TextColorFocused;
			ColorUnFocusedText = TextColorUnFocused;
			
			if(	this.Click!=null && this.Click.SubCommands!=null && 
				this.Click.SubCommands.size()>0)
			{
				g.setColor(TextColor);
				g.fillTriangle(
						x+W-H/2+2,
						y+H/2,
						x+W-H/2-2,
						y+H/2-4,
						x+W-H/2-2,
						y+H/2+4
						);
			}
			
			
		}

		
		
		public void clicked(Form sender, int key, int x, int y) {
			if(IsEnabled){
				super.clicked(sender, key, x, y);
			}
		}

		protected void getFocuse(Form sender) {
			super.getFocuse(sender);
			HoldTimer = MenuPopFreezeTime;
		}

		protected void lossFocuse(Form sender) {
			super.lossFocuse(sender);
			
			if(NextMenu!=null){
				NextMenu.close(false);
				NextMenu = null;
			}
			
			
		}
		
		
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------
	public UIRect CommandRect = new UIRect(false);
	public int TextColorFocused 	= 0xffffffff;
	public int TextColorUnFocused 	= 0xffc0c0c0;
	public int TextColorDisabled 	= 0xff808080;
	
	int MenuWidth = 128;
	int MenuHeight = 20;
	
	ItemListener ItemListener;
	
	Hashtable<Command, Item> Commands = new Hashtable<Command, Item>();
	
	Vector<Item> List = new Vector<Item>();
	
	Menu SuperMenu;
	Item SuperItem;
	
	private boolean firstPointerDown = false;
	
	public Menu(FormManager forms, ItemListener listener, int width, int itemHeight) 
	{
		super(width, SCREEN_HEIGHT);
		MenuWidth  = width;
		MenuHeight = itemHeight;
		
		OwnerManager = forms;
		
		IsShowScrollBar = false;
		
		this.TransitionMax = 4;
		
		ItemListener = listener;
	}
	
	public Menu(FormManager forms, ItemListener listener)
	{
		this(forms, listener, 128 , 20);
	}

	public void notifyLogic()
	{
		
		super.notifyLogic();	
		
		
//		if(!isFormTransition())
		{
			boolean catchPointer = includePoint(getPointerX(), getPointerY());
			
			if(KeyEnable)
			{
				if(catchPointer)
				{
					for (int i = size() - 1; i >= 0; i--)
					{
						Item item = getItem(i);
						
						item.released(this);
						
						if(item!=null && item.CanFocus && !item.IsFocused)
						{
							if(includeItemPoint(item, getPointerX(), getPointerY()))
							{
								Item prew = getFocus();
								
								if(prew != item)
								{
									focus(item);
								}
								break;
							}
						}
					}
					
					KeyEnable = false;
				}
				else
				{
					if (isPointerDown())
					{
//						if (SuperItem == null || !SuperItem.IsFocused) 
						{
							this.close(false);
						}
					}
					
					if (isPointerUp() && firstPointerDown)
					{
//						if (SuperItem == null || !SuperItem.IsFocused) 
						{
							this.close(false);
						}
					}
					
					if(SuperMenu!=null && !SuperMenu.IsVisible){
						this.close(false);
					}
					
				}
				
				if(isPointerUp())firstPointerDown = true;
				
				if (isKeyDown(KeyClose))
				{
					Menu next = SuperMenu;
					while(next != null){
						next.close(false);
						next = next.SuperMenu;
					}
					this.close(false);
				}
				
			}
			
		
			
		}
		
		

		
	}
	
	
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public void setCommandListener(ItemListener listener){
		ItemListener = listener;
	}
	
	public void addCommand(Command command)
	{
		Item item = null;
		if(command.getLable().equals("-")){
			LabelBar blank = new LabelBar("",MenuWidth,4);
			blank.UserRect.BackColor = 0;
			blank.UserRect.BorderColor = 0;
			blank.TextColor = 0;
			blank.CanFocus = false;
			item = blank;
		}else{
			SelectItem btn 			= new SelectItem(MenuWidth, MenuHeight, command);
			btn.UserRect 			= CommandRect;
			btn.ColorFocusedText 	= TextColorFocused;
			btn.ColorUnFocusedText 	= TextColorUnFocused;
			btn.IsEnabled 			= (command.Type != Command.DISABLED);
			btn.setCommandListener(ItemListener);
			
			item = btn;
		}
		Commands.put(command, item);
		List.addElement(item);
	}
	
	public Item getItem(Command command)
	{
		return (Item)Commands.get(command);
	}
	
	public void addCommand(String command)
	{
		addCommand(new Command(command));
	}
	
	public void addCommands(Vector commands)
	{
		for(int i=0;i<commands.size();i++){
			Command cmd = (Command)commands.elementAt(i);
			this.addCommand(cmd);
		}
	}
	
	public void addCommands(Command[] commands)
	{
		for(int i=0;i<commands.length;i++){
			Command cmd = commands[i];
			this.addCommand(cmd);
		}
	}
	
	public void removeCommand(Command command)
	{
		Item item = (Item)Commands.get(command);
		if(item != null){
			Commands.remove(command);
			List.removeElement(item);
		}
	}
	
	public void removeAllCommands(){
		Commands.clear();
		List.removeAllElements();
	}
	
	public boolean containsCommand(Command command)
	{
		return Commands.containsKey(command);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	

	protected void clickItem(Item item, int key, int x, int y) {
		if(Commands.contains(item) && item.IsEnabled)
		{
			Command cmd = item.Click;
			if(cmd.size()>0){
//				popSub(item);
			}else{
				
				Menu next = SuperMenu;
				while(next != null){
					next.close(false);
					next = next.SuperMenu;
				}
				this.close(false);
				
				super.clickItem(item, key, x, y);

			}
		}
		
	}

	protected void popSub(SelectItem item)
	{
		int sx = toScreenPosX(item.X);
		int sy = toScreenPosY(item.Y);
		
		Menu sub = new Menu(OwnerManager, ItemListener);
		
		sub.SuperMenu 	= this;
		sub.SuperItem 	= item;
		sub.UserRect 	= this.UserRect;
		sub.CommandRect = this.CommandRect;
		sub.TextColorFocused	= this.TextColorFocused;
		sub.TextColorUnFocused	= this.TextColorUnFocused;
		sub.TextColorDisabled	= this.TextColorDisabled;
		sub.addCommands(item.getClick().SubCommands);
		
		sub.show(sx, sy, sx, sy, item.W, item.H);
		
		item.NextMenu = sub;
	}
	

	public void show(int x, int y, int startX, int startY, int startW, int startH)
	{
		
		startY -= (UserRect.BorderSize + ViewBorderSize);
		startH += (UserRect.BorderSize + ViewBorderSize)*2;
		
		super.show(startX + startW, startY, startX, startY, startW, startH);

		if( WindowX + WindowW > SCREEN_WIDTH ){
			WindowX = startX - WindowW;
			PopedRect.X1 = (short)startX;
			PopedRect.X2 = (short)startX;
		}else{
			PopedRect.X1 = (short)(startX + startW);
			PopedRect.X2 = (short)(startX + startW);
		}
		
		if( WindowY + WindowH > SCREEN_HEIGHT )
		{
			WindowY = startY - WindowH + startH;
			PopedRect.Y1 = (short)(startY+startH );
			PopedRect.Y2 = (short)(startY+startH );
		}
		else
		{
			PopedRect.Y1 = (short)(startY );
			PopedRect.Y2 = (short)(startY );
		}
		
		WindowX = Math.max(0, WindowX);
		WindowY = Math.max(0, WindowY);
//		WindowW = Math.min(WindowW, SCREEN_WIDTH);
//		WindowH = Math.min(WindowH, SCREEN_HEIGHT);
		
	}

	public void show(int x, int y, int startX, int startY) 
	{
		super.show(startX, startY, startX, startY);
		
		PopedRect.X1 = (short)startX;
		PopedRect.Y1 = (short)startY;
		PopedRect.X2 = (short)startX;
		PopedRect.Y2 = (short)startY;	
		
		if( WindowX + WindowW > SCREEN_WIDTH ){
			WindowX = WindowX - WindowW;
		}
		if( WindowY + WindowH > SCREEN_HEIGHT ){
			WindowY = WindowY - WindowH;
		}
		
		WindowX = Math.max(0, WindowX);
		WindowY = Math.max(0, WindowY);
//		WindowW = Math.min(WindowW, SCREEN_WIDTH);
//		WindowH = Math.min(WindowH, SCREEN_HEIGHT);
		
	}

	public void show(int x, int y) {
		super.show(x, y);
		
		PopedRect.X1 = (short)x;
		PopedRect.Y1 = (short)y;
		PopedRect.X2 = (short)x;
		PopedRect.Y2 = (short)y;	
		
		if( WindowX + WindowW > SCREEN_WIDTH ){
			WindowX = WindowX - WindowW;
		}
		if( WindowY + WindowH > SCREEN_HEIGHT ){
			WindowY = WindowY - WindowH;
		}

		WindowX = Math.max(0, WindowX);
		WindowY = Math.max(0, WindowY);
//		WindowW = Math.min(WindowW, SCREEN_WIDTH);
//		WindowH = Math.min(WindowH, SCREEN_HEIGHT);
	}
	
/*
	public void open()
	{
		if(List.size()>0)
		{
			OwnerManager.pushForm(this);
			
			this.deleteAllItem();
			
			WindowW = Math.min(MenuWidth, SCREEN_HCENTER);
			WindowH = SCREEN_HEIGHT;
			
			BoardW = ViewBorderSize;
			BoardH = ViewBorderSize;
			
			for(int i=0;i<List.size();i++){
				Item item = (Item)List.elementAt(i);
				appendItem(item, true, true);
//				BoardH = item.Y + item.getHeight();
			}
			
			this.optmizeBoard();
			this.optmizeSize();
			
			IsShowItemFocus = true;
			
//			if(WindowH > SCREEN_HEIGHT)
//			{
//				IsShowScrollBar = true;
//				WindowW += ScrollBarV.BarSize;
//			}
			
			WindowW = Math.min(WindowW, SCREEN_WIDTH);
			WindowH = Math.min(WindowH, SCREEN_HEIGHT);
			
			super.open();
		}
		
		firstPointerDown = false;
	}
*/
	public void open()
	{
		if(List.size()>0)
		{
			OwnerManager.pushForm(this);
			this.deleteAllItem();
			
			WindowW = MenuWidth;
			WindowH = SCREEN_HEIGHT;
			
			int sx = ViewBorderSize;
			int sy = ViewBorderSize;
			
			for (int i = 0; i < List.size(); i++)
			{
				Item item = (Item)List.elementAt(i);
				item.resize(getViewWidth()-ViewBorderSize*2, item.getHeight());
				if (sy + item.getHeight() > getViewHeight()) {
					sy = ViewBorderSize;
					sx += item.getWidth();
				}
				appendItem(item, sx, sy);
				sy += item.getHeight();
			}

			WindowW = SCREEN_WIDTH;
			this.optmizeBoard();
			this.optmizeSize();
			WindowW = Math.min(WindowW, SCREEN_WIDTH);
			WindowH = Math.min(WindowH, SCREEN_HEIGHT);
			
			IsShowItemFocus = true;
			
			super.open();
		}
		
		firstPointerDown = false;
	}
	
	
	public void close(boolean reopen) {
		TransitionMax = 0;
		super.close(false);
		IsVisible = false;
		IsClosing = false;
		IsOpening = false;
		
	}

}
