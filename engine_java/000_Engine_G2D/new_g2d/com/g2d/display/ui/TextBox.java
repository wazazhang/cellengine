package com.g2d.display.ui;

import java.awt.Rectangle;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Hashtable;

import com.cell.CMath;
import com.cell.CObject;
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
import com.g2d.text.MultiTextLayout;
import com.g2d.text.TextBuilder;
import com.g2d.text.MultiTextLayout.AttributedSegment;



public class TextBox extends UIComponent
{

	public static int SCROLL_BAR_SIZE = 12;
	
	public Color 						textColor			= new Color(0xffffffff);
	public boolean 						is_readonly			= false;
	public boolean						is_show_link;
	protected ScrollBar					v_scrollbar			= ScrollBar.createVScroll(SCROLL_BAR_SIZE);
	protected boolean					v_scroll_left_dock	= false;
	private boolean						enable_scrollbar	= true;
	
	/**文字是否抗锯齿*/
	public boolean						enable_antialiasing	 = false;

	public int							text_shadow_x		= 0;
	public int							text_shadow_y		= 0;
	public float						text_shadow_alpha	= 1f;
	public int							text_shadow_rgb		= 0xff000000;

//	-------------------------------------------------------------------------------------------------------------------
	
	private int 						text_draw_x;
	private int 						text_draw_y;
	private Rectangle 					view_port_rect		= new Rectangle();
	
	protected MultiTextLayout text = Engine.getEngine().createMultiTextLayout();

	transient Hashtable<Attribute, TextClickSegmentListener> click_segment_listeners;
	
	
//	-------------------------------------------------------------------------------------------------------------------
	
	public TextBox() 
	{
		this("");
	}
	
	public TextBox(String text)
	{
		this(text, 100, 100);
	}
	
	public TextBox(String text, int w, int h)
	{
		enable_key_input	= true;
		enable_mouse_wheel	= true;
		super.addChild(v_scrollbar);
		
		this.text.setText(text);
		this.setSize(w, h);		
		
		this.addClickSegmentListener(TextAttribute.LINK, 
				new TextClickSegmentListener.URLClickSegmentListener());
	}
	
	public MultiTextLayout getTextLayout() {
		return text;
	}
	
	public void setEnableScrollBar(boolean vs) {
		enable_scrollbar = vs;
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
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
		
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public void setText(String text, boolean buildScript) {
		if (buildScript) {
			this.text.setText(TextBuilder.buildScript(text));
		} else {
			setText(text);
		}
	}
	
	public void setText(AttributedString atext) {
		this.text.setText(atext);
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
	}
	
	public void appendText(AttributedString atext) {
		this.text.appendText(atext);
	}

	public void appendLine(String text) {
		this.text.appendText(text+"\n");
	}
	
	public void appendLine(AttributedString atext) {
		this.text.appendText(Tools.linkAttributedString(atext, new AttributedString("\n")));
	}
	
	protected void onMouseDown(MouseEvent event) {
//		System.out.println("TextBox onMouseDown");
		text.setCaret(getMouseX()-text_draw_x, getMouseY()-text_draw_y);
//		text.getSelectedSegment(AttributedCharacterIterator.Attribute.INPUT_METHOD_SEGMENT);
		if (click_segment_listeners != null) {
			int position = text.pointToPosition(getMouseX() - text_draw_x, getMouseY() - text_draw_y);
			for (Attribute attribute : click_segment_listeners.keySet()) {
				TextClickSegmentListener listener = click_segment_listeners.get(attribute);
				if (listener != null) {
					AttributedSegment segment = text.getSegment(position, attribute);
					if (segment != null) {
						listener.segmentClicked(event, this, segment);
					}
				}
			}
		}
	}
	
	/**
	 * @see MultiTextLayout
	 * @param attribute
	 * @param value
	 * @param x
	 * @param y
	 * @return
	 */
	public AttributedSegment getSegment(Attribute attribute, Object value, int x, int y) {
		int position = text.pointToPosition(x - text_draw_x, y - text_draw_y);
		AttributedSegment segment = text.getSegment(position, attribute, value);
		return segment;
	}
	
	/**
	 * @see MultiTextLayout
	 * @param attribute
	 * @param x
	 * @param y
	 * @return
	 */
	public AttributedSegment getSegment(Attribute attribute, int x, int y) {
		int position = text.pointToPosition(x - text_draw_x, y - text_draw_y);
		AttributedSegment segment = text.getSegment(position, attribute);
		return segment;
	}
	
	protected void onMouseDraged(MouseMoveEvent event) {
		//System.out.println("TextBox onMouseDraged");
		text.dragCaret(getMouseX()-text_draw_x, getMouseY()-text_draw_y);
	}
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		//System.out.println(" mouseWheelMoved");
		if (enable_scrollbar) {
			v_scrollbar.moveInterval(event.scrollDirection);
		}
	}
	
