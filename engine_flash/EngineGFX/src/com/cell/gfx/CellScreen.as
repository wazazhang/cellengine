package com.cell.gfx
{
	import flash.display.Sprite;

	/**
	 * 游戏的舞台屏幕。
	 * */
	public class CellScreen extends Sprite
	{
		internal var _interval : int = 0;
		
		public function CellScreen()
		{
			
		}
		
		public function getParent() : CellScreenManager
		{
			return parent as CellScreenManager;
		}
		
		/**
		 * 添加到CellScreenManager里时被调用
		 * @param root 场景管理器
		 * @param args 由切换屏幕传递的参数
		 */
		public function added(root:CellScreenManager, args:Array) : void
		{
			
		}
		
		/**
		 * 被移除CellScreenManager时被调用
		 */
		public function removed(root:CellScreenManager) : void
		{
		
		}
		
		/**
		 */
		public function transitionCompleted() : void
		{
			
		}
		
		/**
		 * 获得当前帧和上一帧的时间间隔
		 */
		public function get interval() : int
		{
			return _interval;
		}
		
		
		/**
		 * 已添加到CellScreenManager里的当前屏幕，每帧更新一次。
		 */
		public function update() : void {
			
		}
		
	}
}