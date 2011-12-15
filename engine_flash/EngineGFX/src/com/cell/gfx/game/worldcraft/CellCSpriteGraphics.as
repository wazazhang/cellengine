package com.cell.gfx.game.worldcraft
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;

	public class CellCSpriteGraphics extends CellCSprite
	{
		private var _cg		: IGraphics;
		private var _old_anim : int = 0;
		private var _old_frame : int = 0;
		private var _repaint	: Boolean;
		
		public function CellCSpriteGraphics(spr:CSprite)
		{		
			this._spr = spr.copy();
			this._cg = new CGraphicsDisplay(graphics);			
			this.repaint();
			this.mouseEnabled = false;
			this.mouseChildren = false;
		}
		
		public function get cg() : IGraphics
		{
			return _cg;
		}
		
		public function repaint() : void
		{
			_repaint = true;
		}
		
		internal override function renderIn() : void 
		{
			if (_repaint || 
				_old_anim  != _spr.getCurrentAnimate() || 
				_old_frame != _spr.getCurrentFrame()) 
			{
				graphics.clear();
				render(_cg, 0, 0, _spr.getCurrentAnimate(), _spr.getCurrentFrame());
			}				
			_repaint 	= false;
			_old_anim  	= _spr.getCurrentAnimate();
			_old_frame 	= _spr.getCurrentFrame();
		}

		public function render(g:IGraphics, x:int, y:int, anim:int, frame:int) : void 
		{
			spr.render(g, x, y, anim, frame);
			spr.setTransform(this, _spr.getCurrentAnimate(), _spr.getCurrentFrame());
		}

//		------------------------------------------------------------------------------------------------------
		

	}
}