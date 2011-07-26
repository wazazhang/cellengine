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
	}
}