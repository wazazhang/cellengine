package com.g2d.text;

import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;

import com.g2d.Graphics2D;
import com.g2d.geom.Dimension;

public abstract class MultiTextLayout 
{	
	final public static char CHAR_CUT			= 24;	// ctrl + x
	final public static char CHAR_PASTE			= 22;	// ctrl + v
	final public static char CHAR_COPY			= 3;	// ctrl + c
	
	final public static char CHAR_BACKSPACE		= 8;	// backspace
	final public static char CHAR_DELETE		= 127;	// delete

//	----------------------------------------------------------------------------------------------------------------
//	显示
	
	abstract public boolean isSingleLine();
	abstract public boolean isReadOnly();
	abstract public boolean isShowCaret();
	abstract public boolean isShowSelect();

	abstract public void	setSingleLine(boolean single_line);
	abstract public void 	setReadOnly(boolean enable);
	abstract public void 	setShowCaret(boolean enable);
	abstract public void 	setShowSelect(boolean enable);
	
	
	
//	----------------------------------------------------------------------------------------------------------------

	
	
	/**
	 * 将光标设置到x，y位置
	 * @param x
	 * @param y
	 */
	abstract public void setCaret(int x, int y);

	/**
	 * 尝试拖动光标到x，y位置
	 * @param x
	 * @param y
	 */
	abstract public void dragCaret(int x, int y);
	
	
	abstract public int getCaretStartPosition() ;

	abstract public int getCaretEndPosition();
	
	/**
	 * 
	 * @return 获得当前光标位置？
	 * @author yagamiya
	 */
	abstract public int getCaretPosition() ;
	
	/**
	 * 移动光标位置
	 * @param d
	 */
	abstract public void moveCaretPosition(int d) ;
	
	abstract public void moveCaretPositionEnd();
	
	
	abstract public void setCaretPosition(int position);
	
	abstract public String getSelectedText();
	
	abstract public int getCaretPosX() ;
	
	abstract public int getCaretPosY() ;
	
	/**
	 * 坐标位置转换到文字位置
	 * @param x
	 * @param y
	 * @return
	 */
	abstract public int pointToPosition(int x, int y);
	
	/**
	 * 得到该位置的文字属性，比如 attribute=TextAttribute.FOREGROUND, value=Color.BLUE，则返回该位置是否有颜色是蓝色的属性
	 * @param position 文本位置
	 * @param attribute 属性
	 * @param value 属性值
	 * @return
	 */
	abstract public AttributedSegment getSegment(int position, Attribute attribute, Object value);
	
	/**
	 * 得到该位置的文字属性
	 * @param position
	 * @param attribute
	 * @return
	 */
	abstract public AttributedSegment getSegment(int position, Attribute attribute);	
	
//	----------------------------------------------------------------------------------------------------------------

	/**
	 * @param c
	 * @return 增加多少文字，负数代表删除了多少文字
	 */
	abstract public InsertInfo insertChar(char c);

	/**
	 * 设置文本
	 * @param text
	 */
	abstract public void setText(String text);
	
	/**
	 * 设置高级文本
	 * @param atext
	 */
	abstract public void setText(AttributedString atext);
	
	/**
	 * 在最后附加文字
	 * @param atext
	 */
	abstract public void appendText(String text) ;
	
	/**
	 * 在最后附加高级文字
	 * @param atext
	 */
	abstract public void appendText(AttributedString atext);
	
	
	/**
	 * 删除一段文字
	 * @param start
	 * @param end
	 */
	abstract public void deleteText(int start, int end);
	
	/**
	 * 在开始处插入文字
	 * @param start
	 * @param atext
	 */
	abstract public void insertText(int start, AttributedString atext);
	
	/**
	 * 将此范围的文字替换成atext
	 * @param start
	 * @param end
	 * @param atext
	 */
	abstract public void insertText(int start, int end, AttributedString atext);
	
	/**
	 * 在开始处插入文字
	 * @param start
	 * @param atext
	 */
	abstract public void insertText(int start, String text) ;
	
	/**
	 * 将此范围的文字替换成atext
	 * @param start
	 * @param end
	 * @param atext
	 */
	abstract public void insertText(int start, int end, String text) ;

	/**
	 * 删除文字
	 * @param start
	 * @param end
	 */
	abstract public void removeText(int start, int end) ;

	
	abstract public void putAttribute(Attribute attribute, Object value, int start, int end) ;
	
	
//	---------------------------------------------------------------------------------------------------------------
	
	abstract public void setWidth(int width) ;
	
	abstract public void setSpace(int space) ;
	
//	----------------------------------------------------------------------------------------------------------------

