package com.g2d.cell.game;


import com.cell.gameedit.SetResource;
import com.cell.gameedit.SetResource.LoadSpriteListener;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CSprite;
import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Tools;
import com.g2d.game.rpg.MoveableUnit;
import com.g2d.game.rpg.Unit;
import com.g2d.util.Drawing;

/**
 * @author WAZA
 * 支持网络缓冲的动态精灵
 */
public class SceneImage extends Unit
{
//	------------------------------------------------------------------------------------------------------------------------------------
//	resource
	
	transient protected IImages	 			image;
	transient protected SetResource			set_resource;
	transient protected ImagesSet 			set_images;

	protected int index;
	protected int trans;
	protected IGraphics.ImageAnchor anchor;
	
//	------------------------------------------------------------------------------------------------------------------------------------
	
	
	public SceneImage(SetResource set, String imagesID)
	{
		set_resource 		= set;
		set_images			= set.ImgTable.get(imagesID);
		image 				= set.getImages(imagesID);
	}
	
	protected SceneImage(SceneImage other)
	{
		set_resource 		= other.set_resource;
		set_images			= other.set_images;
		image 				= other.image;
	}
	
	
	public SceneImage clone()
	{
		SceneImage spr = new SceneImage(this);

		spr.index			= this.index;
		spr.trans			= this.trans;
		spr.anchor			= this.anchor;
		
		spr.x 				= this.x;
		spr.y 				= this.y;
		spr.scale_x			= this.scale_x;
		spr.scale_y			= this.scale_y;
		spr.rotate			= this.rotate;
		
		return spr;
	}
	
	public SetResource getSetResource() {
		return set_resource;
	}
	
	public BufferedImage getSnapshot(int width, int height)
	{
		BufferedImage ret = (BufferedImage)image.getImage(index);
		return Tools.combianImage(width, height, ret);
	}

	public void render(Graphics2D g) 
	{
		int rw = -image.getWidth(index);
		int th = -image.getHeight(index);
		
		int cw = rw/2;
		int ch = th/2;
		
		switch(anchor) {
		case L_T:
			image.render(g, index, 0, 0, trans);
			break;
		case C_T:
			image.render(g, index, cw, 0, trans);
			break;
		case R_T:
			image.render(g, index, rw, 0, trans);
			break;
			
		case L_C:
			image.render(g, index, 0, ch, trans);
			break;
		case C_C:
			image.render(g, index, cw, ch, trans);
			break;
		case R_C:
			image.render(g, index, rw, ch, trans);
			break;
			
		case L_B:
			image.render(g, index, 0, th, trans);
			break;
		case C_B:
			image.render(g, index, cw, th, trans);
			break;
		case R_B:
			image.render(g, index, rw, th, trans);
			break;
		}
	}

	
	
}
