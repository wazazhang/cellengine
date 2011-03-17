package com.cell.rpg.particle;

import java.io.Serializable;

import com.cell.CMath;
import com.cell.gfx.game.CSprite;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.annotation.Property;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleAppearance;

public enum ParticleAppearanceType
{
	IMAGE(DisplayNodeImage.class), 
	SPRITE(DisplayNodeSprite.class), 
	;

//	-----------------------------------------------------------------------------------------------
	
	final private Class<? extends ParticleAppearance> type;
	
	ParticleAppearanceType(Class<? extends ParticleAppearance> type) {
		this.type = type;
	}
	
	public Class<? extends ParticleAppearance> getType() {
		return type;
	}
	
//	-----------------------------------------------------------------------------------------------
	
	public static class DisplayNodeImage implements ParticleAppearance, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		/** CPJ工程名 */
		public String	cpj_project_name;
		
		/** CPJ图片组名*/
		public String	cpj_sprite_name;
		
		/** CPJ图片组图片编号*/
		public int		cpj_image_id;

		@Property("混合方法")
		public int		blend_mode = 0;

		private transient Image image;
		
		public void setImage(Image src) {
			this.image = src;
		}

		synchronized public Image getImage() {
			return image;
		}

		synchronized public DisplayNodeImage cloneDisplay() {
			return this;
		}
		
		@Override
		public void render(Graphics2D g, Layer layer) {
			g.pushBlendMode();
			try  {
				g.setBlendMode(blend_mode);
				if (getImage() != null) {
					g.drawImage(getImage(), -getImage().getWidth() >> 1, -getImage().getHeight() >> 1);
				} else {
					g.setColor(Color.WHITE);
					g.drawArc(-2, -2, 4, 4, 0, 360);
				}
			} finally {
				g.popBlendMode();
			}
		}
	}
	
	public static class DisplayNodeSprite implements ParticleAppearance, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		/** CPJ工程名 */
		public String	cpj_project_name;
		
		/** CPJ图片组名*/
		public String	cpj_sprite_name;
		
		/** 精灵动画号 */
		public int		sprite_anim;
		
		@Property("混合方法")
		public int		blend_mode = 0;

		transient private CSprite	sprite;
		transient private int		st_current_timer;

		public DisplayNodeSprite cloneDisplay() {
			DisplayNodeSprite ret = new DisplayNodeSprite();
			ret.st_current_timer 	= st_current_timer;
			ret.sprite_anim 		= sprite_anim;
			ret.sprite 				= sprite;
			ret.blend_mode 			= blend_mode;
			return ret;
		}
		
		synchronized public void setSprite(CSprite src) {
			this.sprite = src;
		}

		synchronized public CSprite getSprite() {
			return sprite;
		}

		@Override
		public void render(Graphics2D g, Layer layer) {
			g.pushBlendMode();
			try  {
				g.setBlendMode(blend_mode);
				if (sprite != null) {
					int anim = CMath.cycNum(sprite_anim, 0, sprite.getAnimateCount());
					int fram = CMath.cycNum(st_current_timer, 0, sprite.getFrameCount(anim));
					sprite.render(g, 0, 0, anim, fram);
					st_current_timer ++;
				} else {
					g.setColor(Color.WHITE);
					g.drawArc(-2, -2, 4, 4, 0, 360);
				}
			} finally {
				g.popBlendMode();
			}
		}
	}
}
