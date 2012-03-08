package com.cell.ui.component
{
	import com.cell.ui.layout.UILayoutManager;
	
	import flash.text.TextField;

	public class TextBox extends UIComponent
	{		
		protected var textField : TextField;

		public function TextBox()
		{			
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.TextBox", this));
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.component.TextBox.text", this);
			textField.multiline = true;
			textField.wordWrap 	= true;
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