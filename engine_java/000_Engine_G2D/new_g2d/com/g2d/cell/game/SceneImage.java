package com.g2d.cell.game;


import com.cell.gameedit.SetResource;
import com.cell.gameedit.SetResource.LoadSpriteListener;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IGraphics.ImageTrans;
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
	
	
	public SceneImage(
			SetResource set, 
			String imagesID, 
			int tileID,
			IGraphics.ImageAnchor anchor,
			IGraphics.ImageTrans trans)
	{
		this.set_resource 	= set;
		this.set_images		= set.ImgTable.get(imagesID);
		this.image 			= set.getImages(imagesID);
		this.index			= tileID;
		this.trans			= ImageTrans.stringToTrans(trans);
		this.anchor			= anchor;
		init();
	}
	
	protected SceneImage(SceneImage other)
	{
		this.set_resource 	= other.set_resource;
		this.set_images		= other.set_images;
		this.image 			= other.image;
		this.index			= other.index;
		this.trans			= other.trans;
		this.anchor			= other.anchor;
		init();
	}
	
	protected void init()
	{
		int iw = image.getWidth(index);
		int ih = image.getHeight(index);
		
		int rw = -iw;
		int th = -ih;
		
		int cw = rw/2;
		int ch = th/2;
		
		switch(anchor) {
		case L_T:
			this.setLocalBounds(0, 0, iw, ih);
			break;
		case C_T:
			this.setLocalBounds(cw, 0, iw, ih);
			break;
		case R_T:
			this.setLocalBounds(rw, 0, iw, ih);
			break;
			
		case L_C:
			this.setLocalBounds(0, ch, iw, ih);
			break;
		case C_C:
			this.setLocalBounds(cw, ch, iw, ih);
			break;
		case R_C:
			this.setLocalBounds(rw, ch, iw, ih);
			break;
			
		case L_B:
			this.setLocalBounds(0, th, iw, ih);
			break;
		case C_B:
			this.setLocalBounds(cw, th, iw, ih);
			break;
		case R_B:
			this.setLocalBounds(rw, th, iw, ih);
			break;
		}

		
	}
	
	public SceneImage clone()
	{
		SceneImage spr = new SceneImage(this);

		
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
		image.render(g, index, local_bounds.x, local_bounds.y, trans);
	}

	
	
}
