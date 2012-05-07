package com.cell.gfx;

public interface IGraphics
{
	final static public double ANGLE_90		= Math.PI / 2;
	final static public double ANGLE_270	= Math.PI * 3 / 2;

	public static enum ImageAnchor
	{
		L_T,
		C_T,
		R_T,
		L_C,
		C_C,
		R_C,
		L_B,
		C_B,
		R_B,
	};
	
	public static enum ImageTrans
	{
		NONE,
		R_90, 
		R_180,
		R_270,
		MIRROR,
		MR_90, 
		MR_180,
		MR_270;
		
		public static int stringToTrans(ImageTrans trans)
		{
			switch (trans) {
			case NONE:
				return IGraphics.TRANS_NONE;
			case R_90:
				return IGraphics.TRANS_ROT90;
			case R_180:
				return IGraphics.TRANS_ROT180;
			case R_270:
				return IGraphics.TRANS_ROT270;
			case MIRROR:
				return IGraphics.TRANS_MIRROR;
			case MR_90:
				return IGraphics.TRANS_MIRROR_ROT90;
			case MR_180:
				return IGraphics.TRANS_MIRROR_ROT180;
			case MR_270:
				return IGraphics.TRANS_MIRROR_ROT270;
			}
			return 0;
		}
	};

	
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 1;
    public static final int TRANS_ROT180 = 2;
    public static final int TRANS_ROT270 = 3;
    public static final int TRANS_MIRROR = 4;
    public static final int TRANS_MIRROR_ROT90 = 5;
    public static final int TRANS_MIRROR_ROT180 = 6;
    public static final int TRANS_MIRROR_ROT270 = 7;
    

    public static final int BLEND_MODE_NONE = 0;
    public static final int BLEND_MODE_AVERAGE = 1;
    public static final int BLEND_MODE_MULTIPLY = 2;
    public static final int BLEND_MODE_SCREEN = 3;
    public static final int BLEND_MODE_DARKEN = 4;
    public static final int BLEND_MODE_LIGHTEN = 5;
    public static final int BLEND_MODE_OVERLAY = 6;
    public static final int BLEND_MODE_HARD_LIGHT = 7;
    public static final int BLEND_MODE_SOFT_LIGHT = 8;
    public static final int BLEND_MODE_DIFFERENCE = 9;
    public static final int BLEND_MODE_NEGATION = 10;
    public static final int BLEND_MODE_EXCLUSION = 11;
    public static final int BLEND_MODE_COLOR_DODGE = 12;
    public static final int BLEND_MODE_INVERSE_COLOR_DODGE = 13;
    public static final int BLEND_MODE_SOFT_DODGE = 14;
    public static final int BLEND_MODE_COLOR_BURN = 15;
    public static final int BLEND_MODE_INVERSE_COLOR_BURN = 16;
    public static final int BLEND_MODE_SOFT_BURN = 17;
    public static final int BLEND_MODE_REFLECT = 18;
    public static final int BLEND_MODE_GLOW = 19;
    public static final int BLEND_MODE_FREEZE = 20;
    public static final int BLEND_MODE_HEAT = 21;
    public static final int BLEND_MODE_ADD = 22;
    public static final int BLEND_MODE_SUBTRACT = 23;
    public static final int BLEND_MODE_STAMP = 24;
    public static final int BLEND_MODE_RED = 25;
    public static final int BLEND_MODE_GREEN = 26;
    public static final int BLEND_MODE_BLUE = 27;
    public static final int BLEND_MODE_HUE = 28;
    public static final int BLEND_MODE_SATURATION = 29;
    public static final int BLEND_MODE_COLOR = 30;
    public static final int BLEND_MODE_LUMINOSITY = 31;
    
    public static final int BLEND_MODE_ALPHA_CLEAR = 101;
    public static final int BLEND_MODE_ALPHA_DST = 102;
    public static final int BLEND_MODE_ALPHA_DST_ATOP = 103;
    public static final int BLEND_MODE_ALPHA_DST_IN = 104;
    public static final int BLEND_MODE_ALPHA_DST_OUT = 105;
    public static final int BLEND_MODE_ALPHA_DST_OVER = 106;
    public static final int BLEND_MODE_ALPHA_SRC = 107;
    public static final int BLEND_MODE_ALPHA_SRC_ATOP = 108;
    public static final int BLEND_MODE_ALPHA_SRC_IN = 109;
    public static final int BLEND_MODE_ALPHA_SRC_OUT = 110;
    public static final int BLEND_MODE_ALPHA_SRC_OVER = 111;
    public static final int BLEND_MODE_ALPHA_XOR = 112;
	
