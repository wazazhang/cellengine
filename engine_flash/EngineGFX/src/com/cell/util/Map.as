package com.cell.util
{
	import flash.utils.Dictionary;

	public dynamic class Map extends Dictionary
	{
		public function Map()
		{
		}
		
		public function clear() : void
		{
			for (var key : Object in this) { 
				delete this[key];
			}
		}
		
		public function contains(key:*) : Boolean {
			return this[key] != null;
		}
		
		public function put(key:*, value:*) : void
		{
			this[key] = value;
		}
		
		public function get(key:*) : *
		{
			return this[key];
		}
		
		public function remove(key:*) : *
		{
			return delete[key];
		}
		
		public function removeValue(value:*) : void
		{
			for (var key : Object in this) { 
				if (this[key] == value) {
					delete[key];
				}
			}
		}
		
		public function size() : int
		{
			var ret:int = 0;
			for (var key : Object in this) { 
				ret++;
			}
			return ret;
		}
		
		
		public function keys() : Array
		{
			var ret : Array = new Array();
			for (var key : Object in this) { 
				ret.push(key);
			}
			return ret;
		}
		
		public function values() : Array
		{
			var ret : Array = new Array();
			for (var key : Object in this) { 
				ret.push(this[key]);
			}
			return ret;
		}
		
		public function getMapSet(keys:Array) : Map
		{
			var ret : Map = new Map();
			for each(var key : Object in keys) { 
				var v : * = this[key];
				if (v != null) {
					ret.put(key, v);
				}
			}
			return ret;
		}
		
//		------------------------------------------------------------------------------------
		
		public function getNumber(key:String, defaultValue:Number) : Number
		{
			var value : String = this.get(key);
			if (value != null) {
				try {
					var ret : Number = Number(value);
					return ret;
				} catch(e : Error) {
					trace(e);
				}
			} 
			return defaultValue;
		}
		
//		------------------------------------------------------------------------------------

		public static function readFromProperties(properties:String, equalChar:String="=") : Map
		{
			var lines : Array = StringUtil.splitString(properties, "\n");
			return readFromLines(lines, equalChar);
		}
		
		public static function readFromLines(lines:Array, equalChar:String="=") : Map
		{
			var map : Map = new Map();
			for each (var line : String in lines) {
				var kv : Array = StringUtil.splitString(line, equalChar, 2, true);
				if (kv.length==2) {
					map.put(kv[0], kv[1]);	
				}
			}
			return map;
		}
		
		/**<pre>
		 * [key, value]
		 * [key, value] 
		 */
		public static function initFromArray(arr:Array) : Map
		{
			var ret : Map = new Map();
			
			for each (var a : * in arr) 
			{
				ret[a[0]] = a[1];
			}
			
			return ret;
		}
	}
}