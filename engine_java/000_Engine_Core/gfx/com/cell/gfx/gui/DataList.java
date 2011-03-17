package com.cell.gfx.gui;

import com.cell.gfx.AScreen;


abstract public class DataList extends Group
{
	public static String PageSelectTextOK 		= "ok";
	public static String PageSelectTextCancel 	= "cancel";
	
	public class PageSelect extends Form
	{
		int PagePrevIndex = -1;
		int PageSelectIndex = 0;
		
		public Button Add;
		public Button Sub;
		public TextBox Number;
		
		public Button OK;
		public Button Cancel;
		
		protected PageSelect(String title, int w, int h)
		{
			super(w, h);
			
			super.setListener(DataList.this.OwnerForm.Listener);
			super.IsLayoutDialog 	= true;
			super.UserRect 			= DataList.this.OwnerForm.UserRect;
			super.TransitionMax 	= DataList.this.OwnerForm.TransitionMax;
			super.CloseIcon			= DataList.this.OwnerForm.CloseIcon;
			
			super.setTitle(" " + title);
			super.Title.UserRect	= DataList.this.OwnerForm.Title.UserRect;
			
			
			PagePrevIndex			= DataList.this.getPageIndex();
			PageSelectIndex 		= DataList.this.getPageIndex();
			
			Add = new Button(
					DataList.this.Title.getHeight(),
					DataList.this.Title.getHeight(),
					new Command("+"));

			Sub = new Button(
					DataList.this.Title.getHeight(),
					DataList.this.Title.getHeight(),
					new Command("-"));
			
			Number = new TextBox("1",
					getViewWidth()-Add.getWidth()-Sub.getWidth(),
					Add.getHeight());
			Number.IsMultiLine 		= false;
			Number.CanEdit 			= true;
			Number.IsNumberOnly 	= true;
			Number.CanFocus			= true;
			
			OK = new Button(
					50,
					DataList.this.Title.getHeight(),
					new Command(PageSelectTextOK));;
			Cancel = new Button(
					50,
					DataList.this.Title.getHeight(),
					new Command(PageSelectTextCancel));;
			
			this.appendItem(Sub, true, false);
			this.appendItemH(Number, false);
			this.appendItemH(Add, false);
			
			this.appendItem(OK,true,false);
			this.appendItemH(Cancel, false);
		
			this.optmizeSize();
		
			DataList.this.OwnerForm.OwnerManager.pushForm(this);
			
			
			this.setDefaultButton(OK);
		}

		
		
		public void open() {
			super.open();
			this.focus(Number);
		}



		public void notifyLogic() 
		{
			
			super.notifyLogic();
			
			if(isEventPointerHold()){
				if(Add.isPressed()){
					PageSelectIndex ++;
				}
				if(Sub.isPressed()){
					PageSelectIndex --;
				}
			}
			
			if(getFocus() != Number){
				Number.setText((PageSelectIndex+1)+"");
			}else{
				String num = Number.getText();
				try{
					int index = Integer.parseInt(num);
					index = Math.max(index, 1);
					index = Math.min(index, DataList.this.getPageCount());
					PageSelectIndex = index - 1;
				}catch(Exception err){
					PageSelectIndex = 0;
				}
			}
			
			PageSelectIndex = Math.max(PageSelectIndex, 0);
			PageSelectIndex = Math.min(PageSelectIndex, DataList.this.getPageCount()-1);
		
			
		}
		
		protected void clickItem(Item item, int key, int x, int y) {
			super.clickItem(item, key, x, y);
			
			if(item == OK){
				this.close(false);
				DataList.this.setPageIndex(PageSelectIndex);
				clickedPageButton(getPageIndex(), PagePrevIndex);
				DataList.this.pageChanged(PageSelectIndex);
				//if(PagePrevIndex!=PageSelectIndex)DataList.this.pageChanged(PageSelectIndex);
			}
			else if(item == Cancel){
				this.close(false);
			}
			
		}

