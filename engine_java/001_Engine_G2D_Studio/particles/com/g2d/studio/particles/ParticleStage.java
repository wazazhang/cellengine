package com.g2d.studio.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import com.g2d.Canvas;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleData;
import com.g2d.display.particle.ParticleDisplay;
import com.g2d.geom.AffineTransform;
import com.g2d.studio.gameedit.EffectEditor;

public class ParticleStage extends Stage
{
	EffectEditor 	cur_edit;
	ParticleDisplay	particle;
	
	Color			back_color				= Color.BLACK;
	boolean			is_show_cross 			= false;
	boolean			is_show_spawn_region	= false;
	boolean			is_show_spawn_bounds	= false;
	
	public ParticleStage(EffectEditor cur_edit) 
	{
		this.cur_edit = cur_edit;
		this.particle = new ParticleDisplay(cur_edit.getData().particles);
		this.addChild(particle);

	}
	
	public void added(DisplayObjectContainer parent) {
		particle.setLocation(getWidth()/2, getHeight()/2);
	}
	
	public void removed(DisplayObjectContainer parent) {
	}

	public void update() {
		cur_edit.getData();
		if (getRoot().isMouseHold(MouseEvent.BUTTON1)) {
			particle.setLocation(getMouseX(), getMouseY());
			particle.spawn();
		}
		if (getRoot().isMouseDown(MouseEvent.BUTTON3)) {
			particle.setLocation(getMouseX(), getMouseY());
			particle.spawn();
		}
		if (getRoot().isMouseHold(MouseEvent.BUTTON3)) {
			particle.setLocation(getMouseX(), getMouseY());
		}
	}

	public void render(com.g2d.Graphics2D g) 
	{
		g.setColor(back_color.getRGB());
		g.fill(local_bounds);
		
		if (is_show_cross) {
			g.setColor(com.g2d.Color.GRAY);
			g.drawLine(0, particle.getY(), getWidth(), particle.getY());
			g.drawLine(particle.getX(), 0, particle.getX(), getHeight());
		}
	}
	
	@Override
	protected void renderAfter(com.g2d.Graphics2D g)
	{
		if (is_show_spawn_region) {
			g.setColor(com.g2d.Color.WHITE);
			for (Layer layer : particle.getData()) {
				com.g2d.geom.Shape shape = ParticleDisplay.getOriginShape(layer);
				double tx = particle.x;
				double ty = particle.y;
				try{
					g.translate(tx, ty);
					g.draw(shape);
				}finally{
					g.translate(-tx, -ty);
				}
			}
		}
		
		if (is_show_spawn_bounds) {
			g.setColor(com.g2d.Color.YELLOW);
			com.g2d.geom.Rectangle rect = ParticleDisplay.getOriginBounds(particle.getData());
			double tx = particle.x;
			double ty = particle.y;
			try{
				g.translate(tx, ty);
				g.draw(rect);
			}finally{
				g.translate(-tx, -ty);
			}
		}
	}

	
}
