package com.cell.ui
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.text.TextFieldType;

	public class Alert extends Sprite
	{
		public var text: TextField = new TextField();
		
		public function Alert()
		{
			text.mouseEnabled = false;
			text.type = TextFieldType.DYNAMIC;
			text.multiline = true;
			addChild(text);
		}
		
		public static function showAlert(text:String,
										 dst:DisplayObjectContainer, 
										 isHtml:Boolean=false,  
										 bd_color:int=0x000000, 
										 bd_alpha:Number = 0.5,
										 bg_color:int=0xffffff, 
										 bg_alpha:Number = 0.5) : Alert
		{
			var alert : Alert = new Alert();
			if (isHtml) {
				alert.text.htmlText = text;
			} else {
				alert.text.text = text;
			}
			
			alert.text.x = 16;
			alert.text.y = 16;
			alert.text.width = dst.width - 32;
			
			alert.graphics.beginFill(bd_color, bd_alpha);
			alert.graphics.drawRect(0, 0, dst.width, alert.text.height+32);
			alert.graphics.endFill();			
			
			alert.x = 0;
			alert.y = dst.height/2 - alert.height/2;
			
			
			Dialog.showAsDialog(alert, dst, bg_color, bg_alpha);
			return alert;
		}
	}
}