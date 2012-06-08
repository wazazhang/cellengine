package com.g2d.studio.scene.scene_map;

import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.WorldSet.ImageObject;
import com.cell.gameedit.object.WorldSet.SpriteObject;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.cell.game.Scene;
import com.g2d.cell.game.Scene.WorldMap;
import com.g2d.cell.game.Scene.WorldObjectImage;
import com.g2d.cell.game.Scene.WorldObjectSprite;
import com.g2d.game.rpg.Unit;

public class SceneMap extends WorldMap
{
	public static float default_mask_alpha = 0.5f;
	public static int default_mask_color = 0x808080;
	
	public SceneMap(Scene scene, SetResource resource, String worldname) 
	{
		super(scene, resource, resource.WorldTable.get(worldname));
	}
	
	@Override
	protected Unit createWorldObject(SetResource set, SpriteObject worldSet) {
		return new EatWorldObject(set, worldSet);
	}
	
	@Override
	protected Unit createWorldObject(SetResource set, ImageObject worldSet) {
		return new EatWorldTile(set, worldSet);
	}
	
	
	public static class EatWorldTile extends WorldObjectImage
	{
		public EatWorldTile(SetResource set, ImageObject worldSet) {
			super(set, worldSet);
		}
	}
	
	public static class EatWorldObject extends WorldObjectSprite
	{
		public boolean is_mask = false;
		int old_color = 0;
		float old_alpha = 0;
		BufferedImage static_buffer;
		
		public EatWorldObject(SetResource set, SpriteObject worldSet) {
			super(set, worldSet);
		}
		
		@Override
		public void loaded(SetResource set, CSprite cspr,
				com.cell.gameedit.object.SpriteSet spr) {
			super.loaded(set, cspr, spr);
		}
		
		@Override
		public void render(Graphics2D g) 
		{
			if (csprite != null)
			{
				if (is_mask) {
					if (old_color != default_mask_color || 
						old_alpha != default_mask_alpha) {
						old_color = default_mask_color;
						old_alpha = default_mask_alpha;
						static_buffer = null;
					}
				}
				CCD cd = csprite.getFrameBounds();
				if (static_buffer == null) {
					static_buffer = com.g2d.Tools.createImage(cd.getWidth(), cd.getHeight());
					Graphics2D g2d = static_buffer.createGraphics();
					csprite.render(g2d, -cd.X1, -cd.Y1);
					g2d.dispose();
					if (is_mask) {
						static_buffer = com.g2d.Tools.createAlpha(static_buffer,
								default_mask_alpha, 
								default_mask_color);
					}
				}
				if (static_buffer != null) {
					g.drawImage(static_buffer, cd.X1, cd.Y1);
				}
			}
		}
	}
	
}
