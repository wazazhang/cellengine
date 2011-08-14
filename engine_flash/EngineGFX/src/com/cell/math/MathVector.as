package com.cell.math
{
	public class MathVector
	{
		/**
		 * 移动指定偏移
		 * @param v
		 * @param dx x距离
		 * @param dy y距离
		 */
		public static function move(v:IVector2D, dx:Number, dy:Number) : void {
			v.addVectorX(dx);
			v.addVectorY(dy);
		}
		
		/**
		 * 通过极坐标来移动
		 * @param v
		 * @param degree 弧度
		 * @param distance 距离
		 */
		public static function movePolar(v:IVector2D, degree:Number, distance:Number) : void {
			move(v, 
				Math.cos(degree) * distance, 
				Math.sin(degree) * distance);
		}
	
		/**
		 * 通过极坐标来移动
		 * @param v
		 * @param degree 弧度
		 * @param speed  速度 (单位距离/秒)
		 * @param interval_ms 毫秒时间
		 */
		public static function movePolarSpeed(v:IVector2D,  degree:Number,  speed:Number, interval_ms:Number) : void {
			movePolar(v, degree, getDistanceSpeed(speed, interval_ms));
		}
		
		/**
		 * 向目标移动
		 * @param v
		 * @param x 目标x
		 * @param y 目标y
		 * @return 是否到达目的地
		 */
		public static function moveTo(v:IVector2D,  x:Number,  y:Number,  distance:Number) : Boolean {
			var ddx : Number = x - v.getVectorX();
			var ddy : Number = y - v.getVectorY();
			if (Math.abs(ddx) < distance && Math.abs(ddy) < distance) {
				v.setVectorX(x);
				v.setVectorY(y);
				return true;
			} else {
				var angle : Number = Math.atan2(ddy, ddx);
				movePolar(v, angle, distance);
				return false;
			}
		}
		
		/**
		 * 向量缩放
		 * @param v
		 * @param scale
		 */
		public static function scale(v:IVector2D,  scale_x:Number,  scale_y:Number) : void {
			v.setVectorX(v.getVectorX() * scale_x);
			v.setVectorY(v.getVectorY() * scale_y);
		}
	
		/**
		 * 向量按照{0,0}点旋转
		 * @param v
		 * @param degree 弧度
		 */
		public static function rotate(v:IVector2D,  degree:Number) : void 
		{
			var cos_v : Number = Math.cos(degree);
			var sin_v : Number = Math.sin(degree);
			var x : Number = (v.getVectorX()) * cos_v - (v.getVectorY()) * sin_v;
			var y : Number = (v.getVectorY()) * cos_v + (v.getVectorX()) * sin_v; 
			v.setVectorX(x);
			v.setVectorY(y);
		}
		
		/**
		 * 向量按照p0点旋转
		 * @param v
		 * @param p0
		 * @param degree 弧度
		 */
		public static function rotatePoint(v:IVector2D,  px:Number,  py:Number,  degree:Number) : void {
			var dx : Number = v.getVectorX() - px;
			var dy : Number = v.getVectorY() - py;
			var cos_v : Number = Math.cos(degree);
			var sin_v : Number = Math.sin(degree);
			var x : Number = px + dx * cos_v - dy * sin_v;
			var y : Number = py + dy * cos_v + dx * sin_v;
			v.setVectorX(x);
			v.setVectorY(y);
		}
		
		/**
		 * 得到速度和时间产生的距离
		 * @param speed 速度 (单位距离/秒)
		 * @param interval_ms 毫秒时间
		 * @return
		 */
		public static function getDistanceSpeed(speed:Number, interval_ms:Number) : Number {
			var rate : Number = interval_ms / 1000 ;
			return speed * rate;
		}
		
		/**
		 * 得到零点和此点的距离
		 * @param v
		 * @return
		 */
		public static function getDistancePoint(v:IVector2D) : Number {
			return getDistance2Point(0, 0, v.getVectorX(), v.getVectorY());
		}
		
		/**
		 * 得到2点的距离
		 * @param speed 速度 (单位距离/秒)
		 * @param interval_ms 毫秒时间
		 * @return
		 */
		public static function getDistance2Point( x1 : Number,  y1 : Number,  x2 : Number,  y2 : Number) : Number {
			var r1 : Number = x1-x2;
			var r2 : Number = y1-y2;
			return Math.sqrt(r1*r1+r2*r2);
		}
		
		
		/**
		 * 得到弧度
		 * @param dx x向量
		 * @param dy y向量
		 * @return
		 */
		public static function getDegree(dx:Number, dy:Number) : Number 
		{
			return Math.atan2(dy, dx);
		}
		
		/**
		 * 得到弧度
		 * @param v 向量
		 * @return
		 */
		public static function getDegreePoint(v:IVector2D) : Number 
		{
			return Math.atan2(v.getVectorY(), v.getVectorX());
		}
		
		public static function getDirect(v:Number) : Number 
		{
			return v==0?0:(v>0?1:-1);
		}
		
	}
}
