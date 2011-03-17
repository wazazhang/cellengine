package com.cell.j2se;

import java.applet.Applet;
import java.awt.image.BufferedImage;

import com.cell.gfx.IImage;



public interface IGame 
{
	public void initApplet(Applet applet) ;
	
	
	/**Screen size*/
	public int getWidth();
	/**Screen size*/
	public int getHeight();
	
	/**Client screen title*/
	public String getTitle();
	public BufferedImage getLoadingImage();
	public BufferedImage getCursorImage();
	public BufferedImage getIconImage();
	
	/**Get root screen name*/
	public String getRootScreenName();
	
	/**Game exited*/
	public void exited();
	
	/**Get game fps*/
	public int getFPS();
	
	/**Exception caught in main game loop*/
	public void exceptionCaught(Exception err);
	
	//public NetClient createNetClient(NetClientListener listener);
	
}