    public void 					dispose();

//	---------------------------------------------------------------------------------------------------------------------
//	image
    
//	public void drawImage(IImage img, int x, int y, int transform, int blend_mode, float blend_alpha); 
	/**
	 * 绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。
	 * @param img 要绘制的指定图像。
	 * @param x x 坐标。
	 * @param y y 坐标。
	 * @param transform 翻转方式
	 */
	public void drawImage(IImage img, int x, int y, int transform); 
//	用setBlendMode();改变渲染模式，不需要每画一张图片就改变一次Blend
//	blend只是个状态，不需要每次都设置。否则，画100张图片相同混合方式的图片或图片，每次都new一个blend对象出来。
//	一个组合精灵一帧可能有很多图片，一帧共用渲染模式。
	/**
	 * 绘制指定图像中已缩放到适合指定矩形内部的图像。 
	 * 图像绘制在此图形上下文坐标空间的指定矩形内部，如果需要，则进行缩放。
	 * @param img 要绘制的指定图像。
	 * @param x x 坐标。
	 * @param y y 坐标。
	 * @param w 矩形的宽度。
	 * @param h 矩形的高度。
	 * @param transform 翻转方式
	 */
	public void drawImage(IImage img, int x, int y, int w, int h, int transform); 
//	public void drawRoundImage(IImage img, int x, int y, int widht, int height, int transform, int blend_mode, float blend_alpha); 
//	用setBlendMode(int blend);改变渲染模式，不需要每画一张图片就改变一次Blend
//	用setBlendMode(int blend);改变渲染模式，不需要每画一张图片就改变一次Blend
	/**
	 * 绘制指定图像中的一部分。 
	 * @param src 要绘制的指定图像。
	 * @param x_src 原图片部分的X位置
	 * @param y_src 原图片部分的Y位置
	 * @param width 原图片矩形的宽度。
	 * @param height 原图片矩形的高度。
	 * @param transform 翻转方式
	 * @param x_dest 目标X坐标。
	 * @param y_dest 目标Y坐标。
	 */
	public void drawRegion(IImage src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest); 

	/**
	 * 平铺绘制图片到指定范围。
	 * 如果目标范围比原图片大，则按顺序平铺。
	 * @param img 要绘制的指定图像。
	 * @param x 目标X坐标。
	 * @param y 目标Y坐标。
	 * @param width 目标矩形宽度。
	 * @param height 目标矩形高度。
	 * @param transform 翻转方式
	 */
	public void drawRoundImage(IImage img, int x, int y, int width, int height, int transform); 

//	---------------------------------------------------------------------------------------------------------------------
//	geometry
	
	public void drawLine(int x1, int y1, int x2, int y2); 
	public void drawRect(int x, int y, int width, int height); 
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle); 
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle); 
	public void fillRectAlpha(int argb, int x, int y, int width, int height); 
	public void fillRect(int x, int y, int width, int height); 
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3); 

//	---------------------------------------------------------------------------------------------------------------------
//	alpha and blend
	
	public float	setAlpha(float alpha);
	public float	getAlpha();
	public void 	setBlendMode(int blend);
	public void 	setBlendMode(int blend, float alpha);
	public void		pushBlendMode();
	public void		popBlendMode();

//	---------------------------------------------------------------------------------------------------------------------
//	clipping
	
	public void		pushClip();
	public void		popClip();
	public int		getClipHeight(); 
	public int		getClipWidth(); 
	public int		getClipX(); 
	public int		getClipY(); 
	public void 	setClip(int x, int y, int width, int height); 
	public void 	clipRect(int x, int y, int width, int height);

//	---------------------------------------------------------------------------------------------------------------------
//	color
	
	public void 	setColor(int RGB); 
	public void 	setColor(int red, int green, int blue); 

//	---------------------------------------------------------------------------------------------------------------------
//	string and fonts
	
	public int		getStringWidth(String src);
	public int		getStringHeight();

	public void 	drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY);
	public void 	drawString(String str, int x, int y); 

	public void 	setFont(String name, int size);
	public String 	getFontName();
	public int 		getFontSize();
	
	public void		setStringAntiAllias(boolean antiallias);
	
	
	public StringLayer createStringLayer(String src);
	public StringLayer createStringLayer(String src, StringAttribute[] attributes);
	
	public String[] getStringLines(String text, int w, int[] out_para);
	
	public static class StringAttribute
	{
		public static enum Attribute{
			FONT(),
			SIZE(),
			COLOR(),
			;
			
		}
		
		public Attribute Attr;
		
		public Object Value;
		
		/** inclusive */
		public int Start;
		
		/** exclusive */
		public int End;
		
		public StringAttribute(StringAttribute attr)
		{
			Attr = attr.Attr;
			Value = attr.Value;
			Start = attr.Start;
			End = attr.End;
		}
		
		public StringAttribute(Attribute k, Object v, int start, int end)
		{
			Attr = k;
			Value = v;
			Start = start;
			End = end;
		}
		
		public StringAttribute(Attribute k, Object v)
		{
			Attr = k;
			Value = v;
			Start = -1;
			End = -1;
		}
		
		public static StringAttribute createColorAttribute(int start, int end, int color) {
			StringAttribute attr = new StringAttribute(Attribute.COLOR, color, start, end);
			return attr;
		}
		
		public static StringAttribute createSizeAttribute(int start, int end, int size) {
			StringAttribute attr = new StringAttribute(Attribute.SIZE, size, start, end);
			return attr;
		}
	}
	
	
	public interface StringLayer
	{
		public StringAttribute[] getAttributes();
		
		public String getString();
		public int getWidth();
		public int getHeight();
		
//		public int getWidth(int start, int end);
//		public int getIndex(int x, int y);
		
		
//		public StringLayer split(String regex);
		public StringLayer subString(int start);
		public StringLayer subString(int start, int end);
		
		public StringLayer concat(StringLayer append);
		
		public void render(IGraphics g, int x, int y);
		
		public StringLayer[] getStringLines(int w, int[] out_para);
		
	}
	
}

