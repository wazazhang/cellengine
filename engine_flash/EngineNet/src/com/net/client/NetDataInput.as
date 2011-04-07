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
		
		
		public function readAnyArray(type : Object) : Message {
			var count : int = super.readInt();
			if (count == 0) {
				return null;
			} else if (count < 0) { // 表示成员还是个数组
				count = -count;
				for (var i : int = 0; i < count; i++) {
					readAnyArray(type);
				}
			} else if (count > 0) { // 表示成员是个通常对象
				for (var i : int = 0; i < count; i++) {
					readAny(type);
				}
			}
			return null;
		}

		public function readAny(type : Object) : Message
		{
			
			
			return null;
		}

		
	}
}
