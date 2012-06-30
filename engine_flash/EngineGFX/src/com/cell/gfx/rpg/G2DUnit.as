package com.cell.gfx.rpg
{
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.rpg.intention.Action;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	import com.cell.util.Arrays;
	import com.cell.util.Map;
	import com.cell.util.Util;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;

	public class G2DUnit extends Sprite implements IVector2D
	{
		
		public function update() : void 
		{
			
		}
		
		public function move(dx:Number, dy:Number) : void {
			this.x += dx;
			this.y += dy;
		}
		
		/**
		 * 通过极坐标来移动
		 * @param angle 弧度
		 * @param distance
		 */
		public function movePolar(angle:Number, distance:Number) : void {
			MathVector.movePolar(this, angle, distance);
		}
		
		/**
		 * 向目标移动
		 * @param dx
		 * @param dy
		 * @return 是否到达目标点
		 */
		public function moveTo(x:Number, y:Number, distance:Number) : Boolean {
			return MathVector.moveTo(this, x, y, distance);
		}
		
		
		public function addVectorX(dx:Number) : void {
			this.x += dx;
		}
		public function addVectorY(dy:Number) : void {
			this.y += dy;
		}
		public function setVectorX(x:Number) : void {
			this.x = x;
		}
		public function setVectorY(y:Number) : void {
			this.y = y;
		}
		public function getVectorX() : Number {
			return this.x;
		}
		public function getVectorY() : Number {
			return this.y;
		}
	}
}