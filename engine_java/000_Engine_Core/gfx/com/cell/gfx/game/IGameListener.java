package com.cell.gfx.game;

public interface IGameListener
{
	public void unitAction(CWorld world, Object event, CUnit unit);
	public void unitAction(CWorld world, Object event, CUnit unit[]);
	
	// sprite
	public void sprAdded(CWorld world, CSprite src);
	public void sprRemoved(CWorld world, CSprite src);
	public void sprMoved(CWorld world, CSprite src, CSprite blockedSpr, CMap blockedMap, int dx, int dy);
	
	// camera
	public void cameraMoved(CWorld world, CCamera camera, int mbx, int mby, int mbw, int mbh, int dx, int dy);
	
	
}