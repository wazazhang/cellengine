
package com.cell.gfx.game;


import com.cell.CMath;
import com.cell.CObject;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;

public class CWorldMini extends CObject {

	public int[] MapColor;

	public int CameraX = 0;
	public int CameraY = 0;

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
	
	IImage 		Buffer;
	IGraphics 	bg;

	boolean ShowSpr = true;
	boolean ShowMap = true;
	boolean ShowCam = true;

	
	/**
	 * @param world
	 * @param width
	 * @param height
	 * @param cellW
	 * @param cellH
	 * @param colorKeyMapPos
	 * @param colorKeySprPos
	 * @param isShowMap
	 * @param isShowSpr
	 * @param isShowCam
	 * @param surface
	 */
	public CWorldMini(
			CWorld world,
			int width,int height,
			int cellW,int cellH,
			int colorKeyMapPos,
			int colorKeySprPos, 
			boolean isShowMap, 
			boolean isShowSpr, 
			boolean isShowCam,
			IImage surface
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
		WTW = World.getMap().getXCount() * CW;
		WTH = World.getMap().getYCount() * CH;
		
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
					e.printStackTrace();
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
	    	
	    	Buffer = surface.createBuffer(WTW ,WTH);
			bg = Buffer.getIGraphics();
			for (int by = 0; by < world.getMap().getYCount(); by++) {
				for (int bx = 0; bx < world.getMap().getXCount(); bx++) {
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
	
	@Override
	protected void finalize() throws Throwable {
		if (bg!=null) {
			bg.dispose();
		}
	}
	
	public void render(IGraphics g,int x,int y){
		try{
			g.pushClip();
	    	g.setClip(x,y,W,H);
	    	
	    	if(!World.getMap().IsCyc){
	    	
		    	if(CameraX<0)CameraX=0;
		    	if(CameraX+WW>World.getMap().getWidth())CameraX=World.getMap().getWidth()-WW;
		    	if(CameraY<0)CameraY=0;
		    	if(CameraY+WH>World.getMap().getHeight())CameraY=World.getMap().getHeight()-WH;

				if(ShowMap){
					g.drawRegion(Buffer, 
							(CameraX)*CW/World.getMap().CellW, 
							(CameraY)*CH/World.getMap().CellH,
							W, H, 
							0,
							x, y);
				}
				if(ShowSpr){
					for(int i=0;i<World.getSpriteCount();i++){
						if( World.getSprite(i).Visible &&
							World.getSprite(i).Priority == SprPriority
							){
							g.setColor(World.getSprite(i).BackColor);
							g.fillRect(
									x -CW/2 + (World.getSprite(i).X-CameraX)*CW/World.getMap().CellW, 
									y -CH/2 + (World.getSprite(i).Y-CameraY)*CH/World.getMap().CellH, 
									CW, CH);
						}
					}
				}
				if(ShowCam){
					g.setColor(World.getCamera().BackColor);
					g.drawRect(
							x -CW/2 + (World.getCamera().getX()-CameraX)*CW/World.getMap().CellW, 
							y -CH/2 + (World.getCamera().getY()-CameraY)*CH/World.getMap().CellH, 
							World.getCamera().getWidth() *CW/World.getMap().CellW, 
							World.getCamera().getHeight()*CH/World.getMap().CellH);
				}
				
				
	    	}else{
	    		int TX = CMath.cycNum(CameraX, 0, World.getMap().getWidth());
	    		int TY = CMath.cycNum(CameraY, 0, World.getMap().getHeight());
	    		
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
									x -CW/2 + (World.getSprite(i).X-CameraX)*CW/World.getMap().CellW, 
									y -CH/2 + (World.getSprite(i).Y-CameraY)*CH/World.getMap().CellH, 
									CW, CH);
						}
					}
				}
				if(ShowCam){
					g.setColor(World.getCamera().BackColor);
					g.drawRect(
							x -CW/2 + (World.getCamera().getX()-CameraX)*CW/World.getMap().CellW, 
							y -CH/2 + (World.getCamera().getY()-CameraY)*CH/World.getMap().CellH, 
							World.getCamera().getWidth() *CW/World.getMap().CellW, 
							World.getCamera().getHeight()*CH/World.getMap().CellH);
				}
	    	}
	    	
	    	g.popClip();
	    	
		}catch(RuntimeException e){
		}
		
	}
	
	
}
