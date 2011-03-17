package com.g2d.jogl.impl;

import java.awt.RenderingHints;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.media.opengl.GL;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IGraphics.StringAttribute;
import com.cell.gfx.IGraphics.StringLayer;
import com.g2d.BasicStroke;
import com.g2d.Color;
import com.g2d.Composite;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.RadialGradientPaint;
import com.g2d.Stroke;
import com.g2d.geom.Path2D;
import com.g2d.geom.PathIterator;
import com.g2d.geom.Polygon;
import com.g2d.geom.Rectangle2D;

abstract class GLGraphics2D extends Graphics2D
{
	final private GL 								gl;
	final java.awt.Graphics2D						awt_g2d;
	
	private Color									cur_color;
	private float[] 								cur_color_4f;
	private Font									cur_font;
	private int										cur_font_h;
	
	private Stack<java.awt.Shape> 					stack_clip 		= new Stack<java.awt.Shape>();
	private Stack<java.awt.Composite> 				stack_comp 		= new Stack<java.awt.Composite>();
	private Stack<java.awt.geom.AffineTransform> 	stack_trans		= new Stack<java.awt.geom.AffineTransform>();
	private Stack<java.awt.Stroke> 					stack_stroke	= new Stack<java.awt.Stroke>();
	private Stack<java.awt.Paint> 					stack_paint		= new Stack<java.awt.Paint>();


	GLGraphics2D(GL gl, java.awt.Graphics2D awt_g2d) 
	{
		this.awt_g2d 		= awt_g2d;
		this.cur_color		= new Color(awt_g2d.getColor().getRGB());
		this.cur_font		= new GLFont(awt_g2d.getFont());
		this.cur_font_h 	= awt_g2d.getFontMetrics().getHeight();
		
		this.gl = gl;
		this.cur_color_4f	= new float[] {
			cur_color.getRed(),
			cur_color.getGreen(),
			cur_color.getBlue(),
			cur_color.getAlpha(),
		};
		gl.glGetFloatv(GL.GL_COLOR, cur_color_4f, 0);
		
	}
	
