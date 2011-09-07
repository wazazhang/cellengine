package com.cell.ui
{
	import com.cell.io.UrlManager;
	
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class TouchToggleButton extends Sprite
	{
		private var img_chk_next1 : DisplayObject;
		private var img_chk_next2 : DisplayObject;

		private var value : Boolean = false;
		
		public function TouchToggleButton(unsel:DisplayObject, sel:DisplayObject)
		{
			img_chk_next1 = unsel;
			img_chk_next2 = sel;
			img_chk_next1.visible = !value;
			img_chk_next2.visible = value;
			this.addEventListener(MouseEvent.CLICK, onMouseClick);
			addChild(img_chk_next1);
			addChild(img_chk_next2);
		}
		
		public function setSelected(v:Boolean) : void
		{
			value = v;
			img_chk_next1.visible = !value;
			img_chk_next2.visible = value;
		}
		
		public function getSelected() : Boolean
		{
			return value;
		}
		
		private function onMouseClick(e:Event):void
		{
			value = !value;
			img_chk_next1.visible = !value;
			img_chk_next2.visible = value;
		}		
		
		public static function createTouchToggleButton(up_url:String, down_url:String) : TouchToggleButton
		{
			var udl : Loader = new Loader();
			udl.load(UrlManager.getUrl(up_url));
			var ddl : Loader = new Loader();
			ddl.load(UrlManager.getUrl(down_url));
			return new TouchToggleButton(udl, ddl);
		}
		
		public static function createTouchToggleButtonClass(up_c:Class, down_c:Class) : TouchToggleButton
		{
			return new TouchToggleButton(new up_c() as DisplayObject, new down_c() as DisplayObject);
		}
	}
}