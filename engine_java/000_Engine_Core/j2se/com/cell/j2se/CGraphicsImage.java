package com.cell.j2se;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import com.cell.CMath;
import com.cell.exception.NotImplementedException;
import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IGraphics.StringAttribute;
import com.cell.gfx.IGraphics.StringLayer;
import com.cell.j2se.AlphaComposite.AlphaBlendMode;
import com.cell.j2se.BlendComposite.BlendingMode;
import com.cell.j2se.CGraphics.CStringLayer;

/**
 * 仅支持图片绘制
 * @author WAZA
 */
class CGraphicsImage implements IGraphics 
{
	protected Graphics2D 	m_graphics2d ;
	
	CGraphicsImage(Graphics2D graphics){
		setGraphics(graphics);
	}

	final public void dispose() {
		m_graphics2d.dispose();
	}

	void setGraphics(Graphics2D graphics) {
		m_graphics2d = graphics;
	}
	
	final public Graphics2D getGraphics() {
		return m_graphics2d;
	}
	
	final  public  void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		m_graphics2d.drawArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final  public  void drawLine(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.drawLine(arg0, arg1, arg2, arg3);
	}

	final  public void drawRect(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.drawRect(arg0, arg1, arg2-1, arg3-1);
	}
	
	final  public  void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		m_graphics2d.fillArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final  public  void fillRect(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.fillRect(arg0, arg1, arg2, arg3);
	}
	
	final  public  void fillRectAlpha(int argb, int x, int y, int width, int height){
		if(argb==0)return;
		Color old = m_graphics2d.getColor();
		m_graphics2d.setColor(new Color(argb, true));
		m_graphics2d.fillRect(x, y, width, height);
		m_graphics2d.setColor(old);
	}

	final  public  void fillTriangle(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		int[] x = new int[]{arg0,arg2,arg4,};
		int[] y = new int[]{arg1,arg3,arg5,};
		m_graphics2d.fillPolygon(x,y,3);
	}

	final  public  void setColor(int arg0, int arg1, int arg2) {
		m_graphics2d.setColor(new Color(arg0, arg1, arg2, 0xff));
	}
	final  public  void setColor(int arg0) {
		m_graphics2d.setColor(new Color(arg0, true));
	}


	private Stack<java.awt.Shape> stack_clip = new Stack<java.awt.Shape>();

	final  public  void setClip(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.setClip(arg0, arg1, arg2, arg3);
	}
	
	final  public  void clipRect(int arg0, int arg1, int arg2, int arg3){
		m_graphics2d.clipRect(arg0, arg1, arg2, arg3);
	}
	
	@Override
	final public void pushClip() {
		stack_clip.push(m_graphics2d.getClip());
	}
	
	@Override
	final public void popClip() {
		m_graphics2d.setClip(stack_clip.pop());
	}
	
	final public int getClipX() {
		Rectangle b = m_graphics2d.getClipBounds();
		if (b != null)
			return b.x;
		return 0;
	}

	final public int getClipY() {
		Rectangle b = m_graphics2d.getClipBounds();
		if (b != null)
			return b.y;
		return 0;
	}

	final public int getClipHeight() {
		Rectangle b = m_graphics2d.getClipBounds();
		if (b != null)
			return b.height;
		return 0;
	}

	final public int getClipWidth() {
		Rectangle b = m_graphics2d.getClipBounds();
		if (b != null)
			return b.width;
		return 0;
	}

	private Stack<java.awt.Composite> stack_blend = new Stack<java.awt.Composite>();
	

	final public float setAlpha(float alpha) {
		float src_alpha = 1f;
		java.awt.Composite com = m_graphics2d.getComposite();
		if (com instanceof java.awt.AlphaComposite) {
			src_alpha = ((java.awt.AlphaComposite)com).getAlpha();
			m_graphics2d.setComposite(((java.awt.AlphaComposite)com).derive(alpha));
		} else if (com instanceof BlendComposite) {
			src_alpha = ((BlendComposite)com).getAlpha();
			m_graphics2d.setComposite(((BlendComposite)com).derive(alpha));
		}
		return src_alpha;
	}
	
	final public float getAlpha() {
		java.awt.Composite com = m_graphics2d.getComposite();
		if (com instanceof java.awt.AlphaComposite) {
			return ((java.awt.AlphaComposite)com).getAlpha();
		} else if (com instanceof BlendComposite) {
			return ((BlendComposite)com).getAlpha();
		}
		return 1f;
	}
	
	@Override
	final public void setBlendMode(int blend) {
		setBlendMode(blend, getAlpha());
	}
	
	@Override
	final public void setBlendMode(int blend, float alpha) {
		if (blend != IGraphics.BLEND_MODE_NONE) {
			java.awt.Composite toset_composite = this.getCompositeFrom(blend, alpha);
			if (toset_composite != null) {
				m_graphics2d.setComposite(toset_composite);
			}
		} else {
			m_graphics2d.setComposite(java.awt.AlphaComposite.SrcOver.derive(alpha));
		}
	}
	

	
	@Override
	final public void pushBlendMode() {
		stack_blend.push(m_graphics2d.getComposite());
	}
	
	@Override
	final public void popBlendMode() {
		java.awt.Composite c = stack_blend.pop();
		if (c != null) {
			m_graphics2d.setComposite(c);
		}
	}
	
