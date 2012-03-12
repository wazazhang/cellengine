package com.cell.net.io
{
	import com.cell.io.TextDeserialize;
	import com.cell.io.TextReader;
	
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class TextNetDataInput extends BaseNetDataInput implements NetDataInput
	{
		private var input : TextReader;
		
		public function TextNetDataInput(factory:MessageFactory, input:TextReader)
		{
			super(factory);
			this.input = input;
		}
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		
		private function getNext() : String 
		{
			var sb : String = "";
			while (true) {
				var r : String = input.read();
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
				var r : String = input.read();
				if (r == null) 
					break;
				sb += r;
			}
			input.skip(1);
			return sb;
		}
		
		
		
		//		-------------------------------------------------------------------------------------------------------------------
		//		
		//		-------------------------------------------------------------------------------------------------------------------
		
		override public function readBoolean():Boolean {
			return Boolean(getNext());
		}
		
		override public function readByte():int {
			return int(getNext());
		}
		
		override public function readDouble():Number {
			return Number(getNext());
		}
		
		override public function readFloat():Number {
			return Number(getNext());
		}
		
		override public function readInt():int {
			return int(getNext());
		}
		
		override public function readShort():int {
			return int(getNext());
		}
		
		override public function readUnsignedByte():uint {
			return uint(getNext());
		}
		
		override public function readUnsignedInt():uint {
			return uint(getNext());
		}
		
		override public function readUnsignedShort():uint{
			return uint(getNext());
		}
		
		override public function readChar() : int
		{
			return this.readShort();
		}
		
//		override public function readLong() : Number
//		{
//			return Number(getNext());
//		}
		
		override public function readJavaUTF() : String {
			var len:int = readUnsignedShort();
			return getNextLen(len);
		}
		
		
		
		override public function readDate() : Date
		{
			var dd : Number = readDouble();
			var ret : Date = new Date();
			ret.setTime(dd);
			return ret;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}
}