		public int focus(Item item) {
			int flag = super.focus(item);
			if(item == Number){
				Number.selectedAllText();
			}
			return flag;
		}
	
		
	
		
	}
	
	public static enum ViewMode
	{
		MODE_DETAIL(),
		MODE_GRID(),
		;
	}
	
	private ViewMode CurViewMode;
	
	
	LabelBar[] 		GridColumnHeads;
	Item[][] 		GridRows;// [r][c]

//	private Vector Lines;
	
	public LabelBar 	Title;
	public LabelBar		HeadStrip;
	public Button 		BeginPage;
	public Button 		NextPage;
	public LabelBar 	Page;
	public Button 		PrewPage;
	public Button 		EndPage;
	
	protected LabelBar 	BackGround;
	
	final public int X ;
	final public int Y ;
	final public int W ;
	final public int H ;
	
	final public int CellSX;
	final public int CellSY;
	final public int CellSW;
	final public int CellSH;
	
	public int CellH;
	public int CellW;
	
	private int m_PageIndex;
	private int m_PageCount;
	private int m_MaxDataCount;
	
	private int m_Interval = 0;
	
	private boolean IsShowHead = true;
	
	/**
	 * construct detail list
	 * @param form
	 * @param title
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param headh
	 * @param cellh
	 * @param isBeginEnd
	 */
	public DataList(
			Form form, 
			String title, 
			int x, int y, int w, int h,
			int headh, 
			int cellh,
			boolean isBeginEnd
			)
	{
		this(form, title, x, y, w, h, headh, 0, cellh, isBeginEnd, ViewMode.MODE_DETAIL, false);
	}
	
	public DataList(
			Form form, 
			String title, 
			int x, int y, int w, int h,
			int headh, 
			int cellw,
			int cellh,
			boolean isBeginEnd,
			ViewMode viewmode
			)
	{
		this(form, title, x, y, w, h, headh, cellw, cellh, isBeginEnd, viewmode, false);
	}
	
/*
	+---------------------------------------------+
	| title     | begin | prew | 1/1 | next | end |  page control
	|---------------------------------------------|
	| label1  | label2 | label3 |   label4        |  elements head
	|---------------------------------------------|
	|         |        |        |                 |  elements data
	|         |        |        |                 |
	|         |     data list   |                 |
	|         |        |        |                 |
	|         |        |        |                 |
	|         |        |        |                 |
	+---------------------------------------------+

*/	
	
	public DataList(
			Form form, 
			String title, 
			int x, int y, int w, int h,
			int headh, 
			int cellw,
			int cellh,
			boolean isBeginEnd,
			ViewMode viewmode,
			boolean isPageControlBottom,
			int interval
			)
	{
		CurViewMode = viewmode;
		
		OwnerForm = form;
		
		//int dvw = w % cellw;
		//int dvh = h % cellh;
		
		X = x;
		Y = y;
		W = w;//CurViewMode == ViewMode.MODE_GRID ? w - dvw : w;
		H = h;//CurViewMode == ViewMode.MODE_GRID ? h - dvh : h;
		
		CellW = cellw;
		CellH = cellh;

		m_Interval = interval;
		
		int vt = form.ViewBorderSize;
		form.ViewBorderSize = 0;

		BackGround = new LabelBar("", W, H);
		form.appendItem(BackGround, X, Y);

		{
			EndPage = new Button();
			NextPage = new Button();
			Page = new LabelBar();
			PrewPage = new Button();
			BeginPage = new Button();
			Title = new LabelBar();
		}
		
		
		if (isPageControlBottom) 
		{
			
			addListControls(form, viewmode, headh, X, Y, W, H);
			
			addPageControls(form, title, headh, X, Y + H - headh, W, H, isBeginEnd);
			
			CellSX = X;
			CellSY = Y + (IsShowHead ? headh : 0);
			CellSW = W;
			CellSH = H - (IsShowHead ? headh : 0) - headh;
			
		}
		else
		{
			addPageControls(form, title, headh, X, Y, W, H, isBeginEnd);
			
			addListControls(form, viewmode, headh, X, Y + headh, W, H);
			
			CellSX = X;
			CellSY = Y + (IsShowHead ? headh : 0) + headh;
			CellSW = W;
			CellSH = H - (IsShowHead ? headh : 0) - headh;
			
		}
		
		setViewMode(viewmode);
		
		form.ViewBorderSize = vt;
		
		
		
		form.appendGroup(this);
	
		
	}
	
