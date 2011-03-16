/*
 * 张翼飞
 * Created on 2005-7-25
 *
 */
package Cell.Game;

import javax.microedition.lcdui.Graphics;

import Cell.CObject;
import Cell.CMath;

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

	final static private int PRECISION = 0;

	/** 碰撞类型 */
	public short Type;

	public boolean Active = true;

	/**
	 * 碰撞属性</br> 
	 * 在进行碰撞检测的时候，第一个碰撞块属性和第二个碰撞块属性进行按位与操作，
	 * 如果结果非零，则进行相应的碰撞检测运算，否则直接返回false。
	 */
	public int Mask;

	/** 矩形快速排斥，X坐标 */
	public short X;
	/** 矩形快速排斥，Y坐标 */
	public short Y;
	/** 矩形快速排斥，宽 */
	public short W;
	/** 矩形快速排斥，高 */
	public short H;

	public short PX;
	public short PY;
	public short QX;
	public short QY;

	public CCD() {
	}

	public CCD(CCD theobj) {
		this.Type = theobj.Type;
		this.Mask = theobj.Mask;

		this.X = theobj.X;
		this.Y = theobj.Y;
		this.W = theobj.W;
		this.H = theobj.H;

		this.PX = theobj.PX;
		this.PY = theobj.PY;
		this.QX = theobj.QX;
		this.QY = theobj.QY;
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
		if (!Active || Mask == 0)
			return;
		g.setColor(color);
		switch (Type) {
		case CD_TYPE_LINE:
			g.drawLine(px + X + PX, py + Y + PY, px + X + QX, py + Y + QY);
			break;
		case CD_TYPE_RECT:
			g.drawRect(px + X, py + Y, W - 1, H - 1);
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
		if (w <= 0 || h <= 0)
			println("-_-! Width and Height can not less than '0' !!!");
		ret.X = (short) x;
		ret.Y = (short) y;
		ret.W = (short) w;
		ret.H = (short) h;
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
	static public CCD createCDLine(int mask, int x, int y, int w, int h,
			int px, int py, int qx, int qy) {
		CCD ret = new CCD();
		ret.Type = CD_TYPE_LINE;
		ret.Mask =  mask;
		if (w <= 0 || h <= 0)
			println("-_-! Width and Height can not less than '0' !!!");
		ret.X = (short) x;
		ret.Y = (short) y;
		ret.W = (short) w;
		ret.H = (short) h;
		if (px < 0 || py < 0 || qx < 0 || qy < 0 || px > w || py > h || qx > w
				|| qy > h)
			println("-_-! the Line point can not out of reference Rect !!!");
		ret.PX = (short) px;
		ret.PY = (short) py;
		ret.QX = (short) qx;
		ret.QY = (short) qy;
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
	static public boolean touch(CCD b1, int x1, int y1, CCD b2, int x2,
			int y2, boolean processStatus) {
		//TODO
		if ((b1.Active && b2.Active)
				&& ((processStatus && (b1.Mask & b2.Mask) != 0) || !processStatus)) {
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
	 * 
	 * @param rect1
	 * @param x1
	 * @param y1
	 * @param rect2
	 * @param x2
	 * @param y2
	 * @return false:true
	 */
	static public boolean touchRect(CCD rect1, int x1, int y1, CCD rect2,
			int x2, int y2) {
		return cdRect(rect1.X + x1, rect1.Y + y1, rect1.W, rect1.H, rect2.X
				+ x2, rect2.Y + y2, rect2.W, rect2.H);
	}

	static public int touchRectGetW(CCD rect1, int x1, int y1, CCD rect2,
			int x2, int y2) {
		return cdRectGetW(rect1.X + x1, rect1.Y + y1, rect1.W, rect1.H, rect2.X
				+ x2, rect2.Y + y2, rect2.W, rect2.H);
	}
	
	static public int touchRectGetH(CCD rect1, int x1, int y1, CCD rect2,
			int x2, int y2) {
		return cdRectGetH(rect1.X + x1, rect1.Y + y1, rect1.W, rect1.H, rect2.X
				+ x2, rect2.Y + y2, rect2.W, rect2.H);
	}
	/**
	 * 
	 * @param line1
	 * @param x1
	 * @param y1
	 * @param line2
	 * @param x2
	 * @param y2
	 * @return false:true
	 */
	static public boolean touchLine(CCD line1, int x1, int y1, CCD line2,
			int x2, int y2) {
		if (touchRect(line1, x1, y1, line2, x2, y2)) {
			return cdLine((line1.X + line1.PX + x1) << PRECISION, (line1.Y
					+ line1.PY + y1) << PRECISION,
					(line1.X + line1.QX + x1) << PRECISION,
					(line1.Y + line1.QY + y1) << PRECISION,
					(line2.X + line2.PX + x2) << PRECISION,
					(line2.Y + line2.PY + y2) << PRECISION,
					(line2.X + line2.QX + x2) << PRECISION,
					(line2.Y + line2.QY + y2) << PRECISION);
		}
		return false;
	}

	/**
	 * 
	 * @param rect
	 * @param rx
	 * @param ry
	 * @param line
	 * @param lx
	 * @param ly
	 * @return false:true
	 */
	static public boolean touchRectLine(CCD rect, int rx, int ry, CCD line,
			int lx, int ly) {
		if (touchRect(rect, rx, ry, line, lx, ly)) {
			if (cdLine(//TOP
					(rx + rect.X) << PRECISION,//
					(ry + rect.Y) << PRECISION,//
					(rx + rect.X + rect.W) << PRECISION,//
					(ry + rect.Y) << PRECISION,//
					(lx + line.X + line.PX) << PRECISION,//
					(ly + line.Y + line.PY) << PRECISION,//
					(lx + line.X + line.QX) << PRECISION,//
					(ly + line.Y + line.QY) << PRECISION))
				return true;
			if (cdLine(//LEFT
					(rx + rect.X) << PRECISION,//
					(ry + rect.Y) << PRECISION,//
					(rx + rect.X) << PRECISION,//
					(ry + rect.Y + rect.H) << PRECISION,//
					(lx + line.X + line.PX) << PRECISION,//
					(ly + line.Y + line.PY) << PRECISION,//
					(lx + line.X + line.QX) << PRECISION,//
					(ly + line.Y + line.QY) << PRECISION))
				return true;
			if (cdLine(//RIGHT
					(rx + rect.X + rect.W) << PRECISION,//
					(ry + rect.Y) << PRECISION,//
					(rx + rect.X + rect.W) << PRECISION,//
					(ry + rect.Y + rect.H) << PRECISION,//
					(lx + line.X + line.PX) << PRECISION,//
					(ly + line.Y + line.PY) << PRECISION,//
					(lx + line.X + line.QX) << PRECISION,//
					(ly + line.Y + line.QY) << PRECISION))
				return true;
			if (cdLine(//BUTTON
					(rx + rect.X) << PRECISION,//
					(ry + rect.Y + rect.H) << PRECISION,//
					(rx + rect.X + rect.W) << PRECISION,//
					(ry + rect.Y + rect.H) << PRECISION,//
					(lx + line.X + line.PX) << PRECISION,//
					(ly + line.Y + line.PY) << PRECISION,//
					(lx + line.X + line.QX) << PRECISION,//
					(ly + line.Y + line.QY) << PRECISION))
				return true;
		}
		return false;
	}

	//----------------------------------------------------------------------------------------------------------------------

	/* croe */

	/**
	 * 判断2条直线段是否相撞 屏幕坐标系: 判断P1P2跨立Q1Q2的依据是： 
	 * ((Q2-Q1)×(P1-Q1))*((P2-Q1)×(Q2-Q1)) >= 0 
	 * 同理判断Q1Q2跨立P1P2的依据是： 
	 * ((P2-P1)×(Q1-P1))*((Q2-P1)×(P2-P1)) >= 0
	 * 
	 * @param p1x
	 *            line 1
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @param q1x
	 *            line 2
	 * @param q1y
	 * @param q2x
	 * @param q2y
	 * @return false:true
	 */
	final static public boolean cdLine(
			int p1x, int p1y, int p2x, int p2y,
			int q1x, int q1y, int q2x, int q2y) {
		int p1[] = CMath.vectorSub(q2x, q2y, q1x, q1y);//1
		//		int p2[] = GameMath.vectorSub(p1x,p1y,q1x,q1y);//2
		//		int p3[] = GameMath.vectorSub(p2x,p2y,q1x,q1y);//3
		//		int p4[] = GameMath.vectorSub(q2x,q2y,q1x,q1y);//4=1
		int p5[] = CMath.vectorSub(p2x, p2y, p1x, p1y);//5
		//		int p6[] = GameMath.vectorSub(q1x,q1y,p1x,p1y);//6
		//		int p7[] = GameMath.vectorSub(q2x,q2y,p1x,p1y);//7
		//		int p8[] = GameMath.vectorSub(p2x,p2y,p1x,p1y);//8=5
		if (CMath.vectorMul(p1, CMath.vectorSub(p1x, p1y, q1x, q1y))
				* CMath.vectorMul(CMath.vectorSub(p2x, p2y, q1x, q1y), p1) >= 0
				&& CMath.vectorMul(p5, CMath.vectorSub(q1x, q1y, p1x, p1y))
						* CMath.vectorMul(
								CMath.vectorSub(q2x, q2y, p1x, p1y), p5) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断2个矩形是否相撞
	 * 
	 * @param sx
	 *            第一个矩形
	 * @param sy
	 * @param sw
	 * @param sh
	 * @param dx
	 *            第二个矩形
	 * @param dy
	 * @param dw
	 * @param dh
	 * @return false:true 是否相撞
	 */
	final static public boolean cdRect(
			int sx, int sy, int sw, int sh, 
			int dx, int dy, int dw, int dh) {
		if (Math.max(sx, dx) - Math.min(sx+sw, dx+dw) < 0 && //
			Math.max(sy, dy) - Math.min(sy+sh, dy+dh) < 0) { //
			return true;
		}
		return false;
	}
	
	final static public int cdRectGetW(
			int sx, int sy, int sw, int sh, 
			int dx, int dy, int dw, int dh){
		int x = Math.min(sx+sw, dx+dw) - Math.max(sx, dx) ;
		int y = Math.min(sy+sh, dy+dh) - Math.max(sy, dy) ;
		if ( x>=0 && y>=0  ) {
			return x+1;
		}
		return 0;
	}
	
	final static public int cdRectGetH(
			int sx, int sy, int sw, int sh, 
			int dx, int dy, int dw, int dh){
		int x = Math.min(sx+sw, dx+dw) - Math.max(sx, dx) ;
		int y = Math.min(sy+sh, dy+dh) - Math.max(sy, dy) ;
		if ( x>=0 && y>=0  ) {
			return y+1;
		}
		return 0;
	}
	
	/**
	 * 判断2个圆是否相撞
	 * 
	 * @param x1
	 * @param y1
	 * @param r1
	 * @param x2
	 * @param y2
	 * @param r2
	 * @return false:true
	 */
	final static public boolean cdRound(int x1, int y1, int r1, int x2, int y2,
			int r2) {
		int x, y, z;
		x = Math.abs(x1 - x2);
		y = Math.abs(y1 - y2);
		z = CMath.sqrt(x * x + y * y);
		if (z <= r1 + r2) {
			return true;
		}
		return false;
	}

	/**
	 * 判断点和线段是否相交
	 * 
	 * @param qx
	 *            point
	 * @param qy
	 * @param p1x
	 *            line
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @return false:true
	 */
	final static public boolean cdPointLine(int qx, int qy, int p1x, int p1y,
			int p2x, int p2y) {
		//：( Q - P1 ) × ( P2 - P1 ) = 0
		if (CMath.vectorMul(CMath.vectorSub(qx, qy, p1x, p1y), CMath
				.vectorSub(p2x, p2y, p1x, p1y)) == 0) {
			if (Math.min(p1x, p2x) <= qx && Math.max(p1x, p2x) >= qx
					&& Math.min(p1y, p2y) <= qy && Math.max(p1y, p2y) >= qy) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断2个点是否相撞
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return false:true
	 */
	final static public boolean cdPoint(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2)
			return true;
		return false;
	}

}