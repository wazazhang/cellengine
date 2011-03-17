package com.g2d.display.ui;

import java.awt.Rectangle;

import com.g2d.display.DisplayObject;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.ui.layout.UILayout;

public class ScrolledPan extends Container
{
	public static int DEFAULT_SCROLL_BAR_SIZE = 12;
	
	ScrollBar.ScrollBarPair		scrollbar;
	
	/** 实际的容器 */
	UIComponent			view_port_view;
	/** 视口 */
	Pan 				view_port;
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public ScrolledPan(UIComponent view) 
	{
		enable_mouse_wheel = true;

		view_port 	= new Pan();
		scrollbar	= new ScrollBar.ScrollBarPair(DEFAULT_SCROLL_BAR_SIZE);

		super.addChild(scrollbar.v_scroll);		
		super.addChild(scrollbar.h_scroll);
		
		view_port.enable_input = false;
		view_port.setCustomLayout(UILayout.createBlankRect());
		view_port.setLayout(UILayout.createBlankRect());
		super.addChild(view_port);
		
		setViewPortView(view);
	}
	
	public void setViewPortView(UIComponent comp) {
		if (view_port_view != null) {
			view_port.removeComponent(view_port_view);
		}
		view_port_view 	= comp;
		view_port.addChild(view_port_view);
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
		
	@Override
	public void update() 
	{
		super.update();

		int sx = layout.BorderSize;
		int sy = layout.BorderSize;
		int sw = getWidth()-(layout.BorderSize<<1);
		int sh = getHeight()-(layout.BorderSize<<1);
		
		Rectangle view_rect = scrollbar.update(this, sx, sy, sw, sh, 
				view_port.getWidth(),  view_port_view.local_bounds.width,
				view_port.getHeight(), view_port_view.local_bounds.height);
		
		view_port.setBounds(view_rect);
		
		view_port_view.setLocation(
				-(int)scrollbar.h_scroll.getValue(), 
				-(int)scrollbar.v_scroll.getValue());
		view_port_view.setMinimumSize(
				view_port.getWidth(), 
				view_port.getHeight());
	}

	
}
