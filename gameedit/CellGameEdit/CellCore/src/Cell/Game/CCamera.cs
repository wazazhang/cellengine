package Cell.Game;

import javax.microedition.lcdui.*;

/**
 * 
 * 游戏卷轴的摄象机。</br>
 * 
 * 用于拍摄世界中的某个区域显示在屏幕上。
 *  
 */
public class CCamera extends CUnit {

	public int WindowX;
	public int WindowY;
	
	private int WindowW;
	private int WindowH;
	private int WindowBW;
	private int WindowBH;
//	-----------------------------------------------------------------------------------
	
	protected int X;
	protected int Y;
	
	protected CMap Map;
	
	protected Image BackBuffer;
	protected Graphics bg;
	
	protected int BackColor;
	
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
		
		Map = map;
		
		CellW = map.CellW;
		CellH = map.CellH;
		
		MapBW = Map.getWCount();
		MapBH = Map.getHCount();
		MapW = Map.getWidth();
		MapH = Map.getHeight();
		
		WindowW = windowW-windowW%CellW;
		WindowH = windowH-windowH%CellH;
		if(WindowW + CellW > MapW)WindowW = MapW - CellW;
		if(WindowH + CellH > MapH)WindowH = MapH - CellH;
		WindowBW = WindowW / CellW;
		WindowBH = WindowH / CellH;
		
		
		IsBackBuffer = isBackBuffer;
		BackColor = backColor;
		
