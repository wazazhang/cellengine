package com.cell.gfx.gui;


import java.util.Stack;
import java.util.Vector;

import com.cell.CMath;
import com.cell.gfx.AScreen;
import com.cell.gfx.GfxUtil;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IInputListener;
import com.cell.gfx.IGraphics.StringAttribute;
import com.cell.gfx.IGraphics.StringLayer;
import com.cell.gfx.IGraphics.StringAttribute.Attribute;
import com.cell.script.objective.IObjectiveFactory;
import com.cell.script.objective.Objective;


public class RichTextBox extends Item implements IInputListener
{
	public String FontName = null;
	public int FontSize = 12;
	
//	public static String[][] ReplaceTable ;
	
	public boolean m_IsPointer = false;
	
	public int CharCount = 80000;
	
	
	protected boolean Focused = false;
	
	//text
	protected StringLayer[] m_CurTexts = new StringLayer[0];
	
	private int CurTextPos = 0;
	protected int m_CharH;
	
	private int m_TextX;
	private int m_TextY;
	private int m_TextW;
	private int m_TextH;
	
	private boolean m_NewFocuse = true;
	
	public int SelectRegionColor 	= 0xff808080;
	public int CaretColor			= 0xffffffff;
	
	protected int CaretPosition 	= 0;
	protected int PrewCaretPosition = 0;
	
	protected int CaretLine = 0;
	protected int CaretIndex = 0;
	
	
//	ScrollBar	ScrollBarH;
	private ScrollBar	ScrollBarV	;
	public void setScrollBarV(ScrollBar other){
		ScrollBarV.set(other);
		resize(getWidth(), getHeight());
	}
	public ScrollBar getScrollBarV(){
		return ScrollBarV;
	}


	// key
	public int m_KeyScrollUP		= Form.KEY_UP;
	public int m_KeyScrollDOWN		= Form.KEY_DOWN;

	public int m_ScrollSpeed 		= 2;

	public boolean IsProcessColor = false;
	
	protected boolean IsScrollToCaret = false;
	
	public RichTextBox() {
		this("", 10, 10);
	}
	
	public RichTextBox(Scripts scr, int w, int h){
		this(scr.Text, scr.Attributes, w, h);
	}
	
	public RichTextBox(String text, int width,int height){
		this(text, new IGraphics.StringAttribute[0], width, height);
	}
	
	public RichTextBox(String text, Vector<IGraphics.StringAttribute> attributes, int width,int height)
	{
		ScrollBarV	= ScrollBar.createScrollBarV(8);
		ScrollBarV.IsVisible = false;
		
		resize(width, height);
		
		setText(text, attributes);
		
		if(Form.CurGraphics!=null){
			m_ScrollSpeed = Form.CurGraphics.getStringHeight() + 1;
		}
		if(text!=null){
			CaretPosition = text.length();
		}
		PrewCaretPosition = CaretPosition;
	}
	
	public RichTextBox(String text, IGraphics.StringAttribute[] attributes, int width,int height)
	{
		ScrollBarV	= ScrollBar.createScrollBarV(8);
		ScrollBarV.IsVisible = false;
		
		resize(width, height);
		
		setText(text, attributes);
		
		if(Form.CurGraphics!=null){
			m_ScrollSpeed = Form.CurGraphics.getStringHeight() + 1;
		}
		if(text!=null){
			CaretPosition = text.length();
		}
		PrewCaretPosition = CaretPosition;
		
		
	}
	
	public void notifyDestroy() {
		super.notifyDestroy();
		removeInputListener(this);
//		println("remove key listener");
	}

	public String getText(){
		return Lable;
	}
	
	public void selectedText(int start, int end){
		if(Lable!=null){
			CaretPosition 		= Math.max(start, end);
			PrewCaretPosition 	= Math.min(start, end);
			if(CaretPosition<0)CaretPosition=0;
			if(CaretPosition>Lable.length())CaretPosition=Lable.length();
		}
	}
	
	public void selectedAllText(){
		if(Lable!=null){
			CaretPosition 		= Lable.length();
			PrewCaretPosition 	= 0;
		}
	}
	
