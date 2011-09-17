package com.cell.gfx
{
	import flash.display.Sprite;
	import flash.events.Event;

	public class CellSprite extends Sprite
	{
		public function CellSprite()
		{			
			addEventListener(Event.ADDED, _added);
			addEventListener(Event.REMOVED, _removed);
		}
		
		private function _added(e:Event) : void
		{				
			addEventListener(Event.ENTER_FRAME, update);
		}
		private function _removed(e:Event) : void
		{				
			removeEventListener(Event.ENTER_FRAME, update);
		}

		protected function update(e:Event) : void
		{				
		}
		

	}
}