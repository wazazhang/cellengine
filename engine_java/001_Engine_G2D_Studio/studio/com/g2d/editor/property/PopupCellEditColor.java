package com.g2d.editor.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

import com.g2d.Color;
import com.g2d.java2d.impl.AwtEngine;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditColor extends PopupCellEdit<Color>
{
	JColorChooser colorChooser;
	
	public PopupCellEditColor() 
	{
		// Set up the dialog that the button brings up.
		colorChooser = new JColorChooser();
	}

	@Override
	public void onOpenEditor(Color value) 
	{
		// The user has clicked the cell, so
		// bring up the dialog.
		java.awt.Color color = AwtEngine.unwrap(value);
		setBackground(color);
		colorChooser.setColor(color);
		
		JDialog dialog = JColorChooser.createDialog(
				sender.getComponent(), 
				"Pick a Color",
				true, // modal
				colorChooser, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						java.awt.Color dcolor = colorChooser.getColor();
						setBackground(dcolor);
						current_value = AwtEngine.wrap(dcolor);
					}
				}, // OK button handler
				null); // no CANCEL button handler
		
		dialog.setVisible(true);
	}
	
	
}
