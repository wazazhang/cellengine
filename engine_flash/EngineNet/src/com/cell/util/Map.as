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
			for each (var o : Object in this) { 
				delete this[o];
			}
		}
	}
}