	@Override
	public void dispose() {
		awt_g2d.dispose();
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
//	blending
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setComposite(Composite comp) {}
	public void pushComposite() {}
	public void popComposite() {}
	public void popBlendMode() {}
	public void pushBlendMode() {}
	public void setBlendMode(int blend, float alpha) {}
	public void setBlendMode(int blend) {}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	paint
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setPaint(Paint paint) {}
	public void pushPaint() {}
	public void popPaint() {}

//	-------------------------------------------------------------------------------------------------------------------------
//	stroke
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setStroke(Stroke s) {}
	public void pushStroke() {}
	public void popStroke() {}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	color
//	-------------------------------------------------------------------------------------------------------------------------
	
	public Color getColor() {
		return this.cur_color;
	}
	public void setColor(Color c) {
		this.cur_color_4f[0] = c.getRed();
		this.cur_color_4f[1] = c.getGreen();
		this.cur_color_4f[2] = c.getBlue();
		this.cur_color_4f[3] = c.getAlpha();
		this.gl.glGetFloatv(GL.GL_COLOR, cur_color_4f, 0);
		this.cur_color = c;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	clip
//	-------------------------------------------------------------------------------------------------------------------------

	final public int getClipX() {
		return 0;
	}
	final public int getClipY() {
		return 0;
	}
	final public int getClipHeight() {
		return 0;
	}
	final public int getClipWidth() {
		return 0;
	}
	
	final public void clipRect(int x, int y, int width, int height) {}
	
	final public void setClip(int x, int y, int width, int height) {}
	
	@Override
	public void pushClip() {}
	
	@Override
	public void popClip() {}

	public boolean hitClip(int x, int y, int width, int height){
		return true;
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	transform
//	-------------------------------------------------------------------------------------------------------------------------

	public void translate(int x, int y) {
		gl.glTranslatef(x, y, 0f);
	}
	public void translate(double tx, double ty) {
		gl.glTranslated(tx, ty, 0d);
	}
	public void rotate(double theta) {
		gl.glRotated(Math.toDegrees(theta), 0, 0, 1d);
	}
	public void rotate(double theta, double x, double y) {
		gl.glRotated(Math.toDegrees(theta), x, y, 1d);
	}
	public void scale(double sx, double sy) {
		gl.glScaled(sx, sy, 0);
	}
	public void shear(double shx, double shy) {
	
	}
	@Override
	public void pushTransform() {
		gl.glPushMatrix();
	}
	@Override
	public void popTransform() {
		gl.glPopMatrix();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	base shape
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawLine(int x1, int y1, int x2, int y2) {
	}
	public void drawPath(Path2D path) {
	}
	public void fillPath(Path2D path) {
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void fillRect(int x, int y, int width, int height){}
	public void fillRect(Rectangle2D rect) {}
	public void drawRect(int x, int y, int width, int height){}
	public void drawRect(Rectangle2D rect){}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	round rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	arc
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}

	
//	-------------------------------------------------------------------------------------------------------------------------
//	polygon
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {}
	public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {}
	public void drawPolygon(Polygon p) {}
	public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {}
	public void fillPolygon(Polygon p) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	chars
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawChars(char data[], int offset, int length, int x, int y) {}
	public void drawString(String str, int x, int y) {}
	public void drawString(String str, float x, float y) {}
	public void drawString(AttributedString atext, int x, int y) {}
	public void drawString(AttributedString atext, float x, float y) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	image
//	-------------------------------------------------------------------------------------------------------------------------
	
	public boolean drawImage(Image img, int x, int y) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, x, y, buff.getWidth(), buff.getHeight(), 0, 0, buff.getWidth(), buff.getHeight());
		return true;
	}

	public boolean drawImage(Image img, int x, int y, int width, int height) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, x, y, width, height, 0, 0, buff.getWidth(), buff.getHeight());
		return true;
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, dx1, dy1, dx2-dx1, dy2-dy1, sx1, sy1, dx2-dx1, dy2-dy1);
		return true;
	}

	public void drawImage(IImage img, int x, int y, int w, int h, int transform) {
		pushTransform();
		transform(transform, w, h);
		drawImage((Image)img, x, y, w, h);
		popTransform();
	}
	
	public void drawImage(IImage src, int x, int y, int transform) {
		pushTransform();
		transform(transform, src.getWidth(), src.getHeight());
		drawImage((Image)src, x, y);
		popTransform();
	}

	public void drawRegion(IImage src, int xSrc, int ySrc, int width, int height, int transform, int xDest, int yDest) {
		pushTransform();
		transform(transform, width, height);
		GLImage buff = (GLImage)src;
		buff.draw(gl, xDest, yDest, width, height, xSrc, ySrc, width, height);
		popTransform();
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	flag
//	-------------------------------------------------------------------------------------------------------------------------

	public float setAlpha(float alpha) {
		return 1f;
	}

	public float getAlpha() {
		return 1f;
	}

	public boolean setFontAntialiasing(boolean enable) {
		return false;
	}

	public boolean getFontAntialiasing() {
		return false;
	}


//	-------------------------------------------------------------------------------------------------------------------------
//	string layer
//	-------------------------------------------------------------------------------------------------------------------------

	public Font getFont() {
		return cur_font;
	}
	
	public void setFont(Font font) {
		this.cur_font = font;
	}
	
	public void setFont(String name, int size) {
		this.cur_font = GLEngine.getEngine().createFont(name, 0, size);
	}
	
	public int getStringHeight() {
		return cur_font_h;
	}
	
	public int getStringWidth(String src) {
		return (int)awt_g2d.getFontMetrics().getStringBounds(src, awt_g2d).getWidth();
	}
	
	@Override
	public String[] getStringLines(String text, int w, int[] out_para) {
		return null;
	}

	@Override
	public StringLayer createStringLayer(String src) {
		return null;
	}

	@Override
	public StringLayer createStringLayer(String src, StringAttribute[] attributes) {
		return null;
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	2 impl
//	-------------------------------------------------------------------------------------------------------------------------

	static class GLGraphicsPBuffer extends GLGraphics2D
	{
		public GLGraphicsPBuffer(GL gl, java.awt.Graphics2D g2d) {
			super(gl, g2d);
		}
	}
	
	static class GLGraphicsScreen extends GLGraphics2D
	{
		public GLGraphicsScreen(GL gl, java.awt.Graphics2D g2d) {
			super(gl, g2d);
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	blending
//	-------------------------------------------------------------------------------------------------------------------------
	

	
	
	
	
	
	
}
