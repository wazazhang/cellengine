package com.g2d;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;

final public class SimpleCanvas extends Panel implements CanvasContainer
{
	private static final long serialVersionUID = Version.VersionG2D;

	final CanvasAdapter canvas_adapter;
	
	public SimpleCanvas()
	{		
		this.setLayout(null);

		canvas_adapter = new CanvasAdapter(this);
	}
	
	public SimpleCanvas(int width, int height)
	{
		super.setSize(width, height);

		this.setLayout(null);

		canvas_adapter = new CanvasAdapter(this);
	}
	
//	--------------------------------------------------------------------------------
	
	public CanvasAdapter getCanvasAdapter() {
		return canvas_adapter;
	}
	
	
	public void update(Graphics dg) 
	{
		Graphics2D g = (Graphics2D)dg;
		
		canvas_adapter.update(g.getDeviceConfiguration());

		Image vm_buffer = canvas_adapter.getVMBuffer();
		
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	public void paint(Graphics dg)
	{
		Graphics2D g = (Graphics2D)dg;
		Image vm_buffer = canvas_adapter.getVMBuffer();
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}

//	--------------------------------------------------------------------------------
	
	@Override
	public Component getContainer() {
		return this;
	}	
	
	@Override
	public Component getComponent() {
		return this;
	}
	@Override
	public void superPaint(Graphics g) {
		super.paint(g);
	}
	@Override
	public void superUpdate(Graphics g) {
		super.update(g);
	}
	
}
