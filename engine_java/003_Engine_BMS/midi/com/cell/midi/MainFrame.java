package com.cell.midi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

import com.cell.j2se.CAppBridge;
import com.g2d.util.AbstractFrame;

@SuppressWarnings("serial")
public class MainFrame extends AbstractFrame
{
	public MainFrame() 
	{
		Config.init();
		
		super.setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
		super.addWindowListener(new WindowListener());
		super.add(new MainPanel());
	}
	
	class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(
					MainFrame.this,
					"退出？", 
					"退出？", 
					JOptionPane.OK_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				System.exit(1);
			} else {
				new Thread() {
					public void run() {
						MainFrame.this.setVisible(true);
					}
				}.start();
			}
		}
	}
	
	public static void main(String[] args)
	{
		MainFrame studio = new MainFrame();
		studio.setCenter();
		studio.setVisible(true);
	}

}
