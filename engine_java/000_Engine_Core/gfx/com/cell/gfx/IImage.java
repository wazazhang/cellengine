package com.cell.gfx;


public interface IImage {

//	/** None Flip Rotate */
//	final public static byte TRANS_NONE = 0;
//	
//	/** Flip Vertical */
//	final public static byte TRANS_V = 1;
//	
//	/** Flip Horizental */
//	final public static byte TRANS_H = 2;
//	
//	/** Flip Horizental and Vertical */
//	final public static byte TRANS_HV = 3;
//	
//	/** anticlockwise rotate 90 angle */
//	final public static byte TRANS_90 = 6;
//	
//	/** anticlockwise rotate 270 angle */
//	final public static byte TRANS_270 = 5;
//	
//	/** first anticlockwise rotate 90 angle , second flip Horizental */
//	final public static byte TRANS_H90 = 4;
//	
//	/** first anticlockwise rotate 90 angle , second flip Vertical */
//	final public static byte TRANS_V90 = 7;
//	
//	/** 180 Rotate */
//	final public static byte TRANS_180 = 3; 

	
	
	
	public IImage newInstance();
	
	//Change size with a new surface
	public IImage createBuffer(int width, int height);
	
	public IImage resize(int newWidth, int newHeight);
	
//	public Image getSrc() ;
	
	
	//Copy a rect scope to a new Image of the image
	public IImage subImage(int x, int y, int width, int height);
	
	//Creates a new Graphics object that renders to this image. 
	public IGraphics createGraphics() ;
	
	//Obtains ARGB pixel data from the specified region of this image and stores it in the provided array of integers. 
	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) ;
	
	//Gets the width of the image in pixels. 
	public int getWidth() ;
    
    //Gets the height of the image in pixels. 
	public int getHeight() ;
	
	
//	final static public int MODE_RAM	= 1;
//	final static public int MODE_VRAM	= 2;
//	final static public int MODE_FILE	= 3;
//	
//	public int setMode(int mode);
	
	// there is two color models
	// one is direct color, each pixel represent a color which has argb information
	// the other one is index color, each pixel is the index in a table which store the actual colors
	final static public int COLOR_MODEL_DIRECT = 0;
	final static public int COLOR_MODEL_INDEX = 1;
	
	/**
	 * get the color model of the image
	 * @return color model
	 */
	public int getColorModel();
	
	/**
	 * if the color model of image is model index, means it has a palette
	 * @return get the palette if it exists
	 */
	public IPalette getPalette();
	
	/**
	 * if the color model of image is model index, set a new palette
	 * @param palette
	 */
	public void setPalette(IPalette palette);
	
	public void dispose();
}



