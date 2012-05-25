package com.cell.gfx.game;


import java.io.Serializable;

import com.cell.CMath;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;

/*
 * Created on 2004-11-8
 *
 *  
 * 
 */


/**
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CSprite extends CUnit implements Serializable
{

	private static final long serialVersionUID = 1L;
	
//	----------------------------------------------------------------------------------------------
	protected boolean IsRemoved 	= false;
	
	public boolean haveSprBlock 	= false;
	public boolean haveMapBlock 	= false;
	public boolean OnScreen 		= true;
	public int Priority				= 0;
	
//	----------------------------------------------------------------------------------------------
//	Logic detail
	
	/** illegible touch, moving do not stop on border */
	public boolean 	movingIllegible	= false;
	
//	----------------------------------------------------------------------------------------------

	protected CAnimates animates;

	protected CCollides collides;
	
	protected String AnimateNames[];
	protected short FrameAnimate[][];
	protected short FrameCDMap[][];
	protected short FrameCDAtk[][];
	protected short FrameCDDef[][];
	protected short FrameCDExt[][];
	
	final static public byte CD_TYPE_MAP 	= 0;
	final static public byte CD_TYPE_ATK 	= 1;
	final static public byte CD_TYPE_DEF 	= 2;
	final static public byte CD_TYPE_EXT 	= 3;
	
	
//	protected byte Transform = IImage.TRANS_NONE;
	
	protected int CurAnimate = 0;
	protected int CurFrame = 0;
	
	
//	----------------------------------------------------------------------------------------------

	public int X = 0;
	public int Y = 0;
	
	public int SpeedX256 = 0;
	public int SpeedY256 = 0;
	
	public int AccX256 = 0;
	public int AccY256 = 0;
	
	public int DirectX = 0;
	public int DirectY = 0;

	public int HPos256 = 0;
	public int VPos256 = 0;
	
//	-------------------------------------------------------------------------------------------------


	/**
	 * @param canimates
	 * @param ccollides
	 * @param frameAnimate 
	 * 			frameAnimate[animate id][frame id] = canimate frame index.
	 * @param frameCDMap 
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDAtk 
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDDef 
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDExt
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 */
	public CSprite(
			CAnimates canimates, 
			CCollides ccollides,
			short[][] frameAnimate,
			short[][] frameCDMap,
			short[][] frameCDAtk,
			short[][] frameCDDef,
			short[][] frameCDExt) 
	{
		this(canimates, ccollides, null, frameAnimate, frameCDMap, frameCDAtk, frameCDDef, frameCDExt);
	}
	
	public CSprite(
			CAnimates canimates, 
			CCollides ccollides,
			String[] animateNames, 
			short[][] frameAnimate, 
			short[][] frameCDMap,
			short[][] frameCDAtk, 
			short[][] frameCDDef, 
			short[][] frameCDExt) 
	{
		animates = canimates;
		collides = ccollides;

		AnimateNames = animateNames;
		
		FrameAnimate = frameAnimate;
		FrameCDMap = frameCDMap;
		FrameCDAtk = frameCDAtk;
		FrameCDDef = frameCDDef;
		FrameCDExt = frameCDExt;

	}

	/**
	 * @param spr 
	 */
	public CSprite(CSprite spr)
	{
		animates = spr.animates;
		collides = spr.collides;

		AnimateNames		= spr.AnimateNames;
		
		FrameAnimate 	= spr.FrameAnimate;
		FrameCDMap 		= spr.FrameCDMap;
		FrameCDAtk 		= spr.FrameCDAtk;
		FrameCDDef 		= spr.FrameCDDef;
		FrameCDExt 		= spr.FrameCDExt;
		
//		Transform 		= spr.Transform;
		
		Priority 		= spr.Priority;
		
		haveSprBlock 	= spr.haveSprBlock;
		haveMapBlock 	= spr.haveMapBlock;
		OnScreen 		= spr.OnScreen;

		movingIllegible	= spr.movingIllegible;
		
		CurAnimate 		= spr.CurAnimate;
		CurFrame 		= spr.CurFrame;
		
		
	}

	/**
	 * @return 
	 */
	public CSprite copy()
	{
		 CSprite spr = new CSprite(this);
		 spr.world = this.world;
		 return spr;
	}
	
	public CSprite clone()
	{
		CAnimates animates = (CAnimates) this.animates.clone();

		CCollides collides = (CCollides) this.collides.clone();
		
		String AnimateNames[] = this.AnimateNames.clone();
		short FrameAnimate[][] = this.FrameAnimate.clone();
		short FrameCDMap[][] = this.FrameCDMap.clone();
		short FrameCDAtk[][] = this.FrameCDAtk.clone();
		short FrameCDDef[][] = this.FrameCDDef.clone();
		short FrameCDExt[][] = this.FrameCDExt.clone();
		
		CSprite spr = new CSprite(animates, collides, 
								AnimateNames, 
								FrameAnimate,
								FrameCDMap, FrameCDAtk, FrameCDDef, FrameCDExt);
		
		spr.IsRemoved 	= this.IsRemoved;
		
		spr.haveSprBlock 	= this.haveSprBlock;
		spr.haveMapBlock 	= this.haveMapBlock;
		spr.OnScreen 		= this.OnScreen;
		spr.Priority		= this.Priority;
		
		spr.movingIllegible	= this.movingIllegible;
		
//		spr.Transform = this.Transform;
		
		spr.CurAnimate = this.CurAnimate;
		spr.CurFrame = this.CurFrame;

		spr.X = this.X;
		spr.Y = this.Y;
		
		spr.SpeedX256 = this.SpeedX256;
		spr.SpeedY256 = this.SpeedY256;
		
		spr.AccX256 = this.AccX256;
		spr.AccY256 = this.AccY256;
		
		spr.DirectX = this.DirectX;
		spr.DirectY = this.DirectY;

		spr.HPos256 = this.HPos256;
		spr.VPos256 = this.VPos256;		 

		spr.world 		= this.world;
		spr.Visible 	= this.Visible;
		spr.Active 		= this.Active; 
		spr.BackColor 	= this.BackColor;
		spr.IsDebug		= this.IsDebug;
		
		return spr;
	}
	
