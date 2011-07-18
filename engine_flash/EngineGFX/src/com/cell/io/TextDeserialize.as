package com.cell.io
{
	public class TextDeserialize
	{
		private static function getNext(input:TextReader) : String 
		{
			var sb : String = "";
			while (true) {
				var r : String = input.read();
				if (r == null) 
					break;
				if (r == ",") 
					break;
				sb += r;
			}
			return sb;
		}
		
		public static function getString(input:TextReader) : String 
		{
			var stringLen : int = getInt(input);
			var ret : String = input.readLen(stringLen);
			if (input.read() != ',') {
				trace("TextDeserialize : bad end with getString");
			}
			return ret;
		}
		
		public static function getBoolean(input:TextReader) : Boolean
		{
			return Boolean(getNext(input));
		}
		
		public static function getInt(input:TextReader) : int
		{
			return int(getNext(input));
		}
		
		public static function getDouble(input:TextReader) : Number
		{
			return Number(getNext(input));
		}
	
		//////////////////////////////////////////////////////////////////////////////////////
		// get array
		
		public static function getStrings(input:TextReader) : Array
		{
			var count : int = getInt(input);
			if (count > 0) {
				var values : Array = new Array(count);
				for (var i:int=0; i<count; ++i )
					values[i] = getString(input);
				return values;
			}
			return null;
		}
		
		public static function getBooleans(input:TextReader) : Array
		{
			var count : int = getInt(input);
			if (count > 0) {
				var values : Array = new Array(count);
				for (var i:int=0; i<count; ++i )
					values[i] = getBoolean(input);
				return values;
			}
			return null;
		}	
		
		public static function getInts(input:TextReader) : Array
		{
			var count : int = getInt(input);
			if (count > 0) {
				var values : Array = new Array(count);
				for (var i:int=0; i<count; ++i )
					values[i] = getInt(input);
				return values;
			}
			return null;
		}
		
		public static function getDoubles(input:TextReader) : Array
		{
			var count : int = getInt(input);
			if (count > 0) {
				var values : Array = new Array(count);
				for (var i:int=0; i<count; ++i )
					values[i] = getDouble(input);
				return values;
			}
			return null;
		}
	
	
		
	}

}