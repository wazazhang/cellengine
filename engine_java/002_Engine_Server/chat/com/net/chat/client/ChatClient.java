
package com.net.chat.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

import com.net.MessageHeader;
import com.net.chat.ChatMessages;
import com.net.chat.ChatMessages.ChannelNotifyS2C;
import com.net.chat.ChatMessages.ChannelRequestC2S;
import com.net.chat.ChatMessages.ChannelResponseS2C;
import com.net.chat.ChatMessages.ChatNotifyS2C;
import com.net.chat.ChatMessages.ChatRequestC2S;
import com.net.chat.ChatMessages.ChatResponseS2C;
import com.net.chat.ChatMessages.LoginRequestC2S;
import com.net.chat.ChatMessages.LoginResponseS2C;
import com.net.client.BasicNetService;
import com.net.client.NetService;
import com.net.client.NotifyListener;
import com.net.client.WaitingListener;
import com.net.minaimpl.client.ServerSessionImpl;



public class ChatClient extends JFrame implements 
WaitingListener<MessageHeader, MessageHeader>, 
NotifyListener<MessageHeader>,
Runnable,
WindowListener,
ActionListener,
KeyListener,
ItemListener
{
	private static final long serialVersionUID = 1L;

	private int DEFAULT_CHANNEL = 0;
	
	final String UserName;

	Random Rand = new Random();
	
	ChatBot[] Bots;
	
	NetService Client;
	
	JTextArea 	TextReader;
	JScrollPane	ScrollReader;
	JTextField	TextWriter;
	JButton		ButtonSend;
	
	JTextField	TextServerHost;
	JButton		ButtonConnect;
	JLabel		LabelStatus;
	
	JList		ListPlayers;
	JComboBox	ComboChannels;
	
	JButton		ToolStartBot;

	
	ChatClient(String host, Integer port)
	{
		// ui
		this.setLayout(new BorderLayout());
    	this.addWindowListener(this);
		this.setSize(800, 600);
			
		{
			ListPlayers = new JList(new String[]{"123456","456678"});
			ListPlayers.setSize(200, 500);
			this.add(new JScrollPane(ListPlayers), BorderLayout.WEST);
		}{
			
			TextReader = new JTextArea();
			TextReader.setBorder(new LineBorder(Color.BLACK));
			TextReader.setSize(800, 500);
			
			ScrollReader = new JScrollPane(TextReader);
			this.add(ScrollReader, BorderLayout.CENTER);
		}{
			JPanel con = new JPanel();
			con.setLayout(new BorderLayout());
			
			TextServerHost = new JTextField(host+":"+port);
			TextServerHost.setSize(400,20);
			con.add(TextServerHost, BorderLayout.CENTER);
			
			ButtonConnect = new JButton("connect");
			ButtonConnect.setSize(100, 20);
			ButtonConnect.addActionListener(this);
			con.add(ButtonConnect, BorderLayout.EAST);
			
			LabelStatus = new JLabel();
			LabelStatus.setSize(200,20);
			con.add(LabelStatus, BorderLayout.WEST);
			
			this.add(con, BorderLayout.NORTH);
		}{
			JPanel con = new JPanel();
			con.setLayout(new BorderLayout());
			
			TextWriter = new JTextField();
			TextWriter.setSize(400,20);
			TextWriter.addKeyListener(this);
			con.add(TextWriter, BorderLayout.CENTER);
	
			ButtonSend = new JButton("send");
			ButtonSend.setSize(100, 20);
			ButtonSend.addActionListener(this);
			con.add(ButtonSend, BorderLayout.EAST);
			
			ComboChannels = new JComboBox(new String[]{"c10000","c20000","c30000","c40000"});
			ComboChannels.setSize(200, 20);
			ComboChannels.addItemListener(this);
			con.add(ComboChannels, BorderLayout.WEST);
			
			this.add(con, BorderLayout.SOUTH);
		}{
			
			JToolBar tools = new JToolBar(JToolBar.VERTICAL);
			
			ToolStartBot = new JButton("start/stop bot");
			ToolStartBot.addActionListener(this);
			tools.add(ToolStartBot);
			
			this.add(tools, BorderLayout.EAST);
		}
		
		this.doLayout();
		this.setVisible(true);

    	
		
		// network
		{
			String username = "(noname)";
			try {
				username = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			UserName = username;
			
	    	Client = new NetService(new ServerSessionImpl());
	        Client.DropRequestTimeOut = 600000;
	        
	    	Client.registNotifyListener(ChatMessages.ChannelNotifyS2C.class, this); 
	    	Client.registNotifyListener(ChatMessages.ChatNotifyS2C.class, this);
	    	
	    	connect(host, port);
	    	
	    	
	    	/*
			Client.sendRequest(
					ChannelRequestC2S.create_Action(ChannelRequestC2S.EAction.JOIN_CHANNEL, "channel 1"), 
					1000, this);
	    	 */
	    	
		}
		
		new Thread(this).run();
	}
	
	private void connect (String host, Integer port) {
		
		Client.connect(host, port);
    	
    	Client.sendRequest(
    			LoginRequestC2S.create_Login(
    					UserName,
    					UserName), 
    			1000, this);
    	
    	Client.sendRequest(
    			ChannelRequestC2S.create_Action(
    					ChannelRequestC2S.EAction.JOIN_CHANNEL,
    					DEFAULT_CHANNEL), 
    			1000, this);
	}
	
	
	
	public void notify(BasicNetService service, MessageHeader notify)
	{
		appendReaderLine("notify : " + notify);
		
		if (notify instanceof ChannelNotifyS2C)
		{
			ChannelNotifyS2C cnotify = (ChannelNotifyS2C)notify;
			
			switch (cnotify.Action)
			{
			case JOINED:
				Client.sendRequest(
		    			ChannelRequestC2S.create_Action(
		    					ChannelRequestC2S.EAction.GET_JOINED_CHANNELS,
		    					DEFAULT_CHANNEL), 
		    			1000, this);
				break;
				
			case LEAVED:
				Client.sendRequest(
		    			ChannelRequestC2S.create_Action(
		    					ChannelRequestC2S.EAction.GET_JOINED_CHANNELS,
		    					DEFAULT_CHANNEL), 
		    			1000, this);
				break;
				
			case MEMBER_JOINED:
				Client.sendRequest(
		    			ChannelRequestC2S.create_Action(
		    					ChannelRequestC2S.EAction.GET_CHANNEL_MEMBERS,
		    					DEFAULT_CHANNEL), 
		    			1000, this);
				break;
				
			case MEMBER_LEAVED:
				Client.sendRequest(
		    			ChannelRequestC2S.create_Action(
		    					ChannelRequestC2S.EAction.GET_CHANNEL_MEMBERS,
		    					DEFAULT_CHANNEL), 
		    			1000, this);
				break;
			}
			
		}
		else if (notify instanceof ChatNotifyS2C)
		{
			
		}
	}
	
	
	public void response(BasicNetService service, MessageHeader request, MessageHeader response)
	{
		appendReaderLine("response : " + request + " : " + response);
		
		if (response instanceof LoginResponseS2C)
		{
			LoginResponseS2C lresponse = (LoginResponseS2C)response;
			
			if (lresponse.Result == LoginResponseS2C.EResult.SUCCEED) 
			{
				Client.sendRequest(
		    			ChannelRequestC2S.create_Action(
		    					ChannelRequestC2S.EAction.CREATE_CHANNEL,
		    					DEFAULT_CHANNEL), 
		    			1000, this);
			}
		}
		else if (response instanceof ChatResponseS2C)
		{
			ChatResponseS2C cresponse = (ChatResponseS2C)response;
			
		
		}
		else if (response instanceof ChannelResponseS2C) 
		{
			ChannelResponseS2C cresponse = (ChannelResponseS2C)response;
			
			if (cresponse.Result == ChannelResponseS2C.EResult.SUCCEED) 
			{
				switch (cresponse.Action)
				{
				case CREATE_CHANNEL:
					break;
				case JOIN_CHANNEL:
					break;
				case LEAVE_CHANNEL:
					break;
					
				case GET_CHANNEL_MEMBERS:
					ListPlayers.setListData(new Vector<String>(cresponse.Members));
					break;
					
				case GET_JOINED_CHANNELS:
					ComboChannels.removeAllItems();
					for (int ch : cresponse.Channels) {
						ComboChannels.addItem(ch+"");
					}
					break;
				}
			}
		}
	}
	
	
	public void timeout(BasicNetService service, MessageHeader request, long time) {
		appendReaderLine("timeout : " + request + " : " + time);
	}
	
	private void appendReaderLine(String text) {
		TextReader.append(text + "\n");
		ScrollReader.getVerticalScrollBar().setValue(ScrollReader.getVerticalScrollBar().getMaximum());
	}
	
	
	public void run() {
		while (this.isVisible()) {
			try {
				LabelStatus.setText("Connection : " + Client.isConnected());
				Thread.sleep(1000); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("timer exited !");
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == ButtonSend) {
			Client.sendRequest(
	    			ChatRequestC2S.create_ChatChannel(
	    					UserName, 
	    					DEFAULT_CHANNEL, 
	    					TextWriter.getText()),
	    			1000, this);
		}
		else if (e.getSource() == ButtonConnect) {
			try{
				String [] addr = TextServerHost.getText().split(":");
				Client.disconnect(true);
				connect(addr[0], Integer.parseInt(addr[1]));
			}catch (Exception err) {
				err.printStackTrace();
			}
			
		}
		else if (e.getSource() == ToolStartBot) {
			if (Bots == null) {
				Bots = new ChatBot[100];
				for (int i=0; i<Bots.length; i++) {
					Bots[i] = new ChatBot();
				}
			}else{
				for (ChatBot bot : Bots) {
					bot.stop();
				}
			}
		}
		
	}
	

	public void itemStateChanged(ItemEvent e) {
		DEFAULT_CHANNEL = Integer.parseInt(e.getItem().toString());
		
	}
	
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Client.sendRequest(
	    			ChatRequestC2S.create_ChatChannel(
	    					UserName, 
	    					0, 
	    					TextWriter.getText()),
	    			1000, this);
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		try{
			this.setVisible(false);
			Client.disconnect(true);
		}catch(Exception err){err.printStackTrace();}
		System.exit(1);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	
	
	class ChatBot implements Runnable, WaitingListener<MessageHeader, MessageHeader>, NotifyListener<MessageHeader>
	{
		NetService Client;
		
		String UserName = "BOT-" + Rand.nextInt()%10000 + "";
		
		public ChatBot() {
			
			Client = new NetService(new ServerSessionImpl());
			Client.DropRequestTimeOut = 600000;
			
	    	Client.registNotifyListener(ChatMessages.ChannelNotifyS2C.class, this); 
	    	Client.registNotifyListener(ChatMessages.ChatNotifyS2C.class, this);
	    	
	    	new Thread(this).start();
		}
		
		public void stop() {
			Client.disconnect(true);
		}
		
		public void run() 
		{
			String [] addr = TextServerHost.getText().split(":");
			
			Client.connect(addr[0], Integer.parseInt(addr[1]));
	    	
	    	Client.sendRequest(
	    			LoginRequestC2S.create_Login(
	    					UserName,
	    					UserName), 
	    			1000, this);
	    	
	    	Client.sendRequest(
	    			ChannelRequestC2S.create_Action(
	    					ChannelRequestC2S.EAction.JOIN_CHANNEL,
	    					DEFAULT_CHANNEL), 
	    			1000, this);
			
			while (Client.isConnected()) 
			{
				MessageHeader request = null;
				
				try 
				{
					switch (Math.abs(Rand.nextInt() % 100)) 
					{
					// login req
					case 0:
						request = LoginRequestC2S.create_Login(
								UserName,UserName);
						break;
					// channel req
					case 1:
						request = ChannelRequestC2S.create_Action(
								ChannelRequestC2S.EAction.CREATE_CHANNEL, 
								DEFAULT_CHANNEL);
						break;
					case 2:
						request = ChannelRequestC2S.create_Action(
								ChannelRequestC2S.EAction.GET_CHANNEL_MEMBERS, 
								DEFAULT_CHANNEL);
						break;
					case 3:
						request = ChannelRequestC2S.create_Action(
								ChannelRequestC2S.EAction.GET_JOINED_CHANNELS, 
								DEFAULT_CHANNEL);
						break;
					case 4:
						request = ChannelRequestC2S.create_Action(
								ChannelRequestC2S.EAction.JOIN_CHANNEL, 
								DEFAULT_CHANNEL);
						break;
					case 5:
						request = ChannelRequestC2S.create_Action(
								ChannelRequestC2S.EAction.LEAVE_CHANNEL, 
								DEFAULT_CHANNEL);
						break;
					}
					
					if (request != null) {
						Client.sendRequest(request, 1000, this);
					}
					
					switch (Math.abs(Rand.nextInt() % 3)) 
					{
					case 0:
						request = ChatRequestC2S.create_ChatSingle(UserName, UserName, "wo cao ni ma !");
						break;
					case 1:
						request = ChatRequestC2S.create_ChatChannel(UserName, DEFAULT_CHANNEL, "wo cao ni men ma !");
						break;
					case 2:
						request = ChatRequestC2S.create_ChatAll(UserName, "wo cao ni men ma !");
						break;
					}
					
					if (request != null) {
						Client.sendRequest(request, 1000, this);
					}
					
					Thread.sleep(1000);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void notify(BasicNetService service, MessageHeader notify) {
			
		}
		public void response(BasicNetService service, MessageHeader request, MessageHeader response) {
			
		}
		public void timeout(BasicNetService service, MessageHeader request, long time) {
			
		}
		
	}
	
    public static void main(String[] args) 
    {
    	String host = "game.lordol.com";
    	String port = "17001";

    	if (args!=null && args.length >= 2) {
    		host = args[0];
    		port = args[1];
    	}
    	
    	new ChatClient(host, Integer.parseInt(port)).setVisible(true);
    	
    }
   
    
}
