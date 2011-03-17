package com.cell.gfx.gui;

import com.cell.CMath;
import com.cell.gfx.GfxUtil;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IInputListener;

public class TextBox extends Item implements IInputListener
{
	public String FontName = null;
	public int FontSize = 12;
	
	
	public boolean m_IsCenter = false;
	public boolean m_IsPointer = false;
	
	public boolean CanEdit = true;
	
	public int CharCount = 80000;
	
	
	protected boolean Focused = false;
	
	//text
	protected String m_CurText;
	protected String[] m_CurTexts  ;
	private int CurTextPos = 0;
//	private int m_TextBorderSize	= 2;
	protected int m_CharH;
	
	private int m_TextX;
	private int m_TextY;
	private int m_TextW;
	private int m_TextH;
	
	private int m_OldTextW;
	
	private boolean m_NewFocuse = true;
//	public int TextColor			= 0xff000000;
	
	//color
//	public int m_ColorBack			= 0xffffffff;
//	public int m_ColorBackBorder	= 0xff000000;
	
	public int ShandowColor			= 0;
	public int ShandowX				= 0;
	public int ShandowY				= 2;
	
	public int SelectRegionColor 	= 0xff808080;
	public int CaretColor			= 0xffffffff;
	
	protected int CaretPosition 	= 0;
	protected int PrewCaretPosition = 0;
	
	protected int CaretLine = 0;
	protected int CaretIndex = 0;
	
	
//	ScrollBar	ScrollBarH;
	ScrollBar	ScrollBarV	;
	public void setScrollBarV(ScrollBar other){
		ScrollBarV.set(other);
	}
	public ScrollBar getScrollBarV(){
		return ScrollBarV;
	}


	// key
	public int m_KeyScrollUP		= Form.KEY_UP;
	public int m_KeyScrollDOWN		= Form.KEY_DOWN;

	public int m_ScrollSpeed 		= 2;

	private boolean m_isFilled = false;
	
	public boolean IsPassword = false;
	
	public boolean IsMultiLine = true;
	
	public boolean IsNumberOnly = false;
	public boolean IsNumberOrEngOnly = false;
	
	public boolean IsProcessColor = false;
	
	protected boolean IsScrollToCaret = false;
	
	public TextBox() {
		this("", 10, 10);
	}
	
