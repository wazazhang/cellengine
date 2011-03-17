package com.g2d.text;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.Hashtable;

import com.cell.util.EnumManager.ValueEnum;
import com.g2d.font.TextAttribute;

public enum Instruction implements ValueEnum<String>
{
	/** [color:color as AARRGGBB]text[color] */
	COLOR			("color",	TextAttribute.FOREGROUND),

	/** [back:color as AARRGGBB]text[back] */
	BACK_COLOR		("back",	TextAttribute.BACKGROUND),
	
	/** [size:font_size]text[size] */
	SIZE			("size",	TextAttribute.SIZE),

	/** [bold]text[bold] */
	BOLD			("bold",	TextAttribute.BOLD),
	
	/** [font:font name@style@size]text[font] <br> 
	 * @see java.awt.Font*/
	FONT			("font",	TextAttribute.FONT),
	
	/** [under]text[under] */
	UNDERLINE		("under",	TextAttribute.UNDERLINE),
	
	/** [link:text or url]text[link] */
	LINK			("link",	TextAttribute.LINK),
	
	/** [anti:1 or 0]text[anti] */
	ANTIALIASING	("anti",	TextAttribute.ANTIALIASING, true),

	/** [image:path]replacement[image]<br>
	 * 其中的文字将用图片填充*/
	IMAGE			("image",	TextAttribute.REPLACEMENT),
	
	/** [sprite:path@sprite name@animate]replacement[sprite]<br>
	 * 其中的文字将用精灵填充*/
	SPRITE			("sprite",	TextAttribute.REPLACEMENT),
	;

	final public String		value;
	final public Attribute	attribute;
	final public boolean	is_single;
	
	Instruction(String v, Attribute attr) {
		this(v, attr, false);
	}
	
	Instruction(String v, Attribute attr, boolean is_single) {
		this.value 		= v;
		this.attribute 	= attr;
		this.is_single 	= is_single;
	}
	
	public String getValue() {
		return value;
	}
	
	private static Hashtable<String, Instruction> instractions = null;

	static public Instruction getInstraction(String string)
	{
		if (instractions == null) {
			instractions = new Hashtable<String, Instruction>();
			for (Instruction ins : Instruction.values()) {
				instractions.put(ins.value, ins);
			}
		}
		return instractions.get(string);
	}
}
