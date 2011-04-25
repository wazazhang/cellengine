package com.g2d.display.ui;

import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Hashtable;

import com.cell.CMath;
import com.cell.CObject;
import com.cell.math.TVector;
import com.cell.util.Pair;
import com.g2d.AnimateCursor;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.font.TextAttribute;
import com.g2d.geom.Dimension;
import com.g2d.geom.Point;
import com.g2d.geom.Rectangle;
import com.g2d.text.MultiTextLayout;
import com.g2d.text.TextBuilder;
import com.g2d.text.MultiTextLayout.AttributedSegment;



public class TextBoxBlock extends UIComponent
{
	public static int SCROLL_BAR_SIZE = 12;
	
	public Color 						textColor			= new Color(0xffffffff);
	public boolean						is_show_link;
	protected ScrollBar					v_scrollbar			= ScrollBar.createVScroll(SCROLL_BAR_SIZE);
	protected boolean					v_scroll_left_dock	= false;
	private boolean						enable_scrollbar	= true;
	private boolean						enable_show_select	= true;
	
	private Rectangle 					view_port_rect		= new Rectangle();

	
	public int							text_shadow_x		= 0;
	public int							text_shadow_y		= 0;
	public float						text_shadow_alpha	= 1f;
	public int							text_shadow_rgb		= 0xff000000;
	
	private Dimension					text_size 			= new Dimension();
		
	private ArrayList<Pair<MultiTextLayout, Rectangle>>	
										texts = new ArrayList<Pair<MultiTextLayout, Rectangle>>();

	private Hashtable<Attribute, TextClickSegmentListener> 
										click_segment_listeners;
	
	private HitInfo						selected_text;
	
//	-------------------------------------------------------------------------------------------------------------------
	
