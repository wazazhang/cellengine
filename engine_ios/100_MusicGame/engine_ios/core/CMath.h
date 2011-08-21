//
//  CMath.h
//  100_MusicGame
//
//  Created by wazazhang on 11-8-12.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//


#ifndef _COM_CELL_MATH
#define _COM_CELL_MATH

#include <string>
#include <vector>
#include <fstream>
#include <math.h>

#include "CType.h"

namespace com_cell
{
    class Math
    {
    public:
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		// 
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		
		/**
		 * 角度转弧度
		 */
        static float toDegree(float angle) ;		
		
		/**
		 * comput cyc number: (value+d) within 0~max scope
		 * @param value
		 * @param d
		 * @param max
		 * @return 
		 */
		static int cycNum(int value, int d, int max) ;		
		
		/**
		 * comput cyc mod: -1 mod 10 = -1
		 * @param value
		 * @param div
		 * @return 
		 */
		static int cycMod(int value, int div) ;		
		
		/**
		 * @param value 
		 * @return 1 or 0 or -1
		 */
		static int getDirect(int value) ;		
		
		/**
		 * comput round mod roundMode(33,8) = 5 => 33/8 + (33%8==0?:0:1)
		 * @param value
		 * @param div
		 * @return 
		 */
		static int roundMod(int value, int div) ;		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		// line intersect
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		static void vectorAdd(Vector2D &out_v, float px, float py, float qx, float qy) ;		
		
		static void vectorSub(Vector2D &out_v, float px, float py, float qx, float qy) ;
		
		static float vectorCrossProduct(Vector2D const &p, Vector2D const &q) ;	
		
		static bool intersectLine(float p1x, float p1y, float p2x, float p2y,
								  float q1x, float q1y, float q2x, float q2y) ;		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		// collides
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		static bool intersectRound(float sx, float sy, float sr, 
								   float dx, float dy, float dr) ;
		
		static bool includeRoundPoint(float sx, float sy, float sr, 
									  float px, float py) ;		
		
		static bool intersectRect(float sx1, float sy1, float sx2, float sy2, 
								  float dx1, float dy1, float dx2, float dy2);	
		
		static bool intersectRectWH(float sx1, float sy1, float sw, float sh, 
									float dx1, float dy1, float dw, float dh) ;
		
		static bool includeRectPoint(float sx1, float sy1, float sx2, float sy2, 
									 float dx, float dy) ;		
		
		static bool includeRectPointWH(float sx1, float sy1, float sw, float sh, 
									   float dx, float dy) ;		
		
		/**
		 * if the value is in a~b or equal
		 * @param value
		 * @param a
		 * @param b
		 * @return
		 */
		static bool isIncludeEqual(int value, int min, int max);		
		/**
		 * if the value is in a~b not equal
		 * @param value
		 * @param a
		 * @param b
		 * @return
		 */
		static bool isInclude(int value, int min, int max);		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		// vector 2d
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * 移动指定偏移
		 * @param v
		 * @param dx x距离
		 * @param dy y距离
		 */
		static void move(Vector2D &v, float dx, float dy);		
		
		/**
		 * 通过极坐标来移动
		 * @param v
		 * @param degree 弧度
		 * @param distance 距离
		 */
		static void movePolar(Vector2D &v, float degree, float distance);		
		/**
		 * 通过极坐标来移动
		 * @param v
		 * @param degree 弧度
		 * @param speed  速度 (单位距离/秒)
		 * @param interval_ms 毫秒时间
		 */
		static void movePolar(Vector2D &v, float degree, float speed, int interval_ms) ;
		
		/**
		 * 向目标移动
		 * @param v
		 * @param x 目标x
		 * @param y 目标y
		 * @return 是否到达目的地
		 */
		static bool moveTo(Vector2D &v, float x, float y, float distance);
		
		/**
		 * 向量缩放
		 * @param v
		 * @param scale
		 */
		static void scale(Vector2D &v, float scale);
		
		/**
		 * 向量缩放
		 * @param v
		 * @param scale
		 */
		static void scale(Vector2D &v, float scale_x, float scale_y);
		
		/**
		 * 向量按照{0,0}点旋转
		 * @param v
		 * @param degree 弧度
		 */
		static void rotate(Vector2D &v, float degree);
		
		/**
		 * 向量按照p0点旋转
		 * @param v
		 * @param p0
		 * @param degree 弧度
		 */
		static void rotate(Vector2D &v, Vector2D const &p0, float degree);
		
		/**
		 * 向量按照p0点旋转
		 * @param v
		 * @param p0
		 * @param degree 弧度
		 */
		static void rotate(Vector2D &v, float px, float py, float degree) ;
		
		
		/**
		 * 得到速度和时间产生的距离
		 * @param speed 速度 (单位距离/秒)
		 * @param interval_ms 毫秒时间
		 * @return
		 */
		static float getDistance(float speed, int interval_ms);
		
		/**(0,0)点和此向量距离*/
		static float getDistance(Vector2D const &v) ;
		
		/**两向量距离*/
		static float getDistance(float x1, float y1, float x2, float y2);
		
		
		/**
		 * 得到弧度
		 * @param dx x向量
		 * @param dy y向量
		 * @return
		 */
		static float getDegree(float dx, float dy);
		
		/**
		 * 得到弧度
		 * @param v 向量
		 * @return
		 */
		static float getDegree(Vector2D const &v);
		
		
		/**
		 * 将2个向量相加得到一个新的向量
		 * @param a
		 * @param b
		 * @return
		 */
		static Vector2D vectorAdd(Vector2D const &a, Vector2D const &b);
		
		/**
		 * 将2个向量相减得到一个新的向量
		 * @param a
		 * @param b
		 * @return
		 */
		static Vector2D vectorSub(Vector2D const &a, Vector2D const &b);
		
		/**
		 * 将一个向量加上新的向量，得到一个新的向量
		 * @param a
		 * @param degree
		 * @param distance
		 * @return
		 */
		static Vector2D vectorAdd(Vector2D const &a, float degree, float distance);
		
		/**
		 * 把一个向量向自己本身的方向相加，得到一个新的向量
		 * @param a
		 * @param distance
		 * @return
		 */
		static Vector2D vectorAdd(Vector2D const &a, float distance);
		
		/**
		 * 将一个向量缩放一定比率后，得到一个新的向量
		 * @param a
		 * @param scale
		 * @return
		 */
		static Vector2D vectorScale(Vector2D const &a, float scale);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		// 
		//////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		
		
		
		
		
		

		
    };
    
    
	
	
}; // namespace com.cell


#endif // #define _COM_CELL_MATH