	final public void transform(int transform, int width, int height)
	{
		switch (transform) 
		{
		case TRANS_ROT90: {
			m_graphics2d.translate(height, 0);
			m_graphics2d.rotate(ANGLE_90);
			break;
		}
		case TRANS_ROT180: {
			m_graphics2d.translate(width, height);
			m_graphics2d.rotate(Math.PI);
			break;
		}
		case TRANS_ROT270: {
			m_graphics2d.translate(0, width);
			m_graphics2d.rotate(ANGLE_270);
			break;
		}
		case TRANS_MIRROR: {
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT90: {
			m_graphics2d.translate(height, 0);
			m_graphics2d.rotate(ANGLE_90);
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT180: {
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			m_graphics2d.translate(width, height);
			m_graphics2d.rotate(Math.PI);
			break;
		}
		case TRANS_MIRROR_ROT270: {
			m_graphics2d.rotate(ANGLE_270);
			m_graphics2d.scale(-1, 1);
			break;
		}
		}
	}
	

	final  public  void drawImage(IImage src, int x, int y, int transform) 
	{
		Image img = ((CImage)src).getSrc();
        
        if (transform == 0)
        {      	 
        	m_graphics2d.drawImage(img, x, y, null);
        }
        else
        {
			AffineTransform savedT = m_graphics2d.getTransform();
			m_graphics2d.translate(x, y);
			transform(transform, src.getWidth(), src.getHeight());
			m_graphics2d.drawImage(img, 0, 0, null);
			m_graphics2d.setTransform(savedT);
		}
        
        AScreen.FrameImageDrawed++;
	}

	final public void drawImage(IImage src, int x, int y, int w, int h, int transform) 
	{
		Image img = ((CImage)src).getSrc();
        
        if (transform == 0)
        {      	 
        	m_graphics2d.drawImage(img, x, y, null);
        }
        else
        {
			AffineTransform savedT = m_graphics2d.getTransform();
			m_graphics2d.translate(x, y);
			transform(transform, w, h);
			m_graphics2d.drawImage(img, 0, 0, w, h, null);
			m_graphics2d.setTransform(savedT);
		}
        
        AScreen.FrameImageDrawed++;
	}
	
	final  public void drawRoundImage(IImage src, int x, int y, int width, int height, int transform) 
	{
		Image img = ((CImage)src).getSrc();

		Rectangle rect = m_graphics2d.getClipBounds();

		m_graphics2d.clipRect(x, y, width, height);

		int w = src.getWidth();
		int h = src.getHeight();

		for (int dx = 0; dx < width;) {
			for (int dy = 0; dy < height;) {
				m_graphics2d.drawImage(img, x + dx, y + dy, null);
				AScreen.FrameImageDrawed++;
				dy += h;
			}
			dx += w;
		}

		m_graphics2d.setClip(rect);
	}
	
	final  public void drawRegion(IImage src, int x_src, int y_src, int width, int height, int transform, int x_dst, int y_dst)
    {
		Image img = ((CImage)src).getSrc();

		AffineTransform savedT = m_graphics2d.getTransform();
		m_graphics2d.translate(x_dst, y_dst);
		transform(transform, width, height);
		m_graphics2d.drawImage(img, 0, 0, width, height, x_src, y_src, x_src + width, y_src + height, null);
		m_graphics2d.setTransform(savedT);
		
		AScreen.FrameImageDrawed++;

	}


	final  public  void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) 
	{
        if (rgbData == null){
        	return;
        }
        if (width == 0 || height == 0) {
        	return;
        }

    	BufferedImage buf = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		buf.setRGB(0, 0, width, height, rgbData, 0, scanlength);
        
        m_graphics2d.drawImage(buf, x, y, null);
        AScreen.FrameImageDrawed++;
    }
    
	protected final Composite getCompositeFrom(int blend_mode, float blend_alpha)
	{
		Composite composite = null;
		
		if (blend_mode != IGraphics.BLEND_MODE_NONE)
		{
			if (blend_mode < IGraphics.BLEND_MODE_ALPHA_CLEAR)
			{
				BlendingMode mode = BlendingMode.values()[blend_mode];
				composite = BlendComposite.getInstance(mode, blend_alpha);
			}
			else
			{
				AlphaBlendMode mode = AlphaBlendMode.values()[blend_mode-IGraphics.BLEND_MODE_ALPHA_CLEAR];
				if (mode != null)
					composite = java.awt.AlphaComposite.getInstance(mode.getValue(), blend_alpha);
			}
		}
		
		return composite;
	}
	
	
//	-------------------------------------------------------------------------------------------------------------------------
//
//	-------------------------------------------------------------------------------------------------------------------------

	@Deprecated
	public int getStringHeight() {
		throw new NotImplementedException();
	}
	@Deprecated
	public int getStringWidth(String src) {
		throw new NotImplementedException();
	}
	@Deprecated
	public void drawString(String str, int x, int y) {
		throw new NotImplementedException();
	}
	@Deprecated
	public void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY) {
		throw new NotImplementedException();
	}
	@Deprecated
	public void setStringAntiAllias(boolean antiallias) {
		throw new NotImplementedException();
	}
	@Deprecated
	public void setFont(String name, int size) {
		throw new NotImplementedException();
	}
	@Deprecated
	public String getFontName() {
		throw new NotImplementedException();
	}
	@Deprecated
	public void setStringSize(int size) {
		throw new NotImplementedException();
	}
	@Deprecated
	public int getFontSize() {
		throw new NotImplementedException();
	}
	@Deprecated
	public String[] getStringLines(String text, int w, int[] out_para) {
		throw new NotImplementedException();
	}
	@Deprecated
	public StringLayer createStringLayer(String src) {
		throw new NotImplementedException();
	}
	@Deprecated
	public StringLayer createStringLayer(String src, StringAttribute[] attributes) {
		throw new NotImplementedException();
	}
	
	
	
	
}
