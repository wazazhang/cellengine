package com.g2d.jogl.impl;

import javax.media.opengl.GL;

import com.cell.exception.NotImplementedException;
import com.g2d.Composite;
import com.g2d.Graphics2D;

public class GLComposite implements Composite
{
	final int 		sfactor;
	final int 		dfactor;
	final float 	alpha;
	
	public GLComposite(int blend, float alpha) {
		switch (blend) {
		// 滤色ALPHA混白
		case Graphics2D.BLEND_MODE_SCREEN:
			this.sfactor	= GL.GL_SRC_ALPHA;
			this.dfactor	= GL.GL_ONE;
			break;
		// 是最常使用的。
		default:
			this.sfactor	= GL.GL_SRC_ALPHA;
			this.dfactor	= GL.GL_ONE_MINUS_SRC_ALPHA;
			break;
		}
		this.alpha		= alpha;
	}
	
	public GLComposite(float alpha) {
		this(Graphics2D.BLEND_MODE_NONE, alpha);
	}
	
//	void changeComposite(GL gl)
//	{
//		gl.glBlendFunc(GL.GL_ONE_MINUS_SRC_COLOR, GL.GL_ONE_MINUS_SRC_ALPHA);
//		gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_SRC_COLOR);
//		gl.glBlendFunc(GL.GL_ONE, GL.GL_SRC_ALPHA);
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE); // 滤色ALPHA混白
//		gl.glBlendFunc(GL.GL_ONE , GL.GL_ZERO ); // 源色将覆盖目标色。
//		gl.glBlendFunc(GL.GL_ZERO , GL.GL_ONE ); // 目标色将覆盖源色。 
//		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA); // 是最常使用的。
//		gl.glBlendFunc(GL.GL_SRC_ALPHA_SATURATE, GL.GL_ONE);
//		gl.glBlendFunc(sfactor, dfactor);
//		gl.glColor4f(1f, 1f, 1f, alpha);
//		gl.glEnable(GL.GL_ALPHA_TEST);
//		gl.glAlphaFunc(GL.GL_GREATER, 0.5f);
//	}
	
}
