package 
{
    import com.cell.gameedit.ResourceEvent;
    import com.cell.gameedit.ResourceLoader;
    import com.cell.gfx.CellScreen;
    import com.cell.gfx.CellScreenManager;
    import com.cell.gfx.IScreenAdapter;
    import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
    import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
    
    import flash.display.Sprite;
    import flash.display.StageAlign;
    import flash.display.StageScaleMode;
    import flash.events.Event;
    import flash.events.MouseEvent;
    
    import starling.core.Starling;
    
    [SWF(width="800", height="480", frameRate="60", backgroundColor="#222222")]
    public class Startup extends Sprite
    {
        private var mStarling:Starling;
		
		private var res : ResourceLoader = new ResourceLoader("edit/output/CakeQQ.xml");
		
		private var sprite : CellCSpriteBuffer;
		
        public function Startup()
        {
            stage.scaleMode = StageScaleMode.NO_SCALE;
            stage.align = StageAlign.TOP_LEFT;
            
            Starling.multitouchEnabled = true; // useful on mobile devices
            Starling.handleLostContext = true; // deactivate on mobile devices (to save memory)
            
            mStarling = new Starling(Game, stage);
            mStarling.simulateMultitouch = true;
            mStarling.enableErrorChecking = false;
            mStarling.start();
            
            // this event is dispatched when stage3D is set up
            stage.stage3Ds[0].addEventListener(Event.CONTEXT3D_CREATE, onContextCreated);
			
			
			{
				this.graphics.beginFill(0x808080, 1);
				this.graphics.drawRect(0, 0, width, height);
				this.graphics.endFill();
				
				res.addEventListener(ResourceEvent.LOADED, onLoaded);
				res.load();	
				
				// 监听每帧事件
				this.mouseEnabled = true;
				addEventListener(Event.ENTER_FRAME, onUpdate);
				addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
			}
        }
        
		
        private function onContextCreated(event:Event):void
        {
            // set framerate to 30 in software mode
            
            if (Starling.context.driverInfo.toLowerCase().indexOf("software") != -1)
                Starling.current.nativeStage.frameRate = 30;
        }
		
	
		private function onLoaded(e:Event) : void
		{
			sprite = new CellCSpriteBuffer(res.getSpriteBuffer("Actor"));
			sprite.setCurrentAnimateName("walk");
			addChild(sprite);
		}
		
		// 每帧调用一次
		private function onUpdate(e:Event) : void
		{
			if (sprite != null) 
			{
				sprite.renderSelf();
				sprite.nextCycFrame();
				sprite.x = mouseX;
				sprite.y = mouseY;
			}
		}
		
		private function onMouseDown(e:MouseEvent) : void
		{
			if (sprite != null) 
			{
				sprite.renderSelf();
				sprite.nextCycFrame();
				sprite.x = mouseX;
				sprite.y = mouseY;
			}
		}
    }
}