package com.cell.net.io.text
{
	import com.cell.io.TextWriter;
	import com.cell.net.io.BaseNetDataOutput;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.NetDataOutput;
	import com.cell.net.io.NetDataTypes;
	
	import flash.utils.ByteArray;
	
	public class TextNetDataOutput extends BaseNetDataOutput
	{
		private var output : TextWriter;
		
		public function TextNetDataOutput(factory : MessageFactory)
		{
			super(factory);
			this.output = new TextWriter();
			
		}
		
		public function toString() : String
		{
			return output.toString();
		}
		
//		-------------------------------------------------------------------------------------------------------------------
//		
//		-------------------------------------------------------------------------------------------------------------------
		
		
		private function putNext(text:*) : void 
		{
			output.write(text+",");
		}
		
		private function putNextLen(text:String) : void
		{
			output.write(text.length+","+text+",");
		}
		
		
//		-------------------------------------------------------------------------------------------------------------------
//		
//		-------------------------------------------------------------------------------------------------------------------
		
		
		
		/**写入布尔值。*/
		override public function writeBoolean(value:Boolean):void {
			putNext(value);
		}
		
		
		/**写入一个字节。*/
		override public function writeByte(value:int):void{
			putNext(value);
		}
		
		
		/**写入 IEEE 754 双精度（64 位）浮点数。*/
		override public function writeDouble(value:Number):void{
			putNext(value);
		}
		
		
		/**写入 IEEE 754 单精度（32 位）浮点数。*/
		override public function writeFloat(value:Number):void{
			putNext(value);
		}
		
		
		/**写入一个带符号的 32 位整数。*/
		override public function writeInt(value:int):void{
			putNext(value);
		}
		
		
		/**写入一个 16 位整数。*/
		override public function writeShort(value:int):void{
			putNext(value);
		}
		
		
		/**写入一个无符号的 32 位整数。*/
		override public function writeUnsignedInt(value:uint):void{
			putNext(value);
		}
		
		override public function writeLong(v : Number) : void
		{
			putNext(v);
		}
		
		override public function writeChar(data : int) : void
		{
			this.writeShort(data);
		}
		
		override public function writeJavaUTF(data : String) : void
		{
			if (data != null) {
				this.putNextLen(data);
			} else {
				this.writeShort(0);
			}
		}
		
//		-------------------------------------------------------------------------------------------------------------------
//		
//		-------------------------------------------------------------------------------------------------------------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}