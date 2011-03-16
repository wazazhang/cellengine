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

import com.cell.CMath;
import com.cell.IImages;

/*
 * Created on 2004-11-8
 *
 *  
 * 
 */


/**
 * 组合精灵</br>
 * 组合精灵包含一个动画序列，一个碰撞块组。
 * 通过精灵当前的状态显示和处理对象的AI。
 * @author yifeizhang
 * @since 2006-11-30 
 * @version 1.0
 */
public class CSprite extends CUnit {
	
//	----------------------------------------------------------------------------------------------
//	逻辑相关

	/** 是否和精灵有阻挡 */
	public boolean haveSprBlock 	= false;
	/** 是否和地图有阻挡 */
	public boolean haveMapBlock 	= false;
	/**该精灵当前是否在摄象机内，只读*/
	public boolean OnScreen 		= true;
	/**渲染优先级*/
	public int Priority				= 0;
//	----------------------------------------------------------------------------------------------
//	动画相关
	
	//	动画
	protected CAnimates animates;
	//	碰撞块
	protected CCollides collides;
	
	//	帧相关
	protected int FrameAnimate[][];
	protected int FrameCDMap[][];
	protected int FrameCDAtk[][];
	protected int FrameCDDef[][];
	protected int FrameCDExt[][];
	
	/**碰撞块类型：和地图检测*/
	final static public int CD_TYPE_MAP 	= 0; 
	/**碰撞块类型：和攻击块检测*/
	final static public int CD_TYPE_ATK 	= 1;
	/**碰撞块类型：和被攻击块检测*/
	final static public int CD_TYPE_DEF 	= 2;
	/**碰撞块类型：和扩展块检测*/
	final static public int CD_TYPE_EXT 	= 3;

	protected int CurAnimate = 0;
	protected int CurFrame = 0;
	
	protected byte Transform = IImages.TRANS_NONE;
	
//	----------------------------------------------------------------------------------------------
//	物理相关 no method
	
	/**在世界中的X坐标*/
	public int X = 0;
	/**在世界中的Y坐标*/
	public int Y = 0;
	
	/** X方向速度，256倍定点数 */
	public int SpeedX256 = 0; //8位定点数
	/** Y方向速度，256倍定点数 */
	public int SpeedY256 = 0; //8位定点数
	
	/** X方向加速度，256倍定点数 */
	public int AccX256 = 0; //8位定点数
	/** Y方向加速度，256倍定点数 */
	public int AccY256 = 0; //8位定点数
	
	/** X方向 */
	public int DirectX = 0; //方向
	/** Y方向 */
	public int DirectY = 0; //方向

	/** 水平坐标的256倍幻影 */
	public int HPos256 = 0;
	/** 垂直坐标的256倍幻影 */
	public int VPos256 = 0;
	
//	-------------------------------------------------------------------------------------------------


	/**
	 * 构造函数
	 * @param canimates 该精灵用到的图片组
	 * @param ccollides 该精灵用到的碰撞块组
	 * @param frameAnimate 动画序列 </br>
	 * 			frameAnimate[animate id][frame id] = canimate frame index.
	 * @param frameCDMap 地图判定序列（阻挡判定序列）</br>
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDAtk 攻击判定序列 </br>
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDDef 被攻击判定序列 </br>
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 * @param frameCDExt 扩展序列 </br>
	 * 			frameAnimate[animate id][frame id] = ccollide frame index.
	 */
	public CSprite(
			CAnimates canimates, 
			CCollides ccollides,
			int[][] frameAnimate,
			int[][] frameCDMap,
			int[][] frameCDAtk,
			int[][] frameCDDef,
			int[][] frameCDExt
			) {

		animates = canimates;
		collides = ccollides;

		FrameAnimate = frameAnimate;
		FrameCDMap = frameCDMap;
		FrameCDAtk = frameCDAtk;
		FrameCDDef = frameCDDef;
		FrameCDExt = frameCDExt;

	}