	public DataList(
			Form form, 
			String title, 
			int x, int y, int w, int h,
			int headh, 
			int cellw,
			int cellh,
			boolean isBeginEnd,
			ViewMode viewmode,
			boolean isPageControlBottom
			)
	{
		CurViewMode = viewmode;
		
		OwnerForm = form;
		
		//int dvw = w % cellw;
		//int dvh = h % cellh;
		
		X = x;
		Y = y;
		W = w;//CurViewMode == ViewMode.MODE_GRID ? w - dvw : w;
		H = h;//CurViewMode == ViewMode.MODE_GRID ? h - dvh : h;
		
		CellW = cellw;
		CellH = cellh;

		int vt = form.ViewBorderSize;
		form.ViewBorderSize = 0;

		BackGround = new LabelBar("", W, H);
		form.appendItem(BackGround, X, Y);

		{
			EndPage = new Button();
			NextPage = new Button();
			Page = new LabelBar();
			PrewPage = new Button();
			BeginPage = new Button();
			Title = new LabelBar();
		}
		
		
		if (isPageControlBottom) 
		{
			
			addListControls(form, viewmode, headh, X, Y, W, H);
			
			addPageControls(form, title, headh, X, Y + H - headh, W, H, isBeginEnd);
			
			CellSX = X;
			CellSY = Y + (IsShowHead ? headh : 0);
			CellSW = W;
			CellSH = H - (IsShowHead ? headh : 0) - headh;
			
		}
		else
		{
			addPageControls(form, title, headh, X, Y, W, H, isBeginEnd);
			
			addListControls(form, viewmode, headh, X, Y + headh, W, H);
			
			CellSX = X;
			CellSY = Y + (IsShowHead ? headh : 0) + headh;
			CellSW = W;
			CellSH = H - (IsShowHead ? headh : 0) - headh;
			
		}
		
		setViewMode(viewmode);
		
		form.ViewBorderSize = vt;
		
		
		
		form.appendGroup(this);
	}
	
	private void addListControls (Form form, ViewMode viewmode, int headh, int x, int y, int w, int h)
	{
		// page
		setPageIndex(0);
		
		// column head
		GridColumnHeads = createGridHead();
		
		IsShowHead = true;
		int tw = 0;
		for(int c=0;c<GridColumnHeads.length;c++){
			IsShowHead = GridColumnHeads[c].IsVisible ? IsShowHead : false;
			GridColumnHeads[c].X = x + tw;
			GridColumnHeads[c].Y = y;
			GridColumnHeads[c].resize(GridColumnHeads[c].getWidth(), headh);
			if(IsShowHead)form.appendItem(GridColumnHeads[c]);
			tw += GridColumnHeads[c].getWidth();
		}
		
	}
	
	/**
	 * 改变表头的文字
	 * @param c
	 * @param text
	 */
	public void setGridheadText(int c, String text)
	{
		if (c>=GridColumnHeads.length){
			return;
		}
		GridColumnHeads[c].setText(text);
	}
	
	/**
	 * 重新设定表头
	 * @param c
	 * @param head
	 */
	public void setGridhead(int c, LabelBar head)
	{
		if (c>=GridColumnHeads.length || head==null || head == GridColumnHeads[c]){
			return;
		}
//		OwnerForm.deleteItem(head);
		for (int i = 0; i<GridColumnHeads.length ;i++){
			if (GridColumnHeads[i] == head){
				GridColumnHeads[i] = new LabelBar("",0,GridColumnHeads[i].getHeight());
				GridColumnHeads[i].X = head.X;
				GridColumnHeads[i].Y = head.Y;
				OwnerForm.deleteItem(head);
			}
		}
		head.resize(GridColumnHeads[c].getWidth(), GridColumnHeads[c].getHeight());
		head.X = GridColumnHeads[c].X;
		head.Y = GridColumnHeads[c].Y;
		OwnerForm.deleteItem(GridColumnHeads[c]);
		OwnerForm.appendItem(head);
		GridColumnHeads[c] = head;
	}
	
