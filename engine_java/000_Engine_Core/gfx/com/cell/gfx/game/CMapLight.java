package com.cell.gfx.game;

import com.cell.CMath;
import com.cell.gfx.IGraphics;

public class CMapLight extends CMap 
{
	private static final long serialVersionUID = 1L;
	
	int[][] MatrixColor;

	public CMapLight(CMap map)
	{
		super(map,map.IsCyc);
		
		MatrixColor = new int[map.getYCount()][map.getXCount()];
	}
	
	public void renderCell(IGraphics g, int x, int y, int cellX, int cellY) {
		MatrixColor[cellY][cellX] = setLight(cellX, cellY);
		if(MatrixColor[cellY][cellX]!=0){
			g.fillRectAlpha(MatrixColor[cellY][cellX], x, y, cellX, cellY);
		}
	}

	public boolean testSameAnimateTile(int time1, int time2, int bx, int by) {
		return true;
	}


	protected int setLight(int cellx, int celly)
	{
//		cellx = cellx * CellW;
//		celly = celly * CellH;
		
		int fog = 0;
		
//		int cx2 = world.Camera.X + world.Camera.WindowW;
//		int cy2 = world.Camera.Y + world.Camera.WindowH;
//		
//		for(int i=world.Lights.size()-1; i>=0; i--)
//		{
//			CLight light = (CLight)world.Lights.elementAt(i);
//
//			if(light.IsDirty)
//			{
//				if(CMath.intersectRect(
//						light.X - light.Radius,
//						light.Y - light.Radius,
//						light.X + light.Radius,
//						light.Y + light.Radius,
//						world.Camera.X, 
//						world.Camera.Y,
//						cx2, cy2
//						)){
//				}{
//					light.IsDirty = false;
//					
//					int rx = cellx - light.X;
//					int ry = celly - light.Y;
//					
//					rx = rx * rx;
//					ry = ry * ry;
//
////					for(int l=0;l<light.Radius;l++)
////					{
////						r0 *= r0;
////						if(dx + dy <= r0){
////							if(block.FogLevel>l){
////								block.FogLevel = l;
////								break;
////							}
////						}
////					}
//					
//					
//				}
//				
//			}
//
//		}
//		
//		fog = fog * (0xcf / 10 << 24);
		
		return fog;
	
	}


}


class CMapLightCamera extends CCamera
{
	public CMapLightCamera(int windowX, int windowY, int windowW, int windowH, 
			CMapLight map) {
		super(windowX, windowY, windowW, windowH, map, false, 0);

	}

	public void render(IGraphics g) 
	{
		if (!Visible)return;
		
		g.pushClip();
    	g.clipRect(WindowX,WindowY,WindowW,WindowH);
    	
    	{
	    	int bx = CMath.cycNum(X,0,MapW)/CellW;
			int by = CMath.cycNum(Y,0,MapH)/CellH;
			int dx = CMath.cycNum(X,0,MapW)%CellW;
			int dy = CMath.cycNum(Y,0,MapH)%CellH;
			
			int cpx = WindowX-dx;
			int cpy = WindowY-dy;
			
			for(int y=0;y<WindowBH;y++)
			{
				for(int x=0;x<WindowBW;x++)
				{
					Map.renderCell(g, 
							cpx+x*CellW, 
							cpy+y*CellH, 
							CMath.cycNum(bx,x,MapBW), 
							CMath.cycNum(by,y,MapBH)
							);
				}
			}
    	}
		g.popClip();
	}
	
}








