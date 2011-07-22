package com.cell.gfx.game
{
	import com.cell.util.CMath;
	
	import flash.display.Sprite;

	/**
	 * a camera scrollable on a map system, view world unit on screen.</br>
	 * @author yifeizhang
	 * @since 2006-11-29 
	 * @version 1.0
	 */
	public class CCamera
	{
//		private var WindowX		: int;
//		private var WindowY		: int;
//		private var WindowW		: int;
//		private var WindowH		: int;
		
		private var WindowBW	: int;
		private var WindowBH	: int;
		private var WorldW 		: int;
		private var WorldH 		: int;
		
		private	var AnimateTimerOld : int = 0;
		
	//	-----------------------------------------------------------------------------------
		
		private var X			: int;
		private var Y			: int;
		
		private var Map			: CMap;
		private var bg			: CGraphics;
		private var back_buff	: CImage;
		
		private var CellW		: int;
		private var CellH		: int;
	
		private var BufW		: int;
		private var BufH		: int;
		private var BufBW		: int;
		private var BufBH		: int;
		
		private var MapW		: int;
		private var MapH		: int;
		private var MapBW		: int;
		private var MapBH		: int;
		
		private var vBufX		: int;
		private var vBufY		: int;
		private var vBufBX		: int;
		private var vBufBY		: int;
		
		private var vMapX		: int;
		private var vMapY		: int;
		private var vMapBX		: int;
		private var vMapBY		: int;
		
		private var vnT			: int;
		private var vnB			: int;
		private var vnL			: int;
		private var vnR			: int;
	//	-----------------------------------------------------------------------------------
	
	//	-----------------------------------------------------------------------------------
	
		/**
		 * Construct a camera based in specify map.</br>
		 * @param windowX screen window x </br>
		 * @param windowY screen window y </br>
		 * @param windowW screen window width </br>
		 * @param windowH screen window height </br>
		 * @param map one map can be used </br>
		 * @param isBackBuffer is back buffer mode, </br>
		 * true : can run game faster use more heap memory, </br>
		 * false : run game slowly not use memory buffer.</br>
		 * @param backColor 
		 */
		public function CCamera(
//				windowX:int, 
//				windowY:int, 
				windowW:int, 
				windowH:int, 
				map:CMap) 
		{
//			WindowX = windowX;
//			WindowY = windowY;
//			WindowW = windowW;
//			WindowH = windowH;
			
			Map = map;
			
			CellW = map.getCellW();
			CellH = map.getCellH();
			
			WorldW = windowW;
			WorldH = windowH;
			
			MapBW = Map.getXCount();
			MapBH = Map.getYCount();
			MapW = Map.getWidth();
			MapH = Map.getHeight();
			
			var divx : int = windowW%CellW;
			var divy : int = windowH%CellH;
			WorldW = windowW-divx;
			WorldH = windowH-divy;
			
			if(divx!=0)WorldW += CellW;
			if(divy!=0)WorldH += CellH;
			
			
				
				if(WorldW + CellW > MapW)WorldW = MapW - CellW;
				if(WorldH + CellH > MapH)WorldH = MapH - CellH;
				WindowBW = WorldW / CellW;
				WindowBH = WorldH / CellH;
				
				BufBW = WindowBW + 1;
				BufBH = WindowBH + 1;
				BufW = BufBW*CellW;
				BufH = BufBH*CellH;
				
				back_buff	= CImage.createImageBuff(BufW, BufH, 0xff0000ff);
				bg 			= back_buff.createGraphics();
	
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
				for(var y:int=0;y<BufBH;y++)
				{
					for(var x:int=0;x<BufBW;x++)
					{
						Map.renderCell(bg, x*CellW, y*CellH, x, y);
					}

				}
				
				
			
	
	
		}
	
		//----------------------------------------------------------------------------------------------------
	
		/**
		 * get x within map </br>
		 * @return x coordinate
		 */
		public function getX() : int {
			return X;
		}
		/**
		 * get y within map</br>
		 * @return y coordinate
		 */
		public function getY() : int {
			return Y;
		}
		
		/**
		 * get camera size width</br>
		 * @return width
		 */
		public function getWidth() : int {
			return WorldW;
		}
		/**
		 * get camera size height</br>
		 * @return height
		 */
		public function getHeight() : int {
			return WorldH;
		}
		
		/**
		 * set position within map</br>
		 * @param x x 
		 * @param y y
		 */
		public function setPos(x:int, y:int) : void {
			move(x - X, y - Y);
		}
	
		
		/**
		 * move camera within map</br>
		 * @param px offset x
		 * @param py offset y
		 */
		public function move(x:int, y:int) : void
		{
			var px : int = 0; 
			var py : int = 0;
			
			if(Map.isCycMap())
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
			
			
				if( px!=0 )
				{
					var dx : int = (px<0?-1:1);
					var dw : int = Math.abs(px);
					var xCount : int = (dw/CellW+1)<BufBW?(dw/CellW+1):BufBW;
	//				refpos on dest
					vMapX = CMath.cycNum(X,0,MapW);
					vBufX = CMath.cycNum(X,0,BufW);
					vMapBX = CMath.cycNum(vMapX/CellW,0,MapBW);
					vBufBX = CMath.cycNum(vBufX/CellW,0,BufBW);
					vnL = vMapX%CellW;
					vnR = CellW-vnL;
	//				fill back buffer
					var mbx : int = CMath.cycNum(vMapBX,(dx>0?WindowBW:0),MapBW);
					var mby : int = CMath.cycNum(vMapBY,0,MapBH);
					var bbx : int = CMath.cycNum(vBufBX,(dx>0?WindowBW:0),BufBW);
					var bby : int = CMath.cycNum(vBufBY,0,BufBH);
					if(xCount!=0)
					{
						for(var nx:int=0;nx<xCount;nx++)
						{
							for(var ny:int=0;ny<BufBH;ny++)
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
					var dy : int = (py<0?-1:1);
					var dh : int = Math.abs(py);
					var yCount : int = (dh/CellH+1)<BufBH?(dh/CellH+1):BufBH;
	//				refpos				
					vMapY = CMath.cycNum(Y,0,MapH);
					vBufY = CMath.cycNum(Y,0,BufH);
					vMapBY = CMath.cycNum(vMapY/CellH,0,MapBH);
					vBufBY = CMath.cycNum(vBufY/CellH,0,BufBH);
					vnT = vMapY%CellH;
					vnB = CellH-vnT;
	//				fill back buffer
					var mby : int = CMath.cycNum(vMapBY,(dy>0?WindowBH:0),MapBH);
					var mbx : int = CMath.cycNum(vMapBX,0,MapBW);
					var bby : int = CMath.cycNum(vBufBY,(dy>0?WindowBH:0),BufBH);
					var bbx : int = CMath.cycNum(vBufBX,0,BufBW);
					if(yCount!=0)
					{
						for(var ny:int=0;ny<yCount;ny++)
						{
							for(var nx:int=0;nx<BufBW;nx++)
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
	
		
		
//		/**
//		 * set window position within screen</br>
//		 * @param x x
//		 * @param y y
//		 */
//		public function setWinPos(x:int, y:int) : void {
//			WindowX = x;
//			WindowY = y;
//		}
		
		public function resetBuffer() : void 
		{
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
			
			for(var ny:int=0;ny<BufBH;ny++){
				for(var nx:int=0;nx<BufBW;nx++){
					Map.renderCell(bg, 
							CMath.cycNum(vBufBX, nx, BufBW)*CellW, 
							CMath.cycNum(vBufBY, ny, BufBH)*CellH, 
							CMath.cycNum(vMapBX,nx,MapBW), 
							CMath.cycNum(vMapBY,ny,MapBH)
							);
				}
			}
		}
	
		public function resetBufferMapBlock(bx:int, by:int) : void 
		{
			var sbx : int = vMapX/CellW;
			var sby : int  = vMapY/CellH;
			var dbx : int  = vBufX/CellW;
			var dby : int  = vBufY/CellH;
			
			// offset
			var offsetx : int = bx - sbx;
			var offsety : int = by - sby;
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
		public function render(g:CGraphics) : void 
		{
				if(Map.isEnableAnimate() && AnimateTimerOld != Map.getAnimateTimer())
				{
					var sbx : int = vMapX/CellW;
					var sby : int = vMapY/CellH;
					var dbx : int = vBufX/CellW;
					var dby : int = vBufY/CellH;
					
					for(var y:int=0 ;y<BufBH;y++)
					{
						for(var x:int=0;x<BufBW;x++)
						{
							if(!Map.testSameAnimateTile(AnimateTimerOld, Map.getAnimateTimer(), sbx, sby))
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
					
					AnimateTimerOld = Map.getAnimateTimer();
				}
				
				var w1 : int = BufW-vBufX<=WorldW?BufW-vBufX:WorldW;
				var h1 : int = BufH-vBufY<=WorldH?BufH-vBufY:WorldH;
				var w2 : int = WorldW - w1;
				var h2 : int = WorldH - h1;
				
				if (w1 > 0 && h1 > 0) {
					g.drawImageRegion(back_buff, vBufX, vBufY, w1, h1, 0, 0, 0);
				}
				if (w2 > 0 && h2 > 0) {
					g.drawImageRegion(back_buff, 0, 0, w2, h2, 0, w1, h1);
				}
				if (w1 > 0 && h2 > 0) {
					g.drawImageRegion(back_buff, vBufX, 0, w1, h2, 0, 0, h1);
				}
				if (w2 > 0 && h1 > 0) {
					g.drawImageRegion(back_buff, 0, vBufY, w2, h1, 0, w1, 0);
				}
		}
	
	}
}