	abstract public String getText() ;
	
	abstract public int getWidth() ;

	abstract public int getSpace() ;
	
	abstract public int getHeight() ;
	
	/**
	 * 获取分段后总的行数，在头一次渲染后有效
	 * @return
	 */
	abstract public int getLineCount() ;
	
//	----------------------------------------------------------------------------------------------------------------
	
	public Dimension drawText(Graphics2D g, int x, int y) {
		return this.drawText(g, x, y, 0, 0, getWidth(), getHeight());
	}
	
	/**
	 * @param g
	 * @param x 绘制到g的位置
	 * @param y 绘制到g的位置
	 * @param sx 绘制文本的范围，是该Layout的内部坐标
	 * @param sy 绘制文本的范围，是该Layout的内部坐标
	 * @param sw 绘制文本的范围，是该Layout的内部坐标
	 * @param sh 绘制文本的范围，是该Layout的内部坐标
	 * @return
	 */
	public Dimension drawText(Graphics2D g, int x, int y, int sx, int sy, int sw, int sh) {
		return this.drawText(g, x, y, sx, sy, sw, sh, 0, 0, 0, 0);
	}
	
	/**
	 * @param g
	 * @param x 绘制到g的位置
	 * @param y 绘制到g的位置
	 * @param sx 绘制文本的范围，是该Layout的内部坐标
	 * @param sy 绘制文本的范围，是该Layout的内部坐标
	 * @param sw 绘制文本的范围，是该Layout的内部坐标
	 * @param sh 绘制文本的范围，是该Layout的内部坐标
	 * @param shadow_x 阴影偏移
	 * @param shadow_y 阴影偏移	 
	 * @param shadow_alpha 阴影透明度
	 * @param shadow_color 阴影颜色
	 * @return
	 */
	public Dimension drawText(
			Graphics2D g, 
			int x, int y, 
			int sx, int sy, int sw, int sh, 
			int shadow_x, int shadow_y, float shadow_alpha, int shadow_color) 
	{
		return drawText(g, Integer.MAX_VALUE, x, y, sx, sy, sw, sh, shadow_x, shadow_y, shadow_alpha, shadow_color);
	}

	/**
	 * @param g
	 * @param max_line 最多显示几行
	 * @param x 绘制到g的位置
	 * @param y 绘制到g的位置
	 * @param sx 绘制文本的范围，是该Layout的内部坐标
	 * @param sy 绘制文本的范围，是该Layout的内部坐标
	 * @param sw 绘制文本的范围，是该Layout的内部坐标
	 * @param sh 绘制文本的范围，是该Layout的内部坐标
	 * @param shadow_x 阴影偏移
	 * @param shadow_y 阴影偏移	 
	 * @param shadow_alpha 阴影透明度
	 * @param shadow_color 阴影颜色
	 * @return
	 */
	abstract public Dimension drawText(
			Graphics2D g, 
			int max_line,
			int x, int y, 
			int sx, int sy, int sw, int sh, 
			int shadow_x, int shadow_y, float shadow_alpha, int shadow_color) ;
	
	
	/**
	 * @param g
	 * @param max_line 最多显示几行
	 * @param x 绘制到g的位置
	 * @param y 绘制到g的位置
	 * @param sx 绘制文本的范围，是该Layout的内部坐标
	 * @param sy 绘制文本的范围，是该Layout的内部坐标
	 * @param sw 绘制文本的范围，是该Layout的内部坐标
	 * @param sh 绘制文本的范围，是该Layout的内部坐标
	 * @return
	 */
	abstract public Dimension getDrawTextDimension(Graphics2D g) ;
	
//	------------------------------------------------------------------------------------------------------------------------

	/**
	 * 一段有属性的字符串
	 * @author WAZA
	 */
	public static class AttributedSegment
	{
		final public Attribute	attribute;
		final public Object		attribute_value;
		final public String		text;
		
		public AttributedSegment(Attribute	attribute, Object value, String text){
			this.attribute = attribute;
			this.attribute_value = value;
			this.text = text;
		}
		
		@Override
		public String toString() {
			return "\"" + text + "\" : " + attribute + " : " + attribute_value;
		}
	}
	
	/**
	 * 插入字符后，变化结果
	 * @author WAZA
	 *
	 */
	public static class InsertInfo
	{
		/** 插入的原字符 */
		final public char		src_char;
		
		/** 长度的变化，删减 */
		public int		length_change;
		
		/** 如果长度增加，返回增加的字符 */
		public String	inserted_text;
		
		public InsertInfo(char src) {
			this.src_char = src;
		}
	}
	
	
}
