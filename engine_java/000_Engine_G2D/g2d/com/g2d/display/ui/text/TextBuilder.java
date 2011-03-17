package com.g2d.display.ui.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.script.objective.IObjectiveFactory;
import com.cell.script.objective.Objective;

/** 
 * 通过在字符串中嵌入代码，构造样式文本。<br>
 * [color:color as AARRGGBB]text[color]<br>
 * [back:color as AARRGGBB]text[back]<br>
 * [size:font size]text[size]<br>
 * [font:font name@style@size]text[font]<br>
 * [under]text[under]<br>
 * [link:text or url]text[link]<br>
 * [anti:1 or 0]<br>
 * @see com.g2d.display.ui.text.TextBuilder.Instruction
 */
public class TextBuilder extends IObjectiveFactory
{
	public static TextBuilderAdapter DEFAULT_TEXT_BUILDER_ADAPTER = null;
	
	public static AttributedString buildScript(String script)
	{
		if (script!=null){
			return new TextBuilder(script).attributed_text;
		} 
		return null;
	}
	
	public static AttributedString buildScript(String script, TextBuilderAdapter adapter)
	{
		if (script!=null){
			return new TextBuilder(adapter, script).attributed_text;
		} 
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	TextBuilderAdapter 									adapter;
	AttributedString 									attributed_text;
	Hashtable<Instruction, Stack<InstructionObjective>> CallStacks;
	Vector<InstructionObjective> 						ObjectQueue;

//	-----------------------------------------------------------------------------------------------------------------------------------
	
	public TextBuilder(String script)
	{
		this(DEFAULT_TEXT_BUILDER_ADAPTER, script);
	}

	public TextBuilder(TextBuilderAdapter adapter, String script) 
	{
		if (script.length()>3)
		{
			this.adapter 		= adapter;
			this.CallStacks		= new Hashtable<Instruction, Stack<InstructionObjective>>();
			this.ObjectQueue	= new Vector<InstructionObjective>();

			build(script);
			
			if (!ObjectQueue.isEmpty())
			{
				try
				{
					attributed_text = new AttributedString(getBuildState());
					for (InstructionObjective item : ObjectQueue) {
						for (int i = 0; i < item.Attr.length; i++) {
							if (item.Attr[i] != null) {
								attributed_text.addAttribute(
										item.Attr[i],
										item.AttrValue[i], 
										item.AttrStart,
										item.AttrEnd);
							}
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
		
		attributed_text = new AttributedString(script);
	}

	final Stack<InstructionObjective> getStack(Instruction ins) 
	{
		Stack<InstructionObjective> stack = CallStacks.get(ins);
		if (stack == null) {
			stack = new Stack<InstructionObjective>();
			CallStacks.put(ins, stack);
		}
		return stack;
	}
	
	@Override
	final public InstructionObjective getObjective(String src, int begin, int end, String key, String value) 
	{
		// 对应的 TextAttribute
		Instruction ins = Instruction.getInstraction(key);
		
		if (ins != null)
		{
			InstructionObjective objective = null;
			
			if (ins.is_single) 
			{
				objective = getInstructionValue(ins, key, value);
				objective.AttrStart	= getBuildState().length();
				objective.AttrEnd	= getBuildState().length() + 1;
				ObjectQueue.addElement(objective);
			} 
			else 
			{
				// 该 TextAttribute对应的堆栈
				Stack<InstructionObjective> stack = getStack(ins);
				
				objective = stack.isEmpty() ? null : stack.pop();
				
				// 如果 以前没有该类型的数据在堆栈中，则代表为数据头
				if (objective == null)
				{
					objective = getInstructionValue(ins, key, value);
					objective.AttrStart	= getBuildState().length();
					// 入栈
					stack.push(objective);
				}
				else 
				{
					// 出栈上一个匹配值
					objective.AttrEnd		= getBuildState().length();
					// 添加到对象集
					ObjectQueue.addElement(objective);
				}
			}
			
			return objective;
		}
		else
		{
//			System.err.println("unknow instruction : " + key + " : " + value);
		}
		
		return null;
	}

	protected InstructionObjective getInstructionValue(Instruction instruction, String key, String value) 
	{
		try
		{
			if (instruction != null)
			{
				Attribute attr = instruction.attribute;
				
				switch (instruction) {
				case COLOR: {
					return new InstructionObjective(attr, 
							new Color((int)Long.parseLong(value, 16) & 0x00ffffffff, true));
				}
				case BACK_COLOR: {
					return new InstructionObjective(attr, 
							new Color((int)Long.parseLong(value, 16) & 0x00ffffffff, true));
				}
				case SIZE: {
					return new InstructionObjective(attr, 
							new Integer(Integer.parseInt(value)));
				}
				case BOLD: {
					return new InstructionObjective(attr, 
							TextAttribute.WEIGHT_BOLD);
				}
				case UNDERLINE: {
					return new InstructionObjective(attr, 
							TextAttribute.UNDERLINE_ON);
				}
				case FONT: {
					String[] fs = CUtil.splitString(value, "@");
					return new InstructionObjective(attr, 
							new Font(fs[0], Integer.parseInt(fs[1]), Integer.parseInt(fs[2])));
				}
				case LINK: {
					return new InstructionObjective(attr, value);
				}
				case ANTIALIASING: {
					return new InstructionObjective(attr, Integer.parseInt(value));
				}
				// graphics adapter
				case IMAGE: {
					if (adapter != null) {
						return new InstructionObjective(attr, 
								adapter.createGraphicAttribute(instruction, key, value));
					}
				}
				case SPRITE: {
					if (adapter != null) {
						return new InstructionObjective(attr, 
								adapter.createGraphicAttribute(instruction, key, value));
					}
				}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("getInstructionValue : key=" + key + " : value=" + value);
		}
		
		return null;
	}
	
	final public AttributedString getAttributedText() 
	{
		return attributed_text;
	}
	
	final public String getText() {
		return getBuildState();
	}
	
	@Override
	final public String toString() {
		return getBuildState();
	}

//	-----------------------------------------------------------------------------------------------------------------------------------
	
	protected class InstructionObjective extends Objective<Attribute[]>
	{
		public Attribute[]	Attr;
		public Object[] 	AttrValue;
		int					AttrStart;
		int					AttrEnd;
		
		public InstructionObjective(Attribute attr, Object attrvalue) {
			Attr = new Attribute[] { attr };
			AttrValue = new Object[] { attrvalue };
		}

		public InstructionObjective(Attribute[] attrs, Object[] attrvalues) {
			Attr = attrs;
			AttrValue = attrvalues;
		}
		
		public Attribute[] getValue()
		{
			return Attr;
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------

	public static String toString(AttributedString texta)
	{
		StringBuilder sb = new StringBuilder();
		CharacterIterator iter = texta.getIterator();
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static AttributedString concat(AttributedString ... texts) 
	{
		StringBuilder dst_text = new StringBuilder();
		for (AttributedString atext : texts) {
			dst_text.append(toString(atext));
		}
		int i=0;
		AttributedString ret = new AttributedString(dst_text.toString());
		for (AttributedString atext : texts) {
			AttributedCharacterIterator ita = atext.getIterator();
			for (char c = ita.first(); c != CharacterIterator.DONE; c = ita.next()) {
				ret.addAttributes(ita.getAttributes(), i, i+1);
				i ++;
			}
		}
		return ret;
	}

	public static AttributedString linkAttributedString(AttributedString texta, AttributedString textb)
	{
		return concat(texta, textb);
	}
	
	/**
	 * @param src
     * @param start   the beginning index, inclusive.
     * @param end     the ending index, exclusive.
	 * @return 
	 */
	public static AttributedString subString(AttributedString src, int start, int end)
	{
		String dst_text = toString(src);
		dst_text = dst_text.substring(start, end);
		AttributedString ret = new AttributedString(dst_text);
		int src_i = 0;
		int dst_i = 0;
		AttributedCharacterIterator ita = src.getIterator();
		for (char c = ita.first(); c != CharacterIterator.DONE; c = ita.next()) {
			if (src_i >= start && src_i < end) {
				ret.addAttributes(ita.getAttributes(), dst_i, dst_i+1);
				dst_i ++;
			}
			src_i ++;
		}
		return ret;
	}
	
}