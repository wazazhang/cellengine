package com.cell.rpg.template;

import com.g2d.display.particle.ParticleData;


public class TEffect extends TemplateNode
{	
	final public ParticleData particles = new ParticleData();
	
	public TEffect(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
	
}
