
/*�1�7
 * Created on 2005-7-25
 *
 */
package com.cell.gfx.game;

import java.io.Serializable;

import com.cell.CMath;
import com.cell.CObject;
import com.cell.gfx.IGraphics;

public class CCD extends CObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final static public byte CD_TYPE_RECT = 1;
	final static public byte CD_TYPE_LINE = 2;
	final static public byte CD_TYPE_POINT = 3;
	
	public byte Type;

	public int Mask;

	/**Left */
	public short X1;
	/**Top */
	public short Y1;
	/**Right*/
	public short X2;
	/**Bottom*/
	public short Y2;

	transient public Object Data;
	
	transient public String SetData;
	
	public CCD() {}

	/**
	 * form other
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

	public int getWidth(){
		return X2 - X1 + 1;
	}
	
	public int getHeight(){
		return Y2 - Y1 + 1;
	}
	
	/**
	 * 得到外包矩形中心点和渲染中心点的偏移
	 * @return
	 */
	public int getMedianY() {
		return -(getHeight() / 2 - (Y1 + getHeight()));
	}
	
	/**
	 * 得到外包矩形中心点和渲染中心点的偏移
	 * @return
	 */
	public int getMedianX() {
		return -(getWidth() / 2 - (X1 + getWidth()));
	}
	
	public void render(IGraphics g, int px, int py, int color) {
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
	
	static public CCD createCDRect_2Point(int mask, int sx, int sy, int dx, int dy) {
		CCD ret = new CCD();
		ret.Type = CD_TYPE_RECT;
		ret.Mask = mask;

		ret.X1 = (short) Math.min(sx, dx);
		ret.Y1 = (short) Math.min(sy, dy);
		ret.X2 = (short) Math.max(sx, dx);
		ret.Y2 = (short) Math.max(sy, dy);
		return ret;
	}
	
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

	static public boolean touchRect(
			CCD src, int sx, int sy, 
			CCD dst, int dx, int dy) {
		return CMath.intersectRect(
				src.X1 + sx, src.Y1 + sy, 
				src.X2 + sx, src.Y2 + sy, 
				dst.X1 + dx, dst.Y1 + dy, 
				dst.X2 + dx, dst.Y2 + dy);
	}

	static public boolean touchLine(
			CCD src, int sx, int sy, 
			CCD dst, int dx, int dy) {
		if (touchRect(src, sx, sy, dst, dx, dy)) {
			return CMath.intersectLine(
					src.X1 + sx, src.Y1 + sy, 
					src.X2 + sx, src.Y2 + sy, 
					dst.X1 + dx, dst.Y1 + dy, 
					dst.X2 + dx, dst.Y2 + dy);
		}
		return false;
	}

	static public boolean touchRectLine(
			CCD rect, int rx, int ry, 
			CCD line, int lx, int ly) {
		if (CMath.intersectRect(
				rx + rect.X1, ry + rect.Y1,//
				rx + rect.X2, ry + rect.Y2,//
				lx + Math.min(line.X1,line.X2), ly + Math.min(line.Y1,line.Y2),//
				lx + Math.max(line.X1,line.X2), ly + Math.max(line.Y1,line.Y2))//
		) {
			if (CMath.intersectLine(//TOP
					(rx + rect.X1) , (ry + rect.Y1) ,//
					(rx + rect.X2) , (ry + rect.Y1) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (CMath.intersectLine(//LEFT
					(rx + rect.X1) , (ry + rect.Y1) ,//
					(rx + rect.X1) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (CMath.intersectLine(//RIGHT
					(rx + rect.X2) , (ry + rect.Y1) ,//
					(rx + rect.X2) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
			if (CMath.intersectLine(//BUTTON
					(rx + rect.X1) , (ry + rect.Y2) ,//
					(rx + rect.X2) , (ry + rect.Y2) ,//
					(lx + line.X1) , (ly + line.Y1) ,//
					(lx + line.X2) , (ly + line.Y2)))//
				return true;
		}
		return false;
	}

}