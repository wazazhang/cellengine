package com.cell.gfx
{
	import flash.display.Sprite;
	import flash.events.Event;

	public class CellSprite extends Sprite
	{
		public function CellSprite()
		{			
			addEventListener(Event.ADDED_TO_STAGE, _added);
			addEventListener(Event.REMOVED_FROM_STAGE, _removed);
		}
		
		private function _added(e:Event) : void
		{				
			addEventListener(Event.ENTER_FRAME, update);
			this.added(e);
		}
		private function _removed(e:Event) : void
		{				
			removeEventListener(Event.ENTER_FRAME, update);
			this.removed(e);
		}

		protected function added(e:Event) : void
		{
			
		}
		
		protected function removed(e:Event) : void
		{
			
		}
		
		protected function update(e:Event) : void
		{				
		}
		
		public function removeFromParent() : Boolean
		{
			if (parent != null) {
				parent.removeChild(this);
				return true;
			}
			return false;
		}
	}
}