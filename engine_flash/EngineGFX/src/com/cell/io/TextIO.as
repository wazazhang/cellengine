package com.cell.io
{
	import com.cell.net.io.MutualMessage;
	
	import flash.utils.ByteArray;
	
	import mx.formatters.NumberFormatter;

	public class TextIO
	{
	
		public static function encode(data:ByteArray) : String
		{
			var old_p : int = data.position;
			var ret : String = "";
			for (var i : int = 0; i<data.length; i++) {
				var dn : String = data.readUnsignedByte().toString(16);
				if (dn.length==2) {
					ret += dn;
				} else {
					ret += "0" + dn;
				}
			}
			data.position = old_p;
			return ret;
		}
		

	}
}