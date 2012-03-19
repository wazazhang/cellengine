//
//  CMath.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-8-18.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CMath.h"
#include <string>
#include <vector>
#include <fstream>
#include <math.h>

#include "CType.h"

namespace com_cell
{
  
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	float Math::toDegree(float angle) {
		return angle * 180.0 / M_PI;
	}
	
	int Math::cycNum(int value, int d, int max) {
		value += d;
		return (value>=0)?(value % max):((max + value % max) % max) ;
	}
	
	int Math::cycMod(int value, int div) {
		return (value/div) + (value<0?-1:0);
	}
	
	int Math::roundMod(int value, int div) {
		return (value/div) + (value%div==0?0:(1*getDirect(value)));
	}
	
	int Math::getDirect(int value) {
		return value==0?0:(value>0?1:-1);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// line intersect
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	void Math::vectorAdd(Vector2D &out_v, float px, float py, float qx, float qy) {
		out_v.x = px + qx;
		out_v.y = py + qy;
	}
	
	void Math::vectorSub(Vector2D &out_v, float px, float py, float qx, float qy) {
		out_v.x = px - qx;
		out_v.y = py - qy;
	}
		
	float  Math::vectorCrossProduct(Vector2D const &p, Vector2D const &q) {
		return (p.x * q.y - p.y * q.x);
	}

	bool	Math::intersectLine(float p1x, float p1y, float p2x, float p2y,
									float q1x, float q1y, float q2x, float q2y) 
	{
		/* croe */
		Vector2D v1, v2, v3, v5, v6, v7;
		
		vectorSub(v1, q2x, q2y, q1x, q1y);//1
		vectorSub(v2, p1x, p1y, q1x, q1y);//2
		vectorSub(v3, p2x, p2y, q1x, q1y);//3
		//vectorSub(v4, q2x, q2y, q1x, q1y);//4=1
		
		vectorSub(v5, p2x, p2y, p1x, p1y);//5
		vectorSub(v6, q1x, q1y, p1x, p1y);//6
		vectorSub(v7, q2x, q2y, p1x, p1y);//7
		//vectorSub(v8, p2x, p2y, p1x, p1y);//8=5
		
		if (vectorCrossProduct(v1, v2) * vectorCrossProduct(v3, v1) >= 0 && 
			vectorCrossProduct(v5, v6) * vectorCrossProduct(v7, v5) >= 0) {
			return true;
		}
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// collides
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	bool Math::intersectRound(float sx, float sy, float sr, 
							  float dx, float dy, float dr)
	{
		float w = sx - dx;
		float h = sy - dy;
		float r = sr + dr;
		return (w*w + h*h <= r*r);
	}
	
	bool Math::includeRoundPoint(float sx, float sy, float sr, 
								 float px, float py)
	{
		float w = sx - px;
		float h = sy - py;
		return (w*w + h*h <= sr*sr);
	}
		
	bool Math::intersectRect(float sx1, float sy1, float sx2, float sy2, 
							 float dx1, float dy1, float dx2, float dy2)
	{
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}
	
	bool Math::intersectRectWH(float sx1, float sy1, float sw, float sh, 
							   float dx1, float dy1, float dw, float dh)
	{
		float sx2 = sx1 + sw;
		float sy2 = sy1 + sh;
		float dx2 = dx1 + dw;
		float dy2 = dy1 + dh;
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}
	
	bool Math::includeRectPoint(float sx1, float sy1, float sx2, float sy2, 
								 float dx, float dy) 
	{
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}
	
	bool Math::includeRectPointWH(float sx1, float sy1, float sw, float sh, 
								  float dx, float dy) 
	{
		float sx2 = sx1 + sw;
		float sy2 = sy1 + sh;
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}

	bool Math::isIncludeEqual(int value, int min, int max)
	{
		return max >= value && min <= value ;
	}
		
	bool Math::isInclude(int value, int min, int max)
	{
		return max > value && min < value ;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// vector 2d
	//////////////////////////////////////////////////////////////////////////////////////////////
			
	
	void Math::move(Vector2D &v, float dx, float dy){
		v.x += dx;
		v.y += dy;
	}
		
	void Math::movePolar(Vector2D &v, float degree, float distance)
	{
		float dx = cos(degree) * distance;
		float dy = sin(degree) * distance;
		move(v, dx, dy);
	}
	
	void Math::movePolar(Vector2D &v, float degree, float speed, int interval_ms) 
	{
		float distance = getDistance(speed, interval_ms);
		movePolar(v, degree, distance);
	}
		
	bool Math::moveTo(Vector2D &v, float x, float y, float distance)
	{
		float ddx = x - v.x;
		float ddy = y - v.y;
		if (abs(ddx) < distance && abs(ddy) < distance) {
			v.x = x;
			v.y = y;
			return true;
		} else {
			float angle = atan2(ddy, ddx);
			movePolar(v, angle, distance);
			return false;
		}
	}
		
	void Math::scale(Vector2D &v, float scale)
	{
		v.x = (v.x * scale);
		v.y = (v.y * scale);
	}		
		
		
	void Math::scale(Vector2D &v, float scale_x, float scale_y)
	{
		v.x = (v.x * scale_x);
		v.y = (v.y * scale_y);
	}
		
	void Math::rotate(Vector2D &v, float degree)
	{
		float cos_v = cos(degree);
		float sin_v = sin(degree);
		float x = v.x * cos_v - v.y * sin_v;
		float y = v.y * cos_v + v.x * sin_v; 
		v.x = x;
		v.y = y;
	}
		
	void Math::rotate(Vector2D &v, Vector2D const &p0, float degree)
	{
		float dx = v.x - p0.x;
		float dy = v.y - p0.y;
		float cos_v = cos(degree);
		float sin_v = sin(degree);
		float x = p0.x + dx * cos_v - dy * sin_v;
		float y = p0.y + dy * cos_v + dx * sin_v;
		v.x = x;
		v.y = y;
	}
		
	void Math::rotate(Vector2D &v, float px, float py, float degree)  {
		float dx = v.x - px;
		float dy = v.y - py;
		float cos_v = cos(degree);
		float sin_v = sin(degree);
		float x = px + dx * cos_v - dy * sin_v;
		float y = py + dy * cos_v + dx * sin_v;
		v.x = x;
		v.y = y;
	}
	
	float Math::getDistance(float speed, int interval_ms){
		float rate = interval_ms / 1000.0f;
		return speed * rate;
	}
		
	float Math::getDistance(Vector2D const &v) {
		return getDistance(0, 0, v.x, v.y);
	}
		
	float Math::getDistance(float x1, float y1, float x2, float y2)
	{
		float r1 = x1-x2;
		float r2 = y1-y2;
		return sqrt(r1*r1+r2*r2);
	}
		
	float Math::getDegree(float dx, float dy)
	{
		return atan2(dy, dx);
	}
		
	float Math::getDegree(Vector2D const &v)
	{
		return atan2(v.y, v.x);
	}
		
	Vector2D Math::vectorAdd(Vector2D const &a, Vector2D const &b)
	{
		Vector2D v;
		v.x = (a.x + b.x);
		v.y = (a.y + b.y);
		return v;
	}
	
	Vector2D Math::vectorSub(Vector2D const &a, Vector2D const &b)
	{
		Vector2D v;
		v.x = (a.x - b.x);
		v.y = (a.y - b.y);
		return v;
	}
		
	Vector2D Math::vectorAdd(Vector2D const &a, float degree, float distance){
		Vector2D v;
		v.x = (a.x);
		v.y = (a.y);
		movePolar(v, degree, distance);
		return v;
	}
		
	Vector2D Math::vectorAdd(Vector2D const &a, float distance)
	{
		Vector2D v;
		v.x = (a.x);
		v.y = (a.y);
		movePolar(v, getDegree(v), distance);
		return v;
	}
		
	Vector2D Math::vectorScale(Vector2D const &a, float scale)
	{
		Vector2D v;
		v.x = (a.x * scale);
		v.y = (a.y * scale);
		return v;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// 
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	float Math::rSqrt(float number)
	{
		long i;
		float x2, y;
		const float threehalfs = 1.5F;
		
		x2 = number * 0.5F;
		y  = number;
		i  = * ( long * ) &y;                       // evil floating point bit level hacking
		i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
		y  = * ( float * ) &i;
		
		// 以下代码可以一直加，直到想要的精度
		//y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
		//y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, this can be removed
		
		for (int ci=0; ci<1; ci++) {
			y  = y * ( threehalfs - ( x2 * y * y ) );   // iteration
		}
		
		return y;
	}
	
	
}; // namespace com.cell