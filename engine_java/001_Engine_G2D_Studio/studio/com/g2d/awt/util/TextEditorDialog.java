package com.g2d.awt.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class TextEditorDialog extends AbstractOptionDialog<String>
{
	JTextPane text_pane = new JTextPane();
	String value = null;
	public TextEditorDialog() 
	{	
		super.setSize(640, 480);
		super.add(new JScrollPane(text_pane), BorderLayout.CENTER);
	}

	public void setText(String text) {
		text_pane.setText(text);
	}

	@Override
	protected boolean checkOK() {
		return true;
	}
	@Override
	protected String getUserObject(ActionEvent e) {
		return text_pane.getText();
	}
}
