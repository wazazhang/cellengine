package com.g2d.font;

import java.text.AttributedCharacterIterator.Attribute;

public class TextAttribute extends Attribute
{
	private static final long serialVersionUID = 1L;
	
	/** [link:text or url]text[link] */
	final public static TextAttribute LINK			= new TextAttribute("link");
	
	/** [anti:1 or 0]text[anti] */
	final public static TextAttribute ANTIALIASING	= new TextAttribute("anti");

	/** [color:color as AARRGGBB]text[color] */
	final public static TextAttribute FOREGROUND	= new TextAttribute("color");

	/** [back:color as AARRGGBB]text[back] */
	final public static TextAttribute BACKGROUND	= new TextAttribute("back");
	
	/** [size:font_size]text[size] */
	final public static TextAttribute SIZE			= new TextAttribute("size");

	/** [bold]text[bold] */
	final public static TextAttribute BOLD			= new TextAttribute("bold");
	
	/** [font:font name@style@size]text[font] <br> 
	 * @see java.awt.Font*/
	final public static TextAttribute FONT			 = new TextAttribute("font");
	
	/** [under]text[under] */
	final public static TextAttribute UNDERLINE		 = new TextAttribute("under");

	/** [image:path]replacement[image]<br>
	 * 其中的文字将用图片填充*/
	final public static TextAttribute REPLACEMENT	 = new TextAttribute("replacement");

	
	TextAttribute(String name) {
		super("g2d_" + name);
	}
}
