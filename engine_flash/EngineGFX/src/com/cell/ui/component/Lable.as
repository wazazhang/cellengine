package com.cell.ui.component
{
	import com.cell.ui.Anchor;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;

	public class Lable extends UIComponent
	{
		protected var textField : TextField;
	
		private var _anchor : int = Anchor.ANCHOR_CENTER;
		
		public function Lable(html:String)
		{
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.Lable", this));
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.component.Lable.text", this)
			textField.htmlText = html;
			textField.autoSize = TextFieldAutoSize.LEFT;
			textField.type = TextFieldType.DYNAMIC;
			addChild(textField);
			super.setSize(100, 20);
			Anchor.setAnchorRect(textField, _anchor, width, height);
			this.mouseChildren = false;
			this.mouseEnabled = false;
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
			//每次更新文本后，本文重新对齐
			textAnchor = _anchor;
		}
		
		public function getTextField() : TextField
		{
			return textField;
		}
		
		
		public function set textAnchor(anchor:int):void
		{
//			if (_anchor != anchor) {
				_anchor = anchor;
				Anchor.setAnchorRect(textField, _anchor, width, height);
//			}
		}
		
		public function get textAnchor() : int
		{
			return _anchor;
		}
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			if (super.resize(w, h, flush)) {
				Anchor.setAnchorRect(textField, _anchor, w, h);
				return true;
			}
			return false;
		}
		
		
	}
}