	protected void onKeyTyped(KeyEvent event) {
		if (!is_readonly || event.keyChar == MultiTextLayout.CHAR_COPY) {
			text.insertChar(event.keyChar);
		}
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
				AttributedSegment segment = text.getSegment(
						text.pointToPosition(getMouseX()-text_draw_x, getMouseY()-text_draw_y), 
						com.g2d.font.TextAttribute.LINK);
				if (segment!=null) {
					return AnimateCursor.HAND_CURSOR;
				}
			}
			return AnimateCursor.TEXT_CURSOR;
		}
		return super.getCursor();
	}
	

	public Rectangle getTextViewPortRectangle() {
		return view_port_rect;
	}
	
	
	public ScrollBar getVScrollBar() {
		return v_scrollbar;
	}
	
	public void update() 
	{
		if (is_readonly) {
			text.setShowCaret(false);
		} else if (getRoot() != null && !getRoot().isFocusOwner()) {
			text.setShowCaret(false);
		} else if (!isFocusedComponent()) {
			text.setShowCaret(false);
		} else {
			text.setShowCaret(true);
		}
		
		{
			
			int sw = getWidth() -(layout.BorderSize<<1);
			int sh = getHeight()-(layout.BorderSize<<1);
			if (enable_scrollbar) {
				v_scrollbar.setMax(Math.max(text.getHeight(), sh));
				v_scrollbar.setValue(v_scrollbar.getValue(), sh);
			} else {
				v_scrollbar.setMax(Math.max(text.getHeight(), sh));
				v_scrollbar.setValue(0, sh);
			}
			
			
			view_port_rect.x = layout.BorderSize;
			view_port_rect.y = layout.BorderSize;
			view_port_rect.width = text.getWidth();
			view_port_rect.height = sh;
			
			if (enable_scrollbar) {
				if (v_scroll_left_dock) {
					view_port_rect.x = layout.BorderSize + v_scrollbar.size;
					v_scrollbar.setLocation(layout.BorderSize, layout.BorderSize);
				} else {
					v_scrollbar.setLocation(getWidth()-layout.BorderSize-v_scrollbar.size, layout.BorderSize);
				}
				v_scrollbar.setSize(v_scrollbar.size, sh);
				text.setWidth(sw-v_scrollbar.size);
				text_draw_x = view_port_rect.x;
				text_draw_y = view_port_rect.y - (int)v_scrollbar.getValue();
			}
			else {
				text.setWidth(sw);
				text_draw_x = view_port_rect.x;
				text_draw_y = view_port_rect.y;
			}
			
		}
		
		super.update();
		
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		{
			int tsx = 0;
			int tsy = (int)v_scrollbar.getValue();
			int tsw = text.getWidth();
			int tsh = (int)v_scrollbar.getValueLength();
			
			if (enable_scrollbar) {
				tsy = (int)v_scrollbar.getValue();
			} else {
				tsy = 0;
			}
			
			boolean flag = g.setFontAntialiasing(enable_antialiasing);
			try {
				g.setColor(textColor);
				text.drawText(g, 
						text_draw_x, text_draw_y, 
						tsx, tsy, tsw, tsh, 
						text_shadow_x, text_shadow_y, text_shadow_alpha, text_shadow_rgb);
			} finally {
				g.setFontAntialiasing(flag);
			}
		}
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
	
//	public void addClickSegmentListener(String instruction, ClickSegmentListener listener)
//	{
//		Instruction.getInstraction(instruction);
//		addClickSegmentListener(, listener);
//	}
	
//	/**
//	 * 用于监听特殊属性的字符段被点击的接口
//	 * @author WAZA
//	 */
//	public static interface ClickSegmentListener extends EventListener
//	{
//		public void segmentClicked(MouseEvent event, TextBox textbox, AttributedSegment segment);
//	}
//	
//	public static class URLClickSegmentListener implements ClickSegmentListener
//	{
//		@Override
//		public void segmentClicked(MouseEvent event, TextBox textbox, AttributedSegment segment) {
//			if (event.type == MouseEvent.EVENT_MOUSE_DOWN) {
//				String url = segment.attribute_value + "";
//				if (url.startsWith("http://")) {
//					CObject.getAppBridge().openBrowser(url);
//				}
//			}
//		}
//	}
}
