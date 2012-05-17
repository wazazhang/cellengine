package com.cell.net.io
{
	import com.cell.util.Map;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public interface NetDataInput
	{
		
//		----------------------------------------------------------------------------------------------------
//		基础类型
//		----------------------------------------------------------------------------------------------------
		
		/**读取布尔值。*/
		function readBoolean():Boolean;
		
		/**读取带符号的字节。*/
		function readByte():int;
				
		
		/**读取 IEEE 754 双精度浮点数。*/
		function readDouble():Number;
		
		
		/**读取 IEEE 754 单精度浮点数。*/
		function readFloat():Number;
		
		
		/**读取带符号的 32 位整数。*/
		function readInt():int;
		
		
		/**读取带符号的 16 位整数。*/
		function readShort():int;
		
		
		/**读取无符号的字节。*/
		function readUnsignedByte():uint;
		
		
		/**读取无符号的 32 位整数。*/
		function readUnsignedInt():uint;
		
		
		/**读取无符号的 16 位整数。*/
		function readUnsignedShort():uint;
		
		
//		function readLong() : Number;
		
		function readChar() : int;
		
		function readJavaUTF() : String ;
		
		function readDate() : Date;
		
		function readEnum() : String;
		
//		----------------------------------------------------------------------------------------------------
//		复杂类型
//		----------------------------------------------------------------------------------------------------
		
		
		function readMutual() : MutualMessage;
		
		function readAny(component_data_type : int) : Object;
		
		
		
		function readBooleanArray() : Array;
		
		function readCharArray() : Array;
		
		function readByteArray() : Array;
		
		function readShortArray() : Array;
		
		function readIntArray() : Array;
		
		function readFloatArray() : Array;
		
		function readUTFArray() : Array;
	
		function readMutualArray() : Array;
		
		function readAnyArray(component_data_type : int) : Array ;
		
		
		function readCollection(compNetType:int) : Array;
		
		function readMap(keyNetType:int, valueNetType:int) : Map;

		
	}
}
