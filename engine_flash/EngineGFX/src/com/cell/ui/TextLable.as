package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.layout.UILayoutManager;
	
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	
	/**
	 * 在一个锚点上显示文字
	 * */
	public class TextLable extends CellSprite
	{
		protected var textField : TextField;
		
		private var _anchor : int = Anchor.ANCHOR_CENTER;
		
		public function TextLable(html:String)
		{
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.TextLable.text", this)
			textField.htmlText = html;
			textField.autoSize = TextFieldAutoSize.LEFT;
			addChild(textField);
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
			Anchor.setAnchorPos(textField, _anchor);
		}
		
		public function getTextField() : TextField
		{
			return textField;
		}
		
		
		public function set textAnchor(anchor:int):void
		{
			_anchor = anchor;
			Anchor.setAnchorPos(textField, _anchor);
		}
		
		public function get textAnchor() : int
		{
			return _anchor;
		}
		
		
	}
}