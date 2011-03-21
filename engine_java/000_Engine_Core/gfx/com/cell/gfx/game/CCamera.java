
package com.cell.gfx.game;


import com.cell.CMath;
import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

/**
 * a camera scrollable on a map system, view world unit on screen.</br>
 * @author yifeizhang
 * @since 2006-11-29 
 * @version 1.0
 */
public class CCamera extends CUnit {

	/** screen window x </br>*/
	public int WindowX;
	/** screen window y </br>*/
	public int WindowY;
	public int WindowW;
	public int WindowH;
	
	protected int WindowBW;
	protected int WindowBH;
	private int WorldW;
	private int WorldH;
	
	private	int AnimateTimerOld = 0;
//	-----------------------------------------------------------------------------------
	
	protected int X;
	protected int Y;
	
	protected CMap 		Map;
	protected IImage 	BackBuffer;
	protected IGraphics bg;
	
//	protected int BackColor;
	
	protected boolean IsBackBuffer;

	protected int CellW;
	protected int CellH;

	protected int BufW;
	protected int BufH;
	protected int BufBW;
	protected int BufBH;
	
	protected int MapW;
	protected int MapH;
	protected int MapBW;
	protected int MapBH;
	
	private int vBufX;
	private int vBufY;
	private int vBufBX;
	private int vBufBY;
	
	private int vMapX;
	private int vMapY;
	private int vMapBX;
	private int vMapBY;
	
	private int vnT;
	private int vnB;
	private int vnL;
	private int vnR;
//	-----------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------

	/**
	 * Construct a camera based in specify map.</br>
	 * @param windowX screen window x </br>�1�7
	 * @param windowY screen window y </br>�1�7
	 * @param windowW screen window width </br>
	 * @param windowH screen window height </br>
	 * @param map one map can be used </br>
	 * @param isBackBuffer is back buffer mode, </br>
	 * true : can run game faster use more heap memory, </br>
	 * false : run game slowly not use memory buffer.</br>
	 * @param backColor 
	 */
	public CCamera(
			int windowX, 
			int windowY, 
			int windowW, 
			int windowH, 
			CMap map,
			boolean isBackBuffer,
			int backColor) {
		
		
		WindowX = windowX;
		WindowY = windowY;
		WindowW = windowW;
		WindowH = windowH;
		
		
		
		Map = map;
		
		CellW = map.CellW;
		CellH = map.CellH;
		
		WorldW = windowW;
		WorldH = windowH;
		
		MapBW = Map.getXCount();
		MapBH = Map.getYCount();
		MapW = Map.getWidth();
		MapH = Map.getHeight();
		
		IsBackBuffer = isBackBuffer;
		BackColor = backColor;
		
		int divx = windowW%CellW;
		int divy = windowH%CellH;
		WorldW = windowW-divx;
		WorldH = windowH-divy;
		
		if(divx!=0)WorldW += CellW;
		if(divy!=0)WorldH += CellH;
		
		if (!IsBackBuffer){
			
			if(WorldW > MapW)WorldW = MapW;
			if(WorldH > MapH)WorldH = MapH;
			WindowBW = WorldW / CellW + 1;
			WindowBH = WorldH / CellH + 1;
			
		}else{
			
			if(WorldW + CellW > MapW)WorldW = MapW - CellW;
			if(WorldH + CellH > MapH)WorldH = MapH - CellH;
			WindowBW = WorldW / CellW;
			WindowBH = WorldH / CellH;
			
			BufBW = WindowBW + 1;
			BufBH = WindowBH + 1;
			BufW = BufBW*CellW;
			BufH = BufBH*CellH;
			
			BackBuffer = AScreen.getGfxAdapter().createImage(BufW, BufH);
//			BackBuffer.setMode(IImage.MODE_VRAM);
//			BackBuffer.createBuffer(BufW, BufH);
			
//			println("Create BackBuffer : " + BufW + " x " + BufH);
			bg = BackBuffer.createGraphics();
			bg.setColor(BackColor);
			bg.fillRect(0, 0, BufW, BufH);

			vBufX = 0;
			vBufY = 0;
			vBufBX = 0;
			vBufBY = 0;
			
			vMapX = 0;
			vMapY = 0;
			vMapBX = 0;
			vMapBY = 0;
			
			vnT = 0;
			vnB = CellH;
			vnL = 0;
			vnR = CellW;
			
			// fill buffer
			for(int y=0;y<BufBH;y++)
			{
				for(int x=0;x<BufBW;x++)
				{
					Map.renderCell(bg, x*CellW, y*CellH, x, y);
				}
			}
			
			
		}


	}


