package com.cell.gfx.game;


public class CLight extends CUnit
{
	protected boolean IsDirty = true;
	
	protected int X;
	protected int Y;
	
	protected int Level	= 0;
	protected int Radius	= 1;
	protected int Type		= 0;

	public CLight(int x, int y, int level, int r, int type)
	{
		setPos(x, y);
		setLighting(level, r, type);
	}

	public void setPos(int x, int y){
		if(x!=X || y!=Y){
			IsDirty = true;
			X = x;
			Y = y;
		}
	}
	
	public void setLighting(int level, int r, int type){
		if(level!=Level || r!=Radius || type!=Type){
			IsDirty = true;
			Level = level;
			Radius = r;
			Type = type;
		}
	}
	
	
	public int getLevel() {
		return Level;
	}

	public int getRadius() {
		return Radius;
	}

	public int getType() {
		return Type;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	
//	protected void pushLight(CMapLight map)
//	{
//		if(m_world.CurLord.RealmScouts!=null)
//		{
//			MapBlock block = this;
//			
//			block.FogLevel = MaxFogLevel;
//			
//			for (int r = 0; r < m_world.CurLord.RealmScouts.length; r++)
//			{
//				int realmX = m_world.CurLord.RealmScouts[r].RealmLocationX;
//				int realmY = m_world.CurLord.RealmScouts[r].RealmLocationY;
//				int realmScout = m_world.CurLord.RealmScouts[r].Scout;
//				
//				int dx = Math.abs(block.X - realmX);
//				int dy = Math.abs(block.Y - realmY);
//				
//				dx = dx * dx;
//				dy = dy * dy;
//
//				for(int l=0;l<MaxFogLevel;l++){
//					int r0 =  realmScout + l;
//					r0 *= r0;
//					if(dx + dy <= r0){
//						if(block.FogLevel>l){
//							block.FogLevel = l;
//							break;
//						}
//					}
//				}
//				
//			}
//			
//		}
//		
//		FogColor = FogLevel * (0xcf / MaxFogLevel << 24);
//	}
	
}