	public void setGridhead(GroupPageSelect pageSelect, int pageIndex, int c, LabelBar head)
	{
		if (c>=GridColumnHeads.length || head==null || head == GridColumnHeads[c]){
			return;
		}
//		OwnerForm.deleteItem(head);
		for (int i = 0; i<GridColumnHeads.length ;i++){
			if (GridColumnHeads[i] == head){
				GridColumnHeads[i] = new LabelBar("",0,GridColumnHeads[i].getHeight());
				GridColumnHeads[i].X = pageSelect.getItemX(pageIndex, head);
				GridColumnHeads[i].Y = pageSelect.getItemY(pageIndex, head);;
				pageSelect.removeItem(pageIndex, head);
			}
		}
		head.resize(GridColumnHeads[c].getWidth(), GridColumnHeads[c].getHeight());
		head.X = pageSelect.getItemX(pageIndex, GridColumnHeads[c]);
		head.Y = pageSelect.getItemY(pageIndex, GridColumnHeads[c]);
		pageSelect.removeItem(pageIndex, GridColumnHeads[c]);
		pageSelect.addItem(pageIndex, head);
		GridColumnHeads[c] = head;
	}
	/**
	 * 设定各项数据的宽度，只适用与条形
	 * @param widths
	 */
	public void resizeGridhead(int[] widths)
	{
		if (CurViewMode!= ViewMode.MODE_DETAIL || widths.length != GridColumnHeads.length){
			return;
		}
		int tw = 0;
		int x = GridColumnHeads[0].X;
		for(int c=0;c<GridColumnHeads.length;c++){
			IsShowHead = GridColumnHeads[c].IsVisible ? IsShowHead : false;
			GridColumnHeads[c].X = x + tw;
			GridColumnHeads[c].resize(widths[c], GridColumnHeads[c].getHeight());
			tw += GridColumnHeads[c].getWidth();
		}
		resizeGridRows();
	}
	
