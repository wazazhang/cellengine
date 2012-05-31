package com.g2d.display.ui;


import com.g2d.AnimateCursor;
import com.g2d.Color;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.font.GraphicAttributeShape;
import com.g2d.font.TextAttribute;
import com.g2d.geom.Ellipse;
import com.g2d.text.MultiTextLayout;

public class TextBoxSingle extends UIComponent
{	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient int 						text_draw_w;
	
	transient protected MultiTextLayout	text				= Engine.getEngine().createMultiTextLayout();
	
	/**文字颜色*/
	@Property("文字颜色")
	public Color 						textColor			= new Color(0xffffffff);
	
	@Property("是否不可编辑")
	public boolean 						is_readonly			= false;
	
	/** 光标超出范围的偏移量 */
	transient int 						xoffset = 0;

	/**是否显示为密码*/
	private boolean 					is_password 		= false;
	
	@Property("是否显示为密码")
	public boolean isPassword = false;
	
	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
	public boolean	enable_antialiasing	 = false;
	
	public TextBoxSingle() {
		this("TextBoxSingle");
	}
	
	public TextBoxSingle(String text)
	{
		this(text, 100, 25);
	}
	
	public TextBoxSingle(String text, int width, int height)
	{
		enable_key_input	= true;
		enable_mouse_wheel	= true;
		this.text.setSingleLine(true);
		this.setSize(width, height);
		this.setText(text);
	}

	public MultiTextLayout getTextLayout() {
		return text;
	}
	
	@Override
	public AnimateCursor getCursor() {
		return AnimateCursor.TEXT_CURSOR;
	}

	public void setText(String text) {
		this.text.setText(text);
		encode();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
		encode();
	}
	
	public void insertCharAtCurrentCaret(char ch){
		if (!is_readonly) {
			if (is_password && (
					ch == MultiTextLayout.CHAR_COPY || 
					ch == MultiTextLayout.CHAR_CUT || 
					ch == MultiTextLayout.CHAR_PASTE)) {
				return;
			}
			text.insertChar(ch);
			encode();
		}
	}

	public void setTextPassword(boolean b) {
		this.is_password = b;
		this.isPassword = b;
		this.setText(text.getText());
		encode();
	}
	
	public boolean getTextPassword() {
		return this.is_password;
	}

	public String getText() {
		return this.text.getText();
	}
	
	protected void onMouseDown(MouseEvent event) {
		text.setCaret(
				getMouseX()-text_draw_x + xoffset, 
				getMouseY()-text_draw_y);
	}
	
	protected void onMouseDraged(MouseMoveEvent event) {
		text.dragCaret(
				getMouseX()-text_draw_x + xoffset, 
				getMouseY()-text_draw_y);
	}
	
	protected void onKeyTyped(KeyEvent event) {
		insertCharAtCurrentCaret(event.keyChar);
	}
	
	protected void onKeyDown(KeyEvent event) {
		if (event.keyCode == java.awt.event.KeyEvent.VK_LEFT) {
			text.moveCaretPosition(-1);
		} else if (event.keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
			text.moveCaretPosition(1);
		}
	}
	
	public void update() 
	{
		super.update();
		
		if (isPassword != is_password) {
			setTextPassword(isPassword);
		}
		
		if (is_readonly) {
			text.setShowCaret(false);
		} else if (getRoot() != null && !getRoot().isFocusOwner()) {
			text.setShowCaret(false);
		} else if (!isFocusedComponent()) {
			text.setShowCaret(false);
		} else {
			text.setShowCaret(true);
		}
		
		
		int caret = text.getCaretPosX();
		
		UILayout layout = getLayout();
		
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize;
		
		text_draw_w = getWidth() - (layout.BorderSize<<1) - 2;
		
		if (text.getWidth() > text_draw_w) {
			if (xoffset + text_draw_w < caret) {
				xoffset = caret - text_draw_w;
			}
			else if (xoffset > caret) {
				xoffset = caret;
			}
		}else{
			xoffset = 0;
		}
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		g.setColor(textColor);
		boolean flag = g.setFontAntialiasing(enable_antialiasing);
		try {
			UILayout layout = getLayout();
			text.drawText(g, 
					text_draw_x - xoffset, 
					text_draw_y, xoffset, 
					0, 
					text_draw_w + 2, 
					getHeight() - layout.BorderSize*2);
		} finally {
			g.setFontAntialiasing(flag);
		}
	}

	protected void encode() {
		if (is_password) {
			String show = text.getText();
			if (!show.isEmpty()) {
				UILayout layout = getLayout();
				int height = getHeight() - layout.BorderSize*2;
				Ellipse shape = new Ellipse(0, 0, height/2, height/2);
				text.putAttribute(
						TextAttribute.REPLACEMENT, 
						new GraphicAttributeShape(shape, GraphicAttributeShape.BOTTOM_ALIGNMENT, false), 
						0, show.length());
			}
		}
	}
}
