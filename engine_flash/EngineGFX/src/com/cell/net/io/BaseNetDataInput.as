package com.cell.net.io
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class BaseNetDataInput implements NetDataInput
	{
		protected var factory : MessageFactory;
		
		
		public function BaseNetDataInput(factory : MessageFactory) {
			this.factory = factory;
		}
		
		public function readBoolean():Boolean{
			throw new Error("Not impl in abstract class!");
		}
		
		public function readByte():int{
			throw new Error("Not impl in abstract class!");
			}
				
		public function readDouble():Number{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readFloat():Number{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readInt():int{
			throw new Error("Not impl in abstract class!");
			}

		public function readShort():int{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readUnsignedByte():uint{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readUnsignedInt():uint{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readUnsignedShort():uint{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readChar() : int{
			throw new Error("Not impl in abstract class!");
			}
		
		public function readJavaUTF() : String{
			throw new Error("Not impl in abstract class!");
			}
		
//		public function readLong() : Number{
//			throw new Error("Not impl in abstract class!");
//		}
		
		
		public function readDate() : Date
		{
			var size : int = readByte();
			if (size != 0) 
			{
				var YY : int = readShort();
				var MM : int = readByte();
				var DD : int = readByte();
				
				var hh : int = readByte();
				var mm : int = readByte();
				var ss : int = readByte();
				
				var ms : int = readShort();
				
				return new Date(YY, MM, DD, hh, mm, ss, ms);
			}
			return null;
		}
		
		
		public function readEnum() : String
		{
			return readJavaUTF();
		}
		
		
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
		
		public function readMutualArray() : Array
		{
			var count : int = this.readInt();
			var ret : Array = new Array(count);
			for (var i : int = 0; i<count; i++) {
				ret[i] = this.readMutual();
			}
			return ret;
		}
		
		public function readMutual() : MutualMessage
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
			switch (component_data_type) 
			{
//				case NetDataTypes.TYPE_EXTERNALIZABLE:
				case NetDataTypes.TYPE_MUTUAL:
					return readMutual();
					
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
					return readJavaUTF();
				case NetDataTypes.TYPE_DATE:
					return readDate();
					//				case NetDataTypes.TYPE_OBJECT:
					//					return readObject(component_type);
				default:
					return null;
			}
			
			return null;
		}
		
		
		
		public function readCollection(compNetType:int) : Array
		{
			var size : int = readInt();
			var ret : Array = new Array(size);
			if (size > 0) {
				for (var i:int=0; i<size; i++) {
					var data : * = readAny(compNetType);
					ret[i] = (data);
				}
			}
			return ret;
		}
		
		public function readMap(keyNetType:int, valueNetType:int) : Map
		{
			var size : int = readInt();
			var ret : Map = new Map();
			if (size > 0) {
				for (var i:int=0; i<size; i++) {
					var key  : * = readAny(keyNetType);
					var value: * = readAny(valueNetType);
					ret.put(key, value);
				}
			}
			return ret;
		}
	}
}
