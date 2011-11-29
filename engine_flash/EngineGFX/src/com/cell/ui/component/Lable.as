package com.cell.ui.component
{
	import com.cell.ui.layout.UIRect;
	
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;

	public class Lable extends UIComponent
	{
		protected var textField : TextField;
		
		public function Lable(html:String)
		{
			textField = new TextField();
			textField.htmlText = html;
			textField.autoSize = TextFieldAutoSize.NONE;
			addChild(textField);
			super.setSize(100, 20);
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
		}
		
		public function getTextField() : TextField
		{
			return textField;
		}
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			if (super.resize(w, h, flush)) {
				textField.width	 = w;
				textField.height = h;
				return true;
			} else {
				textField.width	 = w;
				textField.height = h;
				return false;
			}
		}
		
		
	}
}