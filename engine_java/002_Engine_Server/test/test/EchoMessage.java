package test;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.cell.CUtil;
import com.cell.j2se.awt.ConsoleFrame;
import com.cell.j2se.awt.ConsoleFrame.ConsoleListener;

import com.net.MessageHeader;
import com.net.client.service.NetService;
import com.net.client.service.WaitingListener;
import com.net.minaimpl.client.ServerSessionImpl;
import com.net.minaimpl.monitor.ServerMonitor;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;


public class EchoMessage extends MessageHeader
{
	private static final long serialVersionUID = 1L;
	
	public int size			= 1;
	public byte[] data		= new byte[size];
	
	public EchoMessage() {}
	
	@Override
	public String toString() {
		return size + "," + data[0];
	}
}
