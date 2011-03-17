package com.g2d.awt.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public abstract class AbstractOptionFrame extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	protected JPanel	south 	= new JPanel(new FlowLayout());
	protected JButton 	ok 		= new JButton("确定");
	protected JButton 	cancel 	= new JButton("取消");
	
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
			this.setVisible(false);
		}  
	}

}
