package com.cell.gameedit;

import com.cell.gfx.IImages;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;

/**
 * 对set里所有文件进行缓冲
 * @author WAZA
 */
public interface SetLoading
{
	public void progress(SetResource set, IImages images, int progress, int maxcount);
	public void progress(SetResource set, CSprite spr, int progress, int maxcount);
	public void progress(SetResource set, CWayPoint[] points, int progress, int maxcount);
	public void progress(SetResource set, CCD[] regions, int progress, int maxcount);
}
