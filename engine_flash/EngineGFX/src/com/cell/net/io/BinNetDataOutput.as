package com.cell.net.io
{
	
	import flash.utils.ByteArray;
	
	public class BinNetDataOutput extends BaseNetDataOutput
	{
		public var output : ByteArray;
		
		public function BinNetDataOutput(factory : MessageFactory, output : ByteArray)
		{
			super(factory);
			this.output = output;
		}
		
		
		
		/**写入布尔值。*/
		override public function writeBoolean(value:Boolean):void {
			output.writeBoolean(value);
		}
		
		
		/**写入一个字节。*/
		override public function writeByte(value:int):void{
			output.writeByte(value);
		}
		
		
		/**写入 IEEE 754 双精度（64 位）浮点数。*/
		override public function writeDouble(value:Number):void{
			output.writeDouble(value);
		}
		
		
		/**写入 IEEE 754 单精度（32 位）浮点数。*/
		override public function writeFloat(value:Number):void{
			output.writeFloat(value);
		}
		
		
		/**写入一个带符号的 32 位整数。*/
		override public function writeInt(value:int):void{
			output.writeInt(value);
		}
		
		
		/**写入一个 16 位整数。*/
		override public function writeShort(value:int):void{
			output.writeShort(value);
		}
		
		
		/**写入一个无符号的 32 位整数。*/
		override public function writeUnsignedInt(value:uint):void{
			output.writeUnsignedInt(value);
		}
		
		
//		override public function writeLong(v : Number) : void
//		{
//			var l : int = v & 0xffffffff;
//			var h : int = (v>>32) & 0xffffffff;
//			output.writeInt(h);
//			output.writeInt(l);
//		}
		
		override public function writeChar(data : int) : void
		{
			output.writeShort(data);
		}
		
		override public function writeJavaUTF(data : String) : void
		{
			if (data != null) {
				output.writeUTF(data);
			} else {
				output.writeShort(0);
			}
		}
		
		
		
		
		
		
	}
}