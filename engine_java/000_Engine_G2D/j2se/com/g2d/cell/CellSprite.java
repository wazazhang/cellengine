package com.g2d.cell;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.AnimateCursor;
import com.g2d.display.Sprite;

public class CellSprite extends Sprite
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient final public CSprite cspr;
	
	public Object user_data;
	
	public CellSprite(CSprite spr) {
		cspr = spr.copy();
		this.setLocalBounds(
				cspr.getVisibleLeft(), 
				cspr.getVisibleTop(), 
				cspr.getVisibleWidth(), 
				cspr.getVisibleHeight());
	}
	
	public void render(Graphics2D g) 
	{
		try{
			cspr.render(new CGraphics(g), 0, 0);
		}catch(Exception err){}
		z = getY();
	}
	
	public CSprite getSprite() {
		return cspr;
	}
	
	/**
	 * 由指定的精灵，创建一个动画鼠标
	 * @param cspr
	 * @return
	 */
	public static AnimateCursor createSpriteCursor(String name, CSprite cspr, int anim)
	{
		return Tools.createSpriteCursor(name, cspr, anim);
	}
	
	public static BufferedImage getFrameSnapshot(CSprite cspr, int anim, int frame)
	{
		return Tools.getFrameSnapshot(cspr, anim, frame);
	}
}
