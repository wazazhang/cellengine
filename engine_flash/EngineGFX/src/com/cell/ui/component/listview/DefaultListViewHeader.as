package com.cell.ui.component.listview
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Util;
	
	import flash.geom.Rectangle;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	
	public class DefaultListViewHeader extends UIComponent
	{
		protected var textField : TextField;
		
		public function DefaultListViewHeader(html:String)
		{
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.listview.ListViewHeader", this));
			textField = new TextField();
			textField.defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.cell.ui.component.listview.ListViewHeader.text", this);
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
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			if (super.resize(w, h, flush)) {
				this.scrollRect = new Rectangle(0, 0, w, h);
				Util.updateScrollRect(this);
				textField.x = w/2 - textField.width/2;
				textField.y = h/2 - textField.height/2;
				return true;
			} else {

				return false;
			}
		}
		
		
	}
}