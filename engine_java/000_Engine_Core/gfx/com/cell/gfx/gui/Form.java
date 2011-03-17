package com.cell.gfx.gui;


import java.util.Vector;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.game.CCD;

/**
 * @author 张翼飞
 *
 */
public class Form extends Control 
{

	public static boolean IS_NONE_POINTER = false;

	
	
//	----------------------------------------------------------------------------------------------------------------------------------------------------
//	FormManager Container;
	
//	
	Item DragedItem = null;
	
	boolean StartDragItem = false;
	int DragedItemSX = -1;
	int DragedItemSY = -1;
	
	
//	
	public int WindowX;
	public int WindowY;
	protected int WindowW;
	protected int WindowH;
	
	FormListener Listener 	= null;
	
	protected FormManager OwnerManager;
	public FormManager getOwnerManager(){
		return OwnerManager;
	}

	public boolean isOnTop(){
		if(OwnerManager!=null){
			return OwnerManager.getTopForm() == this;
		}
		return false;
	}
	
	public boolean IsLayoutDialog 		= false;
	public boolean IsLayoutAutoHide		= false;
	public boolean IsHoldSelect 		= false;
	public boolean IsClipUserRect 		= true;
	public boolean IsProcessKey			= true;
	
	public int ViewBorderSize = 2;
	protected int BoardW = ViewBorderSize;
	protected int BoardH = ViewBorderSize;

	public int getBoardW(){
		return BoardW;
	}
	public int getBoardH(){
		return BoardH;
	}

	public LabelBar Title ;
	{
		Title = new LabelBar("", 1, getStringHeight()+4);
	}
	
	public int		FreezeKeyDelay		= 10;
	public int		FreezeKeyTime		= 0;
	public int		FreezeKeySpeed		= 4;
	
	public int 		KeySelectNextItem	= KEY_DOWN | KEY_TAB;
	public int 		KeySelectPrewItem	= KEY_UP;
	
	
	public int		ScrollSpeed			= 20;
	public int		KeyScrollUp			= KEY_UP;
	public int		KeyScrollDown		= KEY_DOWN;
	public int		KeyScrollLeft		= 0;
	public int		KeyScrollRight		= 0;
	
	
	public int		KeyClose			= KEY_B;
	
	public UIRect 	UserRect 	= new UIRect(false);
	{
		UserRect.BackColor	= 0xffffffff;
		UserRect.BackColor 	= 0xff404040;
		UserRect.BorderSize	= 1;
	}
	
	public boolean 		IsShowScrollBar = true;
	public ScrollBar	ScrollBarH;
	public ScrollBar	ScrollBarV;
	
	public int	TransitionMax	= 8;
	
	protected boolean 	IsVisible = false;
	protected boolean 	IsOpening = false;
	protected boolean 	IsClosing = false;
	protected int 		TransitionTime 	= 0;
	

	public boolean CanClickClose = true;
	public IImage CloseIcon;
	
	public IImage HelpIcon;
	
//	-------------------------------------------------------------------------------------------------------------------
	private int TopOutHintTimer = 0;
	
	
//	-------------------------------------------------------------------------------------------------------------------------

	boolean IsClosed = false;

	boolean ClickDown = false;
	int ClickDownX;
	int ClickDownY;
	
	boolean FormDragStart = false;
	int FormDragOffsetX = 0;
	int FormDragOffsetY = 0;
	
	protected int closew ;
	protected int closeh ;
	protected int closex ;
	protected int closey ;
	
	protected int helpw;
	protected int helph;
	protected int helpx;
	protected int helpy;
	
	int ViewX1 ;
	int ViewY1 ;
	int ViewX2 ;
	int ViewY2 ;
	int ViewW ;
	int ViewH ;
//	int bw ;
//	int bh ;
//	-------------------------------------------------------------------------------------------------------------------
	// let item click once pre frame
	private boolean m_IsOnceItemClicked = false;
	
	
//	-------------------------------------------------------------------------------------------------------------------

	public Form(/*FormManager manager, */String title, int x,int y,int w,int h) 
	{
//		OwnerManager = manager;
		
		WindowX = x;
		WindowY = y;
		WindowW = w;
		WindowH = h;
		
		setTitle(title);
		Title.TextColor = 0xffffffff;
		
		ScrollBarV = new ScrollBar(8, false);
		ScrollBarH = new ScrollBar(8, true);
		
		Title.IsTextCenter = true;
	}
	
	public Form(/*FormManager manager, */String title, int x,int y,int w,int h, Item[] items){
		this( /*manager, */title, x, y, w, h);
		for(int i=0;i<items.length;i++){
			appendItem(items[i]);
		}
	}
	
	public Form(/*FormManager manager, */int w,int h) {
		this(/*manager,*/"",0,0,w,h);
	}


	public void setListener(FormListener listener){
		Listener = listener;
	}
	
//	-------------------------------------------------------------------------------------------------------------------

	public void notifyDestroy() 
	{
		for(int i=0;i<Items.size();i++)
		{
			Item item = (Item)Items.elementAt(i);
			item.notifyDestroy();
		}
		
		for(int i=0;i<Groups.size();i++)
		{
			Groups.elementAt(i).notifyDestroy();
		}
		
		Title.notifyDestroy();
	}
	
