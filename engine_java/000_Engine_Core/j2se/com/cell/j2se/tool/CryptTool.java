package com.cell.j2se.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.cell.security.Crypt;

public class CryptTool extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 1L;

	JTextPane	text_src	= new JTextPane();
	JTextField	text_key	= new JTextField();
	JTextPane	text_dst	= new JTextPane();
	
	JButton		btn_encrypt			= new JButton("encrypt");
	JButton		btn_decrypt			= new JButton("decrypt");
	JButton		btn_encrypt_hex		= new JButton("encrypt_hex");
	JButton		btn_decrypt_hex		= new JButton("decrypt_hex");
	
	public CryptTool()
	{
		super.setTitle(getClass().getSimpleName());
		super.setSize(500, 400);
		super.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width/2 - getWidth()/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - getHeight()/2);
		super.addWindowListener(this);
		
		JSplitPane split_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		{
			JPanel panel_src = new JPanel(new BorderLayout());
			{
				JLabel 		lbl1	= new JLabel("input text");
				panel_src.add(lbl1, 	BorderLayout.NORTH);
				
				JScrollPane	sp1		= new JScrollPane(text_src);
				panel_src.add(sp1, 		BorderLayout.CENTER);
				
				JPanel		panelk	= new JPanel(new BorderLayout());
				{
					JLabel		lbl2	= new JLabel("input key");
					panelk.add(lbl2,		BorderLayout.NORTH);
					panelk.add(text_key,	BorderLayout.SOUTH);
				}
				panel_src.add(panelk, 	BorderLayout.SOUTH);
			}
			split_pane.setTopComponent(panel_src);
		}
		
		{
			JPanel panel_dst = new JPanel(new BorderLayout());
			{
				JLabel 		lbl1	= new JLabel("output text");
				panel_dst.add(lbl1, 	BorderLayout.NORTH);
				
				JScrollPane	sp1		= new JScrollPane(text_dst);
				panel_dst.add(sp1, 		BorderLayout.CENTER);
				
				JPanel		panelk	= new JPanel(new GridLayout(1,4));
				{
					panelk.add(btn_encrypt);
					panelk.add(btn_encrypt_hex);
					panelk.add(btn_decrypt);
					panelk.add(btn_decrypt_hex);
					
					btn_encrypt.setActionCommand("encrypt");
					btn_decrypt.setActionCommand("decrypt");
					btn_encrypt_hex.setActionCommand("encrypt_hex");
					btn_decrypt_hex.setActionCommand("decrypt_hex");
					
					btn_encrypt.addActionListener(this);
					btn_decrypt.addActionListener(this);
					btn_encrypt_hex.addActionListener(this);
					btn_decrypt_hex.addActionListener(this);
				}
				panel_dst.add(panelk, 	BorderLayout.SOUTH);
			}
			split_pane.setBottomComponent(panel_dst);
		}
		split_pane.setDividerLocation(getHeight()/2);
		this.add(split_pane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		text_src.setText(text_src.getText().trim());
		text_key.setText(text_key.getText().trim());
		
		if (e.getActionCommand().equals("encrypt")) {
			text_dst.setText(Crypt.encrypt(text_src.getText(), text_key.getText()));
		}
		else if (e.getActionCommand().equals("decrypt")) {
			text_dst.setText(Crypt.decrypt(text_src.getText(), text_key.getText()));
		}
		else if (e.getActionCommand().equals("encrypt_hex")) {
			text_dst.setText(Crypt.encryptHex(text_src.getText(), text_key.getText()));
		}
		else if (e.getActionCommand().equals("decrypt_hex")) {
			text_dst.setText(Crypt.decryptHex(text_src.getText(), text_key.getText()));
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(1);
		}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	public static void main(String[] args)
	{
		CryptTool frame = new CryptTool();
		frame.setVisible(true);
	}
}
