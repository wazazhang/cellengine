package com.cell.rpg.scene;

import com.g2d.annotation.Property;

public abstract class SceneSprite extends SceneUnit
{
	@Property("触碰范围")
	public int			touch_range		= 30;
	
	public int			animate;
	@Property("动画行为")
	public String		animate_intention;
	
	public SceneSprite(String id) {
		super(id);
	}
}
