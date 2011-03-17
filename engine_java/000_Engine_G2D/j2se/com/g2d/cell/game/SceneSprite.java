package com.g2d.cell.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cell.gameedit.SetResource;
import com.cell.gameedit.SetResource.LoadSpriteListener;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.game.rpg.MoveableUnit;
import com.g2d.util.Drawing;

/**
 * @author WAZA
 * 支持网络缓冲的动态精灵
 */
public class SceneSprite extends MoveableUnit implements LoadSpriteListener
{
	private static final long serialVersionUID = Version.VersionG2D;
//	------------------------------------------------------------------------------------------------------------------------------------
//	resource
	
	transient protected CSprite 			csprite;
	transient protected SetResource			set_resource;
	transient protected SpriteSet 			set_sprite;
	
	protected String 	set_sprite_id;
	protected int		cur_anim;
	
//	------------------------------------------------------------------------------------------------------------------------------------
	
//	@Override
//	protected void init_field() 
//	{
//		super.init_field();
//	}
//
//	@Override
//	protected void init_transient() 
//	{
//		super.init_transient();
//		
//		if (set_resource_name != null && set_sprite_id != null)
//		{
//			CellSetResource set;
//			try {
//				set = CellSetResourceManager.getManager().getSet(set_resource_name);
//				if (set!=null){
//					init(set, set_sprite_id);
//				}
//			} catch (Exception e) {}
//		}
//	}
	
	public SceneSprite()
	{
		
	}
	
	public SceneSprite(SetResource set, String spriteID)
	{
		init(set, spriteID);
	}

	public SceneSprite(SceneSprite obj)
	{
		init(obj);
	}
	
	protected void init(SetResource set, String spriteID)
	{
		set_resource 		= set;
		set_sprite 			= set.getSetSprite(spriteID);
		set_sprite_id 		= set_sprite.Name;
		
		if (csprite == null) {
			set_resource.getSpriteAsync(set_sprite.Name, this);
		}else{
			setSprite(csprite);
		}
		
	}
	
	public void init_async(SetResource set, String spriteID)
	{
		set_resource 		= set;
		set_sprite 			= set.getSetSprite(spriteID);
		set_sprite_id 		= set_sprite.Name;
		
//		if (csprite == null) {
			set_resource.getSpriteAsync(set_sprite.Name, this);
//		}else{
//			setSprite(csprite);
//		}
		
	}

	protected void init(SceneSprite obj)
	{
		set_resource 		= obj.set_resource;
		set_sprite 			= obj.set_sprite;
		set_sprite_id 		= set_sprite.Name;
		
		if (obj.csprite != null) {
			setSprite(obj.csprite);
			csprite.setCurrentFrame(obj.csprite.getCurrentAnimate(), obj.csprite.getCurrentFrame());
		} else {
			set_resource.getSpriteAsync(set_sprite.Name, this);
		}
		
		
	}
	
	
	
	public SceneSprite clone()
	{
		SceneSprite spr = new SceneSprite(this);
		
		spr.move_target_x 	= this.move_target_x;
		spr.move_target_y 	= this.move_target_y;
		spr.move_speed 		= this.move_speed;
		spr.move_blockade 	= this.move_blockade;
		
		spr.x 				= this.x;
		spr.y 				= this.y;
		spr.scale_x			= this.scale_x;
		spr.scale_y			= this.scale_y;
		spr.rotate			= this.rotate;
				
		spr.set_sprite_id 		= this.set_sprite_id;
		
		return spr;
	}
	
	
	public SetResource getSetResource() {
		return set_resource;
	}
	
	public SpriteSet 	getSetSprite() {
		return set_sprite;
	}
	
	@Override
	public BufferedImage getSnapshot(int width, int height)
	{
		BufferedImage ret = Tools.createImage(width, height);
		Graphics2D g = (Graphics2D)ret.getGraphics();

		if (csprite!=null)
		{
			float rx = ((float)width)  / getWidth();
			float ry = ((float)height) / getHeight();
			g.scale(rx, ry);
			csprite.render(new CGraphics(g), -local_bounds.x, -local_bounds.y);
		}
		else
		{
			g.setColor(Color.WHITE);
			Drawing.drawStringBorder(g, "loading...", 1, 1, 0);
		}
		
		g.dispose();
		
		return ret;
	}

//	------------------------------------------------------------------------------------------------------------------------------------
//	resource
	
	public void loaded(SetResource set, CSprite cspr, SpriteSet spr) {
//		System.out.println("loaded : " + spr.SprID);
		setSprite(cspr) ;
	}
	
	public CSprite getSprite() {
		return csprite;
	}
	
	
	public void setSprite(CSprite spr) 
	{
		if (spr!=null)
		{
			csprite = spr.copy();
			
			this.setLocalBounds(
					csprite.getVisibleLeft(), 
					csprite.getVisibleTop(), 
					csprite.getVisibleWidth(), 
					csprite.getVisibleHeight());
			
			if (cur_anim < csprite.getAnimateCount()) {
				csprite.setCurrentFrame(cur_anim, 0);
//				System.out.println("set cur anim " + cur_anim);
			}
		}
	}
	
	public void render(Graphics2D g) 
	{
		if (csprite!=null){
			csprite.render(new CGraphics(g), 0, 0);
			cur_anim = csprite.getCurrentAnimate();
		}else{
			g.setColor(Color.BLUE);
			g.drawString("loading...", 0, 0);
		}
	}

	@Override
	public void onMoveStopped() 
	{
		// do nothing 
		
	}
	
	
}
