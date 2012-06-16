package com.g2d.cell.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gfx.IGraphics.ImageTrans;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CSprite;
import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.game.rpg.Unit;

public class Scene extends com.g2d.game.rpg.Scene 
{
	public Scene() {
	}
	
	public Scene(WorldMap world) {
		super(world);
	}
	
	@SuppressWarnings("deprecation")
	public Scene(SetResource resource, String worldname) 	{
		addChild(new WorldMap(this, resource, resource.WorldTable.get(worldname)));
	}
	

	
	
//	-----------------------------------------------------------------------------------------------------------

	public static class WorldMap extends com.g2d.game.rpg.SceneMap
	{
		final protected SetResource			set_resource;
		final protected WorldSet	set_world;

		public WorldMap(Scene scene, SetResource resource, WorldSet set_world) 
		{
			super(scene, 
					set_world.GridW, 
					set_world.GridH, 
					set_world.GridXCount, 
					set_world.GridYCount, 
					set_world.Terrian);

			this.set_resource 	= resource;
			this.set_world		= set_world;
			
			for (WorldSet.SpriteObject wspr : sortWorldObject(new ArrayList<WorldSet.SpriteObject>(set_world.Sprs.values()))){
				Unit cs = createWorldObject(resource, wspr);
				if (cs != null) {
					addChild(cs);
				}
			}
			for (WorldSet.ImageObject wspr : sortWorldObject(new ArrayList<WorldSet.ImageObject>(set_world.Imgs.values()))){
				Unit cs = createWorldObject(resource, wspr);
				if (cs != null) {
					addChild(cs);
				}
			}
		}
		
		/**
		 * 自定义地图单元的加载顺序
		 * @param objects
		 * @return
		 */
		protected<T> List<T> sortWorldObject(List<T> objects) {
			return objects;
		}
		
		/**
		 * 返回自定义的地图单位实现
		 * @param set
		 * @param world_set
		 * @return
		 */
		protected Unit createWorldObject(SetResource set, WorldSet.SpriteObject world_set) {
			return new WorldObjectSprite(set, world_set);
		}
		
		protected Unit createWorldObject(SetResource set, WorldSet.ImageObject world_set) {
			return new WorldObjectImage(set, world_set);
		}
		
		final public SetResource getSetResource(){
			return set_resource;
		}
		
		final public WorldSet getSetWorld() {
			return set_world;
		}

		public boolean isStreamingImages()
		{
			for (WorldSet.SpriteObject wspr : set_world.Sprs.values()){
				if (set_resource.isStreamingImages(wspr.ImagesID)){
					return true;
				}
			}
			return false;
		}
		
		public BufferedImage createMiniMap(double width, double height) 
		{
			if (isStreamingImages()) {
				return null;
			} else {
				BufferedImage buffer = Tools.createImage((int) width, (int) height);
				Graphics2D g2d = (Graphics2D) buffer.createGraphics();
				double scalew = width / getWidth();
				double scaleh = height / getHeight();
				g2d.scale(scalew, scaleh);
				Vector<DisplayObject> childs = getSortedChilds();
				for (DisplayObject d : childs) {
					g2d.pushTransform();
					g2d.translate(d.x, d.y);
					if (d instanceof WorldObjectSprite) {
						d.render(g2d);
//						CSprite csprite = ((WorldObjectSprite)d).getSprite();
//						csprite.render(g2d, wspr.X, wspr.Y, wspr.Anim, wspr.Frame);
					}
					else if (d instanceof WorldObjectImage) {
						d.onRender(g2d);
					}
					g2d.popTransform();
				}
				g2d.dispose();
				return buffer;
			}
		}
		
		public BufferedImage createScreenshot(int x, int y, double width, double height)
		{
			if (isStreamingImages()) {
				return null;
			} else {
				BufferedImage buffer = Tools.createImage((int) width, (int) height);
				Graphics2D g2d = (Graphics2D) buffer.createGraphics();
				for (WorldSet.SpriteObject wspr : set_world.Sprs.values()) {
					CSprite csprite = set_resource.getSprite(wspr.SprID);
					csprite.render(g2d, x+wspr.X, y+wspr.Y, wspr.Anim, wspr.Frame);
				}
				g2d.dispose();
				return buffer;
			}
		}
	}
	

	public static class WorldObjectSprite extends SceneSprite
	{
		final protected WorldSet.SpriteObject 		set_world_sprite;
		
		public WorldObjectSprite(SetResource set, WorldSet.SpriteObject world_set) 
		{
			set_world_sprite = world_set;
			super.init(set, world_set.SprID);
			setLocation(set_world_sprite.X, set_world_sprite.Y);
		}
		
		@Override
		public void loaded(SetResource set, CSprite cspr, SpriteSet spr) {
			super.loaded(set, cspr, spr);
//			while (set_world_sprite==null) {}
			csprite.setCurrentFrame(set_world_sprite.Anim, set_world_sprite.Frame);
		}
	}
	
	public static class WorldObjectImage extends SceneImage
	{
		final protected WorldSet.ImageObject 		set_world_img;
		
		public WorldObjectImage(SetResource set, WorldSet.ImageObject world_set) 
		{
			super(set, 
					world_set.ImagesID,
					world_set.TileID, 
					world_set.Anchor,
					world_set.Trans);
			set_world_img = world_set;
			setLocation(world_set.X, world_set.Y);
		}
		
	}
	
}
