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

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.g2d.util.Tracker;
import com.g2d.util.Util;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.client.ClientChannel;
import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.client.ServerSessionImpl;

public class ServerMonitor extends JFrame implements ActionListener, ServerSessionListener
{
	 public static void main(String[] args) 
	 {
		String host = "game.lordol.com";
		String port = "17001";

		if (args != null && args.length >= 2) {
			host = args[0];
			port = args[1];
		}

		new ServerMonitor(host, Integer.parseInt(port)).setVisible(true);

	}
	
	
	private static final long serialVersionUID = 1L;

	java.awt.SystemTray SystemTray;
	java.awt.TrayIcon TrayIcon;
	java.awt.PopupMenu TrayMenu;
	
	
	public JPanel button_pan = new JPanel();
    JButton ButtonGC;
//    JButton ButtonTest;
//    JButton ButtonStopTest;
    
    
    ThreadPool	CurThreadPool = new ThreadPool("TestPool", 2, 4, 4);
    
    Future<?> TestTaskFuture;
    
    ServerSessionImpl Session;
    
    SystemMessages.ServerStatusResponseS2C CurrentServerStatus;
    
    
    
    
    public ServerMonitor(String host, int port)
    {
    	this.setTitle("ServerMonitor " +host + ":" + port);
    	this.setLayout(new BorderLayout());
		this.setSize(900, 600);

		this.add(new PaintCanvas(), BorderLayout.NORTH);
		
		
		this.add(button_pan, BorderLayout.SOUTH);
		
		{
			ButtonGC = new JButton("GC");
			ButtonGC.addActionListener(this);
			button_pan.add(ButtonGC);
			
//			ButtonTest = new JButton("Test");
//			ButtonTest.addActionListener(this);
//			bpan.add(ButtonTest);
//			
//			ButtonStopTest = new JButton("StopTest");
//			ButtonStopTest.addActionListener(this);
//			bpan.add(ButtonStopTest);
		}
		
		this.validate();
	
		
//		 try {
//			SystemTray = java.awt.SystemTray.getSystemTray();
//			TrayIcon = new java.awt.TrayIcon(Toolkit.getDefaultToolkit()
//					.getImage(SystemTray.getClass().getResource("/eat/server/ss_icon.png")));
//			TrayMenu = new PopupMenu();
//			
//			// 定义托盘图标的图片
//			TrayIcon.setToolTip("Chat Server");
//			
//			// 为托盘添加右键菜单
//			TrayIcon.setPopupMenu(TrayMenu);
//			TrayMenu.add("show");
//			TrayMenu.add("exit");
//			TrayMenu.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					if (e.getActionCommand().equals("show")){
//						ServerMonitor.this.setVisible(true);
//					}else if (e.getActionCommand().equals("exit")) {
//						ServerMonitor.this.exit();
//					}
//				}
//			});
//			
//			// 定义托盘图标的鼠标事件
//			TrayIcon.addMouseListener(new MouseListener() {
//				public void mouseClicked(MouseEvent e) {
//					if (e.getButton() == MouseEvent.BUTTON1) {
//						ServerMonitor.this.setVisible(true);
//					}
//				}
//				public void mouseEntered(MouseEvent e) {}
//				public void mouseExited(MouseEvent e) {}
//				public void mousePressed(MouseEvent e) {}
//				public void mouseReleased(MouseEvent e) {}
//			});
//			SystemTray.add(TrayIcon);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try{
			
			Session = new ServerSessionImpl();
			
			Session.connect(host, port, this);

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
    }
    
