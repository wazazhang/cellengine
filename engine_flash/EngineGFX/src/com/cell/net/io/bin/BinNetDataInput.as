package com.cell.net.io.bin
{
	import com.cell.net.io.BaseNetDataInput;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataInput;
	import com.cell.net.io.NetDataTypes;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class BinNetDataInput extends BaseNetDataInput
	{
		public var input : ByteArray;
		
		public function BinNetDataInput(factory : MessageFactory, input:ByteArray)
		{
			super(factory);
			this.input = input;
		}
		
		
		
		override public function readBoolean():Boolean {
			return input.readBoolean();
		}
		
		override public function readByte():int {
			return input.readByte();
		}
		
		override public function readDouble():Number {
			return input.readDouble();
		}
		
		override public function readFloat():Number {
			return input.readFloat();
		}
		
		override public function readInt():int {
			return input.readInt();
		}
		
		override public function readShort():int {
			return input.readShort();
		}
		
		override public function readUnsignedByte():uint {
			return input.readUnsignedByte();
		}
		
		override public function readUnsignedInt():uint {
			return input.readUnsignedInt();
		}
		
		override public function readUnsignedShort():uint{
			return input.readUnsignedShort();
		}
		
		override public function readLong() : Number
		{
			var h : int = input.readInt();
			var l : int = input.readInt();
			return (h << 32) + l;
		}
		
		override public function readChar() : int
		{
			return input.readShort();
		}
		
		override public function readJavaUTF() : String 
		{
			return input.readUTF();
		}
		
		
		
		
		
		
		
		
		
		
		
	}
}
