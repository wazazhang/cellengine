package com.g2d.display.ui.text;

import java.text.AttributedCharacterIterator.Attribute;

public class TextAttribute extends Attribute
{
	final public static TextAttribute LINK = new TextAttribute("link");
	
	final public static TextAttribute ANTIALIASING = new TextAttribute("anti");
	
	TextAttribute(String name) {
		super("g2d_" + name);
	}
}
