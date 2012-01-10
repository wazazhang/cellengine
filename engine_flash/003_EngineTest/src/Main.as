package
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.gfx.IScreenAdapter;
	
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.gfx.IScreenAdapter;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	
	import flash.events.Event;
	
	import flash.display.Sprite;
	import flash.events.Event;
	
	[SWF(width="480", height="320", frameRate="24")]
	public class Main extends Sprite
	{
		var res : ResourceLoader = new ResourceLoader("edit/output/CakeQQ.xml");
		
		var sprite : CellCSpriteBuffer;
		
		public function Main()
		{
			res.addEventListener(ResourceEvent.LOADED, onLoaded);
			res.load();	
		}
		
		
		private function onLoaded(e:Event) : void
		{
			sprite = new CellCSpriteBuffer(res.getSpriteBuffer("Actor"));
			sprite.setCurrentAnimateName("walk");
			addChild(sprite);
			// 监听每帧事件
			addEventListener(Event.ENTER_FRAME, onUpdate);
		}
		
		// 每帧调用一次
		private function onUpdate(e:Event) : void
		{
			sprite.renderSelf();
			sprite.nextCycFrame();
			sprite.x = mouseX;
			sprite.y = mouseY;
		}
		
	}
}