	public void notifyLogic() 
	{
		m_IsOnceItemClicked = false;
		
		if(isFormTransition())
		{
			doTransition();
		}
		else if(IsVisible)
		{

//			set user clip region
			if(IsClipUserRect)
			{
				ViewX1 = WindowX + UserRect.BorderSize;
				ViewY1 = WindowY + UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0);
				ViewX2 = WindowX + WindowW - UserRect.BorderSize;
				ViewY2 = WindowY + WindowH - UserRect.BorderSize;
				ViewW = ViewX2 - ViewX1;
				ViewH = ViewY2 - ViewY1;
				
				int bw = 0;
				int bh = 0;
	
				if(ViewW<BoardW)bw = ScrollBarV.getBarSize();
				if(ViewH<BoardH)bh = ScrollBarH.getBarSize();
				if(bw!=0 && IsShowScrollBar)
					bh = ViewH - ScrollBarH.getBarSize() < BoardH ? bh = ScrollBarH.getBarSize() : 0;
				if(bh!=0 && IsShowScrollBar)
					bw = ViewW - ScrollBarV.getBarSize() < BoardW ? bw = ScrollBarV.getBarSize() : 0;
				
				ViewW -= bw;
				ViewH -= bh;
				ViewX2 = ViewX1 + ViewW;
				ViewY2 = ViewY1 + ViewH;
			}
			else
			{
				ViewX1 = WindowX ;
				ViewY1 = WindowY ;
				ViewX2 = WindowX + WindowW ;
				ViewY2 = WindowY + WindowH ;
				ViewW = ViewX2 - ViewX1;
				ViewH = ViewY2 - ViewY1;

			}
			
			
			
//			Form Operater
			if(OwnerManager!=null && OwnerManager.getTopForm() != this){
				
			}
			else
			{
				boolean isGetPoint = includePoint(getPointerX(),getPointerY());
				
				boolean isViewGetPoint = CMath.includeRectPoint(ViewX1, ViewY1, ViewX2, ViewY2, getPointerX(), getPointerY());
				
				boolean isFocusedItemGetPoint = false;
				
				if(isPointerDown() && !isGetPoint)
				{
					TopOutHintTimer = 10;
				}
				
				if(isKeyDown(KeyClose))
				{
					this.hide();
				}
				else if(isPointerDown() && (CanClickClose||HelpIcon!=null))
				{
					if(CMath.includeRectPoint2(
							closex, 
							closey, 
							closew, 
							closeh, 
							getPointerX(),
							getPointerY()))
					{
						this.hide();
					}
					if(CMath.includeRectPoint2(
							helpx, 
							helpy, 
							helpw, 
							helph, 
							getPointerX(),
							getPointerY()))
					{
						this.helpClicked();
					}
				}
				else if(!IS_NONE_POINTER && DefaultConfirmItem!=null && Form.isKeyDown(Form.KEY_C) )
				{
					focus(DefaultConfirmItem);
					clickItem(DefaultConfirmItem, Form.KEY_C, getPointerX(), getPointerY());
				}
				
				if(CMath.includeRectPoint2(
						helpx, 
						helpy, 
						helpw, 
						helph, 
						getPointerX(),
						getPointerY()))
				{
					this.helpGetted(WindowX-getPointerX(), WindowY-getPointerY());
				}
				
				
				
				if(IsHoldSelect)
				{
					IsItemAutoScroll = false;
				}
				
//				process focused item
				if(ItemFocus!=null)
				{
					try
					{
						if(Listener!=null){
							Listener.formAction(this, ItemFocus, FormListener.ITEM_ON_FOCUS);
						}
						if(IsClipUserRect){
							isFocusedItemGetPoint = isViewGetPoint && includeItemPoint(ItemFocus, getPointerX(), getPointerY());
						}else{
							isFocusedItemGetPoint = includeItemPoint(ItemFocus, getPointerX(), getPointerY());
						}
						
						if (Form.isKeyDown(KEY_C) && !Form.isKeyHold(KEY_STAR)){
							clickItem(ItemFocus, KEY_C, getPointerX(), getPointerY());
						}
						
						if(isPointerDown() && isFocusedItemGetPoint){
							pressItem(ItemFocus);
						}
						
						if(isPointerHold())
						{
	//						if(!isFocusedItemGetPoint){
	//							ItemFocus.IsPressed = false;
	//						}else{
	//							ItemFocus.IsPressed = true;
	//						}
						}
						
						if(isPointerUp()){
							if(isFocusedItemGetPoint){
								clickItem(ItemFocus, 0, getPointerX(), getPointerY());
							}
							releasItem(ItemFocus);
						}
						
						
						if (ItemFocus!=null) {
							ItemFocus.update(this);
						}
					
					}catch(Exception err){
						err.printStackTrace();
					}
					
				}//end process focused
				
//				process all items
				{
					if(isPointerDown())
					{
						StartDragItem = false;
						DragedItemSX = getPointerX();
						DragedItemSY = getPointerY();
						
						ClickDown = false;
						FormDragStart = false;
						
						boolean catched = false;
						
						if(isGetPoint)
						{
							ClickDown = true;
							ClickDownX = getPointerX();
							ClickDownY = getPointerY();
							
							if(includeHeadPoint(ClickDownX, ClickDownY))
							{
								FormDragOffsetX = getPointerX() - WindowX;
								FormDragOffsetY = getPointerY() - WindowY;
								FormDragStart = true;
							}
							else if (isViewGetPoint)
							{
								for(int i=size()-1;i>=0;i--){
									Item item = getItem(i);
									if(item!=null && item.CanFocus){
										if(includeItemPoint(item, getPointerX(), getPointerY())){
											if( item != ItemFocus ){
												pressItem(item);
												focus(item);
											}
											if(item.AllowDrop){
												StartDragItem = true;
											}
											catched = true;
											break;
										}
									}
								}
							}
						}
						
						if(!catched){
//							focus(null);
						}
					}
					
					if(isPointerHold()) // hold
					{ 
						if(FormDragStart)
						{
							WindowX = getPointerX() - FormDragOffsetX;
							WindowY = getPointerY() - FormDragOffsetY;
							
							if(WindowX<0)WindowX=0;
							if(WindowY<0)WindowY=0;
							if(WindowX>SCREEN_WIDTH -WindowW)WindowX=SCREEN_WIDTH -WindowW;
							if(WindowY>SCREEN_HEIGHT-WindowH)WindowY=SCREEN_HEIGHT-WindowH;
						}
						
						if(StartDragItem)
						{
							if( DragedItemSX != getPointerX() || 
								DragedItemSY != getPointerY())
							{
								if(	getFocus() != null && getFocus().AllowDrop){
									DragedItem = getFocus();
									startDragItem(getFocus(), DragedItemSX, DragedItemSY);
								}
								StartDragItem = false;
							}
						}
					}
					
					if(isPointerUp())
					{
						
						
						ClickDown = false;
						FormDragStart = false;

						if(isGetPoint)
						{
							
							for(int i=size()-1;i>=0;i--)
							{
								Item item = getItem(i);
								
								if(item!=null)
								{
									releasItem(item);
									
									if (isViewGetPoint && DragedItem != null &&
										includeItemPoint(item, getPointerX(), getPointerY()))
									{
										dropDragItem(DragedItem, item, getPointerX(), getPointerY());
									}
								}
							}
						}
						
						StartDragItem = false;
						DragedItem = null;
						
//						for(int i=size()-1;i>=0;i--){
//							Item item = getItem(i);
//							if(item!=null){
//								releasItem(item);
//							}
//						}
					}
					
					
				}//end process all items
				
//				select item
				if(!isPointerHold() && IsHoldSelect)
				{
					if (isViewGetPoint || !IsClipUserRect)
					{
						boolean catched = false;
						for(int i=size()-1;i>=0;i--){
							Item item = getItem(i);
							if(includeItemPoint(item, getPointerX(), getPointerY())){
								if(item!=null && item!=ItemFocus && item.CanFocus && item.CanAutoFocus && !item.IsFocused){
									focus(item);
								}
								if(item.CanFocus){
									catched = true;
									break;
								}
							}
						}
						if(!catched){
							focus(null);
						}
					}else{
						focus(null);
					}
				}
				
				if(!IsHoldSelect)
				{
					if(isKeyDown(KeySelectNextItem)){
						focusNext();
						FreezeKeyTime = 0;
					}
					if(isKeyDown(KeySelectPrewItem)){
						focusPrew();
						FreezeKeyTime = 0;
					}
					if(FreezeKeyTime>FreezeKeyDelay && FreezeKeyTime%FreezeKeySpeed==0){
						if(isKeyHold(KeySelectNextItem)){
							focusNext();
						}
						if(isKeyHold(KeySelectPrewItem)){
							focusPrew();
						}
					}
				}
				if(IsShowScrollBar && !IsItemAutoScroll)
				{
					int vx1 = WindowX + UserRect.BorderSize;
					int vy1 = WindowY + UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0);
					int vx2 = WindowX + WindowW - UserRect.BorderSize;
					int vy2 = WindowY + WindowH - UserRect.BorderSize;
					int vw = vx2 - vx1;
					int vh = vy2 - vy1;
					
					ScrollBarH.setScope(0, BoardW);
					ScrollBarV.setScope(0, BoardH);
					
					if(isEventKeyHold(KeyScrollUp)){
						ScrollBarV.setValue(ScrollBarV.getValue() - ScrollSpeed, vh);
					}
					if(isEventKeyHold(KeyScrollDown)){
						ScrollBarV.setValue(ScrollBarV.getValue() + ScrollSpeed, vh);
					}
					if(isEventKeyHold(KeyScrollLeft)){
						ScrollBarH.setValue(ScrollBarH.getValue() - ScrollSpeed, vw);
					}
					if(isEventKeyHold(KeyScrollRight)){
						ScrollBarH.setValue(ScrollBarH.getValue() + ScrollSpeed, vw);
					}
				}
				
				if(IsShowScrollBar)
				{
					if(ViewW<BoardW){
						ScrollBarH.setScope(0, BoardW);
						if(IsItemAutoScroll)ScrollBarH.setValue(ItemFocusRect.X1 + (ItemFocusRect.X2-ItemFocusRect.X1)/2 - ViewW/2, ViewW);
						else ScrollBarH.setValue(ScrollBarH.getValue(), ViewW);
						ScrollBarH.update();
					}else{
						ScrollBarH.setValue(0, ViewW);
					}
					if(ViewH<BoardH){
						ScrollBarV.setScope(0, BoardH);
						if(IsItemAutoScroll)ScrollBarV.setValue(ItemFocusRect.Y1 + (ItemFocusRect.Y2-ItemFocusRect.Y1)/2 - ViewH/2, ViewH);
						else ScrollBarV.setValue(ScrollBarV.getValue(), ViewH);
						ScrollBarV.update();
					}else{
						ScrollBarV.setValue(0, ViewH);
					}
					
				}
				
//				updateCommand();
				
				
			}// end Form Operate
			