    public void dispose() {
    	super.dispose();
    	try{
        	this.setVisible(false);
        	SystemTray.remove(TrayIcon);
        	Session.disconnect(true);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
    public void exit() {
    	try{
        	this.setVisible(false);
        	SystemTray.remove(TrayIcon);
        	Session.disconnect(true);
        	System.exit(0);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ButtonGC) {
			System.out.println("gc");
			System.gc();
		}
//		else if (e.getSource() == ButtonTest) {
//			if (TestTaskFuture==null) {
//				System.out.println("test");
//				TestTaskFuture = CurThreadPool.scheduleAtFixedRate(new TestTask(), 1000, 1000);
//			}
//		}
//		else if (e.getSource() == ButtonStopTest) {
//			if (TestTaskFuture!=null){
//				System.out.println("stoptest");
//				TestTaskFuture.cancel(true);
//				TestTaskFuture = null;
//			}
//		}
	}
	
	class TestTask implements Runnable
	{
		public void run() {
			try {
				for (int i=0; i<10000; i++){
					Session.send(new SystemMessages.ServerStatusRequestC2S());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void connected(ServerSession session) {
		System.out.println("connected : " + session);
	}
	public void disconnected(ServerSession session, boolean graceful, String reason) {
		System.out.println("disconnected : " + Session);
	}
	public void joinedChannel(ClientChannel channel) {
		System.out.println("joinedChannel : " + channel);
	}
	public void leftChannel(ClientChannel channel) {
		System.out.println("leftChannel : " + channel);
	}
	public void receivedMessage(ServerSession session, Protocol protocol, MessageHeader message) {
//		System.out.println("receivedMessage : " + message);
		if (message instanceof SystemMessages.ServerStatusResponseS2C) {
			CurrentServerStatus = (SystemMessages.ServerStatusResponseS2C)message;
		}
	}
	@Override
	public void receivedChannelMessage(ClientChannel channel, Protocol protocol, MessageHeader message) {
		if (message instanceof SystemMessages.ServerStatusResponseS2C) {
			CurrentServerStatus = (SystemMessages.ServerStatusResponseS2C)message;
		}
	}
	@Override
	public void sentMessage(ServerSession session, Protocol protocol, MessageHeader message) {
		// TODO Auto-generated method stub
		
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
	    		try 
	    		{
	    			if (Session!=null) 
	    			{
	    				Session.send(new SystemMessages.ServerStatusRequestC2S());
	    			
	    				if (CurrentServerStatus!=null)
		    			{
			    			tSessions.record(CurrentServerStatus.SessionCount);
			    			tRB.record(CurrentServerStatus.ReceivedBytes);
			    			tSB.record(CurrentServerStatus.SentBytes);
			    			tRC.record(CurrentServerStatus.ReceivedMessageCount);
			    			tSC.record(CurrentServerStatus.SentMessageCount);
		    			}
	    			}
	    			
	    			if (ServerMonitor.this.isVisible())
	    			{
		    			this.repaint();
	    			}

	    			Thread.sleep(1000);
	    			
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	//System.out.println("paint service is down !");
	    }
	    
		public void paint(Graphics g) 
		{
			super.paint(g);
			
			if (CurrentServerStatus!=null) 
			{
				Util.drawHeapStatus(g, Color.GRAY, 
						CurrentServerStatus.HeapAvaliable,
						CurrentServerStatus.HeapTotal,
						CurrentServerStatus.HeapMax,
						10, 10, getWidth()-20, 40);
				
				int rx1 = 10;
				int rx2 = getWidth()/2;
				int ry = 80;
				int rw = getWidth()/2 - 20;
				int rh = 80;
				{
					{
						g.setColor(Color.BLUE);
						g.drawString("Session = " + CurrentServerStatus.SessionCount, 
								rx1, ry - 4);
						tSessions.drawGrap(g, Color.BLACK, Color.BLUE, "链接数",
								rx1, ry, rw, rh);
						ry += rh + 20;
					}
					
					{
						g.drawString("接收消息数: " + CurrentServerStatus.ReceivedMessageCount, 
								rx1, ry - 4);
						tRC.drawGrap(g, Color.BLACK, Color.RED, "每秒接收消息数",
								rx1, ry, rw, rh);
						g.drawString("接收字节数:" + CUtil.getBytesSizeString(CurrentServerStatus.ReceivedBytes), 
								rx2, ry - 4);
						tRB.drawGrap(g, Color.BLACK, Color.RED, "每秒接收字节数",
								rx2, ry, rw, rh);
						ry += rh + 20;
					}
					
					{
						g.drawString("发送消息数:" + CurrentServerStatus.SentMessageCount, 
								rx1, ry - 4);
						tSC.drawGrap(g, Color.BLACK, Color.GREEN, "每秒发送消息数",
								rx1, ry, rw, rh);
						g.drawString("发送字节数:" + CUtil.getBytesSizeString(CurrentServerStatus.SentBytes), 
								rx2, ry - 4);
						tSB.drawGrap(g, Color.BLACK, Color.GREEN, "每秒发送字节数",
								rx2, ry, rw, rh);
						ry += rh + 20;
					}
//					{
//						g.setColor(Color.BLACK);
//						g.drawString("I/O Task = " + CurrentServerStatus.IoTaskAlive + " -> " + CurrentServerStatus.IoTaskCompleted, 
//								rx1, ry - 4);
//						tTaskIO.drawGrap(g, Color.BLACK, Color.BLACK, "",
//								rx1, ry, rw, rh);
//						g.drawString("Message Process Task = " + CurrentServerStatus.MesageTaskAlive + " -> " + CurrentServerStatus.MesageTaskCompleted, 
//								rx2, ry - 4);
//						tTaskMS.drawGrap(g, Color.BLACK, Color.BLACK, "",
//								rx2, ry, rw, rh);
//						ry += rh + 20;
//					}
					
					{
						g.drawString("服务器运行时间: " + CObject.timesliceToStringHour(System.currentTimeMillis() - CurrentServerStatus.ServerStartTime) + " 小时", rx1, ry);
					}
				}
			}
			else
			{
				g.setColor(Color.BLACK);
				g.drawString("正在连接到服务器...", 80, 80);
				
			}
		}

	}
    
}
