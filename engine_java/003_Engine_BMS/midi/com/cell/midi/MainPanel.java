package com.cell.midi;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	JToolBar 	tool_bar 	= new JToolBar();
	
	MainCanvas	main_canvas = new MainCanvas();
	
	public MainPanel()
	{
		super.setLayout(new BorderLayout());
		
		tool_bar.setFloatable(false);
		
		
		super.add(tool_bar, 	BorderLayout.NORTH);
		super.add(main_canvas, 	BorderLayout.CENTER);
		
		
	}
}
