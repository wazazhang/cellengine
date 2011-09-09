package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.world.CWorld;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;

	public class CellUnit extends Sprite implements IVector2D
	{
		public function CellUnit()
		{
			mouseEnabled = false;
			mouseChildren = false;
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		public function get world() : CellWorld
		{
			return parent as CellWorld;
		}
		
		
		internal function updateIn(world:CellWorld) : void {
			onUpdate();
		}
		
		internal function renderIn() : void {

		}

		/**
		 * 每帧调用一次
		 */
		protected function onUpdate() : void {}


//		--------------------------------------------------------------------------------------------------------
		
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