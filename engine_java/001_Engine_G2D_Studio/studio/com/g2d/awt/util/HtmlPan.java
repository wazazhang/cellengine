package com.g2d.awt.util;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import com.cell.gfx.AScreen;
import com.g2d.display.ui.layout.UILayout;

public class HtmlPan extends JPanel implements HyperlinkListener, Runnable
{
	UILayout		layout;
	int				broad;
	
	JEditorPane 	html_page;
	String 			html_path;
	boolean 		stream_over = false;
	
	public HtmlPan(String path, int width, int height)
	{
		this.html_path = path;
		this.setLayout(null);
		this.setSize(width, height);
		this.setVisible(true);
		
		new Thread(this).start();
	}
	
	public HtmlPan(String path, int width, int height, UILayout layout, int broad)
	{
		this.layout	= layout;
		this.broad	= broad;
		this.html_path = path;
		this.setLayout(null);
		this.setSize(width, height);
		this.setVisible(true);
		
		new Thread(this).start();
	}
	
	public void run() 
	{
		try 
		{
			html_page = new JEditorPane();
			html_page.setBorder(new EmptyBorder(0,0,0,0));
			html_page.setEditable(false); // 请把editorPane设置为只读，不然显示就不整齐
			html_page.setPage(html_path);
			html_page.addHyperlinkListener(this);
			
			JScrollPane scroll = new JScrollPane(html_page);
			scroll.setBorder(new EmptyBorder(0,0,0,0));
			scroll.setVisible(true);
			
			if (layout!=null) {
				scroll.setBounds(broad, broad, getWidth()-(broad<<1), getHeight()-(broad<<1));
			}else{
				scroll.setBounds(0, 0, getWidth(), getHeight());
			}
			
			this.add(scroll);
			this.updateUI();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		stream_over = true;
	}
	
	// 超链监听器，处理对超级链接的点击事件，但对按钮的点击还捕获不到
	public void hyperlinkUpdate(HyperlinkEvent e) 
	{
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
		{
			JEditorPane pane = (JEditorPane) e.getSource();
			
			if (e instanceof HTMLFrameHyperlinkEvent) 
			{
				HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
				HTMLDocument doc = (HTMLDocument) pane.getDocument();
				doc.processHTMLFrameHyperlinkEvent(evt);
			} 
			else 
			{
				try {
//					pane.setPage(e.getURL());
					System.out.println("open : " + e.getURL().toString());
					AScreen.getGfxAdapter().openBrowser(e.getURL().toString());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}  
	
	@Override
	protected void paintChildren(Graphics g) {
		if (layout!=null) {
//			layout.render((Graphics2D)g, 0, 0, getWidth(), getHeight());
		}
		super.paintChildren(g);
	}
	
	static public void main(String[] args) 
	{
	
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.add(new HtmlPan("http://www.google.com", 800, 600));
		frame.setVisible(true);
			
		
	}
}
