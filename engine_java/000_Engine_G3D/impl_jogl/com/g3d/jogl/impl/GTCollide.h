/*
 *  GTCollide.h
 *  FingerSketch
 *
 *  Created by WAZA on 08-4-13.
 *  Copyright 2008 __MyCompanyName__. All rights reserved.
 *
 */


#ifndef _GAMETILER_COLLIDE
#define _GAMETILER_COLLIDE


#include "GTType.h"

#include "GTGfx.h"

namespace gt
{

class CCD  
{

	static const u8 CD_TYPE_RECT		= 1;
	static const u8 CD_TYPE_LINE		= 2;
	static const u8 CD_TYPE_POINT		= 3;
	
	public:
		u8 Type;
		u32 Mask;

		/**Left */
		float X1;
		/**Top */
		float Y1;
		/**Right*/
		float X2;
		/**Bottom*/
		float Y2;

	public:
	
	inline float getWidth(){
		return X2 - X1 + 1;
	}
	
	inline float getHeight(){
		return Y2 - Y1 + 1;
	}
	
	inline void render(Graphics2D &g, float px, float py) {
		
	}


	static CCD* createCDRect(u32 mask, float x, float y, float w, float h) {
		CCD* ret = new CCD();
		ret->Type = CD_TYPE_RECT;
		ret->Mask = mask;

		ret->X1 =  x;
		ret->Y1 =  y;
		ret->X2 =  (x + w);
		ret->Y2 =  (y + h);
		return ret;
	}

	static CCD* createCDLine(u32 mask, float px, float py, float qx, float qy)
	{
		CCD* ret = new CCD();
		
		ret->Type = CD_TYPE_LINE;
		ret->Mask =  mask;

		ret->X1 = px;
		ret->Y1 = py;
		
		ret->X2 = qx;
		ret->Y2 = qy;
		
		return ret;
	}

	static bool touch(
			CCD b1, int x1, int y1, 
			CCD b2, int x2, int y2, 
			bool processStatus) {
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

	static bool touchRect(
			CCD src, int sx, int sy, 
			CCD dst, int dx, int dy)
	{
		return CMath.intersectRect(
				src.X1 + sx, src.Y1 + sy, 
				src.X2 + sx, src.Y2 + sy, 
				dst.X1 + dx, dst.Y1 + dy, 
				dst.X2 + dx, dst.Y2 + dy);
	}

	static bool touchLine(
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

	static bool touchRectLine(
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

};

};

#endif // #define _GAMETILER_COLLIDE
