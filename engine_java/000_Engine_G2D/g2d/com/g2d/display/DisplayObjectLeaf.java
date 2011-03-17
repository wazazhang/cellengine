package com.g2d.display;

import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * 用于快速渲染的根节点，
 * 该节点不会Clip任何区域，该节点也不处理任何触碰事件。
 */
public abstract class DisplayObjectLeaf extends DisplayObject
{
	void onUpdate(DisplayObjectContainer parent) 
	{
		this.parent 			= parent;
		this.root 				= parent.root;
		this.timer ++;
		this.interval_ms		= parent.interval_ms;
		this.refreshScreen(parent);
		this.update();
	}
	
	final void updateBefore(DisplayObjectContainer parent) {}
	final void updateAfter(DisplayObjectContainer parent) {}
	
	void onRender(Graphics2D g) 
	{
		if (visible) 
		{
			AffineTransform	transfrom	= g.getTransform();
			Composite		composite	= g.getComposite();
			try {
				g.translate(x, y);
				this.render(g);
			} finally {
				g.setComposite(composite);
				g.setTransform(transfrom);
			}
		}
	}
	
	final protected void renderBefore(Graphics2D g){}
	final protected void renderAfter(Graphics2D g) {}
	final protected void renderInteractive(Graphics2D g){}
	final protected void renderDebug(Graphics2D g) {}
	

}
