package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;

	public class SimpleProgress extends CellSprite
	{
		private var loading_text	: TextField;		
		
		private var progress 		: Sprite = new Sprite();
				
		private var _percent 		: Number = 0;
		
		private var _ellipse_size	: int = 8;
		
		private var _border_size	: int = 2;
		
		private var _border_color	: int = 0x404040;
		private var _back_color		: int = 0x808080;
		private var _strip_color	: int = 0x00ff00;
		
		public function SimpleProgress(width:int, height:int, ellipse:int=8, bordersize:int=2)
		{
			this._ellipse_size = ellipse;
			this._border_size  = bordersize;
			
			addChild(progress);
			
			loading_text = new TextField();
			loading_text.mouseEnabled = false;
			loading_text.autoSize = TextFieldAutoSize.CENTER;
			addChild(loading_text);
			
			repaint(width, height, 0);
		}
		
		override protected function update(e:Event):void
		{
			
			
		}
		
		public function setColor(border_color:int, back_color:int, strip_color:int) : void
		{
			this._border_color	= border_color;
			this._back_color	= back_color;
			this._strip_color	= strip_color;
		}
		
		public function get percent() : Number
		{
			return _percent;
		}
		
		public function set percent(v:Number) : void
		{
			if (_percent != v) {
				_percent = v;
				repaint(width, height, v);
			}
		}
		
		override public function set height(value:Number):void
		{
			repaint(width, value, _percent);
			super.height = value;
		}
		
		override public function set width(value:Number):void
		{
			repaint(value, height, _percent);
			super.width = value;
		}
		
		public function set htmlText(html:String) : void
		{
			this.loading_text.htmlText = html;
		}
		
		
		private function repaint(width:int, height:int, percent:Number) : void
		{
			var sw : int = width - _border_size*2;
			var sh : int = height - _border_size*2;
			var pw : int = sw*percent;
			
			progress.graphics.clear();	
			
			if (_border_size>0) {
				progress.graphics.beginFill(_border_color, 1);
				progress.graphics.drawRoundRect(0, 0, width, height, _ellipse_size);
				progress.graphics.endFill();
			}
			
			progress.graphics.beginFill(_back_color, 1);
			progress.graphics.drawRoundRect(_border_size, _border_size, sw, sh, _ellipse_size);
			progress.graphics.endFill();
			
			progress.graphics.beginFill(_strip_color, 1);
			progress.graphics.drawRoundRect(_border_size, _border_size, pw, sh, _ellipse_size);
			progress.graphics.endFill();
			
			loading_text.x = 0;
			loading_text.y = 0;
			loading_text.width = width;
			loading_text.height = height;
		}
		
	}
}