//	------------------------------------------------------------------------------------------

	public int getFrameImageCount(int anim, int frame){
		return animates.Frames[FrameAnimate[anim][frame]].length;
	}
	
	public IImage getFrameImage(int anim,int frame,int sub){
		return animates.getFrameImage(FrameAnimate[anim][frame], sub);
	}
	
	public int getFrameImageX(int anim,int frame,int sub){
		return animates.getFrameX(FrameAnimate[anim][frame], sub);
	}
	
	public int getFrameImageY(int anim,int frame,int sub){
		return animates.getFrameY(FrameAnimate[anim][frame], sub);
	}
	
	public int getFrameImageWidth(int anim, int frame, int sub) {
		return animates.getFrameW(FrameAnimate[anim][frame], sub);
	}
	
	public int getFrameImageHeight(int anim, int frame, int sub) {
		return animates.getFrameH(FrameAnimate[anim][frame], sub);
	}
	
	public byte getFrameImageTransform(int anim, int frame, int sub) {
		return animates.getFrameTransform(FrameAnimate[anim][frame], sub);
	}
	
	public IImage getCurrentImage(int sub){
		return animates.getFrameImage(FrameAnimate[CurAnimate][CurFrame], sub);
	}
	
//	------------------------------------------------------------------------------------------

	public int getFrameCDCount(int anim, int frame, int type){
		switch(type){
		case CD_TYPE_MAP:
			return collides.Frames[FrameCDMap[anim][frame]].length;
		case CD_TYPE_ATK:
			return collides.Frames[FrameCDAtk[anim][frame]].length;
		case CD_TYPE_DEF:
			return collides.Frames[FrameCDDef[anim][frame]].length;
		case CD_TYPE_EXT:
			return collides.Frames[FrameCDExt[anim][frame]].length;
		}
		return -1;
	}

	public CCD getFrameCD(int anim,int frame,int type,int sub){
		switch(type){
		case CD_TYPE_MAP:
			return collides.getFrameCD(FrameCDMap[anim][frame], sub);
		case CD_TYPE_ATK:
			return collides.getFrameCD(FrameCDAtk[anim][frame], sub);
		case CD_TYPE_DEF:
			return collides.getFrameCD(FrameCDDef[anim][frame], sub);
		case CD_TYPE_EXT:
			return collides.getFrameCD(FrameCDExt[anim][frame], sub);
		}
		return null;
	}
	
	public CCD getCurrentCD(int type,int sub){
		switch(type){
		case CD_TYPE_MAP:
			return collides.getFrameCD(FrameCDMap[CurAnimate][CurFrame], sub);
		case CD_TYPE_ATK:
			return collides.getFrameCD(FrameCDAtk[CurAnimate][CurFrame], sub);
		case CD_TYPE_DEF:
			return collides.getFrameCD(FrameCDDef[CurAnimate][CurFrame], sub);
		case CD_TYPE_EXT:
			return collides.getFrameCD(FrameCDExt[CurAnimate][CurFrame], sub);
		}
		return null;
	}
	
	//
	static public boolean touch_SprFrame_Point(
			CSprite spr1, int anim, int frame, int type1, 
			int x, int y)
	{
		return touch_SprFrame_CD(spr1, anim, frame, type1, CCD.createCDRect(0xffffffff, 0, 0, 1, 1), x, y);
	}
	
	
	
	static public boolean touch_Spr_CD(
			CSprite spr1, int type1, 
			CCD cd, int x, int y) 
	{
		return touch_SprFrame_CD(spr1, spr1.CurAnimate, spr1.CurFrame, type1, cd, x, y);
	}
	
	static public boolean touch_SprFrame_CD(
			CSprite spr1, int anim, int frame, int type1, 
			CCD cd, int x, int y)
	{
		int index1 = 0;
		
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[anim][frame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[anim][frame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[anim][frame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[anim][frame];break;
		}
		
		if(CCollides.touchCD(
				spr1.collides, index1, spr1.X, spr1.Y, 
				cd, x, y
				)){
			return true;
		}
		return false;
	}
	
	static public boolean touch_SprSub_CD(
			CSprite spr1, int type1, int sub1, 
			CCD cd, int x, int y)
	{
		return touch_SprFrameSub_CD(spr1, spr1.CurAnimate, spr1.CurFrame, type1, sub1, cd, x, y);
	}
	
	static public boolean touch_SprFrameSub_CD(
			CSprite spr1, int anim, int frame, int type1, int sub1, 
			CCD cd, int x, int y)
	{
		int index1 = 0;
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[anim][frame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[anim][frame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[anim][frame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[anim][frame];break;
		}
		
		try{
			if(CCollides.touchSubCD(
					spr1.collides, index1, sub1, spr1.X, spr1.Y, 
					cd, x, y
					)){
				return true;
			}
		}catch(Exception err){
			return false;	
		}
		
		
		return false;
	}


	static public boolean touch_Spr_Spr(
			CSprite spr1, int type1,
			CSprite spr2, int type2) 
	{
		return touch_SprFrame_SprFrame(spr1, spr1.CurAnimate, spr1.CurFrame, type1, spr2, spr2.CurAnimate, spr2.CurFrame, type2);
	}

	static public boolean touch_SprFrame_SprFrame(
			CSprite spr1, int anim1, int frame1, int type1,
			CSprite spr2, int anim2, int frame2, int type2) 
	{
		int index1 = 0;
		int index2 = 0;
		
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[anim1][frame1];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[anim1][frame1];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[anim1][frame1];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[anim1][frame1];break;
		}
		switch(type2){
		case CD_TYPE_MAP:
			index2=spr2.FrameCDMap[anim2][frame2];break;
		case CD_TYPE_ATK:
			index2=spr2.FrameCDAtk[anim2][frame2];break;
		case CD_TYPE_DEF:
			index2=spr2.FrameCDDef[anim2][frame2];break;
		case CD_TYPE_EXT:
			index2=spr2.FrameCDExt[anim2][frame2];break;
		}
		if(CCollides.touch(
				spr1.collides, index1, spr1.X, spr1.Y, 
				spr2.collides, index2, spr2.X, spr2.Y
				)){
			return true;
		}
		return false;
	}
	
	static public boolean touch_SprSub_SprSub(
			CSprite spr1,int type1,int sub1,
			CSprite spr2,int type2,int sub2)
	{
		return touch_SprFrameSub_SprFrameSub(spr1, spr1.CurAnimate, spr1.CurFrame, type1, sub1, spr2, spr2.CurAnimate, spr2.CurFrame, type2, sub2);
	}
	
	static public boolean touch_SprFrameSub_SprFrameSub(
			CSprite spr1, int anim1, int frame1, int type1,int sub1,
			CSprite spr2, int anim2, int frame2, int type2,int sub2)
	{
		int index1 = 0;
		int index2 = 0;

		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[anim1][frame1];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[anim1][frame1];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[anim1][frame1];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[anim1][frame1];break;
		}
		switch(type2){
		case CD_TYPE_MAP:
			index2=spr2.FrameCDMap[anim2][frame2];break;
		case CD_TYPE_ATK:
			index2=spr2.FrameCDAtk[anim2][frame2];break;
		case CD_TYPE_DEF:
			index2=spr2.FrameCDDef[anim2][frame2];break;
		case CD_TYPE_EXT:
			index2=spr2.FrameCDExt[anim2][frame2];break;
		}
		
		try{
			if(CCollides.touchSub(
					spr1.collides, index1, sub1, spr1.X, spr1.Y, 
					spr2.collides, index2, sub2, spr2.X, spr2.Y
					)){
				return true;
			}
		}catch(Exception err){
			return false;	
		}

		return false;
	}
	
	static public boolean touch_Spr_Map(CSprite spr,int type,CMap map, int mapLayer)
	{
		return touch_SprFrame_Map(spr, spr.CurAnimate, spr.CurFrame, type, map, mapLayer);
	}
	

	static public boolean touch_SprFrame_Map(CSprite spr, int anim, int frame, int type, CMap map, int mapLayer)
	{
		switch (type) {
		case CD_TYPE_MAP:
			for (int sub = 0; sub < spr.collides.Frames[spr.FrameCDMap[anim][frame]].length; sub++) {
				if (touch_SprFrameSub_Map(spr, anim, frame, type, sub, map, mapLayer))
					return true;
			}
		case CD_TYPE_ATK:
			for (int sub = 0; sub < spr.collides.Frames[spr.FrameCDAtk[anim][frame]].length; sub++) {
				if (touch_SprFrameSub_Map(spr, anim, frame, type, sub, map, mapLayer))
					return true;
			}
		case CD_TYPE_DEF:
			for (int sub = 0; sub < spr.collides.Frames[spr.FrameCDDef[anim][frame]].length; sub++) {
				if (touch_SprFrameSub_Map(spr, anim, frame, type, sub, map, mapLayer))
					return true;
			}
		case CD_TYPE_EXT:
			for (int sub = 0; sub < spr.collides.Frames[spr.FrameCDExt[anim][frame]].length; sub++) {
				if (touch_SprFrameSub_Map(spr, anim, frame, type, sub, map, mapLayer))
					return true;
			}
		}	
		
		return false;
	}

	
	static public boolean touch_SprSub_Map(CSprite spr,int type,int sub,CMap map, int mapLayer)
	{
		return touch_SprFrameSub_Map(spr, spr.CurAnimate, spr.CurFrame, type, sub, map, mapLayer);
	}
	
	static public boolean touch_SprFrameSub_Map(CSprite spr, int anim, int frame, int type,int sub,CMap map, int mapLayer)
	{
			int mbx = (spr.X+spr.getCurrentCD(type,sub).X1)/map.CellW-((spr.X+spr.getCurrentCD(type,sub).X1)<0?1:0);//
			int mby = (spr.Y+spr.getCurrentCD(type,sub).Y1)/map.CellH-((spr.Y+spr.getCurrentCD(type,sub).Y1)<0?1:0);//
			int mbw = (spr.X+spr.getCurrentCD(type,sub).X2)/map.CellW-((spr.X+spr.getCurrentCD(type,sub).X2)<0?1:0);//
			int mbh = (spr.Y+spr.getCurrentCD(type,sub).Y2)/map.CellH-((spr.Y+spr.getCurrentCD(type,sub).Y2)<0?1:0);//
			if(map.IsCyc==false){
				for (int y=mby<0?0:mby; y<=mbh && y<map.getYCount(); y++) {
					for (int x=mbx<0?0:mbx; x<=mbw && x<map.getXCount(); x++) {
						if (map.MatrixFlag[mapLayer][y][x] > 0) //
						{	
							if(CSprite.touch_SprFrameSub_CD(
									spr, anim, frame, type, sub,
									map.getCD(mapLayer, x, y), 
									x*map.CellW, 
									y*map.CellH) 
							){
								return true;
							}
						}
					}
				}
			}else{
				for (int y = mby; y <= mbh; y++) {
					for (int x = mbx; x <= mbw; x++) {
						int cycX = CMath.cycNum(x, 0, map.getXCount());
						int cycY = CMath.cycNum(y, 0, map.getYCount());
						if (map.MatrixFlag[mapLayer][cycX][cycY] > 0) //
						{
							if(CSprite.touch_SprFrameSub_CD(
									spr, anim, frame, type, sub,
									map.getCD(mapLayer, cycX, cycY), 
									x*map.CellW, 
									y*map.CellH)
							){
								return true;
							}
						}	
					}
				}
			}
		
		return false;
	}
	
	
//	------------------------------------------------------------------------------------------
	
	public boolean touchMapMoveBlock()
	{
		if (world.MovingFirstMapCD >= 0) 
		{
			return touch_SprSub_Map(this, CD_TYPE_MAP, world.MovingFirstMapCD, world.Map, 0);
		} 
		else 
		{
			return touch_Spr_Map(this, CD_TYPE_MAP, world.Map, 0);
		}
	}
	
	public boolean touchSprMoveBlock(CSprite other)
	{
		if(world.MovingFirstSprCD >= 0)
		{
			return touch_SprSub_SprSub(
					this, 
					CD_TYPE_MAP, world.MovingFirstSprCD,
					other, 
					CD_TYPE_MAP, world.MovingFirstSprCD
					);
		}
		else
		{
			return touch_Spr_Spr(
					this, 
					CD_TYPE_MAP, 
					other, 
					CD_TYPE_MAP
					);
		}
	}
	
	private static int adjustSprID[] = null;


	public boolean tryMove(int x,int y)
	{
		if(x==0 && y==0)return false;
		
		X+=x;
		Y+=y;
		
		int dx = (x!=0?(x<0?-1:1):0);
		int dy = (y!=0?(y<0?-1:1):0);
		
		boolean adjustSpr = false;
		boolean adjustMap = false;
		int sprCount = world.Units.size();
		
		CSprite blockedSpr = null;
		
		if(this.haveSprBlock)
		{
			if(adjustSprID==null || adjustSprID.length < sprCount)
			{
				adjustSprID = new int[sprCount];
			}
			int p = 0;
			
			for (int i = 0; i < sprCount; i++)
			{
				adjustSprID[i] = -1 ;
				CSprite spr = ((CSprite)world.Units.elementAt(i));
				
				if(!equals(spr) && spr.Active && spr.haveSprBlock)
				{
					if(touchSprMoveBlock(spr))
					{
						adjustSprID[p] = i ;
						p++;
						adjustSpr = true;
						blockedSpr = spr;
						if(movingIllegible)break;
						
					}
				}
			}
		}
		
		if(this.haveMapBlock)
		{
			adjustMap = touchMapMoveBlock();
		}
		
		
		if(adjustMap||adjustSpr)
		{
			if(movingIllegible)
			{
				X-=x;
				Y-=y;
			}
			else
			{
				int dstX = X;
				int dstY = Y;
				
				X-=x;
				Y-=y;
				
				while(X!=dstX)
				{
					X+=dx;
					
					if(adjustMap && touchMapMoveBlock())
					{
						X-=dx;
						break;
					}
					
					if(adjustSpr)
					{
						boolean over = false;
						for(int i=0;i<adjustSprID.length;i++)
						{
							if(adjustSprID[i]<0)break;
							CSprite dst = ((CSprite)world.Units.elementAt(adjustSprID[i]));
							if(touchSprMoveBlock(dst))
							{
								over = true;
								blockedSpr = dst;
								break;
							}
						}
						
						if(over)
						{
							X-=dx;
							break;
						}
					}
				}
				
				while(Y!=dstY)
				{
					Y+=dy;
					
					if(adjustMap && touchMapMoveBlock())
					{
						Y-=dy;
						break;
					}
					
					if(adjustSpr)
					{
						boolean over = false;
						for(int i=0;i<adjustSprID.length;i++)
						{
							if(adjustSprID[i]<0)break;
							CSprite dst = ((CSprite)world.Units.elementAt(adjustSprID[i]));
							if(touchSprMoveBlock(dst))
							{
								over = true;
								blockedSpr = dst;
								break;
							}
						}
						
						if(over)
						{
							Y-=dy;
							break;
						}
					}
				}
			}
		}
		

		if (world.WorldListener!=null)
		{
			world.WorldListener.sprMoved(
					world, 
					this, 
					blockedSpr, 
					!adjustMap?null:world.Map, 
					x, y);
		}
		
		if (world.isRPGView && y!=0)
		{
			int count = world.SprsA.size();
			int s = world.SprsA.indexOf(this);
			int d = s - dy;
			while( 	s<count && s>=0 && d<count && d>=0 )
			{
				CSprite that = (CSprite)world.SprsA.elementAt(d);
				
				if(dy<0 && (that.Y + that.Priority) <= (this.Y + this.Priority)){
					break;
				} 
				if(dy>0 && (that.Y + that.Priority) >= (this.Y + this.Priority)){
					break;
				}
				
				world.SprsA.setElementAt(that, s);
				world.SprsA.setElementAt(this, d);
				
//				println( s + ">" + d + " : ");
				
				s = d ;
				d = s - dy;
			}
		}
		
		return adjustMap||adjustSpr;

	}
	
	public boolean setPos(int x, int y){
		return tryMove(x - X, y - Y);
	}

	
//	------------------------------------------------------------------------------------------
//
//	public byte getCurTransform(){
//		return Transform;
//	}
//	
//	public void transform(byte next){
//		int[] point1 = new int[4];
//		Transform = transFlipNext(Transform, next);
//		
////		 transform image
//		animates.w_left 	= 0;
//		animates.w_top 		= 0;
//		animates.w_right 	= 0;
//		animates.w_bottom 	= 0;
//		for (int i = animates.SFlip.length-1; i >= 0 ; i-- ) {
//			animates.SFlip[i] = transFlipNext(animates.SFlip[i], next);
//			point1[0] = animates.SX[i];
//			point1[1] = animates.SY[i];
//			point1[2] = animates.SX[i]+animates.SW[i];
//			point1[3] = animates.SY[i]+animates.SH[i];
//			CSprite.TransformPoint(point1, next);
//			animates.SX[i] = (short)Math.min(point1[0],point1[2]);
//			animates.SY[i] = (short)Math.min(point1[1],point1[3]);
//			animates.SW[i] = (short)Math.abs(point1[0]-point1[2]);
//			animates.SH[i] = (short)Math.abs(point1[1]-point1[3]);
//			animates.fixArea(
//					animates.SX[i], 
//					animates.SY[i], 
//					animates.SX[i]+animates.SW[i], 
//					animates.SY[i]+animates.SH[i]);
//		}
//		
////		transform collide
//		collides.w_left 	= 0;
//		collides.w_top 		= 0;
//		collides.w_right 	= 0;
//		collides.w_bottom 	= 0;
//		for (int i = collides.cds.length-1; i >= 0 ; i-- ) {
//			point1[0] = collides.cds[i].X1;
//			point1[1] = collides.cds[i].Y1;
//			point1[2] = collides.cds[i].X2;
//			point1[3] = collides.cds[i].Y2;
//			CSprite.TransformPoint(point1, next);
//			collides.cds[i].X1 = (short)Math.min(point1[0],point1[2]);
//			collides.cds[i].Y1 = (short)Math.min(point1[1],point1[3]);
//			collides.cds[i].X2 = (short)Math.max(point1[0],point1[2]);
//			collides.cds[i].Y2 = (short)Math.max(point1[1],point1[3]);
//			collides.fixArea(
//					collides.cds[i].X1, 
//					collides.cds[i].Y1, 
//					collides.cds[i].X2, 
//					collides.cds[i].Y2);
//		}
//	}
//	
//	static protected byte SubSprFlipTable[][] = {
//			{	//ScreenFlip = NONE
//				IImage.TRANS_NONE ,
//				IImage.TRANS_H ,
//				IImage.TRANS_V ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_90 ,
//				IImage.TRANS_270 , 
//				IImage.TRANS_H90 ,
//				IImage.TRANS_V90 
//			},{	
//				//ScreenFlip = H
//				IImage.TRANS_H ,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_V ,
//				IImage.TRANS_H90 ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_90 , 
//				IImage.TRANS_270 
//			},{	
//				//ScreenFlip = V
//				IImage.TRANS_V ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_H ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_H90 ,
//				IImage.TRANS_270,
//				IImage.TRANS_90 
//			},{	
////				ScreenFlip = HV
//				IImage.TRANS_HV,
//				IImage.TRANS_V,
//				IImage.TRANS_H ,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_270 ,
//				IImage.TRANS_90 ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_H90 
//			},{
////				ScreenFlip = 90
//				IImage.TRANS_90 ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_H90,
//				IImage.TRANS_270 ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_H ,
//				IImage.TRANS_V ,		
//			},{
////				ScreenFlip = 270
//				IImage.TRANS_270 ,
//				IImage.TRANS_H90 ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_90,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_V ,
//				IImage.TRANS_H ,
//			},{
////				ScreenFlip = H 90
//				IImage.TRANS_H90 ,
//				IImage.TRANS_270 ,	
//				IImage.TRANS_90 ,
//				IImage.TRANS_V90 ,
//				IImage.TRANS_V ,
//				IImage.TRANS_H ,
//				IImage.TRANS_NONE ,
//				IImage.TRANS_HV ,
//			},{
////				ScreenFlip = V 90
//				IImage.TRANS_V90 ,
//				IImage.TRANS_90 ,
//				IImage.TRANS_270 ,
//				IImage.TRANS_H90 ,				
//				IImage.TRANS_H ,
//				IImage.TRANS_V ,
//				IImage.TRANS_HV ,
//				IImage.TRANS_NONE ,
//			}
//			
//	};
//
//	static protected int getFlipIndex(byte trans){
//		switch(trans){
//		case IImage.TRANS_NONE:
//			return 0;
//		case IImage.TRANS_H :
//			return 1;
//		case IImage.TRANS_V :
//			return 2;
//		case IImage.TRANS_HV :
//			return 3;
//		case IImage.TRANS_90 :
//			return 4;
//		case IImage.TRANS_270 : 
//			return 5;
//		case IImage.TRANS_H90 :
//			return 6;
//		case IImage.TRANS_V90 :
//			return 7;
//		}
//		return -1;
//	}

//	static protected byte transFlipNext(byte currentTrans,byte nextTrans){
//		return SubSprFlipTable[getFlipIndex(currentTrans)][getFlipIndex(nextTrans)];
//	}
//	
//	static protected void TransformPoint(int[] point,int transform){
//		int temp = 0;
//		for(int i=0;i<point.length;i+=2){
//			switch(transform){
//			case IImage.TRANS_NONE:
//				break;
//			case IImage.TRANS_H: 
//				point[i+0] = -point[i+0];
//				//point[i+1] = +point[i+1];
//				break;
//			case IImage.TRANS_V: 
//				//point[i+0] = +point[i+0];
//				point[i+1] = -point[i+1];
//				break;
//			case IImage.TRANS_HV: 
//				point[i+0] = -point[i+0];
//				point[i+1] = -point[i+1];
//				break;
//			case IImage.TRANS_90: 
//				temp = point[i+0];
//				point[i+0] = +point[i+1];
//				point[i+1] = -temp;
//				break;
//			case IImage.TRANS_270: 
//				temp = point[i+0];
//				point[i+0] = -point[i+1];
//				point[i+1] = +temp;
//				break;
//			case IImage.TRANS_H90: 
//				point[i+0] = -point[i+0];
//				//point[i+1] = +point[i+1];
//				temp = point[i+0];
//				point[i+0] = +point[i+1];
//				point[i+1] = -temp;
//				break;
//			case IImage.TRANS_V90: 
//				//point[i+0] = +point[i+0];
//				point[i+1] = -point[i+1];
//				temp = point[i+0];
//				point[i+0] = +point[i+1];
//				point[i+1] = -temp;
//				break;
//			}
//		}
//		
//	}
//	------------------------------------------------------------------------------------------

	public boolean includeBounds(int x, int y)
	{
		return CMath.includeRectPoint(
				getVisibleLeft(), 
				getVisibleTop(), 
				getVisibleRight(),
				getVisibleBotton(), 
				x - X, y - Y);
	}
	
	public int getVisibleTop(){
		return animates.w_top;
	}
	public int getVisibleBotton(){
		return animates.w_bottom;
	}
	public int getVisibleLeft(){
		return animates.w_left;
	}
	public int getVisibleRight(){
		return animates.w_right;
	}
	public CCD getVisibleBounds() {
		CCD bounds = new CCD();
		bounds.Type = CCD.CD_TYPE_RECT;
		bounds.X1 = animates.w_left;
		bounds.Y1 = animates.w_top; 
		bounds.X2 = animates.w_right;
		bounds.Y2 = animates.w_bottom;
		return bounds;
	}

//	------------------------------------------------------------------------------------------

	public CCD getFrameBounds(){
		return getFrameBounds(CurAnimate, CurFrame);
	}

	public CCD getFrameBounds(int anim, int frame) {
		CCD bounds = CCD.createCDRect(CCD.CD_TYPE_RECT, 0,0,0,0);
		bounds.X1 = Short.MAX_VALUE;
		bounds.Y1 = Short.MAX_VALUE;
		bounds.X2 = Short.MIN_VALUE;
		bounds.Y2 = Short.MIN_VALUE;
		return getFrameBounds(anim, frame, bounds);
	}
	
	public CCD getFrameBounds(int anim) {
		CCD bounds = CCD.createCDRect(CCD.CD_TYPE_RECT, 0, 0, 0, 0);
		bounds.X1 = Short.MAX_VALUE;
		bounds.Y1 = Short.MAX_VALUE;
		bounds.X2 = Short.MIN_VALUE;
		bounds.Y2 = Short.MIN_VALUE;
		for (int f = 0; f < getFrameCount(anim); f++) {
			getFrameBounds(anim, f, bounds);
		}
		return bounds;
	}

//	------------------------------------------------------------------------------------------

	
	public CCD getFrameBounds(CCD out_bounds){
		return getFrameBounds(CurAnimate, CurFrame, out_bounds);
	}
	
	public CCD getFrameBounds(int anim, int frame, CCD out_bounds)
	{
		CCD bounds = out_bounds;
		
		if (anim < FrameAnimate.length)
		{
			if (frame < FrameAnimate[anim].length)
			{
				int frameid = FrameAnimate[anim][frame];
				int count = animates.getComboFrameCount(frameid);
				for (int i=0; i<count; i++) {
					bounds.X1 = (short)Math.min(bounds.X1, animates.getFrameX(frameid, i));
					bounds.Y1 = (short)Math.min(bounds.Y1, animates.getFrameY(frameid, i));
					bounds.X2 = (short)Math.max(bounds.X2, animates.getFrameX(frameid, i)+animates.getFrameW(frameid, i)-1);
					bounds.Y2 = (short)Math.max(bounds.Y2, animates.getFrameY(frameid, i)+animates.getFrameH(frameid, i)-1);
				}
			}
		}
		
		return bounds;
	}
	
	public CCD getFrameBounds(int anim, CCD out_bounds)
	{
		CCD bounds = out_bounds;
		for (int f = 0; f < getFrameCount(anim); f++) {
			getFrameBounds(anim, f, bounds);
		}
		return bounds;
	}
	
//	------------------------------------------------------------------------------------------

	public int getCDTop(){
		return collides.w_top;
	}
	public int getCDBotton(){
		return collides.w_bottom;
	}
	public int getCDLeft(){
		return collides.w_left;
	}
	public int getCDRight(){
		return collides.w_right;
	}
	
//	------------------------------------------------------------------------------------------

	public int getVisibleHeight(){
		return animates.w_height;
	}
	public int getVisibleWidth(){
		return animates.w_width;
	}
	public int getCDHeight(){
		return collides.w_height;
	}
	public int getCDWidth(){
		return collides.w_width;
	}
	
//	------------------------------------------------------------------------------------------

	public int getAnimateIndex(String animate_name) {
		if (AnimateNames!=null) {
			for (int i = 0; i<AnimateNames.length; i++) {
				if (animate_name.equals(AnimateNames[i])) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public String getAnimateName(int anim) {
		return AnimateNames[anim];
	}
	
	public CAnimates getAnimates(){
		return animates;
	}
	
	public CCollides getCollides(){
		return collides;
	}
	
//	------------------------------------------------------------------------------------------

	public int getAnimateCount() {
		return FrameAnimate.length;
	}

	public int getFrameCount(int id) {
		return FrameAnimate[id].length;
	}
	
	public int getCurrentFrame() {
		return CurFrame;
	}

	public int getCurrentAnimate() {
		return CurAnimate;
	}

	public void setCurrentAnimate(int id) {
		CurAnimate	= (short) id;
		CurAnimate	= CMath.cycNum(CurAnimate, 0, getAnimateCount());
		CurFrame	= CMath.cycNum(CurFrame, 0, getFrameCount(CurAnimate));
	}
	
	public void setCurrentFrame(int id, int index) {
		CurAnimate = (short) id;
		CurFrame = (short) index;
		CurAnimate	= CMath.cycNum(CurAnimate, 0, getAnimateCount());
		CurFrame	= CMath.cycNum(CurFrame, 0, getFrameCount(CurAnimate));
	}
	
	public boolean isEndFrame() {
		if (CurFrame+1 >= FrameAnimate[CurAnimate].length ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean nextFrame() {
		CurFrame++;
		if (CurFrame >= FrameAnimate[CurAnimate].length ) {
			CurFrame = FrameAnimate[CurAnimate].length - 1;
			return true;
		} else {
			return false;
		}
	}

	public void nextCycFrame() {
		CurFrame++;
		if(FrameAnimate[CurAnimate].length>0){
			CurFrame%=FrameAnimate[CurAnimate].length;
		}
	}

	public void nextCycFrame(int restart) {
		CurFrame++;
		if (CurFrame < FrameAnimate[CurAnimate].length) {
		} else {
			if(FrameAnimate[CurAnimate].length>0){
				CurFrame = (restart % FrameAnimate[CurAnimate].length);
			}
		}
	}
	
	

	public boolean prewFrame() {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame = 0;
			return true;
		} else {
			return false;
		}
	}


	public void prewCycFrame() {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame =FrameAnimate[CurAnimate].length-1;
		}
		
	}


	public void prewCycFrame(int restart) {
		CurFrame--;
		if (CurFrame < 0 ) {
			CurFrame = (restart % FrameAnimate[CurAnimate].length);
		}
	}
	
	
	public void nextAnimate(int d) {
		setCurrentFrame(CMath.cycNum(getCurrentAnimate(), d, getAnimateCount()), 0);
	}
	
	
	/**
	 */
	public void playDemo(){
		int anim = getCurrentAnimate();
		if (nextFrame()) {
			setCurrentFrame((anim+1)%getAnimateCount(), 0);
		}
	}
	
	
	
	//--------------------------------------------------------------------------------------------------------


	public void setFrameAnimateData(int id, int index, int data) {
		FrameAnimate[id][index] = (short)data;
	}
	public void setFrameCDMapData(int id, int index, int data) {
		FrameCDMap[id][index] = (short)data;
	}
	public void setFrameCDAtkData(int id, int index, int data) {
		FrameCDAtk[id][index] = (short)data;
	}
	public void setFrameCDDefData(int id, int index, int data) {
		FrameCDDef[id][index] = (short)data;
	}
	public void setFrameCDExtData(int id, int index, int data) {
		FrameCDExt[id][index] = (short)data;
	}
	
	public int getFrameAnimateData(int id, int index) {
		return FrameAnimate[id][index];
	}
	public int getFrameCDMapData(int id, int index) {
		return FrameCDMap[id][index] ;
	}
	public int getFrameCDAtkData(int id, int index) {
		return FrameCDAtk[id][index] ;
	}
	public int getFrameCDDefData(int id, int index) {
		return FrameCDDef[id][index] ;
	}
	public int getFrameCDExtData(int id, int index) {
		return FrameCDExt[id][index] ;
	}
	
	//	----------------------------------------------------------------------------------------------------

	
	
	public void render(IGraphics g,int x,int y) {
		if (OnScreen){
			if ( (CurAnimate < FrameAnimate.length) && (CurFrame < FrameAnimate[CurAnimate].length) ) {
				animates.render(g,FrameAnimate[CurAnimate][CurFrame],x,y);
				//#ifdef _DEBUG
				if (IsDebug && Active){
					collides.render(g,FrameCDMap[CurAnimate][CurFrame],x,y,0xff00ff00);
					collides.render(g,FrameCDAtk[CurAnimate][CurFrame],x,y,0xffff0000);
					collides.render(g,FrameCDDef[CurAnimate][CurFrame],x,y,0xff0000ff);
					collides.render(g,FrameCDExt[CurAnimate][CurFrame],x,y,0xffffffff);
	
					g.setColor(0xffffffff);
					g.drawLine(x-8, y, x+8, y);
					g.drawLine(x, y-8, x, y+8);
					
					g.setColor(0xffff00ff);
					g.drawLine(x-8, y+Priority, x+8, y+Priority);
					g.drawLine(x, y-8+Priority, x, y+8+Priority);
				}
				//#endif
			}
		}
	}
	
	public void render(IGraphics g,int x,int y, int blend_mode, float blend_alpha) {
		if (OnScreen){
			if ( (CurAnimate < FrameAnimate.length) && (CurFrame < FrameAnimate[CurAnimate].length) ) {
				g.pushBlendMode();
				try {
					g.setBlendMode(blend_mode, blend_alpha);
					animates.render(g,FrameAnimate[CurAnimate][CurFrame],x,y);
					//#ifdef _DEBUG
					if (IsDebug && Active){
						collides.render(g,FrameCDMap[CurAnimate][CurFrame],x,y,0xff00ff00);
						collides.render(g,FrameCDAtk[CurAnimate][CurFrame],x,y,0xffff0000);
						collides.render(g,FrameCDDef[CurAnimate][CurFrame],x,y,0xff0000ff);
						collides.render(g,FrameCDExt[CurAnimate][CurFrame],x,y,0xffffffff);
	
						g.setColor(0xffffffff);
						g.drawLine(x-8, y, x+8, y);
						g.drawLine(x, y-8, x, y+8);
						
						g.setColor(0xffff00ff);
						g.drawLine(x-8, y+Priority, x+8, y+Priority);
						g.drawLine(x, y-8+Priority, x, y+8+Priority);
					}			
					//#endif
				} finally {
					g.popBlendMode();
				}
			}
		}
	}	
	
	public void render(IGraphics g,int x,int y, int anim, int frame) {
		if (OnScreen){
			if ( (anim < FrameAnimate.length) && (frame < FrameAnimate[anim].length) ) {
				animates.render(g,FrameAnimate[anim][frame],x,y);
				//#ifdef _DEBUG
				if (IsDebug && Active){
					collides.render(g,FrameCDMap[anim][frame],x,y,0xff00ff00);
					collides.render(g,FrameCDAtk[anim][frame],x,y,0xffff0000);
					collides.render(g,FrameCDDef[anim][frame],x,y,0xff0000ff);
					collides.render(g,FrameCDExt[anim][frame],x,y,0xffffffff);
					
					g.setColor(0xffffffff);
					g.drawLine(x-8, y, x+8, y);
					g.drawLine(x, y-8, x, y+8);
					
					g.setColor(0xffff00ff);
					g.drawLine(x-8, y+Priority, x+8, y+Priority);
					g.drawLine(x, y-8+Priority, x, y+8+Priority);
				}
				//#endif
			}
		}
	}
	
	public void render(IGraphics g,int x,int y, int anim, int frame, int blend_mode, float blend_alpha) {
		if (OnScreen){
			if ( (anim < FrameAnimate.length) && (frame < FrameAnimate[anim].length) ) {
				g.pushBlendMode();
				try {
					g.setBlendMode(blend_mode, blend_alpha);
					animates.render(g,FrameAnimate[anim][frame],x,y);
					//#ifdef _DEBUG
					if (IsDebug && Active){
						collides.render(g,FrameCDMap[anim][frame],x,y,0xff00ff00);
						collides.render(g,FrameCDAtk[anim][frame],x,y,0xffff0000);
						collides.render(g,FrameCDDef[anim][frame],x,y,0xff0000ff);
						collides.render(g,FrameCDExt[anim][frame],x,y,0xffffffff);
						
						g.setColor(0xffffffff);
						g.drawLine(x-8, y, x+8, y);
						g.drawLine(x, y-8, x, y+8);
						
						g.setColor(0xffff00ff);
						g.drawLine(x-8, y+Priority, x+8, y+Priority);
						g.drawLine(x, y-8+Priority, x, y+8+Priority);
					}			
					//#endif
				} finally {
					g.popBlendMode();
				}
			}
		}
	}	
	


}



