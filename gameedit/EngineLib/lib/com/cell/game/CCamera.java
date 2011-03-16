/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.game;


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.cell.AScreen;
import com.cell.CMath;


/**
 * a camera scrollable on a map system, view world unit on screen.</br>
 * map卷轴系统的摄象机，用于把world中的元素显示到屏幕上
 * @author yifeizhang
 * @since 2006-11-29 
 * @version 1.0
 */
public class CCamera extends CUnit {

	/** screen window x </br> 屏幕坐标x*/
	public int WindowX;
	/** screen window y </br> 屏幕坐标y*/
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
	 * 指定一个地图构造摄象机
	 * @param windowX screen window x </br>屏幕坐标x
	 * @param windowY screen window y </br>屏幕坐标y
	 * @param windowW screen window width </br> 视口宽
	 * @param windowH screen window height </br> 视口高
	 * @param map one map can be used </br> 指定的地图，视口如果大于地图大小，则自动调整为地图大小。
	 * @param isBackBuffer is back buffer mode, </br>
	 * true : can run game faster use more heap memory, </br>
	 * false : run game slowly not use memory buffer.</br>
	 * 是否为缓冲加速模式，缓冲加速模式可以得到更快的游戏速度并耗费更多的内存
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
		
		MapBW = Map.getWCount();
		MapBH = Map.getHCount();
		MapW = Map.getWidth();
		MapH = Map.getHeight();
		
		IsBackBuffer = isBackBuffer;
		BackColor = backColor;
		
		if (!IsBackBuffer){
			
			WindowW = windowW-windowW%CellW;
			WindowH = windowH-windowH%CellH;
			if(WindowW > MapW)WindowW = MapW;
			if(WindowH > MapH)WindowH = MapH;
			WindowBW = WindowW / CellW + 1;
			WindowBH = WindowH / CellH + 1;
			
		}else{
			
			WindowW = windowW-windowW%CellW;
			WindowH = windowH-windowH%CellH;
			if(WindowW + CellW > MapW)WindowW = MapW - CellW;
			if(WindowH + CellH > MapH)WindowH = MapH - CellH;
			WindowBW = WindowW / CellW;
			WindowBH = WindowH / CellH;
			
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
					Map.renderCell(bg, x*CellW, y*CellH, x, y);
				}
			}
			
			
		}


	}



	//----------------------------------------------------------------------------------------------------

	/**
	 * get x within map </br> 
	 * 得到在世界中的x坐标
	 * @return x coordinate
	 */
	public int getX(){
		return X;
	}
	/**
	 * get y within map</br>
	 * 得到在世界中的y坐标
	 * @return y coordinate
	 */
	public int getY(){
		return Y;
	}
	
	/**
	 * get camera size width</br>
	 * 得到视口宽
	 * @return width
	 */
	public int getWidth(){
		return WindowW;
	}
	/**
	 * get camera size height</br>
	 * 得到视口高
	 * @return height
	 */
	public int getHeight(){
		return WindowH;
	}
	
	/**
	 * set position within map</br>
	 * 设置世界坐标，也就是拍摄点
	 * @param x x 
	 * @param y y
	 */
	public void setPos(int x,int y){
		mov(x - X,y - Y);
	}

	
	/**
	 * move camera within map</br>
	 * 移动摄象机
	 * @param px offset x
	 * @param py offset y
	 */
	public void mov(int x, int y) {

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
				if(xCount!=0){
					for(int nx=0;nx<xCount;nx++){
						for(int ny=0;ny<BufBH;ny++){
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

			if( py!=0 ){
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
				if(yCount!=0){
					for(int ny=0;ny<yCount;ny++){
						for(int nx=0;nx<BufBW;nx++){
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
	}

	
	
	/**
	 * set window position within screen</br>
	 * 设置视口坐标
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
	 * draw world units on graphics surface</br>
	 * 把摄象机的内容显示到屏幕上
	 * @param g graphics surface
	 */
	public void render(Graphics g) {
		if (!Visible)return;
		if (IsBackBuffer){
			if(Map.IsAnimate){
				int sbx = vMapX/CellW;
				int sby = vMapY/CellH;
				int dbx = vBufX/CellW;
				int dby = vBufY/CellH;
				
				for(int y=0;y<BufBH;y++){
					for(int x=0;x<BufBW;x++){
						if(!Map.testSameAnimateTile(getTimer()-1, getTimer(), sbx, sby)){
							Map.renderAnimateCell(bg, getTimer(), dbx*CellW, dby*CellH, sbx, sby);
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
			}
			int w1 = BufW-vBufX<=WindowW?BufW-vBufX:WindowW;
			int h1 = BufH-vBufY<=WindowH?BufH-vBufY:WindowH;
			int w2 = WindowW - w1;
			int h2 = WindowH - h1;
			if (w1 > 0 && h1 > 0) {
				AScreen.drawRegion(g, BackBuffer, vBufX, vBufY, w1, h1, WindowX + 0, WindowY + 0);
			}
			if (w2 > 0 && h2 > 0) {
				AScreen.drawRegion(g, BackBuffer, 0, 0, w2, h2, WindowX + w1, WindowY + h1);
			}
			if (w1 > 0 && h2 > 0) {
				AScreen.drawRegion(g, BackBuffer, vBufX, 0, w1, h2, WindowX + 0, WindowY + h1);
			}
			if (w2 > 0 && h1 > 0) {
				AScreen.drawRegion(g, BackBuffer, 0, vBufY, w2, h1, WindowX + w1, WindowY + 0);
			}
		}else{
			int cx = g.getClipX();
			int cy = g.getClipY();
			int cw = g.getClipWidth();
			int ch = g.getClipHeight();
	    	g.setClip(WindowX,WindowY,WindowW,WindowH);
	    	
			int bx = CMath.cycNum(X,0,MapW)/CellW;
			int by = CMath.cycNum(Y,0,MapH)/CellH;
			int dx = CMath.cycNum(X,0,MapW)%CellW;
			int dy = CMath.cycNum(Y,0,MapH)%CellH;
			for(int y=0;y<WindowBH;y++){
				for(int x=0;x<WindowBW;x++){
					Map.renderDirectCell(g, 
							getTimer(),
							WindowX+x*CellW-dx, 
							WindowY+y*CellH-dy, 
							CMath.cycNum(bx,x,MapBW), 
							CMath.cycNum(by,y,MapBH)
							);
				}
			}
			g.setClip(cx,cy,cw,ch);
		}
		
	}

	
	/**
	 * draw back buffer directly (DEBUG)</br>
	 * 
	 * @param g graphics surface
	 * @param x x
	 * @param y y
	 * @param c camera position debug color
	 */
	public void renderDebugBackBuffer(Graphics g, int x, int y, int c) {
//#ifdef _DEBUG
			if (IsBackBuffer){
				g.drawImage(BackBuffer, x, y, Graphics.LEFT | Graphics.TOP);
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
//#endif
	}

	/**
	 * draw camera scope within screen</br>
	 * @param g graphics
	 * @param color color
	 */
	public void renderDebugWindow(Graphics g, int color) {
//#ifdef _DEBUG
			g.setColor(color);
			g.drawRect(WindowX, WindowY, WindowW-1, WindowH-1);
//#endif
	}
	//	-------------------------------------------------------------------------------------------------



	
}