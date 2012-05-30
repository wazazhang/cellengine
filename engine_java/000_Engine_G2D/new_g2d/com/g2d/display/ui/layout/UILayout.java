package com.g2d.display.ui.layout;


import com.cell.DObject;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.TexturePaint;
import com.g2d.Tools;
import com.g2d.geom.Rectangle;
import com.g2d.util.Drawing;

public class UILayout extends DObject
{	
	public static enum ImageStyle
	{
		NULL,
		COLOR,
		IMAGE_STYLE_ALL_9,
		IMAGE_STYLE_ALL_8,
		IMAGE_STYLE_H_012, 
		IMAGE_STYLE_V_036,
		IMAGE_STYLE_BACK_4,
		IMAGE_STYLE_BACK_4_CENTER,
		;
		
		public static ImageStyle getStyle(String style) {
			return ImageStyle.valueOf(style);
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	public static UILayout createRect(Color backcolor)
	{
		UILayout rect = new UILayout();
		rect.BackColor = backcolor;
		rect.BorderColor = backcolor;
		rect.BorderSize = 0;
		return rect;
	}

	public static UILayout createRect()
	{
		return createRect(new Color(0,0,0,0));
	}

	public static UILayout createBlankRect()
	{
		UILayout rect = createRect();
		rect.Style = ImageStyle.NULL;
		return rect;
	}

//	------------------------------------------------------------------------------------------------------------------------------
	
	//border
	public int 				BorderSize	= 1;
	
	// color layout
	private Color 			BackColor	= new Color(0xffc0c0c0);//0xffc0c0c0;
	private Color 			BorderColor	= new Color(0xff000000);//0xff000000;
	
	// image layout
	private ImageStyle 		Style		= ImageStyle.COLOR;
	
	private TexturePaint	BorderT;
	private TexturePaint	BorderB;
	private TexturePaint	BorderL;
	private TexturePaint	BorderR;
	private TexturePaint	BackImage;
	private TexturePaint	BorderTL;
	private TexturePaint	BorderTR;
	private TexturePaint	BorderBL;
	private TexturePaint	BorderBR;

	private BufferedImage srcImage;
	
//	------------------------------------------------------------------------------------------------------------------------------

	public UILayout(){}
	
	public UILayout(ImageStyle style){
		this.Style = style;
	}
	
	/**
	 * @see {@link #setImages(BufferedImage[], ImageStyle)}
	 */
	public UILayout(BufferedImage[] images, ImageStyle style){
		setImages(images, style);
	}
	
	/**
	 * @see {@link #setImages(Image, ImageStyle, int)}
	 */
	public UILayout(BufferedImage src, ImageStyle style, int clipsize){
		setImages(src, style, clipsize, clipsize>>1);
	}

	/**
	 * @see {@link #setImages(Image, ImageStyle, int, int)}
	 */
	public UILayout(BufferedImage src, ImageStyle style, int clipsize, int bordersize){
		setImages(src, style, clipsize, bordersize);
	}

	public UILayout(UILayout set){
		this.set(set);
	}

	public BufferedImage getSrcImage() {
		return srcImage;
	}
	
	public Color getBackColor() {
		return BackColor;
	}

	public Color getBorderColor() {
		return BorderColor;
	}

	public ImageStyle getStyle() {
		return Style;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	public TexturePaint getBorderT() {
		return BorderT;
	}
	public TexturePaint getBorderB() {
		return BorderB;
	}
	public TexturePaint getBorderL() {
		return BorderL;
	}
	public TexturePaint getBorderR() {
		return BorderR;
	}
	public TexturePaint getBackImage() {
		return BackImage;
	}
	public TexturePaint getBorderTL() {
		return BorderTL;
	}
	public TexturePaint getBorderTR() {
		return BorderTR;
	}
	public TexturePaint getBorderBL() {
		return BorderBL;
	}
	public TexturePaint getBorderBR() {
		return BorderBR;
	}

	
	
	public void set(UILayout set)
	{
//		body
		BackColor		= set.BackColor;
		BackImage		= set.BackImage;
//		BackAlphaColor 	= set.BackAlphaColor;
		
//		border
		BorderSize		= set.BorderSize;
		BorderColor		= set.BorderColor;
		
		BorderT			= set.BorderT;
		BorderB			= set.BorderB;
		BorderL			= set.BorderL;
		BorderR			= set.BorderR;
		
		BorderTL		= set.BorderTL;
		BorderTR		= set.BorderTR;
		BorderBL		= set.BorderBL;
		BorderBR		= set.BorderBR;
		
		Style			= set.Style;
		srcImage 		= set.srcImage;
	}
	
	public void setBackColor(Color color){
		BackColor = color;
	}
	
	public void setBorderColor(Color color){
		BorderColor = color;
	}
	
	public void setImages(Image src, ImageStyle style, int clipsize)
	{
		setImages(src, style, clipsize, clipsize>>1);
	}

	public void setImages(Image src, ImageStyle style, int clipsize, int bordersize)
	{
		BorderSize = bordersize;
		if (src instanceof BufferedImage) {
			srcImage = (BufferedImage)src;
			clipImages((BufferedImage)src, 
					style, clipsize, clipsize, clipsize, clipsize);
		} else if (src != null) {
			clipImages(Tools.combianImage(src.getWidth(), src.getHeight(), src), 
					style, clipsize, clipsize, clipsize, clipsize);
		}
	}
	

	protected void clipImages(BufferedImage src, ImageStyle style, int L, int R, int T, int B)
	{
		BufferedImage BorderTL	= null;
		BufferedImage BorderT	= null;
		BufferedImage BorderTR 	= null;
		BufferedImage BorderL  	= null;
		BufferedImage BackImage = null;
		BufferedImage BorderR  	= null;
		BufferedImage BorderBL	= null;
		BufferedImage BorderB	= null;
		BufferedImage BorderBR	= null;

		if (src != null)
		{
			int W = src.getWidth();
			int H = src.getHeight();

			switch(style)
			{
			case IMAGE_STYLE_ALL_9:
				BackImage 	= Tools.subImage(src, L, T, W - L - R, H - T - B);
				
			case IMAGE_STYLE_ALL_8:
				BorderTL 	= Tools.subImage(src, 0, 0, L, T);
				BorderT 	= Tools.subImage(src, L, 0, W - L - R, T);
				BorderTR 	= Tools.subImage(src, W - R, 0, R, T);
				BorderL 	= Tools.subImage(src, 0, T, L, H - T - B);
				BorderR 	= Tools.subImage(src, W - R, T, R, H - T - B);
				BorderBL 	= Tools.subImage(src, 0, H - B, L, B);
				BorderB 	= Tools.subImage(src, L, H - B, W - L - R, B);
				BorderBR 	= Tools.subImage(src, W - R, H - B, R, B);
//				IImage m = new CImage(src).newInstance();
//				int rgb[] = new int[1];
//				m.getRGB(rgb, 0, 1, R, T, 1, 1);
//				BackColor = new Color(rgb[0]);
				BackColor	= new Color(src.getRGB(R, T));
				break;
				
			case IMAGE_STYLE_H_012:
				BorderTL	= Tools.subImage(src, 0, 0, L, H);
				BorderT		= Tools.subImage(src, L, 0, W - R - L, H);
				BorderTR 	= Tools.subImage(src, W - R, 0, R, H);
				break;
				
			case IMAGE_STYLE_V_036:
				BorderTL 	= Tools.subImage(src, 0, 0, W, T);
				BorderL 	= Tools.subImage(src, 0, T, W, H - T - B);
				BorderBL 	= Tools.subImage(src, 0, H - B, W, B);
				break;
				
			case IMAGE_STYLE_BACK_4:
			case IMAGE_STYLE_BACK_4_CENTER:
				BackImage 	= src;
				break;
			}
			Style = style;
			
			setImages(new BufferedImage[] {
							BorderTL, BorderT,   BorderTR, 
							BorderL,  BackImage, BorderR, 
							BorderBL, BorderB,   BorderBR, }, 
							style);
		}
	}
	

	/**
	 * @param images
	 * <pre>
	 * Rect
	 * 
	 * TopLeft----------Top---------TopRight 
	 * |                                   | 
	 * Left         Background         Right 
	 * |                                   | 
	 * BottomLeft-----Bottom-----BottomRight 
	 * 
	 * images[0] images[1] images[2]         
	 * images[3] images[4] images[5]         
	 * images[6] images[7] images[8]         
	 * 
	 * 0,1,2 可以用来横向平铺
	 * 0,3,6 可以用来纵向平铺
	 * 4 可以用来缩放
	 * </pre>
	 */
	public void setImages(BufferedImage[] images, ImageStyle style)
	{
		if (images == null)
			return;

		TexturePaint[] clips = new TexturePaint[images.length];
		for (int i = 0; i < clips.length; i++) {
			if (images[i] != null) {
				clips[i] = new TexturePaint(images[i], new Rectangle(images[i].getWidth(), images[i].getHeight()));
			}
		}
		
		BorderTL = clips[0]; BorderT   = clips[1]; BorderTR = clips[2];
		BorderL  = clips[3]; BackImage = clips[4]; BorderR  = clips[5];
		BorderBL = clips[6]; BorderB   = clips[7]; BorderBR = clips[8];
		
//		if (images[0] != null)BorderTL	= new TexturePaint(images[0], new Rectangle(images[0].getWidth(), images[0].getHeight()));
//		if (images[1] != null)BorderT	= new TexturePaint(images[1], new Rectangle(images[1].getWidth(), images[1].getHeight()));
//		if (images[2] != null)BorderTR	= new TexturePaint(images[2], new Rectangle(images[2].getWidth(), images[2].getHeight()));//
//		if (images[3] != null)BorderL	= new TexturePaint(images[3], new Rectangle(images[3].getWidth(), images[3].getHeight()));
//		if (images[4] != null)BackImage	= new TexturePaint(images[4], new Rectangle(images[4].getWidth(), images[4].getHeight()));
//		if (images[5] != null)BorderR	= new TexturePaint(images[5], new Rectangle(images[5].getWidth(), images[5].getHeight()));//
//		if (images[6] != null)BorderBL	= new TexturePaint(images[6], new Rectangle(images[6].getWidth(), images[6].getHeight()));
//		if (images[7] != null)BorderB	= new TexturePaint(images[7], new Rectangle(images[7].getWidth(), images[7].getHeight()));
//		if (images[8] != null)BorderBR	= new TexturePaint(images[8], new Rectangle(images[8].getWidth(), images[8].getHeight()));//
		
		
		Style		= style;
	}
	
//	---------------------------------------------------------------------------------------------------------------
	
	public int getTopClip() {
		if (BorderTL != null) {
			return BorderTL.getImage().getHeight();
		}
		return 0;
	}

	public int getBottomClip() {
		if (BorderBL != null) {
			return BorderBL.getImage().getHeight();
		}
		return 0;
	}
	
	
	public int getLeftClip() {
		if (BorderTL != null) {
			return BorderTL.getImage().getWidth();
		}
		return 0;
	}

	public int getRightClip() {
		if (BorderTR != null) {
			return BorderTR.getImage().getWidth();
		}
		return 0;
	}
	
	
	public void render(Graphics2D g, int x, int y, int W, int H)
	{
		if (Style == ImageStyle.NULL) return;
		
		g.pushPaint();
		g.translate(x, y);
		try {
			switch(Style)
			{
			case COLOR:
				render0(g, W, H);
				break;
			case IMAGE_STYLE_ALL_8:
				renderAll8(g, W, H);
				break;
			case IMAGE_STYLE_ALL_9:
				renderAll9(g, W, H);
				break;
			case IMAGE_STYLE_H_012:
				renderH012(g, W, H);
				break;
			case IMAGE_STYLE_V_036:
				renderV036(g, W, H);
				break;
			case IMAGE_STYLE_BACK_4:
				renderBack4(g, W, H);
				break;
			case IMAGE_STYLE_BACK_4_CENTER:
				renderBack4Center(g, W, H);
				break;
			}
		} finally {
			g.popPaint();
			g.translate(-x, -y);
		}
	}
	
	protected void render0(Graphics2D g, int W, int H) 
	{
		int width  = W - (BorderSize<<1);
		int height = H - (BorderSize<<1);
		
		if ( (width > 0) && (height > 0))
		{
			g.setColor(BackColor);
			g.fillRect(BorderSize, BorderSize, width, height);
		}
		
		g.setColor(BorderColor);
		g.drawRect(0, 0, W-1, H-1);
	}
	
	protected void render0123_5678(Graphics2D g, int W, int H)
	{
			g.drawImage(BorderTL.getImage(), 
					0, 0);
			g.drawImage(BorderBL.getImage(), 0, 
					H - BorderBL.getImage().getHeight());
			g.drawImage(BorderTR.getImage(), 
					W - BorderTR.getImage().getWidth(), 0);
			g.drawImage(BorderBR.getImage(), 
					W - BorderBR.getImage().getWidth(),
					H - BorderBR.getImage().getHeight());
			

			drawPaintRect(g, BorderL,
					0, 
					BorderTL.getImage().getHeight(),
					BorderL.getImage().getWidth(), 
					H - BorderTL.getImage().getHeight() - BorderBL.getImage().getHeight());
			
			drawPaintRect(g, BorderR,
					W - BorderR.getImage().getWidth(), 
					BorderTR.getImage().getHeight(), 
					BorderR.getImage().getWidth(), 
					H - BorderTR.getImage().getHeight() - BorderBR.getImage().getHeight());
			
			drawPaintRect(g, BorderT,
					BorderTL.getImage().getWidth(), 
					0, 
					W-BorderTL.getImage().getWidth()-BorderTR.getImage().getWidth(),
					BorderT.getImage().getHeight());
			
			drawPaintRect(g, BorderB,
					BorderBL.getImage().getWidth(), 
					H - BorderB.getImage().getHeight(), 
					W - BorderBL.getImage().getWidth() - BorderBR.getImage().getWidth(), 
					BorderB.getImage().getHeight());
			

	}
	
	protected void renderAll8(Graphics2D g, int W, int H)
	{
		int mx = BorderL.getImage().getWidth();
		int my = BorderT.getImage().getHeight();
		int mw = W - BorderL.getImage().getWidth() - BorderR.getImage().getWidth();
		int mh = H - BorderT.getImage().getHeight() - BorderB.getImage().getHeight();
		
		if ( (mw > 0) && (mh > 0))
		{
			g.setColor(BackColor);
			g.fillRect(mx, my, mw, mh);
		}
		
		render0123_5678(g, W, H);
	}
	
	protected void renderAll9(Graphics2D g, int W, int H)
	{
		int mx = BorderL.getImage().getWidth();
		int my = BorderT.getImage().getHeight();
		int mw = W - BorderL.getImage().getWidth() - BorderR.getImage().getWidth();
		int mh = H - BorderT.getImage().getHeight() - BorderB.getImage().getHeight();
		
		if ( (mw > 0) && (mh > 0))
		{
			drawPaintRect(g, BackImage, mx, my, mw, mh);
		}

		render0123_5678(g, W, H);
	}
	
	protected void renderH012(Graphics2D g, int W, int H)
	{
		int bl = BorderTL.getImage().getWidth();
		int br = BorderTR.getImage().getWidth();
		//画血条时可能会用到，当血条长度小于左右块长度�?
		if (bl + br >= W) {
			float clip_scale = W / (float)(bl + br);
			int wl = (int)(bl*clip_scale);
			int wr = (int)(br*clip_scale);
			g.drawImage(BorderTL.getImage(), 
					0, 0, wl, H);
			g.drawImage(BorderTR.getImage(), 
					wl, 0, wr, H);
		} else {
			Drawing.drawRoundImageH(g, BorderT.getImage(), bl, 0, W - bl - br, H);
			g.drawImage(BorderTL.getImage(),    0, 0, bl, H);
			g.drawImage(BorderTR.getImage(), W-br, 0, br, H);
		}
	}
	
	protected void renderV036(Graphics2D g, int W, int H)
	{
		int bt = BorderTL.getImage().getHeight();
		int bb = BorderBL.getImage().getHeight();
		//画血条时可能会用到，当血条长度小于左右块长度�?
		if (bt + bb >= H) {
			float clip_scale = H / (float)(bt + bb);
			int ht = (int)(bt*clip_scale);
			int hb = (int)(bb*clip_scale);
			g.drawImage(BorderTL.getImage(), 
					0, 0, W, ht);
			g.drawImage(BorderBL.getImage(), 
					0, ht, W, hb);
		} else {
			Drawing.drawRoundImageV(g, BorderL.getImage(), 0, bt, W, H - bt - bb);
			g.drawImage(BorderTL.getImage(), 0,    0, W, bt);
			g.drawImage(BorderBL.getImage(), 0, H-bb, W, bb);
		}
	}
	
	protected void renderBack4(Graphics2D g, int W, int H)
	{
		g.drawImage(BackImage.getImage(), 0, 0, W, H);
	}
	
	protected void renderBack4Center(Graphics2D g, int W, int H)
	{
		g.drawImage(BackImage.getImage(), 
				((W - BackImage.getImage().getWidth()) >> 1),
				((H - BackImage.getImage().getHeight()) >> 1));
	}
	

	private void drawPaintRect(Graphics2D g, TexturePaint paint, int x, int y, int W, int H) 
	{
//		g.setPaint(paint);
//		g.translate(x, y);
//		g.fillRect(0, 0, W, H);
//		g.translate(-x, -y);
		
		Drawing.drawRoundImage(g, paint.getImage(), x, y, W, H);
	}
	
}
