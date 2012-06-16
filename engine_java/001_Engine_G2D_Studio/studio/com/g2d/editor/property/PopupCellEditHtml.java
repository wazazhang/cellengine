package com.g2d.editor.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

import com.g2d.Color;
import com.g2d.Font;
import com.g2d.awt.util.TextEditorDialog;
import com.g2d.awt.util.font.JFontChooser;
import com.g2d.java2d.impl.AwtEngine;
import javax.swing.text.rtf.RTFEditorKit;


/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditHtml extends PopupCellEdit<String>
{
	TextEditorDialog dialog;
	
	public PopupCellEditHtml(String value) 
	{
		// Set up the dialog that the button brings up.
		dialog = new TextEditorDialog();
		dialog.setText(value+"");
	}

	@Override
	public void setValue(Field field, String value, ObjectPropertyEdit comp) {
		super.setValue(field, value, comp);
		if (value != null) {
			setText(value+"");
		}
	}
	
	@Override
	public void onOpenEditor(String value) 
	{
		dialog.setText(value+"");
		// 
		String result = dialog.showDialog();
		if (result != null) {
			current_value = result;
		}
	}
	
	
}