	public String getSelectedText(){
		if(Lable!=null){
			int si = Math.max(Math.min(CaretPosition, PrewCaretPosition), 0);
			int di = Math.min(Math.max(CaretPosition, PrewCaretPosition), Lable.length());
			return Lable.substring(si, di);
		}
		return "";
	}

	synchronized public void setScriptText(String src){
		Scripts scr = RichTextBox.buildScript(src);
		setText(scr.Text, scr.Attributes);
	}
	
	synchronized public void setText(String text){
		if (text!=null) {
			setText(RichTextBox.buildScript(text));
		}
	}
	
	synchronized public void setText(Scripts text){
		setText(text.Text, text.Attributes);
	}
	
	synchronized public void setText(String text, Vector<IGraphics.StringAttribute> attributes)
	{
		IGraphics.StringAttribute[] attrs = null;
		if (attributes!=null){
			attrs = new IGraphics.StringAttribute[attributes.size()];
			attributes.copyInto(attrs);
		}
		setText(text, attrs);
	}
	
	synchronized public void setText(String text, IGraphics.StringAttribute[] attributes)
	{
		if (text!=null && (attributes==null || attributes.length==0)){
			if(text.length() > CharCount){
				text = text.substring(0, CharCount);
			}
			text = processReplaceTable(text);
			text = LocalCovert.covert(text);
		}
		
		boolean needChangeCaretPosition = (text==null||Lable==null)?true:(text.length()!=Lable.length());
		
		Lable = text;
		
		if(Lable==null){
			CaretPosition = 0;
			m_CurTexts = new StringLayer[0];
		}else {
			if(CaretPosition<0){
				CaretPosition=0;
				needChangeCaretPosition = true;
			}
			if(CaretPosition>Lable.length()){
				CaretPosition=Lable.length();
				needChangeCaretPosition = true;
			}
		}
		
		if(needChangeCaretPosition){
			PrewCaretPosition = CaretPosition;
		}
		
		if (AScreen.CurGraphics!=null && Lable!=null && Lable.length()>0)
		{
			String prevFontName = CurGraphics.getFontName();
			int prevFontSize = CurGraphics.getFontSize();
			if (FontName!=null){
				CurGraphics.setFont(FontName, FontSize);
			}
			
			StringLayer tl = CurGraphics.createStringLayer(Lable, attributes);
			m_CurTexts = tl.getStringLines(m_TextW, null);
			
			if (FontName!=null){
				CurGraphics.setFont(prevFontName, prevFontSize);
			}
			
			
		}
	}
	
	synchronized public void appendLine(StringLayer line){
		if (line.getString().endsWith("\n") && m_CurTexts!=null){
			Lable = Lable + line.getString();
			if (line.getWidth()>m_TextW){
				StringLayer[] lines = line.getStringLines(m_TextW, null);
				StringLayer[] newLines = new StringLayer[m_CurTexts.length+lines.length];
				System.arraycopy(m_CurTexts, 0, newLines, 0, m_CurTexts.length);
				System.arraycopy(lines, 0, newLines, m_CurTexts.length, lines.length);
				m_CurTexts = newLines;
			}
			else{
				StringLayer[] newLines = new StringLayer[m_CurTexts.length+1];
				System.arraycopy(m_CurTexts, 0, newLines, 0, m_CurTexts.length);
				newLines[m_CurTexts.length] = line;
				m_CurTexts = newLines;
			}
		}
		
	}
	
	synchronized public StringLayer removeLine(int index){
		if (m_CurTexts!=null && m_CurTexts.length>index && index>=0 && m_CurTexts.length>0){
			StringLayer ret = m_CurTexts[index];
			Vector<StringLayer> lines = new Vector<StringLayer>(m_CurTexts.length-1);
			Lable = "";
			for (int i = 0; i < m_CurTexts.length; i++) {
				if (i != index) {
					lines.addElement(m_CurTexts[i]);
					Lable+=m_CurTexts[i].getString();
				}
			}
			StringLayer[] newLines = new StringLayer[lines.size()];
			lines.copyInto(newLines);
			m_CurTexts = newLines;
			return ret;
		}
		return null;
	}
	