	@Override
	protected void finalize() throws Throwable {
		if (bg!=null) {
			bg.dispose();
		}
	}

	//----------------------------------------------------------------------------------------------------

	/**
	 * get x within map </br>
	 * @return x coordinate
	 */
	public int getX(){
		return X;
	}
	/**
	 * get y within map</br>�1�7
	 * @return y coordinate
	 */
	public int getY(){
		return Y;
	}
	
	/**
	 * get camera size width</br>�1�7
	 * @return width
	 */
	public int getWidth(){
		return WorldW;
	}
	/**
	 * get camera size height</br>
	 * @return height
	 */
	public int getHeight(){
		return WorldH;
	}
	
	/**
	 * set position within map</br>�1�7
	 * @param x x 
	 * @param y y
	 */
	public void setPos(int x,int y){
		mov(x - X,y - Y);
	}

	
	/**
	 * move camera within map</br>�1�7
	 * @param px offset x
	 * @param py offset y
	 */
	public void mov(int x, int y)
	{
		int px = 0; 
		int py = 0;
		
		if(Map.IsCyc)
		{
			px = x; 
			py = y;
			X += x;
			Y += y;
		}
		else
		{
			if(X+x<0){
				px = 0 - X;
			}else if(X+x+WorldW>MapW){
				px = MapW - (X + WorldW);
			}else{
				px = x; 
			}
			if(Y+y<0){
				py = 0 - Y;
			}else if(Y+y+WorldH>MapH){
				py = MapH - (Y + WorldH);
			}else{
				py = y;
			}
			X += px;
			Y += py;
		}
		
		if(IsBackBuffer)
		{
			if( px!=0 )
			{
				int dx = (px<0?-1:1);
				int dw = Math.abs(px);
				int xCount = (dw/CellW+1)<BufBW?(dw/CellW+1):BufBW;
//				refpos on dest
				vMapX = CMath.cycNum(X,0,MapW);
				vBufX = CMath.cycNum(X,0,BufW);
				vMapBX = CMath.cycNum(vMapX/CellW,0,MapBW);
				vBufBX = CMath.cycNum(vBufX/CellW,0,BufBW);
				vnL = vMapX%CellW;
				vnR = CellW-vnL;
//				fill back buffer
				int mbx = CMath.cycNum(vMapBX,(dx>0?WindowBW:0),MapBW);
				int mby = CMath.cycNum(vMapBY,0,MapBH);
				int bbx = CMath.cycNum(vBufBX,(dx>0?WindowBW:0),BufBW);
				int bby = CMath.cycNum(vBufBY,0,BufBH);
				if(xCount!=0)
				{
					for(int nx=0;nx<xCount;nx++)
					{
						for(int ny=0;ny<BufBH;ny++)
						{
							Map.renderCell(bg, 
									CMath.cycNum(bbx,-dx*nx,BufBW)*CellW, //
									CMath.cycNum(bby,ny,BufBH)*CellH, //
									CMath.cycNum(mbx,-dx*nx,MapBW), //
									CMath.cycNum(mby,ny,MapBH) //
									);
						}

					}
				}
			}

			if( py!=0 )
			{
				int dy = (py<0?-1:1);
				int dh = Math.abs(py);
				int yCount = (dh/CellH+1)<BufBH?(dh/CellH+1):BufBH;
//				refpos				
				vMapY = CMath.cycNum(Y,0,MapH);
				vBufY = CMath.cycNum(Y,0,BufH);
				vMapBY = CMath.cycNum(vMapY/CellH,0,MapBH);
				vBufBY = CMath.cycNum(vBufY/CellH,0,BufBH);
				vnT = vMapY%CellH;
				vnB = CellH-vnT;
//				fill back buffer
				int mby = CMath.cycNum(vMapBY,(dy>0?WindowBH:0),MapBH);
				int mbx = CMath.cycNum(vMapBX,0,MapBW);
				int bby = CMath.cycNum(vBufBY,(dy>0?WindowBH:0),BufBH);
				int bbx = CMath.cycNum(vBufBX,0,BufBW);
				if(yCount!=0)
				{
					for(int ny=0;ny<yCount;ny++)
					{
						for(int nx=0;nx<BufBW;nx++)
						{
							Map.renderCell(bg, 
									CMath.cycNum(bbx,nx,BufBW)*CellW, //
									CMath.cycNum(bby,-dy*ny,BufBH)*CellH, //
									CMath.cycNum(mbx,nx,MapBW), //
									CMath.cycNum(mby,-dy*ny,MapBH) //
									);
						}
					}
				}
			}
		}
		
		if(world.WorldListener!=null)
		{
			world.WorldListener.cameraMoved(world, this,
					vMapBX, 
					vMapBY, 
					WindowBW, 
					WindowBH,
					x,y
					);
		}
	}

	
	
