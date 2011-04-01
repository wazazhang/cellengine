package com.net.client
{
	import flash.utils.ByteArray;

	public class NetDataOutput extends ByteArray
	{
		var factory : MessageFactory ;
		
		public function NetDataOutput(factory : MessageFactory)
		{
			this.factory = factory;
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
				for each (var d : Message in array) {
					this.writeExternal(d);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		
		public function writeExternal(data : Message) : void
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
		
		
		
		public function writeAnyArray(array : Array) : void
		{
			if (array != null) {
				var count : int = array.length;
				if (count > 0) {
					var component : Object = array[0];
					if (component is Array) {
						super.writeInt(-count); 	// 表示成员还是个数组
						for (var i : int = 0; i < count; i++) {
							writeAnyArray(array[i]);
						}
					} else {
						super.writeInt(count);	// 表示成员是个通常对象
						for (var i : int = 0; i < count; i++) {
							writeAny(array[i]);
						}
					}
				} else {
					super.writeInt(0);
				}
			} else {
				super.writeInt(0);
			}
		}
		
		public function writeAny(obj : Object) : void
		{
			var ext_type : int = factory.getType(obj as Message);
			if (ext_type > 0) {
				writeExternal(obj as Message);
			}
			// TODO 有问题，还未完全实现。
		}
		
	}
}