	/**
	 * 类似 拷贝构造函数,浅表
	 * @param spr 
	 */
	public CSprite(CSprite spr){
		animates = spr.animates;
		collides = spr.collides;

		FrameAnimate = spr.FrameAnimate;
		FrameCDMap = spr.FrameCDMap;
		FrameCDAtk = spr.FrameCDAtk;
		FrameCDDef = spr.FrameCDDef;
		FrameCDExt = spr.FrameCDExt;
		
		Transform = spr.Transform;
	}

	/**
	 * 复制本身,浅表
	 * @return 
	 */
	public CSprite clone(){
		 CSprite spr = new CSprite(this);
		 spr.world = this.world;
		 return spr;
	}
//	------------------------------------------------------------------------------------------
	

	/**
	 * 的到当前帧的碰撞块
	 * @param type 碰撞块类型
	 * @param sub 碰撞组里的子判定块
	 * @return 
	 */
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
	
//	/**
//	 * 的到当前帧的图片
//	 * @param sub组合帧里的子图
//	 * @return 
//	 */
//	public Image getCurrentImage(int sub){
//		return animates.getFrameImage(FrameAnimate[CurAnimate][CurFrame], sub);
//	}
	
	/**
	 * 判断一个精灵的当前帧和一个碰撞块是否相交。
	 * @param spr1 精灵
	 * @param type1 精灵的碰撞块类型
	 * @param cd 碰撞块
	 * @param x 碰撞块x位置
	 * @param y 碰撞块y位置
	 * @return 
	 */
	static public boolean touch_Spr_CD(
			CSprite spr1,int type1,
			CCD cd,int x,int y){
		
		int index1 = 0;
		
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[spr1.CurAnimate][spr1.CurFrame];break;
		}
		
