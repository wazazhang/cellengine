package com.cell.net.io
{
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public interface NetDataInput
	{
		
		/**从文件流、字节流或字节数组中读取布尔值。*/
		function readBoolean():Boolean;
		
		/**从文件流、字节流或字节数组中读取带符号的字节。*/
		function readByte():int;
				
		
		/**从文件流、字节流或字节数组中读取 IEEE 754 双精度浮点数。*/
		function readDouble():Number;
		
		
		/**从文件流、字节流或字节数组中读取 IEEE 754 单精度浮点数。*/
		function readFloat():Number;
		
		
		/**从文件流、字节流或字节数组中读取带符号的 32 位整数。*/
		function readInt():int;
		
		
		/**从文件流、字节流或字节数组中读取带符号的 16 位整数。*/
		function readShort():int;
		
		
		/**从文件流、字节流或字节数组中读取无符号的字节。*/
		function readUnsignedByte():uint;
		
		
		/**从文件流、字节流或字节数组中读取无符号的 32 位整数。*/
		function readUnsignedInt():uint;
		
		
		/**从文件流、字节流或字节数组中读取无符号的 16 位整数。*/
		function readUnsignedShort():uint;
		
		
		/**从文件流、字节流或字节数组中读取 UTF-8 字符串。*/
		function readUTF():String;
		
		
		
		
		
		
		
		
		
		function readLong() : Number;
		
		function readBooleanArray() : Array;
		
		function readCharArray() : Array;
		
		function readByteArray() : Array;
		
		function readShortArray() : Array;
		
		function readIntArray() : Array;
		
		function readFloatArray() : Array;
		
		function readUTFArray() : Array;
	
		function readExternalArray() : Array;
		
		function readExternal() : MutualMessage;
		
		function readChar() : int;
		
		function readJavaUTF() : String ;
		
		function readAnyArray(component_data_type : int) : Array ;
		
		function readAny(component_data_type : int) : Object;

		
		
	}
}
