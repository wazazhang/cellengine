package com.g2d.editor.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

import com.g2d.Color;
import com.g2d.Font;
import com.g2d.awt.util.font.JFontChooser;
import com.g2d.java2d.impl.AwtEngine;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditFont extends PopupCellEdit<Font>
{
	JFontChooser fontChooser;
	
	public PopupCellEditFont() 
	{
		// Set up the dialog that the button brings up.
		fontChooser = new JFontChooser();
	}

	@Override
	public void onOpenEditor(Font value) 
	{
		if (value != null) {
			java.awt.Font font = AwtEngine.unwrap(value);
			if (font.getSize() > 0) {
				setFont(font);
				fontChooser.setSelectedFont(font);
			}
		}
		// 
		int result = fontChooser.showDialog(sender.getComponent());
		if (result == JFontChooser.OK_OPTION) {
			java.awt.Font df = fontChooser.getSelectedFont();
			setFont(df);
			current_value = AwtEngine.wrap(df);
		}
	}
	
	
}
