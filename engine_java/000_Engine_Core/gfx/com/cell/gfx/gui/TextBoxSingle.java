package com.cell.gfx.gui;

import com.cell.gfx.GfxUtil;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IInputListener;

public class TextBoxSingle extends Item implements IInputListener
{

	public boolean CanEdit = true;
	
	public int CharCount = 80000;
	
	protected boolean Focused = false;
	
	protected String m_CurText;
	protected int m_CharH;
	
	private boolean m_NewFocuse = true;
	
	public int SelectRegionColor 	= 0xff808080;
	public int CaretColor			= 0xffffffff;
	
	protected int CaretPosition 	= 0;
	protected int PrewCaretPosition = 0;
	
	public boolean IsPassword = false;
	
	public boolean IsNumberOnly = false;
	public boolean IsNumberOrEngOnly = false;
	
	private int OffsetX = 0;
	
	public TextBoxSingle() {
		this("", 10, 10);
	}
	
	public TextBoxSingle(String text,int width,int height)
	{
		resize(width, height);
		
		setText(text);
		
		if(text!=null){
			CaretPosition = text.length();
		}
		PrewCaretPosition = CaretPosition;
		
	}
	
	public void notifyDestroy() {
		super.notifyDestroy();
		removeInputListener(this);
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
		if(start != end)
		{
			int si = Math.max(Math.min(start, end), 0);
			int di = Math.min(Math.max(start, end), Lable.length());
			
			String s1 = Lable.substring(0,si);
			String s2 = Lable.substring(di);
			
			setText(s1 + text + s2);
			
			if(changeCaret)CaretPosition = s1.length() + text.length();
		}
		else
		{
			if(start<=0)
			{
				setText(text+Lable);
				if(changeCaret)CaretPosition=text.length();
			}
			else if(start>=Lable.length())
			{
				setText(Lable + text);
				if(changeCaret)CaretPosition=Lable.length();
			}
			else
			{
				String s1 = Lable.substring(0,start);
				String s2 = Lable.substring(start);
				setText(s1 + text + s2);
				if(changeCaret)CaretPosition+=text.length();
			}
		}
		
		if(changeCaret)PrewCaretPosition = CaretPosition;
			
		return 0;
	}
	
	
	private boolean m_prewHoldSelect = false;
	
