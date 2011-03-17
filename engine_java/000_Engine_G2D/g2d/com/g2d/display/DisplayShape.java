package com.g2d.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import com.g2d.Version;

public class DisplayShape extends DisplayObject 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public Shape 		shape;
	public Color 		color;
	public boolean 		enable_fill = false;
	
	public DisplayShape(Shape shape, Color color) 
	{
		this.shape = shape;
		this.color = color;
		this.local_bounds.setBounds(this.shape.getBounds());
	}

	final protected void onUpdate(DisplayObjectContainer parent) {
		super.onUpdate(parent);
	}
	
	final protected void onRender(Graphics2D g) {
		super.onRender(g);
	}
	
	public void added(DisplayObjectContainer parent) {}
	public void removed(DisplayObjectContainer parent) {}
	public void update() {}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		if (enable_fill) {
			g.fill(shape);
		}else{
			g.draw(shape);
		}
	}

	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
}
