package com.cell.gfx;





// an interface for color palette


public interface IPalette 
{
	public byte[] 	getIndexColors();
	
	public int 		getIndexColorCount();
	
	public int 		getTransparentColorIndex();
	
	public byte[] 	getTransparentColor();
	
	public void 	dispose();
	
	public IPalette clone();
	
}; // interface


