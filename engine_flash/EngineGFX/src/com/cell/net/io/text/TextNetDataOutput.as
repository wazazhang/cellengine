package com.cell.net.io.text
{
	import com.cell.io.TextWriter;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataOutput;
	import com.cell.net.io.NetDataTypes;
	
	import flash.utils.ByteArray;
	
	public class TextNetDataOutput extends TextWriter implements NetDataOutput
	{
		protected var factory : MessageFactory ;
		
		public function TextNetDataOutput(factory : MessageFactory)
		{
			this.factory = factory;
		}
		
		
		
		
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		private function putNext(text:*) : void 
		{
			write(text+",");
		}
		
		private function putNextLen(text:String) : void
		{
			write(text.length+","+text+",");
		}
		
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		
		/**写入布尔值。*/
		public function writeBoolean(value:Boolean):void {
			putNext(value);
		}
		
		
		/**写入一个字节。*/
		public function writeByte(value:int):void{
			putNext(value);
		}
		
		
		/**写入 IEEE 754 双精度（64 位）浮点数。*/
		public function writeDouble(value:Number):void{
			putNext(value);
		}
		
		
		/**写入 IEEE 754 单精度（32 位）浮点数。*/
		public function writeFloat(value:Number):void{
			putNext(value);
		}
		
		
		/**写入一个带符号的 32 位整数。*/
		public function writeInt(value:int):void{
			putNext(value);
		}
		
		
		/**写入一个 16 位整数。*/
		public function writeShort(value:int):void{
			putNext(value);
		}
		
		
		/**写入一个无符号的 32 位整数。*/
		public function writeUnsignedInt(value:uint):void{
			putNext(value);
		}
		
		public function writeLong(v : Number) : void
		{
			putNext(v);
		}
		
		/**将 UTF-8 字符串写入文件流、字节流或字节数组中。*/
		public function writeUTF(value:String):void{
			putNextLen(value);
		}
		
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
		
		public function writeExternalArray(array : Array) : void
		{
			if (array != null) {
				this.writeInt(array.length);
				for each (var d : MutualMessage in array) {
					this.writeExternal(d);
				}
			} else {
				this.writeInt(0);
			}
		}
		
		
		public function writeExternal(data : MutualMessage) : void
		{
			if (data != null) {
				this.writeInt(factory.getType(data));
				factory.writeExternal(data, this);
			} else {
				this.writeInt(0);
			}
		}
		
		public function writeChar(data : int) : void
		{
			this.writeShort(data);
		}
		
		public function writeJavaUTF(data : String) : void
		{
			if (data != null) {
				this.writeUTF(data);
			} else {
				this.writeShort(0);
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
				case NetDataTypes.TYPE_EXTERNALIZABLE:
					writeExternal(obj as MutualMessage);
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
				case NetDataTypes.TYPE_LONG: 
					writeLong(obj as int);
					break;
				case NetDataTypes.TYPE_FLOAT: 
					writeFloat(obj as Number);
					break;
				case NetDataTypes.TYPE_DOUBLE: 
					writeDouble(obj as Number);
					break;
				case NetDataTypes.TYPE_STRING: 
					writeUTF(obj as String);
					break;
//				case NetDataTypes.TYPE_OBJECT: 
//					writeObject(obj);
//					break;
				default:
			}
		}
		
		
		
	}
}