package com.cell.io
{
	import flash.utils.IDataInput;

	public class TextReader
	{
		private var buffer : String;
		private var pos : int;
		
		public function TextReader(buf:String) 
		{
			this.buffer = buf;
		}
		
		public function read() : String 
		{
			if (pos < buffer.length) {
				var ret:String = buffer.substring(pos, pos+1);
				pos ++;
				return ret;
			}
			return null;
		}
		
		public function readLen(len:int) : String
		{
			if (pos < buffer.length - len) {
				var ret:String = buffer.substring(pos, pos + len);
				pos += len;
				return ret;
			} else {
				var ret:String = buffer.substring(pos, buffer.length);
				pos = buffer.length;
				return ret;
			}
		}
		
		public function skip(n:int) : void  {
			pos += n;
		}
		
		public function position() : int {
			return pos;
		}
		
		public function setPosition(pos:int) : void {
			this.pos = pos;
		}
		
		public function remain() : int {
			return buffer.length - pos;
		}
		
	}
}