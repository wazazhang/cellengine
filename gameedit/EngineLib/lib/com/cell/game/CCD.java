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
/*
 * 张翼飞
 * Created on 2005-7-25
 *
 */
package com.cell.game;

import javax.microedition.lcdui.Graphics;

import com.cell.CMath;
import com.cell.CObject;


/**
 * 碰撞块</br> 所有的类型的碰撞块必须包含在快速排斥矩形内，比如一条线段的碰撞块， 
 * 它的2点不能超过快速排斥矩形范围之内。</br>
 * 这样优化过的碰撞块只有才快速排斥矩形碰撞范围内才进一步检测复杂碰撞块。</br>
 * 
 * 注意：碰撞块属性
 */
public class CCD extends CObject{

	/** 矩形碰撞块 */
	final static public short CD_TYPE_RECT = 1;
	/** 线段碰撞块 */
	final static public short CD_TYPE_LINE = 2;
	/** 点碰撞块 */
	final static public short CD_TYPE_POINT = 3;
//	/** 圆碰撞块（暂不支持） */
//	final static public short CD_TYPE_ROUND = 4;

	/** 碰撞类型 */
	public short Type;

	//public boolean Active = true;

	/**
	 * 碰撞属性</br> 
	 * 在进行碰撞检测的时候，第一个碰撞块属性和第二个碰撞块属性进行按位与操作，
	 * 如果结果非零，则进行相应的碰撞检测运算，否则直接返回false。
	 */
	public int Mask;

	/**Left */
	public short X1;
	/**Top */
	public short Y1;
	/**Right*/
	public short X2;
	/**Bottom*/
	public short Y2;

	/**
	 * 构造函数 
	 */
	public CCD() {
	}

	/**
	 * 构造函数 form other
	 * @param theobj 
	 */
	public CCD(CCD theobj) {
		this.Type = theobj.Type;
		this.Mask = theobj.Mask;

		this.X1 = theobj.X1;
		this.Y1 = theobj.Y1;
		this.X2 = theobj.X2;
		this.Y2 = theobj.Y2;
	}

	/**
	 * 
	 * @param g
	 *            绘图设备
	 * @param px
	 *            水平坐标
	 * @param py
	 *            垂直坐标
	 * @param color
	 *            颜色
	 */
	public void render(Graphics g, int px, int py, int color) {
		//TODO
		if ( Mask == 0) return;
		g.setColor(color);
		switch (Type) {
		case CD_TYPE_LINE:
			g.drawLine(px + X1, py + Y1, px + X2, py + Y2 );
			break;
		case CD_TYPE_RECT:
			g.drawRect(px + X1, py + Y1, X2 - X1 , Y2 - Y1 );
			break;
		}
	}

	/**
	 * 创建一个矩形碰撞块
	 * 
	 * @param mask
	 *            状态属性
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return 碰撞块的实例
	 */
	static public CCD createCDRect(int mask, int x, int y, int w, int h) {
		CCD ret = new CCD();
		ret.Type = CD_TYPE_RECT;
		ret.Mask = mask;

		ret.X1 = (short) x;
		ret.Y1 = (short) y;
		ret.X2 = (short)(x + w-1);
		ret.Y2 = (short)(y + h-1);
		return ret;
	}

	/**
	 * 创建一个线段碰撞块
	 * 
	 * @param mask
	 *            状态属性
	 * @param x
	 *            快速排斥矩形
	 * @param y
	 * @param w
	 * @param h
	 * @param px
	 *            线段的第一点
	 * @param py
	 * @param qx
	 *            线段的第二点
	 * @param qy
	 * @return 碰撞块的实例
	 */
	static public CCD createCDLine(
			int mask, 
			int px, int py, int qx, int qy) {
		CCD ret = new CCD();
		ret.Type = CD_TYPE_LINE;
		ret.Mask =  mask;

		ret.X1 = (short)px;
		ret.Y1 = (short)py;
		
		ret.X2 = (short)qx;
		ret.Y2 = (short)qy;
		
		return ret;
	}

