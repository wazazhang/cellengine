package com.g2d.editor.property;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditColor extends PopupCellEdit<Color>
{
	JColorChooser 		colorChooser;
	
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
		setBackground(current_value);
		colorChooser.setColor(current_value);
		
		JDialog dialog = JColorChooser.createDialog(
				sender.getComponent(), 
				"Pick a Color",
				true, // modal
				colorChooser, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						current_value = colorChooser.getColor();
						setBackground(current_value);
					}
				}, // OK button handler
				null); // no CANCEL button handler
		
		dialog.setVisible(true);
	}
	
	
}
