
package com.cell.gfx.game;

import java.util.Hashtable;
import java.util.Vector;

import com.cell.CMath;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.CUtil.ICompare;
import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImages;



/**
 * @since 2006-11-30 
 * @version 1.0
 */
public class CWorld extends CObject
{
	public IGameListener WorldListener;
	
	public boolean IsVisible = true;
	
	public int Width;
	public int Height;
	
	protected Hashtable<String, IImages> m_TilesTable	= new Hashtable<String, IImages>();
	protected Hashtable<String, CSprite> m_SprsTable 	= new Hashtable<String, CSprite>();
	
	
	protected Vector<CSprite> Units = new Vector<CSprite>();	
	protected Vector<CSprite> SprsA = new Vector<CSprite>();
	
	public CMap 		Map;
	public CCamera 		Camera;
	public CWayPoint[] 	WayPoints ;
	public boolean 		isRPGView = false;

	public boolean IsDebug			= false;
	
	//
	public byte 	MovingFirstMapCD	= -1;
	public byte 	MovingFirstSprCD	= -1;

	
//	public Vector Maps = new Vector(0);
//	public Vector Cams = new Vector(0);
	
//	public Vector		SprBuffer = new Vector();	
	
	Vector		Lights = new Vector();
	
	CMapLight 			LayerLight;
	CMapLightCamera		LayerLightCamera;
	
	
	public CWorld(){}
	
	public CWorld(CMap map,CCamera camera,CSprite[] sprs) {
		Map = map;
		Camera = camera;
		addSprites(sprs);
	}
	
	public void EnableLight(boolean light){
		if(light)
		{
			LayerLight = new CMapLight(Map);
			LayerLight.world = this;
			
			LayerLightCamera = new CMapLightCamera(
					Camera.WindowX,
					Camera.WindowY,
					Camera.WindowW,
					Camera.WindowH,
					LayerLight);
			LayerLightCamera.world = this;
			
		}else{
			LayerLight = null;
			LayerLightCamera = null;
		}
	}
	

//	------------------------------------------------------------------------------------------------------
	
   	public boolean containsImages(String key){
   		return m_TilesTable.containsKey(key);
   	}
   	
	public IImages getImagesStuff(String key){
		IImages tile = (IImages)(m_TilesTable.get(key));
		return tile;
	}
	
	public void putImagesStuff(String key, IImages tile){
		if(tile!=null && !m_TilesTable.contains(tile)){
			m_TilesTable.put(key, tile);
		}
	}
	
 	public boolean containsSprite(String key){
   		return m_SprsTable.containsKey(key);
   	}
	
	public CSprite getSpriteStuff(String key){
		CSprite spr = (CSprite)(m_SprsTable.get(key));
		return spr;
	}
	
	public void putSpriteStuff(String key, CSprite sprite){
		if(sprite!=null && !m_SprsTable.contains(sprite)){
			m_SprsTable.put(key, sprite);
		}
	}
	
//	------------------------------------------------------------------------------------------------------
//	sprite

	protected static class SpriteZBuffer implements ICompare<CSprite, CSprite> 
	{
		public int compare(CSprite a, CSprite b) {
			return a.Y - b.Y;
		}
	}
	
	public void bufferz() {
		CUtil.sort(SprsA, new SpriteZBuffer());
	}
	
	public void addSprite(CSprite spr) {
		if (isRPGView) {
			int index = SprsA.size();
			for (int i = 0; i < index; i++) {
				CSprite spra = (CSprite) SprsA.elementAt(i);
				if (spra.Y + spra.Priority < spr.Y + spr.Priority) {
					index = i;
					break;
				}
			}
			SprsA.insertElementAt(spr, index);
		} else {
			this.SprsA.insertElementAt(spr, 0);
		}

		this.Units.addElement(spr);
		
		spr.world = this;
	
		if(WorldListener!=null)WorldListener.sprAdded(this, spr);
	}
	
	public void removeSprite(CSprite spr){
		spr.IsRemoved = true;
	}
	
