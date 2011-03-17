package com.g2d.display.ui;

import java.awt.Rectangle;

import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.ui.layout.UILayout;

public class Panel extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static int DEFAULT_SCROLL_BAR_SIZE = 12;
	
	ScrollBar.ScrollBarPair		scrollbar;
	
	/** 实际的容器 */
	protected PanelContainer	container;
	/** 视口 */
	protected Pan 				view_port;
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public Panel() 
	{
		enable_mouse_wheel = true;
		
		view_port 	= new Pan();
		container 	= new PanelContainer();
		scrollbar	= new ScrollBar.ScrollBarPair(DEFAULT_SCROLL_BAR_SIZE);

		super.addChild(scrollbar.v_scroll);		
		super.addChild(scrollbar.h_scroll);
		
		view_port.setCustomLayout(UILayout.createBlankRect());
		view_port.setLayout(UILayout.createBlankRect());
		view_port.enable_input = false;
		view_port.addChild(container);
		
		super.addChild(view_port);
	}
	
	/** call getContainer().addChild(DisplayObject child); */
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	/**  call getContainer().removeChild(DisplayObject child); */
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	public Container getContainer() {
		return container;
	}
	
	public void setContainer(PanelContainer container) {
		view_port.removeChild(this.container);
		this.container = container;
		view_port.addChild(this.container);
	}
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		scrollbar.v_scroll.moveInterval(event.scrollDirection);
	}

	public ScrollBar getVScrollBar() {
		return scrollbar.v_scroll;
	}
	public ScrollBar getHScrollBar() {
		return scrollbar.h_scroll;
	}
	
	public int getViewPortWidth() {
		return view_port.getWidth();
	}
	
	public int getViewPortHeight() {
		return view_port.getHeight();
	}
	
	public void setAutoScroll(boolean hScroll, boolean vbScroll) {
		setEnableHScrollBar(hScroll);
		setEnableVScrollBar(vbScroll);
	}
	
	public void setEnableVScrollBar(boolean enable) {
		if (enable) {
			if (super.contains(scrollbar.v_scroll)==false) {
				super.addChild(scrollbar.v_scroll);
			}
		} else {
			if (super.contains(scrollbar.v_scroll)==true) {
				super.removeChild(scrollbar.v_scroll);
			}
		}
		scrollbar.v_scroll.enable = enable;
		enable_mouse_wheel = enable;
	}
	
	public void setEnableHScrollBar(boolean enable) {
		if (enable) {
			if (super.contains(scrollbar.h_scroll)==false) {
				super.addChild(scrollbar.h_scroll);
			}
		} else {
			if (super.contains(scrollbar.h_scroll)==true) {
				super.removeChild(scrollbar.h_scroll);
			}
		}
		scrollbar.h_scroll.enable = enable;
	}
	
//	Rectangle view_rect ;
	
	@Override
	public void update() 
	{
		super.update();

		int sx = layout.BorderSize;
		int sy = layout.BorderSize;
		int sw = getWidth()-(layout.BorderSize<<1);
		int sh = getHeight()-(layout.BorderSize<<1);
		
		Rectangle view_rect = scrollbar.update(this, sx, sy, sw, sh, 
				view_port.getWidth(),  container.local_bounds.width,
				view_port.getHeight(), container.local_bounds.height);
		
		view_port.setBounds(view_rect);
		
		container.setLocation(
				-(int)scrollbar.h_scroll.getValue(), 
				-(int)scrollbar.v_scroll.getValue());
	}
	
	@Override
	protected void updateChilds() 
	{
		super.updateChilds();
	}
	
//	@Override
//	protected void renderAfter(Graphics2D g) {
//		super.renderAfter(g);
//		if (view_rect!=null) {
//			g.setColor(Color.GREEN);
//			g.draw(view_rect);
//		}
//	}

	public class PanelContainer extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public PanelContainer() 
		{
			this.enable_input = false;
		}
		
		public void update() 
		{
			local_bounds.width = view_port.getWidth();
			local_bounds.height = view_port.getHeight();
			for (UIComponent item : getComonents()) {	
				local_bounds.width  = (int)Math.max(local_bounds.width,  item.x + item.getWidth());
				local_bounds.height = (int)Math.max(local_bounds.height, item.y + item.getHeight());
			}
		}
		
	}
	
}