			// process all groups
			for(int i=0;i<Groups.size();i++){
				((Group)Groups.elementAt(i)).update(this);
			}
			
			
		}//else if(IsVisible)
		
		processFocusedItem();
		
		FreezeKeyTime++;
		
		if(IsClosing)
		{
			if(ItemFocus!=null){
				focus(null);
			}
			DragedItemSX = -1;
			DragedItemSY = -1;
			DragedItem = null;
		}
		
		if(!Form.isPointerHold())
		{
			DragedItemSX = -1;
			DragedItemSY = -1;
			DragedItem = null;
		}
		
	}
	
	
	
	
	
	
	
	
	public void notifyRender(IGraphics g) 
	{
		
		g.pushClip();
		
		if(isFormTransition())
		{
			drawTransition(g);
		}
		else if(IsVisible)
		{
			UserRect.renderBody(g, WindowX, WindowY, WindowW, WindowH);
			UserRect.renderBorder(g, WindowX, WindowY, WindowW, WindowH);
			
			if(Title.Lable.length()>0)
			{
				int tcolor = Title.TextColor;
				if(OwnerManager!=null && OwnerManager.getTopForm()!=this){
					Title.TextColor &= 0x80808080;
				}
				else if(TopOutHintTimer-->0 && TopOutHintTimer%2==0){
					Title.TextColor = 0;
				}
				Title.resize(WindowW, Title.H);
				Title.render(g, WindowX, WindowY);
				Title.TextColor = tcolor;
				
				if(CanClickClose)
				{
					closew = Title.getHeight() - 4;
					closeh = Title.getHeight() - 4;
					closex = WindowX + 2 + WindowW - Title.getHeight() ;
					closey = WindowY + 2;
					
					if(CloseIcon!=null){
						g.drawImage(CloseIcon, 
								closex + closew/2 - CloseIcon.getWidth()/2, 
								closey + closeh/2 - CloseIcon.getHeight()/2, 
								0);
					}else{
						g.setColor(Title.TextColor);
						g.drawRect(closex, closey, closew, closeh);
						g.drawLine(closex, closey, closex+closew, closey+closeh);
						g.drawLine(closex+closew, closey, closex, closey+closeh);
					}
				}
				
				if (HelpIcon!=null)
				{
					helpw = Title.getHeight() - 4;
					helph = Title.getHeight() - 4;
					helpx = WindowX + 2;
					helpy = WindowY + 2;
					
					g.drawImage(HelpIcon, 
							helpx + helpw/2 - HelpIcon.getWidth()/2, 
							helpy + helph/2 - HelpIcon.getHeight()/2, 
							0);
					
				}
			}
			
			if(IsShowScrollBar)
			{
				if(ViewW!=0 && ViewW<BoardW){
					int barX = 0;
					int barY = WindowH - ScrollBarH.getBarSize();
					int barW = WindowW - (ViewH<BoardH?ScrollBarV.getBarSize():0);
					int barH = ScrollBarH.getBarSize();
					ScrollBarH.render(g, WindowX + barX, WindowY + barY, barW, barH);
				}
				if(ViewH!=0 && ViewH<BoardH){
					int barX = WindowW - ScrollBarV.getBarSize();
					int barY = (Title.Lable.length()>0?Title.H:0);
					int barW = ScrollBarV.getBarSize();
					int barH = WindowH - (ViewW<BoardW?ScrollBarH.getBarSize():0) - (Title.Lable.length()>0?Title.H:0);
					ScrollBarV.render(g, WindowX + barX, WindowY + barY, barW, barH);
				}
			}

			if(IsClipUserRect)
			{
				g.setClip(ViewX1, ViewY1, ViewW, ViewH);
			}
			
			Item item ;
			for(int i=0;i<size();i++)
			{
				item = ((Item)Items.elementAt(i));
				if(item.IsVisible){
					int ix1 = toScreenPosX(item.X);
					int iy1 = toScreenPosY(item.Y);
					int ix2 = toScreenPosX(item.X+item.W);
					int iy2 = toScreenPosY(item.Y+item.H);
					if(!IsClipUserRect || CMath.intersectRect(ViewX1, ViewY1, ViewX2, ViewY2, ix1, iy1, ix2, iy2)){
						item.render(g, ix1, iy1);
					}
				}
			}
	
			renderFocusedItem(g);
			
//			if(Commands.size()>0){
//				renderCommand(g);
//			}
			
		}
		
		g.popClip();
		
		if(Form.isPointerHold() && DragedItem != null )
		{
			DragedItem.render(g, 
					getPointerX(), 
					getPointerY());
			renderFocusedRect(g, 
					getPointerX()-1, 
					getPointerY()-1, 
					DragedItem.getWidth()+2, 
					DragedItem.getHeight()+2
					);
		}
	}
	
	public void notifyResume()
	{
		for(int i=0;i<Items.size();i++)
		{
			Items.elementAt(i).notifyResume();
		}
		
		for(int i=0;i<Groups.size();i++)
		{
			Groups.elementAt(i).notifyResume();
		}
		
		Title.notifyResume();
		
		if (getFocus()!=null)
		{
			getFocus().getFocuse(this);
		}
	}
	
	public void notifyPause() 
	{
		for(int i=0;i<Items.size();i++)
		{
			Items.elementAt(i).notifyPause();
		}
		
		for(int i=0;i<Groups.size();i++)
		{
			Groups.elementAt(i).notifyPause();
		}
		
		Title.notifyPause();
	}
	