	public void addSprites(CSprite[] sprs){
		for(int i=0;i<sprs.length;i++){
			addSprite(sprs[i]);
		}
	}
	public void addSprites(Vector sprs){
		for(int i=0;i<sprs.size();i++){
			addSprite((CSprite)(sprs.elementAt(i)));
		}
	}
	
	public CSprite getSprite(int index){
		return (CSprite)Units.elementAt(index);
	}
	public int getSpriteCount(){
		return Units.size();
	}
	public int getSpriteIndex(CSprite spr){
		return Units.indexOf(spr);
	}
	
//	------------------------------------------------------------------------------------------------------
//	map

	public void addMap(CMap map){
		setMap(map);
	}
	public void setMap(CMap map){
		Map = map;
		Map.world = this;
	}
	public CMap getMap(){
		return Map;
	}
	
//	------------------------------------------------------------------------------------------------------
//	camera
	
	public void addCamera(CCamera camera){
		setCamera(camera);
	}
	public void setCamera(CCamera camera){
		Camera = camera;
		Camera.world = this;
	}
	public CCamera getCamera(){
		return Camera;
	}
	
//	------------------------------------------------------------------------------------------------------
	public int World2ScreenX = 0;
	public int World2ScreenY = 0;
	public int Screen2WorldX = 0;
	public int Screen2WorldY = 0;
	
	public int getWorldPointerX(){
		return AScreen.getPointerX() + Screen2WorldX;
	}

	public int getWorldPointerY(){
		return AScreen.getPointerY() + Screen2WorldY;
	}
	
	public int toWorldPosX(int screenX){
		return screenX + Camera.X - Camera.WindowX ;
	}
	public int toWorldPosY(int screenY){
		return screenY + Camera.Y - Camera.WindowY ;
	}

	public int toScreenPosX(int worldX){
		return worldX - Camera.X + Camera.WindowX;
	}
	public int toScreenPosY(int worldY){
		return worldY - Camera.Y + Camera.WindowY;
	}
	
//	------------------------------------------------------------------------------------------------------

	public void destory(){
		
	}
	
	
	public void update()
	{
		World2ScreenX = -Camera.X + Camera.WindowX;
		World2ScreenY = -Camera.Y + Camera.WindowY;
		Screen2WorldX = Camera.X - Camera.WindowX;
		Screen2WorldY = Camera.Y - Camera.WindowY;
	}
	
	public void render(IGraphics g)
	{
		if(!IsVisible)return ;
		
		g.pushClip();
    	g.clipRect(Camera.WindowX,Camera.WindowY,Camera.getWidth(),Camera.getHeight());
    	
    	int cpx = -Camera.X+Camera.WindowX;
    	int cpy = -Camera.Y+Camera.WindowY;
    	int cx2 = Camera.X + Camera.getWidth();
    	int cy2 = Camera.Y + Camera.getHeight();
    	
    	renderMap(g);
		
    	renderDebug(g, cpx, cpy, cx2, cy2);
    	
		renderSprites(g, cpx, cpy, cx2, cy2);
		
		renderLight(g, cpx, cpy, cx2, cy2);
		
		g.popClip();
		
	}
	
	
	
	protected void renderMap(IGraphics g)
	{
		Camera.render(g);
	}
	
	protected void renderLight(IGraphics g, int cpx, int cpy, int cx2, int cy2)
	{
		if(LayerLightCamera!=null)
		{
			LayerLightCamera.WindowX = Camera.WindowX;
			LayerLightCamera.WindowY = Camera.WindowY;
			LayerLightCamera.WindowW = Camera.WindowW;
			LayerLightCamera.WindowH = Camera.WindowH;
		
			LayerLightCamera.setPos(Camera.X, Camera.Y);
			
			LayerLightCamera.render(g);
		}
	}
	
	protected void renderDebug(IGraphics g, int cpx, int cpy, int cx2, int cy2)
	{
//		#ifdef _DEBUG
		for(int i=0;IsDebug && WayPoints!=null && i<WayPoints.length;i++){
			if(CMath.intersectRect(
					WayPoints[i].X-1, 
					WayPoints[i].Y-1, 
					WayPoints[i].X+1, 
					WayPoints[i].Y+1, 
					Camera.X, 
					Camera.Y, 
					cx2, cy2
					)){
				WayPoints[i].render(g, cpx, cpy);
			}
		}
//#endif
	}
	