		if(CCollides.touchCD(
				spr1.collides, index1, spr1.X, spr1.Y, 
				cd, x, y
				)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断一个精灵的当前帧的子碰撞块和一个碰撞块是否相交。
	 * @param spr1 精灵
	 * @param type1 精灵的碰撞块类型
	 * @param sub1 精灵的子碰撞块
	 * @param cd 碰撞块
	 * @param x 碰撞块x位置
	 * @param y 碰撞块y位置
	 * @return 
	 */
	static public boolean touch_SprSub_CD(
			CSprite spr1,int type1,int sub1,
			CCD cd,int x,int y){
		
		int index1 = 0;
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[spr1.CurAnimate][spr1.CurFrame];break;
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
	
	/**
	 * 判断2个精灵的当前帧是否相交
	 * @param spr1 精灵1
	 * @param type1 精灵1的碰撞类型
	 * @param spr2 精灵2
	 * @param type2 精灵2的碰撞类型
	 * @return 
	 */
	static public boolean touch_Spr_Spr(
			CSprite spr1,int type1,
			CSprite spr2,int type2){
		
		int index1 = 0;
		int index2 = 0;
		
		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[spr1.CurAnimate][spr1.CurFrame];break;
		}
		switch(type2){
		case CD_TYPE_MAP:
			index2=spr2.FrameCDMap[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_ATK:
			index2=spr2.FrameCDAtk[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_DEF:
			index2=spr2.FrameCDDef[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_EXT:
			index2=spr2.FrameCDExt[spr2.CurAnimate][spr2.CurFrame];break;
		}
		if(CCollides.touch(
				spr1.collides, index1, spr1.X, spr1.Y, 
				spr2.collides, index2, spr2.X, spr2.Y
				)){
			return true;
		}
		return false;
	}
	/**
	 * 判断2个精灵的当前帧的子碰撞块是否相交
	 * @param spr1 精灵1 
	 * @param type1 精灵1的碰撞类型
	 * @param sub1 精灵1的子碰撞块
	 * @param spr2 精灵2
	 * @param type2 精灵2的碰撞类型
	 * @param sub2 精灵2的子碰撞块
	 * @return 
	 */
	static public boolean touch_SprSub_SprSub(
			CSprite spr1,int type1,int sub1,
			CSprite spr2,int type2,int sub2){
		
		int index1 = 0;
		int index2 = 0;

		switch(type1){
		case CD_TYPE_MAP:
			index1=spr1.FrameCDMap[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_ATK:
			index1=spr1.FrameCDAtk[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_DEF:
			index1=spr1.FrameCDDef[spr1.CurAnimate][spr1.CurFrame];break;
		case CD_TYPE_EXT:
			index1=spr1.FrameCDExt[spr1.CurAnimate][spr1.CurFrame];break;
		}
		switch(type2){
		case CD_TYPE_MAP:
			index2=spr2.FrameCDMap[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_ATK:
			index2=spr2.FrameCDAtk[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_DEF:
			index2=spr2.FrameCDDef[spr2.CurAnimate][spr2.CurFrame];break;
		case CD_TYPE_EXT:
			index2=spr2.FrameCDExt[spr2.CurAnimate][spr2.CurFrame];break;
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

	/**
	 * 检测精灵的当前帧和地图是否相交
	 * @param spr 精灵
	 * @param type 精灵的碰撞类型
	 * @param map 地图
	 * @return 
	 */
	static public boolean touch_Spr_Map(CSprite spr,int type,CMap map){
		
			switch(type){
			case CD_TYPE_MAP:
				for(int sub=0;sub<spr.collides.Frames[spr.FrameCDMap[spr.CurAnimate][spr.CurFrame]].length;sub++){
					if(touch_SprSub_Map(spr,type,sub,map))return true;
				}
			case CD_TYPE_ATK:
				for(int sub=0;sub<spr.collides.Frames[spr.FrameCDAtk[spr.CurAnimate][spr.CurFrame]].length;sub++){
					if(touch_SprSub_Map(spr,type,sub,map))return true;
				}
			case CD_TYPE_DEF:
				for(int sub=0;sub<spr.collides.Frames[spr.FrameCDDef[spr.CurAnimate][spr.CurFrame]].length;sub++){
					if(touch_SprSub_Map(spr,type,sub,map))return true;
				}
			case CD_TYPE_EXT:
				for(int sub=0;sub<spr.collides.Frames[spr.FrameCDExt[spr.CurAnimate][spr.CurFrame]].length;sub++){
					if(touch_SprSub_Map(spr,type,sub,map))return true;
				}
			}
		
		return false;
	}
	/**
	 * 检测精灵的当前帧的子判定块和地图是否相交
	 * @param spr 精灵
	 * @param type 精灵碰撞类型
	 * @param sub 精灵子判定块
	 * @param map 地图
	 * @return 
	 */
	static public boolean touch_SprSub_Map(CSprite spr,int type,int sub,CMap map){
		
			int mbx = (spr.X+spr.getCurrentCD(type,sub).X1)/map.CellW-((spr.X+spr.getCurrentCD(type,sub).X1)<0?1:0);//
			int mby = (spr.Y+spr.getCurrentCD(type,sub).Y1)/map.CellH-((spr.Y+spr.getCurrentCD(type,sub).Y1)<0?1:0);//
			int mbw = (spr.X+spr.getCurrentCD(type,sub).X2)/map.CellW-((spr.X+spr.getCurrentCD(type,sub).X2)<0?1:0);//
			int mbh = (spr.Y+spr.getCurrentCD(type,sub).Y2)/map.CellH-((spr.Y+spr.getCurrentCD(type,sub).Y2)<0?1:0);//
			if(map.IsCyc==false){
				for (int y=mby<0?0:mby; y<=mbh && y<map.getHCount(); y++) {
					for (int x=mbx<0?0:mbx; x<=mbw && x<map.getWCount(); x++) {
						if (map.MatrixFlag[y][x] > 0) //
						{	
							if(CSprite.touch_SprSub_CD(
									spr, type, sub,
									map.getCD(x, y), 
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
						if (map.MatrixFlag[CMath.cycNum(y,0,map.getHCount())][CMath.cycNum(x, 0, map.getWCount())] > 0) //
						{
							if(CSprite.touch_SprSub_CD(
									spr, type, sub,
									map.getCD(CMath.cycNum(x, 0, map.getWCount()), CMath.cycNum(y,0,map.getHCount())), 
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

	private static int adjustSprID[] = null;
	/**
	 * 阻挡性移动，如果被阻挡，精灵停在阻挡物体边缘。
	 * @param x
	 * @param y
	 * @return 是否被阻挡
	 */
	public boolean tryMove(int x,int y){
		
		if(x==0 && y==0)return false;
		
		int dstX = X + x;
		int dstY = Y + y;
		int dx = (x!=0?(x<0?-1:1):0);
		int dy = (y!=0?(y<0?-1:1):0);

		X+=x;
		Y+=y;
		
		boolean adjustSpr = false;
		boolean adjustMap = false;
		
		if(this.haveSprBlock){
			
			if(adjustSprID==null || adjustSprID.length < world.Sprs.size()){
				adjustSprID = new int[world.Sprs.size()];
			}
			int p = 0;
			
			for(int i=world.Sprs.size()-1;i>=0;i--){
				adjustSprID[world.Sprs.size()-1 - i] = -1 ;
				if(!this.equals(((CSprite)world.Sprs.elementAt(i))) && 
						((CSprite)world.Sprs.elementAt(i)).Active && 
						((CSprite)world.Sprs.elementAt(i)).haveSprBlock){
					if(touch_Spr_Spr(
							this, 
							CD_TYPE_MAP, //0,
							((CSprite)world.Sprs.elementAt(i)), 
							CD_TYPE_MAP//, 0
							)){
						adjustSprID[p] = i ;
						p++;
						adjustSpr = true;
					}
				}
			}
		}
		
		if(this.haveMapBlock){
			adjustMap = touch_Spr_Map(this, CD_TYPE_MAP,this.world.Map);
		}
		
		if(adjustMap||adjustSpr){
			X-=x;
			Y-=y;
			while(X!=dstX){
				X+=dx;
				if(adjustMap && touch_Spr_Map(this,CD_TYPE_MAP,this.world.Map)){
					X-=dx;
					break;
				}
				if(adjustSpr){
					boolean over = false;
					for(int i=0;i<adjustSprID.length;i++){
						if(adjustSprID[i]<0)break;
						if(touch_Spr_Spr(
								this, 
								CD_TYPE_MAP, //0,
								((CSprite)world.Sprs.elementAt(adjustSprID[i])), 
								CD_TYPE_MAP//, 0
								)){
							over = true;
							break;
						}
					}
					if(over){
						X-=dx;
						break;
					}
				}
			}
			while(Y!=dstY){
				Y+=dy;
				if(adjustMap && touch_Spr_Map(this,CD_TYPE_MAP,this.world.Map)){
					Y-=dy;
					break;
				}
				if(adjustSpr){
					boolean over = false;
					for(int i=0;i<adjustSprID.length;i++){
						if(adjustSprID[i]<0)break;
						if(touch_Spr_Spr(
								this, 
								CD_TYPE_MAP, //0,
								((CSprite)world.Sprs.elementAt(adjustSprID[i])), 
								CD_TYPE_MAP//, 0
								)){
							over = true;
							break;
						}
					}
					if(over){
						Y-=dy;
						break;
					}
				}
			}
		}
			
		// 调整精灵优先级别
		if(world.isRPGView && dy!=0){
			int s = world.Sprs.indexOf(this);
			int d = s + dy;
			while( 	s<world.Sprs.size() && s>=0 &&
					d<world.Sprs.size() && d>=0 ){
				
				CSprite that = (CSprite)world.Sprs.elementAt(d);
//				println(that.Y+" : "+this.Y);
				
				if(dy<0 && (that.Y + that.Priority) <= (this.Y + this.Priority)){
//					println("dy<0 "+that.Y+"<="+this.Y+" break");
					break;
				} 
				if(dy>0 && (that.Y + that.Priority) >= (this.Y + this.Priority)){
//					println("dy>0 "+that.Y+">="+this.Y+" break");
					break;
				}
				
				world.Sprs.setElementAt(that, s);
				world.Sprs.setElementAt(this, d);
				
//				print( s + ">" + d + " : ");
				
				s = d ;
				d = s + dy;
			}
//			println("");
		}
		
		return adjustMap||adjustSpr;

	}
	
	

	
//	------------------------------------------------------------------------------------------

	public byte getCurTransform(){
		return Transform;
	}
	
	/**
	 * 从当前状态翻转到下一状态
	 * @param next 8种反转状态的一种
	 */
	public void transform(byte next){
		int[] point1 = new int[4];
		Transform = transFlipNext(Transform, next);
		
//		 transform image
		animates.w_left 	= 0;
		animates.w_top 		= 0;
		animates.w_right 	= 0;
		animates.w_bottom 	= 0;
		for (int i = animates.SFlip.length-1; i >= 0 ; i-- ) {
			animates.SFlip[i] = transFlipNext(animates.SFlip[i], next);
			point1[0] = animates.SX[i];
			point1[1] = animates.SY[i];
			point1[2] = animates.SX[i]+animates.SW[i];
			point1[3] = animates.SY[i]+animates.SH[i];
			CSprite.TransformPoint(point1, next);
			animates.SX[i] = (short)Math.min(point1[0],point1[2]);
			animates.SY[i] = (short)Math.min(point1[1],point1[3]);
			animates.SW[i] = (short)Math.abs(point1[0]-point1[2]);
			animates.SH[i] = (short)Math.abs(point1[1]-point1[3]);
			animates.fixArea(
					animates.SX[i], 
					animates.SY[i], 
					animates.SX[i]+animates.SW[i], 
					animates.SY[i]+animates.SH[i]);
		}
		
//		transform collide
		collides.w_left 	= 0;
		collides.w_top 		= 0;
		collides.w_right 	= 0;
		collides.w_bottom 	= 0;
		for (int i = collides.cds.length-1; i >= 0 ; i-- ) {
			point1[0] = collides.cds[i].X1;
			point1[1] = collides.cds[i].Y1;
			point1[2] = collides.cds[i].X2;
			point1[3] = collides.cds[i].Y2;
			CSprite.TransformPoint(point1, next);
			collides.cds[i].X1 = (short)Math.min(point1[0],point1[2]);
			collides.cds[i].Y1 = (short)Math.min(point1[1],point1[3]);
			collides.cds[i].X2 = (short)Math.max(point1[0],point1[2]);
			collides.cds[i].Y2 = (short)Math.max(point1[1],point1[3]);
			collides.fixArea(
					collides.cds[i].X1, 
					collides.cds[i].Y1, 
					collides.cds[i].X2, 
					collides.cds[i].Y2);
		}
	}
	
	
	/**
	 * 子精灵翻转表。从一种状态到下一状态的转换表。
	 */
	static protected byte SubSprFlipTable[][] = {
			{	//ScreenFlip = NONE
				IImages.TRANS_NONE ,
				IImages.TRANS_H ,
				IImages.TRANS_V ,
				IImages.TRANS_HV ,
				IImages.TRANS_90 ,
				IImages.TRANS_270 , 
				IImages.TRANS_H90 ,
				IImages.TRANS_V90 
			},{	
				//ScreenFlip = H
				IImages.TRANS_H ,
				IImages.TRANS_NONE ,
				IImages.TRANS_HV ,
				IImages.TRANS_V ,
				IImages.TRANS_H90 ,
				IImages.TRANS_V90 ,
				IImages.TRANS_90 , 
				IImages.TRANS_270 
			},{	
				//ScreenFlip = V
				IImages.TRANS_V ,
				IImages.TRANS_HV ,
				IImages.TRANS_NONE ,
				IImages.TRANS_H ,
				IImages.TRANS_V90 ,
				IImages.TRANS_H90 ,
				IImages.TRANS_270,
				IImages.TRANS_90 
			},{	
//				ScreenFlip = HV
				IImages.TRANS_HV,
				IImages.TRANS_V,
				IImages.TRANS_H ,
				IImages.TRANS_NONE ,
				IImages.TRANS_270 ,
				IImages.TRANS_90 ,
				IImages.TRANS_V90 ,
				IImages.TRANS_H90 
			},{
//				ScreenFlip = 90
				IImages.TRANS_90 ,
				IImages.TRANS_V90 ,
				IImages.TRANS_H90,
				IImages.TRANS_270 ,
				IImages.TRANS_HV ,
				IImages.TRANS_NONE ,
				IImages.TRANS_H ,
				IImages.TRANS_V ,		
			},{
//				ScreenFlip = 270
				IImages.TRANS_270 ,
				IImages.TRANS_H90 ,
				IImages.TRANS_V90 ,
				IImages.TRANS_90,
				IImages.TRANS_NONE ,
				IImages.TRANS_HV ,
				IImages.TRANS_V ,
				IImages.TRANS_H ,
			},{
//				ScreenFlip = H 90
				IImages.TRANS_H90 ,
				IImages.TRANS_270 ,	
				IImages.TRANS_90 ,
				IImages.TRANS_V90 ,
				IImages.TRANS_V ,
				IImages.TRANS_H ,
				IImages.TRANS_NONE ,
				IImages.TRANS_HV ,
			},{
//				ScreenFlip = V 90
				IImages.TRANS_V90 ,
				IImages.TRANS_90 ,
				IImages.TRANS_270 ,
				IImages.TRANS_H90 ,				
				IImages.TRANS_H ,
				IImages.TRANS_V ,
				IImages.TRANS_HV ,
				IImages.TRANS_NONE ,
			}
			
	};

	/**
	 * 得到翻转号
	 * @param trans
	 * @return
	 */
	static protected int getFlipIndex(byte trans){
		switch(trans){
		case IImages.TRANS_NONE:
			return 0;
		case IImages.TRANS_H :
			return 1;
		case IImages.TRANS_V :
			return 2;
		case IImages.TRANS_HV :
			return 3;
		case IImages.TRANS_90 :
			return 4;
		case IImages.TRANS_270 : 
			return 5;
		case IImages.TRANS_H90 :
			return 6;
		case IImages.TRANS_V90 :
			return 7;
		}
		return -1;
	}

	/**
	 * 从当前状态翻转到下一状态
	 * @param currentTrans	上个状态
	 * @param nextTrans	要翻转的状态
	 * @return 下一个状态
	 */
	static protected byte transFlipNext(byte currentTrans,byte nextTrans){
		return SubSprFlipTable[getFlipIndex(currentTrans)][getFlipIndex(nextTrans)];
	}
	
	/**
	 * 点转换
	 * @param point
	 * @param transform 
	 */
	static protected void TransformPoint(int[] point,int transform){
		int temp = 0;
		for(int i=0;i<point.length;i+=2){
			switch(transform){
			case IImages.TRANS_NONE:
				break;
			case IImages.TRANS_H: 
				point[i+0] = -point[i+0];
				//point[i+1] = +point[i+1];
				break;
			case IImages.TRANS_V: 
				//point[i+0] = +point[i+0];
				point[i+1] = -point[i+1];
				break;
			case IImages.TRANS_HV: 
				point[i+0] = -point[i+0];
				point[i+1] = -point[i+1];
				break;
			case IImages.TRANS_90: 
				temp = point[i+0];
				point[i+0] = +point[i+1];
				point[i+1] = -temp;
				break;
			case IImages.TRANS_270: 
				temp = point[i+0];
				point[i+0] = -point[i+1];
				point[i+1] = +temp;
				break;
			case IImages.TRANS_H90: 
				point[i+0] = -point[i+0];
				//point[i+1] = +point[i+1];
				temp = point[i+0];
				point[i+0] = +point[i+1];
				point[i+1] = -temp;
				break;
			case IImages.TRANS_V90: 
				//point[i+0] = +point[i+0];
				point[i+1] = -point[i+1];
				temp = point[i+0];
				point[i+0] = +point[i+1];
				point[i+1] = -temp;
				break;
			}
		}
		
	}
//	------------------------------------------------------------------------------------------

	public int getVisibleHeight(){
		return animates.w_bottom - animates.w_top;
	}
	public int getVisibleWidth(){
		return animates.w_right - animates.w_left;
	}
	public int getCDHeight(){
		return collides.w_bottom - collides.w_top;
	}
	public int getCDWidth(){
		return collides.w_right - collides.w_left;
	}
	
	public CAnimates getAnimates(){
		return animates;
	}
	
	public CCollides getCollides(){
		return collides;
	}
	
	/**
	 * 得到动画（动作）数量
	 * @return 
	 */
	public int getAnimateCount() {
		return FrameAnimate.length;
	}

	/**
	 * 根据动画ID ，得到帧数量。
	 * @param id 动画ID。
	 * @return 
	 */
	public int getFrameCount(int id) {
		return FrameAnimate[id].length;
	}
	
	/**
	 * 得到当前帧号
	 * @return 
	 */
	public int getCurrentFrame() {
		return CurFrame;
	}

	/**
	 * 得到当前动画号
	 * @return 
	 */
	public int getCurrentAnimate() {
		return CurAnimate;
	}

	/**
	 * 设置当前帧
	 * @param id 动画ID
	 * @param index 帧号
	 */
	public void setCurrentFrame(int id, int index) {
		CurAnimate = (short) id;
		CurFrame = (short) index;
	}

	/**
	 * 播放到下一帧
	 * @return 是否超出最后一帧
	 */
	public boolean nextFrame() {
		CurFrame++;
		if (CurFrame >= FrameAnimate[CurAnimate].length ) {
			CurFrame = FrameAnimate[CurAnimate].length - 1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 循环播放到下一帧
	 */
	public void nextCycFrame() {
		CurFrame++;
		CurFrame%=FrameAnimate[CurAnimate].length;
	}

	/**
	 * 循环播放到下一帧，如果到最后一帧，则跳到指定位置。
	 * @param restart 跳到的指定位置
	 */
	public void nextCycFrame(int restart) {
		CurFrame++;
		if (CurFrame < FrameAnimate[CurAnimate].length) {
		} else {
			CurFrame = (byte) (restart % FrameAnimate[CurAnimate].length);
		}
	}
	
	

	
	//--------------------------------------------------------------------------------------------------------

	/**
	 * 渲染精灵
	 * @param g
	 * @param x
	 * @param y 
	 */
	public void render(Graphics g,int x,int y) {
		if (OnScreen){
				animates.render(g,FrameAnimate[CurAnimate][CurFrame],x,y);
//#ifdef _DEBUG
			if (Active && IsDebug){
				collides.render(g,FrameCDMap[CurAnimate][CurFrame],x,y,0xff00ff00);
				collides.render(g,FrameCDAtk[CurAnimate][CurFrame],x,y,0xffff0000);
				collides.render(g,FrameCDDef[CurAnimate][CurFrame],x,y,0xff0000ff);
				collides.render(g,FrameCDExt[CurAnimate][CurFrame],x,y,0xffffffff);
			}
//#endif
		}
	}

	//	----------------------------------------------------------------------------------------------------

}



