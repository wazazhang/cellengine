package com.cell.midi;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class MainApplet extends JApplet
{
	public MainApplet() {
		Config.init();
		super.add(new MainPanel());
	}
	
	@Override
	public void start() {
		super.start();
		super.setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
	}
}