//	-------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------
	/**
	 * @param px screen x
	 * @param py screen y
	 * @return
	 */
	public boolean includePoint(int px,int py){
		return CMath.includeRectPoint(
				WindowX, 
				WindowY, 
				WindowX + WindowW, 
				WindowY + WindowH, 
				px, 
				py);
	}
	
	/**
	 * @param px screen x
	 * @param py screen y
	 * @return
	 */
	public boolean includeHeadPoint(int px,int py){
		return Title.Lable.length()>0 && CMath.includeRectPoint(
				WindowX, 
				WindowY, 
				WindowX + WindowW, 
				WindowY + Title.H, 
				px, 
				py);
	}
	
	/**
	 * @param item
	 * @param px screen x
	 * @param py screen y
	 * @return
	 */
	public boolean includeItemPoint(Item item,int px,int py){
		return 
		
		CMath.includeRectPoint(
				toScreenPosX(item.X), 
				toScreenPosY(item.Y), 
				toScreenPosX(item.X+item.W), 
				toScreenPosY(item.Y+item.H), 
				px, 
				py);
	}

	
	public int getWidth(){
		return WindowW;
	}
	
	public int getHeight(){
		return WindowH;
	}

//	-------------------------------------------------------------------------------------------------------------------
	public void setTitle(String title){
		Title.setText(title);
		Title.UserRect.BorderColor = this.UserRect.BorderColor;
		Title.IsTextCenter = true;
	}
	public String getTitle(){
		return Title.Lable;
	}
	public boolean isVisible(){
		return IsVisible;
	}
	public void setAlwaysVisible(){
		show();
		CanClickClose = false;
		KeyClose = 0;
		IsLayoutAutoHide = false;
	}
	public boolean isAlwaysVisible(){
		return !CanClickClose && KeyClose==0;
	}
	
