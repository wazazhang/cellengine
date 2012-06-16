package com.g2d.awt.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public abstract class AbstractOptionFrame extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	protected JPanel	south 	= new JPanel(new FlowLayout());
	protected JButton 	ok 		= new JButton("确定");
	protected JButton 	cancel 	= new JButton("取消");
	
	private int dialogResultValue = 0;

	public static final int OK_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	public static final int ERROR_OPTION = 2;

	
	public AbstractOptionFrame(){
		super.setAlwaysOnTop(true);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		south.add(ok);
		south.add(cancel);
		this.add(south, BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(ok);
		
		KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		cancel.registerKeyboardAction(this, "cancel", ks, JComponent.WHEN_IN_FOCUSED_WINDOW);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			dialogResultValue = CANCEL_OPTION;
			this.setVisible(false);
		}  
		else if (e.getSource() == ok) {
			dialogResultValue = OK_OPTION;
			this.setVisible(false);
		}  
	}
	
	/**
	 *  Show font selection dialog.
	 *  @param parent Dialog's Parent component.
	 *  @return OK_OPTION, CANCEL_OPTION or ERROR_OPTION
	 *
	 *  @see #OK_OPTION 
	 *  @see #CANCEL_OPTION
	 **/
	public int showDialog(Component parent)
	{
		dialogResultValue = ERROR_OPTION;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialogResultValue = CANCEL_OPTION;
			}
		});
		this.setVisible(true);
		return dialogResultValue;
	}
}
