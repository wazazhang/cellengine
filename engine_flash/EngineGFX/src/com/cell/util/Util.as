package com.cell.util
{
	import flash.utils.ByteArray;

	public class Util
	{
		public static function dumpHex(data : ByteArray) : String
		{
			var pos : int = data.position;
			data.position = 0;
			var ret : String = "";
			for (var i : int = 0; i<data.length; i++) {
				var dn : String = (0x1000 | data.readByte()).toString(16);
				ret += dn.substr(2,2) + " ";
			}
			data.position = pos;
			return ret;
		}
	}
}