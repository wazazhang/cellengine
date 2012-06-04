package com.g2d.display;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.geom.Shape;



public class DisplayShape extends DisplayObject 
{	
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
	
	final public void onRender(Graphics2D g) {
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
