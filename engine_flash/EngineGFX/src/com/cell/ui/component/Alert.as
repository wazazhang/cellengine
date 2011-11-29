package com.cell.ui.component
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.Anchor;
	import com.cell.ui.Dialog;
	import com.cell.ui.ImageButton;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Util;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;
	import flash.text.TextFormat;

	public class Alert extends UIComponent
	{
		private var GRID_SIZE	: int = UILayoutManager.getInstance().alertGridSize();
		
		private var _cancel 	: ImageButton;
		private var _ok 		: ImageButton;
		
		private var _title 		: TextField;
		private var _text 		: TextField;
		
		public var closeOnOK : Boolean = true;
		
		public function Alert(
			htmlText:String,
			htmlTitle:String,
			text:String,
			title:String,
			hasCancel:Boolean=true,
			hasOK:Boolean=false)
		{
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			
			if (htmlTitle != null || title != null) {
				_title = new TextField();
				_title.mouseEnabled = false;
				_title.type = TextFieldType.DYNAMIC;
				_title.multiline = false;
				_title.autoSize = TextFieldAutoSize.CENTER;
				setText(_title, htmlTitle, title);
				addChild(_title);
			}
			
			_text = new TextField();
			_text.mouseEnabled = false;
			_text.type = TextFieldType.DYNAMIC;
			_text.multiline = true;
			setText(_text, htmlText, text);
			addChild(_text);
			
			if (hasOK) {
				_ok = UILayoutManager.getInstance().alertCreateOK();
				_ok.anchor = Anchor.ANCHOR_RIGHT | Anchor.ANCHOR_BOTTOM;
				_ok.addEventListener(MouseEvent.CLICK, onMouseClick);
				this.addChild(_ok);
			}
			if (hasCancel) {
				_cancel= UILayoutManager.getInstance().alertCreateCancel();
				_cancel.anchor = Anchor.ANCHOR_RIGHT | Anchor.ANCHOR_BOTTOM;
				_cancel.addEventListener(MouseEvent.CLICK, onMouseClick);
				this.addChild(_cancel);
			}
			
			resize(300, 200, true);
		}
		
		private function setText(tf:TextField, html:String, text:String) : void
		{
			if (html) {
				tf.htmlText = html;
			} else {
				tf.text 	= text;
				tf.setTextFormat(UILayoutManager.getInstance().alertTextFormat());
			}
		}
		
		override protected function resize(w:int, h:int, flush:Boolean) : Boolean
		{
			if (super.resize(w, h, flush))
			{
				var btnx : int = bg.width  - GRID_SIZE;
				var btnh : int = 0;
				if (_ok != null) {
					_ok.x = btnx;
					_ok.y = bg.height - GRID_SIZE;
					btnx -= _ok.width + GRID_SIZE;
					btnh = _ok.height;
				}
				if (_cancel != null) {
					_cancel.x = btnx;
					_cancel.y = bg.height - GRID_SIZE;
					btnx -= _cancel.width + GRID_SIZE;
					btnh = _cancel.height;
				}
				
				var thy : int = GRID_SIZE;
				var thh : int = bg.height - GRID_SIZE - btnh;
				if (_title != null) {
					_title.x 		= GRID_SIZE;
					_title.width	= bg.width - GRID_SIZE - GRID_SIZE;
					_title.y 		= thy;
					thy += _title.height + GRID_SIZE;
					thh -= _title.height + GRID_SIZE;
				}
				this._text.x 		= GRID_SIZE;
				this._text.width  	= bg.width - GRID_SIZE - GRID_SIZE;
				this._text.y 		= thy;
				this._text.height 	= thh;
				return true;
			}
			return false;
		}
		
		public function get btnOK() : ImageButton
		{
			return _ok;
		}
		
		public function get btnCancel() : ImageButton
		{
			return _cancel;
		}
		
		
		
		private function onMouseClick(e:MouseEvent) : void
		{
			if (e.currentTarget == _ok && closeOnOK) {
				parent.removeChild(this);
			} 
			else if (e.currentTarget == _cancel) {
				parent.removeChild(this);
			}
		}
		
		override protected function update(e:Event):void
		{
			
		}
		
		public static function showAlertText(text:String, 
											 title:String = null,
											 hasCancel:Boolean=true,
											 hasOK:Boolean=false) : Alert
		{
			var alert : Alert = new Alert(null, null, text, title, hasCancel, hasOK);
			
			alert.x = UILayoutManager.getInstance().ScreenWidth/2  - alert.bg.width/2;
			alert.y = UILayoutManager.getInstance().ScreenHeight/2 - alert.bg.height/2;
			
			Dialog.showAsDialog(
				alert, 
				UILayoutManager.getInstance().getRoot().getCurrentScreen());
			return alert;
		}
		
		public static function showAlertHtml(htmlText:String, 
										 htmlTitle:String = null,
										 hasCancel:Boolean=true,
										 hasOK:Boolean=false) : Alert
		{
			var alert : Alert = new Alert(htmlText, htmlTitle, null, null, hasCancel, hasOK);
			
			alert.x = UILayoutManager.getInstance().ScreenWidth/2  - alert.bg.width/2;
			alert.y = UILayoutManager.getInstance().ScreenHeight/2 - alert.bg.height/2;
			
			Dialog.showAsDialog(
				alert, 
				UILayoutManager.getInstance().getRoot().getCurrentScreen());
			return alert;
		}
		
	}
}