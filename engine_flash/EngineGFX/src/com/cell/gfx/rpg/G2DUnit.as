package com.cell.gfx.rpg
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.CellUnit;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.rpg.intention.IIntention;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	import com.cell.util.Map;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;

	public class G2DUnit extends CellUnit implements IVector2D
	{
		private var intention_map : Map;
		
		public function G2DUnit()
		{
		}
		
		public function asDisplayObject() : DisplayObject {
			return this;
		}
		
		/**
		 * 每帧调用一次
		 */
		public function update() : void {
			if (intention_map != null) {
				for each (var i:IIntention in intention_map) {
					i.onUpdate(this);
				}
			}
		}
		
		
		
		public function addIntention(name:String, intention:IIntention) : void {
			if (intention_map == null) {
				intention_map = new Map();
			}
			var old : IIntention = intention_map.put(name, intention) as IIntention;
			if (old != null) {
				old.onStop(this);
			}
			intention.onStart(this);
			
		}
		
		public function getIntention(name:String) : IIntention {
			if (intention_map != null) {
				return intention_map.get(name);
			}
			return null;
		}
		
		public function removeIntention(name:String) : IIntention {
			if (intention_map != null) {
				var old : IIntention = intention_map.remove(name);
				if (old != null) {
					old.onStop(this);
					return old;
				}
			}
			return null;
		}
		
		public function stopIntention(intention:IIntention) : void {
			if (intention_map != null) {
				intention_map.removeValue(intention);
				intention.onStop(this);
			}
		}
		
	}
}