package com.g2d.awt.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class HeapStateLabel extends JLabel implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public HeapStateLabel() {
		super("  "+Util.getHeapString()+"  ");
	}
	public void paint(Graphics g) {
		super.paint(g);
		super.setText("  "+Util.getHeapString()+"  ");
		Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth() - 2, getHeight() - 2, false);
	}
	public void run() {
		this.repaint();
	}
}