	public TextBoxBlock()
	{
		this.enable_key_input	= false;
		this.enable_mouse_wheel	= true;
		super.addChild(v_scrollbar);
		this.addClickSegmentListener(TextAttribute.LINK, 
				new TextClickSegmentListener.URLClickSegmentListener());
	}
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}

	
//	-------------------------------------------------------------------------------------------------------------
	
	public void setEnableScrollBar(boolean vs) {
		this.enable_scrollbar = vs;
		if (!vs) {
			if (super.contains(v_scrollbar)) {
				super.removeChild(v_scrollbar);
			}
		} else {
			if (!super.contains(v_scrollbar)) {
				super.addChild(v_scrollbar);
			}
		}
	}
	
	public void setShowSelect(boolean show) {
		this.enable_show_select = show;
		for (Pair<MultiTextLayout, Rectangle> pair : texts) {
			pair.getKey().setShowSelect(show);
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------
	
	public void clearText() {
		texts.clear();
	}

	public void appendLine(String text) {
		MultiTextLayout mtext = getEngine().createMultiTextLayout();
		mtext.setShowCaret(false);
		mtext.setShowSelect(enable_show_select);
		mtext.setText(text);
		texts.add(new Pair<MultiTextLayout, Rectangle>(mtext, null));
	}
	
	public void appendLine(AttributedString atext) {
		MultiTextLayout mtext = getEngine().createMultiTextLayout();
		mtext.setShowCaret(false);
		mtext.setShowSelect(enable_show_select);
		mtext.setText(atext);
		texts.add(new Pair<MultiTextLayout, Rectangle>(mtext, null));
	}
	
	public int getLineCount() {
		return texts.size();
	}
	
	public void removeLine(int index) {
		texts.remove(index);
	}
	
	public MultiTextLayout getLine(int index) {
		return texts.get(index).getKey();
	}

//	public Pair<MultiTextLayout, Rectangle> getLineInfo(int index) {
//		return texts.get(index);
//	}

	public MultiTextLayout getLine(int x, int y)
	{
		HitInfo hit = getHitInfo(x, y);
		if (hit != null) {
			return hit.text;
		}
		return null;
	}
	
//	public Pair<MultiTextLayout, Rectangle> getLineInfo(int x, int y) 
//	{
//		if (local_bounds.contains(x, y)) {
//			HitInfo hit = getHitInfo(x, y);
//			if (hit != null) {
//				return new Pair<MultiTextLayout, Rectangle>(hit.text, hit.text_rect);
//			}
//		}
//		return null;
//	}

	public HitInfo getHitInfo(int x, int y) 
	{
		if (local_bounds.contains(x, y)) {
			int scroll_v = (int) v_scrollbar.getValue();
			y = y - view_port_rect.y + scroll_v;
			x = x - view_port_rect.x;
			for (Pair<MultiTextLayout, Rectangle> pair : texts)
			{
				Rectangle text_rect = pair.getValue();
				if (text_rect != null) 
				{
					if (text_rect.contains(x, y)) 
					{
						x -= text_rect.x;
						y -= text_rect.y;
						return new HitInfo(pair.getKey(), text_rect, new Point(x, y));
					}
				}
			}
		}
		return null;
	}
	
//	private int getTextPosition(MultiTextLayout text, Rectangle rect, int x, int y) 
//	{
//		int scroll_v = (int) v_scrollbar.getValue();
//		y = y - view_port_rect.y + scroll_v;
//		x = x - view_port_rect.x;
//		x -= rect.x;
//		y -= rect.y;
//		return text.pointToPosition(x, y);
//	}
	
	private Point getTextPoint(MultiTextLayout text, Rectangle rect, int x, int y) 
	{
		int scroll_v = (int) v_scrollbar.getValue();
		y = y - view_port_rect.y + scroll_v;
		x = x - view_port_rect.x;
		x -= rect.x;
		y -= rect.y;
		return new Point(x, y);
	}

	public class HitInfo 
	{
		MultiTextLayout text;
		Rectangle text_rect;
		Point point;
		public HitInfo(MultiTextLayout text, Rectangle text_rect, Point point) {
			this.text = text;
			this.text_rect = text_rect;
			this.point = point;
		}
	}

//	-------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * @see MultiTextLayout
	 * @param attribute
	 * @param value
	 * @param x
	 * @param y
	 * @return
	 */
	public AttributedSegment getSegment(Attribute attribute, Object value, int x, int y) 
	{
		HitInfo hit = getHitInfo(x, y);
		if (hit != null) {
			AttributedSegment segment = hit.text.getSegment(
					hit.text.pointToPosition(hit.point.x, hit.point.y), 
					attribute, value);
			return segment;
		}
		return null;
	}
	
	/**
	 * @see MultiTextLayout
	 * @param attribute
	 * @param x
	 * @param y
	 * @return
	 */
	public AttributedSegment getSegment(Attribute attribute, int x, int y) {
		HitInfo hit = getHitInfo(x, y);
		if (hit != null) {
			AttributedSegment segment = hit.text.getSegment(
					hit.text.pointToPosition(hit.point.x, hit.point.y), 
					attribute);
			return segment;
		}
		return null;
	}

	protected void onMouseDown(MouseEvent event) 
	{
		selected_text = getHitInfo(getMouseX(), getMouseY());
		if (selected_text != null) {
			selected_text.text.setCaret(selected_text.point.x, selected_text.point.y);
			for (Pair<MultiTextLayout, Rectangle> pair : texts) {
				if (pair.getKey() != selected_text.text) {
					pair.getKey().clearSelectText();
				}
			}
		}
		if (click_segment_listeners != null) {
			for (Attribute attribute : click_segment_listeners.keySet()) {
				AttributedSegment segment = getSegment(attribute, getMouseX(), getMouseY());
				if (segment != null) {
					TextClickSegmentListener listener = click_segment_listeners.get(attribute);
					if (listener != null) {
						listener.segmentClicked(event, this, segment);
					}
				}
			}
		}
	}
	
	protected void onMouseDraged(MouseMoveEvent event) {
		//System.out.println("TextBox onMouseDraged");
//		text.dragCaret(getMouseX()-text_draw_x, getMouseY()-text_draw_y);
		if (selected_text != null) {
			Point dpoint = getTextPoint(
					selected_text.text, 
					selected_text.text_rect, 
					getMouseX(), getMouseY());
			selected_text.text.dragCaret(dpoint.x, dpoint.y);
		}
	}
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		//System.out.println(" mouseWheelMoved");
		v_scrollbar.moveInterval(event.scrollDirection);
	}

	protected void onKeyTyped(KeyEvent event) {
//		if (!is_readonly || event.keyChar == MultiTextLayout.CHAR_COPY) {
//			text.insertChar(event.keyChar);
//		}
	}
		

	public void addClickSegmentListener(Attribute attribute, TextClickSegmentListener listener)
	{
		if (attribute != null) {
			if (click_segment_listeners == null) {
				click_segment_listeners = new Hashtable<Attribute, TextClickSegmentListener>();
			}
			click_segment_listeners.put(attribute, listener);
		}
	}
	
	public void clearClickSegmentListener()
	{
		this.click_segment_listeners.clear();
	}
	
	@Override
	public AnimateCursor getCursor() {
		if (CMath.includeRectPoint(
				layout.BorderSize, 
				layout.BorderSize, 
				getWidth()-(layout.BorderSize<<1), 
				getHeight()-(layout.BorderSize<<1), 
				getMouseX(), getMouseY())) {
			if (is_show_link) {
				AttributedSegment segment = getSegment(
						com.g2d.font.TextAttribute.LINK, 
						getMouseX(), getMouseY());
				if (segment!=null) {
					return AnimateCursor.HAND_CURSOR;
				}
			}
			return AnimateCursor.TEXT_CURSOR;
		}
		return super.getCursor();
	}
	
	public ScrollBar getVScrollBar() {
		return v_scrollbar;
	}
	
	@Override
	protected void updateChilds() 
	{
		int sw = getWidth() -(layout.BorderSize<<1);
		int sh = getHeight()-(layout.BorderSize<<1);
		
		if (enable_scrollbar) {
			v_scrollbar.setMax(Math.max(text_size.height, sh));
			v_scrollbar.setValue(v_scrollbar.getValue(), sh);
		} else {
			v_scrollbar.setMax(Math.max(text_size.height, sh));
			v_scrollbar.setValue(0, sh);
		}
		
		
		view_port_rect.x = layout.BorderSize;
		view_port_rect.y = layout.BorderSize;
		view_port_rect.height = sh;
		
		if (enable_scrollbar) {
			view_port_rect.width = sw - v_scrollbar.size;
			if (v_scroll_left_dock) {
				view_port_rect.x = layout.BorderSize + v_scrollbar.size;
				v_scrollbar.setLocation(layout.BorderSize, layout.BorderSize);
			} else {
				v_scrollbar.setLocation(getWidth()-layout.BorderSize-v_scrollbar.size, layout.BorderSize);
			}
			v_scrollbar.setSize(v_scrollbar.size, sh);
		} else {
			view_port_rect.width = sw;
		}
		
		
	
		
		super.updateChilds();
	}
	
	public void update() 
	{
		super.update();
		
	}
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		g.pushClip();
		try {
			drawText(g, 
					view_port_rect.x, 
					view_port_rect.y, 
					view_port_rect.width, 
					view_port_rect.height);
		} finally {
			g.popClip();
		}
	}

	private void drawText(Graphics2D g, int x, int y, int w, int h)
	{
		int 	scroll_v 		= (int)v_scrollbar.getValue();
		int		tw				= w - (enable_scrollbar ? v_scrollbar.size : 0);
		boolean	refresh_text 	= text_size.width != tw;

		text_size.width 		= tw;
		text_size.height 		= 0;
		
		for (Pair<MultiTextLayout, Rectangle> pair : texts)
		{
			MultiTextLayout text = pair.getKey();
			text.setWidth(text_size.width);
			Rectangle rect = pair.getValue();
			if (refresh_text || rect == null) {
				Dimension size = text.getDrawTextDimension(g);
				rect = new Rectangle(0, text_size.height, size.width, size.height);
				pair.setValue(rect);
			}
			rect.y = text_size.height;
			text_size.height += text.getHeight();
		}

		g.setColor(textColor);
		for (Pair<MultiTextLayout, Rectangle> pair : texts)
		{
			MultiTextLayout text = pair.getKey();
			Rectangle text_rect = pair.getValue();
			
			int tsx = x + text_rect.x;
			int tsy = y + text_rect.y - scroll_v;
			
			if (CMath.intersectRect2(
					tsx, tsy, text_rect.width, text_rect.height, 
					x, y, w, h)) 
			{
				text.drawText(g, 
						tsx, tsy, 
						0, 0,
						text_rect.width, 
						text_rect.height, 
						text_shadow_x, 
						text_shadow_y,
						text_shadow_alpha, 
						text_shadow_rgb);
			}
		}
	}

	
	

}
