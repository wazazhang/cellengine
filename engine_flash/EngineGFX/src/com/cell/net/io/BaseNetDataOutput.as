package com.cell.net.io
{
	import com.cell.util.Map;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataOutput;
	
	public class BaseNetDataOutput implements NetDataOutput
	{
		protected var factory : MessageFactory ;
		
		public function BaseNetDataOutput(factory : MessageFactory)
		{
			this.factory = factory;
		}
		
		
		/**写入布尔值。*/
		public function writeBoolean(value:Boolean):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入一个字节。*/
		public function writeByte(value:int):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入 IEEE 754 双精度（64 位）浮点数。*/
		public function writeDouble(value:Number):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入 IEEE 754 单精度（32 位）浮点数。*/
		public function writeFloat(value:Number):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入一个带符号的 32 位整数。*/
		public function writeInt(value:int):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入一个 16 位整数。*/
		public function writeShort(value:int):void{
			throw new Error("Not impl in abstract class!");
		}
		
		/**写入一个无符号的 32 位整数。*/
		public function writeUnsignedInt(value:uint):void{
			throw new Error("Not impl in abstract class!");
		}
				
//		public function writeLong(v : Number) : void{
//			throw new Error("Not impl in abstract class!");
//		}
		
		public function writeChar(data : int) : void{
			throw new Error("Not impl in abstract class!");
		}
		
		public function writeJavaUTF(data : String) : void{
			throw new Error("Not impl in abstract class!");
		}
		
		
		public function writeDate(date : Date) : void
		{
			if (date == null)
			{
				writeByte(0);
			}
			else 
			{
				writeByte(1);
				
				writeShort(date.fullYear);
				writeByte(date.month);
				writeByte(date.date);
				
				writeByte(date.hours);
				writeByte(date.minutes);
				writeByte(date.seconds);
				
				writeShort(date.milliseconds);
			}
			
			
		}
		
		public function writeEnum(e:String) : void
		{
			writeJavaUTF(e);
		}
		
		
		
		public function writeBooleanArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : Boolean in array) {
					this.writeBoolean(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeCharArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : int in array) {
					this.writeShort(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeByteArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : int in array) {
					this.writeByte(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeShortArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : int in array) {
					this.writeShort(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeIntArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : int in array) {
					this.writeInt(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeFloatArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : Number in array) {
					this.writeFloat(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeUTFArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : String in array) {
					this.writeJavaUTF(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeMutualArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : MutualMessage in array) {
					this.writeMutual(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		
		public function writeMutual(data : MutualMessage) : void
		{
			if (data != null) {
				this.writeInt(factory.getType(data));
				factory.writeExternal(data, this);
			} else {
				this.writeInt(0);
			}
		}
	
		
		
		
		
		public function writeAnyArray(array : Array, component_data_type : int) : void {
			if (array != null) {
				var count : int = array.length;
				if (count > 0) {
					if (array[0] is Array) {
						writeInt(-count); 	// 表示成员还是个数组
						for (var i : int = 0; i < count; i++) {
							writeAnyArray(array[i], component_data_type);
						}
					} else {
						writeInt(count);	// 表示成员是个通常对象
						for (var i : int = 0; i < count; i++) {
							writeAny(component_data_type, array[i]);
						}
					}
				} else {
					writeInt(0);
				}
			} else {
				writeInt(0);
			}
		}
		
		public function writeAny(component_data_type : int, obj : Object) : void
		{
			switch (component_data_type) {
				case NetDataTypes.TYPE_MUTUAL:
					writeMutual(obj as MutualMessage);
					break;
				case NetDataTypes.TYPE_BOOLEAN:
					writeBoolean(obj as Boolean);
					break;
				case NetDataTypes.TYPE_BYTE:
					writeByte(obj as int);
					break;
				case NetDataTypes.TYPE_CHAR: 
					writeChar(obj as int);
					break;
				case NetDataTypes.TYPE_SHORT: 
					writeShort(obj as int);
					break;
				case NetDataTypes.TYPE_INT: 
					writeInt(obj as int);
					break;
//				case NetDataTypes.TYPE_LONG: 
//					writeLong(obj as int);
//					break;
				case NetDataTypes.TYPE_FLOAT: 
					writeFloat(obj as Number);
					break;
				case NetDataTypes.TYPE_DOUBLE: 
					writeDouble(obj as Number);
					break;
				case NetDataTypes.TYPE_STRING: 
					writeJavaUTF(obj as String);
					break;
				//				case NetDataTypes.TYPE_OBJECT: 
				//					writeObject(obj);
				//					break;
				default:
			}
		}
		

		public function writeCollection(array:Array, compNetType:int) : void
		{
			if (array != null) {
				var count : int = array.length;
				writeInt(count);
				if (count > 0) {
					for each (var o : * in array) {
						writeAny(compNetType, o);
					}
				}
			} else {
				writeInt(0);
			}
		}
		
		
		public function writeMap(map:Map, keyNetType:int, valueNetType:int) : void
		{
			if (map != null) {
				var count : int = map.size();
				writeInt(count);
				if (count > 0) {
					for (var key : * in map) {
						var value : * = map.get(key);
						writeAny(keyNetType,   key);
						writeAny(valueNetType, value);
					}
				}
			} else {
				writeInt(0);
			}
		}
	}
}