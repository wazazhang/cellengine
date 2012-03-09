package com.cell.net.io
{
	import com.cell.util.Map;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataOutput;
	
	public interface NetDataOutput
	{
		
		/**写入布尔值。*/
		function writeBoolean(value:Boolean):void;
		
		
		/**写入一个字节。*/
		function writeByte(value:int):void;
		
		
		/**写入 IEEE 754 双精度（64 位）浮点数。*/
		function writeDouble(value:Number):void;
		
		
		/**写入 IEEE 754 单精度（32 位）浮点数。*/
		function writeFloat(value:Number):void;
		
		
		/**写入一个带符号的 32 位整数。*/
		function writeInt(value:int):void;
		
		
		/**写入一个 16 位整数。*/
		function writeShort(value:int):void;
		
		
		/**写入一个无符号的 32 位整数。*/
		function writeUnsignedInt(value:uint):void;
		
		
//		function writeLong(v : Number) : void;
		
		function writeChar(data : int) : void;
		
		
		/**将 UTF-8 字符串写入文件流、字节流或字节数组中。*/
		function writeJavaUTF(value:String):void;
		
		
		function writeDate(date:Date) : void;
		
		
		
		
		
		
		

		
		
		function writeBooleanArray(array : Array) : void;
		
		function writeCharArray(array : Array) : void;
		
		function writeByteArray(array : Array) : void;
		
		function writeShortArray(array : Array) : void;
		
		function writeIntArray(array : Array) : void;
		
		function writeFloatArray(array : Array) : void;
		
		function writeUTFArray(array : Array) : void;
		
		function writeMutualArray(array : Array) : void;
		
		function writeMutual(data : MutualMessage) : void;
		
		
		
		function writeAnyArray(array : Array, component_data_type : int) : void;

		function writeAny(component_data_type : int, obj : Object) : void;
		
		
		
		function writeCollection(array:Array, compNetType:int) : void;
		
		function writeMap(map:Map, keyNetType:int, valueNetType:int) : void;
	}
}