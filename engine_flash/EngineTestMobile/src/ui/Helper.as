package ui
{
	import com.qq.openapi.*;
	import flash.display.DisplayObject;

	public class Helper
	{
		public static var root:DisplayObject = null;
		public static function get appid():String
		{
			return "3";
		}

		public static function set message(mes:String):void
		{
			root["content"].text = mes;			
		}

		public static function initialize():void
		{
			if (ApiTool.initialize(Helper.root) == false)
			{
				return ;
			}
		}
	};
}
