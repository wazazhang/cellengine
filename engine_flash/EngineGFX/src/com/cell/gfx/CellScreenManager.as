package com.cell.gfx
{
	import com.cell.gfx.transition.AlphaTransition;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.filters.BitmapFilter;
	import flash.filters.GlowFilter;
	import flash.geom.Rectangle;
	import flash.text.TextField;
	import flash.utils.getTimer;
	
	/**
	 * 屏幕管理类，游戏跟节点。
	 */
	public class CellScreenManager extends CellSprite
	{
		private var current_screen		: CellScreen;
		
		private var update_time		: int = 0;
		
		private var frame_interval		: int = 1;
		
		private var next_screen_name	: Class;
		private var next_screen_args	: Array;
		
		private var transition			: IScreenTransition ;
		private var transition_running	: Boolean = true;
		
		private var m_width			: Number;
		private var m_height			: Number;
		
		private var fps_txt 			: TextField = new TextField();
		
		
		public function CellScreenManager(width:int, height:int)
		{
			this.m_width = width;
			this.m_height = height;
//			this.addEventListener(Event.EXIT_FRAME, onLastUpdate);
			this.transition = new AlphaTransition(width, height, 0xffffff, 10);
			//this.mouseEnabled = false;
//			this.scrollRect = new Rectangle(0, 0, width, height);
//			this.graphics.beginFill(0x808080, 1);
//			this.graphics.drawRect(0, 0, width, height);
//			this.graphics.endFill();
			this.fps_txt.visible = false;
			this.fps_txt.mouseEnabled = false;
			this.fps_txt.width = 100;
			this.fps_txt.height = 20;
			this.addChild(fps_txt);
			
			var tfilter:BitmapFilter = new GlowFilter(0xff8080ff);
			var tfilters:Array = new Array();
			tfilters.push(tfilter);
			this.fps_txt.filters = tfilters;
		}
		
		override public function get width() : Number
		{
			return m_width;
		}
		
		override public function get height() : Number
		{
			return m_height;
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
			} else if (this.transition_running) {
				if (this.transition != null) {
					if (contains(this.transition.asSprite()) && next_screen_name == null) {
						removeChild(this.transition.asSprite());
						trace("transition off : " + this.transition);
						this.transition_running = false;
						this.current_screen.transitionCompleted();
					}
				} else {
					this.transition_running = false;
					this.current_screen.transitionCompleted();
				}
			}
			if (fps_txt.visible) {
				fps_txt.text = "fps=" + getFPS();
				var index : int = getChildIndex(fps_txt);
				if (index != numChildren-1) {
					swapChildrenAt(index, numChildren-1);
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
		
		protected function onScreenChanged(screen:CellScreen) : void
		{
			
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
				var name : Class = next_screen_name;
				this.next_screen_args	= null;
				this.next_screen_name	= null;

				this.current_screen = new name() as CellScreen;
				trace("ChangeStage -> "+ current_screen.toString());	
				this.addChild(current_screen);
				this.current_screen.added(this, args);
				if (this.transition != null) {
					this.transition.startTransitionIn();
				}
				onScreenChanged(current_screen);
			}
		}
		
		/**开始切换到下一屏幕
		 * @param screen_name 下一屏的名字，一般由IScreenAdapter提供。
		 * @param args 参数组
		 */
		public function changeScreen(screen_class:Class, args:Array=null) : void
		{
			this.next_screen_name = screen_class;
			this.next_screen_args = args;
			if (this.transition != null) {
				this.transition.startTransitionOut();
			}			
			this.transition_running = true;
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
		
		public function showFPS(v:Boolean, color:uint=0xffffffff) : void {
			this.fps_txt.visible = v;
			this.fps_txt.textColor = color;
		}
		
	}
}