package com.cell.bms.file
{
	public class Note
	{
		public var line				: int;
		public var track			: int;
		public var command			: String;
		public var value			: String;
		public var note_define		: NoteDefine;

		public var begin_position	: Number;
		public var end_position	: Number;

		function Note(
			line : int,
			track : int, 
			begin_position : Number, 
			end_position : Number,
			command : String,
			value : String,
			note_define : NoteDefine)
		{
			this.line			= line;
			this.track			= track;
			this.command		= command;
			this.value			= value;
			this.note_define	= note_define;
			this.begin_position	= begin_position;
			this.end_position	= end_position;
		}
		
		function validate() : Boolean
		{
			switch(command)
			{
				case DataCommand.INDEX_BMP_BG:
				case DataCommand.INDEX_BMP_POOR:
				case DataCommand.INDEX_BMP_LAYER:
				case DataCommand.INDEX_BPM:
				case DataCommand.INDEX_STOP:
				case DataCommand.INDEX_WAV_BG:
				case DataCommand.INDEX_WAV_KEY_1P_:
				case DataCommand.INDEX_WAV_KEY_2P_:
				case DataCommand.INDEX_WAV_LONG_KEY_1P_:
				case DataCommand.INDEX_WAV_LONG_KEY_2P_:
					return note_define != null;
				default:
					return true;
			}
		}
		
		/** 是否为长音 */
		public function isLongKey() : Boolean {
			return this.begin_position != this.end_position;
		}
		
		public function compareTo(o:Note) : int {
			return (int)(this.begin_position - o.begin_position);
		}
		
		public function toString() : String {
			return line + " : " + track + " : " + command + " : " + value + " : " + begin_position;
		}
		
		
	}
}