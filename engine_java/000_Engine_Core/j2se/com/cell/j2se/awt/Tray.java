package com.cell.j2se.awt;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.cell.CIO;

public class Tray extends MouseAdapter
{
	java.awt.SystemTray		SystemTray;
	java.awt.TrayIcon		TrayIcon;
	java.awt.PopupMenu		TrayMenu;
	
	public Tray(String tip, Image icon, String[] commands, ActionListener commandListener)
	{
//		Toolkit.getDefaultToolkit().createImage(CIO.loadData(imgPath))
		SystemTray	= java.awt.SystemTray.getSystemTray();
		TrayIcon	= new java.awt.TrayIcon(icon);
		TrayMenu	= new PopupMenu();
		
		// 定义托盘图标的图片
		TrayIcon.setToolTip(tip);
		
		// 为托盘添加右键菜单
		TrayIcon.setPopupMenu(TrayMenu);
		for (String cmd : commands) {
			TrayMenu.add(cmd);
		}
		TrayMenu.addActionListener(commandListener);
		
		// 定义托盘图标的鼠标事件
		TrayIcon.addMouseListener(this);
		try {
			SystemTray.add(TrayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}
	
	public void destory(){
		SystemTray.remove(TrayIcon);
	}
	
	public void mouseClicked(MouseEvent e) {}
	
	
	
	
}