	private void addPageControls(Form form, String title, int headh, int x, int y, int w, int h, boolean isBeginEnd)
	{
		{
			int tw = w;
			
			HeadStrip = new LabelBar("",w,headh);
			form.appendItem(HeadStrip,x,y);
			
			EndPage = new Button(50,headh,new Command("End"));
			EndPage.X = x + tw - EndPage.getWidth();
			EndPage.Y = y;
			if(isBeginEnd){
				form.appendItem(EndPage);
				tw -= EndPage.getWidth();
			}
			
			NextPage = new Button(50,headh,new Command("Next"));
			NextPage.X = x + tw - NextPage.getWidth();
			NextPage.Y = y;
			form.appendItem(NextPage);
			tw -= NextPage.getWidth();
			
			Page = new LabelBar("1/1",60,headh);
			Page.X = x + tw - Page.getWidth();
			Page.Y = y;
			form.appendItem(Page);
			tw -= Page.getWidth();
			Page.CanFocus = true;
			Page.Click = new Command("");
			
			
			PrewPage = new Button(50,headh,new Command("Prev"));
			PrewPage.X = x + tw - PrewPage.getWidth();
			PrewPage.Y = y;
			form.appendItem(PrewPage);
			tw -= PrewPage.getWidth();
			
			
			BeginPage = new Button(50,headh,new Command("Begin"));
			BeginPage.X = x + tw - BeginPage.getWidth();
			BeginPage.Y = y;
			if(isBeginEnd){
				form.appendItem(BeginPage);
				tw -= BeginPage.getWidth();
			}
			
			Title = new LabelBar(title,tw,headh);
			Title.X = x;
			Title.Y = y;
			form.appendItem(Title);
			
		}
	}
	
	
	private void resizeGridRows()
	{
		for(int r=0;r<GridRows.length;r++){
			for(int c=0;c<GridRows[r].length;c++){
				GridRows[r][c].resize(GridColumnHeads[c].getWidth(), CellH);
				GridRows[r][c].X = GridColumnHeads[c].X;
				GridRows[r][c].Y = CellSY + r*(CellH+m_Interval);
//				form.appendItem(GridRows[r][c]);
			}
		}
	}
	
	
	public void setViewMode(ViewMode mode) 
	{
		CurViewMode = mode;
		
		Form form = OwnerForm;
		
		if (GridRows!=null) {
			for(int r=0;r<GridRows.length;r++){
				form.deleteItems(GridRows[r]);
			}
		}
		
		if (CurViewMode == ViewMode.MODE_DETAIL)
		{
			int rcount = (CellSH+m_Interval)/(CellH+m_Interval);
			
			GridRows = new Item[rcount][GridColumnHeads.length];
			
			for(int r=0;r<GridRows.length;r++){
				GridRows[r] = createGridRow(r);
				for(int c=0;c<GridRows[r].length;c++){
					GridRows[r][c].resize(GridColumnHeads[c].getWidth(), CellH);
					GridRows[r][c].X = GridColumnHeads[c].X;
					GridRows[r][c].Y = CellSY + r*(CellH+m_Interval);
					form.appendItem(GridRows[r][c]);
				}
			}
		}
		else if (CurViewMode == ViewMode.MODE_GRID)
		{
			int xc = (CellSW+m_Interval) / (CellW+m_Interval);
			int yc = (CellSH+m_Interval) / (CellH+m_Interval);
			int rcount = xc * yc;
			
			GridRows = new Item[rcount][GridColumnHeads.length];
			
			int sx = CellSX + (CellSW - xc*(CellW+m_Interval)>>1);
			for(int r=0;r<GridRows.length;r++){
				GridRows[r] = createGridRow(r);
				for(int c=0;c<GridRows[r].length;c++){
					GridRows[r][c].resize(CellW, CellH);
					GridRows[r][c].X = sx + (r%xc) * (CellW+m_Interval);
					GridRows[r][c].Y = CellSY + (r/xc) * (CellH+m_Interval);
					form.appendItem(GridRows[r][c]);
				}
			}
		}
		
	}
	
	public ViewMode getViewMode(){
		return CurViewMode;
	}
	
	protected void appended(Form form) {OwnerForm = form;}

