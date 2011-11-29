package com.cell.ui.component
{
	import flash.text.TextField;

	public class TextBox extends UIComponent
	{		
		protected var textField : TextField;

		public function TextBox(text:String)
		{			
			textField = new TextField();
			textField.text = text;
			textField.width = width;
			textField.height = height;
			addChild(textField);
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
		}
		
		public function setText(text:String):void
		{
			textField.text = text;
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