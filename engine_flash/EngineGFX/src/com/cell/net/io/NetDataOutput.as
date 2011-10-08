package com.cell.net.io
{
	import flash.utils.ByteArray;
	
	public class NetDataOutput extends ByteArray
	{
		protected var factory : MessageFactory ;
		
		public function NetDataOutput(factory : MessageFactory)
		{
			this.factory = factory;
		}
		
		public function writeLong(v : Number) : void
		{
			var l : int = v & 0xffffffff;
			var h : int = (v>>32) & 0xffffffff;
			super.writeInt(h);
			super.writeInt(l);
		}
		
		public function writeBooleanArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : Boolean in array) {
					super.writeBoolean(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeCharArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : int in array) {
					super.writeShort(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeByteArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : int in array) {
					super.writeByte(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeShortArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : int in array) {
					super.writeShort(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeIntArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : int in array) {
					super.writeInt(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeFloatArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : Number in array) {
					super.writeFloat(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeUTFArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : String in array) {
					this.writeJavaUTF(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeExternalArray(array : Array) : void
		{
			if (array != null) {
				super.writeInt(array.length);
				for each (var d : MutualMessage in array) {
					this.writeExternal(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		
		public function writeExternal(data : MutualMessage) : void
		{
			if (data != null) {
				super.writeInt(factory.getType(data));
				factory.writeExternal(data, this);
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeChar(data : int) : void
		{
			super.writeShort(data);
		}
		
		public function writeJavaUTF(data : String) : void
		{
			if (data != null) {
				super.writeUTF(data);
			} else {
				super.writeShort(0);
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
//				case NetDataTypes.TYPE_LONG: 
//					writeLong(obj as int);
//					break;
				case NetDataTypes.TYPE_FLOAT: 
					writeFloat(obj as Number);
					break;
//				case NetDataTypes.TYPE_DOUBLE: 
//					writeDouble((Double)obj);
//					break;
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