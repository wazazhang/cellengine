package com.cell.ui.component
{
	import flash.text.TextField;
	import flash.text.TextFieldType;

	public class TextInput extends UIComponent
	{
		protected var textField : TextField;
		
		public function TextInput(text:String)
		{			
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			textField = new TextField();
			textField.text = text;
			textField.width = width;
			textField.height = height;
			textField.type = TextFieldType.INPUT;
//			textField.alwaysShowSelection = true;

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
		
		public function getText() : String
		{
			return textField.text;
		}
		
		override protected function resize(w:int, h:int):Boolean
		{
			if (super.resize(w, h)) {
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