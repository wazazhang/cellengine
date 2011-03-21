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
			super.writeInt(array.length);
			for each (var d : Boolean in array) {
				super.writeBoolean(d);
			}
		}
		
		public function writeCharArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : int in array) {
				super.writeShort(d);
			}
		}
		
		public function writeByteArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : int in array) {
				super.writeByte(d);
			}
		}
		
		public function writeShortArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : int in array) {
				super.writeShort(d);
			}
		}
		
		public function writeIntArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : int in array) {
				super.writeInt(d);
			}
		}
		
		public function writeFloatArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : Number in array) {
				super.writeFloat(d);
			}
		}
		
		public function writeUTFArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : String in array) {
				super.writeUTF(d);
			}
		}
		
		public function writeExternalArray(array : Array) : void
		{
			super.writeInt(array.length);
			for each (var d : Message in array) {
				this.writeExternal(d);
			}
		}
		
		
		public function writeExternal(data : Message) : void
		{
			factory.writeExternal(data, this);
			
		}
		
		public function writeChar(data : int) : void
		{
			super.writeShort(data);
			
		}
		
	}
}