package com.g3d.jogl.test;

import java.awt.Color;
import java.awt.Font;

import javax.media.opengl.GL;

import com.sun.opengl.util.j2d.TextRenderer;

public class TextLayer 
{
	TextRenderer tr;
	
	public TextLayer() 
	{
		tr = new TextRenderer(new Font("SansSerif", Font.BOLD, 32));

	}
	
	public void draw(GL gl, float x, float y) 
	{
		tr.begin3DRendering();
		tr.setColor(Color.RED);
		tr.draw3D("asdf asf a\nsf", x, y, 0, 1f);
		tr.end3DRendering();
	}

}
