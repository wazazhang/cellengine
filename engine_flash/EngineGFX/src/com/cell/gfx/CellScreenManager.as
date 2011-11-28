package com.cell.gfx
{
	import com.cell.gfx.transition.AlphaTransition;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Rectangle;
	import flash.utils.getTimer;
	
	/**
	 * 屏幕管理类，游戏跟节点。
	 */
	public class CellScreenManager extends CellSprite
	{
		private var adapter 			: IScreenAdapter;
		
		private var current_screen		: CellScreen;
		
		private var update_time			: int = 0;
		
		private var frame_interval		: int = 1;
		
		private var next_screen_name	: String;
		private var next_screen_args	: Array;
		
		private var transition			: IScreenTransition ;
		private var transition_running	: Boolean = false;
		
		public function CellScreenManager(width:int, height:int, adapter:IScreenAdapter)
		{
			this.adapter = adapter;
//			this.addEventListener(Event.EXIT_FRAME, onLastUpdate);
			this.transition = new AlphaTransition(width, height, 0xffffff, 10);
			//this.mouseEnabled = false;
			this.scrollRect = new Rectangle(0, 0, width, height);
			this.graphics.beginFill(0x808080, 1);
			this.graphics.drawRect(0, 0, width, height);
			this.graphics.endFill();
		}
		
		override protected function update(e:Event) : void
		{
			var cur_time : int 	= getTimer();
			this.frame_interval	= cur_time - update_time;
			this.update_time 	= cur_time;
		
			if (current_screen != null) {
				current_screen._interval = frame_interval;
				current_screen.update();
			}
				
			if (next_screen_name != null) {
				tryChangeStage();
			}
			
			if (isTransition()) {
				if (contains(this.transition.asSprite())) {
					var index : int = this.getChildIndex(this.transition.asSprite())
					if (index != this.numChildren-1) {
						this.swapChildrenAt(index, this.numChildren-1);
					}
					this.transition.render(this);
				} else {
					addChild(this.transition.asSprite());
					trace("transition on : " + this.transition);
				}
			} else if (this.transition != null) {
				if (this.transition_running) {
					if (contains(this.transition.asSprite()) && next_screen_name == null) {
						removeChild(this.transition.asSprite());
						this.transition_running = false;
						trace("transition off : " + this.transition);
						this.current_screen.transitionCompleted();
					}
				}
			}
		}
		
//		protected function onLastUpdate(e:Event) : void
//		{
//			this.graphics.beginFill(0xffffff, 1);
//			this.graphics.drawRect(200, 300, 100, 100);
//			this.graphics.endFill();
//			
//		}
		
		/**设置切换屏幕时的特效*/
		public function setTransition(t:IScreenTransition) : void
		{
			this.transition = t;
		}
		
		/**获得切换屏幕时的特效*/
		public function getTransition() : IScreenTransition
		{
			return this.transition;
		}
		
		/**是否正在进入当前屏幕*/
		public function isTransitionIn() : Boolean
		{
			if (this.transition != null) {
				return this.transition.isTransitionIn();
			}
			return false;
		}
		
		/**只否正在离开当前屏幕*/
		public function isTransitionOut() : Boolean
		{
			if (this.transition != null) {
				return this.transition.isTransitionOut();
			}
			return false;
		}
		
		/**只否正在进入或离开当前屏幕*/
		public function isTransition() : Boolean
		{
			if (this.transition != null) {
				return this.transition.isTransitionIn() || this.transition.isTransitionOut();
			}
			return false;
		}
		
		protected function tryChangeStage() : void
		{
			// clear current stage
			if (current_screen!=null)
			{
				if (!isTransition())
				{
					this.removeChild(current_screen);
					current_screen.removed(this);
					current_screen = null;
				}
			}
			else
			{
				var args : Array = next_screen_args;
				var name : String = next_screen_name;
				this.next_screen_args	= null;
				this.next_screen_name	= null;

				this.current_screen = adapter.createScreen(this, name);
				trace("ChangeStage -> "+ current_screen.toString());	
				this.addChild(current_screen);
				this.current_screen.added(this, args);
				if (this.transition != null) {
					this.transition.startTransitionIn();
				}
			}
		}
		
		/**开始切换到下一屏幕
		 * @param screen_name 下一屏的名字，一般由IScreenAdapter提供。
		 * @param args 参数组
		 */
		public function changeScreen(screen_name:String, args:Array=null) : void
		{
			this.next_screen_name = screen_name;
			this.next_screen_args = args;
			if (this.transition != null) {
				this.transition.startTransitionOut();
				this.transition_running = true;
			}
		}
		
		public function getCurrentScreen() : CellScreen
		{
			return current_screen;
		}
		
		/**
		 * 获得当前帧和上一帧的时间间隔
		 */
		public function getInterval() : int
		{
			return frame_interval;
		}
		
		/**
		 * 获得当前帧和上一帧的时间间隔
		 */
		public function getFPS() : int
		{
			return 1000 / frame_interval;
		}
	}
}