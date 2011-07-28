package com.cell.bms.file
{

	/**
	 * 定义在 Header 的 HeaderDefine 
	 * @author WAZA
	 */
	public class NoteDefine
	{		
		public var command		: String;
		public var index		: String;
		public var value		: String;
		public var value_res	: IDefineResource;
		
		public function NoteDefine(
			command: String,
			index: String,
			value: String,
			value_res : IDefineResource) 
		{
			this.command	= command;
			this.index		= index;
			this.value		= value;
			this.value_res	= value_res;
		}
		
		public function dispose() : void 
		{
			if (value_res!=null) {
				value_res.dispose();
			}
		}
	}
	
}