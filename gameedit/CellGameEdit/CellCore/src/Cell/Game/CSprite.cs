package Cell.Game;

import javax.microedition.lcdui.Graphics;


/*
 * Created on 2004-11-8
 *
 *  
 * 
 */

/**
 * 组合精灵类
 *  
 */
public class CSprite extends CUnit {
	
//	----------------------------------------------------------------------------------------------

	/** 是否显示 */
	 public boolean Visible = true;
	/** 是否活动 */
	 public boolean Active = true;
	/** 是否和精灵有碰撞 */
	 public boolean haveSprCD = true;
	/** 是否和地图有碰撞 */
	 public boolean haveMapCD = true;
		
	 protected int X = 0;
	 protected int Y = 0;
	 protected boolean OnScreen = true;
	 protected CCD mapcd ;
//	----------------------------------------------------------------------------------------------
		
	//碰撞块
	 protected CCollides collides;
	//动画相关
	 protected CAnimate animates;

	//帧相关
	 protected int FrameAnimate[][];
	 protected int FrameCollide[][];

	 protected int CurAnimate;
	 protected int CurFrame;
	
//	----------------------------------------------------------------------------------------------
	/*
	 * 物理相关 no method
	 */
	/** X方向速度，256倍定点数 */
	 public int SpeedX256; //8位定点数
	/** Y方向速度，256倍定点数 */
	 public int SpeedY256; //8位定点数

	/** X方向 */
	 public int DirectX; //方向
	/** Y方向 */
	 public int DirectY; //方向

	/** 水平坐标的256倍幻影 */
	 public int HPos256;
	/** 垂直坐标的256倍幻影 */
	 public int VPos256;
	
	//	-------------------------------------------------------------------------------------------------

	 public CSprite(CSprite spr){
		 
	 }
	 
	/**
	 * 构造化精灵
	 * 
	 * @param t
	 *            精灵用的TILE组。
	 * @param type
	 *            精灵类型
	 * @param count
	 *            子精灵数
	 * @param cdcount
	 *            碰撞块数
	 */
	public CSprite(
			CAnimate canimate, 
			CCollides ccollides) {

		animates = canimate;
		collides = ccollides;

		//动画
		CurAnimate = 0;
		CurFrame = 0;
	}

	
	//------------------------------------------------------------------------------------------

	static public boolean touchFrame(
			CSprite spr1,int anim1,int frame1,
			CSprite spr2,int anim2,int frame2){
		if(spr1.haveSprCD && spr2.haveSprCD){
			
		}
		return false;
	}
	
	static public boolean touchCurFrame(
			CSprite spr1,int frame1,
			CSprite spr2,int frame2){
		return false;
	}

	static public boolean touchCurAnimate(
			CSprite spr1,int anim1,
			CSprite spr2,int anim2){
		if(spr1.haveSprCD && spr2.haveSprCD){
			
		}
		return false;
	}
	


	static protected boolean touchSpr(CSprite spr1,CSprite spr2){
		if(spr1.haveSprCD && spr2.haveSprCD){
			return CCD.touchRect(
				spr1.mapcd, spr1.X, spr1.Y, 
				spr2.mapcd, spr2.X, spr2.Y);
		}
		return false;
	}
	
	static protected boolean touchMap(CSprite spr,CMap map){
		if(spr.haveMapCD){
			int mbx = (spr.X+spr.mapcd.X)/map.CellW-((spr.X+spr.mapcd.X)<0?1:0);
			int mby = (spr.Y+spr.mapcd.Y)/map.CellH-((spr.Y+spr.mapcd.Y)<0?1:0);
			int mbw = spr.mapcd.W/map.CellW+2;
			int mbh = spr.mapcd.H/map.CellH+2;
			if(map.IsCyc==false){
				for (int y=mby<0?0:mby; y<mby+mbh && y<map.getHCount(); y++) {
					for (int x=mbx<0?0:mbx; x<mbx+mbw && x<map.getWCount(); x++) {
						if (map.MatrixCD[y][x] > 0) //
						{
							if(CCD.touch(
									spr.mapcd, 
									spr.X, 
									spr.Y, 
									map.mapcd, 
									x*map.CellW, 
									y*map.CellH,
									true)
							){
								return true;
							}
						}
					}
				}
			}else{
				for (int y = mby; y < mby+mbh; y++) {
					for (int x = mbx; x < mbx+mbw; x++) {
						if (map.MatrixCD[cycNum(y,0,map.getHCount())][cycNum(x, 0, map.getWCount())] > 0) //
						{
							if(CCD.touch(
									spr.mapcd, 
									spr.X, 
									spr.Y, 
									map.mapcd, 
									x*map.CellW, 
									y*map.CellH,
									true
									)
							){
								return true;
							}
						}	
					}
				}
			}
		}
		return false;
	}
	