	synchronized public void removeAll(){
		m_CurTexts = new StringLayer[0];
	}
	
	synchronized public int getLineCount(){
		if (m_CurTexts!=null){
			return m_CurTexts.length;
		}
		return 0;
	}
	
	
	synchronized public String[] getTexts() {
		String[] ret = new String[m_CurTexts.length];
		for (int i = ret.length - 1; i >= 0; --i) {
			ret[i] = m_CurTexts[i].getString();
		}
		return ret;
	}
	
	synchronized public StringLayer[] getLines() {
		return m_CurTexts;
	}
	
	protected void getFocuse(Form sender) 
	{
		super.getFocuse(sender);

		m_NewFocuse = true;
		
		appendInputListener(this);
	}
	
	protected void lossFocuse(Form sender)
	{
		super.lossFocuse(sender);
		//PrewCaretPosition = CaretPosition;
		Focused = false;
		removeInputListener(this);
	}
	
	public void notifyPause() {
		//Focused = false;
		removeInputListener(this);
	}
	
	public void clicked(Form sender,int key,int x,int y)
	{
		if (this.IsEnabled)
		{
			if (this.Listener != null)
			{
				Listener.itemCommandAction(Click,this);
			}
			
		}
	}
	
	public int getScrollLength()
	{
		return  m_CurTexts.length*m_CharH - m_TextH;
	}
	
	public void setScrollPos(int pos){
		if(m_CurTexts==null)return ;

		CurTextPos=pos;
		if(CurTextPos<0){
			CurTextPos = 0;
		}
		if(CurTextPos>m_CurTexts.length*m_CharH - m_TextH){
			CurTextPos = m_CurTexts.length*m_CharH - m_TextH;
		}
	}

	public boolean scrollUp()
	{
		if(m_CurTexts==null)return true;
		CurTextPos-=m_ScrollSpeed;
		if(CurTextPos<0){
			CurTextPos = 0;
			return true;
		}
		return false;
	}
	
	public boolean scrollDown()
	{
		if(m_CurTexts==null)return true;
		CurTextPos+=m_ScrollSpeed;
		if(CurTextPos>m_CurTexts.length*m_CharH - m_TextH){
			CurTextPos = m_CurTexts.length*m_CharH - m_TextH;
			return true;
		}
		return false;
	}

	public void scrollToCaret()
	{
		int caretY = CaretLine*m_CharH;
		
		if(caretY>CurTextPos+H-UserRect.BorderSize*2-m_CharH)
		{
			this.setScrollPos(caretY-(H-UserRect.BorderSize*2-m_CharH));
		}
		if(caretY<CurTextPos)
		{
			this.setScrollPos(caretY);
		}
	}
	
	public void resize(int width, int height){
		W = width;
		H = height;
		m_TextX = UserRect.BorderSize;
		m_TextY = UserRect.BorderSize;
		m_TextW = W - UserRect.BorderSize*2;
		m_TextH = H - UserRect.BorderSize*2;
		m_TextW -= ScrollBarV.getBarSize();
	}
	