//	public int getHeight() {
//		return H;
//	}
//	public int getWidth() {
//		return W;
//	}
	
	public void resize(int w, int h){
		WindowW = w;
		WindowH = h;
	}
	
	public void AutoSize()
	{
		if(Items.size()>0)
		{
			int H = 0;
			int W = 0;
			for(int i=0;i<Items.size();i++){
				int barw = 0;
				int barh = 0;
				if(IsShowScrollBar){
					barw = ScrollBarV.getBarSize();
					barh = ScrollBarH.getBarSize();
				}
				Item item = getItem(i);
				H = Math.max(H, item.Y + item.getHeight() + UserRect.BorderSize*3 + (Title.Lable.length()>0?Title.H:0) + barh);
				W = Math.max(W, item.X + item.getWidth()  + UserRect.BorderSize*3 + barw);
			
			}
			WindowW = W;
			WindowH = H;
		}
	}
	
	public void optmizeSize()
	{
		if(Items.size()>0)
		{
			int H = 0;
			int W = 0;
			for(int i=0;i<Items.size();i++){
				int barw = 0;
				int barh = 0;
				if(IsShowScrollBar){
					barw = ScrollBarV.getBarSize();
					barh = ScrollBarH.getBarSize();
				}
				Item item = getItem(i);
				H = Math.max(H, item.Y + item.getHeight() + UserRect.BorderSize*3 + (Title.Lable.length()>0?Title.H:0) + barh);
				W = Math.max(W, item.X + item.getWidth()  + UserRect.BorderSize*3 + barw);
			
			}
			WindowW = Math.min(W, WindowW);
			WindowH = Math.min(H, WindowH);
		}
	}
	
	public void setCenter(){
		WindowX = SCREEN_WIDTH / 2  - WindowW/2;
		WindowY = SCREEN_HEIGHT / 2 - WindowH/2;
	}
	 
	public void optmizeBoard()
	{
//		if(Items.size()>0)
		{
			int H = ViewBorderSize;
			int W = ViewBorderSize;
			for(int i=0;i<Items.size();i++){
				Item item = getItem(i);
				H = Math.max(H, item.Y + item.getHeight()+ ViewBorderSize);
				W = Math.max(W, item.X + item.getWidth() + ViewBorderSize);
			}
			BoardH = Math.min(H, BoardH);
			BoardW = Math.min(W, BoardW);
		}
	}
	
	
	/**
	 * intersection with current graphics clip
	 * @param g
	 */
	public void clipUserRect(IGraphics g){
		int vx1 = WindowX + UserRect.BorderSize;
		int vy1 = WindowY + UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0);
		int vx2 = WindowX + WindowW - UserRect.BorderSize;
		int vy2 = WindowY + WindowH - UserRect.BorderSize;
		int vw = vx2 - vx1;
		int vh = vy2 - vy1;
		int bw = 0;
		int bh = 0;

		if(vw<BoardW)bw = ScrollBarV.getBarSize();
		if(vh<BoardH)bh = ScrollBarH.getBarSize();
		if(bw!=0 && IsShowScrollBar)bh = (vh - ScrollBarH.getBarSize() < BoardH) ? (bh = ScrollBarH.getBarSize()) : 0;
		if(bh!=0 && IsShowScrollBar)bw = (vw - ScrollBarV.getBarSize() < BoardW) ? (bw = ScrollBarV.getBarSize()) : 0;
		
		vw -= bw;
		vh -= bh;
		vx2 = vx1 + vw;
		vy2 = vy1 + vh;
		
		g.clipRect(vx1, vy1, vw, vh);
		
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------
	public void open(){
		if(!IsClosed){

			if(OwnerManager!=null && OwnerManager.indexOf(this)<0){
				OwnerManager.pushForm(this);
			}
			
			WindowW = Math.min(SCREEN_WIDTH, WindowW);
			WindowH = Math.min(SCREEN_HEIGHT, WindowH);
			
			WindowX = Math.max(0, WindowX);
			WindowY = Math.max(0, WindowY);
			
			
			if(!IsOpening && !IsVisible){
				IsOpening = true;
				IsClosing = false;
				TransitionTime = 0;
				IsVisible = true;
				//resetCommand();
			}
			focusFirst();
		}
	}
	
	public void open(FormManager manager) {
		OwnerManager = manager;
		open();
	}
	
	public void close(boolean reopen){
		if(!IsClosing && IsVisible){
			IsClosing = true;
			IsOpening = false;
			TransitionTime = TransitionMax;
		}
		IsClosed = !reopen;
	}
	
	public void close(){
		this.close(false);
	}
	
	public void show(){
		open();
	}
	
	public void show(int x,int y){
		WindowX = x;
		WindowY = y;
		PopedRect.X1 = (short)WindowX;
		PopedRect.Y1 = (short)WindowY;
		PopedRect.X2 = (short)WindowX;
		PopedRect.Y2 = (short)WindowY;		
		
		open();
	}
	
	public void show(int x,int y,int startX, int startY){
		WindowX = x;
		WindowY = y;
		PopedRect.X1 = (short)startX;
		PopedRect.Y1 = (short)startY;
		PopedRect.X2 = (short)startX;
		PopedRect.Y2 = (short)startY;
		
		open();
	}
	public void show(int x,int y,int startX, int startY, int startW, int startH){
		WindowX = x;
		WindowY = y;
		PopedRect.X1 = (short)startX;
		PopedRect.Y1 = (short)startY;
		PopedRect.X2 = (short)(startX+startW);
		PopedRect.Y2 = (short)(startY+startH);
		
		open();
	}
	
	public void hide(){
		close(!IsClosed);
	}

	
	public boolean isColosed(){
		return IsClosed;
	}
	
	public boolean isFormTransition(){
		return IsOpening || IsClosing;
	}
	
	public boolean isEnabled(){
		return IsVisible && !(IsOpening || IsClosing);
	}
	
	private void doTransition(){
		if(IsOpening){
			TransitionTime ++;
			if(TransitionTime>TransitionMax){
				IsOpening = false;
				IsVisible = true;
			}
		}
		if(IsClosing){
			TransitionTime --;
			if(TransitionTime<0){
				IsClosing = false;
				IsVisible = false;
			}
		}
	}
	
	public int getTransitionX(){
		int w1 = (WindowX - PopedRect.X1)*TransitionTime / TransitionMax;
		int x1 = PopedRect.X1 + w1;
		return x1;
	}
	public int getTransitionY(){
		int h1 = (WindowY - PopedRect.Y1)*TransitionTime / TransitionMax;
		int y1 = PopedRect.Y1 + h1;
		return y1;
	}
	public int getTransitionW(){
		int w1 = (WindowX - PopedRect.X1)*TransitionTime / TransitionMax;
		int w2 = (WindowX + WindowW - PopedRect.X2)*TransitionTime / TransitionMax;
		int x1 = PopedRect.X1 + w1;
		int x2 = PopedRect.X2 + w2;
		int w = x2 - x1 + 1;
		return w;
	}
	public int getTransitionH(){
		int h1 = (WindowY - PopedRect.Y1)*TransitionTime / TransitionMax;
		int h2 = (WindowY + WindowH - PopedRect.Y2)*TransitionTime / TransitionMax;
		int y1 = PopedRect.Y1 + h1;
		int y2 = PopedRect.Y2 + h2;
		int h = y2 - y1 + 1;
		return h;
	}
	
	private void drawTransition(IGraphics g){
		
		if(TransitionMax<=0)return;
		
		int w1 = (WindowX - PopedRect.X1)*TransitionTime / TransitionMax;
		int h1 = (WindowY - PopedRect.Y1)*TransitionTime / TransitionMax;
		int w2 = (WindowX + WindowW - PopedRect.X2)*TransitionTime / TransitionMax;
		int h2 = (WindowY + WindowH - PopedRect.Y2)*TransitionTime / TransitionMax;
		
		int x1 = PopedRect.X1 + w1;
		int y1 = PopedRect.Y1 + h1;
		int x2 = PopedRect.X2 + w2;
		int y2 = PopedRect.Y2 + h2;
		int w = x2 - x1 + 1;
		int h = y2 - y1 + 1;
		
		// body
		g.setClip(x1, y1, w, h);
//		if(BackImage!=null){
//			g.drawImage(BackImage, x1, y1, 0);
//		}else{
//			g.setColor(BackColor);
//			g.fillRect(x1, y1, x2 - x1, y2 - y1);
//		}
		
		UserRect.renderBody(g, x1, y1, w, h);
		UserRect.renderBorder(g, x1, y1, w, h);
		
//		g.setColor(BorderColor);
//		g.drawRect(x1, y1, x2 - x1, y2 - y1);
	
		/*
		// command
		if(Commands.size()>0){
			y1 = SCREEN_HEIGHT - CommandInfo.H*TransitionTime / TransitionMax;
			y2 = SCREEN_HEIGHT ;
			
			g.setClip(0, y1, SCREEN_WIDTH, y2 - y1 + 1);
			int tw = SCREEN_WIDTH / 4 ;
			
			CommandInfo.X = tw;
			CommandInfo.Y = y1;
			CommandInfo.W = tw*2; 
			
			CommandLButton.X = 0;
			CommandLButton.Y = y1;
			CommandLButton.W = tw;
			
			CommandRButton.X = SCREEN_WIDTH - tw;
			CommandRButton.Y = y1;
			CommandRButton.W = tw;
			
			CommandInfo.UserRect=UserRectCommand;
			CommandLButton.UserRect=UserRectCommand;
			CommandRButton.UserRect=UserRectCommand;
			
			CommandInfo.render(g, CommandInfo.X, CommandInfo.Y);
			CommandLButton.render(g, CommandLButton.X, CommandLButton.Y);
			CommandRButton.render(g, CommandRButton.X, CommandRButton.Y);
		}
		*/
		
	}
	