	/**
	 * 设定数据间距
	 * 当CurViewMode为MODE_DETAIL时设定的是行间距
	 * 当CurViewMode为MODE_GRID时同时设定行间距和列间距
	 * @param interval
	 */
	public void setInterval(int interval)
	{
		m_Interval = interval;
		setViewMode(CurViewMode);
	}
	/**
	 * 设定表格中各个元素的外观
	 * setTitleUIRect Title 标题
	 * setBackGroundUIRect BackGround 背景
	 * setButtonUIRect BeginPage、EndPage、PrewPage、NextPage 第一页，最后页，上一页，下一页
	 * setPageUIRect Page 页码
	 * setHeadStrip HeadStrip 标题和翻页按钮所在的空条
	 * @param uir
	 */
	public void setTitleUIRect(UIRect uir)
	{
		if (Title!=null){
			Title.UserRect = uir;
		}
	}
	public void setTitleTextColor(int color)
	{
		if (Title!=null){
			Title.TextColor = color;
		}
	}
	public void setTitleVisible(boolean visible)
	{
		Title.IsVisible = visible;
	}
	public void setBackGroundUIRect(UIRect uir)
	{
		if (BackGround!=null){
			BackGround.UserRect = uir;
		}
	}
	public void setButtonUIRect(UIRect uir, UIRect uird)
	{
		if (BeginPage!=null){
			BeginPage.UserRect = uir;
			BeginPage.PressDownRect = uird;
		}
		if (EndPage!=null){
			EndPage.UserRect = uir;
			EndPage.PressDownRect = uird;
		}
		if (PrewPage!=null){
			PrewPage.UserRect = uir;
			PrewPage.PressDownRect = uird;
		}
		if (NextPage!=null){
			NextPage.UserRect = uir;
			NextPage.PressDownRect = uird;
		}
	}
	public void setButtonTextColor(int colorFocuse, int colorUnFocuse)
	{
		if (BeginPage!=null){
			BeginPage.ColorFocusedText = colorFocuse;
			BeginPage.ColorUnFocusedText = colorUnFocuse;
		}
		if (EndPage!=null){
			EndPage.ColorFocusedText = colorFocuse;
			EndPage.ColorUnFocusedText = colorUnFocuse;
		}
		if (PrewPage!=null){
			PrewPage.ColorFocusedText = colorFocuse;
			PrewPage.ColorUnFocusedText = colorUnFocuse;
		}
		if (NextPage!=null){
			NextPage.ColorFocusedText = colorFocuse;
			NextPage.ColorUnFocusedText = colorUnFocuse;
		}
	}
	public void setPageUIRect(UIRect uir)
	{
		if (Page!=null){
			Page.UserRect = uir;
		}
	}
	public void setPageTextColor(int color)
	{
		if (Page!=null){
			Page.TextColor = color;
		}
	}
	public void setHeadStrip(UIRect uir)
	{
		HeadStrip.UserRect = uir;
	}
	/**
	 * 可以设定空条为不可见
	 * @param Visible
	 */
	public void setHeadStripVisible(boolean Visible)
	{
		HeadStrip.IsVisible = Visible;
	}
	/**
	 * 设定按钮的文字
	 * setPrevText 上一页
	 * setNextText 下一页
	 * setBeginText 第一页
	 * setEndText 最后页
	 * @param text
	 */
	public void setPrevText(String text)
	{
		PrewPage.setText(text);
	}
	public void setNextText(String text)
	{
		NextPage.setText(text);
	}
	public void setBeginText(String text)
	{
		BeginPage.setText(text);
	}
	public void setEndText(String text)
	{
		EndPage.setText(text);
	}
	
	
	protected boolean itemAction(Command command, Item item, Group group)
	{
		int prevPage = getPageIndex();
		
		if(item==NextPage)
		{
			
			if(getPageCount()<=0){
				setPageIndex(0);
			}else{
				if (getPageIndex() < getPageCount() - 1) {
					setPageIndex(getPageIndex() + 1);
				}else{
					setPageIndex(getPageCount() - 1);
				}
			}
			clickedNextPage();
			clickedPageButton(getPageIndex(), prevPage);
			if(prevPage!=getPageIndex())pageChanged(getPageIndex());
			return true;
		}
		else if(item == PrewPage)
		{
			if (getPageIndex() > 0) {
				setPageIndex(getPageIndex() - 1);
			}else{
				setPageIndex(0);
			}
			clickedPrewPage();
			clickedPageButton(getPageIndex(), prevPage);
			if(prevPage!=getPageIndex())pageChanged(getPageIndex());
			return true;
		}
		else if(item==BeginPage)
		{
			setPageIndex(0);
			clickedBeginPage();
			clickedPageButton(getPageIndex(), prevPage);
			if(prevPage!=getPageIndex())pageChanged(getPageIndex());
			return true;
		}
		else if(item == EndPage)
		{
			if(getPageCount()>0)setPageIndex(getPageCount()-1);
			else setPageIndex(0);
			clickedEndPage();
			clickedPageButton(getPageIndex(), prevPage);
			if(prevPage!=getPageIndex())pageChanged(getPageIndex());
			return true;
		}
		else if(item == Page)
		{
			PageSelect ps = new PageSelect(Title.getText(),160,160);
			
			int dx = OwnerForm.toScreenPosX(Page.X) + Page.getWidth()/2 - 80;
			int dy = OwnerForm.toScreenPosY(Page.Y) + Page.getHeight() + 2;
			
			dx = Math.min(dx, AScreen.SCREEN_WIDTH - ps.getWidth());
			dx = Math.max(dx, 0);
			
			if (dy + ps.getHeight() > AScreen.SCREEN_HEIGHT) {
				dy = OwnerForm.toScreenPosY(Page.Y) - ps.getHeight() - 2;
			}
			
			
			ps.show(
					dx, dy,
					OwnerForm.toScreenPosX(Page.X), 
					OwnerForm.toScreenPosY(Page.Y), 
					Page.getWidth(), 
					Page.getHeight()
					);
			return true;
		}
		return false;
	}

