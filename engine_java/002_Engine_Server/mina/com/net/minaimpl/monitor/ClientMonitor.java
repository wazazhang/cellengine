package com.net.minaimpl.monitor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cell.CUtil;
import com.g2d.util.Tracker;
import com.g2d.util.Util;
import com.net.minaimpl.client.ServerSessionImpl;

public class ClientMonitor extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public JPanel button_pan = new JPanel();
    JButton ButtonGC;
    
    ServerSessionImpl Session;
    
    public ClientMonitor(ServerSessionImpl session)
    {
		Session = session;
		
    	this.setTitle("ClientMonitor");
    	this.setLayout(new BorderLayout());
		this.setSize(900, 600);

		this.add(new PaintCanvas(), BorderLayout.NORTH);
		
		
		this.add(button_pan, BorderLayout.SOUTH);
		
		{
			ButtonGC = new JButton("GC");
			ButtonGC.addActionListener(this);
			button_pan.add(ButtonGC);
			
		}
		
		this.validate();
	
    }
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ButtonGC) {
			System.out.println("gc");
			System.gc();
		}
	}
	
	class PaintCanvas extends Canvas implements Runnable
	{
		private static final long serialVersionUID = 1L;
		
		Tracker tSessions = new Tracker(120);
	    Tracker tRB = new Tracker.SecondRateTracker(120);
	    Tracker tSB = new Tracker.SecondRateTracker(120);
	    Tracker tRC = new Tracker.SecondRateTracker(120);
	    Tracker tSC = new Tracker.SecondRateTracker(120);
	    
//	    Tracker tTaskIO = new Tracker.SecondRateTracker(120);
//	    Tracker tTaskMS = new Tracker.SecondRateTracker(120);
	    
		PaintCanvas() 
		{
			setSize(900, 500);
			Thread t = new Thread(this);
			t.start();
		}
		
	    public void run() 
	    {
	    	for (;;)
	    	{
	    		try {
	    			if (ClientMonitor.this.isVisible())
	    			{
		    			if (Session!=null)
		    			{
			    			tRB.record(Session.getReceivedBytes());
			    			tSB.record(Session.getSentBytes());
			    			tRC.record(Session.getReceivedMessageCount());
			    			tSC.record(Session.getSentMessageCount());
		    			}
		    			
		    			this.repaint();
		    			
		    			Thread.sleep(1000);
	    			}
	    			else
	    			{
	    				Thread.sleep(2000);
	    			}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	//System.out.println("paint service is down !");
	    }
	    
	    @Override
	    public void update(Graphics g) {
	    	// TODO Auto-generated method stub
	    	super.update(g);
	    }
	    
		public void paint(Graphics g) 
		{
			super.paint(g);
			
			if (Session!=null) 
			{
				Util.drawHeapStatus(g, Color.GRAY, 10, 10, getWidth()-20, 40);
				
				int rx1 = 10;
				int rx2 = getWidth()/2;
				int ry = 80;
				int rw = getWidth()/2 - 20;
				int rh = 80;
				{
					
					{
						g.drawString("接收消息数: " + Session.getReceivedMessageCount(), 
								rx1, ry - 4);
						tRC.drawGrap(g, Color.BLACK, Color.RED, "每秒接收消息数",
								rx1, ry, rw, rh);
						g.drawString("接收字节数:" + CUtil.getBytesSizeString(Session.getReceivedBytes()), 
								rx2, ry - 4);
						tRB.drawGrap(g, Color.BLACK, Color.RED, "每秒接收字节数",
								rx2, ry, rw, rh);
						ry += rh + 20;
					}
					
					{
						g.drawString("发送消息数:" + Session.getSentMessageCount(), 
								rx1, ry - 4);
						tSC.drawGrap(g, Color.BLACK, Color.GREEN, "每秒发送消息数",
								rx1, ry, rw, rh);
						g.drawString("发送字节数:" + CUtil.getBytesSizeString(Session.getSentBytes()), 
								rx2, ry - 4);
						tSB.drawGrap(g, Color.BLACK, Color.GREEN, "每秒发送字节数",
								rx2, ry, rw, rh);
						ry += rh + 20;
					}
				}
			}
		}

	}
    
}
