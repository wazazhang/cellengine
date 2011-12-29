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
		
		public function putAll(map:Dictionary) : void
		{
			for (var key : Object in map) { 
				this[key] = map[key];
			}
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
			for (var key : Object in this) { 
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

		public function getPropertiesText() : String
		{
			var ret : String = "";
			for (var key : Object in this) { 
				ret += key + " = " + this[key] + "\n";
			}
			return ret;
		}
		
		
		public static function readFromProperties(properties:String, equalChar:String="=", showTrace:Boolean=false) : Map
		{
			var map : Map = new Map();
			
			var lines : Array = StringUtil.splitString(properties, "\n");
			
			var line : String = null;
			
			for (var i : int = 0; i < lines.length; i++)
			{
				try
				{
					if (line == null) {
						line = StringUtil.trim(lines[i]);
					} else {
						line += StringUtil.trim(lines[i]);
					}
					// 如果是注释 #
					if (StringUtil.trim(line).charAt(0) == '#'){
						line = null;
						continue;
					}
					// 如果是尾部出现 \
					if (i < lines.length - 1 && line.charAt(line.length-1) == '\\') {
						line = line.substring(0, line.length-1);
						continue;
					}
					var kv : Array = StringUtil.splitString(line, equalChar, 2, true);
					if (kv.length == 2) {
						map.put(kv[0], kv[1]);
//						trace(line);
					}		
					line = null;
				}
				catch(err:Error){
					trace(err.getStackTrace());
					line = null;
				}
			}
			
			return map;
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