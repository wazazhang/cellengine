package com.g2d.studio.scene.effect;


import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.cell.game.SceneSprite;
import com.g2d.display.Sprite;
import com.g2d.studio.scene.units.SceneUnitTag;

public class AbilityEffectInfo extends Sprite
{
	Class<?>		ability_type;

	BufferedImage 	image ;
	
	AbilityEffectInfo(Class<?> atype, BufferedImage image) 
	{
		this.ability_type	= atype;
		this.image			= image;
		this.setSize(image.getWidth(), image.getHeight());
		this.setLocalBounds(
				-image.getWidth()/2, 
				-image.getHeight()/2,
				image.getWidth(), 
				image.getHeight());
	}
	
	boolean updateActor(SceneUnitTag<?> unit) {
		if (unit.getUnit().getAbility(ability_type)!=null) {
			if (unit.getGameUnit().contains(this)==false) {
				unit.getGameUnit().addChild(this);
			}
			return true;
		} else {
			if (unit.getGameUnit().contains(this)) {
				unit.getGameUnit().removeChild(this);
			}
			return false;
		}
	}
	
	@Override
	public void render(Graphics2D g)
	{
		super.render(g);
		
		if (getParent() instanceof SceneSprite) {
			setLocation(0, getParent().local_bounds.y);
		}
		g.drawImage(image, local_bounds.x, local_bounds.y);
	}
	
}
