package com.g2d.studio.cpj.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cell.gameedit.object.SpriteSet;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.g2d.cell.CellSprite;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.awt.util.*;


public class CPJSprite extends CPJObject<SpriteSet>
{	
	public CPJSprite(CPJFile parent, String name, CPJResourceType res_type) {
		super(parent, name, SpriteSet.class, res_type);
//		System.out.println("read a cpj sprite : " + name);
	}
	
	@Override
	public void setSetObject(SpriteSet obj) {
		super.setSetObject(obj);
	}

	public CSprite createCSprite() {
		parent.getSetResource().initAllStreamImages();
		CSprite cspr = parent.getSetResource().getSprite(name);
		return cspr;
	}
	
	public CellSprite createCellSprite() {
		CSprite cspr = createCSprite();
		CellSprite cell_sprite = new CellSprite(cspr);					
		cell_sprite.user_data = parent.getName()+"/" + name;
		return cell_sprite;
	}
	
	@Override
	public BufferedImage getSnapShoot() {
		if (snapshoot==null) {
			try {
				File file = parent.getCPJDir().getChildFile("icon_" + name + ".png");
//				File file = new File(parent.getCPJDir()+"/icon.png");
				if (file.exists()) {
					snapshoot = Tools.readImage(file.getPath());
					if (snapshoot != null) {
						snapshoot = Tools.combianImage(32, 32, snapshoot);
					}
				}
				
				if (snapshoot == null && parent.getSetResource() != null) 
				{
					synchronized (parent.getSetResource()) 
					{
						boolean unload = !parent.getSetResource().isLoadImages();
						try{
							parent.getSetResource().initAllStreamImages();
							CSprite			spr		= parent.getSetResource().getSprite(name);
							int anims = spr.getAnimateCount();
							for ( int i=0; (i<anims)&&(snapshoot==null); ++i ) {
								int frames = spr.getFrameCount(i);								
								for ( int j=0; (j<frames)&&(snapshoot==null); ++j ) {
									CCD	bounds	= spr.getFrameBounds(i, j);
									if (bounds.getWidth() > 0 && bounds.getHeight() > 0) {
										BufferedImage 	img		= Tools.createImage(bounds.getWidth(), bounds.getHeight());
										Graphics2D		g2d		= img.createGraphics();
										spr.render(Tools.wrap(g2d), -bounds.X1, -bounds.Y1, i, j);
										g2d.dispose();
										snapshoot = Tools.combianImage(32, 32, img);
									}
								}
							}
							Tools.writeImage(file.getPath(), snapshoot);
							System.err.println("create a sprite icon file : " + name);
						} catch (Exception ex) {
							System.err.println("create a sprite icon file error : " + name);
							snapshoot = Res.icon_error;
						} finally {
							if (unload) {
								parent.getSetResource().destoryAllStreamImages();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getSnapShoot();
	}
	
}
