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
import com.cell.CObject;
import com.cell.IImages;

public class CWorldMini extends CObject {

	public int[] MapColor;

	public int X = 0;
	public int Y = 0;

	public int SprPriority = 0;

	
	int W;
	int H;
	int CW;
	int CH;

	int WW;
	int WH;
	
	int WTW;
	int WTH;
	
	CWorld World;
	
	Image Buffer;
	Graphics bg;

	boolean ShowSpr = true;
	boolean ShowMap = true;
	boolean ShowCam = true;

	public CWorldMini(
			CWorld world,
			int width,int height,
			int cellW,int cellH,
			int colorKeyMapPos,
			int colorKeySprPos, 
			boolean isShowMap, 
			boolean isShowSpr, 
			boolean isShowCam
			){
		
		ShowSpr = isShowSpr;
		ShowMap = isShowMap;
		ShowCam = isShowCam;
		
		World = world;
		W = width;
		H = height;
		CW = cellW;
		CH = cellH;
		WW = W*World.getMap().getCellW()/CW;
		WH = H*World.getMap().getCellH()/CH;
		WTW = World.getMap().getWCount() * CW;
		WTH = World.getMap().getHCount() * CH;
		
		if(ShowSpr){
			for(int i=0;i<world.getSpriteCount();i++){
	       		try {
	       			IImages images = world.getSprite(i).getAnimates().images;
	       			int index = world.getSprite(i).getAnimates().STileID[world.getSprite(i).getAnimates().Frames[world.getSprite(i).CurAnimate][world.getSprite(i).CurFrame]];
					world.getSprite(i).BackColor = images.getPixel(
							index, 
							colorKeySprPos%images.getWidth(index),
							colorKeySprPos/images.getWidth(index));
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
					world.getSprite(i).BackColor = 0xffffffff;
				}
	       	}
		}
		
		if(ShowMap){
			int[] mapColor = new int[world.getMap().getAnimates().getCount()];
	    	for(int i=0;i<mapColor.length;i++){
	    		try {
	    			IImages images = world.getMap().getAnimates().images;
	       			int index = world.getMap().getAnimates().STileID[world.getMap().getAnimates().Frames[i][0]];
	    			mapColor[i] = images.getPixel(
	    					index, 
	    					colorKeyMapPos%images.getWidth(index),
	    					colorKeyMapPos/images.getWidth(index)
							);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
					mapColor[i] = 0xff00ff00;
				}
	       	}
	    	MapColor = mapColor;
	    	
	    	Buffer = Image.createImage(WTW ,WTH);
			bg = Buffer.getGraphics();
			for (int by = 0; by < world.getMap().getHCount(); by++) {
				for (int bx = 0; bx < world.getMap().getWCount(); bx++) {
					bg.setColor(MapColor[World.Map.getTile(bx, by)]);
					bg.fillRect(bx*CW, by*CH, CW, CH);
				}
			}
		}

	}

	public int getWidth(){
		return W;
	}
	public int getHeight(){
		return H;
	}
	public int getWorldWidth(){
		return WW;
	}
	public int getWorldHeight(){
		return WH;
	}
	
	public void render(Graphics g,int x,int y){
		try{
			int cx = g.getClipX();
			int cy = g.getClipY();
			int cw = g.getClipWidth();
			int ch = g.getClipHeight();
	    	g.setClip(x,y,W,H);
	    	
	    	if(!World.getMap().IsCyc){
	    	
		    	if(X<0)X=0;
		    	if(X+WW>World.getMap().getWidth())X=World.getMap().getWidth()-WW;
		    	if(Y<0)Y=0;
		    	if(Y+WH>World.getMap().getHeight())Y=World.getMap().getHeight()-WH;

				if(ShowMap){
					AScreen.drawRegion(g, Buffer, 
							(X)*CW/World.getMap().CellW, 
							(Y)*CH/World.getMap().CellH,
							W, H, 
							x, y);
				}
				if(ShowSpr){
					for(int i=0;i<World.getSpriteCount();i++){
						if( World.getSprite(i).Visible &&
							World.getSprite(i).Priority == SprPriority
							){
							g.setColor(World.getSprite(i).BackColor);
							g.fillRect(
									x -CW/2 + (World.getSprite(i).X-X)*CW/World.getMap().CellW, 
									y -CH/2 + (World.getSprite(i).Y-Y)*CH/World.getMap().CellH, 
									CW, CH);
						}
					}
				}
				if(ShowCam){
					g.setColor(World.getCamera().BackColor);
					g.drawRect(
							x -CW/2 + (World.getCamera().getX()-X)*CW/World.getMap().CellW, 
							y -CH/2 + (World.getCamera().getY()-Y)*CH/World.getMap().CellH, 
							World.getCamera().getWidth() *CW/World.getMap().CellW, 
							World.getCamera().getHeight()*CH/World.getMap().CellH);
				}
				
				
	    	}else{
	    		int TX = CMath.cycNum(X, 0, World.getMap().getWidth());
	    		int TY = CMath.cycNum(Y, 0, World.getMap().getHeight());
	    		
				if(ShowMap){
					int sx = (TX)*CW/World.getMap().CellW;
					int sy = (TY)*CH/World.getMap().CellH;
					int dx = sx + W;
					int dy = sy + H;
					int sbx = sx / WTW ;
					int sby = sy / WTH ;
					int dbx = dx / WTW ;
					int dby = dy / WTH ;
					
					for(int bx=0;bx<=dbx-sbx;bx++){
						for(int by=0;by<=dby-sby;by++){
							g.drawImage(Buffer, x + bx*WTW-sx, y + by*WTH-sy, 0);
						}
					}
				}
				if(ShowSpr){
					for(int i=0;i<World.getSpriteCount();i++){
						if( World.getSprite(i).Visible &&
							World.getSprite(i).Priority == SprPriority
							){
							g.setColor(World.getSprite(i).BackColor);
							g.fillRect(
									x -CW/2 + (World.getSprite(i).X-X)*CW/World.getMap().CellW, 
									y -CH/2 + (World.getSprite(i).Y-Y)*CH/World.getMap().CellH, 
									CW, CH);
						}
					}
				}
				if(ShowCam){
					g.setColor(World.getCamera().BackColor);
					g.drawRect(
							x -CW/2 + (World.getCamera().getX()-X)*CW/World.getMap().CellW, 
							y -CH/2 + (World.getCamera().getY()-Y)*CH/World.getMap().CellH, 
							World.getCamera().getWidth() *CW/World.getMap().CellW, 
							World.getCamera().getHeight()*CH/World.getMap().CellH);
				}
	    	}
	    	
	    	g.setClip(cx,cy,cw,ch);
	    	
		}catch(RuntimeException e){
		}
		
	}
	
	
}
