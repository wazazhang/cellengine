package com.cell.gfx.game.worldcraft
{
	import com.cell.gfx.game.world.CWorld;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;
	import flash.events.Event;

	public class CellUnit extends Sprite implements IVector2D
	{
//		private var _world : CellWorld;
		
		public function CellUnit()
		{
			mouseEnabled = false;
			mouseChildren = false;
//			addEventListener(Event.ADDED, 	addedWorld);
//			addEventListener(Event.REMOVED,	removedWorld);
		}
		
		public function get world() : CellWorld
		{
			return parent as CellWorld;
		}
		
//		--------------------------------------------------------------------------------------------------------
		

		
		
		internal function updateIn(world:CellWorld) : void {
			onUpdate();
		}
		
		/**
		 * 每帧调用一次
		 */
		internal function renderIn() : void {

		}
		
//		--------------------------------------------------------------------------------------------------------

		/**
		 * 每帧调用一次
		 */
		protected function onUpdate() : void {}
		
//		protected function onAddedWorld(wd:CellWorld) : void{}
//		
//		protected function onRemovedWorld(wd:CellWorld) : void{}
		
//		--------------------------------------------------------------------------------------------------------

//		private function addedWorld(e:Event) : void
//		{
//			if (parent as CellWorld) {
//				_world = world;
//				_world._units.push(this);
//				onAddedWorld(_world);
//			}
//		}
//		private function removedWorld(e:Event) : void
//		{
//			if (_world != null) {
//				_world._units.splice(_world._units.indexOf(this), 1);
//				onRemovedWorld(_world);
//				_world = null;
//			}
//		}
		
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