package com.cell.ui.component
{
	import com.cell.ui.layout.UILayoutManager;
	
	import flash.text.TextField;
	import flash.text.TextFieldType;

	public class TextInput extends UIComponent
	{
		protected var textField : TextField;
		
		public function TextInput(text:String)
		{			
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.TextInput", this));
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.component.TextInput.text", this);
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
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			var bd1 : int = bg.getClipBorder();
			var bd2 : int = bd1*2;
			
			if (super.resize(w, h, flush)) {
				textField.x = bd1;
				textField.y = bd1;
				textField.width	 = w - bd2;
				textField.height = h - bd2;
				return true;
			} else {
				textField.x = bd1;
				textField.y = bd1;
				textField.width	 = w - bd2;
				textField.height = h - bd2;
				return false;
			}
		}
		
		
	}
}