	public TextBox(String text,int width,int height)
	{
		ScrollBarV	= ScrollBar.createScrollBarV(8);
		ScrollBarV.IsVisible = false;
		
		resize(width, height);
		
		setText(text);
		
		if(Form.CurGraphics!=null){
			m_ScrollSpeed = Form.CurGraphics.getStringHeight() + 1;
		}
		if(text!=null){
			CaretPosition = text.length();
		}
		PrewCaretPosition = CaretPosition;
		
//		if(CommonUserRect!=null)
//			UserRect = CommonUserRect;
		
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

	
	
	synchronized public void setText(String text)
	{
		if(text!=null && text.length() > CharCount)
		{
			text = text.substring(0, CharCount);
		}
		
		boolean needChangeCaretPosition = (text==null||Lable==null)?true:(text.length()!=Lable.length());
		
		Lable = text;
		
		if(Lable==null)
		{
			CaretPosition = 0;
		}
		else 
		{
			Lable = processReplaceTable(Lable);
			
			Lable = LocalCovert.covert(Lable);
			
			if(CaretPosition<0){
				CaretPosition=0;
				needChangeCaretPosition = true;
			}
			if(CaretPosition>Lable.length()){
				CaretPosition=Lable.length();
				needChangeCaretPosition = true;
			}
			
		}
		
		if(needChangeCaretPosition)
		{
			PrewCaretPosition = CaretPosition;
		}
		
	}
	
	public void appendText(String text)
	{
		setText(getText() + text);
	}
	
	public int insertText(String text, int start, int end, boolean changeCaret)
	{
		String strnew = Lable;
		if(start != end)
		{
			int si = Math.max(Math.min(start, end), 0);
			int di = Math.min(Math.max(start, end), Lable.length());
			
			String s1 = Lable.substring(0,si);
			String s2 = Lable.substring(di);
			
			strnew = (s1 + text + s2);
			
			if (strnew.length()<=CharCount){
				setText(strnew);
				if(changeCaret){
					CaretPosition = s1.length() + text.length();
					PrewCaretPosition = CaretPosition;
				}
			}
		}
		else
		{
			if(start<=0)
			{
				strnew = (text+Lable);
				if (strnew.length()<=CharCount){
					setText(strnew);
					if(changeCaret){
						CaretPosition=text.length();
						PrewCaretPosition = CaretPosition;
					}
				}
			}
			else if(start>=Lable.length())
			{
				strnew = (Lable + text);
				if (strnew.length()<=CharCount){
					setText(strnew);
					if(changeCaret){
						CaretPosition=Lable.length();
						PrewCaretPosition = CaretPosition;
					}
				}
			}
			else
			{
				String s1 = Lable.substring(0,start);
				String s2 = Lable.substring(start);
				strnew = (s1 + text + s2);
				if (strnew.length()<=CharCount){
					setText(strnew);
					if(changeCaret){
						CaretPosition+=text.length();
						PrewCaretPosition = CaretPosition;
					}
				}
			}
		}

		return 0;
	}
	
	public String[] getTexts(){
		return m_CurTexts;
	}
	
	private boolean m_prewHoldSelect = false;
	
	protected void getFocuse(Form sender) 
	{
		if (sender != null && CanEdit) {
			m_prewHoldSelect = sender.IsHoldSelect;
			sender.IsHoldSelect = false;
		}
		super.getFocuse(sender);
		
		if(IsMultiLine){
//			CaretPosition = 0;
		}else{
			CaretPosition = getText().length();
		}
		//PrewCaretPosition = CaretPosition;
		
		m_NewFocuse = true;
		
		appendInputListener(this);
	}
	
	protected void lossFocuse(Form sender)
	{
		if (sender != null && CanEdit) {
			sender.IsHoldSelect = m_prewHoldSelect;
		}
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
		m_CurTexts=null;
	}
	
	public boolean update(Form form)
	{
		if(form != null && form.getFocus()==this && form.isVisible()/* && CanEdit*/)
		{
			Focused = true;
			
			if(IsMultiLine)
			{
				clearKey(KEY_TAB);
			}
		}
		else
		{
			Focused = false;
		}
		
		m_TextX = UserRect.BorderSize;
		m_TextY = UserRect.BorderSize;
		m_TextW = W - UserRect.BorderSize*2;
		m_TextH = H - UserRect.BorderSize*2;
		if(IsMultiLine)m_TextW -= ScrollBarV.getBarSize();
		
		if(Lable == null)return true;
		
		ScrollBarV.IsVisible = false;
		
		boolean IsDragScrollBar = false;
		
		if(IsMultiLine && m_CurTexts!=null)
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

		if(/*CanEdit && */!IsDragScrollBar)
		{
			m_IsCenter = false;
			
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
				
				if(Form.isPointerDown() || m_NewFocuse){
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
			
			if(IsMultiLine)
			{
				if(m_CurTexts!=null && m_CurTexts[CaretLine]!=null)
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
						CaretPosition += m_CurTexts[CaretLine].length()-CaretIndex;
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
			else
			{
				return true;
			}
		}
		else
		{
			if(IsMultiLine)
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
			else
			{
				return true;
			}

		}
		
		
	}
	
	protected int getCaretPositionFromPos(int cx, int cy)
	{
		m_CharH = Form.CurGraphics.getStringHeight() + 1;
		
		int pos = 0;
		
		if(IsMultiLine && m_CurTexts!=null)
		{
			cy += CurTextPos;
			
			for(int line=0;line<m_CurTexts.length;line++)
			{
				String str = m_CurTexts[line];
				
				if(IsPassword)
				{
					char[] m = new char[str.length()];
					for(int s=0;s<m.length;s++)
					{
						m[s] = '*';
					}
					str = new String(m);
				}
				
				if(line*m_CharH+m_CharH>cy){
					pos += Math.min(GfxUtil.getStringIndex(Form.CurGraphics, str, 0, cx), str.length()-1);
					break;
				}else{
					pos += str.length();
				}
			}
		}
		else
		{
			String str = Lable;
			
			if(IsPassword)
			{
				char[] m = new char[str.length()];
				for(int s=0;s<m.length;s++)
				{
					m[s] = '*';
				}
				str = new String(m);
			}
			
			pos = GfxUtil.getStringIndex(Form.CurGraphics, str, 0, cx);
		}
	
		if(pos<0)pos=0;
		if(pos>Lable.length())pos=Lable.length();
		
		return pos;
	}
	
	
	public void maxSize(IGraphics g, int width){

		resize(width, H);
		
		m_CharH = g.getStringHeight() + 1;
		
		if(m_CurTexts==null || Lable!=m_CurText || m_TextW!=m_OldTextW)
		{
			m_CurText = Lable ;
			m_CurTexts = GfxUtil.toStringMultiline(g, m_CurText+"\n", m_TextW);
		}
		
		int h = m_CurTexts.length * m_CharH;
    	int w = -1;
    		
		for (int i = 0; i < m_CurTexts.length; i++){
			w = Math.max(g.getStringWidth(m_CurTexts[i]), w);
		}
		
		this.resize(
				Math.min(w + UserRect.BorderSize * 2, SCREEN_WIDTH), 
				Math.min(h + UserRect.BorderSize * 2, SCREEN_HEIGHT)
				);
		
	}
	
	public void renderMultiText(IGraphics g, int x, int y)
	{
		m_CharH = g.getStringHeight() + 1;
		
		if(m_CurTexts==null || Lable!=m_CurText || m_TextW!=m_OldTextW)
		{
			m_CurText = Lable ;
			m_CurTexts = GfxUtil.toStringMultiline(g, m_CurText+"\n", m_TextW);
		}
		
		m_OldTextW = m_TextW;
		
		int prewCaretLine = CaretLine;
		int prewCaretIndex = CaretIndex;
		
		
		{
			if(m_CurTexts.length<=0)
			{
	    		CaretLine = 0;
	    		CaretIndex = 0;
	    	}
			else
	    	{
	    		CaretLine = m_CurTexts.length-1;
	    		CaretIndex = m_CurTexts[CaretLine].length();
	    	}
			
			prewCaretLine = CaretLine;
			prewCaretIndex = CaretIndex;
			
			boolean flag1 = false;
			boolean flag2 = false;
			int C = 0;
    		for (int l = 0; (!flag1 || !flag2) && l < m_CurTexts.length; l++)
    		{
    			for (int i = 0; (!flag1 || !flag2) && i < m_CurTexts[l].length(); i++)
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

    	if(m_CurTexts.length*m_CharH<=m_TextH)
    	{
    		CurTextPos = 0;
    	}

    	g.setColor(TextColor);
    	
    	if(m_CurTexts.length==0)
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
					
					String str = m_CurTexts[line];
					if(IsPassword)
					{
						char[] m = new char[str.length()];
						for(int s=0;s<m.length;s++)
						{
							m[s] = '*';
						}
						str = new String(m);
					}
					
					if(m_IsCenter)
					{
						g.drawString(
								str, 
								x+(W-g.getStringWidth(str))/2, 
								y+m_TextY+i*m_CharH-offsety,
								ShandowColor,
								ShandowX,
								ShandowY
								);
						
					}
					else
					{
						
//						// render region
						if(/*CanEdit && */Lable.length()>0)
						{
							int rx = x+m_TextX;
							int rw = 0;
							if(pL1 == line && pL2 == line)
							{
								String sub = str.substring(pI1,pI2);
								rw = g.getStringWidth(sub);
								rx += g.getStringWidth(str.substring(0,pI1));
							}
							else if(pL1 == line)
							{
								String sub = str.substring(0,pI1);
								int w = g.getStringWidth(sub);
								rx += w;
								rw = m_TextW - w;
							}
							else if(pL2 == line)
							{
								String sub = str.substring(0,pI2);
								rw = g.getStringWidth(sub);
								rx += 0;
							}
							else if(line>pL1 && line<pL2)
							{
								rw = g.getStringWidth(str);
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
						{
						g.setColor(TextColor);
						g.drawString(
								str, 
								x+m_TextX, 
								y+m_TextY+i*m_CharH-offsety,
								ShandowColor,
								ShandowX,
								ShandowY
								);
						}
						
//						// render caret point
						if (getCurrentInputListener()==this && this.isFocused() && getTimer()/4%2==0)
						{
							try{
								if(CaretLine == line)
								{
									String sub = str.substring(0,CaretIndex);
									int dx = x+m_TextX + g.getStringWidth(sub);
									g.setColor(CaretColor);
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

	public void renderSingleText(IGraphics g, int x, int y)
	{
		m_CharH = g.getStringHeight();
		
		if(m_CurTexts==null || Lable!=m_CurText){
			m_CurText = Lable ;
		}
		
		String str = m_CurText;
		
		if(IsPassword)
		{
			char[] m = new char[m_CurText.length()];
			for(int s=0;s<m.length;s++)
			{
				m[s] = '*';
			}
			str = new String(m);
		}
		
		{
			try
			{
				int c = CaretPosition;
				int c1 = Math.max(Math.min(PrewCaretPosition, CaretPosition), 0);
				int c2 = Math.min(Math.max(PrewCaretPosition, CaretPosition), str.length());
				String s = str.substring(0,c);
				String s1 = str.substring(0,c1);
				String s2 = str.substring(0,c2);
				int dx = x + UserRect.BorderSize + g.getStringWidth(s);
				int dx1 = x + UserRect.BorderSize + g.getStringWidth(s1);
				int dx2 = x + UserRect.BorderSize + g.getStringWidth(s2);
				
//				 render selecte region
				if(c1!=c2){
					g.setColor(SelectRegionColor);
					g.fillRect(dx1, y + (H-m_CharH)/2, dx2-dx1, m_CharH);
				}
				
//				 render text
				g.setColor(TextColor);
				g.drawString(
						str, 
						x + m_TextX, 
						y + (H-m_CharH)/2,
						ShandowColor,
						ShandowX,
						ShandowY
						);
				
//				 render caert pos
				if(getCurrentInputListener()==this && this.isFocused() && getTimer()/4%2==0){
					g.setColor(CaretColor);
					g.fillRect(dx, y + (H-m_CharH)/2, 2, m_CharH);
				}
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
		}
		
	}
	
	public void render(IGraphics g, int x, int y) 
	{
		g.pushClip();
    	
		if(Lable==null)return;
		
		String prevFontName = g.getFontName();
		int prevFontSize = g.getFontSize();
		if (FontName!=null){
			g.setFont(FontName, FontSize);
		}
		
		g.clipRect(x, y, W, H);
		UserRect.renderBody(g, x, y, W, H);
		UserRect.renderBorder(g, x, y, W, H);
		
		if(IsMultiLine)
		{
			int barX = x + W - ScrollBarV.getBarSize() + 1;
			int barY = y;
			int barW = ScrollBarV.getBarSize();
			int barH = H;
			
			if(CMath.intersectRect2(x, y, W, H, barX, barY, barW, barH))
			{
				ScrollBarV.render(g, barX, barY, barW, barH);
			}
		}
		
		g.clipRect(x+m_TextX, y+m_TextY, m_TextW, m_TextH);
		

		if(IsMultiLine){
			renderMultiText(g, x, y);
		}else{
			renderSingleText(g, x, y);
		}
		
		
		
		g.popClip();
		
		if (FontName!=null){
			g.setFont(prevFontName, prevFontSize);
		}
	}
	

	

	public void charTyped(char ch)
	{
		if (Focused)
		{
//			println(e.paramString());
//			println((int)e.getKeyChar()+"");
			
			String input = "";
			
			{
				char c = ch;
				
				if(c == 24)// ctrl + x
				{
					if(PrewCaretPosition != CaretPosition)
					{
			            getAppBridge().setClipboardText(getSelectedText());
					}
				}
				else if(c == 3)// ctrl + c
				{
					if(PrewCaretPosition != CaretPosition)
					{
			            getAppBridge().setClipboardText(getSelectedText());
						if(CanEdit)PrewCaretPosition = CaretPosition;
					}
				}
				else if(c == 22)// ctrl + v
				{
	                input = getAppBridge().getClipboardText();
				}
				else if(c == 8)// backspace
				{
					if(PrewCaretPosition == CaretPosition)
					{
						CaretPosition = CaretPosition - 1;
						CaretPosition = Math.max(CaretPosition, 0);
					}
				}
				else if(c == 127)// delete
				{
					if(PrewCaretPosition == CaretPosition)
					{
						CaretPosition = CaretPosition + 1;
						CaretPosition = Math.min(CaretPosition, Lable.length());
					}
				}
				else if(c >= 32 || c=='\t' || c=='\n')
				{
					if( IsMultiLine && c=='\t'){
						input = "    ";
					}else{
						input = c+"";
					}
				}
			}
			
			if(CanEdit)
			{
				StringBuffer sb = new StringBuffer();
				
				int count = input.length();
				
				for(int i=0;i<count;i++)
				{
					char c = input.charAt(i);
					
					if (IsNumberOnly && !((c>='0' && c<='9') || c=='-'))
					{
					}
					else if(IsNumberOrEngOnly && !((c>='0' && c<='9') || (c>='a' && c<='z') || (c>='A' && c<='Z') || c=='_'))
					{
					}
					else if(!IsMultiLine && (c=='\n' || c=='\t') )
					{
					}
					else if(c!='\t' && c!='\n' && (c<32 || c==127))
					{
					}
					else
					{
						sb.append(c);
					}
				}

				insertText(sb.toString(), CaretPosition, PrewCaretPosition, true);
				
				IsScrollToCaret = true;
			}
		}
		
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------
	static TextBox RendableTextBox;
	
	static public void renderTexts(IGraphics g, String text, int color, int x, int y, int w, int h, int ypos)
	{
		if(RendableTextBox==null)
		{
			RendableTextBox = new TextBox(text, w, h);
			RendableTextBox.UserRect.BackColor		= 0;
			RendableTextBox.UserRect.BorderColor	= 0;
			RendableTextBox.CanEdit = false;
		}
		RendableTextBox.setText(text);
		RendableTextBox.resize(w, h);
		RendableTextBox.setScrollPos(ypos);
		RendableTextBox.update(null);
		RendableTextBox.render(g, x, y);
		
	}
	
}
