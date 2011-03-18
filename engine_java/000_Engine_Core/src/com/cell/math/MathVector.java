package com.cell.math;

import java.awt.geom.AffineTransform;

public class MathVector
{

	/**
	 * 移动指定偏移
	 * @param v
	 * @param dx x距离
	 * @param dy y距离
	 */
	public static void move(Vector v, double dx, double dy){
		v.addVectorX(dx);
		v.addVectorY(dy);
	}
	
	/**
	 * 通过极坐标来移动
	 * @param v
	 * @param degree 弧度
	 * @param distance 距离
	 */
	public static void movePolar(Vector v, double degree, double distance){
		double dx = Math.cos(degree) * distance;
		double dy = Math.sin(degree) * distance;
		move(v, dx, dy);
	}

	/**
	 * 通过极坐标来移动
	 * @param v
	 * @param degree 弧度
	 * @param speed  速度 (单位距离/秒)
	 * @param interval_ms 毫秒时间
	 */
	public static void movePolar(Vector v, double degree, double speed, int interval_ms) {
		double distance = getDistance(speed, interval_ms);
		movePolar(v, degree, distance);
	}
	
	/**
	 * 向目标移动
	 * @param v
	 * @param x 目标x
	 * @param y 目标y
	 * @return 是否到达目的地
	 */
	public static boolean moveTo(Vector v, double x, double y, double distance){
		double ddx = x - v.getVectorX();
		double ddy = y - v.getVectorY();
		if (Math.abs(ddx) < distance && Math.abs(ddy) < distance) {
			v.setVectorX(x);
			v.setVectorY(y);
			return true;
		} else {
			double angle = Math.atan2(ddy, ddx);
			movePolar(v, angle, distance);
			return false;
		}
	}
	
	/**
	 * 向量缩放
	 * @param v
	 * @param scale
	 */
	public static void scale(Vector v, double scale){
		v.setVectorX(v.getVectorX() * scale);
		v.setVectorY(v.getVectorY() * scale);
	}
	
	/**
	 * 向量缩放
	 * @param v
	 * @param scale
	 */
	public static void scale(Vector v, double scale_x, double scale_y){
		v.setVectorX(v.getVectorX() * scale_x);
		v.setVectorY(v.getVectorY() * scale_y);
	}

	/**
	 * 向量按照{0,0}点旋转
	 * @param v
	 * @param degree 弧度
	 */
	public static void rotate(Vector v, double degree)
	{
		double cos_v = Math.cos(degree);
		double sin_v = Math.sin(degree);
		double x = (v.getVectorX()) * cos_v - (v.getVectorY()) * sin_v;
		double y = (v.getVectorY()) * cos_v + (v.getVectorX()) * sin_v; 
		v.setVectorX(x);
		v.setVectorY(y);
	}
	
	/**
	 * 向量按照p0点旋转
	 * @param v
	 * @param p0
	 * @param degree 弧度
	 */
	public static void rotate(Vector v, Vector p0, double degree)
	{
		double dx = v.getVectorX() - p0.getVectorX();
		double dy = v.getVectorY() - p0.getVectorY();
		double cos_v = Math.cos(degree);
		double sin_v = Math.sin(degree);
		double x = p0.getVectorX() + dx * cos_v - dy * sin_v;
		double y = p0.getVectorY() + dy * cos_v + dx * sin_v;
		v.setVectorX(x);
		v.setVectorY(y);
	}
	
	/**
	 * 向量按照p0点旋转
	 * @param v
	 * @param p0
	 * @param degree 弧度
	 */
	public static void rotate(Vector v, double px, double py, double degree) {
		double dx = v.getVectorX() - px;
		double dy = v.getVectorY() - py;
		double cos_v = Math.cos(degree);
		double sin_v = Math.sin(degree);
		double x = px + dx * cos_v - dy * sin_v;
		double y = py + dy * cos_v + dx * sin_v;
		v.setVectorX(x);
		v.setVectorY(y);
	}
	/**
	 * 得到速度和时间产生的距离
	 * @param speed 速度 (单位距离/秒)
	 * @param interval_ms 毫秒时间
	 * @return
	 */
	public static double getDistance(double speed, int interval_ms){
		double rate = interval_ms / 1000f ;
		return speed * rate;
	}
	
	public static double getDistance(Vector v) {
		return getDistance(0, 0, v.getVectorX(), v.getVectorY());
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2){
		double r1 = x1-x2;
		double r2 = y1-y2;
		return Math.sqrt(r1*r1+r2*r2);
	}
	
	
	/**
	 * 得到弧度
	 * @param dx x向量
	 * @param dy y向量
	 * @return
	 */
	public static double getDegree(double dx, double dy)
	{
		return Math.atan2(dy, dx);
	}
	
	/**
	 * 得到弧度
	 * @param v 向量
	 * @return
	 */
	public static double getDegree(Vector v)
	{
		return Math.atan2(v.getVectorY(), v.getVectorX());
	}
	
	
	/**
	 * 将2个向量相加得到一个新的向量
	 * @param a
	 * @param b
	 * @return
	 */
	public static TVector vectorAdd(Vector a, Vector b){
		TVector v = new TVector();
		v.setVectorX(a.getVectorX() + b.getVectorX());
		v.setVectorY(a.getVectorY() + b.getVectorY());
		return v;
	}
	
	/**
	 * 将2个向量相减得到一个新的向量
	 * @param a
	 * @param b
	 * @return
	 */
	public static TVector vectorSub(Vector a, Vector b){
		TVector v = new TVector();
		v.setVectorX(a.getVectorX() - b.getVectorX());
		v.setVectorY(a.getVectorY() - b.getVectorY());
		return v;
	}
	
	/**
	 * 将一个向量加上新的向量，得到一个新的向量
	 * @param a
	 * @param degree
	 * @param distance
	 * @return
	 */
	public static TVector vectorAdd(Vector a, double degree, double distance){
		TVector v = new TVector();
		v.setVectorX(a.getVectorX());
		v.setVectorY(a.getVectorY());
		movePolar(v, degree, distance);
		return v;
	}
	
	/**
	 * 把一个向量向自己本身的方向相加，得到一个新的向量
	 * @param a
	 * @param distance
	 * @return
	 */
	public static TVector vectorAdd(Vector a, double distance){
		TVector v = new TVector();
		v.setVectorX(a.getVectorX());
		v.setVectorY(a.getVectorY());
		movePolar(v, getDegree(v), distance);
		return v;
	}
	
	/**
	 * 将一个向量缩放一定比率后，得到一个新的向量
	 * @param a
	 * @param scale
	 * @return
	 */
	public static TVector vectorScale(Vector a, double scale){
		TVector v = new TVector();
		v.setVectorX(a.getVectorX() * scale);
		v.setVectorY(a.getVectorY() * scale);
		return v;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
