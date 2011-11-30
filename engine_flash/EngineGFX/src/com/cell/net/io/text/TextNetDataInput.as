package com.cell.net.io.text
{
	import com.cell.io.TextReader;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataInput;
	import com.cell.net.io.NetDataTypes;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class TextNetDataInput extends TextReader implements NetDataInput
	{
		protected var factory 	: MessageFactory ;
				
		public function TextNetDataInput(factory:MessageFactory, srcData:String)
		{
			super(srcData);
			this.factory = factory;
		}
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		private function getNext() : String 
		{
			var sb : String = "";
			while (true) {
				var r : String = this.read();
				if (r == null) 
					break;
				if (r == ",") 
					break;
				sb += r;
			}
			return sb;
		}
		
		private function getNextLen(len:int) : String
		{
			var sb : String = "";
			for (var i:int=0; i<len; i++) {
				var r : String = this.read();
				if (r == null) 
					break;
				sb += r;
			}
			skip(1);
			return sb;
		}
		
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		public function readBoolean():Boolean {
			return Boolean(getNext());
		}
		
		public function readByte():int {
			return int(getNext());
		}
		
		public function readDouble():Number {
			
			return Number(getNext());
		}
		
		public function readFloat():Number {
			return Number(getNext());
		}
		
		public function readInt():int {
			return int(getNext());
		}
		
		public function readShort():int {
			return int(getNext());
		}
		
		public function readUnsignedByte():uint {
			return uint(getNext());
		}
		
		public function readUnsignedInt():uint {
			return uint(getNext());
		}
		
		public function readUnsignedShort():uint{
			return uint(getNext());
		}
		
		public function readLong() : Number
		{
			return Number(getNext());
		}
		
		public function readUTF():String {
			var len:int = readUnsignedShort();
			return getNextLen(len);
		}
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public function readBooleanArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readBoolean();
			}
			return ret;
		}
		
		public function readCharArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readShort();
			}
			return ret;
		}
		
		public function readByteArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readByte();
			}
			return ret;
		}
		
		public function readShortArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readShort();
			}
			return ret;
		}
		
		public function readIntArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readInt();
			}
			return ret;
		}
				
		public function readFloatArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readFloat();
			}
			return ret;
		}
			
		public function readUTFArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readJavaUTF();
			}
			return ret;
		}
	
		public function readExternalArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readExternal();
			}
			return ret;
		}
		
		public function readExternal() : MutualMessage
		{
			var type : int = this.readInt();
			if (type > 0) {
				var ret : MutualMessage = factory.createMessage(type);
				factory.readExternal(ret, this);
				return ret;
			} else {
				return null;
			}
		}
				
		public function readChar() : int
		{
			return this.readShort();
		}
		
		public function readJavaUTF() : String {
			return this.readUTF();
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
				case NetDataTypes.TYPE_LONG:
					return readLong();
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