	protected void renderSprites(IGraphics g, int cpx, int cpy, int cx2, int cy2)
	{
		for (int i = SprsA.size() - 1; i >= 0; i--)
		{
			CSprite spr = ((CSprite)SprsA.elementAt(i));
			
			if(spr.IsRemoved)
			{
				this.SprsA.removeElementAt(i);
				this.Units.removeElement(spr);
				if(WorldListener!=null)WorldListener.sprRemoved(this, spr);
//				sprCount --;
//				i--;
			}
			else if (spr.Visible)
			{
				if(CMath.intersectRect(
					spr.X + spr.animates.w_left, 
					spr.Y + spr.animates.w_top, 
					spr.X + spr.animates.w_right, 
					spr.Y + spr.animates.w_bottom, 
					Camera.X, 
					Camera.Y, 
					cx2, cy2))
				{
					spr.OnScreen = true;
				}else{
					spr.OnScreen = false;
				}
				spr.render(g, spr.X + cpx, spr.Y + cpy);
			}
		}
	}
	
	
	
	public void renderSprites(IGraphics g,Vector sprs)
	{
		int cpx = -Camera.X+Camera.WindowX;
    	int cpy = -Camera.Y+Camera.WindowY;
    	
    	int cx2 = Camera.X + Camera.getWidth();
    	int cy2 = Camera.Y + Camera.getHeight();
		
		for(int i=0;i<sprs.size();i++){
			CSprite spr = ((CSprite)sprs.elementAt(i));
			if (spr.Visible){
				if(CMath.intersectRect(
					spr.X + spr.animates.w_left, 
					spr.Y + spr.animates.w_top, 
					spr.X + spr.animates.w_right, 
					spr.Y + spr.animates.w_bottom, 
					Camera.X, 
					Camera.Y, 
					cx2, cy2
					)){
					spr.OnScreen = true;
				}else{
					spr.OnScreen = false;
				}
				spr.render(g, spr.X + cpx, spr.Y + cpy);
			}
		}
	}
	
	public void renderSprites(IGraphics g,CSprite[] sprs){
		
		int cpx = -Camera.X+Camera.WindowX;
    	int cpy = -Camera.Y+Camera.WindowY;
    	
    	int cx2 = Camera.X + Camera.getWidth();
    	int cy2 = Camera.Y + Camera.getHeight();
		
		
		for(int i=0;i<sprs.length;i++){
			if(sprs[i].Visible){
				if(CMath.intersectRect(
					sprs[i].X + sprs[i].animates.w_left, 
					sprs[i].Y + sprs[i].animates.w_top, 
					sprs[i].X + sprs[i].animates.w_right, 
					sprs[i].Y + sprs[i].animates.w_bottom, 
					Camera.X, 
					Camera.Y, 
					cx2, cy2
					)){
					sprs[i].OnScreen = true;
				}else{
					sprs[i].OnScreen = false;
				}
				sprs[i].render(g, sprs[i].X + cpx, sprs[i].Y + cpy);
			}
		}
	}
	
	public void renderWayPoints(IGraphics g,CWayPoint[] waypoints){
		
		int cpx = -Camera.X+Camera.WindowX;
    	int cpy = -Camera.Y+Camera.WindowY;
    	int cx2 = Camera.X + Camera.getWidth();
    	int cy2 = Camera.Y + Camera.getHeight();
    	
		for(int i=0;/*IsDebug &&*/ waypoints!=null && i<waypoints.length;i++){
			if(CMath.intersectRect(
					waypoints[i].X-1, 
					waypoints[i].Y-1, 
					waypoints[i].X+1, 
					waypoints[i].Y+1, 
					Camera.X, 
					Camera.Y, 
					cx2, cy2
					)){
				waypoints[i].render(g, cpx, cpy);
			}
		}
	}
	static public void tickTimer() {
		AScreen.tickTimer();
	}
	static public void resetTimer() {
		AScreen.resetTimer();
	}
	static public int getTimer() {
		return AScreen.getTimer();
	}
}