	static protected boolean touchWorldAll(CSprite spr,CWorld world){
		if(touchWorldSprGetID(spr, world)>=0){
			return true;
		}
		if(touchMap(spr,world.Map)){
			return true;
		}
		return false;
	}
	
	static protected int touchWorldSprGetID(CSprite spr,CWorld world){
		for(int i=0;i<world.Sprs.length;i++){
			if(spr.equals(world.Sprs[i])==false && touchSpr(spr,world.Sprs[i])){
				return i;
			}
		}
		return -1;
	}

	public boolean mov(int x,int y){
		
		int dstX = X + x;
		int dstY = Y + y;
		int dx = (x!=0?(x<0?-1:1):0);
		int dy = (y!=0?(y<0?-1:1):0);

		X+=x;Y+=y;
		
		int adjustSprID = touchWorldSprGetID(this, this.world);
		boolean adjustMap = touchMap(this, this.world.Map);

		if(adjustMap==true||adjustSprID>=0){
			X-=x;Y-=y;
			while(X!=dstX){
				X+=dx;
				if((adjustSprID>=0 && touchSpr(this,world.Sprs[adjustSprID])) || //
				   (adjustMap && touchMap(this,this.world.Map)) //
				    ){
					X-=dx;
					break;
				}
			}
			while(Y!=dstY){
				Y+=dy;
				if((adjustSprID>=0 && touchSpr(this,world.Sprs[adjustSprID])) || //
				   (adjustMap && touchMap(this,this.world.Map)) //
				    ){
					Y-=dy;
					break;
				}
			}
			return true;
		}else{
			return false;
		}
		

	}
	
	public void setPos(int x,int y){
		X = x;
		Y = y;
	}
	
	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}
	

//	------------------------------------------------------------------------------------------

	final public void setFrames(int[][] frameAnimate,int[][] frameCollides) {
		FrameAnimate = frameAnimate;
		FrameCollide = frameCollides;
	}
	
	final public void setFrame(int id, int[] animate,int[] collides) {
		FrameAnimate[id] = animate;
		FrameCollide[id] = collides;
	}

	final public int getAnimateCount() {
		return FrameAnimate.length;
	}

	final public int getFrameCount(int id) {
		return FrameAnimate[id].length;
	}

	final public int getCurrentFrame() {
		return CurFrame;
	}

	final public int getCurrentAnimate() {
		return CurAnimate;
	}

	/**
	 * 设置当前贞
	 * 
	 * @param id
	 *            动画索引。
	 * @param index
	 *            贞号。
	 */
	final public void setCurrentFrame(int id, int index) {
		CurAnimate = (short) id;
		CurFrame = (short) index;
	}

	/**
	 * 到下一贞
	 * 
	 * @return is End of Frames
	 */
	final public boolean nextFrame() {
		if (CurFrame < FrameAnimate[CurAnimate].length - 1) {
			CurFrame++;
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 到下一贞，如果到最后一贞，则循环。
	 */
	final public void nextCycFrame() {
		
		CurFrame++;
		CurFrame%=FrameAnimate[CurAnimate].length;
	}

	/**
	 * 到下一贞，如果到最后一贞，则在指定位置循环。
	 * 
	 * @param restart
	 *            重播的位置
	 */
	final public void nextCycFrame(int restart) {
		CurFrame++;
		if (CurFrame < FrameAnimate[CurAnimate].length) {
		} else {
			CurFrame = (byte) (restart % FrameAnimate[CurAnimate].length);
		}
	}

	//--------------------------------------------------------------------------------------------------------

	/**
	 * 渲染精灵
	 * 
	 * @param g
	 */
	final public void render(Graphics g,int x,int y) {
		if (Visible) {
			if(mapcd!=null){
				mapcd.render(g, x, y, DebugColor);
			}
			animates.render(g, 
					FrameAnimate[CurAnimate][CurFrame],x,y);
			if(isDebug){
				collides.render(g, 
						FrameCollide[CurAnimate][CurFrame],x,y,DebugColor);
			}
			
		}
	}


	//	----------------------------------------------------------------------------------------------------

}

