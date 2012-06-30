package com.cell.gfx.rpg
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;

	public class G2DMapObject extends G2DUnit
	{
		private var buff : CellCSpriteBuffer;
		
		public function G2DMapObject(spr:CSpriteBuffer, obj:SpriteObject)
		{
			this.buff = new CellCSpriteBuffer(spr);
			this.buff.setCurrentAnimate(obj.Anim);
			this.buff.setCurrentFrame(obj.Frame);
			this.buff.renderSelf();
			this.addChild(buff);
			this.x = obj.X;
			this.y = obj.Y;
		}
		
		override public function update() : void {
			this.buff.nextCycFrame();
			this.buff.renderSelf();
		}
	}
}