	public boolean update(Form form)
	{
		if (form != null && form.getFocus() == this && form.isVisible()) {
			Focused = true;
		} else {
			Focused = false;
		}
		
		if(Lable == null)return true;
		
		ScrollBarV.IsVisible = false;
		
		boolean IsDragScrollBar = false;
		
		if(m_CurTexts!=null)
		{
			int boardh = m_CurTexts.length*m_CharH;
			
			if(m_TextH < boardh)
			{
				ScrollBarV.IsVisible = true;
				
				ScrollBarV.setScope(0, boardh);
				ScrollBarV.setValue(CurTextPos, m_TextH);
				
				IsDragScrollBar = ScrollBarV.update();
				
				CurTextPos = ScrollBarV.getValue();
			}
			
		}

		if(!IsDragScrollBar)
		{
			if(IsScrollToCaret)
			{
				IsScrollToCaret = false;
				
				scrollToCaret();
			}
			
			
			if(Form.isPointerHold() || Form.isPointerDown())
			{
				int x = Form.getPointerX();
				int y = Form.getPointerY();
			
				if(form != null)
				{
					if (Form.isPointerDown() && form.includeItemPoint(this, x, y) || 
						Form.isPointerHold())
					{
						int cx = Math.max(0, form.toFormPosX(x) - X - UserRect.BorderSize);
						int cy = Math.max(0, form.toFormPosY(y) - Y - UserRect.BorderSize);
						cx = Math.min(cx, W-UserRect.BorderSize*2);
						cy = Math.min(cy, H-UserRect.BorderSize*2);
						CaretPosition = getCaretPositionFromPos(cx, cy);
					}
				}

				if(CaretPosition<0)CaretPosition=0;
				if(CaretPosition>Lable.length())CaretPosition=Lable.length();
				
				if(Form.isPointerDown() || m_NewFocuse)
				{
					m_NewFocuse = false;
					PrewCaretPosition = CaretPosition;
				}
				//println("C="+CaretPosition+" P="+PrewCaretPosition);
			}
			
//			if(!IsDragScrollBar)
			{
				if(Form.isEventKeyHold(Form.KEY_LEFT)){
					CaretPosition --;
					if(CaretPosition<0)CaretPosition=0;
					if(!Form.isPointerHold())PrewCaretPosition = CaretPosition;
				}
				if(Form.isEventKeyHold(Form.KEY_RIGHT)){
					CaretPosition ++;
					if(CaretPosition>Lable.length())CaretPosition=Lable.length();
					if(!Form.isPointerHold())PrewCaretPosition = CaretPosition;
				}
			}
			
			{
				if(m_CurTexts!=null && m_CurTexts.length > CaretLine && m_CurTexts[CaretLine]!=null)
				{
					if(Form.isEventKeyHold(Form.KEY_UP)){
//						CaretLine = Math.max(--CaretLine,0);
//						CaretIndex = Math.min(m_CurTexts[CaretLine].length(), CaretIndex);
						CaretPosition += -CaretIndex-1;
						if(CaretPosition<0)CaretPosition=0;
						if(!Form.isPointerHold())PrewCaretPosition = CaretPosition;
					}
					if(Form.isEventKeyHold(Form.KEY_DOWN)){
//						CaretLine = Math.min(++CaretLine,m_CurTexts.length-1);
//						CaretIndex = Math.min(m_CurTexts[CaretLine].length(), CaretIndex);
						CaretPosition += m_CurTexts[CaretLine].getString().length()-CaretIndex;
						if(CaretPosition>Lable.length())CaretPosition=Lable.length();
						if(!Form.isPointerHold())PrewCaretPosition = CaretPosition;
					}
					
				}

				boolean flag = false;
				if(Form.isKeyDown(m_KeyScrollUP)){
					flag = scrollUp()?true:flag;
				}
				if(Form.isKeyDown(m_KeyScrollDOWN)){
					flag = scrollDown()?true:flag;
				}
				return flag;
			}
		}
		else
		{
				boolean flag = false;
				if(Form.isKeyDown(m_KeyScrollUP)){
					flag = scrollUp()?true:flag;
				}
				if(Form.isKeyDown(m_KeyScrollDOWN)){
					flag = scrollDown()?true:flag;
				}
				
				return flag;
			
		}
		
		
	}
	
	protected int getCaretPositionFromPos(int cx, int cy)
	{
		m_CharH = Form.CurGraphics.getStringHeight() + 1;
		
		int pos = 0;
		
		if(m_CurTexts!=null)
		{
			cy += CurTextPos;
			
			for(int line=0;line<m_CurTexts.length;line++)
			{
				StringLayer str = m_CurTexts[line];
				
				if(line*m_CharH+m_CharH>cy){
					pos += Math.min(GfxUtil.getStringIndex(Form.CurGraphics, str.getString(), 0, cx), str.getString().length()-1);
					break;
				}else{
					pos += str.getString().length();
				}
			}
		}
	
		if(pos<0)pos=0;
		if(pos>Lable.length())pos=Lable.length();
		
		return pos;
	}
	
	
	public void maxSize(IGraphics g){
		if (m_CurTexts!=null){
			m_CharH = g.getStringHeight() + 1;
			int h = m_CurTexts.length * m_CharH;
	    	int w = -1;
			for (int i = 0; i < m_CurTexts.length; i++){
				w = Math.max(m_CurTexts[i].getWidth() + ScrollBarV.getBarSize(), w);
			}
			this.resize(
					Math.min(w + UserRect.BorderSize * 2, SCREEN_WIDTH), 
					Math.min(h + UserRect.BorderSize * 2, SCREEN_HEIGHT)
					);
		}
	}
	