	private boolean m_pageChaged = false;
	
	protected void update(Form form)
	{
		synchronized (this) {
			updatePage();
		}
		
		if (m_pageChaged) {
			m_pageChaged = false;
			pageChanged(getPageIndex());
		}
		
		Page.setText((getPageIndex()+1)+"/"+getPageCount());
		
		for(int r=0;r<getRowCount();r++){
			updateGridRow(GridRows[r],r);
		}
		
	}
	
	private void updatePage() 
	{
		int oldpageindex = m_PageIndex;
		
		if(m_MaxDataCount>0){
			int page = (m_MaxDataCount+GridRows.length-1) / GridRows.length;
			m_PageCount = page;
		}else{
			m_PageIndex = 0;
			m_PageCount = 0;
		}
		if(m_PageCount > 0 && m_PageIndex>=m_PageCount){
			m_PageIndex = getPageCount()-1;
		}
		
		if (oldpageindex != m_PageIndex) {
			m_pageChaged = true;
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------
	
	/**
	 * set head size
	 * @return
	 */
	protected abstract LabelBar[] createGridHead();
	
	/**
	 * set grid type
	 * @param r TODO
	 * @param row
	 */
	protected abstract Item[] createGridRow(int r);
	
//	-------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * no need call super
	 * @param page
	 */
	protected void pageChanged(int page){}
	
	protected void clickedPageButton(int curpage, int prewpage){}
	
	/**
	 * no need call super
	 * @return
	 */
	protected boolean clickedBeginPage(){return true;}
	/**
	 * no need call super
	 * @return
	 */
	protected boolean clickedEndPage(){return true;}
	/**
	 * no need call super
	 * @return
	 */
	protected boolean clickedNextPage(){return true;}
	/**
	 * no need call super
	 * @return
	 */
	protected boolean clickedPrewPage(){return true;}
	/**
	 * no need call super
	 * @param row
	 * @param r
	 */
	protected void updateGridRow(Item[] row, int r){}
	
//	-------------------------------------------------------------------------------------------------------------------
//	size get

	
	synchronized public void setPageIndex(int m_PageIndex) {
		this.m_PageIndex = m_PageIndex;
		updatePage() ;
	}
	synchronized public void setMaxDataCount(int m_MaxDataCount) {
		this.m_MaxDataCount = m_MaxDataCount;
		updatePage() ;
	}
	
	synchronized public int getPageIndex() {
		return m_PageIndex;
	}
	synchronized public int getPageCount() {
		return m_PageCount;
	}
	synchronized public int getMaxDataCount() {
		return m_MaxDataCount;
	}
	


	
	
	
//	-------------------------------------------------------------------------------------------------------------------
//	size get

	public int getColumnCount(){
		return GridColumnHeads.length;
	}
	
	/**
	 * 得到该页能容纳的最多元素。
	 * @return
	 */
	public int getRowCount(){
		return GridRows.length;
	}
	
	
	public Item getCell(int column, int row){
		return (GridRows[row][column]);
	}
	
	public LabelBar getHead(int column){
		return GridColumnHeads[column];
	}

	public final int getWidth()
	{
		return W;
	}
	
	public final int getHeight()
	{
		return H;
	}

//	-----------------------------------------------------------------------------------------------------------------
	
}
