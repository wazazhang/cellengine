package com.g2d.jogl.impl;

import java.awt.RenderingHints;
import java.awt.Shape;
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

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

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
import com.g2d.geom.Rectangle;
import com.g2d.geom.Rectangle2D;

abstract class GLGraphics2D extends Graphics2D
{
	final private GL 								gl;
	final java.awt.Graphics2D						awt_g2d;
	
	private Color									cur_color;
	private float[] 								cur_color_4f;
	private Font									cur_font;
	private int										cur_font_h;
	private GLComposite								cur_composite;
	private java.awt.Rectangle						cur_clip;
	
	private Stack<GLComposite> 						stack_comp 		= new Stack<GLComposite>();
	private Stack<java.awt.Rectangle> 				stack_clip 		= new Stack<java.awt.Rectangle>();
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
		
		setAlpha(1f);

		_clip(awt_g2d.getClipBounds());
		
		// test
		pushTransform();
		pushClip();
		{
			setAlpha(.5f);

			setColor(Color.GREEN);
			{
				rotate(.1);
				translate(20, 20);
				test_draw(10, 10);
			}
			setColor(Color.RED);
			{
				translate(20, 20);
				test_draw(10, 10);
				test_draw(20, 20);
			}
			setColor(Color.YELLOW);
			{
				translate(100, 100);
				scale(2, 2);
				rotate(.1);
				test_draw(10, 10);
//				System.out.println(
//						getClipX() + "," + getClipY() + "," + 
//						getClipWidth() + "," + getClipHeight()) ;
			}
		}
		popClip();
		popTransform();
	}
	
	private void test_draw(int x, int y)
	{
		setClip(x, y, 100, 100);
		drawRect(x, y, 100, 100);
		fillRect(x + 10, y + 10, 10, 10);
		
		fillRect(x - 20, y + 50, 200, 20);
		fillRect(x + 50, y - 20, 20, 200);
	}
	
	@Override
	public void dispose()
	{		
		gl.glDisable(GL.GL_SCISSOR_TEST);
		gl.glDisable(GL.GL_CLIP_PLANE0);
		gl.glDisable(GL.GL_CLIP_PLANE1);
		gl.glDisable(GL.GL_CLIP_PLANE2);
		gl.glDisable(GL.GL_CLIP_PLANE3);
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
//	blending
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void pushComposite() {
		stack_comp.push(cur_composite);
	}
	public void popComposite() {
		if (!stack_comp.isEmpty()) {
			cur_composite = stack_comp.pop();
			gl.glBlendFunc(cur_composite.sfactor, cur_composite.dfactor);
		}
	}
	public void pushBlendMode() {
		pushComposite();
	}
	public void popBlendMode() {
		popComposite();
	}
	public void setBlendMode(int blend, float alpha) {
		cur_composite = new GLComposite(blend, alpha);
		gl.glBlendFunc(cur_composite.sfactor, cur_composite.dfactor);
	}
	public void setBlendMode(int blend) {
		cur_composite = new GLComposite(blend, 1f);
		gl.glBlendFunc(cur_composite.sfactor, cur_composite.dfactor);
	}

	public float setAlpha(float alpha) {
		float ret = 1f;
		if (cur_composite != null) {
			ret = cur_composite.alpha;
		}
		cur_composite = new GLComposite(alpha);
		gl.glBlendFunc(cur_composite.sfactor, cur_composite.dfactor);
		return ret;
	}
	public float getAlpha() {
		float ret = 1f;
		if (cur_composite != null) {
			ret = cur_composite.alpha;
		}
		return ret;
	}

	public boolean setFontAntialiasing(boolean enable) {
		return false;
	}

	public boolean getFontAntialiasing() {
		return false;
	}

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
		this.cur_color = c;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	clip
//	-------------------------------------------------------------------------------------------------------------------------

	final public int getClipX() {
		return cur_clip.x;
	}
	final public int getClipY() {
		return cur_clip.y;
	}
	final public int getClipHeight() {
		return cur_clip.height;
	}
	final public int getClipWidth() {
		return cur_clip.width;
	}

	final public void clipRect(int x, int y, int width, int height) {
		cur_clip = cur_clip.intersection(new java.awt.Rectangle(x, y, width, height));
		_clip(cur_clip);
	}
	
	final public void setClip(int x, int y, int width, int height) {
		cur_clip = new java.awt.Rectangle(x, y, width, height);
		_clip(cur_clip);
	}
	
	@Override
	public void pushClip() {
		stack_clip.push(cur_clip);
	}
	
	@Override
	public void popClip() {
		cur_clip = stack_clip.pop();
		if (cur_clip != null) {
			_clip(cur_clip);
		}
	}

	public boolean hitClip(int x, int y, int width, int height){
		return awt_g2d.hitClip(x, y, width, height);
	}
	
	private void _clip(java.awt.Rectangle b) 
	{
		cur_clip = b;
		
		double[] e0 = { -1, 0, 0.0, b.x + b.width};
		gl.glClipPlane(GL.GL_CLIP_PLANE0, e0, 0);
		gl.glEnable(GL.GL_CLIP_PLANE0);
		
		double[] e1 = {  1, 0, 0.0, -b.x};
		gl.glClipPlane(GL.GL_CLIP_PLANE1, e1, 0);
		gl.glEnable(GL.GL_CLIP_PLANE1);
		
		double[] e2 = { 0, -1, 0.0, b.y + b.height};
		gl.glClipPlane(GL.GL_CLIP_PLANE2, e2, 0);
		gl.glEnable(GL.GL_CLIP_PLANE2);
		
		double[] e3 = { 0,  1, 0.0, -b.y};
		gl.glClipPlane(GL.GL_CLIP_PLANE3, e3, 0);
		gl.glEnable(GL.GL_CLIP_PLANE3);

//		gl.glScissor(b.x, b.y, b.width, b.height);
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
		gl.glScaled(sx, sy, 1);
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
		gl.glBegin(GL.GL_LINE);
		gl.glColor4f(
				cur_color_4f[0], 
				cur_color_4f[1], 
				cur_color_4f[2],
				cur_color_4f[3] * cur_composite.alpha);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
	}
	
	public void drawPath(Path2D path) {
		for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
			
		}
	}
	
	public void fillPath(Path2D path) {
		for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
			
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void fillRect(int x, int y, int width, int height) {
		gl.glBegin(GL.GL_QUADS);
		gl.glColor4f(
				cur_color_4f[0], 
				cur_color_4f[1], 
				cur_color_4f[2],
				cur_color_4f[3] * cur_composite.alpha);
		// 第一个顶点的坐标
		gl.glVertex2f(x, y);
		gl.glVertex2f(x, y + height);
		gl.glVertex2f(x + width, y + height);
		gl.glVertex2f(x + width, y);
		gl.glEnd();
	}

	public void fillRect(Rectangle2D rect) {
		double x = rect.getX(), y = rect.getY(), width = rect.getWidth(), height = rect.getHeight();
		gl.glBegin(GL.GL_QUADS);
		gl.glColor4f(
				cur_color_4f[0], 
				cur_color_4f[1], 
				cur_color_4f[2],
				cur_color_4f[3] * cur_composite.alpha);
		// 第一个顶点的坐标
		gl.glVertex2d(x, y);
		gl.glVertex2d(x, y + height);
		gl.glVertex2d(x + width, y + height);
		gl.glVertex2d(x + width, y);
		gl.glEnd();
	}

	public void drawRect(int x, int y, int width, int height) {
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor4f(
				cur_color_4f[0], 
				cur_color_4f[1], 
				cur_color_4f[2],
				cur_color_4f[3] * cur_composite.alpha);
		// 第一个顶点的坐标
		gl.glVertex2f(x, y);
		gl.glVertex2f(x, y + height);
		gl.glVertex2f(x + width, y + height);
		gl.glVertex2f(x + width, y);
		gl.glVertex2f(x, y);
		gl.glEnd();
	}

	public void drawRect(Rectangle2D rect) {
		double x = rect.getX(), y = rect.getY(), width = rect.getWidth(), height = rect.getHeight();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor4f(
				cur_color_4f[0], 
				cur_color_4f[1], 
				cur_color_4f[2],
				cur_color_4f[3] * cur_composite.alpha);
		// 第一个顶点的坐标
		gl.glVertex2d(x, y);
		gl.glVertex2d(x, y + height);
		gl.glVertex2d(x + width, y + height);
		gl.glVertex2d(x + width, y);
		gl.glVertex2d(x, y);
		gl.glEnd();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	round rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	arc
//	-------------------------------------------------------------------------------------------------------------------------
//	To draw a circle
//	OpenGL does not have any primitives for drawing curves or circles.
//	However we can approximate a circle using a triangle fan like this:
//	 glBegin(GL_TRIANGLE_FAN)
//	 glVertex2f(x1, y1)
//	 for angle# = 0 to 360 step 5
//	 glVertex2f(x1 + sind(angle#) * radius#, y1 + cosd(angle#) * radius#)
//	 next
//	 glEnd()
//	Or for a hollow circle:
//	 glBegin(GL_LINE_LOOP)
//	 for angle# = 0 to 360 step 5
//	 glVertex2f(x1 + sind(angle#) * radius#, y1 + cosd(angle#) * radius#)
//	 next
//	 glEnd()
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
//		 // 移动操作，移入屏幕(Z轴)5个像素, x, y , z  
//        gl.glTranslatef(0.0f, 0.0f, -5.0f);  
//          
//        //旋转, angle, x, y , z  
//        gl.glRotatef(rotateAngle, 1.0f, 0.0f, 0.0f);  
//  
//        // 设置当前色为红色, R, G, B, Alpha  
//        gl.glColor4f(1.0f, 0.1f, 0.1f, 1.0f);  
//          
//        //设置圆形顶点数据，这个是在创建时生成  
//        FloatBuffer verBuffer = FloatBuffer.wrap(vertices);  
//  
//        //设置顶点类型为浮点坐标(GL_FLOAT)，不设置或者设置错误类型将导致图形不能显示或者显示错误  
//        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, verBuffer);  
//  
//        //打开顶点数组  
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
//          
//        //向OGL发送实际画图指令  
//        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 360);  
//          
//        //关闭顶点数组功能  
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
//  
//        //画图结束  
//        gl.glFinish();  
//          
//        //更改旋转角度  
//        rotateAngle += 0.5;  
	}
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		
	}
	
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
		buff.draw(gl, x, y, buff.getWidth(), buff.getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), 
				cur_composite.alpha);
		return true;
	}

	public boolean drawImage(Image img, int x, int y, int width, int height) {
		GLImage buff = (GLImage)img;
		gl.glColor4f(1f, 1f, 1f, cur_composite.alpha);
		buff.draw(gl, x, y, width, height, 0, 0, buff.getWidth(), buff.getHeight(), 
				cur_composite.alpha);
		return true;
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		GLImage buff = (GLImage)img;
		gl.glColor4f(1f, 1f, 1f, cur_composite.alpha);
		buff.draw(gl, dx1, dy1, dx2-dx1, dy2-dy1, sx1, sy1, dx2-dx1, dy2-dy1, 
				cur_composite.alpha);
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
		gl.glColor4f(1f, 1f, 1f, cur_composite.alpha);
		buff.draw(gl, xDest, yDest, width, height, xSrc, ySrc, width, height, 
				cur_composite.alpha);
		popTransform();
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
		@Override
		public void dispose() {
			super.dispose();
			awt_g2d.dispose();
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
