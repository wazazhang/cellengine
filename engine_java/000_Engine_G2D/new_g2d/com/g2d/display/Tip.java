package com.g2d.display;

import com.g2d.Graphics2D;
import com.g2d.geom.Point;



public abstract class Tip extends DisplayObjectContainer
{	
	public Tip() {
		setSize(10, 10);
	}
	
	/**
	 * 该tip自定义的anchor，返回空，则使用stage的anchor
	 * @return
	 */
	public TipAnchor getTipAnchor() {
		return null;
	}
	
	@Override
	final protected boolean testCatchMouse(Graphics2D g) {
		return false;
	}
	
	@Override
	final public void added(DisplayObjectContainer parent) {
	}
	
	@Override
	final public void removed(DisplayObjectContainer parent) {
	}
}