//	public void selecteLeft(){
//		
//	}
//	public void selecteRight(){
//			
//	}
//	public void selecteLeft(){
//		
//	}
//	public void selecteLeft(){
//		
//	}
	
//	------------------------------------------------------------------------------------------------------

	public int toFormPosX(int screenX){
		return screenX + ScrollBarH.getValue() - (WindowX + UserRect.BorderSize) ;
	}
	public int toFormPosY(int screenY){
		return screenY + ScrollBarV.getValue() - (WindowY + UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0) ) ;
	}

	public int toScreenPosX(int formX){
		return formX - ScrollBarH.getValue() + (WindowX + UserRect.BorderSize);
	}
	
	public int toScreenPosY(int formY){
		return formY - ScrollBarV.getValue() + (WindowY + UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0));
	}

	public int getViewX(){
		return UserRect.BorderSize ;
	}
	public int getViewY(){
		return UserRect.BorderSize + (Title.Lable.length()>0?Title.H:0);
	}
	
	public int getViewWidth(){
		return WindowW - UserRect.BorderSize*2 - (IsShowScrollBar?ScrollBarV.getBarSize():0);
	}
	
	public int getViewHeight(){
		return WindowH - UserRect.BorderSize*2 - (IsShowScrollBar?ScrollBarH.getBarSize():0) - (Title.Lable.length()>0?Title.H:0);
	}
	
