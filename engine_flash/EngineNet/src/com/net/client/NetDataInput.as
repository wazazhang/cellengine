package com.net.client
{
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class NetDataInput extends ByteArray
	{
		var factory : MessageFactory ;
		
		public function NetDataInput(factory : MessageFactory)
		{
			this.factory = factory;
		}
		
		public function readBooleanArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readBoolean();
			}
			return ret;
		}
		
		public function readCharArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readShort();
			}
			return ret;
		}
		
		public function readByteArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readByte();
			}
			return ret;
		}
		
		public function readShortArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readShort();
			}
			return ret;
		}
		
		public function readIntArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readInt();
			}
			return ret;
		}
				
		public function readFloatArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = super.readFloat();
			}
			return ret;
		}
			
		public function readUTFArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readJavaUTF();
			}
			return ret;
		}
	
		public function readExternalArray() : Array
		{
			var count : int = super.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readExternal();
			}
			return ret;
		}
		
		public function readExternal() : Message
		{
			var type : int = super.readInt();
			if (type > 0) {
				var ret : Message = factory.createMessage(type);
				factory.readExternal(ret, this);
				return ret;
			} else {
				return null;
			}
		}
				
		public function readChar() : int
		{
			return super.readShort();
		}
		
		public function readJavaUTF() : String {
			return super.readUTF();
		}
		
		
		public function readAnyArray(component_data_type : int) : Array {
			var count : int = readInt();
			if (count == 0) {
				return null;
			}
			if (count < 0) { // 表示成员还是个数组
				count = -count;
				var array : Array = new Array(count);
				for (var i : int = 0; i < count; i++) {
					array[i] = readAnyArray(component_data_type);
				}
				return array;
			} else if (count > 0) { // 表示成员是个通常对象
				var array : Array = new Array(count);
				for (var i : int = 0; i < count; i++) {
					array[i] = readAny(component_data_type);
				}
				return array;
			}
			return null;
		}
		

		public function readAny(component_data_type : int) : Object
		{
			switch (component_data_type) {
				case NetDataTypes.TYPE_EXTERNALIZABLE:
					return readExternal();
				case NetDataTypes.TYPE_BOOLEAN:
					return readBoolean();
				case NetDataTypes.TYPE_BYTE:
					return readByte();
				case NetDataTypes.TYPE_CHAR:
					return readChar();
				case NetDataTypes.TYPE_SHORT:
					return readShort();
				case NetDataTypes.TYPE_INT:
					return readInt();
//				case NetDataTypes.TYPE_LONG:
//					return readLong();
				case NetDataTypes.TYPE_FLOAT:
					return readFloat();
				case NetDataTypes.TYPE_DOUBLE:
					return readDouble();
				case NetDataTypes.TYPE_STRING: 
					return readUTF();
//				case NetDataTypes.TYPE_OBJECT:
//					return readObject(component_type);
				default:
					return null;
			}
			
			return null;
		}

		
	}
}