	/**
	 * set window position within screen</br>
	 * @param x x
	 * @param y y
	 */
	public void setWinPos(int x, int y) {
		WindowX = (short) x;
		WindowY = (short) y;
	}
	
	public void resetBuffer(){
		vMapX = CMath.cycNum(X,0,MapW);
		vMapY = CMath.cycNum(Y,0,MapH);
		vMapBX = CMath.cycNum(vMapX/CellW,0,MapBW);
		vMapBY = CMath.cycNum(vMapY/CellH,0,MapBH);
		
		vBufX = CMath.cycNum(X,0,BufW);
		vBufY = CMath.cycNum(Y,0,BufH);
		vBufBX = CMath.cycNum(vBufX/CellW,0,BufBW);
		vBufBY = CMath.cycNum(vBufY/CellH,0,BufBH);
		
		vnL = vMapX%CellW;
		vnR = CellW-vnL;
		vnT = vMapY%CellH;
		vnB = CellH-vnT;
		
		for(int ny=0;ny<BufBH;ny++){
			for(int nx=0;nx<BufBW;nx++){
				Map.renderCell(bg, 
						CMath.cycNum(vBufBX, nx, BufBW)*CellW, 
						CMath.cycNum(vBufBY, ny, BufBH)*CellH, 
						CMath.cycNum(vMapBX,nx,MapBW), 
						CMath.cycNum(vMapBY,ny,MapBH)
						);
			}
		}
	}