//	-------------------------------------------------------------------------------------------------------------------
//	final static public int POPED_ANCHOR_LEFT		= 0x00;
//	final static public int POPED_ANCHOR_RIGHT		= 0x01;
//	final static public int POPED_ANCHOR_HCENTER	= 0x02;
//	final static public int POPED_ANCHOR_TOP		= 0x00;
//	final static public int POPED_ANCHOR_BOTTOM		= 0x10;
//	final static public int POPED_ANCHOR_VCENTER	= 0x20;
//	int			PopedAnchor = 0;
	protected CCD 		PopedRect 	= CCD.createCDRect(0xff000000, 0, 0, 1, 1);
	
	protected 	Vector<Item>	Items 			= new Vector<Item>();
	protected 	Item	ItemFocus 		= null;
	protected	CCD		ItemFocusRect 	= CCD.createCDRect(0xff00ff00, 0, 0, 1, 1);
	public 		boolean	IsItemAutoScroll	= true;
	public 		boolean IsShowItemFocus 	= true;//
	protected	boolean IsDrawItemFocus		= true;//
	public 		int 	ItemTransitionMax 	= 3;
	
	
	protected	Button	DefaultConfirmItem = null;

	public void setDefaultButton(Button btn){
		DefaultConfirmItem = btn;
	}
	
	
	
	/**
	 * @param item
	 * @param key
	 * @param x screen pos x
	 * @param y screen pos x
	 */
	protected void clickItem(Item item, int key, int x, int y){
		if(item!=null && !m_IsOnceItemClicked){
			try{
				item.clicked(this, key, x, y);
				
				for(int i=0;i<Groups.size();i++){
					Group group = ((Group)Groups.elementAt(i));
					if(group.itemAction(item.getClick(), item, group))break;
				}
				
				m_IsOnceItemClicked = true;
				
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
	}
	protected void pressItem(Item item){
		if(item!=null){
			item.pressed(this);
		}
	}
	protected void releasItem(Item item){
		if(item!=null){
			item.released(this);
		}
	}
	
	protected void startDragItem(Item item, int x , int y){
		println("Start Drag");
		if(item.EventListener!=null){
			
		}
	}
	
	protected void dropDragItem(Item item, Item dst, int x, int y){
		println("Drop Drag");
		if(item.EventListener!=null){
			
		}
	}
	
	protected void helpClicked(){}
	
	protected void helpGetted(int px, int py){}
	
	
	
	
	public int appendItem(Item item) {
		int textcolor = item.TextColor;
		if(Items.contains(item)){
			Items.removeElement(item);
		}
		//item.OwnerForm = this;
		Items.addElement(item);
		item.TextColor = textcolor;
		BoardW = Math.max(BoardW, item.X+item.W+ViewBorderSize);
		BoardH = Math.max(BoardH, item.Y+item.H+ViewBorderSize);
		if(Listener!=null){
			Listener.formAction(this, item, FormListener.ITEM_ADDED);
		}
//		if(ItemFocus==null && item.CanFocus)focus(item);
		item.TextColor = textcolor;		//补丁，因为已经过上面的formAction就会丢失颜色
		return Items.indexOf(item);
	}
	
	public int appendItem(Item item, int x, int y) {
		item.X = x;
		item.Y = y;
		return appendItem(item);
	}
	
	public void appendItems(Vector<Item> items) {
		for (Item item : items) {
			appendItem(item);
		}
	}
	
	/**
	 * 在所有控件的最下方添加一个控件
	 * @param item 当前添加的控件
	 * @param autoFace 是否在所有控件的最下方添加一个控件，如果为false则不改变前控件的坐标
	 * @param fillHorizontal 是否将该控件宽度填充整个窗口，前提是autoFace为true
	 * @return
	 */
	public int appendItem(Item item, boolean autoFace, boolean fillHorizontal){
		if(autoFace){
			item.X = ViewBorderSize;
			item.Y = BoardH;
			if(fillHorizontal){
				item.resize(WindowW-UserRect.BorderSize*2-ViewBorderSize*2-(IsShowScrollBar?ScrollBarV.getBarSize():0), item.H);
			}
		}
		return appendItem(item);
	}

	public int appendItemH(Item item, boolean fillHorizontal){
		if(Items.size()>0){
			Item last = getItem(Items.size()-1);
			item.X = last.X + last.getWidth() + ViewBorderSize;
			item.Y = last.Y ;
			
			if(fillHorizontal){
				item.resize(
						(WindowW-UserRect.BorderSize*2-ViewBorderSize-(IsShowScrollBar?ScrollBarV.getBarSize():0))-item.X, 
						item.H
						);
			}
			
		}
		return appendItem(item);
	}

	
	/**
	 * 紧接着一个控件的下方添加一个控件
	 * @param item 要添加的控件
	 * @param base 参照控件
	 * @param resize 是否调整尺寸和参照控件一致
	 * @return
	 */
	public int appendItemD(Item item, Item base, boolean resize){
		if(base!=null && this.indexOfItem(base)>=0){
			if (resize) {
				item.resize(base.getWidth(), base.getHeight());
			}
			item.X = base.X;
			item.Y = base.Y + base.getHeight() + ViewBorderSize;
		}
		return appendItem(item);
	}
	
	/**
	 * 紧接着一个控件的上方添加一个控件
	 * @param item 要添加的控件
	 * @param base 参照控件
	 * @param resize 是否调整尺寸和参照控件一致
	 * @return
	 */
	public int appendItemU(Item item, Item base, boolean resize){
		if(base!=null && this.indexOfItem(base)>=0){
			if (resize) {
				item.resize(base.getWidth(), base.getHeight());
			}
			item.X = base.X;
			item.Y = base.Y - item.getHeight() - ViewBorderSize;
		}
		return appendItem(item);
	}
	
	/**
	 * 紧接着一个控件的左边添加一个控件
	 * @param item 要添加的控件
	 * @param base 参照控件
	 * @param resize 是否调整尺寸和参照控件一致
	 * @return
	 */
	public int appendItemL(Item item, Item base, boolean resize){
		if(base!=null && this.indexOfItem(base)>=0){
			if (resize) {
				item.resize(base.getWidth(), base.getHeight());
			}
			item.Y = base.Y;
			item.X = base.X + base.getWidth() + ViewBorderSize;
		}
		return appendItem(item);
	}
	
	/**
	 * 紧接着一个控件的右边添加一个控件
	 * @param item 要添加的控件
	 * @param base 参照控件
	 * @param resize 是否调整尺寸和参照控件一致
	 * @return
	 */
	public int appendItemR(Item item, Item base, boolean resize){
		if(base!=null && this.indexOfItem(base)>=0){
			if (resize) {
				item.resize(base.getWidth(), base.getHeight());
			}
			item.Y = base.Y;
			item.X = base.X - item.getWidth() - ViewBorderSize;
		}
		return appendItem(item);
	}
	
	//remove
	
	public void deleteItem(Item item) 
	{
		if(Items.contains(item)){
			if (ItemFocus == item) {
				ItemFocus = null;
			}
			Items.removeElement(item);
			if(Listener!=null){
				Listener.formAction(this, item, FormListener.ITEM_REMOVED);
			}
		}
	}
	
	public void deleteItems(Vector items){
		if (items!=null){
			for(int i=items.size()-1;i>=0;i--){
				deleteItem((Item)items.elementAt(i)) ;
			}
		}
	}
	
	public void deleteItems(Item[] items){
		if (items!=null){
			for(int i=items.length-1;i>=0;i--){
				deleteItem(items[i]) ;
			}
		}
	}
	
	public void deleteAllItem() 
	{
		Item[] items = new Item[Items.size()];
		Items.copyInto(items);
		for(int i=0;i<items.length;i++){
			deleteItem(items[i]) ;
		}
		BoardW = ViewBorderSize;
		BoardH = ViewBorderSize;
	}
	
	
	/**
	 * @param x screen x
	 * @param y screen y
	 * @return
	 */
	public Item getItem(int x, int y) {
		if (includePoint(x, y)) {
			for (int i = size() - 1; i >= 0; i--) {
				Item item = getItem(i);
				if (item != null && includeItemPoint(item, x, y)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public Item getVisibleItem(int x, int y) {
		if (includePoint(x, y)) {
			for (int i = size() - 1; i >= 0; i--) {
				Item item = getItem(i);
				if (item != null && item.IsVisible && includeItemPoint(item, x, y)) {
					return item;
				}
			}
		}
		return null;
	}
	
	public Item getAttributeItem(String configid) {
		return (Item)getAttribute("item_"+configid);
	}

	public Object putAttributeItem(String configid, Item item) {
		return setAttribute("item_"+configid, item);
	}
	
	public Group getAttributeGroup(String configid) {
		return (Group)getAttribute("group_"+configid);
	}

	public Object putAttributeGroup(String configid, Group group) {
		return setAttribute("group_"+configid, group);
	}
	
	// get set
	public Item getItem(int itemNum) 
	{
		return (Item)Items.elementAt(itemNum);
	}
	
	public int indexOfItem(Item item)
	{
		return Items.indexOf(item);
	}
	
	public void setItem(int itemNum, Item item) 
	{
		Items.setElementAt(item, itemNum);
	}
	
	public int size()
	{
		return Items.size();
	}
	
	public Item getFocus()
	{
		return ItemFocus;
	}
	
	public int focus(Item item){
		
		if(!IsVisible){
			return -1;
		}
		
		if(item==null)
		{
			if(ItemFocus!=null)
			{
				ItemFocus.IsFocused = false;
				ItemFocus.lossFocuse(this);
				
				if(Listener!=null)
				{
					Listener.formAction(this, ItemFocus, FormListener.ITEM_LOSS_FOCUS);
				}
				ItemFocus = null;
			}
			
			return -1;
		}
		
		if(item.CanFocus)
		{
			
			if(item!=null && item == ItemFocus){
				return Items.indexOf(ItemFocus);
			}
			
			if(ItemFocus!=null){
				ItemFocus.IsFocused = false;
				ItemFocus.lossFocuse(this);
				if(Listener!=null){
					Listener.formAction(this, ItemFocus, FormListener.ITEM_LOSS_FOCUS);
				}
			}
			
			ItemFocus = item;
			
			if(ItemFocus!=null){
				ItemFocus.IsFocused = true;
				ItemFocus.getFocuse(this);
				if(Listener!=null){
					Listener.formAction(this, ItemFocus, FormListener.ITEM_GET_FOCUS);
				}
			}
			
			return Items.indexOf(ItemFocus);
		}else{
			return -1;
		}
	}
	
	public int focusFirst(){
		int count = Items.size();
		for(int i=0;i<count;i++){
			Item item = getItem(i);
			if(item.CanFocus){
				return focus(item);
			}
		}
		return -1;
	}
	
	public int focusNext(){
		if(Items.size()<=0)return -1;
		int pos = -1;
		if(ItemFocus!=null){
			pos = CMath.cycNum(indexOfItem(ItemFocus), 1, size());
		}
		for(int i=0;i<Items.size();i++){
			int next = CMath.cycNum(pos, i, Items.size());
			Item item = getItem(next);
			if(item.CanFocus){
				return focus(item);
			}
		}
		return pos;
	}
	
	public int focusPrew(){
		if(Items.size()<=0)return -1;
		int pos = -1;
		if(ItemFocus!=null){
			pos = CMath.cycNum(indexOfItem(ItemFocus),-1, size());
		}
		for(int i=0;i<Items.size();i++){
			int prew = CMath.cycNum(pos,-i, Items.size());
			Item item = getItem(prew);
			if(item.CanFocus){
				return focus(item);
			}
		}
		return pos;
	}
	
	/**
	 * 
	 */
	protected void processFocusedItem()
	{
		if(ItemFocus!=null)
		{
			int dx1 = ItemFocus.X - 1 - ItemFocusRect.X1;
			int dy1 = ItemFocus.Y - 1 - ItemFocusRect.Y1;
			int dx2 = ItemFocus.X + ItemFocus.W + 1	- ItemFocusRect.X2;
			int dy2 = ItemFocus.Y + ItemFocus.H	+ 1 - ItemFocusRect.Y2;
			
			
			if(ItemTransitionMax>0 && Math.abs(dx1)>=ItemTransitionMax){
				ItemFocusRect.X1 += dx1/ItemTransitionMax;
			}else{
				ItemFocusRect.X1 += dx1;
			}
			
			if(ItemTransitionMax>0 && Math.abs(dy1)>=ItemTransitionMax){
				ItemFocusRect.Y1 += dy1/ItemTransitionMax;
			}else{
				ItemFocusRect.Y1 += dy1;
			}
			
			if(ItemTransitionMax>0 && Math.abs(dx2)>=ItemTransitionMax){
				ItemFocusRect.X2 += dx2/ItemTransitionMax;
			}else{
				ItemFocusRect.X2 += dx2;
			}
			
			if(ItemTransitionMax>0 && Math.abs(dy2)>=ItemTransitionMax){
				ItemFocusRect.Y2 += dy2/ItemTransitionMax;
			}else{
				ItemFocusRect.Y2 += dy2;
			}
		}
	}
	
	protected void renderFocusedItem(IGraphics g)
	{
		if( ItemFocus!=null && ItemFocus.IsVisible && IsShowItemFocus && IsDrawItemFocus)
		{
			int b = Math.abs(CMath.sinTimes256(getTimer()*10));
			
			b = b>255?255:b;
			b = b<0?0:b;
			ItemFocusRect.Mask = 0xff000000 + b*0x10000 +  b*0x100  + b;
			ItemFocusRect.render(g, 
					toScreenPosX(0), 
					toScreenPosY(0), 
					ItemFocusRect.Mask);
		}
	}
	
	public void renderFocusedRect(IGraphics g, int x, int y, int w, int h){
		int b = Math.abs(CMath.sinTimes256(getTimer()*10));
		b = b>255?255:b;
		b = b<0?0:b;
		int color = 0xff000000 + b*0x10000 +  b*0x100  + b;
		g.setColor(color);
		g.drawRect(x, y, w, h);
	}
	
//	-------------------------------------------------------------------------------------------------------------------
//	//
	Vector<Group> Groups = new Vector<Group>();
	
	public void appendGroup(Group group)
	{
		if(group!=null)
		{
			if (!Groups.contains(group))
			{
				Groups.addElement(group);
				group.OwnerForm = this;
				group.appended(this);
				if(Listener!=null){
					Listener.formAction(this, group, FormListener.GROUP_ADDED);
				}
			}
		}
	}
	
	public void deleteGroup(Group group)
	{
		if(group!=null)
		{
			if (Groups.contains(group))
			{
				Groups.removeElement(group);
				group.OwnerForm = null;
				group.removed(this);
				if(Listener!=null){
					Listener.formAction(this, group, FormListener.GROUP_REMOVED);
				}
			}
		}
	}
	public void deleteGroups(Group[] groups)
	{
		if (groups!=null){
			for (int i=groups.length-1; i>=0; --i) {
				deleteGroup(groups[i]);
			}
		}
	}
	public void deleteGroups(Vector groups)
	{
		if (groups!=null){
			for (int i=groups.size()-1; i>=0; --i) {
				deleteGroup((Group)groups.elementAt(i));
			}
		}
	}
//	-------------------------------------------------------------------------------------------------------------------

	
}