	public void renderMultiText(IGraphics g, int x, int y)
	{
		m_CharH = g.getStringHeight() + 1;

		int prewCaretLine = CaretLine;
		int prewCaretIndex = CaretIndex;
		
		if(m_CurTexts != null)
		{
			if(m_CurTexts.length<=0)
			{
	    		CaretLine = 0;
	    		CaretIndex = 0;
	    	}
			else
	    	{
	    		CaretLine = m_CurTexts.length-1;
	    		CaretIndex = m_CurTexts[CaretLine].getString().length();
	    	}
			
			prewCaretLine = CaretLine;
			prewCaretIndex = CaretIndex;
			
			boolean flag1 = false;
			boolean flag2 = false;
			int C = 0;
    		for (int l = 0; (!flag1 || !flag2) && l < m_CurTexts.length; l++)
    		{
    			for (int i = 0; (!flag1 || !flag2) && i < m_CurTexts[l].getString().length(); i++)
    			{
	        		if(!flag1 && CaretPosition <= C)
	        		{
	        			CaretLine = l;
	        			CaretIndex = i;
	        			flag1 = true;
	        		}
	        		if(!flag2 && PrewCaretPosition <= C)
	        		{
	        			prewCaretLine = l;
	        			prewCaretIndex = i;
	        			flag2 = true;
	        		}
	        		C++;
	        	}
        	}
    	}

    	if(m_CurTexts==null || m_CurTexts.length==0)
    	{
    		if(getCurrentInputListener()==this && this.isFocused() && getTimer()/4%2==0)
			{
    			g.setColor(CaretColor);
				g.fillRect(
						x+m_TextX, 
						y+m_TextY-1, 
						2, 
						m_CharH+2
						);
			}
    	}
    	else
    	{
        	if(m_CurTexts.length*m_CharH<=m_TextH)
        	{
        		CurTextPos = 0;
        	}

    		g.setColor(TextColor);
    		
    		int pI1 = 0;
    		int pL1 = 0;
    		
    		int pI2 = 0;
    		int pL2 = 0;
    		
    		if(CaretLine == prewCaretLine)
    		{
    			pI1 = Math.min(CaretIndex, prewCaretIndex);
    			pI2 = Math.max(CaretIndex, prewCaretIndex);
    			pL1 = CaretLine;
    			pL2 = CaretLine;
    		}
    		else if(CaretLine < prewCaretLine)
    		{
    			pI1 = CaretIndex;
    			pL1 = CaretLine;
        		pI2 = prewCaretIndex;
        		pL2 = prewCaretLine;
    		}
    		else if(CaretLine > prewCaretLine)
    		{
    			pI1 = prewCaretIndex;
    			pL1 = prewCaretLine;
        		pI2 = CaretIndex;
        		pL2 = CaretLine;
    		}
    		
			for(int i=0; i<m_TextH/m_CharH+2 ;i++)
			{
				int line = CurTextPos / m_CharH + i;
				
				if(line>=0 && line<m_CurTexts.length)
				{
					int offsety = CurTextPos%m_CharH;
					
					StringLayer str = m_CurTexts[line];
				
					{
//						// render region
						
						if(Lable.length()>0)
						{
							int rx = x+m_TextX;
							int rw = 0;
							if(pL1 == line && pL2 == line)
							{
								String sub = str.getString().substring(pI1,pI2);
								rw = g.getStringWidth(sub);
								rx += g.getStringWidth(str.getString().substring(0,pI1));
							}
							else if(pL1 == line)
							{
								String sub = str.getString().substring(0,pI1);
								int w = g.getStringWidth(sub);
								rx += w;
								rw = m_TextW - w;
							}
							else if(pL2 == line)
							{
								String sub = str.getString().substring(0,pI2);
								rw = g.getStringWidth(sub);
								rx += 0;
							}
							else if(line>pL1 && line<pL2)
							{
								rw = str.getWidth();
								rx += 0;
							}
							
							if(rw!=0)
							{
								g.setColor(SelectRegionColor);
								g.fillRect(
										rx, 
										y+m_TextY+i*m_CharH-offsety-1, 
										rw, 
										m_CharH+2
										);
							}
						}
						
//						// render string
						g.setColor(TextColor);
						str.render(g,
								x+m_TextX, 
								y+m_TextY+i*m_CharH-offsety);
						
						
//						// render caret point
						if (getCurrentInputListener()==this && this.isFocused() && getTimer()/4%2==0)
						{
							try{
								if(CaretLine == line)
								{
									String sub = str.getString().substring(0,CaretIndex);
									int dx = x+m_TextX + g.getStringWidth(sub);
									g.setColor(0xffffffff);
									g.fillRect(
											dx, 
											y+m_TextY+i*m_CharH-offsety-1, 
											2, 
											m_CharH+2
											);
								}
							}catch(Exception err){
								
							}
						}
						
					}
				}
			}
    	}
	}

	
	public void render(IGraphics g, int x, int y) 
	{
		g.pushClip();
    	
		if(Lable==null)return;
		
		if (!this.IsFocused){
			ScrollBarV.IsVisible = false;
			if (m_CurTexts != null) {
				int boardh = m_CurTexts.length * m_CharH;
				if (m_TextH < boardh) {
					ScrollBarV.IsVisible = true;
					ScrollBarV.setScope(0, boardh);
					ScrollBarV.setValue(CurTextPos, m_TextH);
				}
			}
		}
		
		g.clipRect(x, y, W, H);
		UserRect.renderBody(g, x, y, W, H);
		UserRect.renderBorder(g, x, y, W, H);
		
		{
			int barX = x + W - ScrollBarV.getBarSize() + 1;
			int barY = y;
			int barW = ScrollBarV.getBarSize();
			int barH = H;
			
			if(CMath.intersectRect2(x, y, W, H, barX, barY, barW, barH)){
				ScrollBarV.render(g, barX, barY, barW, barH);
			}
		}
		
		g.clipRect(x+m_TextX, y+m_TextY, m_TextW, m_TextH);
		
		renderMultiText(g, x, y);
		
		g.popClip();
		
	}
	

	

