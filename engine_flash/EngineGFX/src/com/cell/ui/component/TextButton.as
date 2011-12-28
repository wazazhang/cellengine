package com.cell.ui.component
{
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.geom.Rectangle;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;

	
	public class TextButton extends UIComponent
	{
		protected var textField 	: TextField;
		
		protected var baseButton 	: ImageButton;
		
		protected var btn_unsel		: UIRect;
		protected var btn_sel		: UIRect;
		
		public function TextButton(html:String, w:int=200, h:int=30)
		{
			super(null);
			
			btn_unsel 	= UILayoutManager.getInstance().createUI("com.cell.ui.component.TextButton.up", this);
			btn_sel   	= UILayoutManager.getInstance().createUI("com.cell.ui.component.TextButton.down", this);
			baseButton 	= new ImageButton(btn_unsel, btn_sel);
			baseButton.mouseEnabled = true;
			addChild(baseButton);
			
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.component.TextButton.text", this);
			textField.htmlText = html;
			textField.autoSize = TextFieldAutoSize.NONE;
			textField.mouseEnabled = false;
			addChild(textField);
			
			this.resize(w, h, true);
			
			this.mouseChildren = true;
		}
		
		public function setHTMLText(html:String):void
		{
			textField.htmlText = html;
//			textField.x = width/2  - textField.textWidth/2;
//			textField.y = height/2 - textField.textHeight/2;
		}
		
		public function getTextField() : TextField
		{
			return textField;
		}
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			if (super.resize(w, h, flush)) {
				this.scrollRect = new Rectangle(0, 0, w, h);
				var bmpData:BitmapData = new BitmapData(1, 1);
				bmpData.draw(this);
				btn_unsel.createBuffer(w, h);
				btn_sel.createBuffer(w, h);
				textField.x = w/2 - textField.textWidth/2;
				textField.y = h/2 - textField.textHeight/2;
				return true;
			} else {
				return false;
			}
		}
		
		
	}

	
}