package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;

	/**
	 * 在一个矩形框里显示文字
	 * */
	public class TextPan extends CellSprite
	{
		protected var textField : TextField;
		
		private var _anchor : int = Anchor.ANCHOR_CENTER;
		
		public function TextPan(html:String)
		{
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.TextPan.text", this)
			textField.htmlText = html;
			textField.autoSize = TextFieldAutoSize.LEFT;
			addChild(textField);
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
		}
		
		public function getTextField() : TextField
		{
			return textField;
		}
		
		public function get textAnchor() : int
		{
			return _anchor;
		}
		
		public function resize(w:int, h:int, anchor:int=Anchor.ANCHOR_CENTER):void
		{
			_anchor = anchor;
			Anchor.setAnchorRect(textField, _anchor, w, h);
		}
		
		
	}
}