	/**
	 * 测试2碰撞块是否碰撞
	 * 
	 * @param b1
	 *            第一块
	 * @param x1
	 *            第一块水平坐标
	 * @param y1
	 *            第一块垂直坐标
	 * @param b2
	 *            第二块
	 * @param x2
	 *            第二块水平坐标
	 * @param y2
	 *            第二块垂直坐标
	 * @param processStatus
	 *            是否处理MASK
	 * @return false:true
	 */
	static public boolean touch(
			CCD b1, int x1, int y1, 
			CCD b2, int x2, int y2, 
			boolean processStatus) {
		//TODO
		if ((processStatus && (b1.Mask & b2.Mask) != 0) || !processStatus) {
			if (b1.Type == CD_TYPE_RECT && b2.Type == CD_TYPE_RECT) {
				return touchRect(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_LINE && b2.Type == CD_TYPE_LINE) {
				return touchLine(b1, x1, y1, b2, x2, y2);
			} else if (b1.Type == CD_TYPE_RECT) {
				return touchRectLine(b1, x1, y1, b2, x2, y2);
			} else if (b2.Type == CD_TYPE_RECT) {
				return touchRectLine(b2, x2, y2, b1, x1, y1);
			}
		}
		return false;
	}

	/**
	 * 检测2个快速排斥块是否相交
	 * @param rect1
	 * @param x1
	 * @param y1
	 * @param rect2
	 * @param x2
	 * @param y2
	 * @return false:true
	 */
	static public boolean touchRect(
			CCD src, int sx, int sy, 
			CCD dst, int dx, int dy) {
		return cdRect(
				src.X1 + sx, src.Y1 + sy, 
				src.X2 + sx, src.Y2 + sy, 
				dst.X1 + dx, dst.Y1 + dy, 
				dst.X2 + dx, dst.Y2 + dy);
	}

	
	/**
	 * 检测2线段碰撞块是否相交
	 * @param line1
	 * @param x1
	 * @param y1
	 * @param line2
	 * @param x2
	 * @param y2
	 * @return false:true
	 */
	static public boolean touchLine(
			CCD src, int sx, int sy, 
			CCD dst, int dx, int dy) {
		if (touchRect(src, sx, sy, dst, dx, dy)) {
			return cdLine(
					src.X1 + sx, src.Y1 + sy, 
					src.X2 + sx, src.Y2 + sy, 
					dst.X1 + dx, dst.Y1 + dy, 
					dst.X2 + dx, dst.Y2 + dy);
		}
		return false;
	}

	/**
	 * 检测矩形碰撞块和线碰撞块是否相交
	 * @param rect
	 * @param rx
	 * @param ry
	 * @param line
	 * @param lx
	 * @param ly
	 * @return false:true
	 */
	static public boolean touchRectLine(
			CCD rect, int rx, int ry, 
			CCD line, int lx, int ly) {
		if (cdRect(
				rx + rect.X1, ry + rect.Y1,//
				rx + rect.X2, ry + rect.Y2,//
				lx + Math.min(line.X1,line.X2), ly + Math.min(line.Y1,line.Y2),//
				lx + Math.max(line.X1,line.X2), ly + Math.max(line.Y1,line.Y2))//
		) {
			if (cdLine(//TOP
					(rx + rect.X1) , (ry + rect.Y1) ,//
					(rx + rect.X2) , (ry + rect.Y1) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (cdLine(//LEFT
					(rx + rect.X1) , (ry + rect.Y1) ,//
					(rx + rect.X1) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (cdLine(//RIGHT
					(rx + rect.X2) , (ry + rect.Y1) ,//
					(rx + rect.X2) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (cdLine(//BUTTON
					(rx + rect.X1) , (ry + rect.Y2) ,//
					(rx + rect.X2) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
		}
		return false;
	}

	//----------------------------------------------------------------------------------------------------------------------

	/* croe */
	static int v1[] = new int[2];
	static int v2[] = new int[2];
	static int v3[] = new int[2];
	static int v4[] = new int[2];
	
	static int v5[] = new int[2];
	static int v6[] = new int[2];
	static int v7[] = new int[2];
	static int v8[] = new int[2];
	
	
	/**
	 * 判断2条直线段是否相撞 屏幕坐标系: 判断P1P2跨立Q1Q2的依据是： 
	 * ((Q2-Q1)×(P1-Q1))*((P2-Q1)×(Q2-Q1)) >= 0 
	 * 同理判断Q1Q2跨立P1P2的依据是： 
	 * ((P2-P1)×(Q1-P1))*((Q2-P1)×(P2-P1)) >= 0
	 * 
	 * @param p1x line 1
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @param q1x line 2
	 * @param q1y
	 * @param q2x
	 * @param q2y
	 * @return false:true
	 */
	final static public boolean cdLine(
			int p1x, int p1y, int p2x, int p2y,
			int q1x, int q1y, int q2x, int q2y) {
		
		CMath.vectorSub(v1, q2x, q2y, q1x, q1y);//1
		CMath.vectorSub(v2, p1x, p1y, q1x, q1y);//2
		CMath.vectorSub(v3, p2x, p2y, q1x, q1y);//3
//		CMath.vectorSub(v4, q2x, q2y, q1x, q1y);//4=1
		
		CMath.vectorSub(v5, p2x, p2y, p1x, p1y);//5
		CMath.vectorSub(v6, q1x, q1y, p1x, p1y);//6
		CMath.vectorSub(v7, q2x, q2y, p1x, p1y);//7
//		CMath.vectorSub(v8, p2x, p2y, p1x, p1y);//8=5
		
		if (CMath.vectorCrossProduct(v1, v2) * CMath.vectorCrossProduct(v3, v1) >= 0 && 
			CMath.vectorCrossProduct(v5, v6) * CMath.vectorCrossProduct(v7, v5) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断2个矩形是否相撞</br>
	 * 第一个矩形</br>
	 * @param sx1 
	 * @param sy1
	 * @param sx2
	 * @param sy2
	 * 第二个矩形</br>
	 * @param dx1 
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @return false:true 是否相撞
	 */
	final static public boolean cdRect(
			int sx1, int sy1, int sx2, int sy2, 
			int dx1, int dy1, int dx2, int dy2) {
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

	/**
	 * 判断2个矩形是否相撞</br>
	 * 第一个矩形</br>
	 * @param sx1 
	 * @param sy1
	 * @param sx2
	 * @param sy2
	 * 第二个矩形</br>
	 * @param dx1 
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @return false:true 是否相撞
	 */
	final static public boolean cdRectPoint(
			int sx1, int sy1, int sx2, int sy2, 
			int dx, int dy) {
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}
}