		if (IsBackBuffer){
			BufBW = WindowBW + 1;
			BufBH = WindowBH + 1;
			BufW = BufBW*CellW;
			BufH = BufBH*CellH;
			
			BackBuffer = Image.createImage(BufW, BufH);
			println("Create BackBuffer : " + BufW + " x " + BufH);
			bg = BackBuffer.getGraphics();
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
			
			for(int y=0;y<BufBH;y++){
				for(int x=0;x<BufBW;x++){
					Map.renderDirectCell(bg, x*CellW, y*CellH, x, y,false);
				}
			}
		}


	}



	//----------------------------------------------------------------------------------------------------

	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}
	public int getWidth(){
		return WindowW;
	}
	public int getHeight(){
		return WindowH;
	}
	
	public void setPos(int x,int y){
		mov(x - X,y - Y);
	}

	
	/**
	 * TODO 移动摄象机
	 * 
	 * @param px
	 * @param py
	 */
	public void mov(int x, int y) {
		
//		resetCamera(X+x,Y+y);
//		if(true)return;
		
		int px = 0; 
		int py = 0;
		
		if(Map.IsCyc){
			px = x; 
			py = y;
			X += x;
			Y += y;
		}else{
			if(X+x<0){
				px = 0 - X;
			}else if(X+x+WindowW>MapW){
				px = MapW - (X + WindowW);
			}else{
				px = x; 
			}
			if(Y+y<0){
				py = 0 - Y;
			}else if(Y+y+WindowH>MapH){
				py = MapH - (Y + WindowH);
			}else{
				py = y;
			}
			X += px;
			Y += py;
		}
		
		if(IsBackBuffer){
			if( px!=0 ){
				int dx = (px<0?-1:1);
				int dw = Math.abs(px);
				int xCount = (dw/CellW+1)<BufBW?(dw/CellW+1):BufBW;
//				refpos on dest
				vMapX = cycNum(X,0,MapW);
				vBufX = cycNum(X,0,BufW);
				vMapBX = cycNum(vMapX/CellW,0,MapBW);
				vBufBX = cycNum(vBufX/CellW,0,BufBW);
				vnL = vMapX%CellW;
				vnR = CellW-vnL;
//				fill back buffer
				int mbx = cycNum(vMapBX,(dx>0?WindowBW:0),MapBW);
				int mby = cycNum(vMapBY,0,MapBH);
				int bbx = cycNum(vBufBX,(dx>0?WindowBW:0),BufBW);
				int bby = cycNum(vBufBY,0,BufBH);
				if(xCount!=0){
					for(int nx=0;nx<xCount;nx++){
						for(int ny=0;ny<BufBH;ny++){
							Map.renderDirectCell(bg, 
									cycNum(bbx,-dx*nx,BufBW)*CellW, //
									cycNum(bby,ny,BufBH)*CellH, //
									cycNum(mbx,-dx*nx,MapBW), //
									cycNum(mby,ny,MapBH), //
									false);
						}

					}
//					if(isDebug ){
//						println("Buffer X :"+
//								" px="+px+
//								" nL="+vnL+
//								" nR="+vnR+
//								" s="+vMapX+
//								" d="+vBufX+
//								" mB="+vMapBX+"/"+MapBW+
//								" bB="+vBufBX+"/"+BufBW+
//								" count="+xCount+
//								"");
//					}
					
				}
			}

			if( py!=0 ){
				int dy = (py<0?-1:1);
				int dh = Math.abs(py);
				int yCount = (dh/CellH+1)<BufBH?(dh/CellH+1):BufBH;
//				refpos				
				vMapY = cycNum(Y,0,MapH);
				vBufY = cycNum(Y,0,BufH);
				vMapBY = cycNum(vMapY/CellH,0,MapBH);
				vBufBY = cycNum(vBufY/CellH,0,BufBH);
				vnT = vMapY%CellH;
				vnB = CellH-vnT;
//				fill back buffer
				int mby = cycNum(vMapBY,(dy>0?WindowBH:0),MapBH);
				int mbx = cycNum(vMapBX,0,MapBW);
				int bby = cycNum(vBufBY,(dy>0?WindowBH:0),BufBH);
				int bbx = cycNum(vBufBX,0,BufBW);
				if(yCount!=0){
					for(int ny=0;ny<yCount;ny++){
						for(int nx=0;nx<BufBW;nx++){
							Map.renderDirectCell(bg, 
									cycNum(bbx,nx,BufBW)*CellW, //
									cycNum(bby,-dy*ny,BufBH)*CellH, //
									cycNum(mbx,nx,MapBW), //
									cycNum(mby,-dy*ny,MapBH), //
									false
									);
						}
					}
//					if(isDebug ){
//					println("Buffer Y :"+
//							" count="+yCount+
//							"");
//					
//					}
					
				}
			}
		}
	}

	
	
	public void setWinPos(int x, int y) {
		WindowX = (short) x;
		WindowY = (short) y;
	}
	
	private void resetCamera(int x,int y){
		X = x;
		Y = y;
		
		vMapX = cycNum(X,0,MapW);
		vMapY = cycNum(Y,0,MapH);
		vMapBX = cycNum(vMapX/CellW,0,MapBW);
		vMapBY = cycNum(vMapY/CellH,0,MapBH);
		
		vBufX = cycNum(X,0,BufW);
		vBufY = cycNum(Y,0,BufH);
		vBufBX = cycNum(vBufX/CellW,0,BufBW);
		vBufBY = cycNum(vBufY/CellH,0,BufBH);
		
		vnL = vMapX%CellW;
		vnR = CellW-vnL;
		vnT = vMapY%CellH;
		vnB = CellH-vnT;
		
		for(int ny=0;ny<BufBH;ny++){
			for(int nx=0;nx<BufBW;nx++){
				Map.renderDirectCell(bg, 
						cycNum(vBufBX, nx, BufBW)*CellW, 
						cycNum(vBufBY, ny, BufBH)*CellH, 
						cycNum(vMapBX,nx,MapBW), 
						cycNum(vMapBY,ny,MapBH),
						false
						);
			}
		}
	}
	//	----------------------------------------------------------------------------------------

	public void render(Graphics g) {
		if (IsBackBuffer){
			if(Map.IsAnimate){
				int sbx = vMapX/CellW;
				int dbx = vBufX/CellW;
				int sby = vMapY/CellH;
				int dby = vBufY/CellH;
				for(int y=0;y<BufBH;y++){
					for(int x=0;x<BufBW;x++){
						if(!Map.testSameAnimateTile(getTimer()-1, getTimer(), sbx, sby)){
							Map.renderDirectCell(bg, 
								dbx*CellW, dby*CellH, sbx, sby, true);
						}
						sbx = cycNum(sbx,+1,MapBW);
						dbx = cycNum(dbx,+1,BufBW);
					}
					sbx = vMapX/CellW;
					dbx = vBufX/CellW;
					sby = cycNum(sby,+1,MapBH);
					dby = cycNum(dby,+1,BufBH);
				}
			}
			int w1 = BufW-vBufX<=WindowW?BufW-vBufX:WindowW;
			int h1 = BufH-vBufY<=WindowH?BufH-vBufY:WindowH;
			int w2 = WindowW - w1;
			int h2 = WindowH - h1;
			if (w1 > 0 && h1 > 0) {
				drawRegion(g, BackBuffer, vBufX, vBufY, w1, h1, WindowX + 0, WindowY + 0);
			}
			if (w2 > 0 && h2 > 0) {
				drawRegion(g, BackBuffer, 0, 0, w2, h2, WindowX + w1, WindowY + h1);
			}
			if (w1 > 0 && h2 > 0) {
				drawRegion(g, BackBuffer, vBufX, 0, w1, h2, WindowX + 0, WindowY + h1);
			}
			if (w2 > 0 && h1 > 0) {
				drawRegion(g, BackBuffer, 0, vBufY, w2, h1, WindowX + w1, WindowY + 0);
			}
		}else{
			int cx = g.getClipX();
			int cy = g.getClipY();
			int cw = g.getClipWidth();
			int ch = g.getClipHeight();
	    	g.setClip(WindowX,WindowY,WindowW,WindowH);
	    	
			int bx = cycNum(X,0,MapW)/CellW;
			int by = cycNum(Y,0,MapH)/CellH;
			int dx = cycNum(X,0,MapW)%CellW;
			int dy = cycNum(Y,0,MapH)%CellH;
			for(int y=0;y<WindowBH+1;y++){
				for(int x=0;x<WindowBW+1;x++){
					Map.renderDirectCell(g, 
							WindowX+x*CellW-dx, 
							WindowY+y*CellH-dy, 
							cycNum(bx,x,MapBW), 
							cycNum(by,y,MapBH),
							true
							);
				}
			}
			g.setClip(cx,cy,cw,ch);
		}
		
	}

	/**
	 * 把后备缓冲直接画出来(DEBUG)
	 * 
	 * @param g
	 *            绘图Graphics。
	 * @param x
	 *            X坐标。
	 * @param y
	 *            Y坐标。
	 * @param c
	 *            颜色。
	 */
	public void renderDebugBackBuffer(Graphics g, int x, int y, int c) {
		if (IsBackBuffer){
			g.drawImage(BackBuffer, x, y, (int)(Graphics.LEFT | Graphics.TOP));
			g.setColor(c);
			int w1 = BufW-vBufX<=WindowW?BufW-vBufX:WindowW;
			int h1 = BufH-vBufY<=WindowH?BufH-vBufY:WindowH;
			int w2 = WindowW - w1;
			int h2 = WindowH - h1;
			if (w1 > 0 && h1 > 0)
				g.drawRect(x + vBufX, y + vBufY, w1-1, h1-1);
			if (w2 > 0 && h2 > 0)
				g.drawRect(x + 0, y + 0, w2-1, h2-1);
			if (w1 > 0 && h2 > 0)
				g.drawRect(x + vBufX, y + 0, w1-1, h2-1);
			if (w2 > 0 && h1 > 0)
				g.drawRect(x + 0, y + vBufY, w2-1, h1-1);
		}

	}

	/**
	 * TODO 画摄象机的窗口范围。
	 * 
	 * @param g
	 *            绘图Graphics。
	 * @param colour
	 *            颜色。
	 */
	public void renderDebugWindow(Graphics g, int colour) {
		g.setColor(colour);
		g.drawRect(WindowX, WindowY, WindowW-1, WindowH-1);
	}
	//	-------------------------------------------------------------------------------------------------




	
}