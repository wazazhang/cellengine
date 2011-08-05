package com.cell.gfx.transition
{
	import com.cell.gfx.CellScreenManager;
	import com.cell.gfx.IScreenTransition;
	
	import flash.display.Sprite;

	public class AlphaTransition extends Sprite implements IScreenTransition
	{
		private var transition_max_time	: Number	= 10;
		private var transition_timer	: Number	= 10;
		
		private var is_transition_in 	: Boolean	= true;
		private var is_transition_out	: Boolean	= false;
		
		private var color 				: int;
		
		public function AlphaTransition(width:int, height:int, color:int=0xff000000, max_time:int=10) 
		{
			this.mouseChildren = false;
			this.mouseEnabled = false;
			this.color = color;
			this.transition_max_time = Math.max(1, max_time);
			this.x = 0;
			this.y = 0;
			this.graphics.beginFill(color, 1);
			this.graphics.drawRect(0, 0, width, height);
			this.graphics.endFill();		
			startTransitionIn();	
		}
		
		public function asSprite() : Sprite
		{
			return this;
		}
		
		public function startTransitionIn() : void {
			if (!is_transition_in) {
				is_transition_in 	= true;
				is_transition_out 	= false;
				transition_timer 	= 0;
			}
		}
		
		public function startTransitionOut() : void {
			if (!is_transition_out) {
				is_transition_in 	= false;
				is_transition_out 	= true;
				transition_timer 	= 0;
			}
		}
		
		public function isTransitionIn() : Boolean {
			return is_transition_in;
		}
		
		public function isTransitionOut() : Boolean {
			return is_transition_out;
		}
		
		public function render(root:CellScreenManager) : void
		{
			var a : Number = 0;
			
			if (is_transition_in) {
				a = 1 - transition_timer / transition_max_time;
			} else if (is_transition_out) {
				a = transition_timer / transition_max_time;
			}
			a = Math.max(a, 0);
			a = Math.min(a, 1);
			
			this.alpha = a;
			
			if (transition_timer > transition_max_time) {
				is_transition_in	= false;
				is_transition_out	= false;
			}
			transition_timer ++;
			
		}
		
	}
}