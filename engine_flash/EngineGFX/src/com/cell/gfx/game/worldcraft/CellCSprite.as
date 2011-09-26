package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;

	public class CellCSprite extends CellUnit
	{
		internal var _spr : CSprite;
		
		public function CellCSprite()
		{
		}
		
		public function get spr() : CSprite
		{
			return _spr;
		}
		
		public function getCSprite() : CSprite
		{
			return _spr;
		}
		
//		------------------------------------------------------------------------------------------------------
		
		internal override function updateIn(world:CellWorld) : void 
		{	
			onUpdate();
		}

		public function renderSelf() : void
		{
			renderIn();
		}

		
//		------------------------------------------------------------------------------------------------------
		
		public function getCurrentFrame() : uint {
			return _spr.getCurrentFrame();
		}
		
		public function getCurrentAnimate() : uint {
			return _spr.getCurrentAnimate();
		}
		
		public function isEndFrame() : Boolean {
			return _spr.isEndFrame();
		}
		
		public function isBeginFrame() : Boolean {
			return _spr.isBeginFrame();
		}


		public function setCurrentAnimateName(anim_name:String) : int {
			return _spr.setCurrentAnimateName(anim_name);
		}
		
		public function setCurrentAnimate(anim:uint, cyc:Boolean=false) : int {
			return _spr.setCurrentAnimate(anim, cyc);
		}
		
		public function setCurrentFrame(frame:uint, cyc:Boolean=false) : int {
			return _spr.setCurrentFrame(frame, cyc);
		}
		
		public function nextFrame() : int {
			return _spr.nextFrame();
		}
		
		public function nextCycFrame() : int {
			return _spr.nextCycFrame();
		}
		
		public function prewFrame() : int {
			return _spr.prewFrame();
		}
		
		public function prewCycFrame() : int {
			return _spr.prewCycFrame();
		}


	}
}