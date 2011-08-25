package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;

	public class CellCSprite extends CellUnit implements IImageObserver
	{
		private var _spr		: CSprite;
		private var _cg		: IGraphics;
		private var old_anim : int = 0;
		private var old_frame : int = 0;
		
		
		public function CellCSprite(spr:CSprite)
		{
			this._cg = new CGraphicsDisplay(graphics);			
			this._spr = spr;
			this.repaint();
			this.spr.getAnimates().getImages().addImageObserver(this);
			this.mouseEnabled = false;
			this.mouseChildren = false;
		}
		
		public function get spr() : CSprite
		{
			return _spr;
		}
		
		public function getCSprite() : CSprite
		{
			return _spr;
		}
		
		public function get cg() : IGraphics
		{
			return _cg;
		}
		
		internal override function update(world:CellWorld) : void 
		{	
			onUpdate();
			
			if (old_anim  != _spr.getCurrentAnimate() || 
				old_frame != _spr.getCurrentFrame()){
				repaint();
			}
			old_anim  = _spr.getCurrentAnimate();
			old_frame : _spr.getCurrentFrame();
		}

		public function repaint() : void
		{
			graphics.clear();
			spr.render(cg, 0, 0, spr.getCurrentAnimate(), spr.getCurrentFrame());
			graphics.moveTo( 0, 0);
//			if (DEBUG) {
//				graphics.lineStyle(1, 0xffff0000, 1);
//				graphics.lineTo(-8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0,-8); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0, 8); graphics.moveTo( 0, 0);
//				graphics.endFill();
//			}
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			//			trace(e.type + " : " + e.images_set.Name);
			repaint();
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