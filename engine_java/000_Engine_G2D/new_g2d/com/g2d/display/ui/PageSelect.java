package com.g2d.display.ui;

import java.util.Vector;

import com.g2d.Graphics2D;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.layout.UILayout;

public abstract class PageSelect<P extends com.g2d.display.ui.PageSelect.Page> extends UIComponent
{

//	----------------------------------------------------------------------------------------------------------------------------
	
	public int 		page_header_width	= 60;
	
	public int		page_header_height	= 20;
	
	Vector<P>		pages;
	
	P				selected_page;
	
//	----------------------------------------------------------------------------------------------------------------------------

	public PageSelect() 
	{
		pages 			= new Vector<P>();
		enable_input 	= false;
	}
	
	protected abstract P createPage(String text);
	
//	----------------------------------------------------------------------------------------------------------------------------
//	pages
	
	public void selectPage(int index) {
		selectPage(getPage(index));
	}
	
	@SuppressWarnings("unchecked")
	public void selectPage(Page page) {
		if (page!=null && page!=selected_page) {
			P ori_page = selected_page;			
			if (selected_page != null) {
//				selected_page.panel.removeFromParent();
				super.removeChild(selected_page.getPageView());
			}
			selected_page = (P)page;
			super.addChild(selected_page.getPageView());
			onPageSelected(ori_page, selected_page);
		}
	}
	

	public P addPage(P page) {
		if (page!=null) {
			page.setMinimumSize(page_header_width, page_header_height);
			pages.add(page);
			page.page_select = this;
			if (super.addChild(page)) {
				if (selected_page==null) {
					selectPage(page);
				}
				return page;
			}
		}
		return null;
	}
	
	
	public void removePage(P page) {
		if (page!=null){
			if (selected_page==page) {
				super.removeChild(page.getPageView());
				selected_page = null;
			}
			super.removeChild(page);
			pages.remove(page);
		}
	}
	
	
	public P addPage(String text) {
		P ret = createPage(text);
		addPage(ret);
		return ret;
	}
	
	public P getPage(int index) {
		return pages.elementAt(index);
	}
	
	public int getPageIndex(P page)
	{
		return pages.indexOf(page);
	}
	
	public void removePage(int index) {
		removePage(getPage(index));
	}
	
	public void removeAllPage() {
		while (!pages.isEmpty()) {
			removePage(pages.elementAt(0));
		}
	}
	
	public P getSelectedPage(){
		return selected_page;
	}
	
	public Vector<P> getPages(){
		return new Vector<P>(pages);
	}
	
	protected void onPageSelected(P ori_page, P new_page){}
	
	
//	----------------------------------------------------------------------------------------------------------------------------

	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		
	}
	
	@Override
	protected void updateChilds() {
		if (!pages.isEmpty())
		{
			int h = page_header_height;
			int w = Math.min(getWidth()/pages.size(), page_header_width);
			int x = 0;
			for (Page page : pages) {
				page.setSize(w, h);
				page.setLocation(x, 0);
				page.getPageView().setSize(getWidth(), getHeight()-h);
				page.getPageView().setLocation(0, h);
				x += page.getWidth();
			}
		}
		super.updateChilds();
	}
	
	synchronized public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	synchronized public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------


	public abstract static class Page extends Button
	{
		PageSelect<?> page_select;

		public Page(String headText)
		{
			super(headText);
			setSize(60, 20);
		}
		
		/** call getPanel().getContainer().addChild(DisplayObject child); */
		@Deprecated
		final public boolean addChild(DisplayObject child) {
			throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
		}
		/**  call getPanel().getContainer().removeChild(DisplayObject child); */
		@Deprecated
		final public boolean removeChild(DisplayObject child) {
			throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
		}
		
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (page_select != null) {
				page_select.selectPage(this);
			}
		}
		
		public void render(Graphics2D g) {
			UILayout up = custom_layout_up;
			UILayout down = custom_layout_down;
			
			if (page_select.selected_page == this) {
				custom_layout_up = custom_layout_down = (custom_layout_down!=null?custom_layout_down:layout_down);
				super.render(g);
			}else{
				custom_layout_down = custom_layout_up = (custom_layout_up!=null?custom_layout_up:layout_up);
				super.render(g);
			}
			
			custom_layout_up = up;
			custom_layout_down = down;
		}
		
		abstract protected UIComponent getPageView() ;
		
	}
	
	
}
