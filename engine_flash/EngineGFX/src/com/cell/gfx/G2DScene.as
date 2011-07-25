package com.cell.gfx
{
	import com.cell.gameedit.OutputLoader;
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.ImagesSet;
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CCamera;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	
	import flash.display.Sprite;

	public class G2DScene extends Sprite
	{
		private var resource : ResourceLoader;
		
		public function G2DScene(res:ResourceLoader, world:WorldSet)
		{
			graphics.beginFill(0x00ff00, 1);
			graphics.drawRect(0, 0, world.Width, world.Height);
			graphics.endFill();
			
			this.resource = res;
			
			for each (var obj:SpriteObject in world.Sprs) {
				var unit : G2DSprite = createUnit(obj);
				unit.x = obj.X;
				unit.y = obj.Y;
				unit.getCSprite().setCurrentAnimate(obj.Anim);
				unit.getCSprite().setCurrentFrame(obj.Frame);
				addChild(unit);
			}
		}
		
		public function getResource() : ResourceLoader
		{
			return resource;
		}
		
		protected function createUnit(obj:SpriteObject) : G2DSprite {
			var cspr : CSprite = resource.getSprite(obj.SprID);
			var ret : G2DSprite = new G2DSprite(cspr);
			return ret;
		}
	}
	
	
}