	protected void getFocuse(Form sender) 
	{
		if (sender != null && CanEdit) {
			m_prewHoldSelect = sender.IsHoldSelect;
			sender.IsHoldSelect = false;
		}
		
		super.getFocuse(sender);
		
		CaretPosition = getText().length();
	
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
	
	
	public void resize(int width, int height){
		W = width;
		H = height;
	}
	
	public boolean update(Form form)
	{
		if(form != null && form.getFocus()==this && form.isVisible()/* && CanEdit*/)
		{
			Focused = true;
		}
		else
		{
			Focused = false;
		}
		
		if(Lable == null)return true;
		
		boolean IsDragScrollBar = false;
		
		

		if(!IsDragScrollBar)
		{
			if(Form.isPointerHold() || Form.isPointerDown())
			{
				int x = Form.getPointerX();
				int y = Form.getPointerY();
			
				if(form != null)
				{
					if (Form.isPointerDown() && form.includeItemPoint(this, x, y))
					{
						int cx = Math.max(0, form.toFormPosX(x) - X - UserRect.BorderSize);
						int cy = Math.max(0, form.toFormPosY(y) - Y - UserRect.BorderSize);
						cx = Math.min(cx, W-UserRect.BorderSize*2);
						cy = Math.min(cy, H-UserRect.BorderSize*2);
						CaretPosition = getCaretPositionFromPos(cx, cy);
					}
					else if (Form.isPointerHold())
					{
						int cx = form.toFormPosX(x) - X - UserRect.BorderSize;
						int cy = Math.max(0, form.toFormPosY(y) - Y - UserRect.BorderSize);
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
			}
			
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
			
		}
	
		return true;
	}
	
	protected int getCaretPositionFromPos(int cx, int cy)
	{
		m_CharH = Form.CurGraphics.getStringHeight() + 1;
		
		int pos = 0;

		String str = Lable;
		
		if (IsPassword) {
			char[] m = new char[str.length()];
			for (int s = 0; s < m.length; s++) {
				m[s] = '*';
			}
			str = new String(m);
		}
		
		pos = GfxUtil.getStringIndex(Form.CurGraphics, str, 0, cx+OffsetX);
		
		if(pos<0)pos=0;
		if(pos>Lable.length())pos=Lable.length();
		
		return pos;
	}
	
	

	public void renderSingleText(IGraphics g, int x, int y, int w, int h)
	{
		m_CharH = g.getStringHeight();
		
		if(Lable!=m_CurText){
			m_CurText = Lable ;
		}
		
		String str = m_CurText;
		
		if(IsPassword)
		{
			char[] m = new char[m_CurText.length()];
			for (int s = 0; s < m.length; s++) {
				m[s] = '*';
			}
			str = new String(m);
		}
		
		{
			int sw = g.getStringWidth(str);
			
			try
			{
				int c = CaretPosition;
				int c1 = Math.max(Math.min(PrewCaretPosition, CaretPosition), 0);
				int c2 = Math.min(Math.max(PrewCaretPosition, CaretPosition), str.length());
				
				String s = str.substring(0,c);
				String s1 = str.substring(0,c1);
				String s2 = str.substring(0,c2);
				
				int swc = g.getStringWidth(s);
				int s1w = g.getStringWidth(s1);
				int s2w = g.getStringWidth(s2);
				
				int dy = y + (H-m_CharH)/2;
				int dxc = x + swc;
				int dx1 = x + s1w;
				int dx2 = x + s2w;
				
				if (sw>w)
				{
					if (dxc-OffsetX+2 > x+w)
					{
						OffsetX += ((dxc-OffsetX+2) - (x+w));
					}
					else if (dxc-OffsetX < x)
					{
						OffsetX -= (x - (dxc-OffsetX));
					}
					
					OffsetX = Math.max(OffsetX, 0);
					OffsetX = Math.min(OffsetX, sw);
				}
				else
				{
					OffsetX = 0;
				}
				
//				 render selecte region
				if(c1!=c2){
					g.setColor(SelectRegionColor);
					g.fillRect(dx1 - OffsetX, dy, dx2-dx1, m_CharH);
				}
				
//				 render text
				g.setColor(TextColor);
				g.drawString(str, x - OffsetX, dy);
				
// 				 render caert pos
				if(getCurrentInputListener()==this && this.isFocused() && getTimer()/4%2==0){
					g.setColor(CaretColor);
					g.fillRect(dxc - OffsetX, dy, 2, m_CharH);
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
		
		g.clipRect(x, y, W, H);
		UserRect.renderBody(g, x, y, W, H);
		UserRect.renderBorder(g, x, y, W, H);
		
		g.clipRect(x+UserRect.BorderSize, y, W-UserRect.BorderSize*2, H);
		renderSingleText(g, x+UserRect.BorderSize, y, W-UserRect.BorderSize*2, H);
		
		g.popClip();
		
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
					input = c+"";
				}
			}
			
			if(CanEdit)
			{
				StringBuffer sb = new StringBuffer();
				
				int count = input.length();
				
				for(int i=0;i<count;i++)
				{
					char c = input.charAt(i);
					
					if (IsNumberOnly && !((c>='0' && c<='9') || c=='-')){
					}
					else if(IsNumberOrEngOnly && !((c>='0' && c<='9') || (c>='a' && c<='z') || (c>='A' && c<='Z') || c=='_')){
					}
					else if(c=='\n' || c=='\t'){
					}
					else if(c!='\t' && c!='\n' && (c<32 || c==127)){
					}
					else{
						sb.append(c);
					}
				}
				
				insertText(sb.toString(), CaretPosition, PrewCaretPosition, true);
			}
		}
		
	}

}