	public void charTyped(char ch) {
		if (Focused) {
			char c = ch;
			// ctrl + c
			if (c == 3) {
				if (PrewCaretPosition != CaretPosition) {
					String text = getSelectedText();
					getAppBridge().setClipboardText(text);
				}
			}
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------

	public static class Scripts extends IObjectiveFactory
	{
		class Instruction extends Objective<StringAttribute>
		{
			StringAttribute Attr;
			int BeginLen; 
			int EndLen;
			
			public Instruction(StringAttribute attr){
				Attr = attr;
			}
			public StringAttribute getValue(){
				return Attr;
			}
		}
		
		public String Text;
		
		public Vector<StringAttribute> Attributes = new Vector<StringAttribute>();
		
		protected Stack<Instruction> CallStack = new Stack<Instruction>();
		protected Vector<Instruction> Queue = new Vector<Instruction>();
		
		public Scripts(String src)
		{
			if (src!=null){
				src = processReplaceTable(src);
				src = LocalCovert.covert(src);
			}
			
			if (src.length()<3)
			{
				Text = src;
				return;
			}
			
			build(src);
			
			Text = "";
			
			if (!Queue.isEmpty())
			{
				try
				{
					int index = 0;
					int count = Queue.size();
					for (int i=0; i<count; i++)
					{
						Instruction item = Queue.elementAt(i);
						String hd = src.substring(index, item.Attr.Start);
						String sub = src.substring(item.Attr.Start+item.BeginLen,item.Attr.End-item.EndLen);
						index = item.Attr.End;
						
						Text += hd;
						item.Attr.Start = Text.length();
						Text += sub;
						item.Attr.End = Text.length();
						
						Attributes.addElement(item.Attr);
					}
					
					if (index<src.length())
					{
						Text += src.substring(index);
					}
				}catch(Exception err){
					Text = "Text Scripts build error: [color:AARRGGBB] <-> [color] not pair!";
					Attributes.clear();
					System.err.println(Text);
				}
			}
			else
			{
				Text = src;
			}

		}
		
		@Override
		public Objective<StringAttribute> getObjective(String src, int begin, int end, String key, String value) {
			//[color:AARRGGBB]text[color] 
			if ("color".equals(key)){
				int color = 0;
				try {
					if (value!=null) color = (int)(Long.parseLong(value.trim(), 16) & 0x00ffffffff);
				} catch (NumberFormatException err) {
					System.err.println("Text Scripts build error: color value invaied : see [color:AARRGGBB]");
				}
				Instruction c = new Instruction(new StringAttribute(Attribute.COLOR, color, 0, 0));
				if (value!=null){
					c.Attr.Start = begin;
					c.BeginLen = end - begin;
					CallStack.push(c);
				}
				else if (!CallStack.isEmpty()){
					Instruction prew = CallStack.pop();
					if (prew.Attr.Attr.equals(StringAttribute.Attribute.COLOR)){
						prew.Attr.End = end;
						prew.EndLen = end - begin;
						Queue.addElement(prew);
					}
				}
				return c;
			}
			
			return null;
		}
		
		@Override
		public String toString() {
			return Text;
		}

	}

	static public Scripts buildScript(String src){
		
		if (src==null)return null;
		
		try{
			Scripts scripts = new Scripts(src);
			return scripts;
		}catch(Exception err){
			err.printStackTrace();
		}
	
		return new Scripts("error script !!!");
	}
	
	public static class ScriptsUI extends IObjectiveFactory
	{
		class InstructionBackColor extends Objective<Integer>
		{
			int BackColor;
			
			public InstructionBackColor(int color){
				BackColor = color;
			}
			public Integer getValue(){
				return BackColor;
			}
		}
		
		public String Text;
		
		public int BackColor;
		
		public ScriptsUI(String src)
		{
			if (src!=null){
				src = processReplaceTable(src);
				src = LocalCovert.covert(src);
			}
			
			if (src.length()<3)
			{
				Text = src;
				return;
			}
			
			Text = build(src);
			
		}
		
		@Override
		public Objective getObjective(String src, int begin, int end, String key, String value) {
			//[color:AARRGGBB]text[color] 
			if ("backcolor".equals(key)){
				int color = 0;
				try {
					if (value!=null) color = (int)(Long.parseLong(value.trim(), 16) & 0x00ffffffff);
					InstructionBackColor c = new InstructionBackColor(color);
					BackColor = color;
					return c;
				} catch (NumberFormatException err) {
					System.err.println("Text Scripts build error: color value invaied : see [color:AARRGGBB]");
				}
			}
			else if ("bcolor".equals(key)){
				int color = 0;
				try {
					if (value!=null) color = (int)(Long.parseLong(value.trim(), 16) & 0x00ffffffff);
					InstructionBackColor c = new InstructionBackColor(color);
					BackColor = color;
					return c;
				} catch (NumberFormatException err) {
					System.err.println("Text Scripts build error: color value invaied : see [color:AARRGGBB]");
				}
			}
			return null;
		}
		
		@Override
		public String toString() {
			return Text;
		}

	}
	

	static public ScriptsUI buildScriptUI(String src){
		
		if (src==null)return null;
		
		try{
			ScriptsUI scripts = new ScriptsUI(src);
			return scripts;
		}catch(Exception err){
			err.printStackTrace();
		}
	
		return new ScriptsUI("error script !!!");
	}

}
