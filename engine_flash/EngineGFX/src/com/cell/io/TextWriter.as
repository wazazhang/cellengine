package com.cell.io
{
	import flash.utils.IDataInput;

	public class TextWriter
	{
		private var buffer : String = "";
		
		public function TextWriter() 
		{
		}
		
		public function write(text:String) : void 
		{
			buffer += text;
		}
		
		public function toString() : String
		{
			return buffer;
		}
	}
}