	public void resetBufferMapBlock(int bx,int by){

		int sbx = vMapX/CellW;
		int sby = vMapY/CellH;
		int dbx = vBufX/CellW;
		int dby = vBufY/CellH;
		
		// offset
		int offsetx = bx - sbx;
		int offsety = by - sby;
		if(Math.abs(offsetx)>=BufBW)return;
		if(Math.abs(offsety)>=BufBH)return;
		
		dbx = CMath.cycNum(dbx,offsetx,BufBW);
		dby = CMath.cycNum(dby,offsety,BufBH);
		
		Map.renderCell(bg, dbx*CellW, dby*CellH, bx, by);
				
	
	}
	//	----------------------------------------------------------------------------------------

	
	/**
	 * draw world units on graphics surface
	 * @param g graphics surface
	 */
	public void render(IGraphics g) {
		if (!Visible)return;
		
		g.pushClip();
    	g.clipRect(WindowX,WindowY,WindowW,WindowH);
    	
		if (IsBackBuffer)
		{
			if(Map.IsAnimate && AnimateTimerOld != Map.AnimateTimer)
			{
				int sbx = vMapX/CellW;
				int sby = vMapY/CellH;
				int dbx = vBufX/CellW;
				int dby = vBufY/CellH;
				
				for(int y=0;y<BufBH;y++)
				{
					for(int x=0;x<BufBW;x++)
					{
						if(!Map.testSameAnimateTile(AnimateTimerOld, Map.AnimateTimer, sbx, sby))
						{
							Map.renderCell(bg, dbx*CellW, dby*CellH, sbx, sby);
							//println("draw "+CScreen.getTimer());
						}
						sbx = CMath.cycNum(sbx,+1,MapBW);
						dbx = CMath.cycNum(dbx,+1,BufBW);
					}
					sbx = vMapX/CellW;
					dbx = vBufX/CellW;
					sby = CMath.cycNum(sby,+1,MapBH);
					dby = CMath.cycNum(dby,+1,BufBH);
				}
				
				AnimateTimerOld = Map.AnimateTimer;
			}
			
			int w1 = BufW-vBufX<=WorldW?BufW-vBufX:WorldW;
			int h1 = BufH-vBufY<=WorldH?BufH-vBufY:WorldH;
			int w2 = WorldW - w1;
			int h2 = WorldH - h1;
			
			if (w1 > 0 && h1 > 0) 
				g.drawRegion(BackBuffer, vBufX, vBufY, w1, h1, 0, WindowX + 0, WindowY + 0);
			
			if (w2 > 0 && h2 > 0) 
				g.drawRegion(BackBuffer, 0, 0, w2, h2, 0, WindowX + w1, WindowY + h1);
			
			if (w1 > 0 && h2 > 0) 
				g.drawRegion(BackBuffer, vBufX, 0, w1, h2, 0, WindowX + 0, WindowY + h1);
			
			if (w2 > 0 && h1 > 0) 
				g.drawRegion(BackBuffer, 0, vBufY, w2, h1, 0, WindowX + w1, WindowY + 0);
			
		}
		else
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

	
	/**
	 * draw back buffer directly (DEBUG)</br>
	 * @param g graphics surface
	 * @param x x
	 * @param y y
	 * @param c camera position debug color
	 */
	public void renderDebugBackBuffer(IGraphics g, int x, int y, int c) {
//#ifdef _DEBUG
			if (IsBackBuffer){
				g.drawImage(BackBuffer, x, y, 0);
				g.setColor(c);
				int w1 = BufW-vBufX<=WorldW?BufW-vBufX:WorldW;
				int h1 = BufH-vBufY<=WorldH?BufH-vBufY:WorldH;
				int w2 = WorldW - w1;
				int h2 = WorldH - h1;
				if (w1 > 0 && h1 > 0)
					g.drawRect(x + vBufX, y + vBufY, w1-1, h1-1);
				if (w2 > 0 && h2 > 0)
					g.drawRect(x + 0, y + 0, w2-1, h2-1);
				if (w1 > 0 && h2 > 0)
					g.drawRect(x + vBufX, y + 0, w1-1, h2-1);
				if (w2 > 0 && h1 > 0)
					g.drawRect(x + 0, y + vBufY, w2-1, h1-1);
			}
//#endif
	}
	
	/**
	 * draw camera scope within screen</br>
	 * @param g graphics
	 * @param color color
	 */
	public void renderDebugWindow(IGraphics g, int color) {
//#ifdef _DEBUG
			g.setColor(color);
			g.drawRect(WindowX, WindowY, WindowW, WindowH);
//#endif
	}

}