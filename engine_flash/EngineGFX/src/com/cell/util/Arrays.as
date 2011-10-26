package com.cell.util
{
	public class Arrays
	{
		
		
		
		public static function arrayCopy(
			src:Array, src_pos:uint, 
			dst:Array, dst_pos:uint, 
			length:uint) : void {
			for (var i:int=0; i<length; i++) {
				dst[dst_pos+i] = src[src_pos+i];
			}
		}
		
		public static function arrayFirstIndexOf(array:Array, value:*) : int
		{
			for (var i:int=0; i<array.length; i++) {
				if (array[i] == value) {
					return i;
				}
			}
			return -1;
		}
		
		public static function arrayEquals(array1:Array, array2:Array) : Boolean
		{
			if (array1 == array2)
				return true;
			
			if ( ( (array1==null)&&(array2!=null) )
				|| ((array1!=null)&&(array2==null)) )
				return false;
			
			if (array1.length != array2.length)
				return false;
			
			for (var i:int = 0; i < array1.length; i++) {
				if (array1[i] != array2[i]) {
					return false;
				}
			}
			return true;
		}
		
		public static function newArray2D(r:uint, c:uint=0) : Array
		{
			var ret : Array = new Array(r);
			for (var i : uint = 0; i < r;  i ++) {
				ret[i] = new Array(c);
			}
			return ret;
		}
		
		/**
		 * 返回目标数组在源数组的包含部分
		 * */
		public static function intersectionSet(src:Array, dst:Array) : Array
		{
			var ret : Array = new Array();
			for each(var d : Object in dst) {
				if (src.indexOf(d) >= 0 && ret.indexOf(d) < 0) {
					ret.push(d);
				}
			}
			return ret;
		}
		
		/**
		 * 返回目标数组不存在源数组中的部分
		 * */
		public static function unionSet(src:Array, dst:Array) : Array
		{
			var ret : Array = new Array();
			for each(var d : Object in dst) {
				if (src.indexOf(d) < 0 && ret.indexOf(d) < 0) {
					ret.push(d);
				}
			}
			return ret;
		}
		
		/**
		 * 不同出现相同元素的集合
		 */
		public static function toSet(src:Array) : Array
		{
			var ret : Array = new Array();
			for each(var s : Object in src) {
				if (ret.indexOf(s) < 0) {
					ret.push(s);
				}
			}
			return ret;
		}
	}
}