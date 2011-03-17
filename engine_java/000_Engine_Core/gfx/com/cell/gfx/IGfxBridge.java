package com.cell.gfx;

import java.io.InputStream;


public interface IGfxBridge 
{
	public IImage 		createImage(String filename);
	public IImage 		createImage(InputStream is);
	public IImage 		createImage(int w, int h);
	public IImage 		createImage(int argb, int w, int h);
	
	public void 		openBrowser(String url);

}
