package com.cell.gfx.rpg
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;

	public class G2DCSprite extends G2DUnit
	{
		public static var DEBUG : Boolean = false;
		
		private var spr		: CSprite;
		private var cg		: IGraphics;
		
		public function G2DCSprite(spr:CSprite)
		{
			this.cg = new CGraphicsDisplay(graphics);			
			this.spr = spr;
			this.repaint();
		}
		
		public function get src() : CSprite
		{
			return spr;
		}
		
		public function getCSprite() : CSprite
		{
			return spr;
		}
		
		public function repaint() : void
		{
			graphics.clear();
			spr.render(cg, 0, 0, spr.getCurrentAnimate(), spr.getCurrentFrame());
			graphics.moveTo( 0, 0);
			if (DEBUG) {
				graphics.lineStyle(1, 0xffff0000, 1);
				graphics.lineTo(-8, 0); graphics.moveTo( 0, 0);
				graphics.lineTo( 8, 0); graphics.moveTo( 0, 0);
				graphics.lineTo( 0,-8); graphics.moveTo( 0, 0);
				graphics.lineTo( 0, 8); graphics.moveTo( 0, 0);
				graphics.endFill();
			}
		}
		
		
	}
}