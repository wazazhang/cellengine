package com.cell.gfx;

import java.util.Collection;
import java.util.Vector;

public class GfxUtil {

	static public String trimAttributedString(String text, Vector<IGraphics.StringAttribute> attrs)
	{
		return GfxUtil.trimAttributeString(text, attrs);
	}

	static public String trimAttributedString(String text, IGraphics.StringAttribute[] attrs) 
	{
		if (text == null) return text;
		if (attrs == null) return text.trim();
		if (attrs.length==0) return text.trim();
		
		// adjust head
		{
			int h = 0;
			for (; h<text.length(); h++) {
				char ch = text.charAt(h);
				if (ch>' ') {
					break;
				}
			}
			if (h!=0) {
				for (IGraphics.StringAttribute attr : attrs) {
					attr.Start = Math.max(0, attr.Start-h);
					attr.End = Math.max(0, attr.End-h);
				}
				text = text.substring(h);
			}
		}
		
		// adjust end
		{
			int len = text.length();
			text = text.trim();
			if (len != text.length()) {
				len = len - text.length();
				for (IGraphics.StringAttribute attr : attrs) {
					attr.Start = Math.min(text.length(), attr.Start);
					attr.End = Math.min(text.length(), attr.End);
				}
			}
		}
		
		return text;
	}

	static public String[] toStringMultiline(IGraphics g, String text, int width){
		return g.getStringLines(text, width, null);
	}

	public static String trimAttributeString(String text, Collection<IGraphics.StringAttribute> attrs)
	{
		if (text == null) return text;
		if (attrs == null) return text.trim();
		if (attrs.size()==0) return text.trim();
		
		// adjust head
		{
			int h = 0;
			for (; h<text.length(); h++) {
				char ch = text.charAt(h);
				if (ch>' ') {
					break;
				}
			}
			if (h!=0) {
				for (IGraphics.StringAttribute attr : attrs) {
					attr.Start = Math.max(0, attr.Start-h);
					attr.End = Math.max(0, attr.End-h);
				}
				text = text.substring(h);
			}
		}
		
		// adjust end
		{
			int len = text.length();
			text = text.trim();
			if (len != text.length()) {
				len = len - text.length();
				for (IGraphics.StringAttribute attr : attrs) {
					attr.Start = Math.min(text.length(), attr.Start);
					attr.End = Math.min(text.length(), attr.End);
				}
			}
		}
		
		return text;
	}

	/**
	 * Left close
	 * Right open
	 * @param g
	 * @param str
	 * @param sx
	 * @param px
	 * @return
	 */
	static public int getStringIndex(IGraphics g, String str, int sx, int px)
	{
		int sw = g.getStringWidth(str);
		
		if(px < sx) return 0;
		
		if(px > sx + sw) return str.length();
		
		
		StringBuffer sb = new StringBuffer("");
		char[] chars = str.toCharArray();
		
		for(int i=0;i<chars.length;i++)
		{
			sb.append(chars[i]);
			
			int tw = g.getStringWidth(sb.toString());
			
			int tx2 = sx + tw;
			
			if(px < tx2){
				return i;
			}
		}
		
		return chars.length;
	
	}

	/**
	 * Up close
	 * Down close
	 * @param g
	 * @param lines
	 * @param charh
	 * @param sy
	 * @param py
	 * @return
	 */
	static public int getStringLine(IGraphics g, String[] lines, int charh, int sy, int py)
	{
		int sh = lines.length*charh;
		
		if(py < sy) return 0;
		
		if(py > sy + sh) return lines.length - 1;
		
		int h = py - sy;
		
		return h / charh;
	
	}

}
